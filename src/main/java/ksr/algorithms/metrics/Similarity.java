package ksr.algorithms.metrics;

public class Similarity {

    public float calculateWordsSimilarity(String str1, String str2) {
        int N = str1.length();
        int n = 3;

        int numberOfPossibleGrams = N - n + 1;

        float invertedNumberOfPossibleGrams = 1.0F / numberOfPossibleGrams;

        int numberOfActualGram = 0;

        for (int i = 0; i < numberOfPossibleGrams; i++) {
            String gram = str1.substring(i, i + n);
            if (str2.contains(gram)){
                numberOfActualGram++;
            }
        }

        return invertedNumberOfPossibleGrams * numberOfActualGram;
    }
}
