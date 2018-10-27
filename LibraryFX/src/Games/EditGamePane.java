package Games;

import Necessities.FontGenerator;
import static Necessities.IO.readGenres;
import static Necessities.IO.readPlatforms;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import library.Game;
import Necessities.ImagePane;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import library.Games;
import static library.LibraryIO.writePlatforms;
import library.Platform;
import library.Platforms;

public final class EditGamePane extends HBox {
    private final Game game; 
    private VBox vbox;
    private GridPane gridPane;
    private ImagePane gameImage;
    
    private TextField titleField;
    private DatePicker releaseField;
    private TextField publisherField;
    private TextField developerField;
    private ComboBox genreComboBox;
    private ComboBox platformComboBox;
    private Text id2; 
    private ComboBox statusComboBox;
    private Button imageButton;
    private Games games;
    private Platforms platforms;
    private StackPane imageStackPane;
    private Button gameImageButton;
    
    public final String fontsPath = "resources/fonts/";
    private final Font largeFont; 
    private final Font smallFont;
    
    private File imageFile;
    private FileChooser imageFileChooser;
    
    EditGamePane(Game g, int x, int y, Games games, Platforms platforms) {
        game = g;
        this.games = games;
        this.platforms = platforms;
        largeFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", x).getFont();
        smallFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", y).getFont();
        createGameInfoPane();
    }
    
    public void createGameInfoPane() {
        createVBox();
        createGameImageButton();
        
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(imageStackPane, vbox);
        this.setSpacing(20);
    }
    
    public void createVBox() {
        createGridPane();
        
        vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);            
        titleField = new TextField();
            titleField.setPromptText(game.getTitle());
            titleField.setFont(largeFont);
            
        StackPane titleStackPane = new StackPane();
            titleStackPane.setAlignment(Pos.CENTER_LEFT);
            titleStackPane.getChildren().add(titleField);
            
        vbox.getChildren().addAll(titleStackPane, gridPane);
        vbox.setSpacing(1);
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
        
        releaseField = new DatePicker();
            releaseField.setPromptText(game.getRelease());
            
        publisherField = new TextField();
            publisherField.setPromptText(game.getPublisher());
            publisherField.setFont(smallFont);
            
        developerField = new TextField();
            developerField.setPromptText(game.getDeveloper());
            developerField.setFont(smallFont);
        
        ObservableList<String> genreOptions = readGenres(games); 
        genreComboBox = new ComboBox(genreOptions);
            genreComboBox.setPromptText(game.getGenre());
            genreComboBox.setStyle("-fx-background-color: #F1E14B");
            genreComboBox.setEditable(true);
            
        ObservableList<String> platformOptions = readPlatforms(platforms); 
        platformComboBox = new ComboBox(platformOptions);
            platformComboBox.setPromptText(game.getPlatform());
            platformComboBox.setStyle("-fx-background-color: #F1E14B");
            platformComboBox.setEditable(true);
            
        id2 = new Text(new String() + game.getID());
            id2.setFill(Color.WHITE);
            id2.setTextAlignment(TextAlignment.LEFT);
            id2.setFont(smallFont);
         
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Yes/Yes","Yes/No","No/No");    
        statusComboBox = new ComboBox(statusOptions);
            statusComboBox.setPromptText(game.getStatus());
         
        gridPane.add(releaseField, 1, 0);
        gridPane.add(publisherField, 1, 1);
        gridPane.add(developerField, 1, 2);
        gridPane.add(genreComboBox, 1, 3);
        gridPane.add(platformComboBox, 1, 4);
        gridPane.add(id2, 1, 5);
        gridPane.add(statusComboBox, 1, 6);  
        
        gridPane.setHgap(3);
        gridPane.setVgap(3);  
        
    }
    
    public void createGameImageButton() {
        gameImage = new ImagePane(game.getPath(), 225, 300);
        gameImageButton = new Button("Add\nGame\nImage");
            gameImageButton.setPrefSize(225, 300);
            gameImageButton.setTextAlignment(TextAlignment.CENTER);
            gameImageButton.setFont(largeFont);
            gameImageButton.setStyle("-fx-background-color: transparent; -fx-border-color: #F1E14B;\n" +
            "-fx-border-width: 5px;");
            gameImageButton.setTextFill(Color.WHITE);
            gameImageButton.setOnAction(new EditGamePane.ButtonHandler());
            gameImageButton.setOnMouseEntered(new EditGamePane.HoverOn());
            gameImageButton.setOnMouseExited(new EditGamePane.HoverOff());
            
        imageStackPane = new StackPane();
            imageStackPane.setAlignment(Pos.CENTER);
            imageStackPane.getChildren().addAll(gameImage, gameImageButton);
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
    
    public class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
                      
            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
                       
            
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
            
        }
    }
    
    public Game addCorrections() {
        
        if (!titleField.getText().trim().isEmpty()) game.setTitle(titleField.getText().trim());
        if (releaseField.getValue() != null) game.setRelease(java.sql.Date.valueOf(releaseField.getValue()));
        if (!publisherField.getText().trim().isEmpty()) game.setPublisher(publisherField.getText().trim()); 
        if (!developerField.getText().trim().isEmpty()) game.setDeveloper(developerField.getText().trim()); 
        if (genreComboBox.getValue() != null) game.setGenre(((String) genreComboBox.getValue()).trim()); 
        if (statusComboBox.getValue() != null) game.setStatus((String) statusComboBox.getValue());
        
        if (platformComboBox.getValue() != null) {
            updateOldPlatform();
            File oldImage = new File("resources/images/games/" + game.getID()+ ".png"); //any location
            
            game.setPlatform(((String) platformComboBox.getValue()).trim());
            game.setID(newGameID());
            File newImage = new File("resources/images/games/" + game.getID()+ ".png"); //any location
            
            oldImage.renameTo(newImage);
        }
        
        if (imageFile != null) {
            try{
                File newImage = new File("resources/images/games/" + game.getID()+ ".png"); //any location
                Files.copy(imageFile.toPath(), newImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException ex) {
            Logger.getLogger(AddGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        return game;
    }
    
    public void updateOldPlatform() {
        
        Platform platform = new Platform();
        
        for (int i = 0; i < platforms.getNumPlatforms(); i++) {
            platform = platforms.getPlatform(i);
            if (game.getPlatform().equals(platform.getName())) break;      
        }
        
        for (int i = 0; i < platform.getNumUsedIDs();i++) {
            if (platform.getUsedID(i) == game.getID()) {
                platform.addUnusedID(platform.deleteUsedID(i));
                break;
            }
        }
    }
    
    public int newGameID() {
        
        int newGameID;
        Platform platform = new Platform();
            platform.setName(game.getPlatform());
        
        for (int i = 0; i < platforms.getNumPlatforms(); i++) {
            
            if (platforms.getPlatform(i).getName().toLowerCase().equals(platform.getName().toLowerCase())) {
                
                platform = platforms.getPlatform(i);
                
                if (platform.getNumUnusedIDs() != 0) newGameID = platform.getUnusedIDs().remove(0);             
                else newGameID = (platform.getID() * 1000) + platform.getNumUsedIDs();
                
                platform.getUsedIDs().add(newGameID);
                return newGameID;  
            }
        }
        
        platform.setID((platforms.getNumPlatforms() + 1) * 100);
        newGameID = platform.getID() * 1000;
        platform.getUsedIDs().add(newGameID);
        
        platforms.addPlatform(platform);
        
        return newGameID;
    }
    
    public Platforms returnPlatforms() {
        return platforms;
    }
    
    public Games returnGames() {
        return games;
    }
   
}
