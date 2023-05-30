package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PiCalculator {

    /**
     * Calculate pi and represent it as a BigDecimal object with the given floating point number (digits after . )
     * There are several algorithms designed for calculating pi, it's up to you to decide which one to implement.
     Experiment with different algorithms to find accurate results.

     * You must design a multithreaded program to calculate pi. Creating a thread pool is recommended.
     * Create as many classes and threads as you need.
     * Your code must pass all of the test cases provided in the test folder.

     * @param floatingPoint the exact number of digits after the floating point
     * @return pi in string format (the string representation of the BigDecimal object)
     * @author Abolfazl
     */

    public static final int MAX = 10_000_000;
    public static final int STEP = 1_000_000;
    public static final int THREADS_COUNT = 5;
    public static int precision;
    public static int current = 0;
    public static BigDecimal[] numbers = new BigDecimal[MAX];

    public static class ComputationThread extends Thread {
        int start;

        public ComputationThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    compute();
                    start = increment();
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }

        private synchronized int increment() {
            current += STEP;
            return current;
        }

        private void compute() {
            for (int i = start; i < start + STEP & i < MAX; i++) {

                BigDecimal x = new BigDecimal((i + 0.5) / MAX);
//                x = Math.pow(x, 2);
                x = x.pow(2);
                x = x.add(new BigDecimal("1"));
                BigDecimal max = new BigDecimal(MAX);
                x = x.multiply(max);
                x = (new BigDecimal(4)).divide(x, 1000, RoundingMode.DOWN);
                numbers[i] = x;
                if (i + 1 == MAX) throw new IndexOutOfBoundsException();
            }
        }
    }

    public String calculate(int floatingPoint)
    {
        Thread[] threads = new Thread[THREADS_COUNT];
        // create thread pool
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new ComputationThread(i * current);
        }
        // start threads
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i].start();
        }
        // wait end of computation
        for (int i = 0; i < THREADS_COUNT; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        BigDecimal sum = new BigDecimal("0");
        for (int i = 0; i < MAX; i++) {
            sum = sum.add(numbers[i]);
        }
        return sum.toString().substring(0, floatingPoint+2);
    }

    public static void main(String[] args) {
        // Use the main function to test the code yourself
        PiCalculator piCalculator = new PiCalculator();
        int x = 100;
        System.out.println(piCalculator.calculate(x));
    }
}