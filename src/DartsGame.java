/*
title: COSC 1200
author: Luke Saldanha
Date 2024-01-30
name: Assingment 1
 */

// importing


import java.util.Scanner;

public class DartsGame {
    // Member variables
    public static final int MAX_SCORE = 501;
    public int currentScorePlayer1;
    public int currentScorePlayer2;
    public int roundsToWin;
    public String playerOne;
    public String playerTwo;
    public String currentPlayer;
    public int gamesWonPlayer1;
    public int gamesWonPlayer2;

    public DartsGame() {
        currentScorePlayer1 = MAX_SCORE;
        currentScorePlayer2 = MAX_SCORE;
        roundsToWin = 3;
        currentPlayer = "Player1";
        gamesWonPlayer1 = 0;
        gamesWonPlayer2 = 0;
        startGame();
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        String proceed;// Start the Darts game

        do {
            displayMainMenu();

            int menuChoice;

            do {
                // Get user choice
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); // Consume the invalid input
                }

                menuChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (menuChoice < 0 || menuChoice > 2) {
                    System.out.println("Invalid choice. Please enter a valid number.");
                }
            } while (menuChoice < 0 || menuChoice > 2);

            switch (menuChoice) {
                case 1:
                    // Option 1: Start the darts game
                    System.out.println("Starting Darts Game...");
                    playerOne = getPlayerName("Player 1");
                    playerTwo = getPlayerName("Player 2");
                    startDartsGame();
                    break;
                case 2:
                    // Option 2: Display rules
                    displayRules();
                    break;
                case 0:
                    // Option 0: Exit the program
                    System.out.println("Exiting the Darts Game.");
                    return;
            }

            System.out.print("Do you want to return to the main menu? (yes/no): ");
            proceed = scanner.nextLine();
        } while (proceed.equalsIgnoreCase("yes"));

        scanner.close();
    }

    public void startDartsGame() {
        String firstPlayer = Tools.decideFirstPlayer(playerOne, playerTwo);
        System.out.println("Welcome to the Darts Game!");
        System.out.println(playerOne + " and " + playerTwo + ", you are playing against each other.");
        System.out.println(firstPlayer + " will start the game.\n");

        for (int game = 1; game <= roundsToWin * 2; game++) {
            System.out.println("Game " + game);

            currentPlayer = (game % 2 == 1) ? playerOne : playerTwo;
            resetScores();

            for (int round = 1; round <= 3; round++) {
                System.out.println("Round " + round);

                for (int turn = 1; turn <= 3; turn++) {
                    System.out.println(currentPlayer + ", enter your score for turn " + turn + ": ");
                    int score = getValidScoreInput();

                    if (isValidScore(score)) {
                        updateScore(score);
                    } else {
                        System.out.println("Invalid score. Please follow the rules.");
                        turn--; // Repeat the same turn
                    }
                }

                if (currentPlayer.equals(playerOne) && currentScorePlayer1 == 0) {
                    System.out.println(playerOne + " wins the round!");
                    break; // Game winner found
                } else if (currentPlayer.equals(playerTwo) && currentScorePlayer2 == 0) {
                    System.out.println(playerTwo + " wins the round!");
                    break; // Game winner found
                } else if (round == 3) {
                    System.out.println(currentPlayer + " did not finish the round successfully. Scores remain: " +
                            playerOne + " - " + currentScorePlayer1 + ", " + playerTwo + " - " + currentScorePlayer2);
                } else {
                    System.out.println("Scores after round " + round + ": " +
                            playerOne + " - " + currentScorePlayer1 + ", " + playerTwo + " - " + currentScorePlayer2);
                }

                // Switch player for the next round
                currentPlayer = (currentPlayer.equals(playerOne)) ? playerTwo : playerOne;
            }

            // Determine the game winner
            if (currentScorePlayer1 == 0) {
                gamesWonPlayer1++;
            } else if (currentScorePlayer2 == 0) {
                gamesWonPlayer2++;
            }

            System.out.println("Games won - " + playerOne + ": " + gamesWonPlayer1 +
                    ", " + playerTwo + ": " + gamesWonPlayer2);

            if (gamesWonPlayer1 == roundsToWin || gamesWonPlayer2 == roundsToWin) {
                // Match winner found
                String matchWinner = (gamesWonPlayer1 == roundsToWin) ? playerOne : playerTwo;
                System.out.println(matchWinner + " wins the match!");
                break;
            }

            // Switch starting player for the next game
            String temp = playerOne;
            playerOne = playerTwo;
            playerTwo = temp;
        }
    }

    public void resetScores() {
        currentScorePlayer1 = MAX_SCORE;
        currentScorePlayer2 = MAX_SCORE;
    }

    public String getPlayerName(String player) {
        System.out.println("Enter the name for " + player + ": ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public int getValidScoreInput() {
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Consume the invalid input
        }

        return scanner.nextInt();
    }

    public boolean isValidScore(int score) {
        return score >= 0 && ((currentPlayer.equals(playerOne) && currentScorePlayer1 - score >= 0) ||
                (currentPlayer.equals(playerTwo) && currentScorePlayer2 - score >= 0));
    }

    public void updateScore(int score) {
        if (currentPlayer.equals(playerOne)) {
            currentScorePlayer1 -= score;
        } else {
            currentScorePlayer2 -= score;
        }
        System.out.println(currentPlayer + " scored " + score + ". Scores: " + playerOne + " - " + currentScorePlayer1 +
                ", " + playerTwo + " - " + currentScorePlayer2);
    }

    public void displayMainMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Start Darts Game");
        System.out.println("2. Rules");
        System.out.println("0. Exit");
        System.out.println("=================");
        System.out.print("Enter your choice: ");
    }

    public void displayRules() {
        System.out.println("Darts Game Rules:");
        System.out.println("1. Each player takes three turns in each round.");
        System.out.println("2. The first player to reach a score of 0 wins the round.");
        System.out.println("3. The game consists of three rounds.");
        System.out.println("4. The player with the most round wins is the overall winner.");
        System.out.println("5. To finish a round, the final dart must land in a double.");
        System.out.println("6. If a player's three-dart thrown score would result in a score of 1 or a negative number, no score is received at all.");
        System.out.println("7. Maximum out with 3 darts is 170 (Triple 20, Triple 20, Double Bullseye).");
        System.out.println("8. Enjoy the game!");
        System.out.println();
    }

    public static void main(String[] args) {
        new DartsGame();
    }
    public void main() {
        new DartsGame();
        System.out.println("=== Main Menu ===");
        System.out.println("1. Start Darts Game");
        System.out.println("2. Rules");
        System.out.println("0. Exit");
        System.out.println("=================");
        System.out.println("Current Turn: " + currentPlayer);

        // Display the required score for the current turn
        if (currentPlayer.equals(playerOne)) {
            System.out.println("Required Score: " + calculateRequiredScore(currentScorePlayer1));
        } else {
            System.out.println("Required Score: " + calculateRequiredScore(currentScorePlayer2));
        }

        System.out.print("Enter your choice: ");
    }

    public int calculateRequiredScore(int currentScore) {

        return (currentScore <= 170) ? currentScore : 170;
    }


}
