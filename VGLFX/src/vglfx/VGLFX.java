package vglfx;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import vgl.Games;
import static vgl.IO.readGames;
import static vgl.IO.readPlatforms;
import vgl.Platforms;

public class VGLFX extends Application {
    
    public Games games;
    public Platforms platforms;
    
    @Override
    public void start(Stage primaryStage) {
       
        games = readGames("resources/files/games.txt");
        platforms = readPlatforms("resources/files/platforms.txt");

        MainMenu mm = new MainMenu(games, platforms);
        Scene scene = new Scene(mm);
        
//        GamesMenu eg = new GamesMenu(games, platforms);
//        Scene scene = new Scene(eg); 
        
//        EditGame eg = new EditGame(games, platforms);
//        Scene scene = new Scene(eg);

//        AddGame eg = new AddGame(games, platforms);
       //Scene scene = new Scene(eg);

//        DeleteGame eg = new DeleteGame(games, platforms);
//        Scene scene = new Scene(eg);

//        PlatformsMenu eg = new PlatformsMenu(games, platforms);
       //Scene scene = new Scene(eg);

        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
}

public static void main(String[] args) {
        launch(args);
    }
    
}