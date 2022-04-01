package ksr.algorithms.metrics;

import ksr.extraction.Properties;

public class TaxicabMetric implements Metric {

    Similarity similarity = new Similarity();

    @Override
    public float calculateDistance(Properties prop1, Properties prop2) {
        float distance=0;

        distance += Math.abs(prop1.wordsRangeOneFive - prop2.wordsRangeOneFive);
        distance += Math.abs(prop1.wordsRangeSixInf - prop2.wordsRangeSixInf);
        distance += Math.abs(prop1.wordsCount - prop2.wordsCount);
        distance += Math.abs(prop1.keyWordsCount - prop2.keyWordsCount);
        distance += Math.abs(prop1.uniqueWordsCount - prop2.uniqueWordsCount);
        distance += Math.abs(prop1.uniqueKeyWordsCount - prop2.uniqueKeyWordsCount);
        distance += Math.abs(prop1.maxWordLength - prop2.maxWordLength);
        distance += Math.abs(prop1.avgWordLength - prop2.avgWordLength);
        distance += Math.abs(similarity.calculateWordsSimilarity(prop1.topWordOccurence, prop2.topWordOccurence));
        distance += Math.abs(similarity.calculateWordsSimilarity(prop1.topKeyWordOccurence, prop2.topKeyWordOccurence));

        return distance;
    }
}
