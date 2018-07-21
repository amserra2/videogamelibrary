package vglfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import vgl.Games;
import vgl.Platforms;
import static vglfx.IO.printGames;
import static vglfx.IO.printPlatforms;

public class MainMenu extends StackPane {
    private ImagePane background;
    private final File musicFile;
    private final Games games;
    private final Platforms platforms;
    private final String imagesPath;
    private final String fontsPath;
    
    private final MediaPlayer backgroundMediaPlayer;
    private final Media backgroundSound;
    
    private StackPane platformCount; 
    private HBox gameCount;
    private StackPane box;
    
    private VBox mainMenuVBox; //vbox that will hold everything for the pane
    private GridPane mainMenuGridPane; //gridpane that will hold options
    private VBox mainMenuOptionsVBox;
    
    private final int cbWidth = 160;
    private final int cbHeight = 124;
    private final int cornerSize = 30;
    
    private ImagePane platformClapBoard;
    private ImagePane gameClapBoard;
    //private ImagePane searchClapBoard;
    private ImagePane exitClapBoard;
    
    private GridPane platformClapBoard2;
    private GridPane gameClapBoard2;
    //private GridPane searchClapBoard2;
    private GridPane exitClapBoard2;
    
    private final File clapBoardSound;
    private MediaPlayer clapBoardPlayer;
    
    public MainMenu(Games games, Platforms platforms) {
        this.games = games;
        this.platforms = platforms;
        imagesPath = "resources/images/mainmenu/";
        fontsPath = "resources/fonts/";
        musicFile = new File("resources/sounds/supermariomaker.mp3");
        backgroundSound = new Media(musicFile.toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundSound);
        clapBoardSound = new File("resources/sounds/clapboard.mp3");
        createMainMenu();
    }
    
    private void createMainMenu() {
        createBackground();
        createBackgroundMusic();
        createMainMenuVBox();
        this.getChildren().addAll(background, platformCount, gameCount, box, mainMenuVBox);
    }
    
    public void createBackground() {
        background = new ImagePane(imagesPath + "background.png", 1200, 750);
        
        platformCount = new StackPane();
        createPlatformCount();
        
        gameCount = new HBox();
        createGameCount();
        
        box = new StackPane();
            box.getChildren().add(new ImagePane(imagesPath + "box.gif", 50, 50));
            box.setPadding(new Insets(0,0,281,753));

    }
    
    public void createBackgroundMusic() {
        backgroundMediaPlayer.setOnEndOfMedia(() -> {
        backgroundMediaPlayer.seek(Duration.ZERO);
        });
        backgroundMediaPlayer.play();
    }
    
    public void createPlatformCount() {
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 30).getFont();

        String numberConsoles = String.format("%02d", platforms.getNumPlatforms()); // 0 fill to 9 digits num   
        
        Text numConsoles = new Text(numberConsoles);
            numConsoles.setFont(textFont);
            numConsoles.setFill(Color.WHITE);
            
            platformCount.getChildren().add(numConsoles);
            platformCount.setAlignment(Pos.TOP_LEFT);
            platformCount.setPadding(new Insets(65,0,0,95));
    }
    
    public void createGameCount() {
       Font textFont = new CustomFont(fontsPath + "joystick.ttf", 30).getFont();
        
        ImagePane clock = new ImagePane(imagesPath + "clock.png", 45, 35);
            clock.setAlignment(Pos.TOP_RIGHT);
            clock.setPadding(new Insets(0,0,640,0));
        
        Text time = new Text();
            time.setFont(textFont);
            time.setFill(Color.WHITE);
            
        String numberGames = String.format("%09d", games.getNumGames()); // 0 fill to 9 digits num    
        
        Text numGames = new Text(numberGames);
            numGames.setFont(textFont);
            numGames.setFill(Color.WHITE);
            
        StackPane gameNumber = new StackPane();
            gameNumber.getChildren().add(numGames);
            gameNumber.setAlignment(Pos.TOP_RIGHT);
        
        final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            final Calendar cal = Calendar.getInstance();
            time.setText(timeFormat.format(cal.getTime()));
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        gameCount.getChildren().addAll(gameNumber, clock, time);
        gameCount.setAlignment(Pos.TOP_RIGHT);
        gameCount.setPadding(new Insets(70,20,0,0));
        gameCount.setSpacing(10);
    }
    
    public void createMainMenuVBox() {
        mainMenuVBox = new VBox();
        createMainMenuGridPane();
        
        Font headerFont = new CustomFont(fontsPath + "superMarioMaker.ttf", 100).getFont();
        Font smallerheaderFont = new CustomFont(fontsPath + "superMarioMaker.ttf", 40).getFont();
        
        Text headerText = new Text("Video Game Library");
            headerText.setFont(smallerheaderFont);
            headerText.setFill(Color.WHITE);
            headerText.setTextAlignment(TextAlignment.CENTER);
        
        Text headerText2 = new Text("Main Menu");
            headerText2.setFont(headerFont);
            headerText2.setFill(Color.WHITE);
            headerText2.setTextAlignment(TextAlignment.CENTER);
            
        VBox topStackPane = new VBox();
            topStackPane.setStyle("-fx-background-color: #66000000;");
            topStackPane.getChildren().addAll(headerText, headerText2);
            topStackPane.setAlignment(Pos.CENTER_LEFT);
            topStackPane.setPadding(new Insets(140, 0, 0, 380));
            
        

        mainMenuVBox.getChildren().addAll(topStackPane, mainMenuOptionsVBox);
        mainMenuVBox.setAlignment(Pos.TOP_CENTER);
        mainMenuVBox.setSpacing(40);
    }
    
    public void createMainMenuGridPane() {
        
        Font textFont = new CustomFont(fontsPath + "superMarioMaker.ttf", 24).getFont();
        
        Button platformButton = new Button("\nPLATFORMS");
            platformButton.setFont(textFont);
            platformButton.setTextAlignment(TextAlignment.CENTER);
            platformButton.setTextFill(Color.WHITE);
            platformButton.setPrefSize(cbWidth, cbHeight);
            platformButton.setBackground(Background.EMPTY);
            platformButton.setOnAction(new ButtonHandler());
            platformButton.setOnMouseEntered(new MainMenu.HoverOn());
            platformButton.setOnMouseExited(new MainMenu.HoverOff());
             
        platformClapBoard2 = new GridPane();
            platformClapBoard2.setVisible(false);
            platformClapBoard2.add(new ImagePane(imagesPath + "upperLeft.png", cornerSize, cornerSize), 0, 0);
            platformClapBoard2.add(new ImagePane(imagesPath + "upperRight.png", cornerSize, cornerSize), 1, 0);
            platformClapBoard2.add(new ImagePane(imagesPath + "lowerLeft.png", cornerSize, cornerSize), 0, 1);
            platformClapBoard2.add(new ImagePane(imagesPath + "lowerRight.png", cornerSize, cornerSize), 1, 1);
            platformClapBoard2.setAlignment(Pos.CENTER);
            platformClapBoard2.setPadding(new Insets(25,0,0,0));
            platformClapBoard2.setVgap(20);
            platformClapBoard2.setHgap(70);
            
        Button gameButton = new Button("\nGAMES");
            gameButton.setFont(textFont);
            gameButton.setTextAlignment(TextAlignment.CENTER);
            gameButton.setTextFill(Color.WHITE);
            gameButton.setPrefSize(cbWidth, cbHeight);
            gameButton.setBackground(Background.EMPTY);
            gameButton.setOnAction(new ButtonHandler());
            gameButton.setOnMouseEntered(new MainMenu.HoverOn());
            gameButton.setOnMouseExited(new MainMenu.HoverOff());
             
        gameClapBoard2 = new GridPane();
            gameClapBoard2.setVisible(false);
            gameClapBoard2.add(new ImagePane(imagesPath + "upperLeft.png", cornerSize, cornerSize), 0, 0);
            gameClapBoard2.add(new ImagePane(imagesPath + "upperRight.png", cornerSize, cornerSize), 1, 0);
            gameClapBoard2.add(new ImagePane(imagesPath + "lowerLeft.png", cornerSize, cornerSize), 0, 1);
            gameClapBoard2.add(new ImagePane(imagesPath + "lowerRight.png", cornerSize, cornerSize), 1, 1);
            gameClapBoard2.setAlignment(Pos.CENTER);
            gameClapBoard2.setPadding(new Insets(25,0,0,0));
            gameClapBoard2.setVgap(20);
            gameClapBoard2.setHgap(70);
        
        Button searchButton = new Button("\n???");
            searchButton.setFont(textFont);
            searchButton.setTextAlignment(TextAlignment.CENTER);
            searchButton.setTextFill(Color.WHITE);
            searchButton.setPrefSize(cbWidth, cbHeight);
            searchButton.setBackground(Background.EMPTY);
            searchButton.setOnAction(new ButtonHandler());
            searchButton.setOnMouseEntered(new MainMenu.HoverOn());
            searchButton.setOnMouseExited(new MainMenu.HoverOff());
             
//        searchClapBoard2 = new GridPane();
//            searchClapBoard2.setVisible(false);
//            searchClapBoard2.add(new ImagePane(imagesPath + "upperLeft.png", cornerSize, cornerSize), 0, 0);
//            searchClapBoard2.add(new ImagePane(imagesPath + "upperRight.png", cornerSize, cornerSize), 1, 0);
//            searchClapBoard2.add(new ImagePane(imagesPath + "lowerLeft.png", cornerSize, cornerSize), 0, 1);
//            searchClapBoard2.add(new ImagePane(imagesPath + "lowerRight.png", cornerSize, cornerSize), 1, 1);
//            searchClapBoard2.setAlignment(Pos.CENTER);
//            searchClapBoard2.setPadding(new Insets(25,0,0,0));
//            searchClapBoard2.setVgap(20);
//            searchClapBoard2.setHgap(70);
            
         Button exitButton = new Button("\nEXIT");
            exitButton.setFont(textFont);
            exitButton.setTextAlignment(TextAlignment.CENTER);
            exitButton.setTextFill(Color.WHITE);
            exitButton.setPrefSize(cbWidth, cbHeight);
            exitButton.setBackground(Background.EMPTY);
            exitButton.setOnAction(new ButtonHandler());
            exitButton.setOnMouseEntered(new MainMenu.HoverOn());
            exitButton.setOnMouseExited(new MainMenu.HoverOff());
             
        exitClapBoard2 = new GridPane();
            exitClapBoard2.setVisible(false);
            exitClapBoard2.add(new ImagePane(imagesPath + "upperLeft.png", cornerSize, cornerSize), 0, 0);
            exitClapBoard2.add(new ImagePane(imagesPath + "upperRight.png", cornerSize, cornerSize), 1, 0);
            exitClapBoard2.add(new ImagePane(imagesPath + "lowerLeft.png", cornerSize, cornerSize), 0, 1);
            exitClapBoard2.add(new ImagePane(imagesPath + "lowerRight.png", cornerSize, cornerSize), 1, 1);
            exitClapBoard2.setAlignment(Pos.CENTER);
            exitClapBoard2.setPadding(new Insets(25,0,0,0));
            exitClapBoard2.setVgap(20);
            exitClapBoard2.setHgap(70);
        
        platformClapBoard = new ImagePane(imagesPath + "clapBoard.png", cbWidth, cbHeight);
        gameClapBoard = new ImagePane(imagesPath + "clapBoard.png", cbWidth, cbHeight);
        //searchClapBoard = new ImagePane(imagesPath + "clapBoard.png", cbWidth, cbHeight);
        exitClapBoard = new ImagePane(imagesPath + "clapBoard.png", cbWidth, cbHeight);
        
        
        StackPane platformStackPane = new StackPane();
            platformStackPane.getChildren().addAll(platformClapBoard, platformClapBoard2, platformButton);
            platformStackPane.setAlignment(Pos.CENTER);
            
        StackPane gameStackPane = new StackPane();
            gameStackPane.getChildren().addAll(gameClapBoard, gameClapBoard2, gameButton);
            gameStackPane.setAlignment(Pos.CENTER);
            
//        StackPane searchStackPane = new StackPane();
//            searchStackPane.getChildren().addAll(searchClapBoard, searchClapBoard2, searchButton);
//            searchStackPane.setAlignment(Pos.CENTER);
            
        StackPane exitStackPane = new StackPane();
            exitStackPane.getChildren().addAll(exitClapBoard, exitClapBoard2, exitButton);
            exitStackPane.setAlignment(Pos.CENTER);
        
        mainMenuGridPane = new GridPane();
            mainMenuGridPane.setAlignment(Pos.CENTER);
            mainMenuGridPane.setHgap(20);
            mainMenuGridPane.setVgap(20);
            
        mainMenuOptionsVBox = new VBox();
            HBox firstRow = new HBox();
            firstRow.getChildren().addAll(platformStackPane, gameStackPane);
            firstRow.setAlignment(Pos.CENTER);
            firstRow.setSpacing(20);
        mainMenuOptionsVBox.getChildren().addAll(firstRow, exitStackPane);
        mainMenuOptionsVBox.setAlignment(Pos.CENTER);
        mainMenuOptionsVBox.setSpacing(20);
            
//        mainMenuGridPane.add(platformStackPane, 0, 0);
//        mainMenuGridPane.add(gameStackPane, 1, 0);
//        mainMenuGridPane.add(searchStackPane, 0, 1);
//        mainMenuGridPane.add(exitStackPane, 0, 1);
    }
    
    public class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
                      
            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
                        
            if (sourceButton.equals("\nPLATFORMS")) platform();
            else if (sourceButton.equals("\nEXIT")) System.exit(-1);
            else if (sourceButton.equals("\n???")) {
                if (!backgroundMediaPlayer.isMute()) backgroundMediaPlayer.setMute(true);
                else backgroundMediaPlayer.setMute(false);
            }
            else if (sourceButton.equals("\nGAMES")) game();
        }
    }
    
    public class HoverOn implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
            
            Media sound = new Media(clapBoardSound.toURI().toString());
            clapBoardPlayer = new MediaPlayer(sound);
            clapBoardPlayer.play();
           
            if (sourceButton.equals("\nPLATFORMS")) platformClapBoard2.setVisible(true);
            else if (sourceButton.equals("\nEXIT")) exitClapBoard2.setVisible(true);
            //else if (sourceButton.equals("\n???")) searchClapBoard2.setVisible(true);
            else if (sourceButton.equals("\nGAMES")) gameClapBoard2.setVisible(true);
            
            
        }
    }
    
    public class HoverOff implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
            
            clapBoardPlayer.stop();

            if (sourceButton.equals("\nPLATFORMS")) platformClapBoard2.setVisible(false);
            else if (sourceButton.equals("\nEXIT")) exitClapBoard2.setVisible(false);
            //else if (sourceButton.equals("\n???")) searchClapBoard2.setVisible(false);
            else if (sourceButton.equals("\nGAMES")) gameClapBoard2.setVisible(false);
            
        }
    }
    
    public void game() {
       
        backgroundMediaPlayer.stop();
        this.setVisible(false);
        this.getScene().getWindow().hide();
        Stage primaryStage = new Stage(); 
        GamesMenu gamesMenu = new GamesMenu(games, platforms); 
        Scene newScene = new Scene(gamesMenu);
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();
         
    }
    
    public void platform() {
       
        backgroundMediaPlayer.stop();
        this.setVisible(false);
        this.getScene().getWindow().hide();
        Stage primaryStage = new Stage(); 
        PlatformsMenu platformsMenu = new PlatformsMenu(games, platforms); 
        Scene newScene = new Scene(platformsMenu);
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();
         
    }
}
