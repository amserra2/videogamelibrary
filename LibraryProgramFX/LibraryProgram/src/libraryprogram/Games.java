package libraryprogram;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static libraryprogram.LibraryIO.readGames;

public class Games {
    private final String filename;
    private File file; 
    private Game selectedGame;
    private ArrayList<Game> games; 
    
    public Games() {
        filename = "/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/files/games.txt"; 
        file = new File(filename);
        games = readGames(file);
    }
    
    public String storeString() {
        String str = new String();
        
        for (int i = 0; i < games.size(); i++) {
            str += (games.get(i)).storeString() + System.lineSeparator();
        }
        
        return str; 
    }
    
    public String downloadString() {
        String str = new String();
        
        for (int i = 0; i < games.size(); i++) str += (games.get(i)).downloadString() + System.lineSeparator();
        
        return str;
    }
    
    public int getNumGames() {
        return games.size();
    }
    
    public Game getGame(int index) {
        return games.get(index);
    }
    
    public void setGame(int index, Game game) {
        games.add(index, game);
    }

    public void addGame(Game game) {
        games.add(game);
    }
    
    public Game deleteGame(int index) {
        Game game = games.get(index);
        games.remove(index); 
        return game; 
    }

    public String getFilename() {
        return filename;
    }

    public Game getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGame(Game selectedGame) {
        this.selectedGame = selectedGame;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ArrayList<Game> getGames() {
        return games;
    }
    
    public void sortGames() {
 
        Map <String, Game> map = new HashMap();
        
        int n = games.size();
        String[] strings = new String[n];
        
        for (int i = 0; i < n; i++) {
            strings[i] = games.get(i).getTitle().replaceAll("\\s+","").toLowerCase().trim();
            map.put(strings[i], games.get(i));
        }        
        
        Arrays.sort(strings);
        
        games.clear();
        
        for (int i = 0; i < n; i++) games.add(map.get(strings[i]));
    }
    
    public void updateGamesPlatform(String oldName, String newName) {
        
        int n = games.size();
        Game g; 
        
        for (int i = 0; i < n; i++) {
            g = games.get(i);
            if (g.getPlatform().equals(oldName)) g.setPlatform(newName);
        }
    }
  
}

