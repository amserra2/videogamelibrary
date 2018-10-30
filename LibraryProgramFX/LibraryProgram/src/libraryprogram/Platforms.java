package libraryprogram;

import java.io.File;
import java.util.ArrayList;
import static libraryprogram.LibraryIO.readPlatforms;

public class Platforms {
    private final String filename;
    private File file; 
    private Platform selectedPlatform;
    private ArrayList<Platform> platforms; 
    
    public Platforms() {
        filename = "/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/files/platforms.txt";
        file = new File(filename);
        platforms = readPlatforms(file);
    }
        
    public String storeString() {
        
        String str = new String();
        
        for (int i = 0; i < platforms.size(); i++) str += (platforms.get(i).storeString()) + System.lineSeparator();
        return str; 
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<Platform> platforms) {
        this.platforms = platforms;
    }

    public int getNumPlatforms() {
        return platforms.size();
    }
    
    public Platform getPlatform(int index) {
        return platforms.get(index);
    }
    
    public void setPlatform(int index, Platform platform) {
        platforms.set(index, platform);
    }

    public void addPlatform(Platform platform) {
        platforms.add(platform);
    }
    
    public Platform deletePlatform(int index) {
        Platform platform = platforms.get(index);
        platforms.remove(index); 
        return platform; 
    }

    public String getFilename() {
        return filename;
    }
    
    public Platform getSelectedPlatform() {
        return selectedPlatform;
    }

    public void setSelectedPlatform(Platform selectedPlatform) {
        this.selectedPlatform = selectedPlatform;
    }

}

