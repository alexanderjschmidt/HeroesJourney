package heroes.journey.utils.ai;

import heroes.journey.GameState;
import heroes.journey.entities.actions.QueuedAction;

import java.util.UUID;

public class MCTS {
    private static final int SIMULATIONS = 500;

    public QueuedAction runMCTS(GameState gameState, UUID playingEntity, Scorer scorer) {
        long start = System.nanoTime();
        Node root = new Node(gameState, null, scorer);
        for (int i = 0; i < SIMULATIONS; i++) {
            Node selectedNode = select(root);
            selectedNode = expand(selectedNode);
            double result = simulate(selectedNode, playingEntity);
            backpropagate(selectedNode, result);
        }
        System.out.println("runMCTS took " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
        return root.bestChildByVisits().getQueuedAction();
    }

    private Node select(Node node) {
        while (node.hasChildren()) {
            node = node.bestUCTChild();
        }
        return node;
    }

    private Node expand(Node node) {
        node.expand();
        if (node.hasChildren()) {
            node = node.getRandomUnvisitedChild();
        }
        return node;
    }

    private double simulate(Node node, UUID playingEntity) {
        long start = System.nanoTime();
        double result = node.rollout(playingEntity); // Random game simulation
        //System.out.println("simulate took " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
        return result;
    }

    private void backpropagate(Node node, double result) {
        while (node != null) {
            node.updateStats(result);
            node = node.getParent();
        }
    }
}
