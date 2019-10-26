# Fraudulent Activity Notifications

Source: [Hackerrank](https://www.hackerrank.com/challenges/fraudulent-activity-notifications/problem).

## Statement

HackerLand National Bank has a simple policy for warning clients about possible fraudulent account
activity. If the amount spent by a client on a particular day is _greater than or equal to_ `2x` 
the client's _median_ spending for a _trailing number of days_, they send the client a notification 
about potential fraud. The bank doesn't send the client any notifications until they have at least 
that trailing number of prior days' transaction data.

Given the number of trailing days `d` and a client's total daily expenditures for a period of `n`
days, find and print the number of times the client will receive a notification over all `n` days.

For example, `d=3` and `expenditures = [10,20,30,40,50]`. On the first three days, they just collect
spending data. At day _4_, we have trailing expenditures of `[10,20,30]`. The median is `20` and the
day's expenditure is `40`. Because `40 >= 2*20`, there will be a notice. The next day, our trailing
expenditures are `[20,30,40]` and the expenditures are `50`. This is less than `2x30` so no notice
will be sent. Over the period, there was one notice sent.

## Constraints

- `1 <= n <= 2 * 10^5`
- `1 <= d <= n`
- `0 <= expenditure[i] <= 200`

## Solutions

We can basically divide the problem in two parts:

1. First, calculate the sliding window of size `d` median of the `expenditure` array. The output
of this part will be an array of size `n - d + 1`.
2. Second, based on the `slidingWindowMedian` calculate the alerts.

The second part seems trivial, so lets start with a basic skeleton for the solution:

```java
abstract class Solver {
        
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
```

Now the problem reduces to implementing the `double[] findSlidingWindowMedian()` method.

### Brute Force Approach

The easiest solution is to use brute force to calculate the median for all possible trailing
subsets, as shown in the following implementation: 

```java
class BruteForceSolver extends Solver {

    BruteForceSolver(int[] expenditures, int windowSize) {
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
```

We now have a working solution, but, the problem with this solution is its time complexity:

- The `Arrays.sort(array)` has a complexity of `O(n log(n))`.
- And the `double findSlidingWindow()` has a cycle that calls this sort operation, so the final
time complexity is `O(n^2 log(n))`.

This does not seem like a reasonable solution considering that we will be handling arrays of 
size `2 * 10^5`.

### Heaps Approach

The second alternative that comes to mind is based on the use of Heaps. A [heap](../heaps.md) is a
complete binary tree. Usually there are two types of heaps: min-heap and max-heap, where the root
node is min or max according to its type.

Java provides a way to implement heaps, by using priority queues as shown below:

```java
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
```

The following is true for the Java implementation of a Priority queue:

- Adding an element and removing one from the top is `O(log(n))`
- Removing any other element is `O(n)`
- Peeking (getting the top element of the heap) is constant time.

The idea to implement this solution using Heaps is:

1. We can have two heaps, one for storing all values higher than the median and the other to
store all values lower than the median.
2. To make the algorithm work, we should maintain the two heaps balanced. That means that they 
should never have a difference of more than one element in their sizes.
3. In each iteration we will add the new element of the window, and remove one too.


```java
class HeapsSolver extends Solver {

    private PriorityQueue<Integer> lowersMaxHeap;
    private PriorityQueue<Integer> highersMinHeap;

    HeapsSolver(int[] expenditures, int windowSize) {
        this.expenditures = expenditures;
        this.windowSize = windowSize;
        this.lowersMaxHeap = new PriorityQueue<>(Collections.reverseOrder());
        this.highersMinHeap = new PriorityQueue<>();
    }

    // This method is O(n)
    double[] findSlidingWindowMedian() {
        double[] result = new double[expenditures.length - windowSize + 1];

        for (int i = 0; i < expenditures.length; i++) {
            addElement(expenditures[i]); // log(n)
            if (lowersMaxHeap.size() + highersMinHeap.size() > windowSize) { // n
                removeElement(expenditures[i - windowSize]);
            }
            balanceElements(); // log(n)
            if (lowersMaxHeap.size() + highersMinHeap.size() == windowSize) { // k
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
```

Based on a similar analysis to the one shown in the previous approach, we can arrive to the
conclusion that we have a time complexity of `O(n^2)`. Which is better than the previous case
but still not ideal. 

### Count sort approach

The last and fastest approach for this problem is based on the restriction:
`0 <= expenditure[i] <= 200`. Given that we can take a different approach to find the median each
window. 

The approach is to have an array of `frequencies` for each of the possible values that the
expenditure can take. In this case that means values from 0 to 200. The median will be found
arriving to the corresponding index of the frequencies.

```java
class CountingSortSolver extends Solver {
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

    // O(k)
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
        // k
        boolean isEven = windowSize % 2 == 0;
        int left = isEven ? windowSize / 2 : (windowSize + 1) / 2;
        int right = isEven ? (windowSize/2) + 1 : (windowSize + 1) / 2;
        return ((double) getElementAt(left) + getElementAt(right)) / 2.0;
    }

    private int getElementAt(int position) { // O(k)
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
```

With this approach we know that finding the median takes constant time which gives as a total
complexity of `O(n)`.
