// **********************************************************************************
// Title: Major Project 3 - Game Library
// Author: Robert Thompson
// Course Section: CMIS201-ONL1 (Seidel) Spring 2023
// File: Loader.java
// Description: Used to load and save gameList onto a text document
// **********************************************************************************

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Loader {

    static String fileCheck = "GameLibrary file v1";

    //allows for loading the game list if it has been created before
    public static VideoGames[] loadGameList(String fileName) throws java.io.IOException {

        VideoGames[] game = new VideoGames[0];
        java.io.File file = new java.io.File(fileName + ".txt");

        if(file.exists()) {

            Scanner input = new Scanner(file);
            String check = input.nextLine();

            if(check.equals(fileCheck)) {
                while (input.hasNext()) {

                    VideoGames[] temp = new VideoGames[game.length + 1];
                    for (int i = 0; i < game.length; i++) {
                        temp[i] = new VideoGames(game[i].getName(), game[i].getType(), game[i].getVgType(), game[i].getIfPlayers(),
                                game[i].getMinPlayers(), game[i].getMaxPlayers(), game[i].getIfDescription(), game[i].getDescription());
                    }

                    temp[game.length] = new VideoGames(input.nextLine(), input.nextLine(), input.nextLine(),
                            "true".equalsIgnoreCase(input.nextLine()), Integer.parseInt(input.nextLine()), Integer.parseInt(input.nextLine()),
                            "true".equalsIgnoreCase(input.nextLine()), input.nextLine());

                    game = new VideoGames[temp.length];

                    for (int i = 0; i < game.length; i++) {
                        game[i] = new VideoGames(temp[i].getName(), temp[i].getType(), temp[i].getVgType(), temp[i].getIfPlayers(),
                                temp[i].getMinPlayers(), temp[i].getMaxPlayers(), temp[i].getIfDescription(), temp[i].getDescription());
                    }
                }
                JOptionPane.showMessageDialog(null, "Loaded " + fileName + ".txt successfully ", "Load Successful", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"Selected " + fileName + ".txt is not a valid file for this program ", "Load Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null,"No " + fileName + ".txt file detected ", "Load Error", JOptionPane.ERROR_MESSAGE);
        }

        return game;
    }

    //allows for saving the game list
    public static void saveGameList(VideoGames[] game, String fileName) {
        boolean failCheck = false;
        try {
            File outfile = new File(fileName + ".txt");
            java.io.PrintWriter out = new java.io.PrintWriter(outfile);
            out.println(fileCheck);
            for(VideoGames gl:game) {
                out.println(gl.getName()+"\n"+gl.getType()+"\n"+gl.getVgType()+"\n"
                        +gl.getIfPlayers()+"\n"+gl.getMinPlayers()+"\n"+gl.getMaxPlayers()+"\n"
                        +gl.getIfDescription()+"\n"+gl.getDescription());
            }
            out.close();
        } catch(FileNotFoundException e) {
            failCheck = true;
            System.out.println(e.getMessage());
        }
        if(failCheck) {
            JOptionPane.showMessageDialog(null, "A error has occurred when saving ", "Save Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Successfully Saved game list onto " + fileName + ".txt ", "Successful Save", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
