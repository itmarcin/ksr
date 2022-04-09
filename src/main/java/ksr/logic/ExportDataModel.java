package ksr.logic;

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
        return
                metric +
                ";" + k +
                ";" + percentage +
                ";" + acc +
                ";" + genPrec +
                ";" + genRec +
                ";" + f1 +
                ';';
    }
}
