package libraryprogramfx;

import MainMenu.MainMenu;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;


public class LibraryProgramFX extends Application {
    
    public static MediaPlayer backgroundMediaPlayer;
    public static Media backgroundSound;
    public static File musicFile;
    
    @Override
    public void start(Stage primaryStage) {
        
        createSounds();
        
        MainMenu mm = new MainMenu();
        Scene scene = new Scene(mm, 1200, 750);
                
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }
    
    public static void createSounds() {
        musicFile = new File("/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/sounds/supermariomaker.mp3");
        backgroundSound = new Media(musicFile.toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundSound);
        
        backgroundMediaPlayer.setOnEndOfMedia(() -> {
            backgroundMediaPlayer.seek(Duration.ZERO);
        });
        
        backgroundMediaPlayer.play();
    }
    
}
