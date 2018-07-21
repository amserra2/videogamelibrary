package vglfx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vgl.Game;
import vgl.Games;
import static vgl.IO.writeGames;
import vgl.Platforms;
import static vglfx.IO.readGenres;
import static vglfx.IO.readPlatforms;
import static vglfx.IO.searchGames;
import static vglfx.IO.sortGames;

public class EditGame extends StackPane {
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
    
    private VBox EditGameVBox;
    
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
    private GamePane selectedGamePane;
    
    private Stage hoverStage;
    private Game hoverGame;
    
    private Button enter; 
    private Button cancel;
    
    private TextField titleField;
    private TextField releaseField;
    private TextField publisherField;
    private TextField developerField;
    private ComboBox genreCB;
    private ComboBox platformCB;
    private ComboBox startedCompletedCB;
    private Button imageButton;
    
    ObservableList<String> genreOptions;
    ObservableList<String> platformOptions;
    ObservableList<String> statusOptions;
    
    private File imageFile;
    private FileChooser imageFileChooser;
    
    private boolean changeTitle;
    private boolean changeRelease;
    private boolean changePublisher;
    private boolean changeDeveloper;
    private boolean changeGenre;
    private boolean changePlatform;
    private boolean changeStatus;
    private boolean changeImage;
    
    public EditGame(Games games, Platforms platforms) { 
        this.games = games;
        this.platforms = platforms;
        imagesPath = "resources/images/gamesmenu/";
        fontsPath = "resources/fonts/";
        musicFile = new File("resources/sounds/pacmanarcade.wav");
        backgroundMusic = new Media(musicFile.toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        creditSound = new File("resources/sounds/credit.mp3");
        createEditGame();
    }
    
    private void createEditGame() {
        createBackground();
        createSounds();
        createEditGameVBox();
        this.getChildren().addAll(background, barVBox, EditGameVBox);
    }
    
    public void createSounds() {
        backgroundMediaPlayer.play();
        
        Media sound = new Media(creditSound.toURI().toString());
        creditPlayer = new MediaPlayer(sound);
    }
    
    public void createBackground() {
        background = new ImagePane(imagesPath + "background.gif", 1200, 750);
        bar = new ImagePane(imagesPath + "bar.png", 625, 150);
        barVBox = new VBox();
            barVBox.setAlignment(Pos.TOP_CENTER);
            barVBox.setPadding(new Insets(20,0,0,0));
            barVBox.getChildren().add(bar);
    }
    
    public void createEditGameVBox() {
        
        EditGameVBox = new VBox();
        
        Font headerFont = new CustomFont(fontsPath + "crackman.ttf", 100).getFont();
        
        Text headerText = new Text("Edit Game");
            headerText.setFont(headerFont);
            headerText.setFill(Color.YELLOW);
            headerText.setTextAlignment(TextAlignment.CENTER);
            
        contentHBox = new HBox();   
            contentHBox.setAlignment(Pos.CENTER);
            contentHBox.setSpacing(10);
            contentHBox.setStyle("-fx-background-color: #000000;");
            contentHBox.setMinWidth(1175);
            contentHBox.setMaxWidth(1175);
            contentHBox.setPadding(new Insets(10,0,10,0));
        createContentHBox();
        
        backHBox = new HBox();
        createBackHBox();
          
        EditGameVBox.getChildren().addAll(headerText, contentHBox, backHBox);
        EditGameVBox.setAlignment(Pos.TOP_CENTER);
        EditGameVBox.setSpacing(50);
        EditGameVBox.setPadding(new Insets(35,0,0,0));
    
    }
    
    public void createContentHBox() {
        
        leftContentVBox = new VBox();
            leftContentVBox.setMaxSize(620, 400);
            leftContentVBox.setMinSize(620, 400);
            leftContentVBox.setAlignment(Pos.CENTER);
            leftContentVBox.setSpacing(10);
        createLeftContentVBox();
            
        
        rightContentStackPane = new StackPane();
            rightContentStackPane.setMaxSize(500, 400);
            rightContentStackPane.setMinSize(500, 400);
            rightContentStackPane.setAlignment(Pos.CENTER);
            rightContentStackPane.setStyle("-fx-background-color: #000000;");
            
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
            searchButton.setOnAction(new EditGame.ButtonHandler());
            searchButton.setOnMouseEntered(new EditGame.HoverOn());
            searchButton.setOnMouseExited(new EditGame.HoverOff());
            searchButton.setStyle("-fx-background-color: transparent;");
            searchButton.setTextFill(Color.WHITE);
            searchButton.setAlignment(Pos.CENTER_LEFT);
        
        Button clearButton = new Button("Clear");
            clearButton.setFont(searchFont);
            clearButton.setOnAction(new EditGame.ButtonHandler());
            clearButton.setOnMouseEntered(new EditGame.HoverOn());
            clearButton.setOnMouseExited(new EditGame.HoverOff());
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
                contentScrollPaneVBox.setMaxWidth(620);
                contentScrollPaneVBox.setMinWidth(620);
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
            text.setText("Select a game to edit.");
            rightContentStackPane.getChildren().add(text);
        }
        else if (version.equals("Selected")) {
            selectedGamePane = new GamePane(selectedGame);
                selectedGamePane.setStyle("-fx-background-color: #000000;");
            editGamePane();    
                
            text.setText("Please edit desired fields.");
            
            enter = new Button();
                enter.setText("Enter");
                enter.setFont(textFont);
                enter.setTextFill(Color.WHITE);
                enter.setStyle("-fx-background-color: transparent;");
                enter.setOnAction(new EditGame.ButtonHandler());
                enter.setOnMouseEntered(new EditGame.HoverOn());
                enter.setOnMouseExited(new EditGame.HoverOff());
            
            cancel = new Button();
                cancel.setText("Cancel");
                cancel.setFont(textFont);
                cancel.setTextFill(Color.WHITE);
                cancel.setStyle("-fx-background-color: transparent;");
                cancel.setOnAction(new EditGame.ButtonHandler());
                cancel.setOnMouseEntered(new EditGame.HoverOn());
                cancel.setOnMouseExited(new EditGame.HoverOff());

            HBox optionButtons = new HBox();
                optionButtons.setSpacing(5);
                optionButtons.setAlignment(Pos.CENTER);
                optionButtons.getChildren().addAll(enter, cancel);
            
            VBox confirmVBox = new VBox();
                confirmVBox.setAlignment(Pos.CENTER);
                confirmVBox.setSpacing(10);
                confirmVBox.getChildren().addAll(selectedGamePane, text, optionButtons);
                
            rightContentStackPane.getChildren().add(confirmVBox);
        }
        
        else if (version.equals("Confirm")) {
            
            enter.setText("Confirm");
            
            text.setText("Would you like to save these edits?");

            HBox optionButtons = new HBox();
                optionButtons.setSpacing(5);
                optionButtons.setAlignment(Pos.CENTER);
                optionButtons.getChildren().addAll(enter, cancel);
            
            VBox confirmVBox = new VBox();
                confirmVBox.setAlignment(Pos.CENTER);
                confirmVBox.setSpacing(10);
                confirmVBox.getChildren().addAll(selectedGamePane, text, optionButtons);
                
            rightContentStackPane.getChildren().add(confirmVBox);
        }
        else if (version.equals("Finished")) {
            text.setText(selectedGame.getTitle() + " has been successfully edited.");
            rightContentStackPane.getChildren().add(text);
        }

    }
    
    public void editGamePane() {
       
         Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 25).getFont();
         Font smallTextFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 10).getFont();
        
         
        imageButton = new Button();
            imageButton.setText("Click\nto\nedit\nimage");
            imageButton.setFont(textFont);
            imageButton.setTextAlignment(TextAlignment.CENTER);
            imageButton.setPrefSize(175, 250);
            
            imageButton.setStyle("-fx-background-color: transparent;");
            imageButton.setOnAction(new EditGame.ButtonHandler());
            funColors();
        
        selectedGamePane.getImagePane().getChildren().add(imageButton);
        
        for (int i = 0; i < 8; i++) selectedGamePane.getGameInfoPane().getChildren().remove(8);
        
        genreOptions = readGenres(games);
        platformOptions = readPlatforms(games);
        statusOptions = FXCollections.observableArrayList("Yes/Yes","Yes/No","No/No");
        
        titleField = new TextField();
            titleField.setPromptText(selectedGame.getTitle());
        releaseField = new TextField();
            releaseField.setPromptText(selectedGame.getRelease());
        publisherField = new TextField();
            publisherField.setPromptText(selectedGame.getPublisher());
        developerField = new TextField();
            developerField.setPromptText(selectedGame.getDeveloper());
        genreCB = new ComboBox(genreOptions);
            genreCB.setPromptText(selectedGame.getGenre());
            genreCB.setEditable(true);
        platformCB = new ComboBox(platformOptions);
            platformCB.setPromptText(selectedGame.getPlatform());
            platformCB.setEditable(true);
        startedCompletedCB = new ComboBox(statusOptions);
            startedCompletedCB.setPromptText(selectedGame.getStatus());
        
        Text ID = new Text(new String() + selectedGame.getID());
            ID.setFont(smallTextFont);
            ID.setFill(Color.WHITE);
        
        selectedGamePane.getGameInfoPane().add(titleField, 1, 0);
        selectedGamePane.getGameInfoPane().add(releaseField, 1, 1);
        selectedGamePane.getGameInfoPane().add(publisherField, 1, 2);
        selectedGamePane.getGameInfoPane().add(developerField, 1, 3);
        selectedGamePane.getGameInfoPane().add(genreCB, 1, 4);
        selectedGamePane.getGameInfoPane().add(platformCB, 1, 5);
        selectedGamePane.getGameInfoPane().add(ID, 1, 6);
        selectedGamePane.getGameInfoPane().add(startedCompletedCB, 1, 7);
        
    }
    
    public void updateGame() {
        
        Font smallTextFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 8).getFont();
        
        for (int i = 0; i < 8; i++) selectedGamePane.getGameInfoPane().getChildren().remove(8);
        Text ID = new Text(new String() + selectedGame.getID());
            ID.setFont(smallTextFont);
            ID.setFill(Color.WHITE);
            
        

        changeTitle = false;
        changeRelease = false;
        changePublisher = false;
        changeDeveloper = false;
        changeGenre = false;
        changePlatform = false;
        changeStatus = false;
        changeImage = true;
        
        
        Text title = new Text();
            title.setFont(smallTextFont);
            title.setWrappingWidth(180);
            title.setTextAlignment(TextAlignment.LEFT);
            title.setFill(Color.WHITE);
            
        if (titleField.getText().isEmpty()) title.setText(selectedGame.getTitle());
        else {
            title.setText(titleField.getText().trim());
            changeTitle = true;
        }
            
        Text release = new Text();
            release.setFont(smallTextFont);
            release.setWrappingWidth(180);
            release.setTextAlignment(TextAlignment.LEFT);
            release.setFill(Color.WHITE);
            
        if (releaseField.getText().isEmpty()) release.setText(selectedGame.getRelease());
        else if (canBeParsed(releaseField.getText())) {
            release.setText(releaseField.getText().trim());
            changeRelease = true;
        }
        else release.setText(selectedGame.getRelease());
        

        Text publisher = new Text();
            publisher.setFont(smallTextFont);
            publisher.setWrappingWidth(180);
            publisher.setTextAlignment(TextAlignment.LEFT);
            publisher.setFill(Color.WHITE);
            
        if (publisherField.getText().isEmpty()) publisher.setText(selectedGame.getPublisher());
        else {
            publisher.setText(titleField.getText().trim());
            changePublisher = true;
        }
        
        Text developer = new Text();
            developer.setFont(smallTextFont);
            developer.setWrappingWidth(180);
            developer.setTextAlignment(TextAlignment.LEFT);
            developer.setFill(Color.WHITE);
            
        if (developerField.getText().isEmpty()) developer.setText(selectedGame.getPublisher());
        else {
            developer.setText(developerField.getText().trim());
            changeDeveloper = true;
        }
        
        Text genre = new Text();
            genre.setFont(smallTextFont);
            genre.setWrappingWidth(180);
            genre.setTextAlignment(TextAlignment.LEFT);
            genre.setFill(Color.WHITE);

        if (genreCB.getValue() == null) genre.setText(selectedGame.getGenre());
        else {
            String blank = (String) genreCB.getValue();
            if (blank.length() == 0) genre.setText(selectedGame.getGenre());
            else {
                genre.setText(blank.trim());
                changeGenre = true;
            }
        }
        
        Text platform = new Text();
            platform.setFont(smallTextFont);
            platform.setWrappingWidth(180);
            platform.setTextAlignment(TextAlignment.LEFT);
            platform.setFill(Color.WHITE);

        if (platformCB.getValue() == null) platform.setText(selectedGame.getPlatform());
        else {
            String blank = (String) platformCB.getValue();
            if (blank.length() == 0) platform.setText(selectedGame.getPlatform());
            else {
                platform.setText(blank.trim());
                changePlatform = true;
            }
        }
        
        Text status = new Text();
            status.setFont(smallTextFont);
            status.setTextAlignment(TextAlignment.LEFT);
            status.setFill(Color.WHITE);

        if (startedCompletedCB.getValue() == null) status.setText(selectedGame.getStatus());
        else {
            status.setText((String)startedCompletedCB.getValue());
            changeStatus = true;
        }
        
        
        if (imageFile == null) changeImage = false;
        
        selectedGamePane.getGameInfoPane().add(title, 1, 0);
        selectedGamePane.getGameInfoPane().add(release, 1, 1);
        selectedGamePane.getGameInfoPane().add(publisher, 1, 2);
        selectedGamePane.getGameInfoPane().add(developer, 1, 3);
        selectedGamePane.getGameInfoPane().add(genre, 1, 4);
        selectedGamePane.getGameInfoPane().add(platform, 1, 5);
        selectedGamePane.getGameInfoPane().add(ID, 1, 6);
        selectedGamePane.getGameInfoPane().add(status, 1, 7);
        
        createRightContentStackPane("Confirm");
    }
    
    public boolean canBeParsed(String string) {
        DateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        
        try {
            Date d = fmt.parse(string);
            return  true;
        } 
        catch (ParseException ex) {
            return false;
        }
    }
    
    public void funColors() {
                Timer randomTimer = new Timer();
        randomTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                  // do your stuff including the next
                  Color[] colors = {Color.RED, Color.GREEN, Color.WHITE, Color.BLUE, Color.BLACK, Color.AQUA, Color.BLUEVIOLET,
                  Color.CHARTREUSE, Color.DARKORANGE, Color.FUCHSIA, Color.YELLOW};
                  Random rand = new Random();
                  int n = rand.nextInt(colors.length);
                  Platform.runLater(() -> {
                      imageButton.setTextFill(colors[n]);
                 });
            }
       }, 0, 400);
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
                    gameButton.setOnAction(new EditGame.ButtonHandler());
                    gameButton.setOnMouseEntered(new EditGame.HoverOn());
                    gameButton.setOnMouseExited(new EditGame.HoverOff());

                contentScrollPaneVBox.getChildren().add(gameButton);
            }

                if (n > 10) wrappingWidth = 620;
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
    
    public void createBackHBox() {
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 25).getFont();
        
        Button back = new Button("Back");
            back.setFont(textFont);
            back.setTextAlignment(TextAlignment.CENTER);
            back.setTextFill(Color.WHITE);
            back.setStyle("-fx-background-color: transparent;");
            back.setOnAction(new EditGame.ButtonHandler());
            back.setOnMouseEntered(new EditGame.HoverOn());
            back.setOnMouseExited(new EditGame.HoverOff());
        
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
                            gameButton.setOnAction(new EditGame.ButtonHandler());
                            gameButton.setOnMouseEntered(new EditGame.HoverOn());
                            gameButton.setOnMouseExited(new EditGame.HoverOff());

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

                if (n > 10) wrappingWidth = 620;
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
        
        else createContentScrollPane();
    }
    
    
    public void imageSelect() {
        
        Stage primaryStage = new Stage(); 
        imageFileChooser = new FileChooser();
        imageFile = imageFileChooser.showOpenDialog(primaryStage);
        if(imageFile != null) {
            imageButton.setStyle("-fx-background-color: transparent;");
            imageButton.setTextFill(Color.WHITE);
            selectedGamePane.getImagePane().getChildren().clear();
            selectedGamePane.getImagePane().getChildren().add(new ImagePane(imageFile.getAbsolutePath(), 175, 250));
            selectedGamePane.getImagePane().getChildren().add(imageButton);
            
        }
        
    }
    
    public void saveGame() {
        
        if(changeTitle) selectedGame.setTitle(titleField.getText().trim());
        if(changeRelease) selectedGame.setRelease(releaseField.getText().trim());
        if(changePublisher) selectedGame.setPublisher(publisherField.getText().trim());
        if(changeDeveloper) selectedGame.setDeveloper(developerField.getText().trim());
        if(changeGenre) selectedGame.setGenre( ((String) genreCB.getValue()).trim());
        if(changePlatform) selectedGame.setPlatform( ((String) platformCB.getValue()).trim());
        if(changeStatus) selectedGame.setStatus( ((String) startedCompletedCB.getValue()));
        if (changeImage) {
            saveImage();
            selectedGame.setImage(selectedGame.getTitle().trim() + ".png");
        }
        
        
        
        writeGames(games);
        createRightContentStackPane("Finished");
        searchField.clear();
        createContentScrollPane();
        
    }
    
    public void saveImage() {
        
        try {
            
            if (!selectedGame.getImage().equals("notavailable.png") && changeTitle) {
                 File deleteFile = new File(selectedGame.getPicture()); //creates link to pictue of game
                 deleteFile.delete(); //deletes picture for space purposes
            }
            
            File newImage = new File(selectedGame.getPath() + selectedGame.getTitle() + ".png"); //any location
            Files.copy(imageFile.toPath(), newImage.toPath(), StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException ex) {
            System.exit(-1);
        }
    }
    
    public class ButtonHandler implements EventHandler<ActionEvent> { 
          @Override
        public void handle(ActionEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("Back")) game();
            else if (sourceButton.equals("Search")) search();
            else if (sourceButton.equals("Enter")) updateGame();
            else if (sourceButton.equals("Click\nto\nedit\nimage")) imageSelect();
            else if (sourceButton.equals("Confirm")) saveGame();
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
                selectedGamePane = new GamePane(selectedGame);
                    selectedGamePane.setStyle("-fx-background-color: #000000;");
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
            else if (sourceButton.equals("Enter")) button.setTextFill(Color.RED);
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
            else if (sourceButton.equals("Enter")) button.setTextFill(Color.WHITE);
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
        Stage primaryStage = new Stage(); 
        GamesMenu gamesMenu = new GamesMenu(games, platforms); 
        Scene newScene = new Scene(gamesMenu);
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();
         
    }
    
    public void selectedGame() {

        GamePane hoverGamePane = new GamePane(hoverGame);
            hoverGamePane.setStyle("-fx-background-color: #F18331;");
            
        Scene newScene = new Scene(hoverGamePane);

        hoverStage.setResizable(false);
        hoverStage.setTitle(hoverGame.getTitle());
        hoverStage.setScene(newScene);
        hoverStage.initStyle(StageStyle.UNDECORATED);
        hoverStage.show();

    }
}