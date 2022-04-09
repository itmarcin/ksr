package ksr.logic;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Exporter {

    public void export(String fileName){

        try {
            PrintWriter writer = new PrintWriter(fileName,"UTF-8");




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
