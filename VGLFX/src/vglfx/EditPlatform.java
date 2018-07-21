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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
import vgl.Games;
import static vgl.IO.writePlatforms;
import vgl.Platform;
import vgl.Platforms;
import static vglfx.IO.createNewPlatfomID;
import static vglfx.IO.readGenerations;
import static vglfx.IO.readGenres;
import static vglfx.IO.readPlatforms;
import static vglfx.IO.searchPlatforms;
import static vglfx.IO.sortPlatforms;

public class EditPlatform extends StackPane {
    private ImagePane background;
    private final Games games;
    private final Platforms platforms;
    private final String imagesPath;
    private final String fontsPath;
    
    private final File musicFile;
    private final MediaPlayer backgroundMediaPlayer;
    private final Media backgroundMusic;
    private final File selectSound;
    private MediaPlayer selectPlayer;
    
    private VBox editPlatformVBox;
    
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
    
    private Platform selectedPlatform;
    private Platform hoverPlatform;
    private PlatformPane selectedPlatformPane;
    
    private Stage hoverStage;
    
    private Button enter; 
    private Button cancel;

    private Stage primaryStage;
    private Scene newScene;
    
    private TextField nameField;
    private TextField releaseField;
    private TextField developerField;
    private TextField manufacturerField;
    private ComboBox generationCB;
    private ComboBox typeCB;
    private Button imageButton;
    
    private boolean[] update;
    
    private File imageFile;
    private FileChooser imageFileChooser;
    
    private Text text;
    
    public EditPlatform (Games games, Platforms platforms) { 
        this.games = games;
        this.platforms = platforms;
        imagesPath = "resources/images/platformsmenu/";
        fontsPath = "resources/fonts/";
        musicFile = new File("resources/sounds/spaceinvaders.mp3");
        backgroundMusic = new Media(musicFile.toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        selectSound = new File("resources/sounds/laser.aiff");
        createDeletePlatform();
    }
    
    private void createDeletePlatform() {
        createBackground();
        createSounds();
        createEditPlatformVBox();
        this.getChildren().addAll(background, editPlatformVBox);
    }
    
    public void createSounds() {
        Media sound = new Media(selectSound.toURI().toString());
        selectPlayer = new MediaPlayer(sound);
    }
    
    public void createBackground() {
        background = new ImagePane(imagesPath + "background.gif", 1200, 750);
    }
    
    public void createEditPlatformVBox() {
        
        editPlatformVBox = new VBox();
       
        Font headerFont = new CustomFont(fontsPath + "spaceinvaders.ttf", 140).getFont();
        Font headerFont2 = new CustomFont(fontsPath + "spaceinvaders.ttf", 135).getFont();
        Font headerFont3 = new CustomFont(fontsPath + "spaceinvaders.ttf", 130).getFont();
        
        
        
        Text headerText = new Text("EDIT PLATFORM");
            headerText.setFont(headerFont);
            headerText.setFill(Color.web("CEC062"));
            headerText.setTextAlignment(TextAlignment.CENTER);
            
        Text headerText2 = new Text("EDIT PLATFORM");
            headerText2.setFont(headerFont2);
            headerText2.setFill(Color.web("4E54E0"));
            headerText2.setTextAlignment(TextAlignment.CENTER);
            StackPane headerTextPane = new StackPane();
                headerTextPane.getChildren().add(headerText2);
                headerTextPane.setPadding(new Insets(0,0,25,0));
                headerTextPane.setAlignment(Pos.CENTER);
         
        Text headerText3 = new Text("EDIT PLATFORM");
            headerText3.setFont(headerFont3);
            headerText3.setFill(Color.web("54AD4A"));
            headerText3.setTextAlignment(TextAlignment.CENTER);
            StackPane headerTextPane2 = new StackPane();
                headerTextPane2.getChildren().add(headerText3);
                headerTextPane2.setPadding(new Insets(0,0,50,0));
                headerTextPane2.setAlignment(Pos.CENTER);
            
            
        StackPane headerPane = new StackPane();
            headerPane.getChildren().addAll(headerTextPane2, headerTextPane, headerText);    
        
        contentHBox = new HBox();    
            createContentHBox();
        
        backHBox = new HBox();
            createBackPane();
          
        editPlatformVBox.getChildren().addAll(headerPane, contentHBox, backHBox);
        editPlatformVBox.setAlignment(Pos.TOP_CENTER);
        editPlatformVBox.setSpacing(20);
        editPlatformVBox.setPadding(new Insets(25,0,0,0));
    
    }
    
    public void createContentHBox() {
        
        leftContentVBox = new VBox();
            leftContentVBox.setAlignment(Pos.CENTER);
            leftContentVBox.setSpacing(10);
            leftContentVBox.setMinSize(450, 400);
            leftContentVBox.setMaxSize(450, 400);
        createLeftContentVBox();
            
        
        rightContentStackPane = new StackPane();
            rightContentStackPane.setAlignment(Pos.CENTER);
            rightContentStackPane.setMinSize(650, 400);
            rightContentStackPane.setMaxSize(650, 400);
            
        createRightContentStackPane("Default");
        
        contentHBox.getChildren().addAll(leftContentVBox, rightContentStackPane);
            contentHBox.setAlignment(Pos.CENTER);
            contentHBox.setSpacing(10);
    }
    
    public void createLeftContentVBox() {
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 30).getFont();
        Font searchFont = new CustomFont(fontsPath + "joystick.ttf", 14).getFont();
        
        Text headerText = new Text("Master Platform List");
                headerText.setFont(textFont);
                headerText.setFill(Color.web("E67F7C"));
                    
        searchField = new TextField();
            searchField.setPrefSize(350, 20);
        
        Button searchButton = new Button("Search");
            searchButton.setFont(searchFont);
            searchButton.setOnAction(new EditPlatform.ButtonHandler());
            searchButton.setOnMouseEntered(new EditPlatform.HoverOn());
            searchButton.setOnMouseExited(new EditPlatform.HoverOff());
            searchButton.setStyle("-fx-background-color: transparent;");
            searchButton.setTextFill(Color.WHITE);
            searchButton.setAlignment(Pos.CENTER_LEFT);
        
        Button clearButton = new Button("Clear");
            clearButton.setFont(searchFont);
            clearButton.setOnAction(new EditPlatform.ButtonHandler());
            clearButton.setOnMouseEntered(new EditPlatform.HoverOn());
            clearButton.setOnMouseExited(new EditPlatform.HoverOff());
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

         
        if (platforms.getNumPlatforms() > 0) {
            
            contentScrollPane = new ScrollPane();
                contentScrollPane.setStyle("-fx-background-color: #000000;");
            
            contentScrollPaneVBox = new VBox();
                contentScrollPaneVBox.setMaxWidth(450);
                contentScrollPaneVBox.setMinWidth(450);
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
        rightContentStackPane.setAlignment(Pos.CENTER);
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 15).getFont();
        
        text = new Text();
            text.setFont(textFont);
            text.setFill(Color.WHITE);
            text.setWrappingWidth(400);
            text.setTextAlignment(TextAlignment.CENTER);
            
        if (version.equals("Default")) {
            text.setText("Select a platform to edit.");
            rightContentStackPane.getChildren().add(text);
        }
        
        else if (version.equals("Selected")) {

        Font gridFont = new CustomFont(fontsPath + "joystick.ttf", 10).getFont();
        Font imageFont = new CustomFont(fontsPath + "joystick.ttf", 25).getFont();
                
        selectedPlatformPane = new PlatformPane(selectedPlatform);

        imageButton = new Button ("Click to\nEdit Image");
                imageButton.setPrefSize(300, 150);
                imageButton.setFont(imageFont);
                imageButton.setTextAlignment(TextAlignment.CENTER);
                imageButton.setStyle("-fx-background-color: transparent;");
                imageButton.setOnAction(new EditPlatform.ButtonHandler());
        selectedPlatformPane.getImagePane().getChildren().add(imageButton);

        for (int i = 0; i < 7; i++) selectedPlatformPane.getPlatformInfoPane().getChildren().remove(7);
        
        
        nameField = new TextField();
            nameField.setPromptText(selectedPlatform.getName());
        releaseField = new TextField();
            releaseField.setPromptText(selectedPlatform.getRelease());
        developerField = new TextField();
            developerField.setPromptText(selectedPlatform.getDeveloper());
        manufacturerField = new TextField();
            manufacturerField.setPromptText(selectedPlatform.getManufacturer());
        generationCB = new ComboBox(readGenerations(platforms));
            generationCB.setPromptText(selectedPlatform.getGeneration());
            generationCB.setEditable(true);
        typeCB = new ComboBox(FXCollections.observableArrayList("Console","Handheld", "Operating System"));
            typeCB.setPromptText(selectedPlatform.getType());
            typeCB.setEditable(true);
        
        
        Text ID = new Text(new String() + selectedPlatform.getID());
            ID.setFont(gridFont);
            ID.setFill(Color.WHITE);
        
        selectedPlatformPane.getPlatformInfoPane().add(nameField, 1, 0);
        selectedPlatformPane.getPlatformInfoPane().add(releaseField, 1, 1);
        selectedPlatformPane.getPlatformInfoPane().add(developerField, 1, 2);
        selectedPlatformPane.getPlatformInfoPane().add(manufacturerField, 1, 3);
        selectedPlatformPane.getPlatformInfoPane().add(generationCB, 1, 4);
        selectedPlatformPane.getPlatformInfoPane().add(ID, 1, 5);
        selectedPlatformPane.getPlatformInfoPane().add(typeCB, 1, 6);
                
        text.setText("Please change the desired fields.");
            
            enter = new Button();
                enter.setText("Enter");
                enter.setFont(textFont);
                enter.setTextFill(Color.WHITE);
                enter.setStyle("-fx-background-color: transparent;");
                enter.setOnAction(new EditPlatform.ButtonHandler());
                enter.setOnMouseEntered(new EditPlatform.HoverOn());
                enter.setOnMouseExited(new EditPlatform.HoverOff());
            
            cancel = new Button();
                cancel.setText("Cancel");
                cancel.setFont(textFont);
                cancel.setTextFill(Color.WHITE);
                cancel.setStyle("-fx-background-color: transparent;");
                cancel.setOnAction(new EditPlatform.ButtonHandler());
                cancel.setOnMouseEntered(new EditPlatform.HoverOn());
                cancel.setOnMouseExited(new EditPlatform.HoverOff());

            HBox optionButtons = new HBox();
                optionButtons.setSpacing(5);
                optionButtons.setAlignment(Pos.CENTER);
                optionButtons.getChildren().addAll(enter, cancel);
            
            VBox enterVBox = new VBox();
                enterVBox.setAlignment(Pos.CENTER);
                enterVBox.setSpacing(10);
                enterVBox.getChildren().addAll(selectedPlatformPane, text, optionButtons);
                
            rightContentStackPane.getChildren().add(enterVBox);
        }
        
        else if (version.equals("Finished")) {
            text.setText(selectedPlatform.getName() + " has been successfully edited.");
            rightContentStackPane.getChildren().add(text);
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
                  javafx.application.Platform.runLater(() -> {
                      imageButton.setTextFill(colors[n]);
                 });
            }
       }, 0, 400);
    }
    
    public void createContentScrollPane() {
        
        contentScrollPaneVBox.getChildren().clear();
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 14).getFont();
        
        Platforms sortedPlatform = sortPlatforms(platforms);

        int n = sortedPlatform.getNumPlatforms();

        Button platformButton;
        String platformText;
        int wrappingWidth;
        
        for (int i = 0; i < n; i++) {
            
            platformText = sortedPlatform.getPlatform(i).getName(); 

                platformButton = new Button(platformText);
                    platformButton.setFont(textFont);
                    platformButton.setTextFill(Color.WHITE);
                    platformButton.setAlignment(Pos.CENTER_LEFT);
                    platformButton.setStyle("-fx-background-color: transparent;");
                    platformButton.setOnAction(new EditPlatform.ButtonHandler());
                    platformButton.setOnMouseEntered(new EditPlatform.HoverOn());
                    platformButton.setOnMouseExited(new EditPlatform.HoverOff());

                contentScrollPaneVBox.getChildren().add(platformButton);
            }

                if (n > 9) wrappingWidth = 450;
                else wrappingWidth = 434;
                
                if (n == 1) {
                    contentScrollPane.setMinViewportHeight(12);
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
    
    public void createBackPane() {
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 25).getFont();
        
        Text backText = new Text("Back");
            backText.setFont(textFont);
            backText.setFill(Color.WHITE);
        
        backButton = new ImagePane(imagesPath + "icon.png", 50, 40);
        backButton.setVisible(false);
            
        Button back = new Button("Back");
            back.setFont(textFont);
            back.setTextAlignment(TextAlignment.CENTER);
            back.setTextFill(Color.WHITE);
            back.setStyle("-fx-background-color: transparent;");
            back.setOnAction(new EditPlatform.ButtonHandler());
            back.setOnMouseEntered(new EditPlatform.HoverOn());
            back.setOnMouseExited(new EditPlatform.HoverOff());
        
        backHBox.getChildren().addAll(backButton, back);
            backHBox.setAlignment(Pos.CENTER);
            backHBox.setStyle("-fx-background-color: #000000;");
            backHBox.setMaxWidth(200);
            backHBox.setMinWidth(200);
    }
    
    public void search() {

        contentScrollPaneVBox.getChildren().clear();
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 14).getFont();

        if (!searchField.getText().trim().isEmpty()) {
            
            Platforms searchResults = searchPlatforms(platforms, searchField.getText().toLowerCase().replaceAll("\\s+",""));
            int n = searchResults.getNumPlatforms();
            int wrappingWidth;
            
            if (n > 0) {
                Button platformButton;
                String platformText;


                for (int i = 0; i < n; i++) {
                    platformText = searchResults.getPlatform(i).getName(); 

                        platformButton = new Button(platformText);
                            platformButton.setFont(textFont);
                            platformButton.setTextFill(Color.WHITE);
                            platformButton.setAlignment(Pos.CENTER_LEFT);
                            platformButton.setStyle("-fx-background-color: transparent;");
                            platformButton.setOnAction(new EditPlatform.ButtonHandler());
                            platformButton.setOnMouseEntered(new EditPlatform.HoverOn());
                            platformButton.setOnMouseExited(new EditPlatform.HoverOff());

                        contentScrollPaneVBox.getChildren().add(platformButton);
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

                if (n > 9) wrappingWidth = 450;
                else wrappingWidth = 434;
                
                if (n == 1) {
                    contentScrollPane.setMinViewportHeight(12);
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
        
        else createContentScrollPane();
    }
    
    //need to fix so it doesn't replace, delete, or rename
    public void confirmEdit() {
        
//        if (update[0]|| update[6]) {
//            if (update[0] && !update[6]) {
//                
//                File oldPictureName = new File(selectedPlatform.getPicture()); //creates link to pictue of game
//                
//                    selectedPlatform.setName(nameField.getText().trim());
//                    selectedPlatform.setImage(selectedPlatform.getName() + ".png");
//
//                File newPictureName = new File(selectedPlatform.getPicture());
//                
//                if (selectedPlatform.getImage().equals("notavailable.png")) {
//                    
//                    try {
//                        Files.copy(oldPictureName.toPath(), newPictureName.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
//                    } catch (IOException ex) {
//                        Logger.getLogger(EditPlatform.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                
//                else oldPictureName.renameTo(newPictureName); 
//            }
//            
//            else if (update[0] && update[6]) {
//                
//                if (!selectedPlatform.getImage().equals("notavailable.png")) {
//                    File deleteFile = new File(selectedPlatform.getPicture()); //creates link to pictue of game
//                    deleteFile.delete(); //deletes picture for space purposes
//                }
//                
//                selectedPlatform.setName(nameField.getText().trim());
//                selectedPlatform.setImage(selectedPlatform.getName() + ".png");
//                
//                try {
//                    File newImage = new File(selectedPlatform.getPicture()); //any location
//                    Files.copy(imageFile.toPath(), newImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                }
//                catch (IOException ex) {
//                    System.exit(-1);
//                }
//            }
//            
//            else if (!update[0] && update[6]) {
//                 try {
//                    if (selectedPlatform.getImage().equals("notavailable.png")) selectedPlatform.setImage(selectedPlatform.getName() + ".png");
//                        File newImage = new File(selectedPlatform.getPicture()); //any location
//                        Files.copy(imageFile.toPath(), newImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                }
//                catch (IOException ex) {
//                    System.exit(-1);
//                }
//            }
//        }
        if (update[0]) selectedPlatform.setName(nameField.getText().trim());
        if (update[1]) selectedPlatform.setRelease(releaseField.getText().trim());
        if (update[2]) selectedPlatform.setDeveloper(developerField.getText().trim());
        if (update[3]) selectedPlatform.setManufacturer(manufacturerField.getText().trim());
        if (update[4]) selectedPlatform.setGeneration(((String)generationCB.getValue()).trim());
        if (update[5]) selectedPlatform.setType(((String)typeCB.getValue()).trim());
        if (update[6]) {
            saveImage();
            selectedPlatform.setImage(selectedPlatform.getName().trim() + ".png");
        }
       
        
        writePlatforms(platforms);
        
        platforms.getPlatformMap().put(selectedPlatform.getName(), selectedPlatform);
        platforms.getPlatformIDs().put(selectedPlatform.getName(), selectedPlatform.getID());
         
        createRightContentStackPane("Finished");
        searchField.clear();
        createContentScrollPane();
    }
    
    public void saveImage() {
        
        try {
            
            if (!selectedPlatform.getImage().equals("notavailable.png") && update[0]) {
                 File deleteFile = new File(selectedPlatform.getPicture()); //creates link to pictue of game
                 deleteFile.delete(); //deletes picture for space purposes
            }
            
            File newImage = new File(selectedPlatform.getPath() + selectedPlatform.getName() + ".png"); //any location
            Files.copy(imageFile.toPath(), newImage.toPath(), StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException ex) {
            System.exit(-1);
        }
    }
    
    public void createUpdateBooleans() {
        
        update = new boolean[7]; 
        for (int i = 0; i < update.length; i++) update[i] = true;
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
    
    public void updatePlatformPane() {
        
        text.setText("Please confirm changes to platform.");
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 10).getFont();
        
        createUpdateBooleans();
        enter.setText("Confirm");
        
        for (int i = 0; i < 7; i++) selectedPlatformPane.getPlatformInfoPane().getChildren().remove(7);
        
            Text name = new Text();
                name.setFont(textFont);
                name.setFill(Color.WHITE);
                name.setWrappingWidth(170);
                if (nameField.getText().trim().length() > 0) name.setText(nameField.getText().trim());
                else {
                    name.setText(selectedPlatform.getName());
                    update[0] = false;
                }
                selectedPlatformPane.getPlatformInfoPane().add(name, 1, 0);
                
            Text release = new Text();
                release.setFont(textFont);
                release.setFill(Color.WHITE);
                release.setWrappingWidth(170);
                String releaseString = release.getText().trim();
                if (releaseString.length() > 0 && canBeParsed(releaseString)) release.setText(releaseField.getText().trim());
                else {
                    release.setText(selectedPlatform.getRelease());
                    update[1] = false;
                }
                selectedPlatformPane.getPlatformInfoPane().add(release, 1, 1);
            
            Text developer = new Text();
                developer.setFont(textFont);
                developer.setFill(Color.WHITE);
                developer.setWrappingWidth(170);
                if (developerField.getText().trim().length() > 0) developer.setText(developerField.getText().trim());
                else {
                    developer.setText(selectedPlatform.getDeveloper());
                    update[2] = false;
                }
                selectedPlatformPane.getPlatformInfoPane().add(developer, 1, 2);
            
            Text manufacturer = new Text();
                manufacturer.setFont(textFont);
                manufacturer.setFill(Color.WHITE);
                manufacturer.setWrappingWidth(170);
                if (manufacturerField.getText().trim().length() > 0) manufacturer.setText(manufacturerField.getText().trim());
                else {
                    manufacturer.setText(selectedPlatform.getManufacturer());
                    update[3] = false;
                }
                selectedPlatformPane.getPlatformInfoPane().add(manufacturer, 1, 3);
            
            Text generation = new Text();
                generation.setFont(textFont);
                generation.setFill(Color.WHITE);
                generation.setWrappingWidth(170);
                String genString = (new String() + (String)generationCB.getValue()).trim();
                if (!genString.equals("null")) generation.setText(genString);
                else {
                    generation.setText(selectedPlatform.getGeneration());
                    update[4] = false;
                }
                selectedPlatformPane.getPlatformInfoPane().add(generation, 1, 4);
           
            Text platformID = new Text();
                platformID.setFont(textFont);
                platformID.setFill(Color.WHITE);
                platformID.setText(new String() + selectedPlatform.getID());
                selectedPlatformPane.getPlatformInfoPane().add(platformID, 1, 5);
                
            Text type = new Text();
                type.setFont(textFont);
                type.setFill(Color.WHITE);
                type.setWrappingWidth(170);
                String typeString = (new String() + (String)typeCB.getValue()).trim();
                if (!typeString.equals("null")) type.setText(typeString);
                else {
                    type.setText(selectedPlatform.getType());
                    update[5] = false;
                }
                selectedPlatformPane.getPlatformInfoPane().add(type, 1, 6);
                
            selectedPlatformPane.getImagePane().getChildren().clear();
            
            if (imageFile == null) {
                update[6] = false;
                selectedPlatformPane.getImagePane().getChildren().add(new ImagePane(selectedPlatform.getPicture(), 300, 150));           
            }
            else selectedPlatformPane.getImagePane().getChildren().add(new ImagePane(imageFile.getAbsolutePath(), 300, 150));   
    }
    
    public void imageSelect() {
        
        imageFileChooser = new FileChooser();
        primaryStage = new Stage(); 
        imageFile = imageFileChooser.showOpenDialog(primaryStage);
        
        if(imageFile != null) {
            selectedPlatformPane.getImagePane().getChildren().clear();
            selectedPlatformPane.getImagePane().getChildren().add(new ImagePane(imageFile.getAbsolutePath(), 300, 150));
            selectedPlatformPane.getImagePane().getChildren().add(imageButton);
            
        }
        
    }
    
    public class ButtonHandler implements EventHandler<ActionEvent> { 
          @Override
        public void handle(ActionEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("Back")) platform();
            else if (sourceButton.equals("Search")) search();
            else if (sourceButton.equals("Confirm")) confirmEdit();
            else if (sourceButton.equals("Enter")) updatePlatformPane();
            else if (sourceButton.equals("Click to\nEdit Image")) imageSelect();
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
                selectedPlatform = platforms.getPlatformMap().get(sourceButton);
                createRightContentStackPane("Selected");
                funColors();
                
            }
        }
    }
    
    public class HoverOn implements EventHandler<MouseEvent> {
        
        

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
            
            selectPlayer.stop();
            selectPlayer.play();

            if (sourceButton.equals("Back")) backButton.setVisible(true);
            else if (sourceButton.equals("Search")) button.setTextFill(Color.web("CEC062"));
            else if (sourceButton.equals("Clear")) button.setTextFill(Color.web("CEC062"));
            else if (sourceButton.equals("Confirm")) button.setTextFill(Color.web("CEC062"));
            else if (sourceButton.equals("Enter")) button.setTextFill(Color.web("CEC062"));
            else if (sourceButton.equals("Cancel")) button.setTextFill(Color.web("CEC062"));
            else {
                
                Bounds bounds = button.getBoundsInLocal();
                Bounds screenBounds = button.localToScreen(bounds);
                
                int x = (int) screenBounds.getMinX();
                int y = (int) screenBounds.getMinY();
                int width = (int) screenBounds.getWidth();
                
                hoverStage = new Stage();
                hoverStage.setX(x + width);
                hoverStage.setY(y - 220);

                button.setTextFill(Color.web("CEC062"));
                hoverPlatform = platforms.getPlatformMap().get(sourceButton);
                selectedPlatform();
            }
           
        }
    }
    
    public class HoverOff implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
            
            
            selectPlayer.play();
            selectPlayer.stop();

            if (sourceButton.equals("Back")) backButton.setVisible(false);
            else if (sourceButton.equals("Search")) button.setTextFill(Color.WHITE);
            else if (sourceButton.equals("Clear")) button.setTextFill(Color.WHITE);
            else if (sourceButton.equals("Confirm")) button.setTextFill(Color.WHITE);
            else if (sourceButton.equals("Enter")) button.setTextFill(Color.WHITE);
            else if (sourceButton.equals("Cancel")) button.setTextFill(Color.WHITE);
            else {
                hoverStage.getScene().getWindow().hide();
                button.setTextFill(Color.WHITE);
            }

        }
    }
    
    public void platform() {
        
        backgroundMediaPlayer.stop();
        
        this.setVisible(false);
        this.getScene().getWindow().hide();
        primaryStage = new Stage(); 
        PlatformsMenu platformsMenu = new PlatformsMenu(games, platforms); 
       newScene = new Scene(platformsMenu);
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();
         
    }
    
    public void selectedPlatform() {

        PlatformPane platformPane = new PlatformPane(hoverPlatform);
            platformPane.setStyle("-fx-background-color: #54AD4A;");
        newScene = new Scene(platformPane);

        hoverStage.setResizable(false);
        hoverStage.setScene(newScene);
        hoverStage.initStyle(StageStyle.UNDECORATED);
        hoverStage.show();

    }
}