package ksr.userinterface;

import ksr.logic.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    private char getUserCharInput(List<Character> validOptions) {
        Scanner scan = new Scanner(System.in);
        System.out.print(PROMPT);
        String input = scan.next();
        char[] charInput = input.toCharArray();
        if (charInput.length != 0 && Character.isLetter(charInput[0])){
            if (validOptions.contains(charInput[0])){
                scan.nextLine();
                scan.close();
                return charInput[0];
            } else {
                System.out.println("Niedozolony znak, sprobuj jeszcze raz.");
                scan.nextLine();
                scan.close();
                getUserCharInput(validOptions);
            }
        }
        else {
            System.out.println("Niedozolony znak, sprobuj jeszcze raz.");
            scan.nextLine();
            scan.close();
            getUserCharInput(validOptions);
        }
        scan.close();
        return 0;
    }

    private int getUserIntInput() {
        Scanner scan = new Scanner(System.in);
        System.out.print(PROMPT);
        if (scan.hasNextInt()) {
            return scan.nextInt();
        } else {
            System.out.println("Niedozolony znak, sprobuj jeszcze raz.");
            scan.close();
            getUserIntInput();
        }
        scan.close();
        return 0;
    }

    private void initializeClassification() {
        clearConsole();
        if (logic.runner(valueK, percentage, metric) == 0) {
            System.out.println("SUKCES!!!");
            startInterface();
        }
    }

    private void displayPercentageChoiceMenu() {
        while (true) {
            clearConsole();
            System.out.println("Wybierz % zbioru testowego z zakresu <1,99>");
            System.out.println("1. Wprowadz wartosc parametru K");
            System.out.println("2. Cofnij");
            int userInput = getUserIntInput();
            switch (userInput) {
                case 1 -> {
                    percentage = getUserIntInput();
                    if (percentage < 1 || percentage > 99) {
                        System.out.println("Liczba nie miesci sie w prawidlowym zakresie!");
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
            clearConsole();
            System.out.println("Algorytm K-NN.");
            System.out.println("1. Wprowadz wartosc parametru K");
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
            System.out.println("Wybierz metryke:");
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
            clearConsole();
            System.out.println("Witaj, co chcesz zrobic.");
            System.out.println("1. Klasyfikuj dokumenty");
            System.out.println("2. Wyjdz");
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
