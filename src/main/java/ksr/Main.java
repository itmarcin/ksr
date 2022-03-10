package ksr;

import ksr.deserialization.Article;
import ksr.deserialization.Deserializer;

import java.io.*;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        try {
            Deserializer des = new Deserializer();
            List<Article> articleList = des.getArticles();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}