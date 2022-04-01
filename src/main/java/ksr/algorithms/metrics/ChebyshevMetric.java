package ksr.algorithms.metrics;

import ksr.extraction.Properties;

public class ChebyshevMetric implements Metric {

    Similarity similarity = new Similarity();

    @Override
    public float calculateDistance(Properties prop1, Properties prop2) {
        float distance ;
        float temp;

        distance = Math.abs(prop1.wordsRangeOneFive - prop2.wordsRangeOneFive);
        temp=Math.abs(prop1.wordsRangeSixInf - prop2.wordsRangeSixInf);
        if( temp > distance )
            distance = temp;
        temp=Math.abs(prop1.wordsCount -prop2.wordsCount);
        if( temp > distance )
            distance = temp;
        temp=Math.abs(prop1.keyWordsCount - prop2.keyWordsCount);
        if( temp > distance )
            distance = temp;
        temp=Math.abs(prop1.uniqueWordsCount - prop2.uniqueWordsCount);
        if( temp > distance )
            distance = temp;
        temp=Math.abs(prop1.uniqueKeyWordsCount -  prop2.uniqueKeyWordsCount);
        if( temp > distance )
            distance = temp;
        temp=Math.abs(prop1.maxWordLength - prop2.maxWordLength);
        if( temp > distance )
            distance = temp;
        temp=Math.abs(prop1.avgWordLength - prop2.avgWordLength);
        if( temp > distance )
            distance = temp;
        temp=Math.abs(similarity.calculateWordsSimilarity(prop1.topWordOccurence, prop2.topWordOccurence));
        if( temp > distance )
            distance = temp;
        temp=Math.abs(similarity.calculateWordsSimilarity(prop1.topKeyWordOccurence, prop2.topKeyWordOccurence));
        if( temp > distance )
            distance = temp;

        return distance;
    }
}
