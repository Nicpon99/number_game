package org.example;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class ComputerGuess implements Serializable{
    private String username;

    private int bestScore = -1;

    public ComputerGuess(String username) {
        this.username = username;
    }

    public ComputerGuess(String username, int bestScore) {
        this.username = username;
        this.bestScore = bestScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    @Override
    public String toString() {
        return "ComputerGuess{" +
                "username='" + username + '\'' +
                ", bestScore=" + bestScore +
                '}';
    }

    public static void getInformationAboutGame(){
        System.out.println("WELCOME IN THE GAME! IN THIS TYPE OF GAME COMPUTER WILL TRY GUESS YOUR SELECTED NUMBER.");
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

    public static ComputerGuess[] readScores(){
        ComputerGuess[] bestScoresFromFileArray = null;

        try (FileInputStream fileInputStream = new FileInputStream("./computer_guess.ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            bestScoresFromFileArray = (ComputerGuess[]) objectInputStream.readObject();

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (EOFException e) {
            System.out.println("The file with scores is empty.");
            bestScoresFromFileArray = new ComputerGuess[0];
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Reading file error: " + e.getMessage());
        }

        return (bestScoresFromFileArray != null) ? bestScoresFromFileArray : new ComputerGuess[0];
    }

    public void getTheBestComputerScore(ComputerGuess[] scores){
        for (ComputerGuess score : scores) {
            if (score.getUsername().equalsIgnoreCase(this.username)) {
                setBestScore(score.getBestScore());
                break;
            }
        }
        if (getBestScore() == -1){
            System.out.println("THIS IS THE FIRST COMPUTER'S GAME AGAINST YOU. GOOD LUCK!");
        } else {
            System.out.println("THE BEST COMPUTER'S SCORE AGAINST YOU IS " + getBestScore() + " NUMBER OF TRIES. " +
                    "THE COMPUTER WILL TRY BEAT IT. GOOD LUCK!");
        }
    }

    public static int selectYourNumber(int min, int max){
        Scanner scanner = new Scanner(System.in);
        int enteredNumber;

        do {
            System.out.print("ENTER A NUMBER IN THE RANGE FROM " + min + " TO " + max + ": ");
            while (!scanner.hasNextInt()) {
                System.out.println("THAT'S NOT A NUMBER!");
                scanner.next();
            }
            enteredNumber = scanner.nextInt();

            if (enteredNumber < min || enteredNumber > max) {
                System.out.println("NUMBER OUT OF RANGE! PLEASE ENTER AGAIN.");
            }
        } while (enteredNumber < min || enteredNumber > max);

        return enteredNumber;
    }

    public int getMiddleNumber(int min, int max){
        return min + (max - min) / 2;
    }

    public String writeHigherOrLower(){
        Scanner scanner = new Scanner(System.in);
        String answer = "a";

        while (!answer.equalsIgnoreCase("H") && !answer.equalsIgnoreCase("L")){
            answer = scanner.nextLine();
        }

        return answer;
    }

    public void play(int selectedNumber, int min, int max){
        boolean isGuessed = false;
        int score = 1;

        while (!isGuessed){
            int computerNumber = getMiddleNumber(min, max);

            if (computerNumber == selectedNumber){
                isGuessed = true;
                System.out.println("COMPUTER HAS GUESSED YOUR NUMBER " + score + " TIMES! "
                        + "COMPUTER'S SCORE IS " + score + ".");

                if (score < getBestScore() || getBestScore() == -1){
                    System.out.println("COMPUTER HAS BEATEN ITS RECORD AGAINST YOU!");
                    setBestScore(score);
                }
            } else {
                System.out.println("THE SELECTED NUMBER BY COMPUTER IS " + computerNumber + ". \n" +
                        "IF THE NUMBER IS HIGHER THAN YOURS WRITE \"H\". \n" +
                        "IF THE NUMBER IS LOWER THAN YOURS WRITE \"L\".");

                String answer = writeHigherOrLower();

                if (answer.equalsIgnoreCase("H")){
                    max = computerNumber;
                } else {
                    min = computerNumber;
                }
                score++;
            }
        }
    }

    public boolean didComputerPlaysBeforeAgainstPlayer(){
        return getBestScore() != -1;
    }

    public ComputerGuess[] updateScores(ComputerGuess[] scores, boolean computerPlayedBefore){
        if (scores.length == 0 || !computerPlayedBefore){
            scores = Arrays.copyOf(scores, scores.length + 1);
            scores[scores.length - 1] = new ComputerGuess(getUsername(), getBestScore());

        } else {
            for (ComputerGuess score : scores) {
                if (score.getUsername().equalsIgnoreCase(getUsername())) {
                    score.setBestScore(getBestScore());
                    break;
                }
            }
        }

        return scores;
    }

    public void saveScores(ComputerGuess[] scores){
        try (FileOutputStream fileOutputStream = new FileOutputStream("./computer_guess.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(scores);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Saving file error: " + e.getMessage());
        }
    }
}
