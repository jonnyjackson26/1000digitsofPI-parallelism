import java.math.BigDecimal;
import java.math.RoundingMode;

public class PiWorker extends Thread {
    private TaskQueue taskQueue;
    private ResultTable resultTable;
    private static final int SCALE = 1100;  // To ensure precision beyond 1000 digits

    public PiWorker(TaskQueue taskQueue, ResultTable resultTable) {
        this.taskQueue = taskQueue;
        this.resultTable = resultTable;
    }

    @Override
    public void run() {
        Integer task;
        while ((task = taskQueue.getTask()) != null) {
            // Compute the Pi digit using the BBP formula
            int digit = computePiDigit(task);
            // Store the result in the result table
            resultTable.storeResult(task, digit);

            // Display progress for every 10th digit computed
            if (task % 10 == 0) {
                System.out.print(".");
                System.out.flush();
            }
        }
    }

    // BBP formula to compute the nth hexadecimal digit of Pi and convert to decimal
    private int computePiDigit(int n) {
        BigDecimal pi = BigDecimal.ZERO;

        for (int k = 0; k <= n; k++) {
            BigDecimal sixteenPowerK = BigDecimal.valueOf(16).pow(k);

            // BBP terms
            BigDecimal term1 = BigDecimal.valueOf(4).divide(BigDecimal.valueOf(8 * k + 1), SCALE, RoundingMode.HALF_UP);
            BigDecimal term2 = BigDecimal.valueOf(2).divide(BigDecimal.valueOf(8 * k + 4), SCALE, RoundingMode.HALF_UP);
            BigDecimal term3 = BigDecimal.ONE.divide(BigDecimal.valueOf(8 * k + 5), SCALE, RoundingMode.HALF_UP);
            BigDecimal term4 = BigDecimal.ONE.divide(BigDecimal.valueOf(8 * k + 6), SCALE, RoundingMode.HALF_UP);

            // Add the terms to the sum, divided by 16^k
            BigDecimal sumTerms = term1.subtract(term2).subtract(term3).subtract(term4);
            pi = pi.add(sumTerms.divide(sixteenPowerK, SCALE, RoundingMode.HALF_UP));
        }

        // Extract the nth digit after the decimal point in base 10
        pi = pi.setScale(SCALE, RoundingMode.HALF_UP);
        BigDecimal nthDigit = pi.multiply(BigDecimal.TEN.pow(n)).remainder(BigDecimal.TEN);
        return nthDigit.intValue();
    }
}
