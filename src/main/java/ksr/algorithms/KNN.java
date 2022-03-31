package ksr.algorithms;

import ksr.algorithms.metrics.Metric;
import ksr.deserialization.Article;

import java.util.*;
import java.util.stream.Collectors;

public class KNN {

    private int k;
    private List<Article> trainingSet;
    private Metric metric;

    public KNN(int k, List<Article> articles, Metric metric) {
        this.k = k;
        this.trainingSet = articles;
        this.metric = metric;
    }

    private Map<Float, String> calculateDistance(Article article) {
        Map<Float, String> distanceMap = new HashMap<>();

        for (Article testedArticle : trainingSet) {
            float distance = metric.calculateDistance(article.getProperties(), testedArticle.getProperties());
            distanceMap.put(distance, testedArticle.getCountry());
        }

        return distanceMap;
    }

    private Map<Float, String> getKNeighbours(Map<Float, String> distances) {
        return distances
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .limit(k)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

//    private String getCountry(Map<Float, String> distances) {
//        String retCountry = "";
//
//        Map<String, Integer> countries = new HashMap<>();
//
//        List<String> mostCountries = new ArrayList<>();
//        int topOccurence = 0;
//        distances.forEach((distance, country) -> {
//            countries.putIfAbsent(country, 1);
//            countries.replace(country, countries.get(country) + 1);
//
//            if(!mostCountries.contains(country) && topOccurence) {
//                mostCountries.add(country);
//                topOccurence =
//            }
//        });
//
//
//
//        return retCountry;
//    }

    public String getPredictedArticleCountry(Article article) {
        String country = "";

        Map<Float, String> distances = calculateDistance(article);

        distances = getKNeighbours(distances);


        return country;
    }


}
