// **********************************************************************************
// Title: Major Project 3 - Game Library
// Author: Robert Thompson
// Course Section: CMIS201-ONL1 (Seidel) Spring 2023
// File: Games.java
// Description: Used to store the information of each individual game.
// **********************************************************************************

public class Games {

    String name;
    String type;
    boolean ifPlayers;
    int minPlayers;
    int maxPlayers;
    boolean ifDescription;
    String description;

    //Used to automatically add values on setup
    Games(String name, String type, boolean players, int min, int max, boolean ifDescription, String description) {
        setName(name);
        setType(type);
        setPlayers(players, min, max);
        setDescription(ifDescription, description);
    }

    //Used to set up with empty values
    Games() {
        setName("");
        setType("");
        setPlayers(false, 0, 0);
        setDescription(false, "");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPlayers(boolean players, int min, int max) {
        this.ifPlayers = players;
        this.minPlayers = min;
        this.maxPlayers = max;
    }

    public void setDescription(boolean ifDescription, String description) {
        this.ifDescription = ifDescription;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public boolean getIfPlayers() {
        return this.ifPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean getIfDescription() {
        return this.ifDescription;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        String result = this.name + " ";
        result += "[" + this.type + "] ";
        if(this.ifPlayers) {
            result += "Players: ";
            result += "Min: " + minPlayers + " ";
            result += "Max: " + maxPlayers + " ";
        }
        if(this.ifDescription) {
            result += "Description: ";
            result += this.description;
        }
        return result;
    }

}
