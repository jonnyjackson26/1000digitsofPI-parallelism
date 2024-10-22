import java.util.LinkedList;
import java.util.Collections;

public class TaskQueue {
    private LinkedList<Integer> taskQueue;

    public TaskQueue() {
        taskQueue = new LinkedList<>();
    }

    public synchronized void addTask(int task) {
        taskQueue.add(task);
    }

    public synchronized Integer getTask() {
        if (taskQueue.isEmpty()) {
            return null;
        }
        return taskQueue.removeFirst();
    }

    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }
}
