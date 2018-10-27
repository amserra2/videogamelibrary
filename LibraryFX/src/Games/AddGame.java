package Games;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
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
import javafx.scene.control.DatePicker;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.Game;
import library.Games;
import Necessities.FontGenerator;
import Necessities.ImagePane;
import static Necessities.IO.readGenres;
import static Necessities.IO.readPlatforms;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import static library.LibraryIO.writeGames;
import static library.LibraryIO.writePlatforms;
import library.Platform;
import library.Platforms;

public final class AddGame extends HBox {
    private Games games; 
    private Platforms platforms; 
    private Game newGame; 
    private VBox vbox;
    private GridPane gridPane;
    private Button gameImageButton;
    private VBox vbox2;
    private VBox vbox3;
    
    private Button enter;
    private Button clear;
    
    private Button confirm;
    private Button cancel;
    
    private StackPane imageStackPane;
    
    private final Font largeFont; 
    private final Font smallFont;
    private StackPane titleStackPane;
    private TextField title;
    private DatePicker release2;
    private TextField publisher2;
    private TextField developer2;
    private ComboBox platformComboBox;
    private ComboBox genreComboBox;
    private ComboBox statusComboBox;
    
    //private HBox mainHBox;
    
    private Text title3;
    private Text release3;
    private Text publisher3;
    private Text developer3;
    private Text platform3;
    private Text genre3;
    private Text status3;
    private Text id2;
    
    private File imageFile;
    private FileChooser imageFileChooser;
    
    private Stage dialogStage;
    private Scene dialogScene;
    
    public final String fontsPath = "resources/fonts/";
    
    AddGame(Games games, Platforms platforms) {
        this.games = games;
        this.platforms = platforms;
        newGame = new Game();
        largeFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 30).getFont();
        smallFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont();
        createAddGame();
    }
    
    public void createAddGame() {
        if (imageFile != null) imageFile.delete();
        createVBox();
        createGameImageButton();
        createVBox2();

        //mainHBox = new HBox();
        this.getChildren().clear();
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(imageStackPane, vbox, vbox2);
        this.setSpacing(20);
        
        
//        this.getChildren().add(mainHBox);
//        this.setBackground(Background.EMPTY);
    }
    
    public void createVBox() {
        createGridPane();
        
        vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(3);

            
        title = new TextField();
            title.setPromptText("Title");
            title.setAlignment(Pos.CENTER_LEFT);
            title.setFont(largeFont);
            
        titleStackPane = new StackPane();
            titleStackPane.setAlignment(Pos.CENTER_LEFT);
            titleStackPane.getChildren().add(title);
            titleStackPane.setMaxHeight(30);
            
        vbox.getChildren().addAll(titleStackPane, gridPane);
    }
    
    public void createVBox2() {
        
        vbox2 = new VBox();
            vbox2.setAlignment(Pos.CENTER);
            vbox2.setSpacing(3);
            vbox2.setPadding(new Insets(0,0,10,20));
            
        enter = new Button("Enter");
            enter.setFont(largeFont);
            enter.setTextFill(Color.WHITE);
            enter.setStyle("-fx-background-color: transparent;");
            enter.setOnAction(new AddGame.ButtonHandler());
            enter.setOnMouseEntered(new AddGame.HoverOn());
            enter.setOnMouseExited(new AddGame.HoverOff());
            
        clear = new Button("Clear");
            clear.setFont(largeFont);
            clear.setTextFill(Color.WHITE);
            clear.setStyle("-fx-background-color: transparent;");
            clear.setOnAction(new AddGame.ButtonHandler());
            clear.setOnMouseEntered(new AddGame.HoverOn());
            clear.setOnMouseExited(new AddGame.HoverOff());
            
        Text prompt = new Text("Ready to go?");
            prompt.setFont(largeFont);
            prompt.setFill(Color.WHITE);
            
        vbox2.getChildren().addAll(prompt, enter, clear);
    }
    
    public void createVBox3() {
        
        vbox3 = new VBox();
            vbox3.setAlignment(Pos.CENTER);
            vbox3.setSpacing(3);
            vbox3.setPadding(new Insets(0,0,10,0));
            
        confirm = new Button("Confirm");
            confirm.setFont(largeFont);
            confirm.setTextFill(Color.WHITE);
            confirm.setStyle("-fx-background-color: transparent;");
            confirm.setOnAction(new AddGame.ButtonHandler());
            confirm.setOnMouseEntered(new AddGame.HoverOn());
            confirm.setOnMouseExited(new AddGame.HoverOff());
            
        cancel = new Button("Cancel");
            cancel.setFont(largeFont);
            cancel.setTextFill(Color.WHITE);
            cancel.setStyle("-fx-background-color: transparent;");
            cancel.setOnAction(new AddGame.ButtonHandler());
            cancel.setOnMouseEntered(new AddGame.HoverOn());
            cancel.setOnMouseExited(new AddGame.HoverOff());
            
        Text prompt = new Text("Create this game?");
            prompt.setFont(largeFont);
            prompt.setFill(Color.WHITE);
            
        vbox3.getChildren().addAll(prompt, confirm, cancel);
    }
    
    public void createGridPane() {
        
        gridPane = new GridPane();
            gridPane.setPadding(new Insets(0,0,0,25));
        
        Text release = new Text("Released: ");
            release.setFill(Color.web("#F1E14B"));
            release.setTextAlignment(TextAlignment.LEFT);
            release.setFont(smallFont);
            
        Text publisher = new Text("Publisher: ");
            publisher.setFill(Color.web("#F1E14B"));
            publisher.setTextAlignment(TextAlignment.LEFT);
            publisher.setFont(smallFont);
            
        Text developer = new Text("Developer: ");
            developer.setFill(Color.web("#F1E14B"));
            developer.setTextAlignment(TextAlignment.LEFT);
            developer.setFont(smallFont);
        
        Text genre = new Text("Genre: ");
            genre.setFill(Color.web("#F1E14B"));
            genre.setTextAlignment(TextAlignment.LEFT);
            genre.setFont(smallFont);
            
        Text platform = new Text("Platform: ");
            platform.setFill(Color.web("#F1E14B"));
            platform.setTextAlignment(TextAlignment.LEFT);
            platform.setFont(smallFont);
            
        Text id = new Text("ID Number: ");
            id.setFill(Color.web("#F1E14B"));
            id.setTextAlignment(TextAlignment.LEFT);
            id.setFont(smallFont);
            
        Text status = new Text("Status: ");
            status.setFill(Color.web("#F1E14B"));
            status.setTextAlignment(TextAlignment.LEFT);
            status.setFont(smallFont);
         
        gridPane.add(release, 0, 0);
        gridPane.add(publisher, 0, 1);
        gridPane.add(developer, 0, 2);
        gridPane.add(genre, 0, 3);
        gridPane.add(platform, 0, 4);
        gridPane.add(id, 0, 5);
        gridPane.add(status, 0, 6);
        
        GridPane.setHalignment(release, HPos.RIGHT);
        GridPane.setHalignment(publisher, HPos.RIGHT);
        GridPane.setHalignment(developer, HPos.RIGHT);
        GridPane.setHalignment(genre, HPos.RIGHT);
        GridPane.setHalignment(platform, HPos.RIGHT);
        GridPane.setHalignment(id, HPos.RIGHT);
        GridPane.setHalignment(status, HPos.RIGHT);
            
        
        release2 = new DatePicker();
            release2.setPromptText("Release Date");
            release2.setEditable(false);
            
        publisher2 = new TextField();
            publisher2.setPromptText("Publisher");
            publisher2.setFont(smallFont);
            
        developer2 = new TextField();
            developer2.setPromptText("Developer");
            developer2.setFont(smallFont);
        
        ObservableList<String> genreOptions = readGenres(games); 
        genreComboBox = new ComboBox(genreOptions);
            genreComboBox.setPromptText("Genre");
            genreComboBox.setStyle("-fx-background-color: #F1E14B");
            genreComboBox.setEditable(true);
            
        ObservableList<String> platformOptions = readPlatforms(platforms); 
        platformComboBox = new ComboBox(platformOptions);
            platformComboBox.setPromptText("Platform");
            platformComboBox.setStyle("-fx-background-color: #F1E14B");
            platformComboBox.setEditable(true);
            
        id2 = new Text("000000");
            id2.setFill(Color.WHITE);
            id2.setTextAlignment(TextAlignment.LEFT);
            id2.setFont(smallFont);
         
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Yes/Yes","Yes/No","No/No");    
        statusComboBox = new ComboBox(statusOptions);
            statusComboBox.setPromptText("Status");
        
        gridPane.add(release2, 1, 0);
        gridPane.add(publisher2, 1, 1);
        gridPane.add(developer2, 1, 2);
        gridPane.add(genreComboBox, 1, 3);
        gridPane.add(platformComboBox, 1, 4);
        gridPane.add(id2, 1, 5);
        gridPane.add(statusComboBox, 1, 6);  
        
        gridPane.setHgap(3);
        gridPane.setVgap(3);

    }
    
    public void createGameImageButton() {
        gameImageButton = new Button("Add\nGame\nImage");
            gameImageButton.setPrefSize(225, 300);
            gameImageButton.setTextAlignment(TextAlignment.CENTER);
            gameImageButton.setFont(largeFont);
            gameImageButton.setStyle("-fx-background-color: transparent; -fx-border-color: #F1E14B;\n" +
            "-fx-border-width: 5px;");
            gameImageButton.setTextFill(Color.WHITE);
            gameImageButton.setOnAction(new AddGame.ButtonHandler());
            gameImageButton.setOnMouseEntered(new AddGame.HoverOn());
            gameImageButton.setOnMouseExited(new AddGame.HoverOff());
            
        imageStackPane = new StackPane();
            imageStackPane.setAlignment(Pos.CENTER);
            imageStackPane.getChildren().add(gameImageButton);
    }
    
    public boolean validateFields() {

        if (title.getText().isEmpty()) return false;
        else if (release2.getValue() == null) return false;
        else if (publisher2.getText().isEmpty()) return false; 
        else if (developer2.getText().isEmpty()) return false; 
        else if (genreComboBox.getValue() == null) return false; 
        else if (((String) genreComboBox.getValue()).length() == 0) return false;
        else if (platformComboBox.getValue() == null) return false; 
        else if (((String) platformComboBox.getValue()).length() == 0) return false;
        else if (statusComboBox.getValue() == null) return false;
        else if (imageFile == null) return false; 
        else return true;
    }
    
    public void update() {
            
        if (title.getText().length() > 25) title3 = new Text(title.getText().substring(0, 25) + "...");
        else title3 = new Text(title.getText());
        
            title3.setTextAlignment(TextAlignment.LEFT);
            title3.setFont(largeFont);
            title3.setFill(Color.WHITE);
        
        titleStackPane.getChildren().clear();
        titleStackPane.getChildren().add(title3);
        
        release2.setVisible(false);
        release3 = new Text(release2.getValue().format(DateTimeFormatter.ofPattern("MM dd, yyyy")));
            release3.setFont(smallFont);
            release3.setFill(Color.WHITE);
        gridPane.add(release3, 1, 0);
        
        publisher2.setVisible(false);
        publisher3 = new Text(publisher2.getText());
            publisher3.setFont(smallFont);
            publisher3.setFill(Color.WHITE);
        gridPane.add(publisher3, 1, 1);
        
        developer2.setVisible(false);
        developer3 = new Text(developer2.getText());
            developer3.setFont(smallFont);
            developer3.setFill(Color.WHITE);
        gridPane.add(developer3, 1, 2);
        
        genreComboBox.setVisible(false);
        genre3 = new Text(((String) genreComboBox.getValue()).trim());
            genre3.setFont(smallFont);
            genre3.setFill(Color.WHITE);
        gridPane.add(genre3, 1, 3);
        
        platformComboBox.setVisible(false);
        platform3 = new Text(((String) platformComboBox.getValue()).trim());
            platform3.setFont(smallFont);
            platform3.setFill(Color.WHITE);
        gridPane.add(platform3, 1, 4);
        
        id2.setText("000000");
        
        statusComboBox.setVisible(false);
        status3 = new Text((String) statusComboBox.getValue());
            status3.setFont(smallFont);
            status3.setFill(Color.WHITE);
        gridPane.add(status3, 1, 6);
        
        
        gameImageButton.setVisible(false); 
        
        createVBox3();
        this.getChildren().remove(vbox2);
        this.getChildren().add(vbox3);
            
    }
    
    public void createGame() {
        
        newGame.setTitle(title.getText().trim());
        newGame.setRelease(java.sql.Date.valueOf(release2.getValue()));
        newGame.setPublisher(publisher2.getText().trim());
        newGame.setDeveloper(developer2.getText().trim());
        newGame.setGenre(((String) genreComboBox.getValue()).trim());
        newGame.setPlatform(((String) platformComboBox.getValue()).trim());
        newGame.setID(getNewGameID());
        newGame.setStatus((String) statusComboBox.getValue());
        
        
        try {
            File newImage = new File("resources/images/games/" + newGame.getID()+ ".png"); //any location
            Files.copy(imageFile.toPath(), newImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } 
        catch (IOException ex) {
            Logger.getLogger(AddGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        games.addGame(newGame);
        writeGames(games);
        
    }
    
    public int getNewGameID() {
        int newGameID;
        Platform platform = new Platform();
            platform.setName(newGame.getPlatform());
        
        for (int i = 0; i < platforms.getNumPlatforms(); i++) {
            
            if (platforms.getPlatform(i).getName().toLowerCase().equals(platform.getName().toLowerCase())) {
                
                platform = platforms.getPlatform(i);
                
                if (platform.getNumUnusedIDs() != 0) newGameID = platform.getUnusedIDs().remove(0);             
                else newGameID = (platform.getID() * 1000) + platform.getNumUsedIDs();
                
                platform.getUsedIDs().add(newGameID);
                writePlatforms(platforms);
                return newGameID;  
            }
        }
        
        platform.setID((platforms.getNumPlatforms() + 1) * 100);
        newGameID = platform.getID() * 1000;
        platform.getUsedIDs().add(newGameID);
        
        platforms.addPlatform(platform);
        writePlatforms(platforms);
        
        return newGameID;
    }
    
    public void imageSelect() {
        
        Stage primaryStage = new Stage(); 
        imageFileChooser = new FileChooser();
        imageFile = imageFileChooser.showOpenDialog(primaryStage);
        
        if(imageFile != null) {
            gameImageButton.setStyle("-fx-background-color: transparent");
            ImagePane image = new ImagePane(imageFile.getAbsolutePath(), 225, 300);
            imageStackPane.getChildren().clear();
            imageStackPane.getChildren().addAll(image, gameImageButton);
            
        }
        
    }
    
    public void blankFieldsPopup() {
        
        dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);
                    
        Font font = new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont();
        
        Text text = new Text("One or more fields\nhave been left blank.\n Please fill out\nall fields.");
           text.setFont(font);
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);                
                            
        Button dialogButton = new Button("dialogButton");
            dialogButton.setPrefSize(300,300);
            dialogButton.setStyle("-fx-background-color: transparent;");
            dialogButton.setTextFill(Color.TRANSPARENT);
            dialogButton.setOnMouseExited(new AddGame.HoverOff());
            
        StackPane sp = new StackPane(text, dialogButton);
            BackgroundSize backgroundSize = new BackgroundSize(300, 300, false, false, true, true);
            Image image = new Image (new File("resources/images/gamesmenu/popup.gif").toURI().toString());
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

    public void newGameCreatedPopup() {
        
        dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);
                    
        Font font = new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont();
        
        Text text = new Text(newGame.getTitle() + " has been \n successfully added!");
           text.setFont(font);
           text.setFill(Color.WHITE);
           text.setTextAlignment(TextAlignment.CENTER);   
           text.setWrappingWidth(275);
                            
        Button dialogButton = new Button("dialogButton");
            dialogButton.setPrefSize(300,300);
            dialogButton.setStyle("-fx-background-color: transparent;");
            dialogButton.setTextFill(Color.TRANSPARENT);
            dialogButton.setOnMouseExited(new AddGame.HoverOff());
            
        StackPane sp = new StackPane(text, dialogButton);
            BackgroundSize backgroundSize = new BackgroundSize(300, 300, false, false, true, true);
            Image image = new Image (new File("resources/images/gamesmenu/popup.gif").toURI().toString());
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

    public class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
                      
            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
                        
            if (sourceButton.equals("Enter")) {
                if (validateFields()) update();
                else blankFieldsPopup();
            }
            else if (sourceButton.equals("Clear") || sourceButton.equals("Cancel")) createAddGame();
            else if (sourceButton.equals("Add\nGame\nImage")) imageSelect();
            else if (sourceButton.equals("Confirm")) {
                createGame();
                createAddGame();
                newGameCreatedPopup();
            }
            
        }
    }
        
    public class HoverOn implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            
            button.setTextFill(Color.web("#F1E14B"));
             
        }
    }
    
    public class HoverOff implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
            button.setTextFill(Color.WHITE);
            
            if (sourceButton.equals("dialogButton")) {
                dialogScene.getWindow().hide();
                dialogStage.getScene().getWindow().hide();
            }
            
        }
    }
    
}
