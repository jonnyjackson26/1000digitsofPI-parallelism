import java.util.ArrayList;
import java.util.Collections;

public class ParallelPiComputation {

    public static void main(String[] args) throws InterruptedException {
        int numDigits = 1000;
        int numCores = Runtime.getRuntime().availableProcessors();

        TaskQueue taskQueue = new TaskQueue();
        ResultTable resultTable = new ResultTable();

        // Populate task queue with randomized tasks
        ArrayList<Integer> tasks = new ArrayList<>();
        for (int i = 1; i <= numDigits; i++) {
            tasks.add(i);
        }
        Collections.shuffle(tasks);
        for (int task : tasks) {
            taskQueue.addTask(task);
        }

        // Start worker threads
        PiWorker[] workers = new PiWorker[numCores];
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numCores; i++) {
            workers[i] = new PiWorker(taskQueue, resultTable);
            workers[i].start();
        }

        // Wait for all threads to finish
        for (PiWorker worker : workers) {
            worker.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("\nComputation took: " + (endTime - startTime) + "ms");

        // Display computed Pi digits
        System.out.print("Pi: 3.");
        for (int i = 1; i <= numDigits; i++) {
            System.out.print(resultTable.getResult(i));
        }
        System.out.println();
    }
}
