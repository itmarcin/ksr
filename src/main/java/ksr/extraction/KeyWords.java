package ksr.extraction;

import ksr.deserialization.Article;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class KeyWords {

    public List<String> keyWords;

    String path ="src/resources/keywords.txt";

    public void initializeKeyWords() throws IOException {
        keyWords = loadFromFile(path);
    }

    public List<String> loadFromFile(String path) throws IOException {
        List<String> keyWords = new ArrayList<>();

        BufferedReader bufferedReader;
        try {
            FileReader fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String[] list = bufferedReader.readLine().split(" ");
            keyWords.addAll(Arrays.asList(list));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return keyWords;
    }

    public void saveToFile(){
        File file = new File(path);
        FileWriter fileWriter;
        try{
            fileWriter = new FileWriter(file);
            for(String word: keyWords){
                fileWriter.write(word + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getWordOccurrence(List<Article> articles) {
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
                .limit(15)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


        wordsMap.forEach((key, value) -> {
            keyWords.add(key);
        });

        saveToFile();
    }
}