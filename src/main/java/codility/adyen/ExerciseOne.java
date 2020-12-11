package codility.adyen;

import java.util.*;

public class ExerciseOne {

    public int solution(int[] sortedArray, int hammerAttempts) {
        Integer[] frequencies = buildFrequenciesArray(sortedArray);
        Integer[] elementsLeft = buildElementsLeft(sortedArray, frequencies);
        int[] maximumFrequency = buildMaximumFrequency(hammerAttempts, frequencies, elementsLeft);
        return Arrays.stream(maximumFrequency).max().orElse(0);
    }

    private Integer[] buildFrequenciesArray(int[] sortedArray) {
        List<Integer> frequencyList = new ArrayList<>();
        Integer currentValue = sortedArray[0];
        int reps = 0;
        for (int newValue : sortedArray) {
            boolean sameValue = currentValue.equals(newValue);
            if (!sameValue) frequencyList.add(reps);
            reps = sameValue ? reps + 1 : 1;
            currentValue = newValue;
        }
        frequencyList.add(reps);
        return frequencyList.toArray(new Integer[0]);
    }

//    private Integer[] buildFrequenciesArray(int[] sortedArray) {
//        Map<Integer, Integer> numberToFrequencyMap = new HashMap<>();
//        Arrays.stream(sortedArray).forEach(element -> {
//            Integer frequency = numberToFrequencyMap.getOrDefault(element, 0);
//            numberToFrequencyMap.put(element, frequency + 1);
//        });
//        return numberToFrequencyMap.values().toArray(new Integer[0]);
//    }

    private Integer[] buildElementsLeft(int[] sortedArray, Integer[] frequencies) {
        int elementsToCheck = sortedArray.length;
        Integer[] elementsLeft = new Integer[frequencies.length];
        for (int i = 0; i < frequencies.length; i++) {
            elementsToCheck -= frequencies[i];
            elementsLeft[i] = elementsToCheck;
        }
        return elementsLeft;
    }

    private int[] buildMaximumFrequency(int hammerAttempts, Integer[] frequencies, Integer[] elementsLeft) {
        int[] result = new int[frequencies.length];
        for (int i = 0; i < frequencies.length; i++) {
            result[i] = frequencies[i] + Math.min(elementsLeft[i], hammerAttempts);
        }
        return result;
    }

}
