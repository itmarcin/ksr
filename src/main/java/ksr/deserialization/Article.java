package ksr.deserialization;

import ksr.extraction.Properties;

public class Article {
    String text;
    String title;
    String places;
    Properties properties;

    @Override
    public String toString() {
        return "Article{" +
                "text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", places='" + places + '\'' +
                '}';
    }

    public Article(String text, String title, String places) {
        this.text = text;
        this.title = title;
        this.places = places;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getPlaces() {
        return places;
    }

    public Properties getProperties() {
        return properties;
    }
}
