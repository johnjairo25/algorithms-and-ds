package hackerrank.implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ClimbingTheLeaderboard {


  public static List<Integer> climbingLeaderboard(List<Integer> rankedScores, List<Integer> playerScores) {
    List<Integer> positions = buildPositionArray(rankedScores);
    int[] rankedScoresArray = rankedScores.stream().mapToInt(i -> Integer.MAX_VALUE - i).toArray();

    return playerScores.stream()
        .map(s -> Integer.MAX_VALUE - s)
        .map(score -> findPosition(score, rankedScoresArray, positions))
        .collect(toList());
  }

  private static List<Integer> buildPositionArray(List<Integer> rankedScores) {
    int position = 1;
    List<Integer> positions = new java.util.ArrayList<>();
    positions.add(position);
    for (int i = 1; i < rankedScores.size(); i++) {
      if (Objects.equals(rankedScores.get(i), rankedScores.get(i - 1))) {
        positions.add(position);
      } else {
        position = position + 1;
        positions.add(position);
      }
    }
    return positions;
  }

  private static Integer findPosition(Integer score, int[] rankedScoresArray, List<Integer> positions) {
    int foundIndex = Arrays.binarySearch(rankedScoresArray, score);
    if (foundIndex >= 0) {
      return positions.get(foundIndex);
    } else {
      int insertionPoint = -foundIndex - 1;
      if (insertionPoint == 0) {
        return 1;
      } else {
        return positions.get(insertionPoint - 1) + 1;
      }
    }
  }

  public static void main(String[] args) {
    climbingLeaderboard(List.of(100,100,50,40,40,20,10), List.of(5, 25, 50, 120))
        .forEach(System.out::println);
  }

}
