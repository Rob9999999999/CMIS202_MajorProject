// **********************************************************************************
// Title: Major Project 3 - Game Library
// Author: Robert Thompson
// Course Section: CMIS201-ONL1 (Seidel) Spring 2023
// File: Help.java
// Description: Contains info for all commands and an about page should the user need it
// **********************************************************************************

import javax.swing.JOptionPane;

public class Help {

    //String array containing each command in each string for ease of use
    public static final String[] mainMenu = {"Commands", "About", "Edit List", "View Data", "Load Data", "Options", "End Program"};
    public static final String[] editList = {"Commands", "Add Game", "Edit Game", "Remove Game", "Go Back"};
    public static final String[] viewData = {"Commands", "Search by Type", "Search by Players", "Random Game", "View All", "Go Back"};
    public static final String[] loadDate = {"Commands", "Load New File", "Change Current File", "Close Files", "Go Back"};

    public static void mainMenuCommands() {
        String commandsText = "About - Learn a little bit about the program \n"+
                "Edit List - anything about editing the currently selected list\n"+
                "View Data - anything about viewing the data of the current file being edited\n"+
                "Load Data - editing which files are loaded and which one you are editing\n"+
                "Options - Allows for setting viewing collection alphabetically by its name or by type\n"+
                "End Program - It saves all open files and then closes the program\n";
        JOptionPane.showMessageDialog(null,"Main Menu Command List:\n"+commandsText,"Main Menu", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void editListCommands() {
        String commandsText = "Add Game - You can add a game to the current selected file \n"+
                "Edit Game - You can edit a game data that already exist within the current list\n"+
                "Remove Game - You can remove a game so it is no longer apart from the current list\n"+
                "Go Back - Go back to the main menu options\n";
        JOptionPane.showMessageDialog(null,"Edit File Command List:\n"+commandsText,"Edit File", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void viewDataCommands() {
        String commandsText = "Search by Type - Allows searching the games base on the type the game is \n"+
                "Search by Players - Allows searching the games based on how many players \n"+
                "Random Game - Pulls up random games from the game list \n"+
                "View All - displays all games on the current list \n"+
                "Go Back - Go back to the main menu options\n";
        JOptionPane.showMessageDialog(null,"View Data Command List:\n"+commandsText,"View Data", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void loadDataCommands() {
        String commandsText = "Load New File - Adds new files that has game list on them so they can be accessed and edited \n"+
                "Change Current File - Sets which file data your currently viewing and editing \n"+
                "Close Files - Allows you to save and remove files you no longer need to access \n"+
                "Go Back - Go back to the main menu options\n";
        JOptionPane.showMessageDialog(null,"Load Data Command List:\n"+commandsText,"Load Data", JOptionPane.INFORMATION_MESSAGE);
    }

    //contains the information about the program
    public static void about() {
        String aboutText = "The program is design to allow for storing and showing game libraries in a neat way\n"+
                "It also has functions to search for specific games through types or number of players \n"+
                "Its super easy to add and edit games to the list \n"+
                "There is even functions to load and save the game list under different names ";
        JOptionPane.showMessageDialog(null,"Help-About:\n"+aboutText,"Help",JOptionPane.INFORMATION_MESSAGE);
    }

}
