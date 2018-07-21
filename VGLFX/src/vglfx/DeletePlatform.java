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
import vgl.Games;
import static vgl.IO.writePlatforms;
import vgl.Platform;
import vgl.Platforms;
import static vglfx.IO.searchPlatforms;
import static vglfx.IO.sortPlatforms;

public class DeletePlatform extends StackPane {
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
    
    private VBox deletePlatformVBox;
    
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
    private ImagePane selectedPlatformImage;
    
    private Stage hoverStage;
    
    private Button confirm; 
    private Button cancel;

    private Stage primaryStage;
    private Scene newScene;
    
    public DeletePlatform (Games games, Platforms platforms) { 
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
        createDeletePlatformVBox();
        this.getChildren().addAll(background, deletePlatformVBox);
    }
    
    public void createSounds() {
        Media sound = new Media(selectSound.toURI().toString());
        selectPlayer = new MediaPlayer(sound);
    }
    
    public void createBackground() {
        background = new ImagePane(imagesPath + "background.gif", 1200, 750);
    }
    
    public void createDeletePlatformVBox() {
        
        deletePlatformVBox = new VBox();
       
        Font headerFont = new CustomFont(fontsPath + "spaceinvaders.ttf", 140).getFont();
        Font headerFont2 = new CustomFont(fontsPath + "spaceinvaders.ttf", 135).getFont();
        Font headerFont3 = new CustomFont(fontsPath + "spaceinvaders.ttf", 130).getFont();
        
        
        
        Text headerText = new Text("DELETE PLATFORM");
            headerText.setFont(headerFont);
            headerText.setFill(Color.web("CEC062"));
            headerText.setTextAlignment(TextAlignment.CENTER);
            
        Text headerText2 = new Text("DELETE PLATFORM");
            headerText2.setFont(headerFont2);
            headerText2.setFill(Color.web("4E54E0"));
            headerText2.setTextAlignment(TextAlignment.CENTER);
            StackPane headerTextPane = new StackPane();
                headerTextPane.getChildren().add(headerText2);
                headerTextPane.setPadding(new Insets(0,0,25,0));
                headerTextPane.setAlignment(Pos.CENTER);
         
        Text headerText3 = new Text("DELETE PLATFORM");
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
          
        deletePlatformVBox.getChildren().addAll(headerPane, contentHBox, backHBox);
        deletePlatformVBox.setAlignment(Pos.TOP_CENTER);
        deletePlatformVBox.setSpacing(30);
        deletePlatformVBox.setPadding(new Insets(25,0,0,0));
    
    }
    
    public void createContentHBox() {
        
        leftContentVBox = new VBox();
            leftContentVBox.setAlignment(Pos.CENTER);
            leftContentVBox.setSpacing(10);
            leftContentVBox.setMinWidth(600);
            leftContentVBox.setMaxWidth(600);
        createLeftContentVBox();
            
        
        rightContentStackPane = new StackPane();
            rightContentStackPane.setAlignment(Pos.CENTER);
            //rightContentStackPane.setStyle("-fx-background-color: #000000;");
            
        createRightContentStackPane("Default");
        
        contentHBox.getChildren().addAll(leftContentVBox, rightContentStackPane);
            contentHBox.setAlignment(Pos.CENTER);
    }
    
    public void createLeftContentVBox() {
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 30).getFont();
        Font searchFont = new CustomFont(fontsPath + "joystick.ttf", 14).getFont();
        
        Text headerText = new Text("Master Platform List");
                headerText.setFont(textFont);
                headerText.setFill(Color.web("E67F7C"));
                    
        searchField = new TextField();
            searchField.setPrefSize(400, 20);
        
        Button searchButton = new Button("Search");
            searchButton.setFont(searchFont);
            searchButton.setOnAction(new DeletePlatform.ButtonHandler());
            searchButton.setOnMouseEntered(new DeletePlatform.HoverOn());
            searchButton.setOnMouseExited(new DeletePlatform.HoverOff());
            searchButton.setStyle("-fx-background-color: transparent;");
            searchButton.setTextFill(Color.WHITE);
            searchButton.setAlignment(Pos.CENTER_LEFT);
        
        Button clearButton = new Button("Clear");
            clearButton.setFont(searchFont);
            clearButton.setOnAction(new DeletePlatform.ButtonHandler());
            clearButton.setOnMouseEntered(new DeletePlatform.HoverOn());
            clearButton.setOnMouseExited(new DeletePlatform.HoverOff());
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
        rightContentStackPane.setAlignment(Pos.CENTER);
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 15).getFont();
        
        Text text = new Text();
            text.setFont(textFont);
            text.setFill(Color.WHITE);
            text.setWrappingWidth(400);
            text.setTextAlignment(TextAlignment.CENTER);
            
        if (version.equals("Default")) {
            text.setText("Select a platform to delete.");
            rightContentStackPane.getChildren().add(text);
        }
        
        else if (version.equals("Selected")) {
            
            selectedPlatformImage = new ImagePane(selectedPlatform.getPicture(), 300, 150);
                
            text.setText("Would you like to delete this platform?");
            
            confirm = new Button();
                confirm.setText("Confirm");
                confirm.setFont(textFont);
                confirm.setTextFill(Color.WHITE);
                confirm.setStyle("-fx-background-color: transparent;");
                confirm.setOnAction(new DeletePlatform.ButtonHandler());
                confirm.setOnMouseEntered(new DeletePlatform.HoverOn());
                confirm.setOnMouseExited(new DeletePlatform.HoverOff());
            
            cancel = new Button();
                cancel.setText("Cancel");
                cancel.setFont(textFont);
                cancel.setTextFill(Color.WHITE);
                cancel.setStyle("-fx-background-color: transparent;");
                cancel.setOnAction(new DeletePlatform.ButtonHandler());
                cancel.setOnMouseEntered(new DeletePlatform.HoverOn());
                cancel.setOnMouseExited(new DeletePlatform.HoverOff());

            HBox optionButtons = new HBox();
                optionButtons.setSpacing(5);
                optionButtons.setAlignment(Pos.CENTER);
                optionButtons.getChildren().addAll(confirm, cancel);
            
            VBox confirmVBox = new VBox();
                confirmVBox.setAlignment(Pos.CENTER);
                confirmVBox.setSpacing(10);
                confirmVBox.getChildren().addAll(selectedPlatformImage, text, optionButtons);
                
            rightContentStackPane.getChildren().add(confirmVBox);
        }
        
        else if (version.equals("Finished")) {
            text.setText(selectedPlatform.getName() + " has been successfully deleted.");
            rightContentStackPane.getChildren().add(text);
        }
        
        
       
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
                    platformButton.setOnAction(new DeletePlatform.ButtonHandler());
                    platformButton.setOnMouseEntered(new DeletePlatform.HoverOn());
                    platformButton.setOnMouseExited(new DeletePlatform.HoverOff());

                contentScrollPaneVBox.getChildren().add(platformButton);
            }

                if (n > 10) wrappingWidth = 600;
                else wrappingWidth = 584;
                
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
            back.setOnAction(new DeletePlatform.ButtonHandler());
            back.setOnMouseEntered(new DeletePlatform.HoverOn());
            back.setOnMouseExited(new DeletePlatform.HoverOff());
        
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
                            platformButton.setOnAction(new DeletePlatform.ButtonHandler());
                            platformButton.setOnMouseEntered(new DeletePlatform.HoverOn());
                            platformButton.setOnMouseExited(new DeletePlatform.HoverOff());

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

                if (n > 10) wrappingWidth = 600;
                else wrappingWidth = 584;
                
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
    
    public void confirmDelete() {
        
        int n = platforms.getNumPlatforms();
        
            for (int i = 0; i < n; i++) {
                if(selectedPlatform.getName().equals(platforms.getPlatform(i).getName())) {
                    platforms.deletePlatform(i);
                    break;
                }
            }
        
        File deleteFile = new File(selectedPlatform.getPicture()); //creates link to pictue of game
        deleteFile.delete(); //deletes picture for space purposes
        
        writePlatforms(platforms);
        
        platforms.getPlatformMap().remove(selectedPlatform.getName());
        platforms.getPlatformIDs().remove(selectedPlatform.getName());
         
        createRightContentStackPane("Finished");
        searchField.clear();
        createContentScrollPane();
    }
    
    public class ButtonHandler implements EventHandler<ActionEvent> { 
          @Override
        public void handle(ActionEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("Back")) platform();
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
                selectedPlatform = platforms.getPlatformMap().get(sourceButton);
                createRightContentStackPane("Selected");
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

