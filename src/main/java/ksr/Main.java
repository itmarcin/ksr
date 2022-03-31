package ksr;

import ksr.algorithms.KNN;
import ksr.algorithms.metrics.EuclidesMetric;
import ksr.algorithms.metrics.Metric;
import ksr.deserialization.Article;
import ksr.deserialization.Deserializer;
import ksr.extraction.Extractor;
import ksr.extraction.KeyWords;

import java.io.Console;
import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("START");

            System.out.println("DESERIALIZER START");
            Deserializer des = new Deserializer();
            List<Article> articleList = des.getArticles();

            //for(Article article : articleList)
            //    System.out.println(article.toString());

            //articleList = articleList.subList(0,5);

            System.out.println("KEYWORDS START");
            KeyWords kw = new KeyWords();

            if(kw.isKeyWordsEmpty()){
                kw.generateKeyWords(articleList);
            }

            System.out.println("EXTRACTING START");
            Extractor extractor = new Extractor(kw);
            extractor.extract(articleList);

            System.out.println("EXTRACTED:");
            int tmpIterator = 0;
            for(Article article: articleList){
                if(article.getProperties().keyWordsCount>0){
                    ++tmpIterator;
                    System.out.println(article.getProperties().toString());
                }
            }
            System.out.println("NUMBER OF ARTICLES WITH KEYWORDS:" + tmpIterator);

            List<Article> testedArticles = articleList.subList(0,1000);

            Metric metric = new EuclidesMetric();

            KNN knn = new KNN(10, testedArticles,metric);

            System.out.println("\n\nDISTANCE MAPS:\n\n");

            for (Article article : articleList.subList(1000, articleList.size())){
                System.out.println("DISTANCE MAP OF ARTICLE {" + article.getTitle() + "}");
               // System.out.println(knn.calculateDistance(article));
            }


            System.out.println("FINISH");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}