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
import java.security.KeyPair;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.List.*;

public class Logic {

    List<String> countries = List.of("west-germany", "usa", "france", "uk", "canada", "japan");

    String fileName;

    public Logic() {
        fileName = "EXPORT_";
        fileName += new SimpleDateFormat("yyyy-MM-dd_HHmm").format(Calendar.getInstance().getTime());
        fileName += ".txt";
    }

    public int runner(int k, int percentage, String metricStr) {

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
            if (kw.isKeyWordsEmpty()) {
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
            for (Article article : articlesTesting) {
                predictedCountry.add(knn.getPredictedArticleCountry(article));
                realCountry.add(article.getCountry());
            }

            //7. Analyzing results
            QualityMeasure qualityMeasure = new QualityMeasure(predictedCountry, realCountry, this.countries);

            float accuracy = qualityMeasure.calculateAccuracy();
            float generalPrecision = qualityMeasure.calculateGeneralPrecision();
            float generalRecall = qualityMeasure.calculateGeneralRecall();
            float f1 = qualityMeasure.calculateF1();

            Map<String, Float> precisions = new HashMap<>();
            Map<String, Float> recalls = new HashMap<>();
            for (String country : countries) {
                precisions.put(country, qualityMeasure.calculatePrecision(of(country)).get(0));
                recalls.put(country, qualityMeasure.calculateRecall(of(country)).get(0));
            }

            //8. Show results
            System.out.println("============================");
            System.out.println("============================");
            System.out.println("=====PARAMETRY WEJSCIOWE====");
            System.out.println("K\t\t\t\t= " + k);
            System.out.println("Podzial zbioru\t= " + percentageOfTrainingSet + " / " + (1 - percentageOfTrainingSet));
            System.out.println("Metryka\t\t\t- " + metricStr);
            System.out.println("============================");
            System.out.println("============================");
            System.out.println("===========WYNIKI===========");
            System.out.println("Accuracy\t\t= " + toPercentage(accuracy));
            System.out.println("Precision\t\t= " + toPercentage(generalPrecision));
            System.out.println("Recall\t\t\t= " + toPercentage(generalRecall));
            System.out.println("F1\t\t\t\t= " + toPercentage(f1));
            System.out.println("Precisions:");

            //for (Map.Entry<String, Float> prec : precisions.entrySet()) {
            //    System.out.println("\t- " + prec.getKey() + " = " + toPercentage(prec.getValue()));
            //}
            precisions.forEach((key, value) -> System.out.println("\t- " + key + " = " + value));

            System.out.println("Recalls:");
            for (Map.Entry<String, Float> prec : recalls.entrySet()) {
                System.out.println("\t- " + prec.getKey() + " = " + toPercentage(prec.getValue()));
            }
            System.out.println("============================");
            System.out.println("============================");

            //9. Export results
            ExportDataModel dataModel = new ExportDataModel(metricStr, k, percentageOfTrainingSet, accuracy, generalPrecision, generalRecall, f1, precisions, recalls);

            Exporter exporter = new Exporter(fileName);
            exporter.export(dataModel);

            return 0;

        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public void runFinal() {

        List<String> metrics = List.of("chebyshev", "euclides", "taxicab");

        /*
        for(String metric: metrics){
            runner(2,50,metric);
            runner(5,50,metric);
            runner(10,50,metric);
            runner(15,50,metric);
            runner(20,50,metric);
            runner(30,50,metric);
            runner(50,50,metric);
            runner(75,50,metric);
            runner(90,50,metric);
            runner(125,50,metric);
        }
        */
        /*
        for(String metric: metrics){
            runner(20,10,metric);
            runner(20,30,metric);
            runner(20,50,metric);
            runner(20,70,metric);
            runner(20,90,metric);
        }
         */

        for (String metric : metrics) {
            runner(20, 60, metric);
            runner(20, 60, metric);
            runner(20, 60, metric);
            runner(20, 60, metric);
            runner(20, 60, metric);
        }


    }

    private String toPercentage(float f) {
        NumberFormat format = NumberFormat.getPercentInstance();
        return format.format(f);
    }

}
