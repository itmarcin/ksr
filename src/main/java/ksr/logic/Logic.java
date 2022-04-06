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


    public int runner(int k, int percentage, String metricStr){
        float percentageOfTrainingSet = (float) percentage / 100;

        Metric metric;
        switch (metricStr.toLowerCase(Locale.ROOT)) {
            case "chebyshev" -> metric = new ChebyshevMetric();
            case "euclides" -> metric = new EuclidesMetric();
            case "taxicab" -> metric = new TaxicabMetric();
            default -> throw new IllegalArgumentException("Nieznana metryka '" + metricStr + "'!");
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
            KNN knn = new KNN(k, articlesTraining, metric);

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

            float accuracy = qualityMeasure.calculateAccuracy();
            float generalPrecision = qualityMeasure.calculateGeneralPrecision();
            float generalRecall = qualityMeasure.calculateGeneralRecall();
            float f1 = qualityMeasure.calculateF1();

            System.out.println("============================");
            System.out.println("============================");
            System.out.println("=====PARAMETRY WEJŚCIOWE====");
            System.out.println("K\t\t\t\t- "+k);
            System.out.println("Podział zbioru\t- "+percentageOfTrainingSet+" / "+(1-percentageOfTrainingSet));
            System.out.println("Metryka\t\t\t- "+metricStr);
            System.out.println("============================");
            System.out.println("============================");
            System.out.println("===========WYNIKI===========");
            System.out.println("Accuracy\t\t- "+accuracy);
            System.out.println("Precision\t\t- "+generalPrecision);
            System.out.println("Recall\t\t\t- "+generalRecall);
            System.out.println("F1\t\t\t\t- "+f1);
            System.out.println("============================");
            System.out.println("============================");

            return 0;


        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
