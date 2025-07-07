package heroes.journey.utils.ai;

import heroes.journey.GameState;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.utils.gamestate.Utils;

import java.util.UUID;
import java.util.concurrent.*;

public class MCTS {
    protected static final int THREADS = 25;
    protected static final double EXPLORATION_FACTOR = Math.sqrt(2);
    protected static final double DEPTH_TO_SIMULATE = 100;
    protected static final long TIME_LIMIT_NANOS = 1_000_000_000L; // 1 second in nanoseconds

    public QueuedAction runMCTS(GameState rootState, UUID playingEntity, Scorer scorer) {
        final long start = System.nanoTime();

        Node root = new Node(null, scorer, playingEntity);
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        CompletionService<Void> completionService = new ExecutorCompletionService<>(executor);

        int submitted = 0;
        int completed = 0;

        while (System.nanoTime() - start < TIME_LIMIT_NANOS) {
            if (submitted - completed < THREADS) {
                // Submit a new task
                completionService.submit(() -> {
                    Node selectedNode;
                    synchronized (root) {
                        selectedNode = select(root, playingEntity);
                    }

                    GameState clonedState = selectedNode.reconstructGameStateFromRoot(rootState);

                    synchronized (selectedNode) {
                        selectedNode = expand(selectedNode, clonedState);
                    }

                    double result = selectedNode.rollout(playingEntity, clonedState);
                    clonedState.getWorld().dispose();

                    synchronized (root) {
                        backpropagate(selectedNode, result, playingEntity);
                    }

                    return null;
                });
                submitted++;
            }

            // Poll for completed tasks to avoid overfilling
            Future<Void> result = completionService.poll();
            if (result != null) {
                try {
                    result.get(); // Make sure it's not throwing
                    completed++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        executor.shutdownNow();

        Utils.logTime("runMCTS completed " + completed + " simulations in ", start, 100);
        return root.bestChildByVisits().getQueuedAction();
    }

    private Node select(Node node, UUID rootPlayer) {
        while (node.hasChildren()) {
            node = node.bestUCTChild(rootPlayer);
        }
        return node;
    }

    private Node expand(Node node, GameState gameState) {
        node.expand(gameState);
        if (node.hasChildren()) {
            node = node.getRandomUnvisitedChild();
        }
        return node;
    }

    private void backpropagate(Node node, double result, UUID resultPlayer) {
        while (node != null) {
            node.updateStats(result, resultPlayer);
            node = node.getParent();
        }
    }
}
