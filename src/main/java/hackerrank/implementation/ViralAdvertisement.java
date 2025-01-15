package hackerrank.implementation;

public class ViralAdvertisement {

  public static int viralAdvertising(int days) {
    int shared;
    int liked = 0;
    int cumulative = 0;

    for (int day = 1; day <= days; day++) {
      shared = day > 1 ? liked * 3 : 5;
      liked = Math.floorDiv(shared, 2);
      cumulative = cumulative + liked;
    }

    return cumulative;
  }

  public static void main(String[] args) {
    System.out.println(viralAdvertising(5));
  }

}
