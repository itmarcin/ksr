package ksr.extraction;

import ksr.deserialization.Article;

import java.io.*;
import java.util.*;

public class Extractor {

    List<String> keyWords;

    public Extractor(KeyWords kw) {
        keyWords = kw.getKeyWords();
    }


    private int countUniqueWords(Map<String, Integer> wordsCount) {
        int uniqueWordsCounter = 0;
        for (Integer value : wordsCount.values()) {
            if (value == 1) {
                uniqueWordsCounter++;
            }
        }
        return uniqueWordsCounter;
    }


    private void extractArticle(Article article) {
        List<String> words = article.getText();

        List<String> keyWordsInArticle = new ArrayList<>();

        Map<String, Integer> keyWordsCount = new HashMap<>();
        Map<String, Integer> wordsCount = new HashMap<>();
        float avgWordsLength = 0F;
        int range1 = 0;
        int range2 = 0;
        int maxWordLength = 0;
        for (String word : words) {
            if (wordsCount.containsKey(word)) {
                wordsCount.replace(word, wordsCount.get(word) + 1);
            }
            wordsCount.putIfAbsent(word, 1);

            for (String keyWord : keyWords) {
                if (keyWord.equals(word)) {
                    keyWordsInArticle.add(word);
                    if (keyWordsCount.containsKey(word)) {
                        keyWordsCount.replace(word, keyWordsCount.get(word) + 1);
                    }
                    keyWordsCount.putIfAbsent(word, 1);
                }
            }

            avgWordsLength += word.length();

            if (word.length() <= 5)
                range1++;
            else
                range2++;

            if (word.length() > maxWordLength)
                maxWordLength = word.length();
        }

        Properties prop = new Properties();
        prop.wordsRangeOneFive = range1;
        prop.wordsRangeSixInf = range2;
        prop.wordsCount = words.size();
        prop.keyWordsCount = keyWordsInArticle.size();
        prop.uniqueWordsCount = countUniqueWords(wordsCount);
        prop.uniqueKeyWordsCount = countUniqueWords(keyWordsCount);
        prop.avgWordLength = avgWordsLength / words.size();
        prop.maxWordLength = maxWordLength;
        prop.topWordOccurence = Collections.max(wordsCount.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        if(!keyWordsCount.isEmpty()){
            prop.topKeyWordOccurence = Collections.max(keyWordsCount.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        }
        else {
            prop.topKeyWordOccurence = "BRAK";
        }



        //System.out.println("TITLE: "+article.getTitle()+"\nKEY: "+prop.TopWordOccurence+"\nVAL: "+ wordsCount.get(prop.TopWordOccurence)+"\n");
        article.setProperties(prop);
    }

    public void extract(List<Article> articles) {
        for (Article article : articles) {
            extractArticle(article);
        }
    }

}
