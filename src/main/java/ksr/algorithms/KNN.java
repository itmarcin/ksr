package ksr.algorithms;

import ksr.algorithms.metrics.Metric;
import ksr.deserialization.Article;

import java.util.*;
import java.util.stream.Collectors;

public class KNN {

    private final int k;
    private final List<Article> trainingSet;
    private final Metric metric;

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

    private String getCountry(Map<Float, String> distances) {

        String retCountry;

        //mapa grupująca k sąsiadów na podstawie krajów
        Map<String, Integer> countries = new HashMap<>();
        distances.forEach((distance, country) -> {
            countries.putIfAbsent(country, 1);
            countries.replace(country, countries.get(country) + 1);
        });

        //Mapa sortująca ilość krajów k sąsiadów
        Map<String, Integer> sortedCountries = countries
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        //Sprawdzenie warunku, gdy co najmniej dwa najczęściej występujące kraje mają taką samą liczebność
        List<Integer> sortedCountriesOccurences = sortedCountries.values().stream().toList();

        if (sortedCountriesOccurences.size() > 1 && Objects.equals(sortedCountriesOccurences.get(0), sortedCountriesOccurences.get(1))) {
            //zwracamy kraj najbliżej
            //TODO: powinniśmy zwrócić najbliżej sposród tych naszych sorted 0 i sorted 1???
            retCountry = distances.values().toArray()[0].toString();
        } else {
            //zwracamy najcześciej występujący kraj
            retCountry = sortedCountries.keySet().toArray()[0].toString();
        }

//        if(sortedCountries.values().toArray().length>1 && sortedCountries.values().toArray()[0] == sortedCountries.values().toArray()[1]){
//            //zwracamy kraj najbliżej
//            //TODO: powinniśmy zwrócić najbliżej sposród tych naszych sorted 0 i sorted 1???
//            retCountry = distances.values().toArray()[0].toString();
//        }
//        else {
//            //zwracamy najcześciej występujący kraj
//            retCountry = sortedCountries.keySet().toArray()[0].toString();
//        }

        /*
        List<String> mostCountries = new ArrayList<>();
        int topOccurence = 0;
        distances.forEach((distance, country) -> {
            countries.putIfAbsent(country, 1);
            countries.replace(country, countries.get(country) + 1);

            if(!mostCountries.contains(country) && topOccurence) {
                mostCountries.add(country);
                topOccurence =
            }
        });
        */


        return retCountry;
    }

    public String getPredictedArticleCountry(Article article) {

        Map<Float, String> distances = calculateDistance(article);

        distances = getKNeighbours(distances);

        return getCountry(distances);
    }
}
