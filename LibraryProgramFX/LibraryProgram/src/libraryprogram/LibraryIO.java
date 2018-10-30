package libraryprogram;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryIO {
    
    public static ArrayList<Game> readGames(File file) {
        ArrayList<Game> games = new ArrayList<>();
        
        try {
            Scanner input = new Scanner(file);
            String a;
            String b;
            String c; 
            
            while(input.hasNext()) {
                a = input.nextLine();
                b = input.nextLine();
                c = input.nextLine();
                
                Game game = readGame(a, b, c);
                games.add(game);
              
            }   
        } 
        
        catch (FileNotFoundException ex) {
            System.err.println("Games file not found for reading.");
            System.exit(-1); 
        }  
        
       return games; 
    } 
    
    public static ArrayList<Platform> readPlatforms(File file) {
        ArrayList<Platform> platforms = new ArrayList<>();
        
        try {
            Scanner input = new Scanner(file);
            String a;
            String b;
            String c; 
            String d;
            String e; 
            
            Platform platform;
            while(input.hasNext()) {
                
                a = input.nextLine();
                b = input.nextLine();
                c = input.nextLine();
                d = input.nextLine().trim();
                e = input.nextLine().trim();
                
                platform = readPlatform(a, b, c);
                if (!d.equals("empty")) platform.setUsedIDs(readUsedIDs(d));
                if (!e.equals("empty")) platform.setUnusedIDs(readUnusedIDs(e));
                
                platforms.add(platform);
                
            }   
        } 
        
        catch (FileNotFoundException ex) {
            System.err.println("Platforms file not found for reading.");
            System.exit(-1); 
        }  
        
       return platforms; 
    } 

    public static void writeGames(Games games) {
        
        try {
            PrintWriter pW = new PrintWriter(games.getFile());
                pW.print(games.storeString());
                pW.close(); 
        }
        
        catch(FileNotFoundException ex) {
            System.err.println("Games file not found for writing.");
            System.exit(-1);
        }
    }
    
    public static void writePlatforms(Platforms platforms) {
        try {
            PrintWriter pW = new PrintWriter(platforms.getFilename());
                pW.print(platforms.storeString());
                pW.close(); 
        }
        
        catch(FileNotFoundException ex) {
            System.err.println("Platforms file not found for writing.");
            System.exit(-1);
        }
    }
    
    public static Game readGame(String a, String b, String c) {
        Game game = new Game();
        String line1[] = a.split("\\|");
        String line2[] = b.split("\\|");
        String line3[] = c.split("\\|");

            game.setTitle(line1[0]);
            try {
                game.setRelease(game.dateFormat.parse(line1[1]));
            } 
            catch (ParseException ex) {
                System.err.print("Game date format incorrect.");
                System.exit(-1);
            }
            
            game.setPublisher(line2[0]);
            game.setDeveloper(line2[1]);
            game.setGenre(line2[2]);
            
            game.setPlatform(line3[0]);
            game.setID(Integer.parseInt(line3[1]));
            game.setStatus(line3[3]);
        
        return game;
    }
    
    public static Platform readPlatform(String a, String b, String c) {
        Platform platform = new Platform();
        String line1[] = a.split("\\|");
        String line2[] = b.split("\\|");
        String line3[] = c.split("\\|");

            platform.setName(line1[0]);
            try {
                platform.setRelease(platform.dateFormat.parse(line1[1]));
            } 
            catch (ParseException ex) {
                System.err.print("Platform date format incorrect.");
                System.exit(-1);
            }
            
            platform.setDeveloper(line2[0]);
            platform.setManufacturer(line2[1]);
            
            platform.setGeneration(line3[0]);  
            platform.setID(Integer.parseInt(line3[1]));
            platform.setType(line3[2]);

            if (line3[3].trim().equals("True")) platform.setOwn(true);
            else platform.setOwn(false);

        return platform;
    }
    
    public static ArrayList<Integer> readUsedIDs(String d) {
        
        ArrayList<Integer> used = new ArrayList<>();
        String u[] = d.trim().split(" ");

        for (int i = 0; i < u.length; i++) used.add(Integer.parseInt(u[i]));
        return used;
        
    }
    
    public static ArrayList<Integer> readUnusedIDs(String e) {
        
        ArrayList<Integer> unused = new ArrayList<>();
        String u[] = e.trim().split(" ");
        
        for (int i = 0; i < u.length; i++) unused.add(Integer.parseInt(u[i]));
        return unused;
        
    }
    
}
