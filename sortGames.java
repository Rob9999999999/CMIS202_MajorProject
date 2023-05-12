import java.util.*;

class sortGames {

    boolean alphabetically = false;//If true, sorts alphabetically, otherwise sorts by type.
    ArrayList<tempGames[]> tempGames = new ArrayList<>();//Used for re-adding games to the gameList from the threads

    //Merge sort has a complexity of O(n logn)

    //Uses multiple merge sort to organise all the types alphabetically and the names within each type alphabetically as well
    public VideoGames[] sortList(VideoGames[] gameList) {

        if(alphabetically) {
            gameList = sortNames(gameList);
            return gameList;
        }

        //Clear tempGames and sort inputted gamelist by its types first
        tempGames.clear();
        gameList = sortType(gameList);

        //Used to know where in gameList type changes
        int[] change = {0};
        String previous = "";

        //For loop that finds type changes
        for (int i = 0; i < gameList.length; i++) {
            if (i == 0) {
                previous = gameList[i].getType();
            } else {
                if (!previous.equals(gameList[i].getType())) {
                    int[] temp = new int[change.length + 1];
                    for (int x = 0; x < change.length; x++) {
                        temp[x] = change[x];
                    }
                    temp[change.length] = i;
                    change = new int[temp.length];
                    for (int x = 0; x < change.length; x++) {
                        change[x] = temp[x];
                    }
                }
                previous = gameList[i].getType();
            }
        }

        //for loop that creates threads that both sort by name within each type and
        Thread[] t1 = new Thread[change.length];
        for (int i = 0; i < change.length - 1; i++) {
            t1[i] = new Thread(new sortGamesRun(false, gameList, change[i], change[i + 1]));
        }
        t1[change.length - 1] = new Thread(new sortGamesRun(false, gameList, change[change.length - 1], gameList.length));

        boolean check;
        do {
            check = false;
            for(Thread t: t1) {
                check = (t.isAlive() || check);
            }
        }while(check);

        int found = 0;
//        VideoGames[] temp = new VideoGames[gameList.length];
//        for(int i = 0; i < gameList.length; i++) {
//            if(tempGames.containsKey(i)) {
//                temp  tempGames.get(i);
//                found = i;
//            }
//            if(temp[i-found] != null) {
//                gameList[i].setAll(temp[i-found]);
//            }
//        }
        for(tempGames[] tg: tempGames) {
            for(int i = tg[0].startValue; i < tg[0].endValue; i++) {
                gameList[i].setAll(tg[i-tg[0].startValue]);
            }
        }

        return gameList;
    }

    class sortGamesRun implements Runnable {

        boolean videoGame;//If true, runs sortNames - else sortVgType
        VideoGames[] gameList;//Keeps track of its own gameList within game
        int startChange;//The start of where the change happens
        int endChange;//end of where change happens
        int oldStart = 0;//Usefull for setting when setting a video gamelist correct spot

        sortGamesRun(boolean vG, VideoGames[] games, int sC, int eC) {
            this.videoGame = vG;
            this.gameList = games;
            this.startChange = sC;
            this.endChange = eC;
        }

        sortGamesRun(boolean vG, VideoGames[] games, int sC, int eC, int oS) {
            this.videoGame = vG;
            this.gameList = games;
            this.startChange = sC;
            this.endChange = eC;
            this.oldStart = oS;
        }

        //What happens when thread starts
        public void run() {
            tempGames[] temp = new tempGames[endChange-startChange];
            for(int i = startChange; i < endChange; i++) {
                temp[i-startChange].setAll(gameList[i]);
                temp[i-startChange].startValue = startChange;
                temp[i-startChange].endValue = endChange;
            }
            if((gameList[startChange].type).equalsIgnoreCase("VideoGame") && !videoGame) {
                sortVgGames(temp, startChange);
            }
            else {
                temp = (tempGames[]) sortNames(temp);
//                VideoGames[] add = new VideoGames[gameList.length];
//                for(int i = 0; i < add.length; i++) {
//                    add[i] = new VideoGames();
//                }
//                for(int i = startChange; i < endChange; i++) {
//                    add[i].setAll(temp[i-startChange]);
//                    //temp[i-startChange].setAll(gameList[i]);
//                }
                synchronized(tempGames) {
                    //tempGames.put(oldStart + startChange, temp);
                    tempGames.add(temp);
                }
            }
        }
    }

    //Used for helping get values from thread back to gameList in correct order
    class tempGames extends VideoGames {
        int startValue;
        int endValue;
    }

    //Used to set up threads for creating sort array for
    public void sortVgGames(VideoGames[] gameList, int sC) {
        gameList = sortVgType(gameList);

        //Used to know where in gameList type changes
        int[] change = {0};
        String previous = "";

        //For loop that finds type changes
        for (int i = 0; i < gameList.length; i++) {
            if (i == 0) {
                previous = gameList[i].getVgType();
            }
            else {
                if (!previous.equals(gameList[i].getVgType())) {
                    int[] temp = new int[change.length + 1];
                    for (int x = 0; x < change.length; x++) {
                        temp[x] = change[x];
                    }
                    temp[change.length] = i;
                    change = new int[temp.length];
                    for (int x = 0; x < change.length; x++) {
                        change[x] = temp[x];
                    }
                }
                previous = gameList[i].getVgType();
            }
        }

        //for loop that creates threads that both sort by name within each type and
        Thread[] t1 = new Thread[change.length];
        for (int i = 0; i < change.length - 1; i++) {
            t1[i] = new Thread(new sortGamesRun(true, gameList, change[i], change[i + 1], sC));
        }
        t1[change.length - 1] = new Thread(new sortGamesRun(true, gameList, change[change.length - 1], gameList.length, sC));

        boolean check;
        do {
            check = false;
            for(Thread t: t1) {
                check = (t.isAlive() || check);
            }
        }while(check);
    }

    //Uses merge sort to sort the game names in alphabetical order
    public VideoGames[] sortNames(VideoGames[] gameList) {
        ArrayList<String> names = new ArrayList<>();
        VideoGames[] output = new VideoGames[gameList.length];
        synchronized (names) {

        }
        for(VideoGames g :gameList) {
            names.add(g.name);
        }
        Collections.sort(names);
        for(int i = 0; i < gameList.length; i++) {
            for(int u = 0; u <gameList.length; u++) {
                if(names.get(i).equals(gameList[u].name)) {
                    output[i] = new VideoGames();
                    output[i].setAll(gameList[u]);
                    break;
                }
            }
        }
        return output;
    }

    //Uses merge sort to sort the game type in alphabetical order
    public VideoGames[] sortType(VideoGames[] gameList) {
        mergeSortType(gameList);
        return gameList;
    }

    //Uses merge sort to sort the game video game type in alphabetical order
    public VideoGames[] sortVgType(VideoGames[] gameList) {
        mergeSortVgType(gameList);
        return gameList;
    }

    public void mergeSortType(VideoGames[] list) {
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

    public void mergeType(VideoGames[] list1, VideoGames[] list2, VideoGames[] temp) {
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

    public void mergeSortVgType(VideoGames[] list) {
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

    public void mergeVgType(VideoGames[] list1, VideoGames[] list2, VideoGames[] temp) {
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
