/*
title: COSC 1200
author: Luke Saldanha
Date 2024-01-30
name: Assingment 1
 */


import java.util.Random;

public class Tools {



    public static Random random = new Random();

    public static String decideFirstPlayer(String playerOne, String playerTwo) {
        int randomNum = random.nextInt(2);
        return (randomNum == 0) ? playerOne : playerTwo;
    }

    public static void startDartsGame(String playerOne, String playerTwo) {
        String firstPlayer = decideFirstPlayer(playerOne, playerTwo);

        System.out.println("Welcome to the dart game " + playerOne + " and " + playerTwo);
        System.out.println("The first player is: " + firstPlayer);


    }

}



