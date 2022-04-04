package ksr.userinterface;

import ksr.logic.Logic;

import java.util.Scanner;

public class CMDInterface {
    //TODO Zamienić cyfry 1,2 przy wyborze na jakieś literki
    //TODO Dodać czyszczenie ekranu w każdym nowym menu (cls)

    private static final String PROMPT = ">:";
    private static final String EUCLIDES = "euclides";
    private static final String CHEBYSHEV = "chebyshev";
    private static final String TAXICAB = "taxicab";

    private Logic logic;
    int valueK;
    int percentage;
    String metric;

    public CMDInterface(Logic logic) {
        this.logic = logic;
    }

    private int getUserIntInput() {
        Scanner scan = new Scanner(System.in);
        System.out.print(PROMPT);
        if (scan.hasNextInt()) {
            return scan.nextInt();
        } else {
            System.out.println("Niedozolony znak, spróbuj jeszcze raz.");
            scan.close();
            getUserIntInput();
        }
        scan.close();
        return 0;
    }

    private void initializeClassification(){
        if(logic.runner(valueK, percentage, metric) == 0){
            System.out.println("SUKCES!!!");
            startInterface();
        }
    }

    private void displayPercentageChoiceMenu() {
        while (true) {
            System.out.println("Wybierz % zbioru testowego z zakresu <1,99>");
            System.out.println("1. Wprowadź wartość parametru K");
            System.out.println("2. Cofnij");
            int userInput = getUserIntInput();
            switch (userInput) {
                case 1 -> {
                    percentage = getUserIntInput();
                    if (percentage < 1 || percentage > 99){
                        System.out.println("Liczba nie mieści się w prawidłowym zakresie!");
                        displayKChoiceMenu();
                    }
                    initializeClassification();
                }
                case 2 -> {
                    return;
                }
            }
        }
    }

    private void displayKChoiceMenu() {
        while (true) {
            System.out.println("Algorytm K-NN.");
            System.out.println("1. Wprowadź wartość parametru K");
            System.out.println("2. Cofnij");
            int userInput = getUserIntInput();
            switch (userInput) {
                case 1 -> {
                    valueK = getUserIntInput();
                    displayPercentageChoiceMenu();
                }
                case 2 -> {
                    return;
                }
            }
        }
    }

    private void displayMetricsMenu() {
        while (true) {
            System.out.println("Wybierz metrykę:");
            System.out.println("1. Euklidesowa");
            System.out.println("2. Czebyszewa");
            System.out.println("3. Uliczna");
            System.out.println("4. Cofnij");
            int userInput = getUserIntInput();
            switch (userInput) {
                case 1 -> {
                    metric = EUCLIDES;
                    displayKChoiceMenu();
                }
                case 2 -> {
                    metric = CHEBYSHEV;
                    displayKChoiceMenu();
                }
                case 3 -> {
                    metric = TAXICAB;
                    displayKChoiceMenu();
                }
                case 4 -> {
                    return;
                }
            }
        }
    }

    public void startInterface() {
        while (true) {
            System.out.println("Witaj, co chcesz zrobić.");
            System.out.println("1. Klasyfikuj dokumenty");
            System.out.println("2. Wyjdź");
            int userInput = getUserIntInput();
            switch (userInput) {
                case 1 -> displayMetricsMenu();
                case 2 -> {
                    System.exit(0);
                }
            }
        }
    }
}
