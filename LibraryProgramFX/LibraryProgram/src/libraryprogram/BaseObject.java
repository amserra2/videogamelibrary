package libraryprogram;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class BaseObject  {
    public Date release;
    public String developer;
    public int ID;
    public final String imagesPath = "/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/images";
    public final DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);;
    
    protected BaseObject() {
        release = new Date();
        developer = "Developer";
        ID = 0;
    }
    
    protected BaseObject(Date rel, String dev, int id) {
        release = rel;
        developer = dev;
        ID = id;
    }

    public String getRelease() {
        return dateFormat.format(release);
    }

    public void setRelease(Date date) {
        release = date;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
