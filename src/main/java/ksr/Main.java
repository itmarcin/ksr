package ksr;

import ksr.algorithms.KNN;
import ksr.algorithms.metrics.EuclidesMetric;
import ksr.algorithms.metrics.Metric;
import ksr.deserialization.Article;
import ksr.deserialization.Deserializer;
import ksr.deserialization.SplitData;
import ksr.extraction.Extractor;
import ksr.extraction.KeyWords;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        try {

            float percentageOfTrainingSet = 0.5F;
            int kNeighbours = 3;

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

            Metric metric = new EuclidesMetric();

            SplitData splitter = new SplitData();

            List<List<Article>> splittedArticles = splitter.splitArticles(articleList,percentageOfTrainingSet);

            List<Article> articlesTraining = splittedArticles.get(0);

            List<Article> articlesTesting = splittedArticles.get(1);

            KNN knn = new KNN(kNeighbours, articlesTraining,metric);

            System.out.println("\n\nDISTANCE MAPS:\n\n");

            int wrongs=0, rights=0;

            for (Article article : articlesTesting){
                //System.out.println("DISTANCE MAP OF ARTICLE {" + article.getTitle() + "}");
               if(!knn.getPredictedArticleCountry(article).equals(article.getCountry())){
                  // System.out.println("WRONG PREDICTION!");
                   wrongs++;
               }
               else{
                  // System.out.println("GOOD PREDICTION");
                   rights++;
               }
            }

            //TODO: coś jest nie tak, bo niezależnie od wielkości zbioru testowego zawsze dostajemy bardzo podobny, o ile nie taki sam % poprawnych...
            System.out.println("WRONGS: "+wrongs+"\nRIGHTS: "+rights+"\n% of RIGHTS: "+((rights*1.0F / (wrongs+rights))*100)+"\nTOTAL ANALYZED: "+(wrongs+rights)+"\nTOTAL ARTICLES: "+articleList.size());


            System.out.println("FINISH");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}