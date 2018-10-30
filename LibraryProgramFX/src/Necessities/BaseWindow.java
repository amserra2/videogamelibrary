package Necessities;

import java.io.File;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import libraryprogram.Games;
import libraryprogram.Platforms;

public abstract class BaseWindow extends StackPane {

    public Games games = new Games();
    public Platforms platforms = new Platforms();
    
    public final String fontsPath = "/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/fonts/";
    public String basePath = "/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/";
    public String imagesPath;
    
    public ImagePane background;
    
    public Stage dialogStage;
    public Scene dialogScene;
    
    public Stage secondDialogStage;
    public Scene secondDialogScene;
        
    public final double width = 1200;
    public final double height = 750;
    
    public final int cbWidth = 160;
    public final int cbHeight = 124;
    public final int cornerSize = 30;
    
    public final int cbWidth2 = 130;
    public final int cbHeight2 = 94;
    public final int cornerSize2 = 20;
    
    public final File clapBoardSound = new File("/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/sounds/clapboard.mp3");;
    public MediaPlayer clapBoardPlayer;
 
    /* METHODS */
    
    public abstract void createBackground();
    
    public void closeDialogs() {
        dialogScene.getWindow().hide();
        dialogStage.getScene().getWindow().hide();
    }
    
    public void closeDialogs2() {
        secondDialogScene.getWindow().hide();
        secondDialogStage.getScene().getWindow().hide();
    }
    
}
