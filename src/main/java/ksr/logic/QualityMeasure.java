package ksr.logic;

import java.util.ArrayList;
import java.util.List;

public class QualityMeasure {

    List<String> predictedCountries;
    List<String> realCountries;

    List<String> countryNames;

    float precision;
    float recall;

    public QualityMeasure(List<String> predictedCountries, List<String> realCountries){
        this.predictedCountries = predictedCountries;
        this.realCountries = realCountries;
        this.countryNames = new ArrayList<>();

        this.countryNames.add("west-germany");
        this.countryNames.add("usa");
        this.countryNames.add("france");
        this.countryNames.add("uk");
        this.countryNames.add("canada");
        this.countryNames.add("japan");

        precision = 0;
        recall = 0;
    }

    public float calculateAccuracy(){
        int ret=0;
        for(int i=0; i< predictedCountries.size();i++)
            if(predictedCountries.get(i).equals(realCountries.get(i)))
                ret++;

        return (float)ret / predictedCountries.size();
    }

    public float calculateGeneralPrecision(){
        float ret =0;

        List<Float> precisions = calculatePrecision(countryNames);
        for(float prec : precisions)
            ret+=prec;

        this.precision =ret/precisions.size();
        return this.precision;
    }

    public List<Float> calculatePrecision(List<String> countries){
        List<Float> precision = new ArrayList<>();

        for (String country : countries) {
            int tp = 0;
            int fp = 0;
            for (int i = 0; i < predictedCountries.size(); i++) {
                if (predictedCountries.get(i).equals(country))
                    if (predictedCountries.get(i).equals(realCountries.get(i)))
                        tp++;
                    else
                        fp++;
            }
            if(tp+fp != 0)
                precision.add( (tp / (float)(tp + fp) ));
            else
                precision.add(0F);
        }
        return precision;
    }

    public float calculateGeneralRecall(){
        float ret=0;

        List<Float> recalls = calculateRecall(countryNames);
        for(float rec: recalls)
            ret+=rec;

        this.recall = ret/recalls.size();
        return this.recall;
    }

    public List<Float> calculateRecall(List<String> countries){

        List<Float> recall = new ArrayList<>();
        for(String country : countries){
            int tp = 0;
            int fn = 0;
            for(int i=0; i < predictedCountries.size(); i++){
                if(realCountries.get(i).equals(country))
                    if(predictedCountries.get(i).equals(realCountries.get(i)))
                        tp++;
                    else
                        fn++;
            }
            if(tp+fn != 0)
                recall.add( (tp / (float) (tp + fn) ));
            else
                recall.add(0F);
        }
        return recall;
    }

    public float calculateF1(){
        float prec, rec;

        if(this.recall!=0)
            rec = this.recall;
        else
            rec = calculateGeneralRecall();
        if(this.precision!=0)
            prec = this.precision;
        else
            prec = calculateGeneralPrecision();

        return 2 * ( (prec * rec) / (prec+rec) );
    }

}
