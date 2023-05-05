// **********************************************************************************
// Title: Major Project 3 - Game Library
// Author: Robert Thompson
// Course Section: CMIS201-ONL1 (Seidel) Spring 2023
// File: minPlayersBST.java
// Description: Used to be able to get a quick sort of games min players values
// **********************************************************************************

import java.util.ArrayList;

public class minPlayersBST {

    VideoGames[] minPlayers = new VideoGames[0];

    public VideoGames[] getMinPlayers() {
        inorder();
        return minPlayers;
    }

    class Node {

        VideoGames key;
        VideoGames[] keys = new VideoGames[0];
        boolean multiple = false;
        Node left, right;

        public Node(VideoGames item) {
            key = item;
            left = right = null;
        }

        public void addKeys(VideoGames g) {
            VideoGames[] temp = new VideoGames[keys.length+1];
            for(int i = 0; i < keys.length; i++) {
                temp[i] = keys[i];
            }
            temp[keys.length] = g;
            keys = new VideoGames[temp.length];
            for(int i = 0; i < keys.length; i++) {
                keys[i] = temp[i];
            }
        }

    }

    Node root;

    minPlayersBST() {
        root = null;
    }

    minPlayersBST(VideoGames value) {
        root = new Node(value);
    }

    void insert(VideoGames key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, VideoGames key) {

        if(root == null) {
            root = new Node(key);
            root.addKeys(key);
            return root;
        }
        else if(key.minPlayers < root.key.minPlayers) {
            root.left = insertRec(root.left, key);
        }
        else if(key.minPlayers > root.key.minPlayers) {
            root.right= insertRec(root.right, key);
        }
        else {
            root.multiple = true;
            root.addKeys(key);
        }

        return root;
    }

    void inorder() {
        minPlayers = new VideoGames[0];
        inorderRec(root);
    }

    void inorderRec(Node root) {
        if(root != null) {

            inorderRec(root.left);

            VideoGames[] temp;
            if(root.multiple) {
                temp = new VideoGames[minPlayers.length + root.keys.length];
            }
            else {
                temp = new VideoGames[minPlayers.length + 1];
            }
            for(int i = 0; i < minPlayers.length; i++) {
                temp[i] = minPlayers[i];
            }
            if(root.multiple) {
                for(int i = 0; i < root.keys.length; i++) {
                    temp[minPlayers.length + i] = root.keys[i];
                }
            }
            else {
                temp[minPlayers.length] = root.key;
            }
            minPlayers = new VideoGames[temp.length];
            for(int i = 0; i < minPlayers.length; i++) {
                minPlayers[i] = temp[i];
            }

            inorderRec(root.right);
        }
    }



}
