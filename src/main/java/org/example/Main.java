package org.example;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String option = chooseOptionGame();
        String difficultyLevel = chooseDifficultyLevel();

        switch (option) {
            case "1":
                playerGuess(difficultyLevel);
                break;

            case "2":
                computerGuess(difficultyLevel);
                break;

            case "3":
                mixedGame(difficultyLevel);
                break;
        }
    }

    public static String chooseOptionGame() {
        String option = "0";
        Scanner scanner = new Scanner(System.in);

        while (!option.equals("1") && !option.equals("2") && !option.equals("3")) {
            System.out.println("""
                    ON THE BEGINNING YOU HAVE TO CHOOSE OPTION OF GAME.\s
                    1. YOU GUESS NUMBER DRAWN BY THE COMPUTER.\s
                    2. THE COMPUTER GUESS NUMBER SELECTED BY YOU.\s
                    3. THE NUMBER WILL BE DRAWN AND THEN YOU AND THE COMPUTER TAKE TURNS TRYING TO GUESS IT.\s
                    ENTER NUMBER OF OPTION:""");

            option = scanner.nextLine();
        }
        return option;
    }

    public static String chooseDifficultyLevel() {
        String option = "0";
        Scanner scanner = new Scanner(System.in);

        while (!option.equals("1") && !option.equals("2") && !option.equals("3")) {
            System.out.println("NOW YOU HAVE TO CHOOSE DIFFICULTY LEVEL. \n" +
                    "1. EASY - RANGE OF NUMBERS 0 TO 100 \n" +
                    "2. NORMAL - RANGE OF NUMBERS 0 TO 1000 \n" +
                    "3. HARD - RANGE OF NUMBERS 0 TO 10000 \n" +
                    "ENTER NUMBER OF LEVEL:");

            option = scanner.nextLine();
        }

        return option;
    }

    public static void playerGuess(String difficultyLevel) {
        PlayerGuess.getInformationAboutGame();

        String username = PlayerGuess.enterYourUsername();

        PlayerGuess playerGuess = new PlayerGuess(username);

        PlayerGuess[] scores = PlayerGuess.readScores();

        System.out.println(Arrays.toString(scores));

        playerGuess.getTheBestPlayerScore(scores);

        boolean playerPlayedBefore = playerGuess.didPlayerPlayedBefore();

        int randomNumberToGuess;

        if (difficultyLevel.equals("1")) {
            randomNumberToGuess = PlayerGuess.generateRandomNumber(0, 100);
        } else if (difficultyLevel.equals("2")) {
            randomNumberToGuess = PlayerGuess.generateRandomNumber(0, 1000);
        } else {
            randomNumberToGuess = PlayerGuess.generateRandomNumber(0, 10000);
        }

        playerGuess.play(randomNumberToGuess);

        scores = playerGuess.updateScores(scores, playerPlayedBefore);

        playerGuess.saveScores(scores);
    }

    public static void computerGuess(String difficultyLevel) {
        ComputerGuess.getInformationAboutGame();

        String username = ComputerGuess.enterYourUsername();

        ComputerGuess computerGuess = new ComputerGuess(username);

        ComputerGuess[] scores = ComputerGuess.readScores();

        System.out.println(Arrays.toString(scores));

        computerGuess.getTheBestComputerScore(scores);

        boolean computerPlayedBefore = computerGuess.didComputerPlaysBeforeAgainstPlayer();

        int selectedNumber;

        if (difficultyLevel.equals("1")) {
            selectedNumber = ComputerGuess.selectYourNumber(0, 100);

            computerGuess.play(selectedNumber, 0, 100);
        } else if (difficultyLevel.equals("2")) {
            selectedNumber = ComputerGuess.selectYourNumber(0, 1000);

            computerGuess.play(selectedNumber, 0, 1000);
        } else {
            selectedNumber = ComputerGuess.selectYourNumber(0, 10000);

            computerGuess.play(selectedNumber, 0, 10000);
        }

        scores = computerGuess.updateScores(scores, computerPlayedBefore);

        computerGuess.saveScores(scores);
    }

    public static void mixedGame(String difficultyLevel) {
        MixedGame.getInformationAboutGame();

        if (difficultyLevel.equals("1")) {
            int numberToGuess = MixedGame.generateRandomNumber(0, 100);
            String whoStarts = MixedGame.drawWhoStarts();
            MixedGame.play(numberToGuess, 0, 100, whoStarts);

        } else if (difficultyLevel.equals("2")) {
            int numberToGuess = MixedGame.generateRandomNumber(0, 1000);
            String whoStarts = MixedGame.drawWhoStarts();
            MixedGame.play(numberToGuess, 0, 1000, whoStarts);

        } else {
            int numberToGuess = MixedGame.generateRandomNumber(0, 10000);
            String whoStarts = MixedGame.drawWhoStarts();
            MixedGame.play(numberToGuess, 0, 10000, whoStarts);
        }
    }
}