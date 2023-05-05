// **********************************************************************************
// Title: Major Project 3 - Game Library
// Author: Robert Thompson
// Course Section: CMIS201-ONL1 (Seidel) Spring 2023
// File: VideoGames.java
// Description: Extension of Games file for video games to allow for setting of a specific video games type
// **********************************************************************************

public class VideoGames extends Games {

    String vgType;

    VideoGames(String name, String type, String vgType, boolean players, int min, int max, boolean ifDescription, String description) {
        setName(name);
        setType(type);
        setVgType(vgType);
        setPlayers(players, min, max);
        setDescription(ifDescription, description);
    }

    VideoGames() {
        setName("");
        setType("");
        setVgType("");
        setPlayers(false, 0, 0);
        setDescription(false, "");
    }

    public void setVgType(String vgType) {
        this.vgType = vgType;
    }

    public void setAll(VideoGames game) {
        setName(game.getName());
        setType(game.getType());
        setVgType(game.getVgType());
        setPlayers(game.getIfPlayers(), game.getMinPlayers(), game.getMaxPlayers());
        setDescription(game.getIfDescription(), game.getDescription());
    }

    public String getVgType() {
        return vgType;
    }

    @Override
    public String toString() {
        String result = this.name + " ";
        result += "["+this.type;
        if(this.type.equalsIgnoreCase("VideoGame")) {
            result += ":"+this.vgType;
        }
        result += "] ";
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
