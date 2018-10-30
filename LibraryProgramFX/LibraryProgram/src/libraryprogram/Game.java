package libraryprogram;

import java.util.Date;

public class Game extends BaseObject {
    private String title; 
    private String platform;
    private String publisher;
    private String genre;
    private String started;
    private String completed;
    private String status;
    
    public Game () {
        super();
        title = "Title";
        platform = "Platform";
        publisher = "Publisher";
        genre = "Genre";
        started = "Started";
        completed = "Completed";
        status = started + "/" + completed;

    }
    
    public Game (String t, Date r, String pL, String pB, String d, String g, String i, int id, String s, String c) {
        super(r, d,id);
        title = t;
        platform = pL;
        publisher = pB;
        genre = g;
        started = s;
        completed = c;
        ID = id;
        status = started + "/" + completed;
        
    }
    
    @Override
    public String toString() {
        String divider = " | ";
        String line1 = title + System.lineSeparator();
        String line2 = dateFormat.format(release) + System.lineSeparator();
        String line3 = publisher + divider + developer + divider + genre + System.lineSeparator();
        String line4 = platform + divider + ID + divider + status + System.lineSeparator();

        return line1 + line2 + line3 + line4;
    }
    
    public String downloadString() {
        return title + " (" + platform + ")";  
    }
    
 
    public String listString() {
        
        return "Title: " + title + System.lineSeparator() +
                "Released: " + dateFormat.format(release) + System.lineSeparator() +
                "Publisher: " + publisher + System.lineSeparator() +
                "Developer: " + developer + System.lineSeparator() +
                "Genre: " + genre + System.lineSeparator() +
                "Platform: " + platform + System.lineSeparator() +
                "ID Number: " + ID + System.lineSeparator() +
                "Started/Completed: " + status + System.lineSeparator();
    }
    
    public String searchString() {
        
        String[] date = dateFormat.format(release).replaceAll(",", "").split(" ");
        //return (title + date[2] + date[0] + date[1] + publisher + developer + genre + platform + ID).replaceAll("\\s+","").replaceAll("[^a-zA-Z0-9]","").toLowerCase();
        
        return (title + date[2] + date[0] + date[1] + publisher + developer + genre + platform + ID).replaceAll("\\s+","").replaceAll("[^a-zA-Z0-9]","").toLowerCase();
    }
    
    public String storeString() {
        String divider = "|";
        
        return title + divider + dateFormat.format(release) + System.lineSeparator() +
                publisher + divider + developer + divider + genre + System.lineSeparator() +
                platform + divider + ID + divider + ID + ".png" + divider + status; 
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
        updateStatus();
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
        updateStatus();
    }

    public String getStatus() {
        return status;
    }
    
    public void updateStatus() {
        status = started + "/" + completed;
    }

    public void setStatus(String status) {
        this.status = status;
        
        String[] line = status.split("/");
            started = line[0];
            completed = line[1];  
    }
    
    public String getPath() {
        return super.imagesPath + "/games/" + ID + ".png";
    }

}
