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
import javafx.util.Duration;
import vgl.Games;
import vgl.Platform;
import vgl.Platforms;
import static vglfx.IO.readGenerations;
import static vglfx.IO.readNames;
import static vglfx.IO.readTypes;
import static vglfx.IO.searchPlatforms;
import static vglfx.IO.sortPlatforms;
import static vglfx.IO.specificGeneration;
import static vglfx.IO.specificName;
import static vglfx.IO.specificType;

public class PlatformsMenu extends StackPane {

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
    
    private VBox platformsMenuVBox;
    private HBox contentHBox;
    
    private GridPane leftPane;
    private StackPane rightPane;
    
    private Stage primaryStage;
    private Scene newScene;
    
    private Button viewMaster;
    private Button viewCategories;
    private Button add;
    private Button delete;
    private Button edit;
    private Button back;
    
    private ImagePane viewMasterPane;
    private ImagePane viewCategoriesPane;
    private ImagePane addPane;
    private ImagePane deletePane;
    private ImagePane editPane;
    private ImagePane backPane;
    
    private Platform randomPlatform;
    private ButtonImagePane randomPlatformImage;
    
    private TextField searchField;
    private HBox searchHBox;
    private VBox searchVBox;
    
    private ScrollPane scrollPane;
    private VBox scrollPaneVBox;
    
    private ComboBox namesComboBox;
    private ComboBox typesComboBox;
    private ComboBox generationsComboBox;
    
    private ScrollPane masterScrollPane;
    private VBox scrollPaneRoot;

    public PlatformsMenu(Games games, Platforms platforms) {
        this.games = games;
        this.platforms = platforms;
        imagesPath = "resources/images/platformsmenu/";
        fontsPath = "resources/fonts/";
        musicFile = new File("resources/sounds/spaceinvaders.mp3");
        backgroundMusic = new Media(musicFile.toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        selectSound = new File("resources/sounds/laser.aiff");
        createPlatformsMenu();
    }

    private void createPlatformsMenu() {
        createBackground();
        createPlatformsMenuVBox();
        createSounds();
        this.getChildren().addAll(background, platformsMenuVBox);
        //PlatformsMenu.setAlignment(bar, Pos.TOP_CENTER);
    }

    private void createBackground() {
        background = new ImagePane(imagesPath + "background.gif", 1200, 750);
        
    }
    
    private void createSounds() {
        backgroundMediaPlayer.setOnEndOfMedia(() -> {
        backgroundMediaPlayer.seek(Duration.ZERO);
        });
        backgroundMediaPlayer.play();
        
        Media sound = new Media(selectSound.toURI().toString());
        selectPlayer = new MediaPlayer(sound);
    }
    
    private void createPlatformsMenuVBox() {
        platformsMenuVBox = new VBox();
       
        Font headerFont = new CustomFont(fontsPath + "spaceinvaders.ttf", 140).getFont();
        Font headerFont2 = new CustomFont(fontsPath + "spaceinvaders.ttf", 135).getFont();
        Font headerFont3 = new CustomFont(fontsPath + "spaceinvaders.ttf", 130).getFont();
        
        
        
        Text headerText = new Text("PLATFORM MENU");
            headerText.setFont(headerFont);
            headerText.setFill(Color.web("CEC062"));
            headerText.setTextAlignment(TextAlignment.CENTER);
            
        Text headerText2 = new Text("PLATFORM MENU");
            headerText2.setFont(headerFont2);
            headerText2.setFill(Color.web("4E54E0"));
            headerText2.setTextAlignment(TextAlignment.CENTER);
            StackPane headerTextPane = new StackPane();
                headerTextPane.getChildren().add(headerText2);
                headerTextPane.setPadding(new Insets(0,0,25,0));
                headerTextPane.setAlignment(Pos.CENTER);
         
        Text headerText3 = new Text("PLATFORM MENU");
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
          
        platformsMenuVBox.getChildren().addAll(headerPane, contentHBox);
        platformsMenuVBox.setAlignment(Pos.TOP_CENTER);
        platformsMenuVBox.setSpacing(20);
        platformsMenuVBox.setPadding(new Insets(25,0,0,0));
    
    }
    
    private void createContentHBox() {
        leftPane = new GridPane();
            //leftPane.setStyle("-fx-background-color: #FF0000;");
            leftPane.setMinSize(450, 450);
            leftPane.setMaxSize(450, 450);
            leftPane.setStyle("-fx-background-color: transparent;");
        createLeftPane();
        
        rightPane = new StackPane();
            //rightPane.setStyle("-fx-background-color: #0000FF;"); 
            rightPane.setMaxSize(650, 450);
            rightPane.setMinSize(650, 450);
            rightPane.setStyle("-fx-background-color: transparent;");
        randomGamePane();
        
        contentHBox.getChildren().addAll(leftPane, rightPane); 
            contentHBox.setAlignment(Pos.CENTER);
            contentHBox.setSpacing(20); 
            contentHBox.setPadding(new Insets(10,0,10,0)); 
            contentHBox.setStyle("-fx-background-color: transparent;"); 
            contentHBox.setMaxWidth(1100);
    }
    
    private void generateRandomPlatform() {
        
        if (platforms.getNumPlatforms() > 0) {
            Random rand = new Random();
            int n = rand.nextInt(platforms.getNumPlatforms());
            randomPlatform = platforms.getPlatform(n);
        }
        
        else randomPlatform = new Platform();
        
        randomPlatformImage = new ButtonImagePane(randomPlatform.getPicture(), 300, 150);
        randomPlatformImage.getButton().setText("Random Platform");
        randomPlatformImage.getButton().setOnAction(new PlatformsMenu.ButtonHandler());
    }
    
    private void randomGamePane() {
        
        ImagePane gamesMiniBackground = new ImagePane(imagesPath + "minibackground.gif", 650, 450);
        
        generateRandomPlatform();
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 25).getFont();
        Font smallTextFont = new CustomFont(fontsPath + "joystick.ttf", 20).getFont();
        Font evenSmallerTextFont = new CustomFont(fontsPath + "joystick.ttf", 15).getFont();
        
        Text rgdText = new Text("Random Platform of the Day:");
                    rgdText.setFont(textFont);
                    rgdText.setFill(Color.web("E67F7C"));
                    rgdText.setTextAlignment(TextAlignment.CENTER);
        Text rgText = new Text(randomPlatform.getName());
                    rgText.setFont(smallTextFont);
                    rgText.setFill(Color.web("FFFFFF"));
        Text clickText = new Text("Click on the picture for game information!");
                    clickText.setFont(evenSmallerTextFont);
                    clickText.setFill(Color.web("E67F7C"));
            
        VBox rightVBox = new VBox();
            rightVBox.getChildren().addAll(rgdText, rgText, randomPlatformImage, clickText);
            rightVBox.setAlignment(Pos.CENTER);
            rightVBox.setSpacing(20);
        
        
        rightPane.getChildren().addAll(gamesMiniBackground, rightVBox);
            rightPane.setAlignment(Pos.CENTER);
    }
    
    private void createLeftPane() {
        
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 25).getFont();
        
        viewMaster = new Button("VIEW MASTER LIST");
            viewMaster.setFont(textFont);
            viewMaster.setTextAlignment(TextAlignment.CENTER);
            viewMaster.setOnAction(new PlatformsMenu.ButtonHandler());
            viewMaster.setOnMouseEntered(new PlatformsMenu.HoverOn());
            viewMaster.setOnMouseExited(new PlatformsMenu.HoverOff());
            viewMaster.setStyle("-fx-background-color: transparent;");
            viewMaster.setTextFill(Color.WHITE);
            viewMaster.setAlignment(Pos.CENTER);
         
        viewCategories = new Button("VIEW CATEGORIES");
            viewCategories.setFont(textFont);
            viewCategories.setTextAlignment(TextAlignment.CENTER);
            viewCategories.setOnAction(new PlatformsMenu.ButtonHandler());
            viewCategories.setOnMouseEntered(new PlatformsMenu.HoverOn());
            viewCategories.setOnMouseExited(new PlatformsMenu.HoverOff());
            viewCategories.setStyle("-fx-background-color: transparent;");
            viewCategories.setTextFill(Color.WHITE);
            viewCategories.setAlignment(Pos.CENTER);
            
        add = new Button("ADD PLATFORM");
            add.setFont(textFont);
            add.setTextAlignment(TextAlignment.CENTER);
            add.setOnAction(new PlatformsMenu.ButtonHandler());
            add.setOnMouseEntered(new PlatformsMenu.HoverOn());
            add.setOnMouseExited(new PlatformsMenu.HoverOff());
            add.setStyle("-fx-background-color: transparent;");
            add.setTextFill(Color.WHITE);
            add.setAlignment(Pos.CENTER);
            
        delete = new Button("DELETE PLATFORM");
            delete.setFont(textFont);
            delete.setTextAlignment(TextAlignment.CENTER);
            delete.setOnAction(new PlatformsMenu.ButtonHandler());
            delete.setOnMouseEntered(new PlatformsMenu.HoverOn());
            delete.setOnMouseExited(new PlatformsMenu.HoverOff());
            delete.setStyle("-fx-background-color: transparent;");
            delete.setTextFill(Color.WHITE);
            delete.setAlignment(Pos.CENTER);
            
        edit = new Button("EDIT PLATFORM");
            edit.setFont(textFont);
            edit.setTextAlignment(TextAlignment.CENTER);
            edit.setOnAction(new PlatformsMenu.ButtonHandler());
            edit.setOnMouseEntered(new PlatformsMenu.HoverOn());
            edit.setOnMouseExited(new PlatformsMenu.HoverOff());
            edit.setStyle("-fx-background-color: transparent;");
            edit.setTextFill(Color.WHITE);
            edit.setAlignment(Pos.CENTER);
            
        back = new Button("BACK");
            back.setFont(textFont);
            back.setTextAlignment(TextAlignment.CENTER);
            back.setOnAction(new PlatformsMenu.ButtonHandler());
            back.setOnMouseEntered(new PlatformsMenu.HoverOn());
            back.setOnMouseExited(new PlatformsMenu.HoverOff());
            back.setStyle("-fx-background-color: transparent;");
            back.setTextFill(Color.WHITE);
            back.setAlignment(Pos.CENTER);
            
        viewMasterPane = new ImagePane(imagesPath + "icon.png", 50, 40);
        viewCategoriesPane = new ImagePane(imagesPath + "icon.png", 50, 40);
        addPane = new ImagePane(imagesPath + "icon.png", 50, 40);
        deletePane = new ImagePane(imagesPath + "icon.png", 50, 40);
        editPane = new ImagePane(imagesPath + "icon.png", 50, 40);
        backPane = new ImagePane(imagesPath + "icon.png", 50, 40); 
        
        viewMasterPane.setVisible(false);
        viewCategoriesPane.setVisible(false);
        addPane.setVisible(false);
        deletePane.setVisible(false);
        editPane.setVisible(false);
        backPane.setVisible(false);
        
        leftPane.add(viewMasterPane, 0, 0);
        leftPane.add(viewCategoriesPane, 0, 1);
        leftPane.add(addPane, 0, 2);
        leftPane.add(deletePane, 0, 3);
        leftPane.add(editPane, 0, 4);
        leftPane.add(backPane, 0, 5);
            
        leftPane.add(viewMaster, 1, 0);
        leftPane.add(viewCategories, 1, 1);
        leftPane.add(add, 1, 2);
        leftPane.add(delete, 1, 3);
        leftPane.add(edit, 1, 4);
        leftPane.add(back, 1, 5);
        
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setVgap(15);
        
    }
    
    
    
    public void viewMasterList() {
        
        rightPane.getChildren().clear();
        
        Font headerFont = new CustomFont(fontsPath + "joystick.ttf", 30).getFont();
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 15).getFont();
        Font listFont = new CustomFont(fontsPath + "joystick.ttf", 20).getFont();
        
        Text viewAllHeaderText = new Text("Master Platform List");
            viewAllHeaderText.setFont(headerFont);
            viewAllHeaderText.setFill(Color.web("E67F7C"));
            
        Text noPlatforms = new Text("No platforms in catalog.");
            noPlatforms.setFont(textFont);
            noPlatforms.setFill(Color.WHITE);
            
        scrollPaneRoot = new VBox();
            scrollPaneRoot.setMinWidth(650);
            scrollPaneRoot.setMaxWidth(650);
            scrollPaneRoot.setStyle("-fx-background-color: #000000;");
            
        masterScrollPane = new ScrollPane();
            masterScrollPane.setMinViewportWidth(650);
        
        Platforms sortedPlatforms = sortPlatforms(platforms);
        int n = sortedPlatforms.getNumPlatforms();
        
        searchField = new TextField();
            searchField.setPrefSize(400, 20);
        
        Button searchButton = new Button("Search");
            searchButton.setFont(listFont);
            searchButton.setOnAction(new PlatformsMenu.ButtonHandler());
            searchButton.setOnMouseEntered(new PlatformsMenu.HoverOn());
            searchButton.setOnMouseExited(new PlatformsMenu.HoverOff());
            searchButton.setStyle("-fx-background-color: transparent;");
            searchButton.setTextFill(Color.WHITE);
            searchButton.setAlignment(Pos.CENTER_LEFT);
        
        Button clearButton = new Button("Clear");
            clearButton.setFont(listFont);
            clearButton.setOnAction(new PlatformsMenu.ButtonHandler());
            clearButton.setOnMouseEntered(new PlatformsMenu.HoverOn());
            clearButton.setOnMouseExited(new PlatformsMenu.HoverOff());
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
        
        for (int i = 0; i < n; i++) {
            Button button = new Button();
                button.setFont(listFont);
                button.setTextAlignment(TextAlignment.LEFT);
                button.setTextFill(Color.WHITE);
                button.setText(sortedPlatforms.getPlatform(i).getName());
                button.setOnAction(new PlatformsMenu.ButtonHandler());
                button.setOnMouseEntered(new PlatformsMenu.HoverOn());
                button.setOnMouseExited(new PlatformsMenu.HoverOff());
                button.setStyle("-fx-background-color: transparent;");
                
                
            scrollPaneRoot.getChildren().add(button);
        }
        
        if (n < 9) masterScrollPane.setMinViewportWidth(634);
        else  masterScrollPane.setMinViewportWidth(650);
        
        StackPane pane = new StackPane();
            pane.getChildren().add(noPlatforms);
            pane.setAlignment(Pos.CENTER);
            pane.setStyle("-fx-background-color: #000000;");

        masterScrollPane.setContent(scrollPaneRoot);  
        masterScrollPane.setStyle("-fx-background-color: #000000;");
        
        VBox viewAllVBox = new VBox();
            viewAllVBox.getChildren().clear();
            if (n > 0) viewAllVBox.getChildren().addAll(viewAllHeaderText, searchHBox, masterScrollPane);
            else viewAllVBox.getChildren().addAll(viewAllHeaderText, pane);
            viewAllVBox.setAlignment(Pos.TOP_CENTER);
            viewAllVBox.setSpacing(10);
            
        rightPane.getChildren().add(viewAllVBox);
    }
    
    public void search() {

        scrollPaneRoot.getChildren().clear();
        
        Font listFont = new CustomFont(fontsPath + "joystick.ttf", 20).getFont();

        if (!searchField.getText().trim().isEmpty()) {
            
            Platforms searchResults = searchPlatforms(platforms, searchField.getText().toLowerCase().replaceAll("\\s+",""));
            int n = searchResults.getNumPlatforms();
            
            if (n > 0) {
                Button platformButton;
                String platformText;


                for (int i = 0; i < n; i++) {
                    platformText = searchResults.getPlatform(i).getName(); 

                        platformButton = new Button(platformText);
                            platformButton.setFont(listFont);
                            platformButton.setTextFill(Color.WHITE);
                            platformButton.setAlignment(Pos.CENTER_LEFT);
                            platformButton.setStyle("-fx-background-color: transparent;");
                            platformButton.setOnAction(new PlatformsMenu.ButtonHandler());
                            platformButton.setOnMouseEntered(new PlatformsMenu.HoverOn());
                            platformButton.setOnMouseExited(new PlatformsMenu.HoverOff());

                        scrollPaneRoot.getChildren().add(platformButton);
                    }
            }
            
            else {
                
                Text noResults = new Text("No results found.");
                    noResults.setFont(listFont);
                    noResults.setFill(Color.WHITE);
                    noResults.setTextAlignment(TextAlignment.CENTER);
                    
                StackPane empty = new StackPane();
                    empty.setMinSize(100, 100);
                    empty.setAlignment(Pos.CENTER);
                    empty.setStyle("-fx-background-color: #000000;");
                    empty.getChildren().add(noResults);
                    
                scrollPaneRoot.getChildren().add(empty);    
            }

            if (n < 9) masterScrollPane.setMinViewportWidth(634);
            else  masterScrollPane.setMinViewportWidth(650);
        }
        
        else viewMasterList();
    }
    
    public void viewCategories() {
        
        ImagePane gamesMiniBackground = new ImagePane(imagesPath + "minibackground.gif", 650, 450);
        
        rightPane.getChildren().clear();
        scrollPaneVBox = new VBox();
            scrollPaneVBox.setMinWidth(650);
            scrollPaneVBox.setMaxWidth(650);
            scrollPaneVBox.setStyle("-fx-background-color: #000000;");
            
        scrollPane = new ScrollPane();
            scrollPane.setStyle("-fx-background-color: transparent;");
            scrollPane.setContent(scrollPaneVBox);
            
        Font headerFont = new CustomFont(fontsPath + "joystick.ttf", 30).getFont();
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 25).getFont();
        
        Text viewCatHeaderText = new Text("Select a Category");
            viewCatHeaderText.setFont(headerFont);
            viewCatHeaderText.setFill(Color.web("E67F7C"));
            viewCatHeaderText.setTextAlignment(TextAlignment.CENTER);
            
        GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
           

            Button name = new Button("Name");
                name.setFont(textFont);
                name.setTextFill(Color.WHITE);
                name.setTextAlignment(TextAlignment.CENTER);
                name.setStyle("-fx-background-color: transparent;");
                name.setOnAction(new PlatformsMenu.ButtonHandler());
                name.setOnMouseEntered(new PlatformsMenu.HoverOn());
                name.setOnMouseExited(new PlatformsMenu.HoverOff());
              
            gridPane.add(name, 0, 0); 
            gridPane.setHalignment(name, HPos.CENTER);
            
            Button type = new Button("Type");
                type.setFont(textFont);
                type.setTextFill(Color.WHITE);
                type.setTextAlignment(TextAlignment.CENTER);
                type.setStyle("-fx-background-color: transparent;");
                type.setOnAction(new PlatformsMenu.ButtonHandler());
                type.setOnMouseEntered(new PlatformsMenu.HoverOn());
                type.setOnMouseExited(new PlatformsMenu.HoverOff());
            gridPane.add(type, 0, 1);
            gridPane.setHalignment(type, HPos.CENTER);
            
            Button generation = new Button("Generation");
                generation.setFont(textFont);
                generation.setTextFill(Color.WHITE);
                generation.setTextAlignment(TextAlignment.CENTER);
                generation.setStyle("-fx-background-color: transparent;");
                generation.setOnAction(new PlatformsMenu.ButtonHandler());
                generation.setOnMouseEntered(new PlatformsMenu.HoverOn());
                generation.setOnMouseExited(new PlatformsMenu.HoverOff());
                
            gridPane.add(generation, 0, 2);
            gridPane.setHalignment(generation, HPos.CENTER);

            gridPane.setVgap(30);
            //gridPane.setGridLinesVisible(true);
        
        VBox viewCatVBox = new VBox();
            viewCatVBox.setStyle("-fx-background-color: #66000000;");
            viewCatVBox.getChildren().addAll(viewCatHeaderText, gridPane);
            viewCatVBox.setAlignment(Pos.CENTER);
            viewCatVBox.setSpacing(30);
            
        rightPane.getChildren().addAll(gamesMiniBackground, viewCatVBox);
    }
    
    public void viewByNames() {
        
        rightPane.getChildren().clear();
        scrollPaneVBox.getChildren().clear();
        scrollPane.setMinViewportWidth(634);
        
        Font headerFont = new CustomFont(fontsPath + "joystick.ttf", 30).getFont();
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 15).getFont();
        
        Text nameHeaderText = new Text("Platform Names");
            nameHeaderText.setFont(headerFont);
            nameHeaderText.setFill(Color.web("E67F7C"));
        
        Text choose = new Text("Choose a letter or character");
            choose.setFont(textFont);
            choose.setTextAlignment(TextAlignment.CENTER);
            choose.setFill(Color.WHITE);
            
        StackPane choosePane = new StackPane();
            choosePane.getChildren().add(choose);
            choosePane.setMinSize(650, 200);
            choosePane.setMaxSize(650, 200);
            choosePane.setAlignment(Pos.CENTER);
        
        scrollPaneVBox.getChildren().add(choosePane);
        scrollPane.setMinViewportWidth(634);

        ObservableList<String> names = readNames(platforms);
            namesComboBox = new ComboBox(names);
            namesComboBox.setStyle("-fx-background-color: #FFFF33; -fx-font: 15px \"textFont\";");
            namesComboBox.setOnAction(new PlatformsMenu.NameComboBoxHandler());
            
        VBox byNameVBox = new VBox();
            byNameVBox.getChildren().addAll(nameHeaderText, namesComboBox, scrollPane);
            byNameVBox.setAlignment(Pos.TOP_CENTER);
            byNameVBox.setSpacing(20);
            
        rightPane.getChildren().add(byNameVBox);
        
    }
    
    public class NameComboBoxHandler implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {
            
            scrollPaneVBox.getChildren().clear();

            String selection = (String) namesComboBox.getValue();

            Platforms specificNameList = specificName(platforms, selection);
            int n = specificNameList.getNumPlatforms();
            
            Font listFont = new CustomFont(fontsPath + "joystick.ttf", 20).getFont();
            
            for (int i = 0; i < n; i++) {
                Button button = new Button();
                    button.setFont(listFont);
                    button.setTextAlignment(TextAlignment.LEFT);
                    button.setTextFill(Color.WHITE);
                    button.setText(specificNameList.getPlatform(i).getName());
                    button.setOnAction(new PlatformsMenu.ButtonHandler());
                    button.setOnMouseEntered(new PlatformsMenu.HoverOn());
                    button.setOnMouseExited(new PlatformsMenu.HoverOff());
                    button.setStyle("-fx-background-color: transparent;");
                    
                scrollPaneVBox.getChildren().add(button);
            }
            
            if (n < 8) scrollPane.setMinViewportWidth(634);
            else scrollPane.setMinViewportWidth(650);
        }
    }
    
    public void viewByType() {
        
        rightPane.getChildren().clear();
        scrollPaneVBox.getChildren().clear();
        scrollPane.setMinViewportWidth(634);
        
        Font headerFont = new CustomFont(fontsPath + "joystick.ttf", 30).getFont();
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 15).getFont();
        
        Text nameHeaderText = new Text("Platform Types");
            nameHeaderText.setFont(headerFont);
            nameHeaderText.setFill(Color.web("E67F7C"));
        
        Text choose = new Text("Choose a platform type");
            choose.setFont(textFont);
            choose.setTextAlignment(TextAlignment.CENTER);
            choose.setFill(Color.WHITE);
            
        StackPane choosePane = new StackPane();
            choosePane.getChildren().add(choose);
            choosePane.setMinSize(650, 200);
            choosePane.setMaxSize(650, 200);
            choosePane.setAlignment(Pos.CENTER);
        
        scrollPaneVBox.getChildren().add(choosePane);
        scrollPane.setMinViewportWidth(634);

        ObservableList<String> types = readTypes(platforms);
            typesComboBox = new ComboBox(types);
            typesComboBox.setStyle("-fx-background-color: #FFFF33; -fx-font: 15px \"textFont\";");
            typesComboBox.setOnAction(new PlatformsMenu.TypeComboBoxHandler());
            
        VBox byTypesVBox = new VBox();
            byTypesVBox.getChildren().addAll(nameHeaderText, typesComboBox, scrollPane);
            byTypesVBox.setAlignment(Pos.TOP_CENTER);
            byTypesVBox.setSpacing(20);
            
        rightPane.getChildren().add(byTypesVBox);
        
    }
    
    public class TypeComboBoxHandler implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {
            
            scrollPaneVBox.getChildren().clear();

            String selection = (String) typesComboBox.getValue();

            Platforms specificTypeList = specificType(platforms, selection);
            int n = specificTypeList.getNumPlatforms();
            
            Font listFont = new CustomFont(fontsPath + "joystick.ttf", 20).getFont();
            
            for (int i = 0; i < n; i++) {
                Button button = new Button();
                    button.setFont(listFont);
                    button.setTextAlignment(TextAlignment.LEFT);
                    button.setTextFill(Color.WHITE);
                    button.setText(specificTypeList.getPlatform(i).getName());
                    button.setOnAction(new PlatformsMenu.ButtonHandler());
                    button.setOnMouseEntered(new PlatformsMenu.HoverOn());
                    button.setOnMouseExited(new PlatformsMenu.HoverOff());
                    button.setStyle("-fx-background-color: transparent;");
                    
                scrollPaneVBox.getChildren().add(button);
            }
            
            if (n < 8) scrollPane.setMinViewportWidth(634);
            else scrollPane.setMinViewportWidth(650);
        }
    }
    
    public void viewByGeneration() {
        
        rightPane.getChildren().clear();
        scrollPaneVBox.getChildren().clear();
        scrollPane.setMinViewportWidth(634);
        
        Font headerFont = new CustomFont(fontsPath + "joystick.ttf", 30).getFont();
        Font textFont = new CustomFont(fontsPath + "joystick.ttf", 15).getFont();
        
        Text nameHeaderText = new Text("Platform Generations");
            nameHeaderText.setFont(headerFont);
            nameHeaderText.setFill(Color.web("E67F7C"));
        
        Text choose = new Text("Choose a generation");
            choose.setFont(textFont);
            choose.setTextAlignment(TextAlignment.CENTER);
            choose.setFill(Color.WHITE);
            
        StackPane choosePane = new StackPane();
            choosePane.getChildren().add(choose);
            choosePane.setMinSize(650, 200);
            choosePane.setMaxSize(650, 200);
            choosePane.setAlignment(Pos.CENTER);
        
        scrollPaneVBox.getChildren().add(choosePane);
        scrollPane.setMinViewportWidth(634);

        ObservableList<String> generations = readGenerations(platforms);
            generationsComboBox = new ComboBox(generations);
            generationsComboBox.setStyle("-fx-background-color: #FFFF33; -fx-font: 15px \"textFont\";");
            generationsComboBox.setOnAction(new PlatformsMenu.GenerationComboBoxHandler());
            
        VBox byGenerationVBox = new VBox();
            byGenerationVBox.getChildren().addAll(nameHeaderText, generationsComboBox, scrollPane);
            byGenerationVBox.setAlignment(Pos.TOP_CENTER);
            byGenerationVBox.setSpacing(20);
            
        rightPane.getChildren().add(byGenerationVBox);
        
    }
    
    public class GenerationComboBoxHandler implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {
            
            scrollPaneVBox.getChildren().clear();

            String selection = (String) generationsComboBox.getValue();

            Platforms specificTypeList = specificGeneration(platforms, selection);
            int n = specificTypeList.getNumPlatforms();
            
            Font listFont = new CustomFont(fontsPath + "joystick.ttf", 20).getFont();
            
            for (int i = 0; i < n; i++) {
                Button button = new Button();
                    button.setFont(listFont);
                    button.setTextAlignment(TextAlignment.LEFT);
                    button.setTextFill(Color.WHITE);
                    button.setText(specificTypeList.getPlatform(i).getName());
                    button.setOnAction(new PlatformsMenu.ButtonHandler());
                    button.setOnMouseEntered(new PlatformsMenu.HoverOn());
                    button.setOnMouseExited(new PlatformsMenu.HoverOff());
                    button.setStyle("-fx-background-color: transparent;");
                    
                scrollPaneVBox.getChildren().add(button);
            }
            
            if (n < 8) scrollPane.setMinViewportWidth(634);
            else scrollPane.setMinViewportWidth(650);
        }
    }
    
    
    
    private class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("BACK")) mainMenu();
            else if (sourceButton.equals("Random Platform")) randomPlatform();
            else if (sourceButton.equals("VIEW MASTER LIST")) viewMasterList();
            else if (sourceButton.equals("VIEW CATEGORIES")) viewCategories();
            else if (sourceButton.equals("ADD PLATFORM")) addPlatform(); 
            else if (sourceButton.equals("DELETE PLATFORM")) deletePlatform();
            else if (sourceButton.equals("EDIT PLATFORM")) editPlatform();
            else if (sourceButton.equals("Name")) viewByNames();  
            else if (sourceButton.equals("Type")) viewByType();  
            else if (sourceButton.equals("Generation")) viewByGeneration();
            else if (sourceButton.equals("Search")) search();
            else if (sourceButton.equals("randomButton") || sourceButton.equals("selectedButton")) {
                newScene.getWindow().hide();
                primaryStage.getScene().getWindow().hide();
            }
            else if (sourceButton.equals("Clear")) {
                searchField.clear();
                viewMasterList();
            }
            else {
                platforms.setSelectedPlatform(platforms.getPlatformMap().get(sourceButton));
                selectedPlatform();
            }
        }
    }
    
    private class HoverOn implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            selectPlayer.stop();
            selectPlayer.play();
            
            if (sourceButton.equals("BACK")) backPane.setVisible(true);
            else if (sourceButton.equals("VIEW MASTER LIST")) viewMasterPane.setVisible(true);
            else if (sourceButton.equals("VIEW CATEGORIES")) viewCategoriesPane.setVisible(true);
            else if (sourceButton.equals("ADD PLATFORM")) addPane.setVisible(true);
            else if (sourceButton.equals("EDIT PLATFORM")) editPane.setVisible(true);
            else if (sourceButton.equals("DELETE PLATFORM")) deletePane.setVisible(true);
            else button.setTextFill(Color.web("CEC062"));
            
        }
    }
    
    private class HoverOff implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
            
            selectPlayer.play();
            selectPlayer.stop();

            if (sourceButton.equals("BACK")) backPane.setVisible(false);
            else if (sourceButton.equals("VIEW MASTER LIST")) viewMasterPane.setVisible(false);
            else if (sourceButton.equals("VIEW CATEGORIES")) viewCategoriesPane.setVisible(false);
            else if (sourceButton.equals("ADD PLATFORM")) addPane.setVisible(false);
            else if (sourceButton.equals("EDIT PLATFORM")) editPane.setVisible(false);
            else if (sourceButton.equals("DELETE PLATFORM")) deletePane.setVisible(false);
            else if (sourceButton.equals("randomButton") || sourceButton.equals("selectedButton")) {
                newScene.getWindow().hide();
                primaryStage.getScene().getWindow().hide();
            }
            else button.setTextFill(Color.WHITE);
            
        }
    }
    
    
    
    private void mainMenu() {
        
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
    
    private void addPlatform() {
        
         backgroundMediaPlayer.stop();
        
        this.setVisible(false);
        this.getScene().getWindow().hide();

        primaryStage = new Stage();
        AddPlatform ap = new AddPlatform(games, platforms);
        newScene = new Scene(ap);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();

    }
    
    private void deletePlatform() {
        
         backgroundMediaPlayer.stop();
        
        this.setVisible(false);
        this.getScene().getWindow().hide();

        primaryStage = new Stage();
        DeletePlatform dp = new DeletePlatform(games, platforms);
        newScene = new Scene(dp);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();

    }
    
    private void editPlatform() {
        
         backgroundMediaPlayer.stop();
        
        this.setVisible(false);
        this.getScene().getWindow().hide();

        primaryStage = new Stage();
        EditPlatform ep = new EditPlatform(games, platforms);
        newScene = new Scene(ep);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Video Game Library");
        primaryStage.setScene(newScene);
        primaryStage.show();

    }
    
    public void randomPlatform() {

        StackPane randomPane = new StackPane();
        
        Button randomButton = new Button("randomButton");
            randomButton.setPrefSize(775,350);
            randomButton.setStyle("-fx-background-color: #66000000;");
            randomButton.setTextFill(Color.TRANSPARENT);
            randomButton.setOnMouseExited(new PlatformsMenu.HoverOff());
            randomButton.setOnAction(new PlatformsMenu.ButtonHandler());
        
        PlatformPane platformPane = new PlatformPane(randomPlatform, 325, 175, 15, 175, 300);
            platformPane.setStyle("-fx-background-color: #4E54E0;");
            randomPane.getChildren().addAll(platformPane, randomButton);
      
        primaryStage = new Stage();
        newScene = new Scene(randomPane);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
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
    
    public void selectedPlatform() {
                
        StackPane randomPane = new StackPane();
        
        Button selectedButton = new Button("selectedButton");
            selectedButton.setPrefSize(775,350);
            selectedButton.setStyle("-fx-background-color: #66000000;");
            selectedButton.setTextFill(Color.TRANSPARENT);
            selectedButton.setOnMouseExited(new PlatformsMenu.HoverOff());
            selectedButton.setOnAction(new PlatformsMenu.ButtonHandler());
        
        PlatformPane platformPane = new PlatformPane(platforms.getSelectedPlatform(), 325, 175, 15, 175, 200);
            platformPane.setStyle("-fx-background-color: #54AD4A;");
            randomPane.getChildren().addAll(platformPane, selectedButton);
            
        primaryStage = new Stage();
        newScene = new Scene(randomPane);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
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
     
}
