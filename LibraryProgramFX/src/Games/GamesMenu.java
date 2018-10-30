package Games;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import static javafx.scene.layout.BackgroundPosition.CENTER;
import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;
import static javafx.scene.layout.BackgroundRepeat.REPEAT;
import javafx.scene.layout.BackgroundSize;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import libraryprogram.Game;
import Necessities.BaseWindow;
import Necessities.FontGenerator;
import Necessities.ImagePane;
import MainMenu.MainMenu;
import static Necessities.IO.completedGames;
import static Necessities.IO.downloadGames;
import static Necessities.IO.genreSelection;
import static Necessities.IO.incompleteGames;
import static Necessities.IO.platformSelection;
import static Necessities.IO.publisherSelection;
import static Necessities.IO.readGenres;
import static Necessities.IO.readPlatforms;
import static Necessities.IO.readPublishers;
import static Necessities.IO.readTitles;
import static Necessities.IO.searchGames;
import static Necessities.IO.titleSelection;
import static Necessities.IO.unplayedGames;
import java.text.ParseException;
import java.util.ArrayList;
import static libraryprogram.LibraryIO.writeGames;
import static libraryprogram.LibraryIO.writePlatforms;
import libraryprogram.Platform;

public class GamesMenu extends BaseWindow {
    private final Font titleFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 35).getFont();

    private StackPane platformCount; 
    private StackPane box;
    private HBox gameCount; 
    private VBox gamesMenuVBox;
    private HBox optionsHBox;
    private int selectedGameIndex;
    private GameInfoPane gameInfoPane;
    
    private ImagePane viewMasterList;
    private ImagePane viewCategories;
    private ImagePane addGame;
    private ImagePane back;
    
    private GridPane viewMasterList2;
    private GridPane viewCategories2;
    private GridPane addGame2;
    private GridPane back2;
    
    private int position = 0;
    private boolean direction = true;
    
    public StackPane viewCategoriesStackPane;
    public StackPane viewMasterListStackPane;
    private StackPane addGameStackPane;

    private StackPane backStackPane;
    private VBox frontVBox;
    private HBox contentHBox;
    
    private StackPane titleStackPane;
    private StackPane emptyStackPane;
    private Text title;
    private Text topText;
    
    private ScrollPane scrollPane;
    private VBox scrollPaneContent;
    
    private TextField searchField;
    private HBox searchHBox;
    private VBox searchVBox;
    
    private GridPane categoriesGridPane;
    private VBox categoriesVBox;
    
    private ObservableList<String> observableList;
    private ComboBox comboBox;

    private Button deleteButton;
    private Button editButton;
    private Button closeButton;
    
    private StackPane optionsStackPane;
    
    private Text gameInfoText;
    private boolean gameInfoBoolean;
    private StackPane gameInfoStackPane;
    private EditGamePane editGamePane;
    
    public GamesMenu () {

        imagesPath = "/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/images/gamesmenu/";
        createBackground();
        createOptionsPane();
        createBackgroundLayers();
        createTitleVBox();
        createScrollPaneAndContent();
        this.getChildren().addAll(platformCount, gameCount, gamesMenuVBox, box, frontVBox);
    }
    
    @Override
    public final void createBackground() {
        Image image = new Image (new File(imagesPath + "background.png").toURI().toString());
        BackgroundSize backgroundSize = new BackgroundSize(width, height, false, false, false, true);
        this.setBackground(new Background(new BackgroundImage(image, REPEAT, NO_REPEAT, CENTER, backgroundSize)));    
    }
    
    private void createTitleVBox() {
        gamesMenuVBox = new VBox();
       
        Font headerFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 100).getFont();
        Font smallerheaderFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 40).getFont();
        
        
        StackPane h1 = new StackPane();
        Text headerText = new Text("Video Game");
            headerText.setFont(smallerheaderFont);
            headerText.setFill(Color.WHITE);
            headerText.setTextAlignment(TextAlignment.LEFT);
            h1.getChildren().add(headerText);
            h1.setAlignment(Pos.CENTER);
            h1.setPadding(new Insets(0,225,0,0));
        
        StackPane h2 = new StackPane();
        Text headerText2 = new Text("Main Menu");
            headerText2.setFont(headerFont);
            headerText2.setFill(Color.WHITE);
            headerText2.setTextAlignment(TextAlignment.CENTER);
            h2.getChildren().add(headerText2);
            h2.setAlignment(Pos.CENTER);

        gamesMenuVBox.getChildren().addAll(h1, h2);
        gamesMenuVBox.setAlignment(Pos.TOP_CENTER);
        gamesMenuVBox.setStyle("-fx-background-color: #66000000;");
        gamesMenuVBox.setMaxSize(100, 100);
        gamesMenuVBox.setPadding(new Insets(0,300,550,0));
    
    }
   
    private void createScrollPaneAndContent() {
            scrollPane = new ScrollPane();
                scrollPane.setMinViewportHeight(235);
                scrollPane.setMinViewportWidth(630);

                scrollPane.setMaxHeight(245);
                scrollPane.setMinHeight(245);
                scrollPane.setMaxWidth(640);
                scrollPane.setMinWidth(640);
                
            scrollPaneContent = new VBox();    
                scrollPaneContent.setStyle("-fx-background-color: transparent;");
    }
    
    private void createBackgroundLayers() {
                
        platformCount = new StackPane();
        createPlatformCount();
        
        gameCount = new HBox();
        createGameCount();
        
         box = new StackPane();
            box.getChildren().add(new ImagePane(imagesPath + "box.gif", 70, 70));
            box.setPadding(new Insets(135,0,0,855));
        
        contentHBox = new HBox();
            contentHBox.setSpacing(40);
            contentHBox.setAlignment(Pos.CENTER_LEFT);
            contentHBox.setMinHeight(335);
            contentHBox.setMaxHeight(335);
            
        titleStackPane = new StackPane();
            titleStackPane.setMinSize(230,335);
            titleStackPane.setMaxSize(230,335);
            titleStackPane.setAlignment(Pos.CENTER_RIGHT);
            
        frontVBox = new VBox();
            frontVBox.getChildren().addAll(contentHBox, optionsHBox);
            frontVBox.setAlignment(Pos.CENTER);
            frontVBox.setPadding(new Insets(60,0,0,0));
        
        if (hideOptions()) createContentPane(0);
        else createContentPane(1);
    }
    
    private boolean hideOptions() {
        
        if (games.getNumGames() == 0) {
            viewCategoriesStackPane.setVisible(false);
            viewMasterListStackPane.setVisible(false);
            return true;
        }
        else {
            viewCategoriesStackPane.setVisible(true);
            viewMasterListStackPane.setVisible(true);
            return false;
        }
    }
        
    private void createContentPane(int value) {
  
        contentHBox.getChildren().clear();
        titleStackPane.getChildren().clear();
        
        hideOptions();
          
        switch (value) {
            case 0:
                title = new Text ("Your library is empty.\nAdd a game to get started!"); 
                title.setFont(titleFont);
                title.setFill(Color.WHITE);
                title.setTextAlignment(TextAlignment.CENTER);
                emptyStackPane = new StackPane();
                    emptyStackPane.setAlignment(Pos.CENTER);
                    emptyStackPane.setPadding(new Insets(0,0,0,375));
                    emptyStackPane.getChildren().add(title);
                contentHBox.getChildren().addAll(emptyStackPane);
                break;
            case 1:
                Game game = games.getGame(new Random().nextInt(games.getNumGames()));
                title = new Text ("Random Game\nYou Should\nPlay"); 
                title.setFont(titleFont);
                title.setFill(Color.WHITE);
                title.setTextAlignment(TextAlignment.RIGHT);
                GameInfoPane gip = new GameInfoPane(game, 30, 20);
                gip.setPadding(new Insets(25,0,0,0));
                titleStackPane.getChildren().add(title);
                contentHBox.getChildren().addAll(titleStackPane, gip);
                break;
            case 2:
                title = new Text ("View\nMaster\nGame List"); 
                title.setFont(titleFont);
                title.setFill(Color.WHITE);
                title.setTextAlignment(TextAlignment.RIGHT);
                games.sortGames();
                createScrollPaneContent(games.getGames());
                createSearchBar();
                VBox middleVBox = new VBox();
                middleVBox.setAlignment(Pos.CENTER);
                middleVBox.setSpacing(10);
                middleVBox.getChildren().addAll(searchHBox,scrollPane);
                titleStackPane.getChildren().add(title);
                contentHBox.getChildren().addAll(titleStackPane, middleVBox);
                break;
            case 3:
                title = new Text ("View\nCategories"); 
                title.setFont(titleFont);
                title.setFill(Color.WHITE);
                title.setTextAlignment(TextAlignment.RIGHT);
                topText = new Text("Choose A Category");
                topText.setFont(titleFont);
                topText.setFill(Color.web("#F1E14B"));
                topText.setTextAlignment(TextAlignment.CENTER);
                categoriesGridPane = new GridPane();
                createCategoriesGridPane();
                categoriesVBox = new VBox();
                categoriesVBox.setAlignment(Pos.CENTER);
                categoriesVBox.setSpacing(10);
                categoriesVBox.getChildren().addAll(topText, categoriesGridPane);
                categoriesVBox.setPadding(new Insets(0,0,0,0));
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(categoriesVBox);
                titleStackPane.getChildren().add(title);
                contentHBox.getChildren().addAll(titleStackPane, stackPane);
                
                break; 
            case 4:
                title = new Text ("Add A\nNew Game"); 
                title.setFont(titleFont);
                title.setFill(Color.WHITE);
                title.setTextAlignment(TextAlignment.RIGHT);
                AddGame addNewGame = new AddGame(games, platforms);
                titleStackPane.getChildren().add(title);
                contentHBox.getChildren().addAll(titleStackPane, addNewGame);
                break; 
            default:
                break;
        }
           
    }
    
    private void updateCategoriesVBox(int version) {
        categoriesVBox.getChildren().clear();
        scrollPaneContent.getChildren().clear();
        
        switch (version) {
            case 1:
                topText = new Text("Select a Letter or Character");
                topText.setFont(titleFont);
                topText.setFill(Color.web("#F1E14B"));
                topText.setTextAlignment(TextAlignment.CENTER);
                observableList = readTitles(games);
                comboBox = new ComboBox(observableList);
                comboBox.setOnAction(new GamesMenu.TitleComboBoxHandler());
                categoriesVBox.getChildren().addAll(topText, comboBox, scrollPane);
                categoriesVBox.setAlignment(Pos.TOP_CENTER);
                categoriesVBox.setPadding(new Insets(0,0,10,0));
                break;
            case 2:
                topText = new Text("Select a Platform");
                topText.setFont(titleFont);
                topText.setFill(Color.web("#F1E14B"));
                topText.setTextAlignment(TextAlignment.CENTER);
                observableList = readPlatforms(platforms);
                comboBox = new ComboBox(observableList);
                comboBox.setOnAction(new GamesMenu.PlatformComboBoxHandler());
                categoriesVBox.getChildren().addAll(topText, comboBox, scrollPane);
                categoriesVBox.setAlignment(Pos.TOP_CENTER);
                categoriesVBox.setPadding(new Insets(0,0,10,0));
                break;
            case 3:
                topText = new Text("Select a Genre");
                topText.setFont(titleFont);
                topText.setFill(Color.web("#F1E14B"));
                topText.setTextAlignment(TextAlignment.CENTER);
                observableList = readGenres(games);
                comboBox = new ComboBox(observableList);
                comboBox.setOnAction(new GamesMenu.GenresComboBoxHandler());
                categoriesVBox.getChildren().addAll(topText, comboBox, scrollPane);
                categoriesVBox.setAlignment(Pos.TOP_CENTER);
                categoriesVBox.setPadding(new Insets(0,0,10,0));
                break;
            case 4:
                topText = new Text("Select a Publisher");
                topText.setFont(titleFont);
                topText.setFill(Color.web("#F1E14B"));
                topText.setTextAlignment(TextAlignment.CENTER);
                observableList = readPublishers(games);
                comboBox = new ComboBox(observableList);
                comboBox.setOnAction(new GamesMenu.PublishersComboBoxHandler());
                categoriesVBox.getChildren().addAll(topText, comboBox, scrollPane);
                categoriesVBox.setAlignment(Pos.TOP_CENTER);
                categoriesVBox.setPadding(new Insets(0,0,10,0));
                break;
            case 5:
                {
                    topText = new Text("Unplayed Games");
                    topText.setFont(titleFont);
                    topText.setFill(Color.web("#F1E14B"));
                    topText.setTextAlignment(TextAlignment.CENTER);
                    Text secondaryText = new Text("Number of unplayed games: " + unplayedGames(games).size());
                    secondaryText.setFont(new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont());
                    secondaryText.setFill(Color.web("#F1E14B"));
                    secondaryText.setTextAlignment(TextAlignment.CENTER);
                    createScrollPaneContent(unplayedGames(games));
                    categoriesVBox.getChildren().addAll(topText, secondaryText, scrollPane);
                    categoriesVBox.setAlignment(Pos.TOP_CENTER);
                    categoriesVBox.setPadding(new Insets(0,0,0,0));

                    break;
                }
            case 6:
                {
                    topText = new Text("Completed Games");
                    topText.setFont(titleFont);
                    topText.setFill(Color.web("#F1E14B"));
                    topText.setTextAlignment(TextAlignment.CENTER);
                    Text secondaryText = new Text("Number of completed games: " + completedGames(games).size());
                    secondaryText.setFont(new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont());
                    secondaryText.setFill(Color.web("#F1E14B"));
                    secondaryText.setTextAlignment(TextAlignment.CENTER);
                    createScrollPaneContent(completedGames(games));
                    categoriesVBox.getChildren().addAll(topText, secondaryText, scrollPane);
                    categoriesVBox.setAlignment(Pos.TOP_CENTER);
                    categoriesVBox.setPadding(new Insets(0,0,0,0));

                    break;
                }
            case 7:
                {
                    topText = new Text("Incomplete Games");
                    topText.setFont(titleFont);
                    topText.setFill(Color.web("#F1E14B"));
                    topText.setTextAlignment(TextAlignment.CENTER);
                    Text secondaryText = new Text("Number of incomplete games: " + incompleteGames(games).size());
                    secondaryText.setFont(new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont());
                    secondaryText.setFill(Color.web("#F1E14B"));
                    secondaryText.setTextAlignment(TextAlignment.CENTER);
                    createScrollPaneContent(incompleteGames(games));
                    categoriesVBox.getChildren().addAll(topText, secondaryText, scrollPane);
                    categoriesVBox.setAlignment(Pos.TOP_CENTER);
                    break;
                }
            default:
                break;
        } 
        
    }
    
    private GridPane createCategoriesGridPane() {
        
        categoriesGridPane.getChildren().clear();
       
        Font textFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 28).getFont();

        Button titleC = new Button("Title");
            titleC.setFont(textFont);
            titleC.setTextFill(Color.WHITE);  
            titleC.setStyle("-fx-background-color: transparent;");
            titleC.setAlignment(Pos.CENTER);
            titleC.setOnAction(new GamesMenu.ButtonHandler());
            titleC.setOnMouseEntered(new GamesMenu.HoverOn());
            titleC.setOnMouseExited(new GamesMenu.HoverOff());
            
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

        
        categoriesGridPane.add(titleC, 0, 2);
        categoriesGridPane.add(platform, 0, 4);
        categoriesGridPane.add(genre, 1, 0);
        categoriesGridPane.add(publisher, 1, 3);
        categoriesGridPane.add(unplayed, 1, 6);
        categoriesGridPane.add(incomplete, 2, 2);
        categoriesGridPane.add(completed, 2, 4);
        
            categoriesGridPane.setHalignment(titleC, HPos.CENTER);
            categoriesGridPane.setHalignment(platform, HPos.CENTER);
            categoriesGridPane.setHalignment(genre, HPos.CENTER);
            categoriesGridPane.setHalignment(publisher,  HPos.CENTER);
            categoriesGridPane.setHalignment(unplayed,  HPos.CENTER);
            categoriesGridPane.setHalignment(incomplete, HPos.CENTER);
            categoriesGridPane.setHalignment(completed, HPos.CENTER);
        
        categoriesGridPane.setAlignment(Pos.CENTER);
        categoriesGridPane.setStyle("-fx-border-color: #F1E14B; -fx-border-width: 5px;");
        categoriesGridPane.setMinSize(600, 275);
        categoriesGridPane.setMaxSize(600, 275);
        
        return categoriesGridPane;
    }
    
    private void createScrollPaneContent(ArrayList<Game> g) {
        
        scrollPaneContent.getChildren().clear();

            String gameText;
            Button gameButton;
            Font textFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 18).getFont();
            
            for (int i = 0; i < g.size(); i++) {

            gameText = g.get(i).getTitle(); 

                gameButton = new Button(gameText);
                    gameButton.setFont(textFont);
                    gameButton.setTextFill(Color.WHITE);
                    gameButton.setAlignment(Pos.CENTER_LEFT);
                    gameButton.setStyle("-fx-background-color: transparent;");
                    gameButton.setOnAction(new GamesMenu.ButtonHandler());
                    gameButton.setOnMouseEntered(new GamesMenu.HoverOn());
                    gameButton.setOnMouseExited(new GamesMenu.HoverOff());

                scrollPaneContent.getChildren().add(gameButton);
               
            }
            
            scrollPane.setContent(scrollPaneContent);
            
    }
    
    private void createSearchBar() {
       
        Font searchBar = new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont();
        Font searchFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 18).getFont();
                    
        searchField = new TextField();
            searchField.setFont(searchBar);
            searchField.setPrefSize(400, 20);
            
        Button downloadButton = new Button("Download");
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
    }
    
    private void createPlatformCount() {
        
        Font textFont = new FontGenerator(fontsPath + "joystick.ttf", 30).getFont();

        Text numConsoles = new Text();
            numConsoles.setFont(textFont);
            numConsoles.setFill(Color.WHITE);
            
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            final Calendar cal = Calendar.getInstance();
            String numberConsoles = String.format("%02d", platforms.getNumPlatforms()); // 0 fill to 9 digits num 
            numConsoles.setText(numberConsoles);
        }));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
            
            platformCount.getChildren().add(numConsoles);
            platformCount.setAlignment(Pos.TOP_LEFT);
            platformCount.setPadding(new Insets(33,0,0,110));
    }
    
    private void createGameCount() {
       Font textFont = new FontGenerator(fontsPath + "joystick.ttf", 30).getFont();
        
        ImagePane clock = new ImagePane(imagesPath + "clock.png", 45, 35);
            clock.setAlignment(Pos.TOP_RIGHT);
            clock.setPadding(new Insets(0,0,670,0));
        
        Text time = new Text();
            time.setFont(textFont);
            time.setFill(Color.WHITE);
            
        Text numGames = new Text();
            numGames.setFont(textFont);
            numGames.setFill(Color.WHITE);
            
        StackPane gameNumber = new StackPane();
            gameNumber.getChildren().add(numGames);
            gameNumber.setAlignment(Pos.TOP_RIGHT);
        
        final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            final Calendar cal = Calendar.getInstance();
            String numberGames = String.format("%09d", games.getNumGames()); // 0 fill to 9 digits num   
            time.setText(timeFormat.format(cal.getTime()));
            numGames.setText(numberGames);
            hideOptions();
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        gameCount.getChildren().addAll(gameNumber, clock, time);
        gameCount.setAlignment(Pos.TOP_RIGHT);
        gameCount.setPadding(new Insets(38,20,0,0));
        gameCount.setSpacing(10);
    }
    
    private void createOptionsPane() {
                
        Font textFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 18).getFont();
        
        Button viewMasterListButton= new Button("View\nMaster List");
            viewMasterListButton.setFont(textFont);
            viewMasterListButton.setTextAlignment(TextAlignment.CENTER);
            viewMasterListButton.setTextFill(Color.WHITE);
            viewMasterListButton.setPrefSize(cbWidth2, cbHeight2);
            viewMasterListButton.setBackground(Background.EMPTY);
            viewMasterListButton.setOnAction(new GamesMenu.ButtonHandler());
            viewMasterListButton.setOnMouseEntered(new GamesMenu.HoverOn());
            viewMasterListButton.setOnMouseExited(new GamesMenu.HoverOff());
             
        viewMasterList2 = new GridPane();
            viewMasterList2.setVisible(false);
            viewMasterList2.add(new ImagePane(imagesPath + "upperLeft.png", cornerSize2, cornerSize2), 0, 0);
            viewMasterList2.add(new ImagePane(imagesPath + "upperRight.png", cornerSize2, cornerSize2), 1, 0);
            viewMasterList2.add(new ImagePane(imagesPath + "lowerLeft.png", cornerSize2, cornerSize2), 0, 1);
            viewMasterList2.add(new ImagePane(imagesPath + "lowerRight.png", cornerSize2, cornerSize2), 1, 1);
            viewMasterList2.setAlignment(Pos.CENTER);
            viewMasterList2.setPadding(new Insets(25,0,0,0));
            viewMasterList2.setVgap(20);
            viewMasterList2.setHgap(70);
            
        Button viewCategoriesButton= new Button("View\nCategories");
            viewCategoriesButton.setFont(textFont);
            viewCategoriesButton.setTextAlignment(TextAlignment.CENTER);
            viewCategoriesButton.setTextFill(Color.WHITE);
            viewCategoriesButton.setPrefSize(cbWidth2, cbHeight2);
            viewCategoriesButton.setBackground(Background.EMPTY);
            viewCategoriesButton.setOnAction(new GamesMenu.ButtonHandler());
            viewCategoriesButton.setOnMouseEntered(new GamesMenu.HoverOn());
            viewCategoriesButton.setOnMouseExited(new GamesMenu.HoverOff());
             
        viewCategories2 = new GridPane();
            viewCategories2.setVisible(false);
            viewCategories2.add(new ImagePane(imagesPath + "upperLeft.png", cornerSize2, cornerSize2), 0, 0);
            viewCategories2.add(new ImagePane(imagesPath + "upperRight.png", cornerSize2, cornerSize2), 1, 0);
            viewCategories2.add(new ImagePane(imagesPath + "lowerLeft.png", cornerSize2, cornerSize2), 0, 1);
            viewCategories2.add(new ImagePane(imagesPath + "lowerRight.png", cornerSize2, cornerSize2), 1, 1);
            viewCategories2.setAlignment(Pos.CENTER);
            viewCategories2.setPadding(new Insets(25,0,0,0));
            viewCategories2.setVgap(20);
            viewCategories2.setHgap(70);
            
        Button addGameButton = new Button("Add Game");
            addGameButton.setFont(textFont);
            addGameButton.setTextAlignment(TextAlignment.CENTER);
            addGameButton.setTextFill(Color.WHITE);
            addGameButton.setPrefSize(cbWidth2, cbHeight2);
            addGameButton.setBackground(Background.EMPTY);
            addGameButton.setOnAction(new GamesMenu.ButtonHandler());
            addGameButton.setOnMouseEntered(new GamesMenu.HoverOn());
            addGameButton.setOnMouseExited(new GamesMenu.HoverOff());
             
        addGame2 = new GridPane();
            addGame2.setVisible(false);
            addGame2.add(new ImagePane(imagesPath + "upperLeft.png", cornerSize2, cornerSize2), 0, 0);
            addGame2.add(new ImagePane(imagesPath + "upperRight.png", cornerSize2, cornerSize2), 1, 0);
            addGame2.add(new ImagePane(imagesPath + "lowerLeft.png", cornerSize2, cornerSize2), 0, 1);
            addGame2.add(new ImagePane(imagesPath + "lowerRight.png", cornerSize2, cornerSize2), 1, 1);
            addGame2.setAlignment(Pos.CENTER);
            addGame2.setPadding(new Insets(25,0,0,0));
            addGame2.setVgap(20);
            addGame2.setHgap(70);
            
        Button backButton = new Button("Back");
            backButton.setFont(textFont);
            backButton.setTextAlignment(TextAlignment.CENTER);
            backButton.setTextFill(Color.WHITE);
            backButton.setPrefSize(cbWidth2, cbHeight2);
            backButton.setBackground(Background.EMPTY);
            backButton.setOnAction(new GamesMenu.ButtonHandler());
            backButton.setOnMouseEntered(new GamesMenu.HoverOn());
            backButton.setOnMouseExited(new GamesMenu.HoverOff());
             
        back2 = new GridPane();
            back2.setVisible(false);
            back2.add(new ImagePane(imagesPath + "upperLeft.png", cornerSize2, cornerSize2), 0, 0);
            back2.add(new ImagePane(imagesPath + "upperRight.png", cornerSize2, cornerSize2), 1, 0);
            back2.add(new ImagePane(imagesPath + "lowerLeft.png", cornerSize2, cornerSize2), 0, 1);
            back2.add(new ImagePane(imagesPath + "lowerRight.png", cornerSize2, cornerSize2), 1, 1);
            back2.setAlignment(Pos.CENTER);
            back2.setPadding(new Insets(25,0,0,0));
            back2.setVgap(20);
            back2.setHgap(70);

        viewMasterList = new ImagePane(imagesPath + "clapBoard.png", cbWidth2, cbHeight2);
        viewCategories = new ImagePane(imagesPath + "clapBoard.png", cbWidth2, cbHeight2);
        addGame = new ImagePane(imagesPath + "clapBoard.png", cbWidth2, cbHeight2);
        back = new ImagePane(imagesPath + "clapBoard.png", cbWidth2, cbHeight2);
        
        viewMasterListStackPane = new StackPane();
            viewMasterListStackPane.getChildren().addAll(viewMasterList, viewMasterList2, viewMasterListButton);
            viewMasterListStackPane.setAlignment(Pos.CENTER);
            
        viewCategoriesStackPane = new StackPane();
            viewCategoriesStackPane.getChildren().addAll(viewCategories, viewCategories2, viewCategoriesButton);
            viewCategoriesStackPane.setAlignment(Pos.CENTER);
            
        addGameStackPane = new StackPane();
            addGameStackPane.getChildren().addAll(addGame, addGame2, addGameButton);
            addGameStackPane.setAlignment(Pos.CENTER);
            
        backStackPane = new StackPane();
            backStackPane.getChildren().addAll(back, back2, backButton);
            backStackPane.setAlignment(Pos.CENTER);
     
        optionsHBox = new HBox();
            optionsHBox.setAlignment(Pos.CENTER);
            optionsHBox.setSpacing(10);
            optionsHBox.setMaxHeight(125);
            optionsHBox.setMinHeight(125);
            optionsHBox.setPadding(new Insets(0,0,0, 250));
            optionsHBox.getChildren().addAll(viewMasterListStackPane, viewCategoriesStackPane,
                    addGameStackPane, backStackPane);
            
        moving();
    }
    
    private void moving() {
        Timer randomTimer = new Timer();
        randomTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                  javafx.application.Platform.runLater(() -> {
                      
                    if (position < 0) direction = false;
                    else if (position > 25) direction = true;
                    
                    if (direction) position--;
                    else position++;
                    
                    viewMasterListStackPane.setPadding(new Insets(0,0,position,0));
                    viewCategoriesStackPane.setPadding(new Insets(position,0,0,0));
                    
                    addGameStackPane.setPadding(new Insets(0,0,position,0));
//                    deleteGameStackPane.setPadding(new Insets(position,0,0,0));
//                    
//                    editGameStackPane.setPadding(new Insets(0,0,position,0));
                    backStackPane.setPadding(new Insets(position,0,0,0));

                    
                 });
            }
       }, 0, 25);
    }
    
    private void search() {

        scrollPaneContent.getChildren().clear();

        if (!searchField.getText().trim().isEmpty()) {
            
            ArrayList<Game> searchResults = searchGames(games, searchField.getText().toLowerCase().replaceAll("\\s+",""));

            if (searchResults.size() > 0) createScrollPaneContent(searchResults);
            else {
                
                Font textFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 18).getFont();
                Text noResults = new Text("No results found.");
                    noResults.setFont(textFont);
                    noResults.setFill(Color.WHITE);
                    noResults.setTextAlignment(TextAlignment.CENTER);
                    
                StackPane empty = new StackPane();
                    empty.setAlignment(Pos.CENTER);
                    empty.setPadding(new Insets(100,0,0,250));
                    empty.getChildren().add(noResults);
                    
                scrollPaneContent.getChildren().add(empty);    
            }
        }
        
        else {
            games.sortGames();
            createScrollPaneContent(games.getGames());
        }
    }
    
    private void createDownloadPopup() {
        
        dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);
                    
        Font font = new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont();
        
        Text text = new Text("Master Games List\nhas been successfully\ndownloaded to\nyour desktop.");
           text.setFont(font);
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);                
                            
        Button dialogButton = new Button("dialogButton");
            dialogButton.setPrefSize(300,300);
            dialogButton.setStyle("-fx-background-color: transparent;");
            dialogButton.setTextFill(Color.TRANSPARENT);
            dialogButton.setOnMouseExited(new GamesMenu.HoverOff());
            dialogButton.setOnAction(new GamesMenu.ButtonHandler());                            
        
        StackPane sp = new StackPane(text, dialogButton);
            BackgroundSize backgroundSize = new BackgroundSize(300, 300, false, false, true, true);
            Image image = new Image (new File(imagesPath + "popup.gif").toURI().toString());
            sp.setAlignment(Pos.CENTER);
            sp.setBackground(new Background(new BackgroundImage(image, REPEAT, NO_REPEAT, CENTER, backgroundSize)));
            sp.setMinSize(185, 310);                     

                   
        dialogScene = new Scene(sp);
            dialogStage.setScene(dialogScene);
            dialogStage.show();
                    
                    
            try {
                Robot robot = new Robot();
                Bounds bounds = sp.localToScreen(sp.getBoundsInLocal());
                    int boundsX = ((int) bounds.getMaxX()) - (int) bounds.getWidth() / 2;
                    int boundsY = ((int) bounds.getMaxY()) - (int) bounds.getHeight() / 2;
                    robot.mouseMove(boundsX, boundsY);
            }
            
            catch(AWTException ex) {
                System.exit(-1);
            }
    }
    
    private void createGameInfoPopup() {
        
        Font largeFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 30).getFont();
        
        dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);
                   
        deleteButton = new Button("Delete Game");
            deleteButton.setFont(largeFont);
            deleteButton.setTextFill(Color.WHITE);
            deleteButton.setStyle("-fx-background-color: transparent;");
            deleteButton.setOnAction(new GamesMenu.ButtonHandler());
            deleteButton.setOnMouseEntered(new GamesMenu.HoverOn());
            deleteButton.setOnMouseExited(new GamesMenu.HoverOff());
            
        editButton = new Button("Edit Game");
            editButton.setFont(largeFont);
            editButton.setTextFill(Color.WHITE);
            editButton.setStyle("-fx-background-color: transparent;");
            editButton.setOnAction(new GamesMenu.ButtonHandler());
            editButton.setOnMouseEntered(new GamesMenu.HoverOn());
            editButton.setOnMouseExited(new GamesMenu.HoverOff());
            
        closeButton = new Button("Close");
            closeButton.setFont(largeFont);
            closeButton.setTextFill(Color.WHITE);
            closeButton.setStyle("-fx-background-color: transparent;");
            closeButton.setOnAction(new GamesMenu.ButtonHandler());
            closeButton.setOnMouseEntered(new GamesMenu.HoverOn());
            closeButton.setOnMouseExited(new GamesMenu.HoverOff());

        gameInfoPane = new GameInfoPane(games.getSelectedGame(), 30, 20);
        
        HBox hbox = new HBox(editButton, deleteButton, closeButton);
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);
            
        createHiddenOptionsHBox(0);
        optionsStackPane.setVisible(false);
        
        gameInfoStackPane = new StackPane(gameInfoPane);
        
        VBox vbox = new VBox(gameInfoStackPane, hbox, optionsStackPane);
            vbox.setAlignment(Pos.CENTER);
            vbox.setMaxSize(990,440);
            vbox.setMinSize(990,440);
            
        
            
        StackPane sp = new StackPane(vbox);
            BackgroundSize backgroundSize = new BackgroundSize(800, 400, false, false, true, true);
            Image image = new Image (new File(basePath + "images/gamesmenu/popup.gif").toURI().toString());
            sp.setAlignment(Pos.CENTER);
            sp.setBackground(new Background(new BackgroundImage(image, REPEAT, NO_REPEAT, CENTER, backgroundSize)));
                   
        dialogScene = new Scene(sp);
            dialogScene.getStylesheets().add(this.getClass().getResource("/css/GamesMenu.css").toExternalForm());
            dialogStage.setScene(dialogScene);
            dialogStage.show();
        
    }
    
    private void setSelectedGame(String gameTitle) {
        
        for (int i = 0; i < games.getNumGames(); i++) {
            if (games.getGame(i).getTitle().equals(gameTitle)) {
                games.setSelectedGame(games.getGame(i));
                selectedGameIndex = i; 
                break;
            }
        }
    }
    
    private void deleteGamePlatformsUpdate(int id) {
        Platform platform = new Platform();
        
        for (int i = 0; i < platforms.getNumPlatforms(); i++) {
            platform = platforms.getPlatform(i);
            if (games.getSelectedGame().getPlatform().equals(platform.getName())) break;      
        }
        
        for (int i = 0; i < platform.getNumUsedIDs();i++) {
            if (platform.getUsedID(i) == id) {
                platform.addUnusedID(platform.deleteUsedID(i));
                break;
            }
        }
        
        
        writePlatforms(platforms);
        
    }
    
    private void createHiddenOptionsHBox(int option) {

        if (option == 0) {
            
            gameInfoText = new Text();
            
            gameInfoText.setFont(new FontGenerator(fontsPath + "superMarioMaker.ttf", 16).getFont());
                gameInfoText.setFill(Color.WHITE);
                gameInfoText.setTextAlignment(TextAlignment.CENTER);
                
            Font textFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 16).getFont();

            optionsStackPane = new StackPane();
                optionsStackPane.setMaxHeight(50);

            Button yes = new Button("Yes");
                yes.setFont(textFont);
                yes.setTextFill(Color.WHITE);
                yes.setStyle("-fx-background-color: transparent;");
                yes.setOnAction(new GamesMenu.ButtonHandler());
                yes.setOnMouseEntered(new GamesMenu.HoverOn());
                yes.setOnMouseExited(new GamesMenu.HoverOff());

            Button no = new Button("No");
                no.setFont(textFont);
                no.setTextFill(Color.WHITE);
                no.setStyle("-fx-background-color: transparent;");
                no.setOnAction(new GamesMenu.ButtonHandler());
                no.setOnMouseEntered(new GamesMenu.HoverOn());
                no.setOnMouseExited(new GamesMenu.HoverOff());
                
            VBox vbox = new VBox();
            HBox hbox = new HBox();

            hbox.getChildren().addAll(yes, no);
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(3);
                hbox.setStyle("-fx-background-color: transparent");

                StackPane textStackPane = new StackPane(gameInfoText);
                    textStackPane.setAlignment(Pos.CENTER);
                    textStackPane.setStyle("-fx-background-color: transparent;");
                vbox.getChildren().clear();
                vbox.getChildren().addAll(textStackPane, hbox);
                    vbox.setSpacing(1);
                    vbox.setStyle("-fx-background-color: transparent");

                optionsStackPane.getChildren().clear();
                optionsStackPane.getChildren().add(vbox);
                   optionsStackPane.setAlignment(Pos.CENTER);
                gameInfoText.setText("BLANK");
        }
            
        optionsStackPane.setVisible(true);
        
        if (option == 1) {
            gameInfoText.setText("Delete?");
            optionsStackPane.setPadding(new Insets(0,0,0,45));
            gameInfoBoolean = false;
        }
        else if (option == 2) {
            gameInfoText.setText("Save edits?");
            optionsStackPane.setPadding(new Insets(0,350,0,0));
            gameInfoBoolean = true;
        } 
       
    }
    
    private void editGame() {
        
        Game updatedGame = editGamePane.addCorrections();
        games.getSelectedGame().setTitle(updatedGame.getTitle());
        games.getSelectedGame().setPublisher(updatedGame.getPublisher());
        games.getSelectedGame().setDeveloper(updatedGame.getDeveloper());
        games.getSelectedGame().setGenre(updatedGame.getGenre());
        games.getSelectedGame().setStatus(updatedGame.getStatus());
        games.getSelectedGame().setID(updatedGame.getID());
        games.getSelectedGame().setPlatform(updatedGame.getPlatform());
        
        try {
            games.getSelectedGame().setRelease(updatedGame.dateFormat.parse(updatedGame.getRelease()));
        }
        
        catch (ParseException ex) {
            System.err.print("Could not parse date correctly.");
            System.exit(-1);
        }

        games = editGamePane.returnGames();
        platforms = editGamePane.returnPlatforms();
        writeGames(games);
        writePlatforms(platforms);
        
        closeDialogs();
        createGameInfoPopup();
        
    }
    
    private void deleteGame() {
        
        games.deleteGame(selectedGameIndex);
        writeGames(games);
        deleteGamePlatformsUpdate(games.getSelectedGame().getID());
        new File(basePath + "images/games/" + games.getSelectedGame().getID()+ ".png").delete();
        closeDialogs();
        
        dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);
                    
        Font font = new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont();
        
        Text text = new Text(games.getSelectedGame().getTitle() + " has been \n successfully deleted!");
           text.setFont(font);
           text.setFill(Color.WHITE);
           text.setTextAlignment(TextAlignment.CENTER);   
           text.setWrappingWidth(275);
                            
        Button dialogButton = new Button("dialogButton");
            dialogButton.setPrefSize(300,300);
            dialogButton.setStyle("-fx-background-color: transparent;");
            dialogButton.setTextFill(Color.TRANSPARENT);
            dialogButton.setOnMouseExited(new GamesMenu.HoverOff());
            
        StackPane sp = new StackPane(text, dialogButton);
            BackgroundSize backgroundSize = new BackgroundSize(300, 300, false, false, true, true);
            Image image = new Image (new File(basePath + "images/gamesmenu/popup.gif").toURI().toString());
            sp.setAlignment(Pos.CENTER);
            sp.setBackground(new Background(new BackgroundImage(image, REPEAT, NO_REPEAT, CENTER, backgroundSize)));
            sp.setMinSize(185, 310);                     

                   
        dialogScene = new Scene(sp);
            dialogStage.setScene(dialogScene);
            dialogStage.show();
                    
                    
            try {
                Robot robot = new Robot();
                Bounds bounds = sp.localToScreen(sp.getBoundsInLocal());
                    int boundsX = ((int) bounds.getMaxX()) - (int) bounds.getWidth() / 2;
                    int boundsY = ((int) bounds.getMaxY()) - (int) bounds.getHeight() / 2;
                    robot.mouseMove(boundsX, boundsY);
            }
            
            catch(AWTException ex) {
                System.exit(-1);
            }
    }
    
    private class TitleComboBoxHandler implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {

            String selection = (String) comboBox.getValue();
            createScrollPaneContent(titleSelection(games, selection));

        }
    
    }
    
    private class PlatformComboBoxHandler implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {

            String selection = (String) comboBox.getValue();
            createScrollPaneContent(platformSelection(games, selection));

        }
    
    }
    
    private class GenresComboBoxHandler implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {

            String selection = (String) comboBox.getValue();
            createScrollPaneContent(genreSelection(games, selection));

        }
    
    }
    
    private class PublishersComboBoxHandler implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {

            String selection = (String) comboBox.getValue();
            createScrollPaneContent(publisherSelection(games, selection));

        }
    
    }
    
    private class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
                      
            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
                        
            if (sourceButton.equals("View\nMaster List")) createContentPane(2);
            else if (sourceButton.equals("Back")) main();
            else if (sourceButton.equals("View\nCategories")) createContentPane(3);
            else if (sourceButton.equals("Add Game")) createContentPane(4);
            else if (sourceButton.equals("Search")) search();
            else if (sourceButton.equals("Title")) updateCategoriesVBox(1);
            else if (sourceButton.equals("Platform")) updateCategoriesVBox(2);
            else if (sourceButton.equals("Genre")) updateCategoriesVBox(3);
            else if (sourceButton.equals("Publisher")) updateCategoriesVBox(4);
            else if (sourceButton.equals("Unplayed")) updateCategoriesVBox(5);
            else if (sourceButton.equals("Completed")) updateCategoriesVBox(6);
            else if (sourceButton.equals("Incomplete")) updateCategoriesVBox(7);
            else if (sourceButton.equals("Close")) closeDialogs();
            else if (sourceButton.equals("Delete Game")) {
                createHiddenOptionsHBox(1);
                gameInfoStackPane.getChildren().clear();
                gameInfoStackPane.getChildren().add(gameInfoPane);
            }
            else if (sourceButton.equals("Edit Game")) {
                createHiddenOptionsHBox(2);
                editGamePane = new EditGamePane(games.getSelectedGame(), 30, 20, games, platforms);
                gameInfoStackPane.getChildren().clear();
                gameInfoStackPane.getChildren().add(editGamePane);
            }
            else if (sourceButton.equals("No")) {
                closeDialogs();
                createGameInfoPopup();
            }
            else if (sourceButton.equals("Yes")) {
                if(gameInfoBoolean) editGame();
                else deleteGame();
                createContentPane(2);
            }
            else if (sourceButton.equals("Download")) {
                downloadGames(games);
                createDownloadPopup();
            }
            else if (sourceButton.equals("Clear")) {
                searchField.clear();
                createScrollPaneContent(games.getGames());
            }
            else {
                setSelectedGame(sourceButton);
                createGameInfoPopup();
            }

        }
    }
    
    private class HoverOn implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
            
            Media sound = new Media(clapBoardSound.toURI().toString());
            clapBoardPlayer = new MediaPlayer(sound);
            clapBoardPlayer.play();
           
            if (sourceButton.equals("View\nMaster List")) viewMasterList2.setVisible(true);
            else if (sourceButton.equals("Back")) back2.setVisible(true);
            else if (sourceButton.equals("View\nCategories")) viewCategories2.setVisible(true);
            else if (sourceButton.equals("Add Game")) addGame2.setVisible(true);
            else button.setTextFill(Color.web("#F1E14B"));
            
            
        }
    }
    
    private class HoverOff implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("View\nMaster List")) viewMasterList2.setVisible(false);
            else if (sourceButton.equals("Back")) back2.setVisible(false);
            else if (sourceButton.equals("View\nCategories")) viewCategories2.setVisible(false);
            else if (sourceButton.equals("Add Game")) addGame2.setVisible(false);
            else if (sourceButton.equals("dialogButton")) closeDialogs();
            else button.setTextFill(Color.WHITE);
            
        }
    }
    
    private void main() {
       
        this.setVisible(false);
        this.getScene().getWindow().hide();
        Stage primaryStage = new Stage(); 
        MainMenu mainMenu = new MainMenu(); 
        Scene newScene = new Scene(mainMenu, width, height);
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();
         
    }
    
}