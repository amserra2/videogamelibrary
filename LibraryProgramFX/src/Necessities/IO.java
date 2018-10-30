package Necessities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import libraryprogram.Game;
import libraryprogram.Games;
import libraryprogram.Platforms;

public class IO {

    public static ArrayList<Game> searchGames(Games games, String selection) {
        
        games.sortGames();
        ArrayList<Game> searchResults = new ArrayList<>();

        int n = games.getNumGames();
        int x; 
        int y; 
        
        String subString;
        String storeString; 
        Game game;
        
        for (int i = 0; i < n; i++) {
            game = games.getGame(i);
            storeString = game.searchString();
            x = (storeString.length() - selection.length()) + 1;
            y = selection.length();
            for (int j = 0; j < x; j++) {
                subString = storeString.substring(j, y + j);
                if (subString.equals(selection)) {
                    searchResults.add(game);
                    break;
                }
            }
        }
        
        return searchResults;
    }
    
    public static void downloadGames(Games games) {
        
        games.sortGames();
        
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

            PrintWriter pW = new PrintWriter(new File("/Users/asia/Desktop/" + "games_download_" + dateS + ".txt"));
            pW.print(games.downloadString());
            pW.close();
        } 
        catch (FileNotFoundException ex) {
            System.err.println("An error has occured with the download.");
            System.exit(-1);
        }
    }
    
    public static ObservableList<String> readTitles(Games games) {
        
        Map <Integer,Character> titlesMap = new HashMap();
        
        ArrayList<String> titles = new ArrayList<>();
       
        games.sortGames();
        int n = games.getNumGames();
        
        for (int i = 0; i < n; i++) {
            String title = games.getGame(i).getTitle();
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
    
    public static ObservableList<String> readGenres(Games games) {
        
        Map <Integer,String> genresMap = new HashMap();
        
        ArrayList<String> genres = new ArrayList<>();
       
        games.sortGames();
        int n = games.getNumGames();
        String platform;
        
        for (int i = 0; i < n; i++) {
            platform = games.getGame(i).getGenre();
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
 
    public static ObservableList<String> readGenerations(Platforms platforms) {
        
        Map <Integer,String> generationsMap = new HashMap();
        
        ArrayList<String> generations = new ArrayList<>();
       
        int n = platforms.getNumPlatforms();
        String generation;
        
        for (int i = 0; i < n; i++) {
            generation = platforms.getPlatform(i).getGeneration();
            if(!generationsMap.containsValue(generation)) generationsMap.put(i, generation);
        }
        
        String text; 
        
        for (int i = 0; i < n; i++) {
            if(generationsMap.containsKey(i)) {
                text = generationsMap.get(i);
                generations.add(text);
            }
        }
        
        Collections.sort(generations);
        
        return FXCollections.observableList(generations);
    }
    
    public static ObservableList<String> readPublishers(Games games) {
        
        Map <Integer,String> publisherMap = new HashMap();
        
        ArrayList<String> publishers = new ArrayList<>();
       
        games.sortGames();
        int n = games.getNumGames();
        String platform;
        
        for (int i = 0; i < n; i++) {
            platform = games.getGame(i).getPublisher();
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
    
    public static ArrayList<Game> titleSelection(Games games, String selection) {
        
        games.sortGames();
        ArrayList<Game> specificTitle = new ArrayList<>();

        int n = games.getNumGames();
        
        for (int i = 0; i < n; i++) {
            char firstLetter = Character.toUpperCase(games.getGame(i).getTitle().charAt(0));
            String first = new String() + firstLetter;
            if(first.equals(selection)) specificTitle.add(games.getGame(i));
        }
        
        return specificTitle;
    }
    
    public static ArrayList<Game> platformSelection(Games games, String selection) {
        
        games.sortGames();
        ArrayList<Game> specificPlatform = new ArrayList<>();

        int n = games.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = games.getGame(i).getPlatform();
            if(first.equals(selection)) specificPlatform.add(games.getGame(i));
        }
        
        return specificPlatform;
    }
    
    public static ArrayList<Game> genreSelection(Games games, String selection) {
        
        games.sortGames();
        ArrayList<Game> specificGenre = new ArrayList<>();

        int n = games.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = games.getGame(i).getGenre();
            if(first.equals(selection)) specificGenre.add(games.getGame(i));
        }
        
        return specificGenre;
    }
    
    public static ArrayList<Game> publisherSelection(Games games, String selection) {
        
        games.sortGames();
        ArrayList<Game> specificPublisher = new ArrayList<>();

        int n = games.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = games.getGame(i).getPublisher();
            if(first.equals(selection)) specificPublisher.add(games.getGame(i));
        }
        
        return specificPublisher;
    }
    
    public static ArrayList<Game> unplayedGames (Games games) {
        
        String selection = "No";
        
        games.sortGames();
        ArrayList<Game> unplayedGames = new ArrayList<>();

        int n = games.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = games.getGame(i).getStarted();
            if(first.equals(selection)) unplayedGames.add(games.getGame(i));
        }
        
        return unplayedGames;
    }
    
    public static ArrayList<Game> completedGames (Games games) {
        
        String selection = "Yes";
        
        games.sortGames();
        ArrayList<Game> completedGames = new ArrayList<>();

        int n = games.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = games.getGame(i).getCompleted();
            if(first.equals(selection)) completedGames.add(games.getGame(i));
        }
        
        return completedGames;
    }
    
    public static ArrayList<Game> incompleteGames (Games games) {
        
        String selection = "No";
        
        games.sortGames();
        ArrayList<Game> incompleteGames = new ArrayList<>();

        int n = games.getNumGames();
        
        String first; 
        
        for (int i = 0; i < n; i++) {
            first = games.getGame(i).getCompleted();
            if(first.equals(selection)) incompleteGames.add(games.getGame(i));
        }
        
        return incompleteGames;
    }
    
    public static int newPlatformNumber(Platforms platforms) {
        int n = platforms.getNumPlatforms();
        
        ArrayList<Integer> ids = new ArrayList<>();
        
        for (int i = 0; i < n; i++) ids.add(platforms.getPlatform(i).getID());

        for (int i = 1; i < n + 1; i++) if (!ids.contains(i * 100)) return i * 100;
        
        return (platforms.getNumPlatforms() + 1) * 100;
    }

}
