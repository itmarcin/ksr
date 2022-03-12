package ksr.deserialization;

import opennlp.tools.stemmer.PorterStemmer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
                List<String> text = steemWords(List.of(element.select("text").text().replaceAll("[^a-zA-Z\\s]", "").split(" ")));
                text.removeAll(Arrays.asList("", null));
                articles.add(new Article(text, title, places));
            }
        }
        return articles;
    }

    public List<String> steemWords(List<String> textToSteeme) {
        List<String> stemmedWord = new ArrayList<String>();
        PorterStemmer porterStemmer = new PorterStemmer();
        for (String word : textToSteeme) {
            stemmedWord.add(porterStemmer.stem(word));
        }
        return stemmedWord;
    }

}
