package org.example;

import java.util.Random;
import java.util.Scanner;

public class MixedGame {

    public static void getInformationAboutGame() {
        System.out.println("WELCOME IN THE GAME! \n" +
                "IN THIS TYPE OF GAME, A NUMBER IS DRAWN AND THEN YOU AND THE COMPUTER TAKE TURNS TRYING TO GUESS IT." +
                " THE FIRST PLAYER WILL BE DRAWN.");
    }

    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        System.out.println("THE NUMBER ALREADY HAS DRAWN.");
        return random.nextInt((max - min) + 1) + min;
    }

    public static String drawWhoStarts() {
        Random random = new Random();
        int number = random.nextInt(2);

        if (number == 1) {
            System.out.println("YOU STARTS!");
            return "player";
        } else {
            System.out.println("THE COMPUTER STARTS");
            return "computer";
        }
    }

    public static int getMiddleNumber(int min, int max) {
        return min + (max - min) / 2;
    }

    public static int enterNumber() {
        Scanner scanner = new Scanner(System.in);
        int userNumber = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.println("ENTER YOUR NUMBER: ");

            try {
                userNumber = Integer.parseInt(scanner.nextLine());
                isValidInput = true;
            } catch (NumberFormatException e) {
                System.out.println("THIS IS NOT A INTEGER. ENTER NUMBER AGAIN.");
            }
        }
        return userNumber;
    }


    public static void play(int numberToGuess, int min, int max, String whoStarts) {
        if (whoStarts.equalsIgnoreCase("player")) {

            while (true) {
                System.out.println("YOUR TURN:");

                int playerNumber = enterNumber();

                if (playerNumber == numberToGuess) {
                    System.out.println("CONGRATULATIONS! YOU HAVE GUESSED THE NUMBER AND WON!");
                    break;

                } else if (playerNumber > numberToGuess) {
                    System.out.println("YOUR ENTERED NUMBER IS TOO HIGH.");

                } else {
                    System.out.println("YOUR ENTERED NUMBER IS TO LOW.");
                }

                System.out.println("COMPUTER'S TURN:");

                int computerNumber = getMiddleNumber(min, max);

                if (computerNumber == numberToGuess){
                    System.out.println("THE COMPUTER HAS GUESSED THE NUMBER AND WON!");
                    break;

                } else if (computerNumber < numberToGuess) {
                    System.out.println("SELECTED NUMBER BY COMPUTER WAS TOO LOW.");
                    min = computerNumber;
                } else {
                    System.out.println("SELECTED NUMBER BY COMPUTER WAS TOO HIGH.");
                    max = computerNumber;
                }
            }

        } else {
            while (true){
                System.out.println("COMPUTER'S TURN:");

                int computerNumber = getMiddleNumber(min, max);

                if (computerNumber == numberToGuess){
                    System.out.println("THE COMPUTER HAS GUESSED THE NUMBER AND WON!");
                    break;

                } else if (computerNumber < numberToGuess) {
                    System.out.println("SELECTED NUMBER BY COMPUTER WAS TOO LOW.");
                    min = computerNumber;
                } else {
                    System.out.println("SELECTED NUMBER BY COMPUTER WAS TOO HIGH.");
                    max = computerNumber;
                }

                System.out.println("YOUR TURN:");

                int playerNumber = enterNumber();

                if (playerNumber == numberToGuess) {
                    System.out.println("CONGRATULATIONS! YOU HAVE GUESSED THE NUMBER AND WON!");
                    break;

                } else if (playerNumber > numberToGuess) {
                    System.out.println("YOUR ENTERED NUMBER IS TOO HIGH");

                } else {
                    System.out.println("YOUR ENTERED NUMBER IS TO LOW");
                }
            }
        }
    }
}
