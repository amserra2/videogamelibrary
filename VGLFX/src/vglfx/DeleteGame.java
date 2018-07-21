package vglfx;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vgl.Game;
import vgl.Games;
import static vgl.IO.writeGames;
import vgl.Platforms;
import static vglfx.IO.searchGames;
import static vglfx.IO.sortGames;

public class DeleteGame extends StackPane {
    private ImagePane background;
    private ImagePane bar;
    private VBox barVBox;
    private final Games games;
    private final Platforms platforms;
    private final String imagesPath;
    private final String fontsPath;
    
    private final File musicFile;
    private final MediaPlayer backgroundMediaPlayer;
    private final Media backgroundMusic;
    private final File creditSound;
    private MediaPlayer creditPlayer;
    
    private VBox deleteGameVBox;
    
    private HBox contentHBox;
    
    private HBox backHBox;
    private ImagePane backButton;
    
    private VBox leftContentVBox;
    private StackPane rightContentStackPane;
    
    private TextField searchField;
    private HBox searchHBox;
    private VBox searchVBox;
    
    private ScrollPane contentScrollPane;
    private VBox contentScrollPaneVBox;
    
    private Game selectedGame;
    private Game hoverGame;
    private ImagePane selectedGameImage;
    
    private Stage hoverStage;
    
    private Button confirm; 
    private Button cancel;

    private Stage primaryStage;
    private Scene newScene;
    
    public DeleteGame(Games games, Platforms platforms) { 
        this.games = games;
        this.platforms = platforms;
        imagesPath = "resources/images/gamesmenu/";
        fontsPath = "resources/fonts/";
        musicFile = new File("resources/sounds/pacmanarcade.wav");
        backgroundMusic = new Media(musicFile.toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        creditSound = new File("resources/sounds/credit.mp3");
        createDeleteGame();
    }
    
    private void createDeleteGame() {
        createBackground();
        createSounds();
        createDeleteGameVBox();
        this.getChildren().addAll(background, barVBox, deleteGameVBox);
    }
    
    public void createSounds() {
        backgroundMediaPlayer.play();
        
        Media sound = new Media(creditSound.toURI().toString());
        creditPlayer = new MediaPlayer(sound);
    }
    
    public void createBackground() {
        background = new ImagePane(imagesPath + "background.gif", 1200, 750);
        bar = new ImagePane(imagesPath + "bar.png", 750, 150);
        barVBox = new VBox();
            barVBox.setAlignment(Pos.TOP_CENTER);
            barVBox.setPadding(new Insets(20,0,0,0));
            barVBox.getChildren().add(bar);
    }
    
    public void createDeleteGameVBox() {
        
        deleteGameVBox = new VBox();
        
        Font headerFont = new CustomFont(fontsPath + "crackman.ttf", 100).getFont();
        
        Text headerText = new Text("Delete Game");
            headerText.setFont(headerFont);
            headerText.setFill(Color.YELLOW);
            headerText.setTextAlignment(TextAlignment.CENTER);
            
        contentHBox = new HBox();   
            contentHBox.setAlignment(Pos.CENTER);
            contentHBox.setStyle("-fx-background-color: #000000;");
            contentHBox.setSpacing(75);
            contentHBox.setPadding(new Insets(10,0,10,0));
            contentHBox.setMinWidth(1100);
            contentHBox.setMaxWidth(1100);
            
        createContentHBox();
        
        backHBox = new HBox();
        createBackHBox();
          
        deleteGameVBox.getChildren().addAll(headerText, contentHBox, backHBox);
        deleteGameVBox.setAlignment(Pos.TOP_CENTER);
        deleteGameVBox.setSpacing(50);
        deleteGameVBox.setPadding(new Insets(35,0,0,0));
    
    }
    
    public void createContentHBox() {
        
        leftContentVBox = new VBox();
            leftContentVBox.setMaxSize(600, 400);
            leftContentVBox.setMinSize(600, 400);
            leftContentVBox.setAlignment(Pos.CENTER_LEFT);
            leftContentVBox.setSpacing(10);
        createLeftContentVBox();
            
        
        rightContentStackPane = new StackPane();
            rightContentStackPane.setMaxSize(300, 400);
            rightContentStackPane.setMinSize(300, 400);
            rightContentStackPane.setAlignment(Pos.CENTER);
            rightContentStackPane.setStyle("-fx-background-color: #000000;");
            rightContentStackPane.setPadding(new Insets(5,0,0,0));
            
        createRightContentStackPane("Default");
        
        contentHBox.getChildren().addAll(leftContentVBox, rightContentStackPane);
    }
    
    public void createLeftContentVBox() {
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 30).getFont();
        Font searchFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 14).getFont();
        
        Text headerText = new Text("Master Video Game List");
                headerText.setFont(textFont);
                headerText.setFill(Color.WHITE);
                    
        searchField = new TextField();
            searchField.setPrefSize(400, 20);
        
        Button searchButton = new Button("Search");
            searchButton.setFont(searchFont);
            searchButton.setOnAction(new DeleteGame.ButtonHandler());
            searchButton.setOnMouseEntered(new DeleteGame.HoverOn());
            searchButton.setOnMouseExited(new DeleteGame.HoverOff());
            searchButton.setStyle("-fx-background-color: transparent;");
            searchButton.setTextFill(Color.WHITE);
            searchButton.setAlignment(Pos.CENTER_LEFT);
        
        Button clearButton = new Button("Clear");
            clearButton.setFont(searchFont);
            clearButton.setOnAction(new DeleteGame.ButtonHandler());
            clearButton.setOnMouseEntered(new DeleteGame.HoverOn());
            clearButton.setOnMouseExited(new DeleteGame.HoverOff());
            clearButton.setStyle("-fx-background-color: transparent;");
            clearButton.setTextFill(Color.WHITE);
            clearButton.setAlignment(Pos.CENTER_LEFT);
        
        searchVBox = new VBox();
            searchVBox.setAlignment(Pos.CENTER);
            searchVBox.setSpacing(2);
            searchVBox.getChildren().addAll(searchButton, clearButton);
            
        searchHBox = new HBox();
            searchHBox.setAlignment(Pos.CENTER);
            searchHBox.setSpacing(10);
            searchHBox.getChildren().addAll(searchField, searchVBox);

         
        if (games.getNumGames() > 0) {
            
            contentScrollPane = new ScrollPane();
                contentScrollPane.setStyle("-fx-background-color: #000000;");
            
            contentScrollPaneVBox = new VBox();
                contentScrollPaneVBox.setMaxWidth(600);
                contentScrollPaneVBox.setMinWidth(600);
                contentScrollPaneVBox.setStyle("-fx-background-color: #000000;");
                contentScrollPaneVBox.setAlignment(Pos.TOP_LEFT);
                
            createContentScrollPane();
            
            leftContentVBox.getChildren().addAll(headerText, searchHBox, contentScrollPane);
            leftContentVBox.setSpacing(20);
        }
        
        else {
            
            Text empty = new Text("No games in library.");
                empty.setFont(textFont);
                empty.setFill(Color.WHITE);
                empty.setTextAlignment(TextAlignment.CENTER);
                
            leftContentVBox.getChildren().addAll(headerText, empty);
            leftContentVBox.setSpacing(200);
        } 
        
        leftContentVBox.setAlignment(Pos.TOP_CENTER);

    }
    
    public void createRightContentStackPane(String version) {
        rightContentStackPane.getChildren().clear();
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 15).getFont();
        
        Text text = new Text();
            text.setFont(textFont);
            text.setFill(Color.WHITE);
            text.setWrappingWidth(400);
            text.setTextAlignment(TextAlignment.CENTER);
            
        if (version.equals("Default")) {
            text.setText("Select a game to delete.");
            rightContentStackPane.getChildren().add(text);
        }
        else if (version.equals("Selected")) {
            
            selectedGameImage = new ImagePane(selectedGame.getPicture(), 175, 250);
                
            text.setText("Would you like to delete this game?");
            
            confirm = new Button();
                confirm.setText("Confirm");
                confirm.setFont(textFont);
                confirm.setTextFill(Color.WHITE);
                confirm.setStyle("-fx-background-color: transparent;");
                confirm.setOnAction(new DeleteGame.ButtonHandler());
                confirm.setOnMouseEntered(new DeleteGame.HoverOn());
                confirm.setOnMouseExited(new DeleteGame.HoverOff());
            
            cancel = new Button();
                cancel.setText("Cancel");
                cancel.setFont(textFont);
                cancel.setTextFill(Color.WHITE);
                cancel.setStyle("-fx-background-color: transparent;");
                cancel.setOnAction(new DeleteGame.ButtonHandler());
                cancel.setOnMouseEntered(new DeleteGame.HoverOn());
                cancel.setOnMouseExited(new DeleteGame.HoverOff());

            HBox optionButtons = new HBox();
                optionButtons.setSpacing(5);
                optionButtons.setAlignment(Pos.CENTER);
                optionButtons.getChildren().addAll(confirm, cancel);
            
            VBox confirmVBox = new VBox();
                confirmVBox.setAlignment(Pos.CENTER);
                confirmVBox.setSpacing(10);
                confirmVBox.getChildren().addAll(selectedGameImage, text, optionButtons);
                
            rightContentStackPane.getChildren().add(confirmVBox);
        }
        else if (version.equals("Finished")) {
            text.setText(selectedGame.getTitle() + " has been successfully deleted.");
            rightContentStackPane.getChildren().add(text);
        }
        
        
       
    }
    
    public void createContentScrollPane() {
        
        contentScrollPaneVBox.getChildren().clear();
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 14).getFont();
        
        Games sortedGames = sortGames(games);

        int n = sortedGames.getNumGames();

        Button gameButton;
        String gameText;
        int wrappingWidth;
        
        for (int i = 0; i < n; i++) {
            
            gameText = sortedGames.getGame(i).getTitle(); 

                gameButton = new Button(gameText);
                    gameButton.setFont(textFont);
                    gameButton.setTextFill(Color.WHITE);
                    gameButton.setAlignment(Pos.CENTER_LEFT);
                    gameButton.setStyle("-fx-background-color: transparent;");
                    gameButton.setOnAction(new DeleteGame.ButtonHandler());
                    gameButton.setOnMouseEntered(new DeleteGame.HoverOn());
                    gameButton.setOnMouseExited(new DeleteGame.HoverOff());

                contentScrollPaneVBox.getChildren().add(gameButton);
            }

                if (n > 11) wrappingWidth = 600;
                else wrappingWidth = 586;
                
                if (n == 1) {
                    contentScrollPane.setMinViewportHeight(10);
                    contentScrollPane.setMaxHeight(10);
                }
                else {
                    contentScrollPane.setMinViewportHeight(USE_PREF_SIZE);
                    contentScrollPane.setMaxHeight(USE_PREF_SIZE);
                }

            contentScrollPane.setContent(contentScrollPaneVBox);
                contentScrollPane.setMinViewportWidth(wrappingWidth);
                contentScrollPane.setMaxWidth(wrappingWidth);
    }
    
    public void createBackHBox() {
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 25).getFont();
        
        Button back = new Button("Back");
            back.setFont(textFont);
            back.setTextAlignment(TextAlignment.CENTER);
            back.setTextFill(Color.WHITE);
            back.setStyle("-fx-background-color: transparent;");
            back.setOnAction(new DeleteGame.ButtonHandler());
            back.setOnMouseEntered(new DeleteGame.HoverOn());
            back.setOnMouseExited(new DeleteGame.HoverOff());
        
        backButton = new ImagePane(imagesPath + "redArrow.png", 50, 50);
        backButton.setVisible(false);
            
        
        backHBox.getChildren().addAll(backButton, back);
            backHBox.setAlignment(Pos.CENTER);
            backHBox.setStyle("-fx-background-color: #000000;");
            backHBox.setMaxWidth(200);
            backHBox.setMinWidth(200);
       
    }
    
    public void search() {

        contentScrollPaneVBox.getChildren().clear();
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 14).getFont();

        if (!searchField.getText().trim().isEmpty()) {
            
            Games searchResults = searchGames(games, searchField.getText().toLowerCase().replaceAll("\\s+",""));
            int n = searchResults.getNumGames();
            int wrappingWidth;
            
            if (n > 0) {
                Button gameButton;
                String gameText;


                for (int i = 0; i < n; i++) {
                    gameText = searchResults.getGame(i).getTitle(); 

                        gameButton = new Button(gameText);
                            gameButton.setFont(textFont);
                            gameButton.setTextFill(Color.WHITE);
                            gameButton.setAlignment(Pos.CENTER_LEFT);
                            gameButton.setStyle("-fx-background-color: transparent;");
                            gameButton.setOnAction(new DeleteGame.ButtonHandler());
                            gameButton.setOnMouseEntered(new DeleteGame.HoverOn());
                            gameButton.setOnMouseExited(new DeleteGame.HoverOff());

                        contentScrollPaneVBox.getChildren().add(gameButton);
                    }
            }
            else {
                
                Text noResults = new Text("No results found");
                    noResults.setFont(textFont);
                    noResults.setFill(Color.WHITE);
                    noResults.setTextAlignment(TextAlignment.CENTER);
                    
                StackPane empty = new StackPane();
                    empty.setMinSize(100, 100);
                    empty.setAlignment(Pos.CENTER);
                    empty.setStyle("-fx-background-color: #000000;");
                    empty.getChildren().add(noResults);
                    
                contentScrollPaneVBox.getChildren().add(empty);    
            }

                if (n > 9) wrappingWidth = 600;
                else wrappingWidth = 586;
                
                if (n == 1) {
                    contentScrollPane.setMinViewportHeight(10);
                    contentScrollPane.setMaxHeight(10);
                }
                else {
                    contentScrollPane.setMinViewportHeight(USE_PREF_SIZE);
                    contentScrollPane.setMaxHeight(USE_PREF_SIZE);
                }
                
                
                contentScrollPane.setContent(contentScrollPaneVBox);
                    contentScrollPane.setMinViewportWidth(wrappingWidth);
                    contentScrollPane.setMaxWidth(wrappingWidth);
        }
        
        else createContentScrollPane();
    }
    
    public void confirmDelete() {
        
        int n = games.getNumGames();
        
            for (int i = 0; i < n; i++) {
                if(selectedGame.getTitle().equals(games.getGame(i).getTitle())) {
                    games.deleteGame(i);
                    break;
                }
            }
        
        File deleteFile = new File(selectedGame.getPicture()); //creates link to pictue of game
        deleteFile.delete(); //deletes picture for space purposes
        
        writeGames(games);
        
        games.getGameMap().remove(selectedGame.getTitle());
        games.getGameIDs().remove(selectedGame.getTitle());
         
        createRightContentStackPane("Finished");
        searchField.clear();
        createContentScrollPane();
    }
    
    public class ButtonHandler implements EventHandler<ActionEvent> { 
          @Override
        public void handle(ActionEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("Back")) game();
            else if (sourceButton.equals("Search")) search();
            else if (sourceButton.equals("Confirm")) confirmDelete();
            else if (sourceButton.equals("Clear")) {
                searchField.clear();
                createContentScrollPane();
            }
            else if (sourceButton.equals("Cancel")) {
                createRightContentStackPane("Default");
                searchField.clear();
                createContentScrollPane();
            }
            
            else {
                selectedGame = games.getGameMap().get(sourceButton);
                createRightContentStackPane("Selected");
            }
        }
    }
    
    public class HoverOn implements EventHandler<MouseEvent> {
        
        

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
            
            creditPlayer.play();

            if (sourceButton.equals("Back")) backButton.setVisible(true);
            else if (sourceButton.equals("Search")) button.setTextFill(Color.RED);
            else if (sourceButton.equals("Clear")) button.setTextFill(Color.RED);
            else if (sourceButton.equals("Confirm")) button.setTextFill(Color.RED);
            else if (sourceButton.equals("Cancel")) button.setTextFill(Color.RED);
            else {
                
                Bounds bounds = button.getBoundsInLocal();
                Bounds screenBounds = button.localToScreen(bounds);
                
                int x = (int) screenBounds.getMinX();
                int y = (int) screenBounds.getMinY();
                int width = (int) screenBounds.getWidth();
                
                hoverStage = new Stage();
                hoverStage.setX(x + width);
                hoverStage.setY(y - 260);

                button.setTextFill(Color.RED);
                hoverGame = games.getGameMap().get(sourceButton);
                selectedGame();
            }
           
        }
    }
    
    public class HoverOff implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
            
            creditPlayer.stop();

            if (sourceButton.equals("Back")) backButton.setVisible(false);
            else if (sourceButton.equals("Search")) button.setTextFill(Color.WHITE);
            else if (sourceButton.equals("Clear")) button.setTextFill(Color.WHITE);
            else if (sourceButton.equals("Confirm")) button.setTextFill(Color.WHITE);
            else if (sourceButton.equals("Cancel")) button.setTextFill(Color.WHITE);
            else {
                hoverStage.getScene().getWindow().hide();
                button.setTextFill(Color.WHITE);
            }

        }
    }
    
    public void game() {
        
        backgroundMediaPlayer.stop();
        
        this.setVisible(false);
        this.getScene().getWindow().hide();
        primaryStage = new Stage(); 
        GamesMenu gamesMenu = new GamesMenu(games, platforms); 
       newScene = new Scene(gamesMenu);
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();
         
    }
    
    public void selectedGame() {

        GamePane gamePane = new GamePane(hoverGame);
            gamePane.setStyle("-fx-background-color: #F18331;");
        newScene = new Scene(gamePane);

        hoverStage.setResizable(false);
        hoverStage.setTitle(hoverGame.getTitle());
        hoverStage.setScene(newScene);
        hoverStage.initStyle(StageStyle.UNDECORATED);
        hoverStage.show();

    }
}
