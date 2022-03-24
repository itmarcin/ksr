package ksr;

import ksr.deserialization.Article;
import ksr.deserialization.Deserializer;
import ksr.extraction.Extractor;
import ksr.extraction.KeyWords;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        try {
            Deserializer des = new Deserializer();
            List<Article> articleList = des.getArticles();

            //for(Article article : articleList)
            //    System.out.println(article.toString());
            KeyWords kw = new KeyWords();

            //TODO!!
            kw.generateKeyWords(articleList);

            kw.initializeKeyWords();
            Extractor extractor = new Extractor(kw);
            extractor.extract(articleList);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}