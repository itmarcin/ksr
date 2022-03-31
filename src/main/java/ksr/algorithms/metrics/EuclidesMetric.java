package ksr.algorithms.metrics;

import ksr.extraction.Properties;

public class EuclidesMetric implements Metric {

    private float calculateSingleVectorPointDistance(float f1, float f2) {
        return (f1 - f2) * (f1 - f2);
    }

    private float calculateWordsSimilarity(String str1, String str2) {
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


    @Override
    public float calculateDistance(Properties prop1, Properties prop2) {
        float distance = 0;

        distance += calculateSingleVectorPointDistance(prop1.wordsRangeOneFive, prop2.wordsRangeOneFive);
        distance += calculateSingleVectorPointDistance(prop1.wordsRangeSixInf, prop2.wordsRangeSixInf);
        distance += calculateSingleVectorPointDistance(prop1.wordsCount, prop2.wordsCount);
        distance += calculateSingleVectorPointDistance(prop1.keyWordsCount, prop2.keyWordsCount);
        distance += calculateSingleVectorPointDistance(prop1.uniqueWordsCount, prop2.uniqueWordsCount);
        distance += calculateSingleVectorPointDistance(prop1.uniqueKeyWordsCount, prop2.uniqueKeyWordsCount);
        distance += calculateSingleVectorPointDistance(prop1.maxWordLength, prop2.maxWordLength);
        distance += calculateSingleVectorPointDistance(prop1.avgWordLength, prop2.avgWordLength);
        distance += calculateWordsSimilarity(prop1.topWordOccurence, prop2.topWordOccurence);
        distance += calculateWordsSimilarity(prop1.topKeyWordOccurence, prop2.topKeyWordOccurence);

        return distance;
    }


}
