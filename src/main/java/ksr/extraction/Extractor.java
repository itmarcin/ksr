package ksr.extraction;

import ksr.deserialization.Article;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extractor {

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
        List<String> words = List.of(article.getText().split(" "));

        Map<String, Integer> wordsCount = new HashMap<>();
        float avgWordsLength = 0F;
        int range1 = 0;
        int range2 = 0;
        int range3 = 0;
        int maxWordLength = 0;
        int punctuations = 0;
        for (String word : words) {
            if (wordsCount.containsKey(word)) {
                wordsCount.replace(word, wordsCount.get(word) + 1);
            }
            wordsCount.putIfAbsent(word, 1);

            avgWordsLength += word.length();

            if (word.length() <= 3)
                range1 += 1;
            else if (word.length() <= 8)
                range2 += 1;
            else
                range3 += 1;

            if (word.length() > maxWordLength)
                maxWordLength = word.length();
        }

        Pattern p = Pattern.compile("\\p{Punct}");
        Matcher m = p.matcher(article.getText());
        while (m.find()) {
            punctuations += 1;
        }

        Properties prop = new Properties();
        prop.uniqueWordsCount = countUniqueWords(wordsCount);
        prop.avgWordLength = avgWordsLength / words.size();
        prop.wordsRangeOneThree = range1;
        prop.wordsRangeFourEight = range2;
        prop.wordsRangeNineInf = range3;
        prop.maxWordLength = maxWordLength;
        prop.wordsCount = words.size();
        prop.punctuationsCount = punctuations;
        prop.TopWordOccurence = Collections.max(wordsCount.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();

        //System.out.println("TITLE: "+article.getTitle()+"\nKEY: "+prop.TopWordOccurence+"\nVAL: "+ wordsCount.get(prop.TopWordOccurence)+"\n");
        article.setProperties(prop);
    }

    public void extract(List<Article> articles) {
        for (Article article : articles) {
            extractArticle(article);
        }
    }

    //9. Najczęściej występujące słowo
    //10.

}
