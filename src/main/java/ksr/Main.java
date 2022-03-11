package ksr;

import ksr.deserialization.Article;
import ksr.deserialization.Deserializer;
import ksr.extraction.Extractor;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        try {
            Deserializer des = new Deserializer();
            List<Article> articleList = des.getArticles();

            //for(Article article : articleList)
            //    System.out.println(article.toString());
            List<Article> tmp = articleList.subList(0,5);
            Extractor extractor = new Extractor();
            extractor.extract(tmp);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}