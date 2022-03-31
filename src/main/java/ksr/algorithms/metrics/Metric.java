package ksr.algorithms.metrics;

import ksr.extraction.Properties;

public interface Metric {
    float calculateDistance(Properties prop1, Properties prop2);
}
