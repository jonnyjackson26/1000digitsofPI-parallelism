import java.util.HashMap;

public class ResultTable {
    private HashMap<Integer, Integer> resultMap;

    public ResultTable() {
        resultMap = new HashMap<>();
    }

    public synchronized void storeResult(int index, int value) {
        resultMap.put(index, value);
    }

    public synchronized int getResult(int index) {
        return resultMap.getOrDefault(index, -1);
    }
}
