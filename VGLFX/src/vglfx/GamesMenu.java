package vglfx;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vgl.Game;
import vgl.Games;
import vgl.Platforms;
import static vglfx.IO.completedGames;
import static vglfx.IO.incompleteGames;
import static vglfx.IO.printGames;
import static vglfx.IO.readGenres;
import static vglfx.IO.readPlatforms;
import static vglfx.IO.readPublishers;
import static vglfx.IO.readTitles;
import static vglfx.IO.searchGames;
import static vglfx.IO.sortGames;
import static vglfx.IO.specificGenre;
import static vglfx.IO.specificPlatform;
import static vglfx.IO.specificPublisher;
import static vglfx.IO.specificTitle;
import static vglfx.IO.unplayedGames;

    
public class GamesMenu extends StackPane {
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
    
    private VBox gamesMenuVBox; //vbox that will hold everything for the pane
    
    private HBox contentHBox; //contains leftPane and rightPane
    
    private GridPane leftPane; //holds these ImagePanes
    
    private ImagePane viewMaster;
    private ImagePane viewCategories;
    private ImagePane add;
    private ImagePane delete;
    private ImagePane edit;
    private ImagePane back;
    
    private StackPane rightPane;
    private VBox rightVBox;
    
    private TextField searchField;
    private HBox searchHBox;
    private VBox searchVBox;
    
    private ScrollPane contentScrollPane;
    private VBox contentScrollPaneVBox;
    
    private Game randomGame;
    private ButtonImagePane randomGameImagePane;
    
    private ImagePane titleBack;
    private ImagePane platformBack;
    private ImagePane genreBack;
    private ImagePane publisherBack;
    private ImagePane unplayedBack;
    private ImagePane incompleteBack;
    private ImagePane completedBack;
    
    private final int w = 250; 
    private final int h = 50;
    
    private GridPane categoriesGridPane;
    private ComboBox titlesComboBox;
    private ComboBox platformsComboBox;
    private ComboBox genresComboBox;
    private ComboBox publishersComboBox;
    
    private final File creditSound;
    private MediaPlayer creditPlayer;
    
    private Stage primaryStage;
    private Scene newScene;
    
    private Button randomButton;
    private Button randomButton2;
    
    private Stage dialogStage;
    private Scene dialogScene;
    
    public GamesMenu(Games games, Platforms platforms) {
        this.games = games;
        this.platforms = platforms;
        imagesPath = "resources/images/gamesmenu/";
        fontsPath = "resources/fonts/";
        musicFile = new File("resources/sounds/pacmanarcade.wav");
        backgroundMusic = new Media(musicFile.toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        creditSound = new File("resources/sounds/credit.mp3");
        createGamesMenu();
    }
    
    private void createGamesMenu() {
        createBackground();
        createGamesMenuVBox();
        createSounds();
        this.getChildren().addAll(background, barVBox, gamesMenuVBox);
        //GamesMenu.setAlignment(bar, Pos.TOP_CENTER);
    }
    
    public void createSounds() {
        backgroundMediaPlayer.play();
        
        Media sound = new Media(creditSound.toURI().toString());
        creditPlayer = new MediaPlayer(sound);
       
    }
    
    public void createBackground() {
        background = new ImagePane(imagesPath + "background.gif", 1200, 750);
        bar = new ImagePane(imagesPath + "bar.png", 1000, 150);
        barVBox = new VBox();
            barVBox.setAlignment(Pos.TOP_CENTER);
            barVBox.setPadding(new Insets(20,0,0,0));
            barVBox.getChildren().add(bar);
    }
    
    public void createGamesMenuVBox() {
        gamesMenuVBox = new VBox();
       
        Font headerFont = new CustomFont(fontsPath + "crackman.ttf", 100).getFont();
        
        Text headerText = new Text("Video Game Menu");
            headerText.setFont(headerFont);
            headerText.setFill(Color.YELLOW);
            headerText.setTextAlignment(TextAlignment.CENTER);
            
        contentHBox = new HBox();    
        createContentHBox();
          
        gamesMenuVBox.getChildren().addAll(headerText, contentHBox);
        gamesMenuVBox.setAlignment(Pos.TOP_CENTER);
        gamesMenuVBox.setSpacing(70);
        gamesMenuVBox.setPadding(new Insets(35,0,0,0));
    
    }
    
    public void createContentHBox() {
        leftPane = new GridPane();
            //leftPane.setStyle("-fx-background-color: #000000;"); 
        createLeftPane();
        
        rightPane = new StackPane();
            //rightPane.setStyle("-fx-background-color: #000000;"); 
            rightPane.setMaxSize(630, 450);
            rightPane.setMinSize(630, 450);
        rightVBox = new VBox();
        createRightPane(0);
        
        contentHBox.getChildren().addAll(leftPane, rightPane); 
            contentHBox.setAlignment(Pos.CENTER);
            contentHBox.setSpacing(40); 
            contentHBox.setPadding(new Insets(10,0,10,0)); 
            contentHBox.setStyle("-fx-background-color: #000000;"); 
            contentHBox.setMaxWidth(1100);
    }
    
    public void createLeftPane() {
        
        //Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 25).getFont();
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 20).getFont();
            
        Button viewMasterButton = new Button("View Master List");
            viewMasterButton.setFont(textFont);
            viewMasterButton.setOnAction(new GamesMenu.ButtonHandler());
            viewMasterButton.setOnMouseEntered(new GamesMenu.HoverOn());
            viewMasterButton.setOnMouseExited(new GamesMenu.HoverOff());
            viewMasterButton.setStyle("-fx-background-color: transparent;");
            viewMasterButton.setTextFill(Color.WHITE);
            viewMasterButton.setAlignment(Pos.CENTER_LEFT);
         
        Button viewCategoriesButton = new Button("View Categories");
            viewCategoriesButton.setFont(textFont);
            viewCategoriesButton.setOnAction(new GamesMenu.ButtonHandler());
            viewCategoriesButton.setOnMouseEntered(new GamesMenu.HoverOn());
            viewCategoriesButton.setOnMouseExited(new GamesMenu.HoverOff());
            viewCategoriesButton.setStyle("-fx-background-color: transparent;");
            viewCategoriesButton.setTextFill(Color.WHITE);
            viewCategoriesButton.setAlignment(Pos.CENTER_LEFT);
            
        Button addButton = new Button("Add Game");
            addButton.setFont(textFont);
            addButton.setOnAction(new GamesMenu.ButtonHandler());
            addButton.setOnMouseEntered(new GamesMenu.HoverOn());
            addButton.setOnMouseExited(new GamesMenu.HoverOff());
            addButton.setStyle("-fx-background-color: transparent;");
            addButton.setTextFill(Color.WHITE);
            addButton.setAlignment(Pos.CENTER_LEFT);
            
        Button deleteButton = new Button("Delete Game");
            deleteButton.setFont(textFont);
            deleteButton.setOnAction(new GamesMenu.ButtonHandler());
            deleteButton.setOnMouseEntered(new GamesMenu.HoverOn());
            deleteButton.setOnMouseExited(new GamesMenu.HoverOff());
            deleteButton.setStyle("-fx-background-color: transparent;");
            deleteButton.setTextFill(Color.WHITE);
            deleteButton.setAlignment(Pos.CENTER_LEFT);
            
        Button editButton = new Button("Edit Game");
            editButton.setFont(textFont);
            editButton.setOnAction(new GamesMenu.ButtonHandler());
            editButton.setOnMouseEntered(new GamesMenu.HoverOn());
            editButton.setOnMouseExited(new GamesMenu.HoverOff());
            editButton.setStyle("-fx-background-color: transparent;");
            editButton.setTextFill(Color.WHITE);
            editButton.setAlignment(Pos.CENTER_LEFT);
            
        Button backButton = new Button("Back");
            backButton.setFont(textFont);
            backButton.setOnAction(new GamesMenu.ButtonHandler());
            backButton.setOnMouseEntered(new GamesMenu.HoverOn());
            backButton.setOnMouseExited(new GamesMenu.HoverOff());
            backButton.setStyle("-fx-background-color: transparent;");
            backButton.setTextFill(Color.WHITE);
            backButton.setAlignment(Pos.CENTER_LEFT);
        
        viewMaster = new ImagePane(imagesPath + "redArrow.png", 50, 50);
        viewCategories = new ImagePane(imagesPath + "redArrow.png", 50, 50);
        add = new ImagePane(imagesPath + "redArrow.png", 50, 50);
        delete = new ImagePane(imagesPath + "redArrow.png", 50, 50);
        edit = new ImagePane(imagesPath + "redArrow.png", 50, 50);
        back = new ImagePane(imagesPath + "redArrow.png", 50, 50); 
        
        viewMaster.setVisible(false);
        viewCategories.setVisible(false);
        add.setVisible(false);
        delete.setVisible(false);
        edit.setVisible(false);
        back.setVisible(false);
            
        leftPane.add(viewMaster, 0, 0);
        leftPane.add(viewCategories, 0, 1);
        leftPane.add(add, 0, 2);
        leftPane.add(delete, 0, 3);
        leftPane.add(edit, 0, 4);
        leftPane.add(back, 0, 5);
        
        leftPane.add(viewMasterButton, 1, 0);
        leftPane.add(viewCategoriesButton, 1, 1);
        leftPane.add(addButton, 1, 2);
        leftPane.add(deleteButton, 1, 3);
        leftPane.add(editButton, 1, 4);
        leftPane.add(backButton, 1, 5);

        leftPane.setVgap(30);
        leftPane.setAlignment(Pos.CENTER_LEFT);
    }
    
    public void createRightPane(int version) {
        rightPane.getChildren().clear();
        rightVBox.getChildren().clear();
        
        if (version == 0) randomGamePane();
        else if (version == 1) masterListPane();
        else if (version == 2) categoriesPane();
        else if (version == 3) titlePane();
        else if (version == 4) platformPane();
        else if (version == 5) genrePane();
        else if (version == 6) publisherPane();
        else if (version == 7) unplayedPane();
        else if (version == 8) incompletePane();
        else if (version == 9) completedPane();
    }
    
    public void randomGamePane() {
        
        ImagePane gamesMiniBackground = new ImagePane(imagesPath + "ghosts.gif", 650, 450);
        
        generateRandomGame();
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 20).getFont();
        Font smallTextFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 15).getFont();
        Font evenSmallerTextFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 10).getFont();
        
        Text rgdText = new Text("Random Game You Should Play: ");
                    rgdText.setFont(textFont);
                    rgdText.setFill(Color.WHITE);
                    rgdText.setTextAlignment(TextAlignment.CENTER);
        Text rgText = new Text(randomGame.getTitle());
                    rgText.setFont(smallTextFont);
                    rgText.setFill(Color.WHITE);
        Text clickText = new Text("Click on the picture for game information!");
                    clickText.setFont(evenSmallerTextFont);
                    clickText.setFill(Color.WHITE);
            
        
        rightVBox.getChildren().addAll(rgdText, rgText, randomGameImagePane, clickText);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.setSpacing(20);
        
        
        rightPane.getChildren().addAll(gamesMiniBackground, rightVBox);
        //rightPane.getChildren().addAll(rightVBox);
            rightPane.setAlignment(Pos.CENTER);
    }
    
    public void generateRandomGame() {
        
        if (games.getNumGames() > 0) {
            Random rand = new Random();
            int n = rand.nextInt(games.getNumGames());
            randomGame = games.getGame(n);
        }
        
        else randomGame = new Game();

        randomGameImagePane = new ButtonImagePane(randomGame.getPicture(), 225, 300);
        randomGameImagePane.getButton().setText("Random Game");
        randomGameImagePane.getButton().setOnAction(new GamesMenu.ButtonHandler());
        
    }
    
    public void masterListPane() {
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 30).getFont();
        Font searchFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 14).getFont();
        
        Text headerText = new Text("Master Video Game List");
                headerText.setFont(textFont);
                headerText.setFill(Color.WHITE);
                    
        searchField = new TextField();
            searchField.setPrefSize(350, 20);
            
        Button downloadButton = new Button("Download\nList");
            downloadButton.setFont(searchFont);
            downloadButton.setOnAction(new GamesMenu.ButtonHandler());
            downloadButton.setOnMouseEntered(new GamesMenu.HoverOn());
            downloadButton.setOnMouseExited(new GamesMenu.HoverOff());
            downloadButton.setStyle("-fx-background-color: transparent;");
            downloadButton.setTextFill(Color.WHITE);
            downloadButton.setTextAlignment(TextAlignment.CENTER);
            downloadButton.setAlignment(Pos.CENTER_LEFT);
        
        Button searchButton = new Button("Search");
            searchButton.setFont(searchFont);
            searchButton.setOnAction(new GamesMenu.ButtonHandler());
            searchButton.setOnMouseEntered(new GamesMenu.HoverOn());
            searchButton.setOnMouseExited(new GamesMenu.HoverOff());
            searchButton.setStyle("-fx-background-color: transparent;");
            searchButton.setTextFill(Color.WHITE);
            searchButton.setAlignment(Pos.CENTER_LEFT);
        
        Button clearButton = new Button("Clear");
            clearButton.setFont(searchFont);
            clearButton.setOnAction(new GamesMenu.ButtonHandler());
            clearButton.setOnMouseEntered(new GamesMenu.HoverOn());
            clearButton.setOnMouseExited(new GamesMenu.HoverOff());
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
            searchHBox.getChildren().addAll(downloadButton, searchField, searchVBox);

         
        if (games.getNumGames() > 0) {
            
            contentScrollPane = new ScrollPane();
                contentScrollPane.setStyle("-fx-background-color: #000000;");
            
            contentScrollPaneVBox = new VBox();
                contentScrollPaneVBox.setMaxWidth(620);
                contentScrollPaneVBox.setMinWidth(620);
                contentScrollPaneVBox.setStyle("-fx-background-color: #000000;");
                contentScrollPaneVBox.setAlignment(Pos.TOP_LEFT);
                
            createContentScrollPane(games);
            
            rightVBox.getChildren().addAll(headerText, searchHBox, contentScrollPane);
            rightVBox.setSpacing(20);
        }
        
        else {
            
            Text empty = new Text("No games in library.");
                empty.setFont(textFont);
                empty.setFill(Color.WHITE);
                empty.setTextAlignment(TextAlignment.CENTER);
                
            rightVBox.getChildren().addAll(headerText, empty);
            rightVBox.setSpacing(200);
        } 
        
        rightVBox.setAlignment(Pos.TOP_CENTER);
            
        rightPane.getChildren().addAll(rightVBox);
            rightPane.setAlignment(Pos.TOP_CENTER);
    }
    
    public void categoriesPane() {
        
        ImagePane gamesMiniBackground = new ImagePane(imagesPath + "ghosts.gif", 650, 450);
        
        categoriesGridPane = new GridPane();
        createCategoriesGridPane();
       
        Font headerFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 30).getFont();
        
        Text headerText = new Text("Choose A Category:");
            headerText.setFont(headerFont);
            headerText.setFill(Color.WHITE);
            
        rightVBox.getChildren().addAll(headerText, categoriesGridPane);
            rightVBox.setSpacing(20);
            rightVBox.setAlignment(Pos.TOP_CENTER);
            
        rightPane.getChildren().addAll(gamesMiniBackground, rightVBox);
        //rightPane.getChildren().addAll(rightVBox);
            rightPane.setAlignment(Pos.TOP_CENTER);
    }
    
    public void titlePane() {
        ObservableList<String> titles = readTitles(games);
        titlesComboBox = new ComboBox(titles);
        titlesComboBox.setOnAction(new GamesMenu.TitleComboBoxHandler());
        titlesComboBox.setStyle("-fx-background-color: #FFFF33; -fx-font: 15px \"textFont\";");
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 25).getFont();
        
        Text headerText = new Text("Video Game Titles");
                headerText.setFont(textFont);
                headerText.setFill(Color.WHITE);
        
              
            Text selectText = new Text();
                 if (!titles.isEmpty()) selectText.setText("Select a letter or character");
                 else selectText.setText("No games in library.");
                 selectText.setFont(textFont);
                 selectText.setFill(Color.WHITE);
                 selectText.setTextAlignment(TextAlignment.CENTER);
                 selectText.setWrappingWidth(500);
            
            StackPane stackPane = new StackPane();
                stackPane.setAlignment(Pos.CENTER);
                stackPane.getChildren().add(selectText);
                stackPane.setMinSize(628,200);
                 
            contentScrollPaneVBox = new VBox();     
                 contentScrollPaneVBox.getChildren().add(stackPane);
                 contentScrollPaneVBox.setAlignment(Pos.CENTER);
                 contentScrollPaneVBox.setStyle("-fx-background-color: #000000;");
                 
            contentScrollPane = new ScrollPane(contentScrollPaneVBox);
                contentScrollPane.setStyle("-fx-background-color: #000000;");   
                
        rightVBox.getChildren().addAll(headerText, titlesComboBox, contentScrollPane);
            rightVBox.setSpacing(20);
            rightVBox.setAlignment(Pos.TOP_CENTER);
            
        rightPane.getChildren().addAll(rightVBox);
            rightPane.setAlignment(Pos.TOP_CENTER);
    }
    
    public void platformPane() {
        ObservableList<String> platformsList = readPlatforms(games);
        platformsComboBox = new ComboBox(platformsList);
        platformsComboBox.setOnAction(new GamesMenu.PlatformComboBoxHandler());
        platformsComboBox.setStyle("-fx-background-color: #FFFF33; -fx-font: 15px \"textFont\";");
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 25).getFont();
        
        Text headerText = new Text("Video Game Platforms");
                headerText.setFont(textFont);
                headerText.setFill(Color.WHITE);
        
              
            Text selectText = new Text();
                if (!platformsList.isEmpty()) selectText.setText("Select a platform");
                else selectText.setText("No games in library.");
                 selectText.setFont(textFont);
                 selectText.setFill(Color.WHITE);
                 selectText.setTextAlignment(TextAlignment.CENTER);
                 selectText.setWrappingWidth(500);
            
            StackPane stackPane = new StackPane();
                stackPane.setAlignment(Pos.CENTER);
                stackPane.getChildren().add(selectText);
                stackPane.setMinSize(628,200);
                 
            contentScrollPaneVBox = new VBox();     
                 contentScrollPaneVBox.getChildren().add(stackPane);
                 contentScrollPaneVBox.setAlignment(Pos.CENTER);
                 contentScrollPaneVBox.setStyle("-fx-background-color: #000000;");
                 
            contentScrollPane = new ScrollPane(contentScrollPaneVBox);
                contentScrollPane.setStyle("-fx-background-color: #000000;");   
                
        rightVBox.getChildren().addAll(headerText, platformsComboBox, contentScrollPane);
            rightVBox.setSpacing(20);
            rightVBox.setAlignment(Pos.TOP_CENTER);
            
        rightPane.getChildren().addAll(rightVBox);
            rightPane.setAlignment(Pos.TOP_CENTER);
    }
    
    public void genrePane() {
        ObservableList<String> genresList = readGenres(games);
        genresComboBox = new ComboBox(genresList);
        genresComboBox.setOnAction(new GamesMenu.GenreComboBoxHandler());
        genresComboBox.setStyle("-fx-background-color: #FFFF33; -fx-font: 15px \"textFont\";");
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 25).getFont();
        
        Text headerText = new Text("Video Game Genres");
                headerText.setFont(textFont);
                headerText.setFill(Color.WHITE);
        
              
            Text selectText = new Text("Select a genre");
                if (!genresList.isEmpty()) selectText.setText("Select a genre");
                else selectText.setText("No games in library.");
                 selectText.setFont(textFont);
                 selectText.setFill(Color.WHITE);
                 selectText.setTextAlignment(TextAlignment.CENTER);
                 selectText.setWrappingWidth(500);
            
            StackPane stackPane = new StackPane();
                stackPane.setAlignment(Pos.CENTER);
                stackPane.getChildren().add(selectText);
                stackPane.setMinSize(628,200);
                 
            contentScrollPaneVBox = new VBox();     
                 contentScrollPaneVBox.getChildren().add(stackPane);
                 contentScrollPaneVBox.setAlignment(Pos.CENTER);
                 contentScrollPaneVBox.setStyle("-fx-background-color: #000000;");
                 
            contentScrollPane = new ScrollPane(contentScrollPaneVBox);
                contentScrollPane.setStyle("-fx-background-color: #000000;");   
                
        rightVBox.getChildren().addAll(headerText, genresComboBox, contentScrollPane);
            rightVBox.setSpacing(20);
            rightVBox.setAlignment(Pos.TOP_CENTER);
            
        rightPane.getChildren().addAll(rightVBox);
            rightPane.setAlignment(Pos.TOP_CENTER);
    }
    
    public void publisherPane() {
        ObservableList<String> publisherList = readPublishers(games);
        publishersComboBox = new ComboBox(publisherList);
        publishersComboBox.setOnAction(new GamesMenu.PublisherComboBoxHandler());
        publishersComboBox.setStyle("-fx-background-color: #FFFF33; -fx-font: 15px \"textFont\";");
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 25).getFont();
        
        Text headerText = new Text("Video Game Publishers");
                headerText.setFont(textFont);
                headerText.setFill(Color.WHITE);
        
              
            Text selectText = new Text();
                if (!publisherList.isEmpty()) selectText.setText("Select a publisher");
                else selectText.setText("No games in library.");
                 selectText.setFont(textFont);
                 selectText.setFill(Color.WHITE);
                 selectText.setTextAlignment(TextAlignment.CENTER);
                 selectText.setWrappingWidth(500);
            
            StackPane stackPane = new StackPane();
                stackPane.setAlignment(Pos.CENTER);
                stackPane.getChildren().add(selectText);
                stackPane.setMinSize(628,200);
                 
            contentScrollPaneVBox = new VBox();     
                 contentScrollPaneVBox.getChildren().add(stackPane);
                 contentScrollPaneVBox.setAlignment(Pos.CENTER);
                 contentScrollPaneVBox.setStyle("-fx-background-color: #000000;");
                 
            contentScrollPane = new ScrollPane(contentScrollPaneVBox);
                contentScrollPane.setStyle("-fx-background-color: #000000;");   
                
        rightVBox.getChildren().addAll(headerText, publishersComboBox, contentScrollPane);
            rightVBox.setSpacing(20);
            rightVBox.setAlignment(Pos.TOP_CENTER);
            
        rightPane.getChildren().addAll(rightVBox);
            rightPane.setAlignment(Pos.TOP_CENTER);
    }
    
    public void unplayedPane() {
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 30).getFont();
        Font resultsFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 12).getFont();
        
        Games unplayedGames = unplayedGames(games);
        
        Text headerText = new Text("Unplayed Video Games");
                headerText.setFont(textFont);
                headerText.setFill(Color.WHITE);
                
        Text resultsText = new Text("Number of games: " + unplayedGames.getNumGames() + "\n");
                resultsText.setFont(resultsFont);
                resultsText.setFill(Color.WHITE);
        
        if (unplayedGames.getNumGames() > 0) {
            
            contentScrollPane = new ScrollPane();
                contentScrollPane.setStyle("-fx-background-color: #000000;");
            
            contentScrollPaneVBox = new VBox();
                contentScrollPaneVBox.setMaxWidth(620);
                contentScrollPaneVBox.setMinWidth(620);
                contentScrollPaneVBox.setStyle("-fx-background-color: #000000;");
                contentScrollPaneVBox.setAlignment(Pos.TOP_LEFT);
                
            createContentScrollPane(unplayedGames);
            
            rightVBox.getChildren().addAll(headerText, resultsText, contentScrollPane);
            rightVBox.setSpacing(10);
        }
        
        else {
            
            Text empty = new Text("No games in library.");
                empty.setFont(textFont);
                empty.setFill(Color.WHITE);
                empty.setTextAlignment(TextAlignment.CENTER);
                
            rightVBox.getChildren().addAll(headerText, empty);
            rightVBox.setSpacing(200);
        } 
        
        rightVBox.setAlignment(Pos.TOP_CENTER);
            
        rightPane.getChildren().addAll(rightVBox);
            rightPane.setAlignment(Pos.TOP_CENTER);
    }
    
    public void incompletePane() {
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 30).getFont();
        Font resultsFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 12).getFont();
        
        Games incompleteGames = incompleteGames(games);
        
        Text headerText = new Text("Incomplete Video Games");
                headerText.setFont(textFont);
                headerText.setFill(Color.WHITE);
                
        Text resultsText = new Text("Number of games: " + incompleteGames.getNumGames() + "\n");
                resultsText.setFont(resultsFont);
                resultsText.setFill(Color.WHITE);
        
        if (incompleteGames.getNumGames() > 0) {
            
            contentScrollPane = new ScrollPane();
                contentScrollPane.setStyle("-fx-background-color: #000000;");
            
            contentScrollPaneVBox = new VBox();
                contentScrollPaneVBox.setMaxWidth(620);
                contentScrollPaneVBox.setMinWidth(620);
                contentScrollPaneVBox.setStyle("-fx-background-color: #000000;");
                contentScrollPaneVBox.setAlignment(Pos.TOP_LEFT);
                
            createContentScrollPane(incompleteGames);
            
            rightVBox.getChildren().addAll(headerText, resultsText, contentScrollPane);
            rightVBox.setSpacing(10);
        }
        
        else {
            
            Text empty = new Text("No games in library.");
                empty.setFont(textFont);
                empty.setFill(Color.WHITE);
                empty.setTextAlignment(TextAlignment.CENTER);
                
            rightVBox.getChildren().addAll(headerText, empty);
            rightVBox.setSpacing(200);
        } 
        
        rightVBox.setAlignment(Pos.TOP_CENTER);
            
        rightPane.getChildren().addAll(rightVBox);
            rightPane.setAlignment(Pos.TOP_CENTER);
    }
    
    public void completedPane() {
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 30).getFont();
        Font resultsFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 12).getFont();
        
        Games completedGames = completedGames(games);
        
        Text headerText = new Text("Completed Video Games");
                headerText.setFont(textFont);
                headerText.setFill(Color.WHITE);
                
        Text resultsText = new Text("Number of games: " + completedGames.getNumGames() + "\n");
                resultsText.setFont(resultsFont);
                resultsText.setFill(Color.WHITE);
        
        if (completedGames.getNumGames() > 0) {
            
            contentScrollPane = new ScrollPane();
                contentScrollPane.setStyle("-fx-background-color: #000000;");
            
            contentScrollPaneVBox = new VBox();
                contentScrollPaneVBox.setMaxWidth(620);
                contentScrollPaneVBox.setMinWidth(620);
                contentScrollPaneVBox.setStyle("-fx-background-color: #000000;");
                contentScrollPaneVBox.setAlignment(Pos.TOP_LEFT);
                
            createContentScrollPane(completedGames);
            
            rightVBox.getChildren().addAll(headerText, resultsText, contentScrollPane);
            rightVBox.setSpacing(10);
        }
        
        else {
            
            Text empty = new Text("No games in library.");
                empty.setFont(textFont);
                empty.setFill(Color.WHITE);
                empty.setTextAlignment(TextAlignment.CENTER);
                
            rightVBox.getChildren().addAll(headerText, empty);
            rightVBox.setSpacing(200);
        } 
        
        rightVBox.setAlignment(Pos.TOP_CENTER);
            
        rightPane.getChildren().addAll(rightVBox);
            rightPane.setAlignment(Pos.TOP_CENTER);
    }
    
    public void createCategoriesGridPane() {
        
        categoriesGridPane.getChildren().clear();
       
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 20).getFont();

        Button title = new Button("Title");
            title.setFont(textFont);
            title.setTextFill(Color.WHITE);  
            title.setStyle("-fx-background-color: transparent;");
            title.setAlignment(Pos.CENTER);
            title.setOnAction(new GamesMenu.ButtonHandler());
            title.setOnMouseEntered(new GamesMenu.HoverOn());
            title.setOnMouseExited(new GamesMenu.HoverOff());
            
        Button platform = new Button("Platform");
            platform.setFont(textFont);
            platform.setTextFill(Color.WHITE);  
            platform.setStyle("-fx-background-color: transparent;");
            platform.setAlignment(Pos.CENTER);
            platform.setOnAction(new GamesMenu.ButtonHandler());
            platform.setOnMouseEntered(new GamesMenu.HoverOn());
            platform.setOnMouseExited(new GamesMenu.HoverOff());
        
        Button genre = new Button("Genre");
            genre.setFont(textFont);
            genre.setTextFill(Color.WHITE);  
            genre.setStyle("-fx-background-color: transparent;");    
            genre.setAlignment(Pos.CENTER);
            genre.setOnAction(new GamesMenu.ButtonHandler());
            genre.setOnMouseEntered(new GamesMenu.HoverOn());
            genre.setOnMouseExited(new GamesMenu.HoverOff());
            
        Button publisher = new Button("Publisher");
            publisher.setFont(textFont);
            publisher.setTextFill(Color.WHITE);
            publisher.setStyle("-fx-background-color: transparent;");
            publisher.setAlignment(Pos.CENTER);
            publisher.setOnAction(new GamesMenu.ButtonHandler());
            publisher.setOnMouseEntered(new GamesMenu.HoverOn());
            publisher.setOnMouseExited(new GamesMenu.HoverOff());
            
        Button unplayed = new Button("Unplayed");
            unplayed.setFont(textFont);
            unplayed.setTextFill(Color.WHITE);  
            unplayed.setStyle("-fx-background-color: transparent;");
            unplayed.setAlignment(Pos.CENTER);
            unplayed.setOnAction(new GamesMenu.ButtonHandler());
            unplayed.setOnMouseEntered(new GamesMenu.HoverOn());
            unplayed.setOnMouseExited(new GamesMenu.HoverOff());
           
        Button incomplete = new Button("Incomplete");
            incomplete.setFont(textFont);
            incomplete.setTextFill(Color.WHITE);  
            incomplete.setStyle("-fx-background-color: transparent;");
            incomplete.setAlignment(Pos.CENTER);
            incomplete.setOnAction(new GamesMenu.ButtonHandler());
            incomplete.setOnMouseEntered(new GamesMenu.HoverOn());
            incomplete.setOnMouseExited(new GamesMenu.HoverOff());
            
        Button completed = new Button("Completed");
            completed.setFont(textFont);
            completed.setTextFill(Color.WHITE);  
            completed.setStyle("-fx-background-color: transparent;");
            completed.setAlignment(Pos.CENTER);
            completed.setOnAction(new GamesMenu.ButtonHandler());
            completed.setOnMouseEntered(new GamesMenu.HoverOn());
            completed.setOnMouseExited(new GamesMenu.HoverOff());
        
            titleBack = new ImagePane(imagesPath + "pacmanBar.png", w, h);
            platformBack = new ImagePane(imagesPath + "pacmanBar.png", w, h);
            genreBack = new ImagePane(imagesPath + "pacmanBar.png", w, h);
            publisherBack = new ImagePane(imagesPath + "pacmanBar.png", w, h);
            unplayedBack = new ImagePane(imagesPath + "pacmanBar.png", w, h);
            incompleteBack = new ImagePane(imagesPath + "pacmanBar.png", w, h);
            completedBack = new ImagePane(imagesPath + "pacmanBar.png", w, h);
        
            titleBack.setVisible(false);
            platformBack.setVisible(false);
            genreBack.setVisible(false);
            publisherBack.setVisible(false);
            unplayedBack.setVisible(false);
            incompleteBack.setVisible(false);
            completedBack.setVisible(false);
        
        categoriesGridPane.add(titleBack, 0, 0);
        categoriesGridPane.add(platformBack, 1, 1);
        categoriesGridPane.add(genreBack, 0, 2);
        categoriesGridPane.add(publisherBack, 1, 3);
        categoriesGridPane.add(unplayedBack, 0, 4);
        categoriesGridPane.add(incompleteBack, 1, 5);
        categoriesGridPane.add(completedBack, 0, 6);
        
        categoriesGridPane.add(title, 0, 0);
        categoriesGridPane.add(platform, 1, 1);
        categoriesGridPane.add(genre, 0, 2);
        categoriesGridPane.add(publisher, 1, 3);
        categoriesGridPane.add(unplayed, 0, 4);
        categoriesGridPane.add(incomplete, 1, 5);
        categoriesGridPane.add(completed, 0, 6);
        
            categoriesGridPane.setHalignment(title, HPos.CENTER);
            categoriesGridPane.setHalignment(platform, HPos.CENTER);
            categoriesGridPane.setHalignment(genre, HPos.CENTER);
            categoriesGridPane.setHalignment(publisher,  HPos.CENTER);
            categoriesGridPane.setHalignment(unplayed,  HPos.CENTER);
            categoriesGridPane.setHalignment(incomplete, HPos.CENTER);
            categoriesGridPane.setHalignment(completed, HPos.CENTER);
        
        categoriesGridPane.setAlignment(Pos.CENTER);
        
    }
    
    public void createContentScrollPane(Games gamesList) {
        
            contentScrollPaneVBox.getChildren().clear();
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 14).getFont();
        
        Games sortedGames = sortGames(gamesList);

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
                    gameButton.setOnAction(new GamesMenu.ButtonHandler());
                    gameButton.setOnMouseEntered(new GamesMenu.HoverOn());
                    gameButton.setOnMouseExited(new GamesMenu.HoverOff());

                contentScrollPaneVBox.getChildren().add(gameButton);
            }

                if (n > 11) wrappingWidth = 620;
                else wrappingWidth = 604;
                
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
                            gameButton.setOnAction(new GamesMenu.ButtonHandler());
                            gameButton.setOnMouseEntered(new GamesMenu.HoverOn());
                            gameButton.setOnMouseExited(new GamesMenu.HoverOff());

                        contentScrollPaneVBox.getChildren().add(gameButton);
                    }
            }
            else {
                
                Text noResults = new Text("No results found.");
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

                if (n > 11) wrappingWidth = 620;
                else wrappingWidth = 604;
                
                if (n == 1) {
                    contentScrollPane.setMinViewportHeight(10);
                    contentScrollPane.setMaxHeight(12);
                }
                else {
                    contentScrollPane.setMinViewportHeight(USE_PREF_SIZE);
                    contentScrollPane.setMaxHeight(USE_PREF_SIZE);
                }
                
                
                contentScrollPane.setContent(contentScrollPaneVBox);
                    contentScrollPane.setMinViewportWidth(wrappingWidth);
                    contentScrollPane.setMaxWidth(wrappingWidth);
        }
        
        else createContentScrollPane(games);
    }
    
    public class TitleComboBoxHandler implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {

            String selection = (String) titlesComboBox.getValue();
            
            contentScrollPaneVBox.getChildren().clear();
                contentScrollPaneVBox.setSpacing(10);
            
            Games specificTitleList = specificTitle(games, selection);
            int n = specificTitleList.getNumGames();
            
            for (int i = 0; i < n; i++) {
                GamePane gamePane = new GamePane(specificTitleList.getGame(i));
                contentScrollPaneVBox.getChildren().add(gamePane);
            }
            
            int wrappingWidth;
            
            if (n > 1) wrappingWidth = 525;
            else wrappingWidth = 509;
                
                if (n == 1) {
                    contentScrollPane.setMinViewportHeight(254);
                    contentScrollPane.setMaxHeight(254);
                }
                else {
                    contentScrollPane.setMinViewportHeight(USE_PREF_SIZE);
                    contentScrollPane.setMaxHeight(USE_PREF_SIZE);
                }
                
            contentScrollPane.setContent(contentScrollPaneVBox);
                    contentScrollPane.setMinViewportWidth(wrappingWidth);
                    contentScrollPane.setMaxWidth(wrappingWidth);
        }
    
    }
    
    public class PlatformComboBoxHandler implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {

            String selection = (String) platformsComboBox.getValue();
            
            contentScrollPaneVBox.getChildren().clear();
                contentScrollPaneVBox.setSpacing(10);
            
            Games specificPlatformList = specificPlatform(games, selection);
            int n = specificPlatformList.getNumGames();
            
            for (int i = 0; i < n; i++) {
                GamePane gamePane = new GamePane(specificPlatformList.getGame(i));
                contentScrollPaneVBox.getChildren().add(gamePane);
            }
            
            int wrappingWidth;
            
            if (n > 1) wrappingWidth = 525;
            else wrappingWidth = 509;
                
                if (n == 1) {
                    contentScrollPane.setMinViewportHeight(254);
                    contentScrollPane.setMaxHeight(254);
                }
                else {
                    contentScrollPane.setMinViewportHeight(USE_PREF_SIZE);
                    contentScrollPane.setMaxHeight(USE_PREF_SIZE);
                }
                
            contentScrollPane.setContent(contentScrollPaneVBox);
                    contentScrollPane.setMinViewportWidth(wrappingWidth);
                    contentScrollPane.setMaxWidth(wrappingWidth);
        }
    
    }
    
    public class GenreComboBoxHandler implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {

            String selection = (String) genresComboBox.getValue();
            
            contentScrollPaneVBox.getChildren().clear();
                contentScrollPaneVBox.setSpacing(10);
            
            Games specificGenreList = specificGenre(games, selection);
            int n = specificGenreList.getNumGames();
            
            for (int i = 0; i < n; i++) {
                GamePane gamePane = new GamePane(specificGenreList.getGame(i));
                contentScrollPaneVBox.getChildren().add(gamePane);
            }
            
            int wrappingWidth;
            
            if (n > 1) wrappingWidth = 525;
            else wrappingWidth = 509;
                
                if (n == 1) {
                    contentScrollPane.setMinViewportHeight(254);
                    contentScrollPane.setMaxHeight(254);
                }
                else {
                    contentScrollPane.setMinViewportHeight(USE_PREF_SIZE);
                    contentScrollPane.setMaxHeight(USE_PREF_SIZE);
                }
                
            contentScrollPane.setContent(contentScrollPaneVBox);
                    contentScrollPane.setMinViewportWidth(wrappingWidth);
                    contentScrollPane.setMaxWidth(wrappingWidth);
        }
    
    }
    
    public class PublisherComboBoxHandler implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {

            String selection = (String) publishersComboBox.getValue();
            
            contentScrollPaneVBox.getChildren().clear();
                contentScrollPaneVBox.setSpacing(10);
            
            Games specificPublisherList = specificPublisher(games, selection);
            int n = specificPublisherList.getNumGames();
            
            for (int i = 0; i < n; i++) {
                GamePane gamePane = new GamePane(specificPublisherList.getGame(i));
                contentScrollPaneVBox.getChildren().add(gamePane);
            }
            
            int wrappingWidth;
            
            if (n > 1) wrappingWidth = 525;
            else wrappingWidth = 509;
                
                if (n == 1) {
                    contentScrollPane.setMinViewportHeight(254);
                    contentScrollPane.setMaxHeight(254);
                }
                else {
                    contentScrollPane.setMinViewportHeight(USE_PREF_SIZE);
                    contentScrollPane.setMaxHeight(USE_PREF_SIZE);
                }
                
            contentScrollPane.setContent(contentScrollPaneVBox);
                    contentScrollPane.setMinViewportWidth(wrappingWidth);
                    contentScrollPane.setMaxWidth(wrappingWidth);
        }
    
    }
    
    public class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("Back")) mainMenu();
            else if (sourceButton.equals("View Master List")) createRightPane(1);
            else if (sourceButton.equals("View Categories")) createRightPane(2);
            else if (sourceButton.equals("Add Game")) addGame();
            else if (sourceButton.equals("Edit Game")) editGame();
            else if (sourceButton.equals("Delete Game")) deleteGame();
            else if (sourceButton.equals("Search")) search();
            else if (sourceButton.equals("Random Game")) randomGame();
            
            else if (sourceButton.equals("Title")) createRightPane(3);
            else if (sourceButton.equals("Platform")) createRightPane(4);
            else if (sourceButton.equals("Genre")) createRightPane(5);
            else if (sourceButton.equals("Publisher")) createRightPane(6);
            else if (sourceButton.equals("Unplayed")) createRightPane(7);
            else if (sourceButton.equals("Incomplete")) createRightPane(8);
            else if (sourceButton.equals("Completed")) createRightPane(9);
            
            else if (sourceButton.equals("Download\nList")) {
                
                    printGames(games);
                
                    dialogStage = new Stage();
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.setResizable(false);
                    dialogStage.initStyle(StageStyle.UNDECORATED);
                    
                    Font font = new CustomFont(fontsPath + "pacmanArcade.ttf", 20).getFont();
        
                        Text text = new Text("Master Games List has been successfully\ndownloaded to your desktop.");
                            text.setFont(font);
                            text.setFill(Color.YELLOW);
                            text.setTextAlignment(TextAlignment.CENTER);
                            
                            
                     randomButton2 = new Button("randomButton2");
                        randomButton2.setPrefSize(850,150);
                        randomButton2.setStyle("-fx-background-color: #66000000;");
                        randomButton2.setTextFill(Color.TRANSPARENT);
                        randomButton2.setOnMouseExited(new GamesMenu.HoverOff());
                        randomButton2.setOnAction(new GamesMenu.ButtonHandler());
                        
                        

                    StackPane sp = new StackPane(text, randomButton2);
                        sp.setAlignment(Pos.CENTER);
                        sp.setStyle("-fx-background-color: #F18331;");

                    dialogScene = new Scene(sp);
                    dialogStage.setScene(dialogScene);
                    dialogStage.show();
                    
                    try {
                            Robot robot = new Robot();
                            Bounds bounds = sp.localToScreen(sp.getBoundsInLocal());
                                int x = ((int) bounds.getMaxX()) - (int) bounds.getWidth() / 2;
                                int y = ((int) bounds.getMaxY()) - (int) bounds.getHeight() / 2;
                                robot.mouseMove(x, y);
                        }
                    catch(AWTException ex) {
                            System.exit(-1);
                        }
            }
            
            else if (sourceButton.equals("Clear")) {
                searchField.clear();
                createContentScrollPane(games);
            }
            
            
            else {
                games.setSelectedGame(games.getGameMap().get(sourceButton));
                selectedGame();
            }
            
            

        }
    }
    
    public class HoverOn implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            creditPlayer.stop();
            creditPlayer.play();

            if (sourceButton.equals("Back")) back.setVisible(true);
            else if (sourceButton.equals("View Master List")) viewMaster.setVisible(true);
            else if (sourceButton.equals("View Categories")) viewCategories.setVisible(true);
            else if (sourceButton.equals("Add Game")) add.setVisible(true);
            else if (sourceButton.equals("Edit Game")) edit.setVisible(true);
            else if (sourceButton.equals("Delete Game")) delete.setVisible(true);
            
            else if (sourceButton.equals("Title")) titleBack.setVisible(true);
            else if (sourceButton.equals("Platform")) platformBack.setVisible(true);             
            else if (sourceButton.equals("Genre")) genreBack.setVisible(true); 
            else if (sourceButton.equals("Publisher")) publisherBack.setVisible(true);
            else if (sourceButton.equals("Unplayed")) unplayedBack.setVisible(true);
            else if (sourceButton.equals("Incomplete")) incompleteBack.setVisible(true);
            else if (sourceButton.equals("Completed")) completedBack.setVisible(true);
            
            else button.setTextFill(Color.RED);
            

        }
    }
    
    public class HoverOff implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("Back")) back.setVisible(false);
            else if (sourceButton.equals("View Master List")) viewMaster.setVisible(false);
            else if (sourceButton.equals("View Categories")) viewCategories.setVisible(false);
            else if (sourceButton.equals("Add Game")) add.setVisible(false);
            else if (sourceButton.equals("Edit Game")) edit.setVisible(false);
            else if (sourceButton.equals("Delete Game")) delete.setVisible(false); 
            
            else if (sourceButton.equals("Title")) titleBack.setVisible(false);
            else if (sourceButton.equals("Platform")) platformBack.setVisible(false);             
            else if (sourceButton.equals("Genre")) genreBack.setVisible(false); 
            else if (sourceButton.equals("Publisher")) publisherBack.setVisible(false);
            else if (sourceButton.equals("Unplayed")) unplayedBack.setVisible(false);
            else if (sourceButton.equals("Incomplete")) incompleteBack.setVisible(false);
            else if (sourceButton.equals("Completed")) completedBack.setVisible(false);
            
            else if (sourceButton.equals("randomButton")) {
                newScene.getWindow().hide();
                primaryStage.getScene().getWindow().hide();
            }
            
            else if (sourceButton.equals("randomButton2")) {
                dialogScene.getWindow().hide();
                dialogStage.getScene().getWindow().hide();
            }
            
            else button.setTextFill(Color.WHITE);
            
        }
    }
    
    public void mainMenu() {
        backgroundMediaPlayer.stop();
        this.setVisible(false);
        this.getScene().getWindow().hide();

        primaryStage = new Stage();
        MainMenu mm = new MainMenu(games, platforms);
        newScene = new Scene(mm);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();

    }
    
    public void randomGame() {

        StackPane randomPane = new StackPane();
        
        randomButton = new Button("randomButton");
            randomButton.setPrefSize(775,350);
            randomButton.setStyle("-fx-background-color: #66000000;");
            randomButton.setTextFill(Color.TRANSPARENT);
            randomButton.setOnMouseExited(new GamesMenu.HoverOff());
            randomButton.setOnAction(new GamesMenu.ButtonHandler());
        
        GamePane gamePane = new GamePane(randomGame, 250, 325, 15, 175, 300);
            gamePane.setStyle("-fx-background-color: #F18331;");
            randomPane.getChildren().addAll(gamePane,randomButton);
      
        primaryStage = new Stage();
        newScene = new Scene(randomPane);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Random Game You Should Play");
        primaryStage.setScene(newScene);
        primaryStage.show();
        primaryStage.setY(300);
        
        try {
            Robot robot = new Robot();
            Bounds bounds = randomPane.localToScreen(randomPane.getBoundsInLocal());
                int x = ((int) bounds.getMaxX()) - (int) bounds.getWidth() / 2;
                int y = ((int) bounds.getMaxY()) - (int) bounds.getHeight() / 2;
                robot.mouseMove(x, y);
        }
        catch(AWTException ex) {
            System.exit(-1);
        }

    }
    
    public void selectedGame() {
                
        StackPane randomPane = new StackPane();
        
        randomButton = new Button("randomButton");
            randomButton.setPrefSize(775,350);
            randomButton.setStyle("-fx-background-color: #66000000;");
            randomButton.setTextFill(Color.TRANSPARENT);
            randomButton.setOnMouseExited(new GamesMenu.HoverOff());
            randomButton.setOnAction(new GamesMenu.ButtonHandler());
        
        GamePane gamePane = new GamePane(games.getSelectedGame(), 250, 325, 15, 175, 300);
            gamePane.setStyle("-fx-background-color: #F18331;");
            randomPane.getChildren().addAll(gamePane,randomButton);
      
        primaryStage = new Stage();
        newScene = new Scene(randomPane);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setTitle(games.getSelectedGame().getTitle());
        primaryStage.setScene(newScene);
        primaryStage.show();
        primaryStage.setY(300);
        
        try {
            Robot robot = new Robot();
            Bounds bounds = randomPane.localToScreen(randomPane.getBoundsInLocal());
                int x = ((int) bounds.getMaxX()) - (int) bounds.getWidth() / 2;
                int y = ((int) bounds.getMaxY()) - (int) bounds.getHeight() / 2;
                robot.mouseMove(x, y);
        }
        catch(AWTException ex) {
            System.exit(-1);
        }

    }
    
    public void addGame() {
        backgroundMediaPlayer.stop();
        this.setVisible(false);
        this.getScene().getWindow().hide();

        primaryStage = new Stage();
        AddGame ag = new AddGame(games, platforms);
        newScene = new Scene(ag);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();

    }
    
    public void deleteGame() {
        backgroundMediaPlayer.stop();
        this.setVisible(false);
        this.getScene().getWindow().hide();

        primaryStage = new Stage();
        DeleteGame dg = new DeleteGame(games, platforms);
        newScene = new Scene(dg);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();

    }
    
    public void editGame() {
        backgroundMediaPlayer.stop();
        this.setVisible(false);
        this.getScene().getWindow().hide();

        primaryStage = new Stage();
        EditGame eg = new EditGame(games, platforms);
        newScene = new Scene(eg);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();

    }
}
