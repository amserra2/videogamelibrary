package Necessities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import library.Games;
import library.Game;
import library.Platform;
import library.Platforms;

public class IO {
        
    public static Games sortGames(Games games) {
        
        Games sortedGames = new Games();
        
        Map <String, Game> map = new HashMap();
        
        int n = games.getNumGames();
        String[] strings = new String[n];
        
        for (int i = 0; i < n; i++) {
            strings[i] = games.getGame(i).getTitle().replaceAll("\\s+","").toLowerCase().trim();
            map.put(strings[i], games.getGame(i));
        }        
        
        Arrays.sort(strings);
        
        for (int i = 0; i < n; i++) {
            sortedGames.addGame(map.get(strings[i]));
        }
        
       return sortedGames; 
    }
    
    public static Games searchGames(Games games, String selection) {
        
        Games sortedGames = sortGames(games);
        Games searchResults = new Games();

        int n = sortedGames.getNumGames();
        int x; 
        int y; 
        
        String subString;
        String storeString; 
        Game game;
        
        for (int i = 0; i < n; i++) {
            game = sortedGames.getGame(i);
            storeString = game.searchString();
            x = (storeString.length() - selection.length()) + 1;
            y = selection.length();
            for (int j = 0; j < x; j++) {
                subString = storeString.substring(j, y + j);
                if (subString.equals(selection)) {
                    searchResults.addGame(game);
                    break;
                }
            }
        }
        
        return searchResults;
    }
    
    public static void downloadGames(Games games) {
        
        Games sortedGames = sortGames(games);
        String list = sortedGames.downloadString();
        
        try {
            
            Date date = new Date();

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);
                int second = cal.get(Calendar.SECOND);
                
                String yearForm = String.format("%04d", year);
                String monForm = String.format("%02d", month);
                String dayForm = String.format("%02d", day);
                
                String hourForm = String.format("%02d", hour);
                String minForm = String.format("%02d", minute);
                String secForm = String.format("%02d", second);
                
                //yyyymmddhhmmss
                String dateS = yearForm + monForm + dayForm + "_" + hourForm + minForm + secForm;

            
            String s = "games_download_" + dateS + ".txt";
        
            PrintWriter pW = new PrintWriter(new File("/Users/asia/Desktop/" + s));
            pW.print(list);
            pW.close();
        } 
        catch (FileNotFoundException ex) {
            
        }
    }
    
    public static ObservableList<String> readTitles(Games originalGamesList) {
        
        Map <Integer,Character> titlesMap = new HashMap();
        
        ArrayList<String> titles = new ArrayList<>();
       
        Games sortedGameList = sortGames(originalGamesList);
        int n = sortedGameList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            String title = sortedGameList.getGame(i).getTitle();
            char first = Character.toUpperCase(title.charAt(0));
            if(!titlesMap.containsValue(first)) titlesMap.put(i, first);
        }
        
        String text; 
        
        for (int i = 0; i < n; i++) {
            if(titlesMap.containsKey(i)) {
                text = new String() + titlesMap.get(i);
                titles.add(text);
            }
        }
        
        return FXCollections.observableList(titles);
    } 
    
    public static ObservableList<String> readPlatforms(Platforms platforms) {
        
        
        ArrayList<String> platformNames = new ArrayList<>();
        
        for (int i = 0; i < platforms.getNumPlatforms(); i++) {
            platformNames.add(platforms.getPlatform(i).getName());
        }
        
        Collections.sort(platformNames);
        
        return FXCollections.observableList(platformNames);
    }
    
    public static ObservableList<String> readGenres(Games originalGamesList) {
        
        Map <Integer,String> genresMap = new HashMap();
        
        ArrayList<String> genres = new ArrayList<>();
       
        Games sortedGameList = sortGames(originalGamesList);
        int n = sortedGameList.getNumGames();
        String platform;
        
        for (int i = 0; i < n; i++) {
            platform = sortedGameList.getGame(i).getGenre();
            if(!genresMap.containsValue(platform)) genresMap.put(i, platform);
        }
        
        String text; 
        
        for (int i = 0; i < n; i++) {
            if(genresMap.containsKey(i)) {
                text = genresMap.get(i);
                genres.add(text);
            }
        }
        
        Collections.sort(genres);
        
        return FXCollections.observableList(genres);
    }
    
    public static ObservableList<String> readPublishers(Games originalGamesList) {
        
        Map <Integer,String> publisherMap = new HashMap();
        
        ArrayList<String> publishers = new ArrayList<>();
       
        Games sortedGameList = sortGames(originalGamesList);
        int n = sortedGameList.getNumGames();
        String platform;
        
        for (int i = 0; i < n; i++) {
            platform = sortedGameList.getGame(i).getPublisher();
            if(!publisherMap.containsValue(platform)) publisherMap.put(i, platform);
        }
        
        String text; 
        
        for (int i = 0; i < n; i++) {
            if(publisherMap.containsKey(i)) {
                text = publisherMap.get(i);
                publishers.add(text);
            }
        }
        
        Collections.sort(publishers);
        
        return FXCollections.observableList(publishers);
    }
    
    public static Games titleSelection(Games originalGamesList, String selection) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games specificTitle = new Games();

        int n = sortedGamesList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            char firstLetter = Character.toUpperCase(sortedGamesList.getGame(i).getTitle().charAt(0));
            String first = new String() + firstLetter;
            if(first.equals(selection)) specificTitle.addGame(sortedGamesList.getGame(i));
        }
        
        return specificTitle;
    }
    
    public static Games platformSelection(Games originalGamesList, String selection) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games specificPlatform = new Games();

        int n = sortedGamesList.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = sortedGamesList.getGame(i).getPlatform();
            if(first.equals(selection)) specificPlatform.addGame(sortedGamesList.getGame(i));
        }
        
        return specificPlatform;
    }
    
    public static Games genreSelection(Games originalGamesList, String selection) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games specificGenre = new Games();

        int n = sortedGamesList.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = sortedGamesList.getGame(i).getGenre();
            if(first.equals(selection)) specificGenre.addGame(sortedGamesList.getGame(i));
        }
        
        return specificGenre;
    }
    
    public static Games publisherSelection(Games originalGamesList, String selection) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games specificPublisher = new Games();

        int n = sortedGamesList.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = sortedGamesList.getGame(i).getPublisher();
            if(first.equals(selection)) specificPublisher.addGame(sortedGamesList.getGame(i));
        }
        
        return specificPublisher;
    }
    
    public static Games unplayedGames (Games originalGamesList) {
        
        String selection = "No";
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games unplayedGames = new Games();

        int n = sortedGamesList.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = sortedGamesList.getGame(i).getStarted();
            if(first.equals(selection)) unplayedGames.addGame(sortedGamesList.getGame(i));
        }
        
        return unplayedGames;
    }
    
    public static Games completedGames (Games originalGamesList) {
        
        String selection = "Yes";
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games completedGames = new Games();

        int n = sortedGamesList.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = sortedGamesList.getGame(i).getCompleted();
            if(first.equals(selection)) completedGames.addGame(sortedGamesList.getGame(i));
        }
        
        return completedGames;
    }
    
    public static Games incompleteGames (Games originalGamesList) {
        
        String selection = "No";
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games incompleteGames = new Games();

        int n = sortedGamesList.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = sortedGamesList.getGame(i).getCompleted();
            if(first.equals(selection)) incompleteGames.addGame(sortedGamesList.getGame(i));
        }
        
        return incompleteGames;
    }


}
