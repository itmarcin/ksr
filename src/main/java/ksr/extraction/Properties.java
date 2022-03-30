package ksr.extraction;

public class Properties {


    //1. Liczba słów w przedziale <1, 5>
    public int wordsRangeOneFive;
    //2. Liczb słów w przedziale <6, infinity>
    public int wordsRangeSixInf;
    //3. Ilość wszystkich słów
    public int wordsCount;
    //4. Ilość słów kluczowych
    public int keyWordsCount;
    //5. Ilość unikalnych słów
    public int uniqueWordsCount;
    //6. Ilość unikalnych słów kluczowych
    public int uniqueKeyWordsCount;
    //7. Długość najdłuższego słowa
    public int maxWordLength;
    //8. Średnia długość słowa
    public float avgWordLength;
    //9. Najczęściej występujące słowo
    public String topWordOccurence;
    //10. Najczęściej występujące słowo kluczowe
    public String topKeyWordOccurence;


    @Override
    public String toString() {
        return "Properties{" +
                "wordsRangeOneFive=" + wordsRangeOneFive +
                ", wordsRangeSixInf=" + wordsRangeSixInf +
                ", wordsCount=" + wordsCount +
                ", keyWordsCount=" + keyWordsCount +
                ", uniqueWordsCount=" + uniqueWordsCount +
                ", uniqueKeyWordsCount=" + uniqueKeyWordsCount +
                ", maxWordLength=" + maxWordLength +
                ", avgWordLength=" + avgWordLength +
                ", topWordOccurence='" + topWordOccurence + '\'' +
                ", topKeyWordOccurence='" + topKeyWordOccurence + '\'' +
                '}';
    }
}
