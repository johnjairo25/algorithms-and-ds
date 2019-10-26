package algorithms.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SortingComparator {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int numberOfPlayers = sc.nextInt();
        List<ScoredName> toSort = new ArrayList<>(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            ScoredName scoredName = new ScoredName(sc.next(), sc.nextInt());
            toSort.add(scoredName);
        }
        toSort.sort(new ScoredNameComparator().reversed());
        for (ScoredName item : toSort) {
            System.out.println(item);
        }
    }

    private static class ScoredName {

        private String name;
        private Integer score;

        ScoredName(String name, Integer score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return name + " " + score;
        }
    }

    private static class ScoredNameComparator implements Comparator<ScoredName> {

        @Override
        public int compare(ScoredName thiz, ScoredName that) {
            if (thiz.score > that.score) {
                return 1;
            } else if (thiz.score < that.score) {
                return -1;
            } else {
                return -1 * thiz.name.compareTo(that.name);
            }
        }
    }

}
