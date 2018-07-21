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
import vgl.Platform;
import vgl.Platforms;
import vgl.Games;
import static vgl.IO.writePlatforms;
import static vglfx.IO.createNewPlatfomID;
import static vglfx.IO.readGenerations;

public class AddPlatform extends StackPane {
    private ImagePane background;
    private final Games games;
    private final Platforms platforms;
    private final String imagesPath;
    private final String fontsPath;
    
    private final File selectSound;
    private MediaPlayer selectPlayer;
    
    private VBox addPlatformVBox; //vbox that will hold everything for the pane
    private HBox contentHBox;
    private HBox optionsHBox;
    private HBox backHBox;
    private ImagePane backButton; 
    
    private VBox leftVBox;
    private StackPane rightVBox;
    
    private Button enter;
    private Button clear;
    
    private String prompt;
    
    private TextField nameField;
    private TextField releaseField;
    private TextField developerField;
    private TextField manufacturerField;
    private ComboBox generationCB;
    private ComboBox typeCB;
    
    private Button imageButton; //to add image for platform 
    
    ObservableList<String> generationOptions;
    ObservableList<String> typeOptions;
    
    private Platform blankPlatform;
    private PlatformPane blankPlatformPane;
    private int blankPlatformID;
    
    private File imageFile;
    private FileChooser imageFileChooser;
    
    private boolean[] update;
    
    public AddPlatform(Games games, Platforms platforms) {
        this.games = games;
        this.platforms = platforms;
        imagesPath = "resources/images/platformsmenu/";
        fontsPath = "resources/fonts/";
        selectSound = new File("resources/sounds/laser.aiff");
        typeOptions = FXCollections.observableArrayList("Console","Handheld", "Operating System");
        createAddPlatform();
    }
    
    private void createAddPlatform() {
        createBackground();
        createSounds();
        createAddPlatformVBox();
        this.getChildren().addAll(background, addPlatformVBox);
    }
    
    public void createSounds() {
        Media sound = new Media(selectSound.toURI().toString());
        selectPlayer = new MediaPlayer(sound);
    }
    
    public void createBackground() {
        background = new ImagePane(imagesPath + "background.gif", 1200, 750);
    }
    
    public void createAddPlatformVBox() {
        
        addPlatformVBox = new VBox();
       
        Font headerFont = new CustomFont(fontsPath + "spaceinvaders.ttf", 140).getFont();
        Font headerFont2 = new CustomFont(fontsPath + "spaceinvaders.ttf", 135).getFont();
        Font headerFont3 = new CustomFont(fontsPath + "spaceinvaders.ttf", 130).getFont();
        
        
        
        Text headerText = new Text("ADD PLATFORM");
            headerText.setFont(headerFont);
            headerText.setFill(Color.web("CEC062"));
            headerText.setTextAlignment(TextAlignment.CENTER);
            
        Text headerText2 = new Text("ADD PLATFORM");
            headerText2.setFont(headerFont2);
            headerText2.setFill(Color.web("4E54E0"));
            headerText2.setTextAlignment(TextAlignment.CENTER);
            StackPane headerTextPane = new StackPane();
                headerTextPane.getChildren().add(headerText2);
                headerTextPane.setPadding(new Insets(0,0,25,0));
                headerTextPane.setAlignment(Pos.CENTER);
         
        Text headerText3 = new Text("ADD PLATFORM");
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
          
        addPlatformVBox.getChildren().addAll(headerPane, contentHBox, backHBox);
        addPlatformVBox.setAlignment(Pos.TOP_CENTER);
        addPlatformVBox.setSpacing(40);
        addPlatformVBox.setPadding(new Insets(25,0,0,0));
    
    }
    
     public void createContentHBox() {
        
        leftVBox = new VBox();
            leftVBox.setAlignment(Pos.CENTER);
            leftVBox.setSpacing(10);
        createLeftVBox();
        funColors();
            
        
        rightVBox = new StackPane();
            rightVBox.setAlignment(Pos.CENTER);
        createRightVBox();
        
        contentHBox.getChildren().addAll(leftVBox , rightVBox); 
            contentHBox.setAlignment(Pos.CENTER);
    }
     
    public void createRightVBox() {
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 15).getFont();
        
        Text select = new Text("Fill all fields to create new platform.");
                select.setWrappingWidth(475);
                select.setFont(textFont);
                select.setFill(Color.WHITE);
                select.setTextAlignment(TextAlignment.CENTER);
                
        rightVBox.getChildren().clear();
        rightVBox.getChildren().add(select);
            rightVBox.setAlignment(Pos.CENTER);
    }
    
    public void createLeftVBox() {
        
        Font headerFont = new CustomFont(fontsPath + "joystick.ttf", 30).getFont();
        
        Text headerText = new Text("Create a new platform");
                headerText.setFont(headerFont);
                headerText.setFill(Color.web("E67F7C"));
                headerText.setTextAlignment(TextAlignment.CENTER);
        
        leftVBox.getChildren().clear();
        
        blankPlatform = new Platform();
        blankPlatformID = createNewPlatfomID(platforms);
            
        blankPlatformPane = new PlatformPane(blankPlatform);
            
        Font imageFont = new CustomFont(fontsPath + "joystick.ttf", 25).getFont();
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 10).getFont();

            
            imageButton = new Button ("Click to\nAdd Image");
                imageButton.setPrefSize(300, 150);
                imageButton.setFont(imageFont);
                imageButton.setTextAlignment(TextAlignment.CENTER);
                imageButton.setOnAction(new AddPlatform.ButtonHandler());
                
                
        blankPlatformPane.getImagePane().getChildren().add(imageButton);
        
        for (int i = 0; i < 7; i++) blankPlatformPane.getPlatformInfoPane().getChildren().remove(7);
        
            nameField = new TextField();
                nameField.setMaxSize(175, 15);
                blankPlatformPane.getPlatformInfoPane().add(nameField, 1, 0);
                
            releaseField = new TextField();
                releaseField.setMaxSize(175, 15);
                blankPlatformPane.getPlatformInfoPane().add(releaseField, 1, 1);
            
            developerField = new TextField();
                developerField.setMaxSize(175, 15);
                blankPlatformPane.getPlatformInfoPane().add(developerField, 1, 2);
            
            manufacturerField = new TextField();
                manufacturerField.setMaxSize(175, 15);
                blankPlatformPane.getPlatformInfoPane().add(manufacturerField, 1, 3);
            
            generationCB = new ComboBox(readGenerations(platforms));
                generationCB.setMaxSize(175, 15);
                generationCB.setEditable(true);
                blankPlatformPane.getPlatformInfoPane().add(generationCB, 1, 4);
           
            Text platformID = new Text();
                platformID.setFont(textFont);
                platformID.setFill(Color.WHITE);
                platformID.setText(new String() + blankPlatformID);
                 blankPlatformPane.getPlatformInfoPane().add(platformID, 1, 5);
                
            typeCB = new ComboBox(typeOptions);
                typeCB.setMaxSize(175, 15);
                blankPlatformPane.getPlatformInfoPane().add(typeCB, 1, 6);
            
            optionsHBox = new HBox();
                createOptionsHBox();
            
                
            leftVBox.getChildren().addAll(headerText, blankPlatformPane, optionsHBox);
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
    
    public void createOptionsHBox() {
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 20).getFont();
            
        enter = new Button("Enter");
            enter.setFont(textFont);
            enter.setTextAlignment(TextAlignment.CENTER);
            enter.setTextFill(Color.WHITE);
            enter.setStyle("-fx-background-color: transparent;");
            enter.setOnAction(new AddPlatform.ButtonHandler());
            enter.setOnMouseEntered(new AddPlatform.HoverOn());
            enter.setOnMouseExited(new AddPlatform.HoverOff());
            
        clear = new Button("Clear");
            clear.setFont(textFont);
            clear.setTextAlignment(TextAlignment.CENTER);
            clear.setTextFill(Color.WHITE);
            clear.setStyle("-fx-background-color: transparent;");
            clear.setOnAction(new AddPlatform.ButtonHandler());
            clear.setOnMouseEntered(new AddPlatform.HoverOn());
            clear.setOnMouseExited(new AddPlatform.HoverOff());
        
        optionsHBox.getChildren().addAll(enter, clear);
            optionsHBox.setAlignment(Pos.CENTER);
            optionsHBox.setStyle("-fx-background-color: #000000;");
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
            back.setOnAction(new AddPlatform.ButtonHandler());
            back.setOnMouseEntered(new AddPlatform.HoverOn());
            back.setOnMouseExited(new AddPlatform.HoverOff());
        
        backHBox.getChildren().addAll(backButton, back);
            backHBox.setAlignment(Pos.CENTER);
            backHBox.setStyle("-fx-background-color: #000000;");
            backHBox.setMaxWidth(200);
            backHBox.setMinWidth(200);
    }
    
    public void imageSelect() {
        
        imageFileChooser = new FileChooser();
        Stage primaryStage = new Stage(); 
        imageFile = imageFileChooser.showOpenDialog(primaryStage);
        
        if(imageFile != null) {
            imageButton.setStyle("-fx-background-color: transparent;");
            imageButton.setTextFill(Color.WHITE);
            imageButton.setText("Click to\nEdit image");
            blankPlatformPane.getImagePane().getChildren().clear();
            blankPlatformPane.getImagePane().getChildren().add(new ImagePane(imageFile.getAbsolutePath(), 300, 150));
            blankPlatformPane.getImagePane().getChildren().add(imageButton);
            
        }
        
    }
    
    public void clearAll() {
        imageFile = new File("null");
        imageButton.setStyle(null);
        
        blankPlatformPane.getImagePane().getChildren().clear();
        blankPlatformPane.getImagePane().getChildren().add(imageButton);
        
        nameField.clear();
        releaseField.clear();
        developerField.clear();
        manufacturerField.clear();
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
    
    public void createUpdateBooleans() {
        
        update = new boolean[7]; 
        for (int i = 0; i < update.length; i++) update[i] = false;
    }
    
    public void checkInput() {
        
        createUpdateBooleans();
            
            String prompt = "The following errors have been found:";
            
            if (nameField.getText().trim().length() == 0) prompt += "\n Name field is empty";
            else if (platforms.getPlatformMap().containsKey(nameField.getText().trim())) prompt += "\nPlatform name already exists.";
            else update[0] = true;
            
            if (releaseField.getText().trim().length() == 0) prompt += "\n Release field is empty";
            else if (!canBeParsed(releaseField.getText())) prompt += "\n Release field format is invalid.";
            else update[1] = true;
            
            if (developerField.getText().trim().length() == 0) prompt += "\n Developer field is empty.";
            else update[2] = true;
            
            if (manufacturerField.getText().trim().length() == 0) prompt += "\n Manufacturer field is empty.";
            else update[3] = true;
            
            if (( new String() + (String)generationCB.getValue()).trim().equals("null")) prompt += "\n Generation field is empty.";
            else update[4] = true;
        
            if (( new String() + (String)typeCB.getValue()).trim().equals("null")) prompt += "\n Type has not been selected.";
            else update[5] = true;
            
            if(imageFile == null) prompt += "\n Image has not been uploaded";
            else update[6] = true;
            
            if (update[0] && update[1] && update[2] && update[3] && update[4] && update[5] && update[6]) confirmPlatform();
            else {
                
                Font textFont = new CustomFont(fontsPath + "joystick.ttf", 15).getFont();
        
                Text errors = new Text(prompt);
                        errors.setWrappingWidth(475);
                        errors.setFont(textFont);
                        errors.setFill(Color.WHITE);
                        errors.setTextAlignment(TextAlignment.CENTER);
                        
                rightVBox.getChildren().clear();
                rightVBox.getChildren().add(errors);
            }
    }
    
    public void confirmPlatform() {
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 10).getFont();
           
        blankPlatformPane.getImagePane().getChildren().clear();
        blankPlatformPane.getImagePane().getChildren().add(new ImagePane(imageFile.getAbsolutePath(), 300, 150));
        
        for (int i = 0; i < 7; i++) blankPlatformPane.getPlatformInfoPane().getChildren().remove(7);
        
            Text name = new Text();
                name.setFont(textFont);
                name.setFill(Color.WHITE);
                name.setText(nameField.getText().trim());
                blankPlatformPane.getPlatformInfoPane().add(name, 1, 0);
                blankPlatform.setName(nameField.getText().trim());
                
            Text release = new Text();
                release.setFont(textFont);
                release.setFill(Color.WHITE);
                release.setText(releaseField.getText().trim());
                blankPlatformPane.getPlatformInfoPane().add(release, 1, 1);
                blankPlatform.setRelease(releaseField.getText().trim());
            
            Text developer = new Text();
                developer.setFont(textFont);
                developer.setFill(Color.WHITE);
                developer.setText(developerField.getText().trim());
                blankPlatformPane.getPlatformInfoPane().add(developer, 1, 2);
                blankPlatform.setDeveloper(developerField.getText().trim());
            
            Text manufacturer = new Text();
                manufacturer.setFont(textFont);
                manufacturer.setFill(Color.WHITE);
                manufacturer.setText(manufacturerField.getText().trim());
                blankPlatformPane.getPlatformInfoPane().add(manufacturer, 1, 3);
                blankPlatform.setManufacturer(manufacturerField.getText().trim());
            
            Text generation = new Text();
                generation.setFont(textFont);
                generation.setFill(Color.WHITE);
                generation.setText(((String) generationCB.getValue()).trim());
                blankPlatformPane.getPlatformInfoPane().add(generation, 1, 4);
                blankPlatform.setGeneration(((String) generationCB.getValue()).trim());
           
            Text platformID = new Text();
                platformID.setFont(textFont);
                platformID.setFill(Color.WHITE);
                platformID.setText(new String() + blankPlatformID);
                blankPlatformPane.getPlatformInfoPane().add(platformID, 1, 5);
                blankPlatform.setID(blankPlatformID);
                
            Text type = new Text();
                type.setFont(textFont);
                type.setFill(Color.WHITE);
                type.setText(((String) typeCB.getValue()).trim());
                blankPlatformPane.getPlatformInfoPane().add(type, 1, 6);
                blankPlatform.setType(((String) typeCB.getValue()).trim());
                
                enter.setText("Confirm");
                clear.setText("Cancel");
                
        Font biggerTextFont = new CustomFont(fontsPath + "joystick.ttf", 15).getFont();
        
                Text errors = new Text("Press confirm to add new platform or cancel to delete.");
                        errors.setWrappingWidth(475);
                        errors.setFont(biggerTextFont);
                        errors.setFill(Color.WHITE);
                        errors.setTextAlignment(TextAlignment.CENTER);
                        
                rightVBox.getChildren().clear();
                rightVBox.getChildren().add(errors);
            
    }
    
    public void createNewPlatform() {
        
        blankPlatform.setImage(blankPlatform.getName() + ".png");
        
        try {
            if (imageFile != null) {
                File newImage = new File(blankPlatform.getPath() + blankPlatform.getImage()); //any location
                Files.copy(imageFile.toPath(), newImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException ex) {
            System.exit(-1);
        }
        
        platforms.addPlatform(blankPlatform);
        platforms.getPlatformIDs().put(blankPlatform.getName(), blankPlatform.getID());
        platforms.getPlatformMap().put(blankPlatform.getName(), blankPlatform);
        writePlatforms(platforms);

        Font biggerTextFont = new CustomFont(fontsPath + "joystick.ttf", 15).getFont();    
        Text success = new Text(blankPlatform.getName() + " has been successfully added. ");
                            success.setWrappingWidth(475);
                            success.setFont(biggerTextFont);
                            success.setFill(Color.WHITE);
                            success.setTextAlignment(TextAlignment.CENTER);

                    rightVBox.getChildren().clear();
                    rightVBox.getChildren().add(success);    


        createLeftVBox();
    }
   
    public class ButtonHandler implements EventHandler<ActionEvent> { 
          @Override
        public void handle(ActionEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("Back")) platform();
            else if (sourceButton.equals("Click to\nAdd Image") || sourceButton.equals("Click to\nEdit image")) imageSelect();
            else if (sourceButton.equals("Enter")) checkInput();
            else if (sourceButton.equals("Confirm")) createNewPlatform();
            else if (sourceButton.equals("Clear") || sourceButton.equals("Cancel")) {
                createLeftVBox();
                createRightVBox();
            }
            
        }
    }
    
    public class HoverOn implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            
            selectPlayer.stop();
            selectPlayer.play();

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("Back")) backButton.setVisible(true);
            else if (sourceButton.equals("Enter") || sourceButton.equals("Confirm")) enter.setTextFill(Color.web("CEC062"));
            else if (sourceButton.equals("Clear") || sourceButton.equals("Cancel")) clear.setTextFill(Color.web("CEC062"));

           
        }
    }
    
    public class HoverOff implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            selectPlayer.play();
            selectPlayer.stop();

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("Back")) backButton.setVisible(false);
            else if (sourceButton.equals("Enter") || sourceButton.equals("Confirm")) enter.setTextFill(Color.WHITE);
            else if (sourceButton.equals("Clear") || sourceButton.equals("Cancel")) clear.setTextFill(Color.WHITE);
        }
    }
    
    public void platform() {
        
        this.setVisible(false);
        this.getScene().getWindow().hide();
        Stage primaryStage = new Stage(); 
        PlatformsMenu platformMenu = new PlatformsMenu(games, platforms); 
        Scene newScene = new Scene(platformMenu);
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();
         
    }
            
}
