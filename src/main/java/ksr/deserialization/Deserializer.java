package ksr.deserialization;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Deserializer {
    File[] files;

    public Deserializer() throws IOException {
        File directory = new File("src/Resources/");
        this.files = directory.listFiles();
    }

    private List<Document> getRawDocuments() throws IOException {
        List<Document> rawDocuments = new ArrayList<Document>();
        for (File f : files) {
            rawDocuments.add(Jsoup.parse(f, "UTF-8", ""));
        }
        return rawDocuments;
    }

    public List<Article> getArticles() throws IOException {
        List<Document> rawDocuments = getRawDocuments();
        List<Article> articles = new ArrayList<>();

        for (Document rawDocument : rawDocuments) {
            for (Element element : rawDocument.select("reuters")) {
                String title = element.select("title").text();
                String places = element.select("places").text();
                String text = element.select("text").text();
                articles.add(new Article(text, title, places));
            }
        }
        return articles;
    }


}
