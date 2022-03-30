package ksr.extraction;

import ksr.deserialization.Article;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class KeyWords {

    private List<String> keyWords = new ArrayList<>();

    String path = "src/resources/keywords.txt";

    public KeyWords() throws IOException {
        initializeKeyWords();
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public boolean isKeyWordsEmpty() {
        return keyWords.isEmpty();
    }

    public void generateKeyWords(List<Article> articles) {

        Map<String, Integer> wordsOccurrence = getWordOccurrence(articles);

        Map<String, Float> wordsMap = new HashMap<>();

        for (Article article : articles) {

            for (String word : article.getText()) {
                if (!word.equals("Reuter") && !word.equals("REUTER") && !word.equals("mln")) {

                    float tf = 0;
                    for (String compWord : article.getText()) {
                        if (compWord.equals(word)) {
                            tf++;
                        }
                    }
                    tf = tf / article.getText().size();

                    float idf = wordsOccurrence.get(word);
                    idf = (float) (Math.log(articles.size()) / idf);

                    float tfidf = tf * idf;

                    wordsMap.put(word, tfidf);
                }
            }
        }

        wordsMap = wordsMap
                .entrySet()
                .stream()
                .sorted((Map.Entry.<String, Float>comparingByValue().reversed()))
                .limit(70)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


        wordsMap.forEach((key, value) -> {
            keyWords.add(key);
        });

        saveToFile();
    }

    private void initializeKeyWords() throws IOException {
        keyWords = loadFromFile(path);
    }

    private List<String> loadFromFile(String path) throws IOException {
        List<String> keyWords = new ArrayList<>();

        BufferedReader bufferedReader;
        try {
            File file = new File(path);
            if (file.exists()) {
                FileReader fileReader = new FileReader(path);
                bufferedReader = new BufferedReader(fileReader);
                String[] list = bufferedReader.readLine().split(" ");
                keyWords.addAll(Arrays.asList(list));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return keyWords;
    }

    private void saveToFile() {
        System.out.println("SAVE KEYWORDS START");
        try (FileWriter fileWriter = new FileWriter(path)) {
            for (String word : keyWords) {
                System.out.print(word + " ");
                fileWriter.write(word + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("SAVE KEYWORDS END");
    }

    private Map<String, Integer> getWordOccurrence(List<Article> articles) {
        Map<String, Integer> wordsOccurrence = new HashMap<>();
        for (Article article : articles) {
            for (String word : article.getText()) {
                if (wordsOccurrence.containsKey(word)) {
                    wordsOccurrence.replace(word, wordsOccurrence.get(word) + 1);
                }
                wordsOccurrence.putIfAbsent(word, 1);
            }
        }
        return wordsOccurrence;
    }

}
