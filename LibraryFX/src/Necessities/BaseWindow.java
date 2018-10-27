package Necessities;

import java.io.File;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import library.Games;
import static library.LibraryIO.readGames;
import static library.LibraryIO.readPlatforms;
import library.Platforms;

public abstract class BaseWindow extends StackPane {

    public Games games = readGames("resources/files/games.txt");
    public Platforms platforms = readPlatforms("resources/files/platforms.txt");
    
    public final String fontsPath = "resources/fonts/";
    public String imagesPath;
    
    public ImagePane background;
    
    public Stage dialogStage;
    public Scene dialogScene;
        
    public final double width = 1200;
    public final double height = 750;
    
    public final int cbWidth = 160;
    public final int cbHeight = 124;
    public final int cornerSize = 30;
    
    public final int cbWidth2 = 130;
    public final int cbHeight2 = 94;
    public final int cornerSize2 = 20;
    
    public final File clapBoardSound = new File("resources/sounds/clapboard.mp3");;
    public MediaPlayer clapBoardPlayer;
 
    /* METHODS */
    
    public abstract void createBackground();
    
}
