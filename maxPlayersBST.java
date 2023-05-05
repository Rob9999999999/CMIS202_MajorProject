// **********************************************************************************
// Title: Major Project 3 - Game Library
// Author: Robert Thompson
// Course Section: CMIS201-ONL1 (Seidel) Spring 2023
// File: maxPlayersBST.java
// Description: Used to be able to get a quick sort of games min players values
// **********************************************************************************

import java.util.ArrayList;

public class maxPlayersBST {

    VideoGames[] maxPlayers = new VideoGames[0];

    public VideoGames[] getMaxPlayers() {
        inorder();
        return maxPlayers;
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
            VideoGames[] temp = new VideoGames[keys.length + 1];
            for (int i = 0; i < keys.length; i++) {
                temp[i] = keys[i];
            }
            temp[keys.length] = g;
            keys = new VideoGames[temp.length];
            for (int i = 0; i < keys.length; i++) {
                keys[i] = temp[i];
            }
        }

    }

    Node root;

    maxPlayersBST() {
        root = null;
    }

    maxPlayersBST(VideoGames value) {
        root = new Node(value);
    }

    void insert(VideoGames key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, VideoGames key) {

        if (root == null) {
            root = new Node(key);
            root.addKeys(key);
            return root;
        } else if (key.maxPlayers < root.key.maxPlayers) {
            root.left = insertRec(root.left, key);
        } else if (key.maxPlayers > root.key.maxPlayers) {
            root.right = insertRec(root.right, key);
        } else {
            root.multiple = true;
            root.addKeys(key);
        }

        return root;
    }

    void inorder() {
        maxPlayers = new VideoGames[0];
        inorderRec(root);
    }

    void inorderRec(Node root) {
        if (root != null) {

            inorderRec(root.left);

            VideoGames[] temp;
            if (root.multiple) {
                temp = new VideoGames[maxPlayers.length + root.keys.length];
            } else {
                temp = new VideoGames[maxPlayers.length + 1];
            }
            for (int i = 0; i < maxPlayers.length; i++) {
                temp[i] = maxPlayers[i];
            }
            if (root.multiple) {
                for (int i = 0; i < root.keys.length; i++) {
                    temp[maxPlayers.length + i] = root.keys[i];
                }
            } else {
                temp[maxPlayers.length] = root.key;
            }
            maxPlayers = new VideoGames[temp.length];
            for (int i = 0; i < maxPlayers.length; i++) {
                maxPlayers[i] = temp[i];
            }

            inorderRec(root.right);
        }
    }
}