package ksr.deserialization;

import ksr.extraction.Properties;

import java.util.List;

public class Article {
    List<String> text;
    String title;
    String country;

    Properties properties;

    @Override
    public String toString() {
        return "Article{" +
                "text=" + text +
                ", title='" + title + '\'' +
                ", country='" + country + '\'' +
                ", properties=" + properties +
                '}';
    }

    public Article(List<String> text, String title, String country) {
        this.text = text;
        this.title = title;
        this.country = country;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public List<String> getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getCountry() {
        return country;
    }
}
