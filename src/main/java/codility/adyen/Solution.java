package codility.adyen;

import java.util.Stack;

public class Solution {

    private Stack<Integer> internalStack;
    private Stack<Stack<Integer>> transactionsInProgress;

    public Solution() {
        this.internalStack = new Stack<>();
        this.transactionsInProgress = new Stack<>();
    }

    public void push(int value) {
        Stack<Integer> currentStack = getCurrentStack();
        currentStack.push(value);
    }

    public int top() {
        Stack<Integer> currentStack = getCurrentStack();
        return currentStack.isEmpty() ? 0 : currentStack.peek();
    }

    public void pop() {
        Stack<Integer> currentStack = getCurrentStack();
        if (!currentStack.isEmpty()) {
            currentStack.pop();
        }
    }

    public void begin() {
        Stack<Integer> currentStack = getCurrentStack();
        transactionsInProgress.push((Stack<Integer>) currentStack.clone());
    }

    public boolean rollback() {
        if (transactionsInProgress.isEmpty()) {
            return false;
        } else {
            transactionsInProgress.pop();
            return true;
        }
    }

    public boolean commit() {
        if (transactionsInProgress.isEmpty()) {
            return false;
        } else {
            Stack<Integer> newVersion = transactionsInProgress.pop();
            if (transactionsInProgress.isEmpty()) {
                this.internalStack = newVersion;
            } else {
                transactionsInProgress.pop();
                transactionsInProgress.push(newVersion);
            }
            return true;
        }
    }

    private Stack<Integer> getCurrentStack() {
        return transactionsInProgress.isEmpty() ? internalStack : transactionsInProgress.peek();
    }

    public static void test() {
        testNoTransaction();
        testEmptyStack();
        testBasicTransaction();
        testComplexTransaction();
    }

    private static void testNoTransaction() {
        Solution sol = new Solution();
        sol.push(5);
        sol.push(2);
        assert sol.top() == 2;
        sol.pop();
        assert sol.top() == 5;
    }

    private static void testEmptyStack() {
        Solution sol2 = new Solution();
        assert sol2.top() == 0;
        sol2.pop();
    }

    private static void testBasicTransaction() {
        Solution sol = new Solution();
        sol.push(4);
        sol.begin();                    // start transaction 1
        sol.push(7);              // stack: [4,7]
        sol.begin();                    // start transaction 2
        sol.push(2);              // stack: [4,7,2]
        assert sol.rollback() == true;  // rollback transaction 2
        assert sol.top() == 7;          // stack: [4,7]
        sol.begin();                    // start transaction 3
        sol.push(10);                   // stack: [4,7,10]
        assert sol.commit() == true;    // transaction 3 is committed
        assert sol.top() == 10;
        assert sol.rollback() == true;  // rollback transaction 1
        assert sol.top() == 4;          // stack: [4]
        assert sol.commit() == false;   // there is no open transaction
    }

    private static void testComplexTransaction() {
        Solution sol = new Solution();
        assert sol.top() == 0;
        sol.push(1);
        sol.push(2);
        sol.push(3); // [1,2,3]
        assert sol.rollback() == false;
        sol.begin(); // [1,2,3]
        sol.pop();
        sol.pop();
        assert sol.top() == 1;
        sol.begin(); // [1]
        sol.push(5);
        assert sol.top() == 5;
        sol.begin(); // [1,5]
        sol.pop();
        assert sol.rollback() == true; // [1,5]
        assert sol.commit() == true; // [1,5]
        sol.push(3);
        assert sol.commit() == true; // [1,5,3]
        assert sol.top() == 3;
        sol.pop();
        assert sol.top() == 5;
        sol.pop();
        assert sol.top() == 1;
        sol.pop();
        assert sol.top() == 0;
        sol.pop();
    }

}
