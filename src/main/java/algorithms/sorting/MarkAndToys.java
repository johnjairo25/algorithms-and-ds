package algorithms.sorting;

import java.util.Arrays;
import java.util.Scanner;

public class MarkAndToys {

    // source: https://www.hackerrank.com/challenges/mark-and-toys

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int numberOfToys = sc.nextInt();
        int amountToSpend = sc.nextInt();
        int[] prices = new int[numberOfToys];

        for (int i = 0; i < numberOfToys; i++) {
            prices[i] = sc.nextInt();
        }

        System.out.println(new MarkAndToysSolver(amountToSpend, prices).maxToysToBuy());
    }

    static class MarkAndToysSolver {

        private final int amountToSpend;
        private final int[] sortedPrices;

        MarkAndToysSolver(int amountToSpend, int[] prices) {
            this.amountToSpend = amountToSpend;
            Arrays.sort(prices);
            this.sortedPrices = prices;
        }

        int maxToysToBuy() {
            // greedy approach
            int currentItem = 0;
            int itemsBought = -1;
            int amountLeft = amountToSpend;
            while (amountLeft >= 0 && currentItem < sortedPrices.length) {
                itemsBought ++;
                amountLeft -= sortedPrices[currentItem];
                currentItem ++;
            }
            return itemsBought;
        }

    }

}
