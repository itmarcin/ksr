package ksr.algorithms.metrics;

import ksr.extraction.Properties;

public class EuclidesMetric implements Metric {

    Similarity similarity = new Similarity();

    private float calculateSingleVectorPointDistance(float f1, float f2) {
        return (f1 - f2) * (f1 - f2);
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
        distance += similarity.calculateWordsSimilarity(prop1.topWordOccurence, prop2.topWordOccurence);
        distance += similarity.calculateWordsSimilarity(prop1.topKeyWordOccurence, prop2.topKeyWordOccurence);

        return (float) Math.sqrt(distance);
    }


}
