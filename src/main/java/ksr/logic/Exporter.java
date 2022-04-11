package ksr.logic;

import java.io.*;
import java.util.Map;


public class Exporter {

    private final String fileName;

    public Exporter(String fileName){
        this.fileName = fileName;
    }

    public void export(ExportDataModel dataModel) {

        try {

            boolean addHeader = false;

            File file = new File(fileName);
            if(!file.exists()){
                addHeader = true;
            }

            FileWriter writer = new FileWriter(this.fileName, true);


            if(addHeader){
                StringBuilder countriesStr = new StringBuilder();
                for (Map.Entry<String, Float> prec : dataModel.precs().entrySet()) {
                    countriesStr.append(prec.getKey()+";");
                }
                writer.write("Metryka;liczba k;podzial zbioru;srednie accuracy;srednie precision;srednie recall;f1;PRECISIONS:;"+countriesStr+"RECALLS:;"+countriesStr+"\n");
            }
            String line = dataModel.toString()+"\n";
            writer.write(line.replace('.',','));

            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
