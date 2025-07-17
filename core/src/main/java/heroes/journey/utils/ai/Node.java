package heroes.journey.utils.ai;

import static heroes.journey.utils.ai.MCTS.DEPTH_TO_SIMULATE;
import static heroes.journey.utils.ai.MCTS.EXPLORATION_FACTOR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.utils.Random;
import lombok.Getter;
import lombok.Setter;

public class Node {

    @Getter Node parent;
    final List<Node> children;
    private int visitCount;
    private double winScore;
    @Getter @Setter private QueuedAction queuedAction;
    private final Scorer scorer;
    private final UUID player;

    public Node(Node parent, Scorer scorer, UUID player) {
        this.parent = parent;
        this.children = Collections.synchronizedList(new ArrayList<>());
        this.visitCount = 0;
        this.winScore = 0.0;
        this.scorer = scorer;
        this.player = player;
    }

    // Get all possible QueuedActions and create child nodes
    public void expand(GameState gameState) {
        if (!children.isEmpty())
            return;

        UUID nextPlayer = gameState.getNextPlayer(player);

        List<QueuedAction> actions = scorer.getPossibleQueuedActions(gameState);
        for (QueuedAction action : actions) {
            Node child = new Node(this, scorer, nextPlayer);
            child.setQueuedAction(action);
            children.add(child);
        }
    }

    // Select the best child using UCT (Upper Confidence Bound for Trees)
    public Node bestUCTChild(UUID rootPlayer) {
        synchronized (children) {
            return children.stream()
                .max(Comparator.comparingDouble(child -> uctValue(child, rootPlayer)))
                .orElseThrow(() -> new IllegalStateException("No children found"));
        }
    }

    // Compute UCT value for a node
    private double uctValue(Node node, UUID rootPlayer) {
        if (node.visitCount == 0)
            return Double.MAX_VALUE; // Favor unexplored nodes
        double winRate = node.winScore / node.visitCount;

        // Flip the perspective if this node's player is not the root player
        if (!node.player.equals(rootPlayer)) {
            winRate = 1.0 - winRate;
        }
        double exploration = EXPLORATION_FACTOR * Math.sqrt(Math.log(this.visitCount) / node.visitCount);
        return winRate + exploration;
    }

    // Get a random unexplored child
    public Node getRandomUnvisitedChild() {
        List<Node> unvisited = new ArrayList<>();
        for (Node child : children) {
            if (child.visitCount == 0) {
                unvisited.add(child);
            }
        }
        return unvisited.isEmpty() ? this : unvisited.get(Random.get().nextInt(unvisited.size()));
    }

    // Simulate a random game from this node and return a result
    public int rollout(UUID playingEntity, GameState gameState) {
        int depth = 0;
        while (scorer.getScore(gameState, playingEntity) > 0 && depth < DEPTH_TO_SIMULATE) {
            long start = System.nanoTime();
            List<QueuedAction> queuedActions = scorer.getPossibleQueuedActions(gameState);
            // TODO this feels like it shouldnt ever happen, they always have access to rest
            if (queuedActions.isEmpty()) {
                break;
            }
            QueuedAction randomQueuedAction = queuedActions.get(Random.get().nextInt(queuedActions.size()));
            gameState = gameState.applyAction(randomQueuedAction);
            depth++;
        }
        return scorer.getScore(gameState, playingEntity); // 1.0 if AI wins, 0.0 if loss, 0.5 for draw
    }

    // Back propagate the result up the tree
    public void updateStats(double result, UUID resultPlayer) {
        visitCount++;
        if (this.player.equals(resultPlayer)) {
            winScore += result;
        } else {
            winScore += (1.0 - result);
        }
    }

    // Get the most visited child (best QueuedAction selection)
    public Node bestChildByVisits() {
        return children.stream()
            .max(Comparator.comparingInt(n -> n.visitCount))
            .orElseThrow(() -> new IllegalStateException("No children available"));
    }

    public GameState reconstructGameStateFromRoot(GameState rootState) {
        GameState state = rootState.clone();
        List<QueuedAction> path = new ArrayList<>();
        Node current = this;

        // Walk backwards to gather actions
        while (current != null && current.queuedAction != null) {
            path.addFirst(current.queuedAction); // Prepend actions
            current = current.parent;
        }

        for (QueuedAction action : path) {
            state = state.applyAction(action); // Apply deltas in order
        }

        return state;
    }

    // Utility methods
    public boolean hasChildren() {
        return !children.isEmpty();
    }

}
