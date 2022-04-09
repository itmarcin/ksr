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
                writer.write("Metryka;liczba k;podzial zbioru;srednie accuracy;srednie precision;srednie recall;f1;PRECISIONS:;west-germany;usa;france;uk;canada;japan;RECALLS:;west-germany;usa;france;uk;canada;japan\n");
            }

            StringBuilder precs= new StringBuilder();
            StringBuilder recs= new StringBuilder();
            for(Map.Entry<String, Float> prec : dataModel.precs().entrySet()){
                precs.append(";").append(prec.getValue());
            }
            for(Map.Entry<String, Float> rec : dataModel.recs().entrySet()){
                recs.append(";").append(rec.getValue());
            }
            writer.write(dataModel.toString()+precs+";"+recs+"\n");

            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
