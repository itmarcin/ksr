package ksr.logic;

import java.text.DecimalFormat;
import java.util.Map;

public record ExportDataModel(String metric,
                              int k,
                              float percentage,
                              float acc,
                              float genPrec,
                              float genRec,
                              float f1,
                              Map<String, Float> precs,
                              Map<String, Float> recs
                              ) {

    @Override
    public String toString() {

        DecimalFormat decimalFormat = new DecimalFormat("##.00");

        StringBuilder precs= new StringBuilder();
        StringBuilder recs= new StringBuilder();
        for(Map.Entry<String, Float> prec : precs().entrySet()){
            precs.append(";").append(decimalFormat.format(prec.getValue()*100));
        }
        for(Map.Entry<String, Float> rec : recs().entrySet()){
            recs.append(";").append(decimalFormat.format(rec.getValue()*100));
        }

        return
                metric +
                ";" + k +
                ";" + decimalFormat.format(percentage*100) +
                ";" + decimalFormat.format(acc*100) +
                ";" + decimalFormat.format(genPrec*100) +
                ";" + decimalFormat.format(genRec*100) +
                ";" + decimalFormat.format(f1*100) +
                ';'+
                precs + ";" +
                recs;
    }
}
