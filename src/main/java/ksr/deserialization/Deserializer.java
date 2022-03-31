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
import java.util.Scanner;

public class Deserializer {
    private File[] files;
    private List<String> stopWords;


    public Deserializer() throws IOException {
        File directory = new File("src/Resources/");
        this.files = directory.listFiles();
        this.stopWords = loadStopWords();
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
                if (!element.select("text").text().isEmpty()) {
                    String title = element.select("title").text();
                    String places = element.select("places").text();

                    String country = "";

                    List<String> placesList = new ArrayList<>();
                    for(Element place : element.select("places").select("d")){
                        placesList.add(place.text());
                    }
                    if(placesList.size() == 1 && places.matches("west-germany|usa|france|uk|canda|japan")){
                        country = placesList.get(0);

                        List<String> text = removeStopWords(
                                steemWords(
                                        List.of(
                                                element.select("text").text()
                                                        .replaceAll("[^a-zA-Z ]", "")
                                                        .split(" ")
                                        )
                                )
                        );
                        text.removeAll(Arrays.asList("", null));

                        articles.add(new Article(text, title, country));
                    }
                }
            }
        }
        return articles;
    }

    private List<String> steemWords(List<String> textToSteeme) {
        List<String> stemmedWord = new ArrayList<String>();
        PorterStemmer porterStemmer = new PorterStemmer();
        for (String word : textToSteeme) {
            stemmedWord.add(porterStemmer.stem(word));
        }
        return stemmedWord;
    }

    private List<String> removeStopWords(List<String> text) {
        List<String> ret = new ArrayList<String>();
        for (String word : text) {
            if (!stopWords.contains(word)) {
                ret.add(word);
            }
        }
        return ret;
    }

    private List<String> loadStopWords() {
        List<String> stopWords = new ArrayList<>();
        File file = new File("src/Resources/stop_words_english.txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine())
                stopWords.add(sc.nextLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopWords;
    }
}
