package Platforms;

import Games.AddGame;
import Necessities.FontGenerator;
import Necessities.ImagePane;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import libraryprogram.Platform;

public final class PlatformInfoPane extends VBox {
    private final Platform platform; 
    private GridPane gridPane;
    private ImagePane platformImage;
    private ImagePane smallerPlatformImage;
    
    public final String fontsPath = "/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/fonts/";
    private final Font largeFont; 
    private final Font smallFont;
    
    private StackPane titleStackPane;
    
    private Text title; 
    private Text release2;
    private Text developer2;
    private Text manufacturer2; 
    private Text generation2;
    private Text own2;
    private Text type2;
    private Text id2;
    
    private DatePicker releaseDP;
    private TextField developerTF;
    private TextField manufacturerTF;
    private ComboBox generationCB;
    private ComboBox ownCB;
    private ComboBox typeCB;
    
    private ObservableList<String> generationOptions; 
    
    private File imageFile;
    private FileChooser imageFileChooser;
    
    private StackPane imageStackPane;
    private Button platformImageButton;
    
    PlatformInfoPane(Platform p, int x, int y) {
        platform = p;
        largeFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", x).getFont();
        smallFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", y).getFont();
        createPlatformInfoPane();
    }
    
    PlatformInfoPane(Platform p, int x, int y, ObservableList<String> s) {
        platform = p;
        largeFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", x).getFont();
        smallFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", y).getFont();
        generationOptions = s;
        createPlatformInfoPane();
        createPlatformImageButton();
    }
    
    public void createPlatformInfoPane() {
        createGridPane();
        
        title = new Text();
            if (platform.getName().length() < 60) title = new Text(platform.getName());
            else title = new Text(platform.getName().substring(0, 60) + "...");
            title.setFill(Color.WHITE);
            title.setTextAlignment(TextAlignment.LEFT);
            title.setFont(largeFont);
            
        titleStackPane = new StackPane();
            titleStackPane.setAlignment(Pos.CENTER);
            titleStackPane.getChildren().add(title);
            
        platformImage = new ImagePane(platform.getPath(), 300, 150);
            platformImage.setAlignment(Pos.CENTER);
            
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(platformImage, gridPane, titleStackPane);
        this.setSpacing(10);
    }
    
    public void createGridPane() {
        
        Color textColor = Color.web("#F03B87");
        
        gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
        
        Text release = new Text("Released: ");
            release.setFill(textColor);
            release.setTextAlignment(TextAlignment.RIGHT);
            release.setFont(smallFont);
            
        Text developer = new Text("Developer: ");
            developer.setFill(textColor);
            developer.setTextAlignment(TextAlignment.RIGHT);
            developer.setFont(smallFont);
            
        Text manufacturer = new Text("Manufacturer: ");
            manufacturer.setFill(textColor);
            manufacturer.setTextAlignment(TextAlignment.RIGHT);
            manufacturer.setFont(smallFont);
        
        Text generation = new Text("Generation: ");
            generation.setFill(textColor);
            generation.setTextAlignment(TextAlignment.RIGHT);
            generation.setFont(smallFont);
            
        Text id = new Text("ID Number: ");
            id.setFill(textColor);
            id.setTextAlignment(TextAlignment.RIGHT);
            id.setFont(smallFont);
            
        Text type = new Text("Type: ");
            type.setFill(textColor);
            type.setTextAlignment(TextAlignment.RIGHT);
            type.setFont(smallFont);
            
        Text own = new Text("Own: ");
            own.setFill(textColor);
            own.setTextAlignment(TextAlignment.RIGHT);
            own.setFont(smallFont);
         
        gridPane.add(release, 0, 0);
        gridPane.add(developer, 0, 1);
        gridPane.add(manufacturer, 0, 2);
        gridPane.add(generation, 0, 3);
        gridPane.add(id, 0, 4);
        gridPane.add(type, 0, 5);
        gridPane.add(own, 0, 6);
        
        GridPane.setHalignment(release, HPos.RIGHT);
        GridPane.setHalignment(developer, HPos.RIGHT);
        GridPane.setHalignment(manufacturer, HPos.RIGHT);
        GridPane.setHalignment(generation, HPos.RIGHT);
        GridPane.setHalignment(id, HPos.RIGHT);
        GridPane.setHalignment(type, HPos.RIGHT);
        GridPane.setHalignment(own, HPos.RIGHT);
                    
        release2 = new Text(platform.getRelease());
            release2.setFill(Color.WHITE);
            release2.setTextAlignment(TextAlignment.LEFT);
            release2.setFont(smallFont);
            
        developer2 = new Text(platform.getDeveloper());
            developer2.setFill(Color.WHITE);
            developer2.setTextAlignment(TextAlignment.LEFT);
            developer2.setFont(smallFont);
        
         manufacturer2 = new Text(platform.getManufacturer());
            manufacturer2.setFill(Color.WHITE);
            manufacturer2.setTextAlignment(TextAlignment.LEFT);
            manufacturer2.setFont(smallFont);
            
        generation2 = new Text(platform.getGeneration());
            generation2.setFill(Color.WHITE);
            generation2.setTextAlignment(TextAlignment.LEFT);
            generation2.setFont(smallFont);
            
        id2 = new Text(platform.getID() + "");
            id2.setFill(Color.WHITE);
            id2.setTextAlignment(TextAlignment.LEFT);
            id2.setFont(smallFont);
            
        type2 = new Text(platform.getType());
            type2.setFill(Color.WHITE);
            type2.setTextAlignment(TextAlignment.LEFT);
            type2.setFont(smallFont);
            
        own2 = new Text();
            own2.setFill(Color.WHITE);
            own2.setTextAlignment(TextAlignment.LEFT);
            own2.setFont(smallFont);
            if(platform.isOwn()) own2.setText("Yes");
            else own2.setText("No");
        
        gridPane.add(release2, 1, 0);
        gridPane.add(developer2, 1, 1);
        gridPane.add(manufacturer2, 1, 2);
        gridPane.add(generation2, 1, 3);
        gridPane.add(id2, 1, 4);
        gridPane.add(type2, 1, 5); 
        gridPane.add(own2, 1, 6); 
        
    }
    
    public void createSmallPlatformImage() {
        smallerPlatformImage = new ImagePane(platform.getPath(), 225, 100);
            smallerPlatformImage.setAlignment(Pos.CENTER);
    }
    
    public GridPane getGridPane() {
        return gridPane;
    }
    
    public StackPane getSmallerImagePane() {
        return imageStackPane;
    }
    
    public void imageSelect() {
        
        Stage primaryStage = new Stage(); 
        imageFileChooser = new FileChooser();
        imageFile = imageFileChooser.showOpenDialog(primaryStage);
        
        if(imageFile != null) {
            ImagePane image = new ImagePane(imageFile.getAbsolutePath(), 225, 100);
            imageStackPane.getChildren().clear();
            imageStackPane.getChildren().addAll(image, platformImageButton);
            
        }
        
    }
    
    public void createPlatformImageButton() {
        smallerPlatformImage = new ImagePane(platform.getPath(), 225, 100);
            smallerPlatformImage.setAlignment(Pos.CENTER);
            
        platformImageButton = new Button("Add New\nPlatform Image");
            platformImageButton.setPrefSize(225, 100);
            platformImageButton.setTextAlignment(TextAlignment.CENTER);
            platformImageButton.setFont(largeFont);
            platformImageButton.setStyle("-fx-background-color: transparent;");
            platformImageButton.setTextFill(Color.WHITE);
            platformImageButton.setOnAction(new PlatformInfoPane.ButtonHandler());
            
        imageStackPane = new StackPane();
            imageStackPane.setAlignment(Pos.CENTER);
            imageStackPane.setMaxSize(225, 100);
            imageStackPane.setStyle("-fx-background-color: transparent; -fx-border-color: #F03B87;-fx-border-width: 1px;");
            imageStackPane.getChildren().addAll(smallerPlatformImage, platformImageButton);
    }
    
    public void addTextFields() {
        title.setVisible(false); 
        release2.setVisible(false); 
        manufacturer2.setVisible(false); 
        generation2.setVisible(false); 
        developer2.setVisible(false); 
        own2.setVisible(false); 
        type2.setVisible(false);

        releaseDP = new DatePicker();
            releaseDP.setPromptText(platform.getRelease());
            releaseDP.setEditable(false);
            
        developerTF = new TextField();
            developerTF.setFont(smallFont);
            developerTF.setPromptText(platform.getDeveloper());
            
        manufacturerTF = new TextField();
            manufacturerTF.setFont(smallFont);
            manufacturerTF.setPromptText(platform.getManufacturer()); 
            
        generationCB = new ComboBox(generationOptions);
            generationCB.setEditable(true);
            generationCB.setPromptText(platform.getGeneration()); 
        
        ObservableList<String> typeOptions = FXCollections.observableArrayList("Console", "Handheld","Duel", "Operation System");        
        typeCB = new ComboBox(typeOptions);
            typeCB.setPromptText(platform.getType());
            
        ObservableList<String> ownOptions = FXCollections.observableArrayList("Yes", "No");    
        ownCB = new ComboBox(ownOptions);
             ownCB.setPromptText("Own");
            
        gridPane.add(releaseDP, 1, 0);
        gridPane.add(developerTF, 1, 1);
        gridPane.add(manufacturerTF, 1, 2);
        gridPane.add(generationCB, 1, 3);
        gridPane.add(typeCB, 1, 5); 
        gridPane.add(ownCB, 1, 6); 
    }
    
    public Platform addCorrections() {
        
        if (releaseDP.getValue() != null) platform.setRelease(java.sql.Date.valueOf(releaseDP.getValue()));
        if (!developerTF.getText().trim().isEmpty()) platform.setDeveloper(developerTF.getText().trim()); 
        if (!manufacturerTF.getText().trim().isEmpty()) platform.setManufacturer(manufacturerTF.getText().trim()); 
        if (generationCB.getValue() != null) platform.setGeneration(((String) generationCB.getValue()).trim()); 
        if (typeCB.getValue() != null) platform.setType(((String) typeCB.getValue()).trim()); 
        if (ownCB.getValue() != null) {
            if(((String) typeCB.getValue()).trim().equals("Yes")) platform.setOwn(true);
            else platform.setOwn(false);
        } 
        
        if (imageFile != null) {
            try{
                File newImage = new File("/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/images/platforms/" + platform.getID()+ ".png"); //any location
                Files.copy(imageFile.toPath(), newImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException ex) {
            Logger.getLogger(AddGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        return platform;
    }
    
    public Boolean validateFields() {
        if (releaseDP.getValue() == null) return false; 
        if (developerTF.getText().trim().isEmpty()) return false; 
        if (manufacturerTF.getText().trim().isEmpty()) return false;
        if (generationCB.getValue() == null) return false;
        if (typeCB.getValue() == null) return false;
        if (ownCB.getValue() == null) return false;
        if (imageFile == null) return false; 

        else return true;
    }
    
    public class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
                      
            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
                        
            if (sourceButton.equals("Add New\nPlatform Image")) imageSelect();
            
        }
    }

}
