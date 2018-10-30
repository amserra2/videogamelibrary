package libraryprogram;

import java.util.ArrayList;
import java.util.Date;

public class Platform extends BaseObject {
    private String name; 
    private String manufacturer;
    private String generation;
    private String type;
    private boolean own; 
    
    private ArrayList<Integer> usedIDs;
    private ArrayList<Integer> unusedIDs;
    
    public Platform() {
        super();
        name = "Name";
        manufacturer = "Manufacturer";
        generation = "Generation";
        type = "Type";
        usedIDs = new ArrayList<>();
        unusedIDs = new ArrayList<>();
        own = false; 
        
    }
    
    public Platform(String n, Date r, String d, String m, String g, String i, int id, String t, ArrayList<Integer> u, ArrayList<Integer> un, boolean b) {
        super(r, d,id);
        name = n;
        manufacturer = m;
        generation = g;
        type = t;
        usedIDs = u;
        unusedIDs = un;
        own = b; 
    }
    
    @Override
     public String toString() {
        String owned;
        if (own) owned = "True";
        else owned = "False";
        
        String divider = " | ";
        String line1 = name + divider + ID +  System.lineSeparator();
        String line2 = dateFormat.format(release) + System.lineSeparator();
        String line3 = developer + divider + manufacturer + System.lineSeparator();
        String line4 = generation + divider + type + divider + owned + System.lineSeparator();

        return line1 + line2 + line3 + line4;
    }
    
    public String listString() {
        String owned;
        if (own) owned = "True";
        else owned = "False";
        
        return "Name: " + name + System.lineSeparator() +
               "Released: " + dateFormat.format(release) + System.lineSeparator() +
               "Developer: " + developer + System.lineSeparator() +
               "Manufacturer: " + manufacturer + System.lineSeparator() +
               "Generation: " + generation + System.lineSeparator() +
               "Platform ID: " + ID + System.lineSeparator() +
               "Type: " + type + System.lineSeparator() + 
               "Own: " + owned + System.lineSeparator(); 
    }
    
    public String searchString() {
        
        String[] date = dateFormat.format(release).replaceAll(",", "").split(" ");
        
        return (name + date[2] + date[0] + date[1] + developer +  manufacturer + generation + ID).replaceAll("\\s+","").replaceAll(",","").toLowerCase(); 
    }
    
    public String storeString() {
        String divider = "|";
        
        String used = new String();
        String unused  = new String(); 
        String owned;
        
        if (own) owned = "True";
        else owned = "False";
        
        if (usedIDs.isEmpty()) used = "empty";
        else for (int i = 0; i < usedIDs.size(); i++) used += usedIDs.get(i) + " ";
        
        if (unusedIDs.isEmpty()) unused = "empty";
        else for (int i = 0; i < unusedIDs.size(); i++) unused += unusedIDs.get(i) + " ";
        
        return name + divider + dateFormat.format(release) + System.lineSeparator() +
                developer + divider + manufacturer + System.lineSeparator() +
                generation + divider + ID + divider + type + divider + owned + System.lineSeparator() +
                used + System.lineSeparator() + unused;
                 
    }

    public ArrayList<Integer> getUsedIDs() {
        return usedIDs;
    }

    public void setUsedIDs(ArrayList<Integer> usedIDs) {
        this.usedIDs = usedIDs;
    }
    
    public int getNumUsedIDs() {
        return usedIDs.size();
    }
    
    public int getUsedID(int index) {
        return usedIDs.get(index);
    }
    
    public void setUsedID(int index, int ID) {
        usedIDs.set(index, ID);
    }

    public void addUsedID(int ID) {
        usedIDs.add(ID);
    }
    
    public int deleteUsedID(int index) {
        int id = usedIDs.get(index);
        usedIDs.remove(index); 
        return id; 
    }

    public ArrayList<Integer> getUnusedIDs() {
        return unusedIDs;
    }

    public void setUnusedIDs(ArrayList<Integer> unusedIDs) {
        this.unusedIDs = unusedIDs;
    }
    
    public int getNumUnusedIDs() {
        return unusedIDs.size();
    }
    
    public int getUnusedID(int index) {
        return unusedIDs.get(index);
    }
    
    public void setUnusedID(int index, int ID) {
        unusedIDs.set(index, ID);
    }

    public void addUnusedID(int ID) {
        unusedIDs.add(ID);
    }
    
    public int deleteUnusedID(int index) {
        int id = unusedIDs.get(index);
        unusedIDs.remove(index); 
        return id; 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isOwn() {
        return own;
    }

    public void setOwn(boolean own) {
        this.own = own;
    }

    public String getPath() {
        return super.imagesPath + "/platforms/" + ID + ".png";
    }

}
