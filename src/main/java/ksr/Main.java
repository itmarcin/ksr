package ksr;

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
            System.out.println("FINISH");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}