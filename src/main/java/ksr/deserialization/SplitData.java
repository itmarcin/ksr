package ksr.deserialization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SplitData {

    public List<List<Article>> splitArticles(List<Article> articles, float ratio){
        Collections.shuffle(articles);
        List<List<Article>> splittedArticles = new ArrayList<>();
        int point = (int)(articles.size()*ratio);
        splittedArticles.add(articles.subList(0,point));
        splittedArticles.add(articles.subList(point,articles.size()));

        return splittedArticles;
    }
}
