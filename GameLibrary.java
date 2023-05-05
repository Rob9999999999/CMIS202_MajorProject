// **********************************************************************************
// Title: Major Project 3 - Game Library
// Author: Robert Thompson
// Course Section: CMIS201-ONL1 (Seidel) Spring 2023
// File: GameLibrary.java
// Description: Used to run the entire program
// **********************************************************************************

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class GameLibrary {

    static String currentListName = "gameList";
    static LinkedList<String> files = new LinkedList<>();
    static Hashtable<String, VideoGames[]> filesData = new Hashtable<>();
    static String[] gameTypes = {"Deckbuilder", "Dungeon-Crawler", "Roll-and-Move", "Table-Top-RPG", "VideoGame", "Other"};;
    static String[] videoGameTypes = {"Real-Time-Strategy", "Role-Playing", "Shooter", "Platformer", "Puzzle", "PartyGame",
            "MOBA", "Simulation", "Horror", "Other"};

    //only set up for test display of game class currently
    public static void main(String[] args) throws IOException {

        String input = "";
        boolean inputB;
        VideoGames[] gameList;

        files.add(currentListName);
        filesData.put(currentListName, new VideoGames[0]);

        inputB = (JOptionPane.showConfirmDialog(null, "Would you like to set a file name to save and load from?\n"
                +"Saying no will automatically set the program to load and save onto default file gameList ",
                "Set File Name", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
        if(inputB) {
            setFileName();
        }

        filesData.replace(currentListName, Loader.loadGameList(currentListName));
        gameList = filesData.get(currentListName);

        boolean runProgram = true;
        do {
            int inputI = JOptionPane.showOptionDialog(null, "Welcome to Game Library \n"
                    +"Please select the commands option at any point to learn more about each option that you see \n",
                    "Main Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, Help.mainMenu, Help.mainMenu[0]);
            if(inputI == 0) {//commands
                Help.mainMenuCommands();
            }//commands
            else if(inputI == 1) {//about - learning about what the program does
                Help.about();
            }//about
            else if(inputI == 2) {//edit list - anything about editing the currently selected file
                boolean runEdit = true;
                do {
                    int editInput = JOptionPane.showOptionDialog(null, "Editing List Menu \n"
                            + "Currently editing " + currentListName + " list ",
                            "Edit List", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, Help.editList, Help.editList[0]);
                    if(editInput == 0) {//commands
                        Help.editListCommands();
                    }//commands
                    else if(editInput == 1) {//add game - You can add a game to the current selected file
                        boolean addGames = false;
                        do {
                            int location = 0;
                            boolean replace = false;
                            boolean cancel = false;
                            //Add new game name
                            String name;
                            boolean check;
                            do {
                                name = JOptionPane.showInputDialog(null, "What is the name of the game? ",
                                        "Add Game - Name", JOptionPane.QUESTION_MESSAGE);
                                if(name == null) {//hit cancel button
                                    inputB = (JOptionPane.showConfirmDialog(null, "Would you like to cancel adding a game? ",
                                            "Add Game - Cancel", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                                    if(inputB) {
                                        cancel = true;
                                        check = false;
                                    }
                                    else {
                                        check = true;
                                    }
                                }
                                else {//checking if there is already a game with the same name
                                    check = false;
                                    for(int i = 0; i < gameList.length; i++) {
                                        if (name.equalsIgnoreCase(gameList[i].getName())) {
                                            check = true;
                                            location = i;
                                            break;
                                        }
                                    }
                                    if (check) {//asking if user want to replace game with same name
                                        inputB = (JOptionPane.showConfirmDialog(null, "There is already a game with the same name in this list \n"
                                                + gameList[location] + " \n"
                                                + "Would you like to replace that game data with what your about to enter? ",
                                                "Add Game - Replace", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                                        if (inputB) {
                                            replace = true;
                                            check = false;
                                        }
                                        else {
                                            inputB = (JOptionPane.showConfirmDialog(null, "Would you like to cancel adding a game? ",
                                                    "Add Game - Cancel", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                                            if(inputB) {
                                                cancel = true;
                                                check = false;
                                            }
                                        }
                                    }
                                }
                            } while(check);
                            if(!cancel) {//true if user did not cancel adding a game
                                //Add new game Type
                                int type;
                                type = JOptionPane.showOptionDialog(null, "What type of game is it? ",
                                        "Add Game - Type", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, gameTypes, gameTypes[0]);
                                int vgType = 9;
                                if (type == gameTypes.length - 2) {
                                    vgType = JOptionPane.showOptionDialog(null, "What type of video game is it? ",
                                            "Add Game - Type", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, videoGameTypes, videoGameTypes[0]);
                                }
                                //Add min and max player
                                boolean ifPlayer = (JOptionPane.showConfirmDialog(null,"Would you like to add a minimum and maximum player count",
                                        "Add Game - Players", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                                int min = 0;
                                int max = 0;
                                if(ifPlayer) {
                                    do {
                                        min = addMinMax("minimum");
                                        if(min < 0) {
                                            cancel = true;
                                            ifPlayer = false;
                                            check = false;
                                        }
                                        if(!cancel) {
                                            max = addMinMax("maximum");
                                            if(max < 0) {
                                                cancel = true;
                                                ifPlayer = false;
                                                check = false;
                                            }
                                            if(!cancel) {
                                                if(max < min) {//minimum value is greater than maximum value error
                                                    inputB = (JOptionPane.showConfirmDialog(null, "The min value is greater than the max value. \n"
                                                            + "Do want to try inputting new values? \nIf you say no, it will not keep the min and max players. ",
                                                            "Add Game - Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                                                    if(inputB) {
                                                        check = true;
                                                    }
                                                    else {
                                                        cancel = true;
                                                        ifPlayer = false;
                                                        check = false;
                                                    }
                                                }
                                                else {
                                                    check = false;
                                                }
                                            }
                                        }

                                    } while(check);
                                }
                                //Add Description
                                boolean ifDescription = (JOptionPane.showConfirmDialog(null,"Would you like to add a description",
                                        "Add Game - Description", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                                String description = "";
                                if(ifDescription) {
                                    description = JOptionPane.showInputDialog(null,"What is the description of the game? \n"
                                            +"(If left blank or cancel is hit, it will remove the description) ",
                                            "Add Game - Description", JOptionPane.QUESTION_MESSAGE);
                                    if(description == null) {
                                        description = "";
                                        ifDescription = false;
                                    }
                                    else if(description.equals("")) {
                                        ifDescription = false;
                                    }
                                }
                                //Put all of it together
                                if(replace) {//replacing game with same name data on list
                                    gameList[location] = new VideoGames(name, gameTypes[type], videoGameTypes[vgType], ifPlayer, min, max, ifDescription, description);
                                }
                                else {//adding game to list
                                    VideoGames[] temp = new VideoGames[gameList.length+1];
                                    for(int i =0; i < gameList.length; i++) {
                                        temp[i] = new VideoGames(gameList[i].getName(), gameList[i].getType(), gameList[i].getVgType(), gameList[i].getIfPlayers(),
                                                gameList[i].getMinPlayers(), gameList[i].getMaxPlayers(), gameList[i].getIfDescription(), gameList[i].getDescription());
                                    }
                                    if(type == gameTypes.length-2) {
                                        temp[gameList.length] = new VideoGames(name, gameTypes[type], videoGameTypes[vgType], ifPlayer, min, max, ifDescription, description);
                                    }
                                    else {
                                        temp[gameList.length] = new VideoGames(name, gameTypes[type], "", ifPlayer, min, max, ifDescription, description);
                                    }
                                    gameList = new VideoGames[temp.length];
                                    for(int i =0; i < gameList.length; i++) {
                                        gameList[i] = new VideoGames(temp[i].getName(), temp[i].getType(), temp[i].getVgType(), temp[i].getIfPlayers(),
                                                temp[i].getMinPlayers(), temp[i].getMaxPlayers(), temp[i].getIfDescription(), temp[i].getDescription());
                                    }
                                }
                                //offering to save changes
                                inputB = (JOptionPane.showConfirmDialog(null, "Would you like to save now after editing the list? ",
                                        "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                                filesData.replace(currentListName,gameList);
                                if(inputB) {
                                    gameList = sortList(gameList);
                                    Loader.saveGameList(gameList, currentListName);
                                }
                                //offering to add another game
                                addGames = (JOptionPane.showConfirmDialog(null, "Would you like to add another game to the list? ",
                                        "Add Game - Again", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                            }//end of big if canceled
                        } while(addGames);
                    }//add game
                    else if(editInput == 2) {//edit game - You can edit a game data that already exist within the current list
                        if(gameList.length == 0) {
                            JOptionPane.showMessageDialog(null,"There is no games in the game list\nUnable to edit anything","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                        else {
                            boolean check;
                            boolean cancel;
                            do {
                                input = JOptionPane.showInputDialog(null, "What is the name of the game you want to edit? \n"
                                        + "Click the cancel button to cancel editing a game\n"
                                        + "Type viewGames to see games currently on the game list",
                                        "Edit Game", JOptionPane.QUESTION_MESSAGE);

                                if(input == null) {
                                    check = false;
                                    cancel = true;
                                }
                                else if (input.equalsIgnoreCase("viewGames")) {//view games
                                    cancel = false;
                                    viewGames(gameList);
                                    check = true;
                                }
                                else {
                                    cancel = false;
                                    check = true;
                                    for (VideoGames gl : gameList) {
                                        if (input.equalsIgnoreCase(gl.getName())) {
                                            check = false;
                                            break;
                                        }
                                    }
                                    if (check) {
                                        JOptionPane.showMessageDialog(null, "There is no game on the list with that name", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            } while (check);

                            if(!cancel) {
                                int game = 0;
                                for(int i = 0; i < gameList.length; i++) {
                                    if(input.equalsIgnoreCase(gameList[i].getName())) {
                                        game = i;
                                        break;
                                    }
                                }
                                do {
                                    input = JOptionPane.showInputDialog(null, "You can edit:\n [Name:Type:Players:Description]\n"
                                            +"This is the current values of the game\n"+ gameList[game].toString() +"\n"
                                            +"Type finish if your done editing");
                                    check = true;
                                    if (input!=null && input.equalsIgnoreCase("name")) {
                                        String newName;
                                        do {
                                            newName = JOptionPane.showInputDialog(null, "What is the new name of the game?");
                                            check = false;
                                            for(VideoGames gl:gameList) {
                                                if (newName.equalsIgnoreCase(gl.getName())) {
                                                    check = true;
                                                    break;
                                                }
                                            }
                                            if(check) {
                                                if((newName.equalsIgnoreCase(gameList[game].getName())) && !(newName.equals(gameList[game].getName()))) {
                                                    check = false;
                                                }
                                                else {
                                                    JOptionPane.showMessageDialog(null, "Their is already another game with the same name in the game list", "Warning", JOptionPane.WARNING_MESSAGE);
                                                }
                                            }
                                        } while(check);
                                        gameList[game].setName(newName);
                                        check = true;
                                    }
                                    else if(input!=null && input.equalsIgnoreCase("type")) {
                                        int type;
                                        type  = JOptionPane.showOptionDialog(null, "What type of game is it? ",
                                                "Type Search",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, gameTypes, gameTypes[0]);
                                        int vgType = -1;
                                        if(type == gameTypes.length-2) {
                                            vgType = JOptionPane.showOptionDialog(null, "What type of video game is it? ",
                                                    "Type Search",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, videoGameTypes, videoGameTypes[0]);
                                        }
                                        gameList[game].setType(gameTypes[type]);
                                        if(type == gameTypes.length-2) {
                                            gameList[game].setVgType(videoGameTypes[vgType]);
                                        }
                                        else {
                                            gameList[game].setVgType("");
                                        }
                                    }
                                    else if(input!=null && input.equalsIgnoreCase("players")) {
                                        boolean ifPlayer = (JOptionPane.showConfirmDialog(null,"Would you like to add a minimum and maximum player count",
                                                "Set Players", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                                        int min = 0;
                                        int max = 0;
                                        if(ifPlayer) {
                                            min = addMinMax("minimum");
                                            max = addMinMax("maximum");
                                        }
                                        gameList[game].setPlayers(ifPlayer,min,max);
                                    }
                                    else if(input!=null && input.equalsIgnoreCase("description")) {
                                        boolean ifDescription = (JOptionPane.showConfirmDialog(null,"Would you like to add a description","Set Description", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                                        String description = "";
                                        if(ifDescription) {
                                            description = JOptionPane.showInputDialog(null,"What is the description of the game?");
                                        }
                                        gameList[game].setDescription(ifDescription, description);
                                    }
                                    else if(input!=null && !input.equalsIgnoreCase("finish")) {
                                        JOptionPane.showMessageDialog(null,"There is no option with that command","Warning",JOptionPane.WARNING_MESSAGE);
                                    }
                                    else {
                                        check = false;
                                    }
                                } while(check);
                            }
                        }
                        inputB = (JOptionPane.showConfirmDialog(null, "Would you like to save now after editing the list? ",
                                "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                        if(inputB) {
                            gameList = sortList(gameList);
                            Loader.saveGameList(gameList, currentListName);
                        }
                    }//edit game
                    else if(editInput == 3) {//remove game - You can remove a game so it is no longer apart from the current list
                        if(gameList.length == 0) {
                            JOptionPane.showMessageDialog(null,"There is no games in the game list\nUnable to delete anything","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                        else {
                            boolean check = false;
                            do {
                                input = JOptionPane.showInputDialog(null, "What is the name of the game you want to remove?\n"
                                        + "Type cancel to cancel deletion\n"
                                        + "Type viewGames to see games currently on the game list");
                                check = false;
                                if(input.equalsIgnoreCase("viewGames")) {
                                    viewGames(gameList);
                                    check = true;
                                }
                                else if(!input.equalsIgnoreCase("cancel")) {
                                    check = true;
                                    for(VideoGames gl:gameList) {
                                        if (input.equalsIgnoreCase(gl.getName())) {
                                            check = false;
                                            break;
                                        }
                                    }
                                    if(check) {
                                        JOptionPane.showMessageDialog(null,"There is no game on the list with that name","Warning",JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            } while(check);

                            if(!input.equalsIgnoreCase("cancel")) {
                                VideoGames[] temp = new VideoGames[(gameList.length-1)];
                                for(int i =0; i < gameList.length; i++) {
                                    if(input.equalsIgnoreCase(gameList[i].getName())) {
                                        check = true;
                                    }
                                    else if(check) {
                                        temp[i-1] = new VideoGames(gameList[i].getName(), gameList[i].getType(), gameList[i].getVgType(), gameList[i].getIfPlayers(),
                                                gameList[i].getMinPlayers(), gameList[i].getMaxPlayers(), gameList[i].getIfDescription(), gameList[i].getDescription());
                                    }
                                    else {
                                        temp[i] = new VideoGames(gameList[i].getName(), gameList[i].getType(), gameList[i].getVgType(), gameList[i].getIfPlayers(),
                                                gameList[i].getMinPlayers(), gameList[i].getMaxPlayers(), gameList[i].getIfDescription(), gameList[i].getDescription());
                                    }
                                }
                                gameList = new VideoGames[temp.length];
                                for(int i = 0; i < gameList.length; i++) {
                                    gameList[i] = new VideoGames(temp[i].getName(), temp[i].getType(), temp[i].getVgType(), temp[i].getIfPlayers(),
                                            temp[i].getMinPlayers(), temp[i].getMaxPlayers(), temp[i].getIfDescription(), temp[i].getDescription());
                                }
                            }
                        }
                        inputB = (JOptionPane.showConfirmDialog(null, "Would you like to save now after editing the list? ",
                                "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                        if(inputB) {
                            Loader.saveGameList(gameList, currentListName);
                        }
                    }//remove game
                    else if(editInput == 4) {//go back - Go back to the main menu options
                        runEdit = false;
                    }//go back
                } while(runEdit);
            }//edit list
            else if(inputI == 3) {//view data - anything about viewing the data of the current file being edited
                boolean runView = true;
                do {
                    int editInput = JOptionPane.showOptionDialog(null, "View Data Menu"
                            + "Currently editing " + currentListName + " list ",
                            "View Data", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, Help.viewData, Help.viewData[0]);
                    if(editInput == 0) {//commands
                        Help.viewDataCommands();
                    }//commands
                    else if(editInput == 1) {//search by type - Allows searching the games base on the type the game is
                        if(gameList.length == 0) {
                            JOptionPane.showMessageDialog(null,"There is no games in the game list\nAdd games to use search functions","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                        else {
                            int type;
                            type  = JOptionPane.showOptionDialog(null, "What is the type of game you want to search for?",
                                    "Type Search",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, gameTypes, gameTypes[0]);
                            int vgType = -1;
                            if (type == gameTypes.length-2) {
                                boolean check = (JOptionPane.showConfirmDialog(null, "Do you want to search for specific video game type?"
                                        , "Type Search", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                                if (check) {
                                    vgType = JOptionPane.showOptionDialog(null, "What is the type of video game you want to search for?",
                                            "Type Search",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, videoGameTypes, videoGameTypes[0]);
                                }
                            }
                            String matchType = "";
                            gameList = sortList(gameList);
                            for(VideoGames gl:gameList) {
                                if(type == 0 && vgType >= 0) {
                                    if(gl.getType().equalsIgnoreCase(gameTypes[type]) && gl.getVgType().equalsIgnoreCase(videoGameTypes[vgType])) {
                                        matchType+=gl.toString() + "\n";
                                    }
                                }
                                else {
                                    if(gl.getType().equalsIgnoreCase(gameTypes[type])) {
                                        matchType+=gl.toString() + "\n";
                                    }
                                }
                            }
                            if(matchType.equals("")) {
                                JOptionPane.showMessageDialog(null,"There are no games with the same type","Result",JOptionPane.INFORMATION_MESSAGE);
                            }
                            else {
                                JOptionPane.showMessageDialog(null,"The following games all have the same type:\n"+matchType,"Search Type Result",JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }//search by type
                    else if(editInput == 2) {//search by player - Allows searching the games based on how many players
                        if(gameList.length < 1) {
                            JOptionPane.showMessageDialog(null,"There is no games in the game list\n"
                                    + "Add games to use search functions - the more games the more affective it is",
                                    "Search Players - Warning",JOptionPane.WARNING_MESSAGE);
                        }
                        else {
                            minPlayersBST min = new minPlayersBST();
                            maxPlayersBST max = new maxPlayersBST();


                            for(VideoGames g : gameList) {
                                min.insert(g);
                                max.insert(g);

                            }

                            VideoGames[] minList = min.getMinPlayers();
                            VideoGames[] maxList = max.getMaxPlayers();

                            ArrayList<Integer> keys = new ArrayList<>();
                            for(int i = minList[0].minPlayers; i <= maxList[gameList.length].maxPlayers; i++) {
                                keys.add(i);
                            }

                            String games = "";
                            String[] options = {"At Least", "Between", "No More"};
                            int players = JOptionPane.showOptionDialog(null, "Options description below:\n"
                                    + "At Least - searching for games that have at least the same number of players you insert \n"
                                    + "Between - Picking two values and checking if the min and max values are between them \n"
                                    + "No More -  Setting only a maximum number of players that a game can have",
                                    "Search Players", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                            if(players == 0) {//at least
                                int num1 = JOptionPane.showOptionDialog(null, "The number of minimum players (Values auto set based on games values)",
                                        "Search Players", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, keys.toArray(), keys.toArray()[0]);
                                for(int i = 0; i < gameList.length; i++) {
                                    if(minList[i].getMinPlayers() >= num1) {
                                        games += minList[i].toString() +  " \n";
                                    }
                                }
                            }
                            else if (players == 1) {//between
                                String[] option = {"General Idea", "Exactly Between"};
                                players = JOptionPane.showOptionDialog(null, "Options description below: \n"
                                                + "General Idea -  Has a min and max player count that generally fits the number of players you want \n"
                                                + "Exactly Between - The min and max player count is exactly between the your values \n",
                                        "Search Players", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);
                                if(players == 0) {//general idea
                                    int num1 = JOptionPane.showOptionDialog(null, "The number of minimum players (Values auto set based on games values)",
                                            "Search Players", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, keys.toArray(), keys.toArray()[0]);
                                    for(int i = 0; i < keys.size(); i++) {
                                        if(num1 > keys.get(0)) {
                                            keys.remove(0);
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    int num2 = JOptionPane.showOptionDialog(null, "The number of maximum players (Values auto set based on games values)",
                                            "Search Players", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, keys.toArray(), keys.toArray()[0]);
                                    for(int i = 0; i < gameList.length; i++) {
                                        if(minList[i].getMinPlayers() >= num1 || minList[i].getMaxPlayers() <= num2) {
                                            games += minList[i].toString() +  " \n";
                                        }
                                    }
                                }
                                else if (players == 1) {//exactly between
                                    int num1 = JOptionPane.showOptionDialog(null, "The number of minimum players (Values auto set based on games values)",
                                            "Search Players", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, keys.toArray(), keys.toArray()[0]);
                                    for(int i = 0; i < keys.size(); i++) {
                                        if(num1 > keys.get(0)) {
                                            keys.remove(0);
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    int num2 = JOptionPane.showOptionDialog(null, "The number of maximum players (Values auto set based on games values)",
                                            "Search Players", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, keys.toArray(), keys.toArray()[0]);
                                    for(int i = 0; i < gameList.length; i++) {
                                        if(minList[i].getMinPlayers() >= num1 && minList[i].getMaxPlayers() <= num2) {
                                            games += minList[i].toString() +  " \n";
                                        }
                                    }
                                }
                            }
                            else if (players == 2) {//no more
                                int num1 = JOptionPane.showOptionDialog(null, "The number of maximum players (Values auto set based on games values)",
                                        "Search Players", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, keys.toArray(), keys.toArray()[0]);
                                for(int i = 0; i < gameList.length; i++) {
                                    if(maxList[i].getMaxPlayers() <= num1) {
                                        games += maxList[i].toString() +  " \n";
                                    }
                                }
                            }
                        }

                    }//search by player
                    else if(editInput == 3) {//random game - Pulls up random games from the game list
                        if(gameList.length == 0) {
                            JOptionPane.showMessageDialog(null,"There is no games in the game list\nAdd games to use the random game command","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                        else if(gameList.length == 1) {
                            JOptionPane.showMessageDialog(null,"There is only one game in the game list. The random command can't properly work with only one game\nAdd more games to be able to use the random command","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                        else {
                            boolean check = false;
                            do {
                                int random = (int)(Math.random()*gameList.length);
                                check = (JOptionPane.showConfirmDialog(null,"Your random game is: \n" + gameList[random].toString() + "\nDo you want to find another random game? ",
                                        "Random Game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                            } while(check);
                        }
                    }//random game
                    else if(editInput == 4) {//view all - displays all games on the current list
                        viewGames(gameList);
                    }//view all
                    else if(editInput == 5) {//go back - Go back to the main menu options
                        runView = false;
                    }//go back
                } while(runView);
            }//view data
            else if(inputI == 4) {//load data - editing which files are loaded and which one your editing
                boolean runLoad = true;
                do {
                    int editInput = JOptionPane.showOptionDialog(null, "Load Data Menu"
                            + "Currently editing " + currentListName + " list ",
                            "Load Data", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, Help.loadDate, Help.loadDate[0]);
                    if(editInput == 0) {//commands
                        Help.loadDataCommands();
                    }//commands
                    else if(editInput == 1) {//load new file - Adds new files that has game list on them so they can be accessed and edited
                        filesData.replace(currentListName, gameList);

                        inputB = (JOptionPane.showConfirmDialog(null, "Would you like to save now before loading another list? \n"
                                        + "Anything not save now will not be lost when loading a new file \n"
                                        + "However it is still a good idea to save this data just in case",
                                "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);

                        if(inputB) {
                            Loader.saveGameList(gameList, currentListName);
                        }

                        String oldFileName = currentListName;
                        setFileName();
                        if(!oldFileName.equals(currentListName)) {
                            filesData.replace(currentListName, Loader.loadGameList(currentListName));
                            gameList = filesData.get(currentListName);
                        }

                        do {
                            inputB = (JOptionPane.showConfirmDialog(null, "Would you like to load another file?\n",
                                    "Load File", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                            if(inputB) {
                                oldFileName = currentListName;
                                setFileName();
                                if(!oldFileName.equals(currentListName)) {
                                    filesData.replace(currentListName, Loader.loadGameList(currentListName));
                                    gameList = filesData.get(currentListName);
                                }
                            }
                        } while(inputB);
                    }//load new file
                    else if(editInput == 2) {//change current file - Sets which file data your currently viewing and editing
                        filesData.replace(currentListName, gameList);

                        int type  = JOptionPane.showOptionDialog(null, "What file do you want to edit now? ",
                                "Edit File",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, files.toArray(), files.get(0));
                        currentListName = files.get(type);
                        gameList = filesData.get(currentListName);
                    }//change current file
                    else if(editInput == 3) {//close files - Allows you to save and remove files you no longer need to access
                        filesData.replace(currentListName, gameList);

                        do {
                            if (files.size() <= 1) {
                                JOptionPane.showMessageDialog(null, "You need to have more than 1 file open to close another file",
                                        "Finish File", JOptionPane.WARNING_MESSAGE);
                                inputB = false;
                            } else {
                                int type = JOptionPane.showOptionDialog(null, "What file are you finished with? \n"
                                                + "It will be saved again before actually removing it \n "
                                                + "Will change currently editing file if closing the open file",
                                        "Finish File", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, files.toArray(), files.get(0));
                                Loader.saveGameList(filesData.get(files.get(type)), files.get(type));

                                filesData.remove(files.get(type));
                                files.remove(files.get(type));

                                if (!files.contains(currentListName)) {
                                    currentListName = files.get(0);
                                    gameList = filesData.get(currentListName);
                                }

                                if(files.size() > 1) {
                                    inputB = (JOptionPane.showConfirmDialog(null, "Would you like to remove another file?\n",
                                            "Finish File", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);

                                }
                                else {
                                    JOptionPane.showMessageDialog(null,"You are no longer able to remove files since there only 1 left ",
                                            "Finish File", JOptionPane.INFORMATION_MESSAGE);
                                    inputB = false;
                                }
                            }
                        } while (inputB && files.size() <= 1);
                    }//close files
                    else if(editInput == 4) {//go back - Go back to the main menu options
                        runLoad = false;
                    }//go back
                } while(runLoad);
            }//load data
            else if(inputI == 5) {//end program
                runProgram = false;
            }//end program
        } while (runProgram);

        JOptionPane.showMessageDialog(null,"Saving all open files before closing program");
        for(String i: files) {
            Loader.saveGameList(filesData.get(i), i);
        }

    }

    //Uses queue to help with making sure numbers are inserted for min and max player count
    public static int addMinMax(String type) {
        int minMax = 0;

        Queue<String> queue = new LinkedList<>();
        queue.offer(type);

        do {
            try {
                String input = JOptionPane.showInputDialog(null, "What is the " + queue.poll() + " number of players? ",
                        "Add Game - Players", JOptionPane.QUESTION_MESSAGE);
                boolean cancel = false;
                if(input == null) {//hit cancel button
                    cancel = true;
                    boolean inputB = (JOptionPane.showConfirmDialog(null, "Would you like to cancel adding player values ",
                            "Add Game - Cancel", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
                    if(inputB) {
                        minMax = -1;
                    }
                    else {
                        queue.offer(type);
                    }
                }
                if(!cancel) {
                    minMax = Integer.parseInt(input);
                }
            } catch (NumberFormatException e) {
                queue.offer(type);
                JOptionPane.showMessageDialog(null, "Please enter a number ", "Add Game - Warning", JOptionPane.WARNING_MESSAGE);
            }
        } while(!queue.isEmpty());

        return minMax;
    }

    public static void viewGames(VideoGames[] gameList) {
        gameList = sortList(gameList);
        String result = "Full List of Games:\n\n";
        for(Games gl:gameList) {
            result+=gl.toString() + "\n";
        }
        JOptionPane.showMessageDialog(null, result);
    }

    public static void setFileName() {
        boolean invalidName;
        String input = JOptionPane.showInputDialog(null, "Please input the name of the file you would want to save and load from \n"
                + "Please only use letter (Upper or Lower case is fine), no spaces, and do not include the .txt at the end \n"
                +"Also, no repeating file names. \n\n"
                + "If you hit cancel, leave the input box empty, or input a invalid file name base on the rules above \n"
                + "the program will then use not add this to the list of files to load and save to ", "Set File Name", JOptionPane.QUESTION_MESSAGE);
        if(input != null) {
            if(!input.equals("")) {
                invalidName = false;
                for (int i = 0; i < input.length(); i++) {
                    invalidName = !Character.isLetter(input.charAt(i));
                    if(invalidName) {
                        break;
                    }
                }
                if (invalidName) {
                    JOptionPane.showMessageDialog(null, "The inputted name is invalid \n"
                            + "No new file name added ",
                            "Invalid File Name", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if(files.contains(input)) {
                        JOptionPane.showMessageDialog(null, "The inputted name is is hte same as another file name \n"
                                        + "No new file name added ",
                                "Invalid File Name", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "The inputted name is valid \n"
                                        + input + " is going to be set to the current file being edited ",
                                "Valid File Name", JOptionPane.INFORMATION_MESSAGE);
                        currentListName = input;
                        files.add(input);
                        filesData.put(currentListName, new VideoGames[0]);
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "The box was empty when ok was hit \n"
                        + "No new file name added ",
                        "Invalid File Name", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "The cancel button was hit \n"
                    + "No new file name added ",
                    "Invalid File Name", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Merge sort has a complexity of O(n logn)
    //Uses multiple merge sort to organise all the types alphabetically and the names within each type alphabetically as well
    public static VideoGames[] sortList (VideoGames[] gameList) {

        gameList = sortType(gameList);

        int[] change = {0};
        String previous = "";

        for(int i = 0; i < gameList.length; i++) {
            if(i == 0) {
                previous = gameList[i].getType();
            }
            else {
                if(!previous.equals(gameList[i].getType())) {
                    int[] temp = new int[change.length+1];
                    for(int x = 0; x < change.length; x++) {
                        temp[x] = change[x];
                    }
                    temp[change.length] = i;
                    change = new int[temp.length];
                    for(int x = 0; x < change.length; x++) {
                        change[x] = temp[x];
                    }
                }
                previous = gameList[i].getType();
            }
        }

        for(int i = 0; i < change.length-1; i++) {
            VideoGames[] temp = new VideoGames[change[i+1]-change[i]];
            for(int x = change[i]; x < change[i+1]; x++ ) {
                temp[x-change[i]].setAll(gameList[x]);
            }
            temp = sortNames(temp);
            for(int x = change[i]; x < change[i+1]; x++ ) {
                gameList[x].setAll(temp[x-change[i]]);
            }
        }

        int last = change[change.length-1];
        VideoGames[] temps = new VideoGames[gameList.length-last];
        for(int x = last; x < gameList.length; x++ ) {
            temps[x-last].setAll(gameList[x]);
        }
        if((gameList[change[last]].getType()).equalsIgnoreCase("VideoGame")) {
            temps = sortVgType(temps);
            int[] changes = {0};

            for(int i = 0; i < temps.length; i++) {
                if(i == 0) {
                    previous = temps[i].getVgType();
                }
                else {
                    if(!previous.equals(temps[i].getVgType())) {
                        int[] temp = new int[changes.length+1];
                        for(int x = 0; x < changes.length; x++) {
                            temp[x] = changes[x];
                        }
                        temp[changes.length] = i;
                        changes = new int[temp.length];
                        for(int x = 0; x < changes.length; x++) {
                            changes[x] = temp[x];
                        }
                    }
                    previous = temps[i].getVgType();
                }
            }

            for(int i = 0; i < changes.length-1; i++) {
                VideoGames[] temp = new VideoGames[changes[i+1]-changes[i]];
                for(int x = changes[i]; x < changes[i+1]; x++ ) {
                    temp[x-changes[i]].setAll(temps[x]);
                }
                temp = sortNames(temp);
                for(int x = changes[i]; x < changes[i+1]; x++ ) {
                    temps[x].setAll(temp[x-changes[i]]);
                }
            }

            int lasts = changes[changes.length-1];
            VideoGames[] temp = new VideoGames[temps.length-last];
            for(int x = last; x < temps.length; x++ ) {
                temp[x-last].setAll(temps[x]);
            }
            temp = sortNames(temp);
            for(int x = last; x < temps.length; x++ ) {
                temps[x].setAll(temp[x-last]);
            }
        }
        else {
            temps = sortNames(temps);
        }
        for(int x = last; x < gameList.length; x++ ) {
            gameList[x].setAll(temps[x-last]);
        }

        return gameList;
    }

    //Uses merge sort to sort the game names in alphabetical order
    public static VideoGames[] sortNames (VideoGames[] gameList) {
        mergeSortName(gameList);
        return gameList;
    }

    //Uses merge sort to sort the game type in alphabetical order
    public static VideoGames[] sortType (VideoGames[] gameList) {
        mergeSortType(gameList);
        return gameList;
    }

    //Uses merge sort to sort the game video game type in alphabetical order
    public static VideoGames[] sortVgType (VideoGames[] gameList) {
        mergeSortVgType(gameList);
        return gameList;
    }

    public static void mergeSortName(VideoGames[] list) {
        if (list.length > 1) {
            // Merge sort the first half
            VideoGames[] firstHalf = new VideoGames[list.length / 2];
            System.arraycopy(list, 0, firstHalf, 0, list.length / 2);
            mergeSortName(firstHalf);

            // Merge sort the second half
            int secondHalfLength = list.length - list.length / 2;
            VideoGames[] secondHalf = new VideoGames[secondHalfLength];
            System.arraycopy(list, list.length / 2,
                    secondHalf, 0, secondHalfLength);
            mergeSortName(secondHalf);

            // Merge firstHalf with secondHalf into list
            mergeName(firstHalf, secondHalf, list);
        }
    }

    public static void mergeName(VideoGames[] list1, VideoGames[] list2, VideoGames[] temp) {
        int current1 = 0; // Current index in list1
        int current2 = 0; // Current index in list2
        int current3 = 0; // Current index in temp

        while (current1 < list1.length && current2 < list2.length) {
            //if list1 value is earlier-smaller than list2 value
            if ((list1[current1].getName()).compareTo(list2[current2].getName()) < 0)
                temp[current3++] = list1[current1++];
            else
                temp[current3++] = list2[current2++];
        }

        while (current1 < list1.length)
            temp[current3++] = list1[current1++];

        while (current2 < list2.length)
            temp[current3++] = list2[current2++];
    }

    public static void mergeSortType(VideoGames[] list) {
        if (list.length > 1) {
            // Merge sort the first half
            VideoGames[] firstHalf = new VideoGames[list.length / 2];
            System.arraycopy(list, 0, firstHalf, 0, list.length / 2);
            mergeSortType(firstHalf);

            // Merge sort the second half
            int secondHalfLength = list.length - list.length / 2;
            VideoGames[] secondHalf = new VideoGames[secondHalfLength];
            System.arraycopy(list, list.length / 2,
                    secondHalf, 0, secondHalfLength);
            mergeSortType(secondHalf);

            // Merge firstHalf with secondHalf into list
            mergeType(firstHalf, secondHalf, list);
        }
    }

    public static void mergeType(VideoGames[] list1, VideoGames[] list2, VideoGames[] temp) {
        int current1 = 0; // Current index in list1
        int current2 = 0; // Current index in list2
        int current3 = 0; // Current index in temp

        while (current1 < list1.length && current2 < list2.length) {
            //if list1 value is earlier-smaller than list2 value
            if ((list1[current1].getType()).compareTo(list2[current2].getType()) < 0)
                temp[current3++] = list1[current1++];
            else
                temp[current3++] = list2[current2++];
        }

        while (current1 < list1.length)
            temp[current3++] = list1[current1++];

        while (current2 < list2.length)
            temp[current3++] = list2[current2++];
    }

    public static void mergeSortVgType(VideoGames[] list) {
        if (list.length > 1) {
            // Merge sort the first half
            VideoGames[] firstHalf = new VideoGames[list.length / 2];
            System.arraycopy(list, 0, firstHalf, 0, list.length / 2);
            mergeSortVgType(firstHalf);

            // Merge sort the second half
            int secondHalfLength = list.length - list.length / 2;
            VideoGames[] secondHalf = new VideoGames[secondHalfLength];
            System.arraycopy(list, list.length / 2,
                    secondHalf, 0, secondHalfLength);
            mergeSortVgType(secondHalf);

            // Merge firstHalf with secondHalf into list
            mergeVgType(firstHalf, secondHalf, list);
        }
    }

    public static void mergeVgType(VideoGames[] list1, VideoGames[] list2, VideoGames[] temp) {
        int current1 = 0; // Current index in list1
        int current2 = 0; // Current index in list2
        int current3 = 0; // Current index in temp

        while (current1 < list1.length && current2 < list2.length) {
            //if list1 value is earlier-smaller than list2 value
            if ((list1[current1].getVgType()).compareTo(list2[current2].getVgType()) < 0)
                temp[current3++] = list1[current1++];
            else
                temp[current3++] = list2[current2++];
        }

        while (current1 < list1.length)
            temp[current3++] = list1[current1++];

        while (current2 < list2.length)
            temp[current3++] = list2[current2++];
    }

}