package ksr;

import ksr.algorithms.KNN;
import ksr.algorithms.metrics.EuclidesMetric;
import ksr.algorithms.metrics.Metric;
import ksr.deserialization.Article;
import ksr.deserialization.Deserializer;
import ksr.deserialization.SplitData;
import ksr.extraction.Extractor;
import ksr.extraction.KeyWords;
import ksr.logic.Logic;
import ksr.userinterface.CMDInterface;

import java.io.IOException;
import java.util.List;


public class Main {


    public static void main(String[] args) {
        CMDInterface cmdInterface = new CMDInterface(new Logic());
        cmdInterface.startInterface();
    }
}