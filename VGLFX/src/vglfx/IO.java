package vglfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vgl.Game;
import vgl.Games;
import vgl.Platform;
import vgl.Platforms;

public class IO {
    
    public static Games sortGames(Games orignalGamesList) {
        
        Games sortedGamesList = new Games();
        
        Map <String, Game> map = new HashMap();
        
        int n = orignalGamesList.getNumGames();
        String[] strings = new String[n];
        
        for (int i = 0; i < n; i++) {
            strings[i] = orignalGamesList.getGame(i).getTitle().replaceAll("\\s+","").toLowerCase().trim();
            map.put(strings[i], orignalGamesList.getGame(i));
        }        
        
        Arrays.sort(strings);
        
        for (int i = 0; i < n; i++) {
            sortedGamesList.addGame(map.get(strings[i]));
        }
        
       return sortedGamesList; 
    }
    
    public static Platforms sortPlatforms(Platforms orignalPlatformsList) {
        
        Platforms sortedPlatformsList = new Platforms();
        
        Map <String, Platform> map = new HashMap();
        
        int n = orignalPlatformsList.getNumPlatforms();
        String[] strings = new String[n];
        
        for (int i = 0; i < n; i++) {
            strings[i] = orignalPlatformsList.getPlatform(i).getName().replaceAll("\\s+","").toLowerCase().trim();
            map.put(strings[i], orignalPlatformsList.getPlatform(i));
        }        
        
        Arrays.sort(strings);
        
        for (int i = 0; i < n; i++) {
            sortedPlatformsList.addPlatform(map.get(strings[i]));
        }
        
       return sortedPlatformsList; 
    }
    
    public static Games searchGames(Games originalGamesList, String selection) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games searchResults = new Games();

        int n = sortedGamesList.getNumGames();
        int x; 
        int y; 
        
        String subString;
        String storeString; 
        Game game;
        
        for (int i = 0; i < n; i++) {
            game = sortedGamesList.getGame(i);
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
    
    public static Platforms searchPlatforms(Platforms originalPlatformsList, String selection) {
        
        Platforms sortedPlatformsList = sortPlatforms(originalPlatformsList);
        Platforms searchResults = new Platforms();

        int n = sortedPlatformsList.getNumPlatforms();
        int x; 
        int y; 
        
        String subString;
        String storeString; 
        Platform platform;
        
        for (int i = 0; i < n; i++) {
            platform = sortedPlatformsList.getPlatform(i);
            storeString = platform.searchString();
            x = (storeString.length() - selection.length()) + 1;
            y = selection.length();
            for (int j = 0; j < x; j++) {
                subString = storeString.substring(j, y + j);
                if (subString.equals(selection)) {
                    searchResults.addPlatform(platform);
                    break;
                }
            }
        }
        
        return searchResults;
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
        
        for (int i = 0; i < n; i++) if(titlesMap.containsKey(i)) titles.add(new String() + titlesMap.get(i));
        
        return FXCollections.observableList(titles);
    } 
    
    public static ObservableList<String> readNames(Platforms originalPlatformsList) {
        
        Map <Integer,Character> namesMap = new HashMap();
        ArrayList<String> names = new ArrayList<>();
       
        Platforms sortedPlatformList = sortPlatforms(originalPlatformsList);
        int n = sortedPlatformList.getNumPlatforms();
        
        for (int i = 0; i < n; i++) {
            String name = sortedPlatformList.getPlatform(i).getName();
            char first = Character.toUpperCase(name.charAt(0));
            if(!namesMap.containsValue(first)) namesMap.put(i, first);
        }
        
        for (int i = 0; i < n; i++) if(namesMap.containsKey(i)) names.add(new String() + namesMap.get(i));
        
        return FXCollections.observableList(names);
    } 
    
    public static Games specificTitle(Games originalGamesList, String selection) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games specificTitleList = new Games();

        int n = sortedGamesList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            char firstLetter = Character.toUpperCase(sortedGamesList.getGame(i).getTitle().charAt(0));
            String first = new String() + firstLetter;
            if(first.equals(selection)) specificTitleList.addGame(sortedGamesList.getGame(i));
        }
        
        return specificTitleList;
    }
    
    public static ObservableList<String> readTypes(Platforms originalPlatformsList) {
        
        Map <Integer,String> typesMap = new HashMap();
        ArrayList<String> types = new ArrayList<>();
       
        Platforms sortedPlatformList = sortPlatforms(originalPlatformsList);
        int n = sortedPlatformList.getNumPlatforms();
        
        for (int i = 0; i < n; i++) {
            String type = sortedPlatformList.getPlatform(i).getType();
            if(!typesMap.containsValue(type)) typesMap.put(i, type);
        }
        
        for (int i = 0; i < n; i++) if(typesMap.containsKey(i)) types.add(new String() + typesMap.get(i));
        
        return FXCollections.observableList(types);
    } 
    
    public static Platforms specificType(Platforms originalPlatformsList, String selection) {
        
        Platforms sortedPlatformsList = sortPlatforms(originalPlatformsList);
        Platforms specificTypeList = new Platforms();

        int n = sortedPlatformsList.getNumPlatforms();
        
        for (int i = 0; i < n; i++) {
            String type = sortedPlatformsList.getPlatform(i).getType();
            if(type.equals(selection)) specificTypeList.addPlatform(sortedPlatformsList.getPlatform(i));
        }
        
        return specificTypeList;
    }
    
    public static ObservableList<String> readGenerations(Platforms originalPlatformsList) {
        
        Map <Integer,String> generationsMap = new HashMap();
        ArrayList<String> generations = new ArrayList<>();
       
        Platforms sortedPlatformList = sortPlatforms(originalPlatformsList);
        int n = sortedPlatformList.getNumPlatforms();
        
        for (int i = 0; i < n; i++) {
            String generation = sortedPlatformList.getPlatform(i).getGeneration();
            if(!generation.equals("N/A")){
                if(!generationsMap.containsValue(generation)) generationsMap.put(i, generation);
            }
        }
        
        for (int i = 0; i < n; i++) if(generationsMap.containsKey(i)) generations.add(new String() + generationsMap.get(i));
        
        return FXCollections.observableList(generations);
    } 
    
    public static Platforms specificGeneration(Platforms originalPlatformsList, String selection) {
        
        Platforms sortedPlatformsList = sortPlatforms(originalPlatformsList);
        Platforms specificGenerationList = new Platforms();

        int n = sortedPlatformsList.getNumPlatforms();
        
        for (int i = 0; i < n; i++) {
            String generation = sortedPlatformsList.getPlatform(i).getGeneration();
            if(generation.equals(selection)) specificGenerationList.addPlatform(sortedPlatformsList.getPlatform(i));
        }
        
        return specificGenerationList;
    }
    
    public static Platforms specificName(Platforms originalPlatformsList, String selection) {
        
        Platforms sortedPlatformsList = sortPlatforms(originalPlatformsList);
        Platforms specificNameList = new Platforms();

        int n = sortedPlatformsList.getNumPlatforms();
        
        for (int i = 0; i < n; i++) {
            char firstLetter = Character.toUpperCase(sortedPlatformsList.getPlatform(i).getName().charAt(0));
            String first = new String() + firstLetter;
            if(first.equals(selection)) specificNameList.addPlatform(sortedPlatformsList.getPlatform(i));
        }
        
        return specificNameList;
    }
    
    public static ObservableList<String> readPlatforms(Games originalGamesList) {
        
        Map <Integer,String> platformMap = new HashMap();
        ArrayList<String> platforms = new ArrayList<>();
       
        Games sortedGameList = sortGames(originalGamesList);
        int n = sortedGameList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            String platform = sortedGameList.getGame(i).getPlatform();
            if(!platformMap.containsValue(platform)) platformMap.put(i, platform);
        }
        
        
        
        for (int i = 0; i < n; i++) if(platformMap.containsKey(i)) platforms.add(platformMap.get(i));
        
        int x = platforms.size();
        
        String[] array = new String[x];
        
        for (int i = 0; i < x; i++) array[i] = platforms.get(i);
        
        Arrays.sort(array);
        
        ArrayList<String> sortedPlatforms = new ArrayList<>();
        
        for (int i = 0; i < x; i++) sortedPlatforms.add(array[i]);
        
        return FXCollections.observableList(sortedPlatforms);
    } 
    
    public static Games specificPlatform (Games originalGamesList, String selection) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games specificPlatformList = new Games();

        int n = sortedGamesList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            String platform = sortedGamesList.getGame(i).getPlatform();
            if(platform.equals(selection)) specificPlatformList.addGame(sortedGamesList.getGame(i));
        }
        
        return specificPlatformList;
    }
    
    public static ObservableList<String> readGenres(Games originalGamesList) {
        
        Map <Integer,String> genreMap = new HashMap();
        ArrayList<String> genres = new ArrayList<>();
       
        Games sortedGameList = sortGames(originalGamesList);
        int n = sortedGameList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            String genre = sortedGameList.getGame(i).getGenre();
            if(!genreMap.containsValue(genre)) genreMap.put(i, genre);
        }
        
        for (int i = 0; i < n; i++) if(genreMap.containsKey(i)) genres.add(genreMap.get(i));
        
        int x = genres.size();
        
        String[] array = new String[x];
        
        for (int i = 0; i < x; i++) array[i] = genres.get(i);
        
        Arrays.sort(array);
        
        ArrayList<String> sortedGenres = new ArrayList<>();
        
        for (int i = 0; i < x; i++) sortedGenres.add(array[i]);
        
        return FXCollections.observableList(sortedGenres);
    } 
    
    public static Games specificGenre (Games originalGamesList, String selection) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games specificGenreList = new Games();

        int n = sortedGamesList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            String genre = sortedGamesList.getGame(i).getGenre();
            if(genre.equals(selection)) specificGenreList.addGame(sortedGamesList.getGame(i));
        }
        
        return specificGenreList;
    }
    
    public static ObservableList<String> readPublishers(Games originalGamesList) {
        
        Map <Integer,String> publisherMap = new HashMap();
        ArrayList<String> publishers = new ArrayList<>();
       
        Games sortedGameList = sortGames(originalGamesList);
        int n = sortedGameList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            String publisher = sortedGameList.getGame(i).getPublisher();
            if(!publisherMap.containsValue(publisher)) publisherMap.put(i, publisher);
        }
        
        for (int i = 0; i < n; i++) if(publisherMap.containsKey(i)) publishers.add(new String() + publisherMap.get(i));
        
        int x = publishers.size();
        
        String[] array = new String[x];
        
        for (int i = 0; i < x; i++) array[i] = publishers.get(i);
        
        Arrays.sort(array);
        
        ArrayList<String> sortedPublishers = new ArrayList<>();
        
        for (int i = 0; i < x; i++) sortedPublishers.add(array[i]);
        
        return FXCollections.observableList(sortedPublishers);
    } 
    
    public static Games specificPublisher (Games originalGamesList, String selection) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games specificPublisherList = new Games();

        int n = sortedGamesList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            String genre = sortedGamesList.getGame(i).getPublisher();
            if(genre.equals(selection)) specificPublisherList.addGame(sortedGamesList.getGame(i));
        }
        
        return specificPublisherList;
    }

    public static Games unplayedGames (Games originalGamesList) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games uplayedGamesList = new Games();

        int n = sortedGamesList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            String played = sortedGamesList.getGame(i).getStarted();
            if(played.equals("No")) uplayedGamesList.addGame(sortedGamesList.getGame(i));
        }
        
        return uplayedGamesList;
    }
    
    public static Games incompleteGames (Games originalGamesList) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games incompleteGamesList = new Games();

        int n = sortedGamesList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            String completed = sortedGamesList.getGame(i).getCompleted();
            if(completed.equals("No")) incompleteGamesList.addGame(sortedGamesList.getGame(i));
        }
        
        return incompleteGamesList;
    }
    
    public static Games completedGames (Games originalGamesList) {
        
        Games sortedGamesList = sortGames(originalGamesList);
        Games completedGamesList = new Games();

        int n = sortedGamesList.getNumGames();
        
        for (int i = 0; i < n; i++) {
            String completed = sortedGamesList.getGame(i).getCompleted();
            if(completed.equals("Yes")) completedGamesList.addGame(sortedGamesList.getGame(i));
        }
        
        return completedGamesList;
    }
    
    public static int createGameID (Games games, Platforms platforms, String platform) {
        
        int id;

            if(platforms.getPlatformMap().containsKey(platform)) {
                int platformID = platforms.getPlatformIDs().get(platform);
                id = nextAvailablePlatformNumber(games, platform, platformID);
                return id;
            }
            
            else return -1;
    }
    
    public static int nextAvailablePlatformNumber(Games games, String platform, int platformID) {
        
        Games specifiedPlatform = specificPlatform(games, platform);

        int n = specifiedPlatform.getNumGames();
        
        int gameID;

        for (int i = 0; i < n; i++) {
            gameID = (platformID * 1000) + i;
            if (!games.getGameIDs().containsValue(gameID)) return gameID;
        }
        
        return (platformID * 1000) + n;
    }
    
    public static void printGames(Games games) {
        
        Games sortedGames = sortGames(games);
        
        String list = new String();
        int n = sortedGames.getNumGames();
        
        for (int i = 0; i < n; i++) {
            list += (i + 1) + ". " + sortedGames.getGame(i).getTitle();
            if (i != n - 1) list += "\n";
        }
        
        try {
            
            Date date = new Date();

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
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
                
                String x;
                
                if (hour < 12) x = "AM";
                else x = "PM";
                
                //yyyymmddhhmmss
                String dateS = yearForm + monForm + dayForm + "_" + hourForm + minForm + secForm + x;

            
            String s = "games_download_" + dateS + ".txt";
        
            PrintWriter pW = new PrintWriter(new File("/Users/asia/Desktop/" + s));
            pW.print(list);
            pW.close();
        } 
        catch (FileNotFoundException ex) {
            
        }
    }
    
    public static void printPlatforms(Platforms platforms) {
        
        Platforms sortedPlatforms = sortPlatforms(platforms);
        
        String list = new String();
        int n = sortedPlatforms.getNumPlatforms();
        
        for (int i = 0; i < n; i++) {
            list += (i + 1) + ". " + sortedPlatforms.getPlatform(i).getName();
            if (i != n - 1) list += "\n";
        }
        
        try {
            
            Date date = new Date();

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
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
                
                String x;
                
                if (hour < 12) x = "AM";
                else x = "PM";
                
                //yyyymmddhhmmss
                String dateS = yearForm + monForm + dayForm + "_" + hourForm + minForm + secForm + x;

            
            String s = "platforms_download_" + dateS + ".txt";
        
            PrintWriter pW = new PrintWriter(new File("/Users/asia/Desktop/" + s));
            pW.print(list);
            pW.close();
        } 
        catch (FileNotFoundException ex) {
            
        }
    }
    
    public static int createNewPlatfomID(Platforms platforms) {
        
        int id; 
        
        if(platforms.getPlatformMap().isEmpty()) return 100;
                
        else {
            
            id = 100;
            
                while (true) {
                    if (!platforms.getPlatformIDs().containsValue(id)) return id;
                    else id += 100;
                }
            }
    }
    
}
