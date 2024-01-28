package org.example;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class PlayerGuess implements Serializable{
    private String username;

    private int bestScore = -1;

    public PlayerGuess(String username) {
        this.username = username;
    }

    public PlayerGuess(String username, int bestScore) {
        this.username = username;
        this.bestScore = bestScore;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBestScore() {
        return this.bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    @Override
    public String toString() {
        return "PlayerGuess{" +
                "username='" + username + '\'' +
                ", bestScore=" + bestScore +
                '}';
    }

    public static void getInformationAboutGame(){
        System.out.println("WELCOME IN THE GAME! IN THIS TYPE OF GAME YOU WILL TRY GUESS THE NUMBER DRAWN BY COMPUTER");
    }

    public static String enterYourUsername(){
        Scanner scanner = new Scanner(System.in);
        String username = "";

        while (username.trim().isEmpty()){
            System.out.println("ENTER YOUR USERNAME");
            username = scanner.nextLine();

            if (username.isEmpty()){
                System.out.println("YOU MUST WRITE YOUR USERNAME!");
            }
        }

        return username;
    }

    public static PlayerGuess[] readScores() {
        PlayerGuess[] bestScoresFromFileArray = null;

        try (FileInputStream fileInputStream = new FileInputStream("./player_guess.ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            bestScoresFromFileArray = (PlayerGuess[]) objectInputStream.readObject();

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (EOFException e) {
            System.out.println("The file with scores is empty.");
            bestScoresFromFileArray = new PlayerGuess[0];
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Reading file error: " + e.getMessage());
        }

        return (bestScoresFromFileArray != null) ? bestScoresFromFileArray : new PlayerGuess[0];
    }

    public void getTheBestPlayerScore(PlayerGuess[] scores){
        for (PlayerGuess score : scores) {
            if (score.getUsername().equalsIgnoreCase(this.username)) {
                setBestScore(score.getBestScore());
                break;
            }
        }
        if (getBestScore() == -1){
            System.out.println("THIS IS YOUR FIRST GAME. GOOD LUCK!");
        } else {
            System.out.println("YOUR THE BEST SCORE IS " + getBestScore() + " NUMBER OF TRIES. " +
                    "TRY BEAT IT. GOOD LUCK!");
        }
    }

    public static int generateRandomNumber(int min, int max){
        Random random = new Random();
        System.out.println("THE COMPUTER ALREADY HAS DRAWN A NUMBER FROM 0 TO 100. YOU CAN START GUESSING.");
        return random.nextInt((max - min) + 1) + min;
    }

    public int enterNumber(){
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

    public void play(int randomNumberToGuess){
        boolean isGuessed = false;
        int score = 1;

        while (!isGuessed){
            int userNumber = enterNumber();

            if (userNumber == randomNumberToGuess){
                isGuessed = true;
                System.out.println("SUCCESS! YOU HAVE GUESSED THE NUMBER " + score + " TIMES! " +
                        "YOUR SCORE IS: " + score + ".");

                if (score < getBestScore() || getBestScore() == -1) {
                    System.out.println("CONGRATULATIONS! YOU HAVE BEATEN YOUR PERSONAL RECORD!");
                    setBestScore(score);
                }

            } else if (userNumber > randomNumberToGuess) {
                System.out.println("YOUR ENTERED NUMBER IS TOO HIGH.");
                score++;

            } else {
                System.out.println("YOUR ENTERED NUMBER IS TOO LOW.");
                score++;
            }
        }

    }

    public PlayerGuess[] updateScores(PlayerGuess[] scores, boolean playerPlayedBefore){
        if (scores.length == 0 || !playerPlayedBefore){
            scores = Arrays.copyOf(scores, scores.length + 1);
            scores[scores.length - 1] = new PlayerGuess(getUsername(), getBestScore());

        } else {
            for (PlayerGuess score : scores) {
                if (score.getUsername().equalsIgnoreCase(getUsername())) {
                    score.setBestScore(getBestScore());
                    break;
                }
            }
        }

        return scores;
    }

    public void saveScores(PlayerGuess[] scores){
        try (FileOutputStream fileOutputStream = new FileOutputStream("./player_guess.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(scores);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("saving file error: " + e.getMessage());
        }
    }

    public boolean didPlayerPlayedBefore(){
        return getBestScore() != -1;
    }
}