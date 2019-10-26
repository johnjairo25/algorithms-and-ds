package algorithms.sorting;

import java.util.*;

public class FraudulentActivityNotifications {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int elements = sc.nextInt(); // 0 < x <= 2 * 10^5
        int windowSize = sc.nextInt(); // 0 < x < elements
        int[] expenditure = new int[elements];
        for (int i = 0; i < elements; i++) {
            expenditure[i] = sc.nextInt(); // 0 <= x <= 200
        }
        System.out.println(new BruteForceSolverWithSlidingWindow(expenditure, windowSize).findNumberOfAlerts());
    }

    private static abstract class Solver {

        int[] expenditures;
        int windowSize;

        int findNumberOfAlerts() {
            int alerts = 0;
            double[] medians = findSlidingWindowMedian();
            for (int i = 0; i < medians.length - 1; i++) {
                if (expenditures[i + windowSize] >= 2 * medians[i]) {
                    alerts++;
                }
            }
            return alerts;
        }

        abstract double[] findSlidingWindowMedian();

    }

    private static class CountingSortSolver extends Solver {

        private static final int MAX_POSSIBLE_VALUE = 200;
        private long[] frequencies;

        CountingSortSolver(int[] expenditures, int windowSize) {
            this.expenditures = expenditures;
            this.windowSize = windowSize;
            initFrequencies();
        }

        private void initFrequencies() {
            frequencies = new long[MAX_POSSIBLE_VALUE + 1];
            for (int i = 0; i < windowSize; i++) {
                frequencies[expenditures[i]]++;
            }
        }

        double[] findSlidingWindowMedian() {
            double[] result = new double[expenditures.length - windowSize + 1];
            result[0] = median();
            for (int i = windowSize; i < expenditures.length; i++) {
                frequencies[expenditures[i]]++;
                frequencies[expenditures[i - windowSize]]--;
                result[i - windowSize + 1] = median();
            }
            return result;
        }

        private double median() {
            boolean isEven = windowSize % 2 == 0;
            int left = isEven ? windowSize / 2 : (windowSize + 1) / 2;
            int right = isEven ? (windowSize/2) + 1 : (windowSize + 1) / 2;
            return ((double) getElementAt(left) + getElementAt(right)) / 2.0;
        }

        private int getElementAt(int position) {
            int currentPosition = 0;
            for (int medianToTest = 0; medianToTest <= MAX_POSSIBLE_VALUE; medianToTest ++) {
                currentPosition += frequencies[medianToTest];
                if (currentPosition >= position) {
                    return medianToTest;
                }
            }
            throw new RuntimeException("should not get to this point");
        }

    }

    private static class HeapsSolver extends Solver {

        private PriorityQueue<Integer> lowersMaxHeap;
        private PriorityQueue<Integer> highersMinHeap;

        HeapsSolver(int[] expenditures, int windowSize) {
            this.expenditures = expenditures;
            this.windowSize = windowSize;
            this.lowersMaxHeap = new PriorityQueue<>(Collections.reverseOrder());
            this.highersMinHeap = new PriorityQueue<>();
        }

        double[] findSlidingWindowMedian() {
            double[] result = new double[expenditures.length - windowSize + 1];

            for (int i = 0; i < expenditures.length; i++) {
                addElement(expenditures[i]);
                if (lowersMaxHeap.size() + highersMinHeap.size() > windowSize) {
                    removeElement(expenditures[i - windowSize]);
                }
                balanceElements();
                if (lowersMaxHeap.size() + highersMinHeap.size() == windowSize) {
                    result[i - windowSize + 1] = medianValue();
                }
            }

            return result;
        }

        private void addElement(Integer element) {
            if (lowersMaxHeap.isEmpty() || lowersMaxHeap.peek() > element) {
                lowersMaxHeap.offer(element);
            } else {
                highersMinHeap.offer(element);
            }
        }

        private void removeElement(Integer element) {
            if (lowersMaxHeap.peek() >= element) {
                lowersMaxHeap.remove(element);
            } else {
                highersMinHeap.remove(element);
            }
        }

        private void balanceElements() {
            PriorityQueue<Integer> biggerHeap = lowersMaxHeap.size() >= highersMinHeap.size()
                    ? lowersMaxHeap : highersMinHeap;
            PriorityQueue<Integer> smallerHeap = lowersMaxHeap.size() < highersMinHeap.size()
                    ? lowersMaxHeap : highersMinHeap;
            if (biggerHeap.size() - smallerHeap.size() >= 2) {
                Integer toBalance = biggerHeap.poll();
                smallerHeap.offer(toBalance);
            }
        }

        private double medianValue() {
            if (lowersMaxHeap.size() > highersMinHeap.size()) {
                return lowersMaxHeap.peek();
            } else if (highersMinHeap.size() > lowersMaxHeap.size()) {
                return highersMinHeap.peek();
            } else {
                return ((double) lowersMaxHeap.peek() + highersMinHeap.peek()) / 2.0;
            }
        }

    }

    private static class BruteForceSolverWithSlidingWindow extends Solver {

        BruteForceSolverWithSlidingWindow(int[] expenditures, int windowSize) {
            this.expenditures = expenditures;
            this.windowSize = windowSize;
        }

        double[] findSlidingWindowMedian() {
            double[] result = new double[expenditures.length - windowSize + 1];
            for (int i = 0; i < result.length; i++) {
                int[] currentWindow = Arrays.copyOfRange(expenditures, i, i + windowSize);
                result[i] = medianOf(currentWindow);
            }
            return result;
        }

        private double medianOf(int[] array) {
            Arrays.sort(array);
            int left = (int) Math.floor((array.length - 1.0) / 2.0);
            int right = (int) Math.ceil((array.length - 1.0) / 2.0);
            return ((double)array[left] + array[right]) / 2.0;
        }

    }

}