package heroes.journey.utils.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import heroes.journey.GameState;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.QueuedAction;

public class MCTS {
    protected static final int SIMULATIONS = 2000;
    protected static final int THREADS = 10;
    protected static final double EXPLORATION_FACTOR = Math.sqrt(2);
    protected static final double DEPTH_TO_SIMULATE = 10;
    protected static final long TIME_LIMIT_NANOS = 1_000_000_000L; // 1 second in nanoseconds

    public QueuedAction runMCTS(GameState rootState, UUID playingEntity, Scorer scorer) {
        long start = System.nanoTime();

        Node root = new Node(null, scorer, playingEntity);
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int i = 0; i < SIMULATIONS; i++) {
            int finalI = i;
            tasks.add(() -> {
                Node selectedNode;
                synchronized (root) {
                    selectedNode = select(root, playingEntity);
                }

                GameState clonedState = selectedNode.reconstructGameStateFromRoot(rootState);

                synchronized (selectedNode) {
                    selectedNode = expand(selectedNode, clonedState);
                }

                long startSim = System.nanoTime();
                double result = selectedNode.rollout(playingEntity, clonedState);
                Utils.logTime("simulation " + finalI + "took", startSim, 50);

                clonedState.getWorld().dispose();

                synchronized (root) {
                    backpropagate(selectedNode, result, playingEntity);
                }

                return null;
            });
        }

        long completedCount = 0;
        try {
            List<Future<Void>> results = executor.invokeAll(tasks, TIME_LIMIT_NANOS, TimeUnit.NANOSECONDS);
            completedCount = results.stream().filter(f -> !f.isCancelled()).count();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdownNow(); // force shutdown to cancel any remaining tasks

        Utils.logTime("runMCTS completed " + completedCount + " simulations in ", start, 100);
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
