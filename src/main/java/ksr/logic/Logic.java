package ksr.logic;

import ksr.algorithms.KNN;
import ksr.algorithms.metrics.ChebyshevMetric;
import ksr.algorithms.metrics.EuclidesMetric;
import ksr.algorithms.metrics.Metric;
import ksr.algorithms.metrics.TaxicabMetric;
import ksr.deserialization.Article;
import ksr.deserialization.Deserializer;
import ksr.deserialization.SplitData;
import ksr.extraction.Extractor;
import ksr.extraction.KeyWords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Logic {


    public int runner(int k, int percentage, String metric){
        float percentageOfTrainingSet = (float) percentage / 100;

        Metric metric1;
        switch (metric.toLowerCase(Locale.ROOT)) {
            case "chebyshev" -> metric1 = new ChebyshevMetric();
            case "euclides" -> metric1 = new EuclidesMetric();
            case "taxicab" -> metric1 = new TaxicabMetric();
            default -> throw new IllegalArgumentException("Nieznana metryka '" + metric + "'!");
        }
        try {
            //1. Reading files and creating deserialized list of articles
            Deserializer des = new Deserializer();
            List<Article> articleList = des.getArticles();

            //2. Initializing keywords.
            //if keywords are empty, generate them
            KeyWords kw = new KeyWords();
            if(kw.isKeyWordsEmpty()){
                kw.generateKeyWords(articleList);
            }

            //3. extracting properties for all articles
            Extractor extractor = new Extractor(kw);
            extractor.extract(articleList);

            //4. Splitting articles to training and testing sets
            SplitData splitter = new SplitData();
            List<List<Article>> splittedArticles = splitter.splitArticles(articleList, percentageOfTrainingSet);
            List<Article> articlesTraining = splittedArticles.get(0);
            List<Article> articlesTesting = splittedArticles.get(1);

            //5. Training our algorithm with training-set of articles
            KNN knn = new KNN(k, articlesTraining, metric1);

            //6. Predicting countries of articles in testing-set
            List<String> predictedCountry = new ArrayList<>();
            List<String> realCountry = new ArrayList<>();
            for (Article article : articlesTesting){
                predictedCountry.add(knn.getPredictedArticleCountry(article));
                realCountry.add(article.getCountry());
            }

            //7.Analyzing and exporting results
            QualityMeasure qualityMeasure = new QualityMeasure(predictedCountry,realCountry);
            //TODO wykonać wszystkie calculate
            //TODO zrobić klasę do eksportu do csvki
            return 0;


        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
