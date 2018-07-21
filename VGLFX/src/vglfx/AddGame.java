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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
import vgl.Game;
import vgl.Games;
import static vgl.IO.writeGames;
import static vgl.IO.writePlatforms;
import vgl.Platform;
import vgl.Platforms;
import static vglfx.IO.createGameID;
import static vglfx.IO.createNewPlatfomID;
import static vglfx.IO.readGenres;
import static vglfx.IO.readPlatforms;

public class AddGame extends StackPane {
    
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
    
    private VBox addGameVBox; //vbox that will hold everything for the pane
    private HBox contentHBox;
    private HBox backHBox;
    private ImagePane backButton; 
    
    private VBox leftContentVBox;
    private StackPane rightContentStackPane;
    
    private Button enter;
    private Button clear;
    
    private String prompt;
    
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
    
    private Game blankGame;
    private GamePane blankGamePane;
    private int blankGameID;
    private Platform newPlatform;
    private int newPlatformID;
    private boolean updatePlatforms;
    
    private File imageFile;
    private FileChooser imageFileChooser;
    
    public AddGame(Games games, Platforms platforms) {
        this.games = games;
        this.platforms = platforms;
        imagesPath = "resources/images/gamesmenu/";
        fontsPath = "resources/fonts/";
        musicFile = new File("resources/sounds/pacmanarcade.wav");
        backgroundMusic = new Media(musicFile.toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        creditSound = new File("resources/sounds/credit.mp3");
        createAddGame();
    }
    
    private void createAddGame() {
        createBackground();
        createSounds();
        createAddGameVBox();
        this.getChildren().addAll(background, barVBox, addGameVBox);
    }
    
    public void createSounds() {
        backgroundMediaPlayer.play();
        
        Media sound = new Media(creditSound.toURI().toString());
        creditPlayer = new MediaPlayer(sound);
    }
    
    public void createBackground() {
        background = new ImagePane(imagesPath + "background.gif", 1200, 750);
        bar = new ImagePane(imagesPath + "bar.png", 600, 150);
        barVBox = new VBox();
            barVBox.setAlignment(Pos.TOP_CENTER);
            barVBox.setPadding(new Insets(20,0,0,0));
            barVBox.getChildren().add(bar);
    }
    
    public void createAddGameVBox() {
        
        addGameVBox = new VBox();
        
        Font headerFont = new CustomFont(fontsPath + "crackman.ttf", 100).getFont();
        
        Text headerText = new Text("Add Game");
            headerText.setFont(headerFont);
            headerText.setFill(Color.YELLOW);
            headerText.setTextAlignment(TextAlignment.CENTER);
            
        contentHBox = new HBox();   
            contentHBox.setAlignment(Pos.CENTER);
            contentHBox.setSpacing(50);
            contentHBox.setStyle("-fx-background-color: #000000;");
            contentHBox.setMaxWidth(1025);
            contentHBox.setMinWidth(1025);
            contentHBox.setPadding(new Insets(10,0,10,0));
            
        createContentHBox();
        
        backHBox = new HBox();
        createBackHBox();
          
        addGameVBox.getChildren().addAll(headerText, contentHBox, backHBox);
        addGameVBox.setAlignment(Pos.TOP_CENTER);
        addGameVBox.setSpacing(40);
        addGameVBox.setPadding(new Insets(35,0,0,0));
    
    }
    
    public void createContentHBox() {
        
        leftContentVBox = new VBox();
            leftContentVBox.setMaxSize(600, 400);
            leftContentVBox.setAlignment(Pos.CENTER);
            leftContentVBox.setSpacing(10);
        createLeftContentVBox();
            
        
        rightContentStackPane = new StackPane();
            rightContentStackPane.setMaxSize(400, 400);
            rightContentStackPane.setAlignment(Pos.CENTER);
        createRightContentStackPane("Default");
        
        contentHBox.getChildren().addAll(leftContentVBox, rightContentStackPane);
    }
    
    public void createLeftContentVBox() {
        
        leftContentVBox.getChildren().clear();
        
        Font headerFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 30).getFont();
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 15).getFont();
        
        Text headerText = new Text("Create a new game");
            headerText.setFont(headerFont);
            headerText.setFill(Color.WHITE);
            headerText.setTextAlignment(TextAlignment.CENTER);
        
        blankGame = new Game();
        blankGamePane = new GamePane(blankGame);
        editGamePane();
        
         enter = new Button();
            enter.setText("Enter");
            enter.setFont(textFont);
            enter.setTextFill(Color.WHITE);
            enter.setStyle("-fx-background-color: transparent;");
            enter.setOnAction(new AddGame.ButtonHandler());
            enter.setOnMouseEntered(new AddGame.HoverOn());
            enter.setOnMouseExited(new AddGame.HoverOff());
            
        clear = new Button();
            clear.setText("Clear");
            clear.setFont(textFont);
            clear.setTextFill(Color.WHITE);
            clear.setStyle("-fx-background-color: transparent;");
            clear.setOnAction(new AddGame.ButtonHandler());
            clear.setOnMouseEntered(new AddGame.HoverOn());
            clear.setOnMouseExited(new AddGame.HoverOff());
            
        HBox optionButtons = new HBox();
            optionButtons.setSpacing(5);
            optionButtons.setAlignment(Pos.CENTER);
            optionButtons.getChildren().addAll(enter, clear);
        
        leftContentVBox.getChildren().addAll(headerText, blankGamePane, optionButtons);
    }
    
    public void editGamePane() {
        
         Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 25).getFont();
         
        imageButton = new Button();
            imageButton.setText("Click\nto\nadd\nimage");
            imageButton.setFont(textFont);
            imageButton.setStyle("-fx-background-color: #FFFFFF;");
            imageButton.setTextAlignment(TextAlignment.CENTER);
            imageButton.setPrefSize(175, 250);
            imageButton.setOnAction(new AddGame.ButtonHandler());
            funColors();
        
        blankGamePane.getImagePane().getChildren().add(imageButton);
        
        for (int i = 0; i < 8; i++) blankGamePane.getGameInfoPane().getChildren().remove(8);
        
        genreOptions = readGenres(games);
        platformOptions = readPlatforms(games);
        statusOptions = FXCollections.observableArrayList("Yes/Yes","Yes/No","No/No");
        
        titleField = new TextField();
        releaseField = new TextField();
        publisherField = new TextField();
        developerField = new TextField();
        genreCB = new ComboBox(genreOptions);
            genreCB.setEditable(true);
        platformCB = new ComboBox(platformOptions);
             platformCB.setEditable(true);
        startedCompletedCB = new ComboBox(statusOptions);
        
        Text blank = new Text();
        
        blankGamePane.getGameInfoPane().add(titleField, 1, 0);
        blankGamePane.getGameInfoPane().add(releaseField, 1, 1);
        blankGamePane.getGameInfoPane().add(publisherField, 1, 2);
        blankGamePane.getGameInfoPane().add(developerField, 1, 3);
        blankGamePane.getGameInfoPane().add(genreCB, 1, 4);
        blankGamePane.getGameInfoPane().add(platformCB, 1, 5);
        blankGamePane.getGameInfoPane().add(blank, 1, 6);
        blankGamePane.getGameInfoPane().add(startedCompletedCB, 1, 7);
        
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
                  javafx.application.Platform.runLater(() -> {
                      imageButton.setTextFill(colors[n]);
                 });
            }
       }, 0, 400);
    }
    
    public void checkGamePaneInput() {
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 15).getFont();
        
        prompt = "Please correct the following:\n";

        boolean t = false;
        boolean t2 = false;
        boolean r = false;
        boolean r2 = false;
        boolean pu = false;
        boolean d = false;
        boolean g = false;
        boolean pl = false;
        boolean s = false;
        boolean i = false;
        
        if (titleField.getText().isEmpty()) prompt+= "\n Title field is empty";
        else {
            t = true;
            if(gameExists()) prompt += "\n Game already exists";
            else t2 = true;
        } 
        
        if (releaseField.getText().isEmpty()) prompt+= "\n Release field is empty"; 
        else {
            r = true;
            if(!canBeParsed(releaseField.getText())) prompt += "\n Release date format is invalid";
            else r2 = true;
        }

        if (publisherField.getText().isEmpty()) prompt+= "\n Publisher field is empty";
        else pu = true;
        
        if (developerField.getText().isEmpty()) prompt+= "\n Developer field is empty";
        else d = true;

        if (genreCB.getValue() == null) prompt+= "\n Genre not selected or entered";
        else {
            String blank = (String) genreCB.getValue();
            if (blank.length() == 0) {
                prompt += "\n Genre not selected or entered";
                g = false;
            }
            else g = true;
        }
        
        if (platformCB.getValue() == null) prompt+= "\n Platform not selected or entered";
        else {
            String blank = (String) platformCB.getValue();
            if (blank.length() == 0) {
                prompt += "\n Platform not selected or entered";
                pl = false;
            }
            else pl = true;
        }
        
        if (startedCompletedCB.getValue() == null) prompt+= "\n Started/Completed status not selected";
        else s = true;
        
        if (imageFile == null) prompt+= "\n Box Art not uploaded or invalid";
        else i = true;
        
        Text select = new Text(prompt);
            select.setFont(textFont);
            select.setFill(Color.WHITE);
            select.setTextAlignment(TextAlignment.CENTER);
            
            
        if (t && t2 && r && r2 && pu && d && g && pl && s && i) createGamePane();
        else createRightContentStackPane("Fields");
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
    
    public void createGamePane() {
        
        for (int i = 0; i < 8; i++) blankGamePane.getGameInfoPane().getChildren().remove(8);
        
        blankGameID = createGameID(games, platforms, (String) platformCB.getValue());
        updatePlatforms = false;
        
        if (blankGameID == -1) {
            String platformName = (String) platformCB.getValue();
            newPlatformID = createNewPlatfomID(platforms);
            
            newPlatform = new Platform();
                newPlatform.setName(platformName);
                newPlatform.setID(newPlatformID);
           blankGameID = newPlatformID * 1000;
           updatePlatforms = true;
        }
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 10).getFont();
        
        Text titleText = new Text();
            titleText.setText(titleField.getText());
            titleText.setFont(textFont);
            titleText.setFill(Color.WHITE);
            
        Text releaseText = new Text();
            releaseText.setText(releaseField.getText());
            releaseText.setFont(textFont);
            releaseText.setFill(Color.WHITE);
            
        Text publisherText = new Text();
            publisherText.setText(publisherField.getText());
            publisherText.setFont(textFont);
            publisherText.setFill(Color.WHITE);
            
        Text developerText = new Text();
            developerText.setText(developerField.getText());
            developerText.setFont(textFont);
            developerText.setFill(Color.WHITE);
            
        Text genreText = new Text();
            genreText.setText((String) genreCB.getValue());
            genreText.setFont(textFont);
            genreText.setFill(Color.WHITE);
            
        Text platformText = new Text();
            platformText.setText((String) platformCB.getValue());
            platformText.setFont(textFont);
            platformText.setFill(Color.WHITE);
            
        Text idText = new Text();
            idText.setText(new String() + blankGameID);
            idText.setFont(textFont);
            idText.setFill(Color.WHITE);
        
        Text statusText = new Text();
            statusText.setText((String) startedCompletedCB.getValue());
            statusText.setFont(textFont);
            statusText.setFill(Color.WHITE);
        
        blankGamePane.getGameInfoPane().add(titleText, 1, 0);
        blankGamePane.getGameInfoPane().add(releaseText, 1, 1);
        blankGamePane.getGameInfoPane().add(publisherText, 1, 2);
        blankGamePane.getGameInfoPane().add(developerText, 1, 3);
        blankGamePane.getGameInfoPane().add(genreText, 1, 4);
        blankGamePane.getGameInfoPane().add(platformText, 1, 5);
        blankGamePane.getGameInfoPane().add(idText, 1, 6);
        blankGamePane.getGameInfoPane().add(statusText, 1, 7);
        
        blankGamePane.getImagePane().getChildren().clear();
        blankGamePane.getImagePane().getChildren().add(new ImagePane(imageFile.getAbsolutePath(), 175, 250));
        
        createRightContentStackPane("Confirm");
        enter.setText("Confirm");
        clear.setText("Cancel");
        
    }
    
    public void createGame() {
        
        try {
            if (imageFile != null) {
                File newImage = new File(blankGame.getPath() + titleField.getText() + ".png"); //any location
                Files.copy(imageFile.toPath(), newImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException ex) {
            System.exit(-1);
        }
        
        blankGame.setTitle(titleField.getText().trim());
        blankGame.setDate(releaseField.getText().trim());
        blankGame.setPublisher(publisherField.getText().trim());
        blankGame.setDeveloper(developerField.getText().trim());
        blankGame.setGenre(((String) genreCB.getValue()).trim());
        blankGame.setPlatform(((String) platformCB.getValue()).trim());
        blankGame.setID(blankGameID);
        blankGame.setStatus(((String) startedCompletedCB.getValue()).trim());
        blankGame.setImage(titleField.getText().trim() + ".png");
        
        
        editGamePane();
        createRightContentStackPane("Finish");
        
        games.addGame(blankGame);
        games.getGameIDs().put(blankGame.getTitle(), blankGameID);
        games.getGameMap().put(blankGame.getTitle(), blankGame);
        writeGames(games);
        
        if (updatePlatforms) {
            platforms.addPlatform(newPlatform);
            platforms.getPlatformIDs().put(newPlatform.getName(), newPlatformID);
            platforms.getPlatformMap().put(newPlatform.getName(), newPlatform);
            writePlatforms(platforms);
        }
        
        blankGame = new Game();
        
    }
    
    public boolean gameExists() {
        
        return games.getGameMap().containsKey(titleField.getText());
    }
    
    public void createRightContentStackPane(String version) {
        
        rightContentStackPane.getChildren().clear();
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 15).getFont();
        
        Text text = new Text();
            text.setFont(textFont);
            text.setFill(Color.WHITE);
            text.setWrappingWidth(400);
            text.setTextAlignment(TextAlignment.CENTER);
        
        if (version.equals("Default")) text.setText("Fill out all fields to create a new game.");
        else if (version.equals("Fields")) text.setText(prompt);
        else if (version.equals("Confirm")) text.setText("Press confirm to save game or cancel to delete.");
        else if (version.equals("Finish")) text.setText(blankGame.getTitle().trim() + " has been added successfully.");

        rightContentStackPane.getChildren().add(text);
   
    }
    
    public void createBackHBox() {
        
        Font textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 25).getFont();
        
        Button back = new Button("Back");
            back.setFont(textFont);
            back.setTextAlignment(TextAlignment.CENTER);
            back.setTextFill(Color.WHITE);
            back.setStyle("-fx-background-color: transparent;");
            back.setOnAction(new AddGame.ButtonHandler());
            back.setOnMouseEntered(new AddGame.HoverOn());
            back.setOnMouseExited(new AddGame.HoverOff());
        
        backButton = new ImagePane(imagesPath + "redArrow.png", 50, 50);
        backButton.setVisible(false);
            
        
        backHBox.getChildren().addAll(backButton, back);
            backHBox.setAlignment(Pos.CENTER);
            backHBox.setStyle("-fx-background-color: #000000;");
            backHBox.setMaxWidth(200);
            backHBox.setMinWidth(200);
       
    }
    
    public void imageSelect() {
        
        Stage primaryStage = new Stage(); 
        imageFileChooser = new FileChooser();
        imageFile = imageFileChooser.showOpenDialog(primaryStage);
        if(imageFile != null) {
            imageButton.setStyle("-fx-background-color: transparent;");
            imageButton.setTextFill(Color.WHITE);
            imageButton.setText("Click\nto\nedit\nimage");
            blankGamePane.getImagePane().getChildren().clear();
            blankGamePane.getImagePane().getChildren().add(new ImagePane(imageFile.getAbsolutePath(), 175, 250));
            blankGamePane.getImagePane().getChildren().add(imageButton);
            
        }
        
    }
    
    public class ButtonHandler implements EventHandler<ActionEvent> { 
          @Override
        public void handle(ActionEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("Back")) game();
            else if (sourceButton.equals("Click\nto\nadd\nimage")) imageSelect();
            else if (sourceButton.equals("Click\nto\nedit\nimage")) imageSelect();
            else if (sourceButton.equals("Enter")) checkGamePaneInput();
            else if (sourceButton.equals("Confirm")) {
                createGame();
                enter.setText("Enter");
                clear.setText("Clear");
                imageButton.setText("Click\nto\nadd\nimage");
            }
            else if (sourceButton.equals("Clear") || sourceButton.equals("Cancel")) {
                editGamePane();
                enter.setText("Enter");
                clear.setText("Clear");
                imageButton.setText("Click\nto\nadd\nimage");
                createRightContentStackPane("Default");
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
            else if (sourceButton.equals("Enter")) enter.setTextFill(Color.RED);
            else if (sourceButton.equals("Confirm")) enter.setTextFill(Color.RED);
            else if (sourceButton.equals("Clear")) clear.setTextFill(Color.RED);
            else if (sourceButton.equals("Cancel")) clear.setTextFill(Color.RED);
           
        }
    }
    
    public class HoverOff implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
            
            creditPlayer.stop();

            if (sourceButton.equals("Back")) backButton.setVisible(false);
            else if (sourceButton.equals("Enter")) enter.setTextFill(Color.WHITE);
            else if (sourceButton.equals("Confirm")) enter.setTextFill(Color.WHITE);
            else if (sourceButton.equals("Clear")) clear.setTextFill(Color.WHITE);
            else if (sourceButton.equals("Cancel")) clear.setTextFill(Color.WHITE);

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
}
