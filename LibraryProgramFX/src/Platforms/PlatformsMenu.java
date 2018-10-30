package Platforms;

import MainMenu.MainMenu;
import Necessities.BaseWindow;
import Necessities.FontGenerator;
import static Necessities.IO.newPlatformNumber;
import static Necessities.IO.readGenerations;
import Necessities.ImagePane;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import static libraryprogram.LibraryIO.writeGames;
import static libraryprogram.LibraryIO.writePlatforms;
import libraryprogram.Platform;

public class PlatformsMenu extends BaseWindow {
    
    private final Font titleFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 35).getFont();
    
    private VBox platformsMenuVBox;
    private StackPane platformCount;
    private HBox gameCount;
    private HBox contentHBox;
    private StackPane titleStackPane;
    
    private ImagePane addPlatform;
    private GridPane addPlatform2;
    private StackPane addPlatformStackPane;
    private ImagePane back;
    private GridPane back2;
    private StackPane backStackPane;
    
    private ImagePane deletePlatform;
    private GridPane deletePlatform2;
    private StackPane deletePlatformStackPane;
    private ImagePane editPlatform;
    private GridPane editPlatform2;
    private StackPane editPlatformStackPane;
    
    private VBox optionsVBox;
    
    private int position = 0;
    private boolean direction = true;
    
    private StackPane scrollPaneStackPane;
    private ScrollPane scrollPane;
    private HBox scrollPaneContent;
    
    private StackPane sideTitle;
    
    private boolean platformBoolean; 
    private boolean addPlatformBoolean = false;
    private Platform selectedPlatform;
    private int selectedPlatformIndex;
    private StackPane sideStackPane; 
    private PlatformInfoPane editpip;
    private TextField newTitle;
    
    private Platform newPlatform;
    private PlatformInfoPane newpip;
    
    public PlatformsMenu() {

        imagesPath = "/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/images/platformsmenu/";
        createBackground();
        createOptionsPane();
        createBackgroundLayers();
        createTitleVBox();
        createScrollPaneAndContent();
        hideOptions();
        this.getChildren().addAll(platformCount, gameCount, platformsMenuVBox, optionsVBox, scrollPaneStackPane, sideTitle);


    }
    
    @Override
    public final void createBackground() {
        Image image = new Image (new File(imagesPath + "background.png").toURI().toString());
        BackgroundSize backgroundSize = new BackgroundSize(width, height, false, false, false, false);
        this.setBackground(new Background(new BackgroundImage(image, REPEAT, NO_REPEAT, CENTER, backgroundSize)));    
    }
    
    private void createTitleVBox() {
        platformsMenuVBox = new VBox();
       
        Font headerFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 100).getFont();
        Font smallerheaderFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 40).getFont();
        
        
        StackPane h1 = new StackPane();
        Text headerText = new Text("Platforms");
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

        platformsMenuVBox.getChildren().addAll(h1, h2);
        platformsMenuVBox.setAlignment(Pos.TOP_CENTER);
        platformsMenuVBox.setStyle("-fx-background-color: #66000000;");
        platformsMenuVBox.setMaxSize(100, 100);
        platformsMenuVBox.setPadding(new Insets(0,300,550,0));
        
        
        sideTitle = new StackPane();
            sideTitle.setMaxSize(275,175);
            sideTitle.setMinSize(275,175);
            sideTitle.setPadding(new Insets(0,930,0,0));
        Text title = new Text ("View\nMaster\nPlatform List"); 
                title.setFont(titleFont);
                title.setFill(Color.WHITE);
                title.setTextAlignment(TextAlignment.RIGHT);
                sideTitle.getChildren().add(title);
    
    }
    
    private void createBackgroundLayers() {
                
        platformCount = new StackPane();
        createPlatformCount();
        
        gameCount = new HBox();
        createGameCount();
        
        contentHBox = new HBox();
            contentHBox.setSpacing(40);
            contentHBox.setAlignment(Pos.CENTER_LEFT);
            contentHBox.setMinHeight(335);
            contentHBox.setMaxHeight(335);
            
        titleStackPane = new StackPane();
            titleStackPane.setMinSize(230,335);
            titleStackPane.setMaxSize(230,335);
            titleStackPane.setAlignment(Pos.CENTER_RIGHT);
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
            platformCount.setPadding(new Insets(30,0,0,100));
    }
    
    private void createGameCount() {
       Font textFont = new FontGenerator(fontsPath + "joystick.ttf", 30).getFont();
       
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
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        gameCount.getChildren().addAll(gameNumber, time);
        gameCount.setAlignment(Pos.TOP_RIGHT);
        gameCount.setPadding(new Insets(35,40,0,0));
        gameCount.setSpacing(45);
    }
    
    private void createOptionsPane() {
        
        Font textFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 18).getFont();
        
        addPlatform = new ImagePane(imagesPath + "clapBoard.png", cbWidth2, cbHeight2);
        back = new ImagePane(imagesPath + "clapBoard.png", cbWidth2, cbHeight2);
        editPlatform = new ImagePane(imagesPath + "clapBoard.png", cbWidth2, cbHeight2);
        deletePlatform = new ImagePane(imagesPath + "clapBoard.png", cbWidth2, cbHeight2);
        
        Button addPlatformButton = new Button("Add\nPlatform");
            addPlatformButton.setFont(textFont);
            addPlatformButton.setTextAlignment(TextAlignment.CENTER);
            addPlatformButton.setTextFill(Color.WHITE);
            addPlatformButton.setPrefSize(cbWidth2, cbHeight2);
            addPlatformButton.setBackground(Background.EMPTY);
            addPlatformButton.setOnAction(new PlatformsMenu.ButtonHandler());
            addPlatformButton.setOnMouseEntered(new PlatformsMenu.HoverOn());
            addPlatformButton.setOnMouseExited(new PlatformsMenu.HoverOff());
             
        addPlatform2 = new GridPane();
            addPlatform2.setVisible(false);
            addPlatform2.add(new ImagePane(imagesPath + "upperLeft.png", cornerSize2, cornerSize2), 0, 0);
            addPlatform2.add(new ImagePane(imagesPath + "upperRight.png", cornerSize2, cornerSize2), 1, 0);
            addPlatform2.add(new ImagePane(imagesPath + "lowerLeft.png", cornerSize2, cornerSize2), 0, 1);
            addPlatform2.add(new ImagePane(imagesPath + "lowerRight.png", cornerSize2, cornerSize2), 1, 1);
            addPlatform2.setAlignment(Pos.CENTER);
            addPlatform2.setPadding(new Insets(25,0,0,0));
            addPlatform2.setVgap(20);
            addPlatform2.setHgap(70);
            
        Button deletePlatformButton = new Button("Delete\nPlatform");
            deletePlatformButton.setFont(textFont);
            deletePlatformButton.setTextAlignment(TextAlignment.CENTER);
            deletePlatformButton.setTextFill(Color.WHITE);
            deletePlatformButton.setPrefSize(cbWidth2, cbHeight2);
            deletePlatformButton.setBackground(Background.EMPTY);
            deletePlatformButton.setOnAction(new PlatformsMenu.ButtonHandler());
            deletePlatformButton.setOnMouseEntered(new PlatformsMenu.HoverOn());
            deletePlatformButton.setOnMouseExited(new PlatformsMenu.HoverOff());
             
        deletePlatform2 = new GridPane();
            deletePlatform2.setVisible(false);
            deletePlatform2.add(new ImagePane(imagesPath + "upperLeft.png", cornerSize2, cornerSize2), 0, 0);
            deletePlatform2.add(new ImagePane(imagesPath + "upperRight.png", cornerSize2, cornerSize2), 1, 0);
            deletePlatform2.add(new ImagePane(imagesPath + "lowerLeft.png", cornerSize2, cornerSize2), 0, 1);
            deletePlatform2.add(new ImagePane(imagesPath + "lowerRight.png", cornerSize2, cornerSize2), 1, 1);
            deletePlatform2.setAlignment(Pos.CENTER);
            deletePlatform2.setPadding(new Insets(25,0,0,0));
            deletePlatform2.setVgap(20);
            deletePlatform2.setHgap(70);
            
        Button editPlatformButton = new Button("Edit\nPlatform");
            editPlatformButton.setFont(textFont);
            editPlatformButton.setTextAlignment(TextAlignment.CENTER);
            editPlatformButton.setTextFill(Color.WHITE);
            editPlatformButton.setPrefSize(cbWidth2, cbHeight2);
            editPlatformButton.setBackground(Background.EMPTY);
            editPlatformButton.setOnAction(new PlatformsMenu.ButtonHandler());
            editPlatformButton.setOnMouseEntered(new PlatformsMenu.HoverOn());
            editPlatformButton.setOnMouseExited(new PlatformsMenu.HoverOff());
             
        editPlatform2 = new GridPane();
            editPlatform2.setVisible(false);
            editPlatform2.add(new ImagePane(imagesPath + "upperLeft.png", cornerSize2, cornerSize2), 0, 0);
            editPlatform2.add(new ImagePane(imagesPath + "upperRight.png", cornerSize2, cornerSize2), 1, 0);
            editPlatform2.add(new ImagePane(imagesPath + "lowerLeft.png", cornerSize2, cornerSize2), 0, 1);
            editPlatform2.add(new ImagePane(imagesPath + "lowerRight.png", cornerSize2, cornerSize2), 1, 1);
            editPlatform2.setAlignment(Pos.CENTER);
            editPlatform2.setPadding(new Insets(25,0,0,0));
            editPlatform2.setVgap(20);
            editPlatform2.setHgap(70);
            
        Button backButton = new Button("Back");
            backButton.setFont(textFont);
            backButton.setTextAlignment(TextAlignment.CENTER);
            backButton.setTextFill(Color.WHITE);
            backButton.setPrefSize(cbWidth2, cbHeight2);
            backButton.setBackground(Background.EMPTY);
            backButton.setOnAction(new PlatformsMenu.ButtonHandler());
            backButton.setOnMouseEntered(new PlatformsMenu.HoverOn());
            backButton.setOnMouseExited(new PlatformsMenu.HoverOff());
             
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
        
        addPlatformStackPane = new StackPane();
            addPlatformStackPane.getChildren().addAll(addPlatform, addPlatform2, addPlatformButton);
            addPlatformStackPane.setAlignment(Pos.CENTER);
            
        backStackPane = new StackPane();
            backStackPane.getChildren().addAll(back, back2, backButton);
            backStackPane.setAlignment(Pos.CENTER);
            
        deletePlatformStackPane = new StackPane();
            deletePlatformStackPane.getChildren().addAll(deletePlatform, deletePlatform2, deletePlatformButton);
            deletePlatformStackPane.setAlignment(Pos.CENTER);
            
        editPlatformStackPane = new StackPane();
            editPlatformStackPane.getChildren().addAll(editPlatform, editPlatform2, editPlatformButton);
            editPlatformStackPane.setAlignment(Pos.CENTER);
            
        optionsVBox = new VBox();
            optionsVBox.setAlignment(Pos.CENTER);
            optionsVBox.setSpacing(10);
            optionsVBox.setMaxWidth(125);
            optionsVBox.setMinWidth(125);
            optionsVBox.setPadding(new Insets(85,0,0,1000));
            optionsVBox.getChildren().addAll(addPlatformStackPane, deletePlatformStackPane,
                   editPlatformStackPane, backStackPane);
            
        moving();
    }
    
    private void createScrollPaneAndContent() {
        
        int spHeight = 425;
        int spWidth = 725;

        scrollPaneContent = new HBox();    
                scrollPaneContent.setStyle("-fx-background-color: #66000000;");
                scrollPaneContent.setPadding(new Insets(0,5,0,5));
                scrollPaneContent.setSpacing(50);
                scrollPaneContent.setAlignment(Pos.CENTER);
                
        createContent(); 
        
            scrollPane = new ScrollPane(scrollPaneContent);
                
                scrollPane.setMinViewportHeight(spHeight);
                scrollPane.setMinViewportWidth(spWidth);
                scrollPane.setFitToHeight(true);
                scrollPane.setFitToWidth(true);

                scrollPane.setMaxHeight(spHeight);
                scrollPane.setMinHeight(spHeight);
                scrollPane.setMaxWidth(spWidth);
                scrollPane.setMinWidth(spWidth);
                
            scrollPaneStackPane = new StackPane();
                scrollPaneStackPane.getChildren().add(scrollPane);
                scrollPaneStackPane.setMaxSize(scrollPane.getWidth(), scrollPane.getHeight());
                scrollPaneStackPane.setStyle("-fx-background-color: #66000000;");
                scrollPaneStackPane.setPadding(new Insets(10,0,0,50));
            
    }
    
    public void createContent() {
        
        scrollPaneContent.getChildren().clear();
                
        if (platforms.getNumPlatforms() != 0 ) {
           
            PlatformInfoPane pip;
            Platform p; 
            
            for (int i = 0; i < platforms.getNumPlatforms(); i++) {
                p = platforms.getPlatform(i);
                pip = new PlatformInfoPane(p, 40, 18);
                scrollPaneContent.getChildren().add(pip);
            }
        }
        
        else {
            
            Text title = new Text ("Your library is empty.\nAdd a platform to get started!"); 
                title.setFont(titleFont);
                title.setFill(Color.WHITE);
                title.setTextAlignment(TextAlignment.CENTER);
                
            scrollPaneContent.getChildren().add(title);

        }
    }
    
    private void hideOptions() {
        if (platforms.getNumPlatforms() == 0) {
            editPlatformStackPane.setVisible(false);
            deletePlatformStackPane.setVisible(false);
        }
        else {
            editPlatformStackPane.setVisible(true);
            deletePlatformStackPane.setVisible(true);
        }
    }
    
    private void setSelectedPlatform(String platformTitle) {
        
        for (int i = 0; i < platforms.getNumPlatforms(); i++) {
            if (platforms.getPlatform(i).getName().equals(platformTitle)) {
                selectedPlatform = platforms.getPlatform(i);
                selectedPlatformIndex = i; 
                break;
            }
        }
    }
    
    private void moving() {
        Timer randomTimer = new Timer();
        randomTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                  javafx.application.Platform.runLater(() -> {
                      
                    if (position < -10) direction = false;
                    else if (position > 10) direction = true;
                    
                    if (direction) position--;
                    else position++;
                    
                    addPlatformStackPane.setPadding(new Insets(0,position,0,0));
                    deletePlatformStackPane.setPadding(new Insets(0,0,0,position));
                    editPlatformStackPane.setPadding(new Insets(0,position,0,0));
                    backStackPane.setPadding(new Insets(0,0,0,position));

                    
                 });
            }
       }, 0, 25);
    }
    
    private void dialogPopUp(int choice) {
        Font largeFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 30).getFont();
        
        dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            
        Button closeButton = new Button("Close");
            closeButton.setFont(largeFont);
            closeButton.setTextFill(Color.WHITE);
            closeButton.setStyle("-fx-background-color: #66000000;");
            closeButton.setOnAction(new PlatformsMenu.ButtonHandler());
            closeButton.setOnMouseEntered(new PlatformsMenu.HoverOn());
            closeButton.setOnMouseExited(new PlatformsMenu.HoverOff()); 
            closeButton.setAlignment(Pos.CENTER);
            
        VBox miniScrollPaneContent = new VBox();
  
        Text platformList = new Text("Platorm List"); 
            platformList.setFont(titleFont);
            platformList.setFill(Color.WHITE);
            platformList.setTextAlignment(TextAlignment.CENTER);
                
        Text text = new Text(); 
            text.setFont(titleFont);
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setWrappingWidth(450);
            
        sideStackPane = new StackPane();
            sideStackPane.setMinSize(550, 300);
        
        if (choice == 1) {
            text.setText("Please choose a\nplatform to delete.");
            platformBoolean = false;
        }
        else if (choice == 2) {
            text.setText("Please choose a\nplatform to edit.");
            platformBoolean = true;
        }
        else if (choice == 3) {
            if(selectedPlatform.getUsedIDs().isEmpty()) {
                platforms.deletePlatform(selectedPlatformIndex);
                new File(basePath + "images/platforms/" + selectedPlatform.getID()+ ".png").delete();
                hideOptions();
                text.setText(selectedPlatform.getName() + " has been deleted.");
            }
            else {
                selectedPlatform.setOwn(false);
                text.setText(selectedPlatform.getName() + " ownership has been updated.");
            }
            
            writePlatforms(platforms);
        }
        else if (choice == 4) {
            
            Platform updatedPlatform = editpip.addCorrections();
            if(!newTitle.getText().trim().isEmpty()) {
                String oldName = selectedPlatform.getName();
                String newName = newTitle.getText().trim();
                games.updateGamesPlatform(oldName, newName);
                selectedPlatform.setName(newName);
                writeGames(games);
            }
            
            selectedPlatform.setDeveloper(updatedPlatform.getDeveloper());
            selectedPlatform.setManufacturer(updatedPlatform.getManufacturer());
            selectedPlatform.setType(updatedPlatform.getType());
            
            if(updatedPlatform.isOwn()) selectedPlatform.setOwn(true);
            else selectedPlatform.setOwn(false);
        
            try {
                selectedPlatform.setRelease(updatedPlatform.dateFormat.parse(updatedPlatform.getRelease()));
            }

            catch (ParseException ex) {
                System.err.print("Could not parse date correctly.");
                System.exit(-1);
            }
            
            text.setText(selectedPlatform.getName() + " has been edited successfully.");
            
            writePlatforms(platforms);
        }
        else if (choice == 5) {
            addPlatform();
        }
        else if (choice == 6) {
            newPlatform = newpip.addCorrections();
            newPlatform.setName(newTitle.getText().trim());
            platforms.addPlatform(newPlatform);
            writePlatforms(platforms);
            hideOptions();
            addPlatform();
        }
        
        sideStackPane.getChildren().add(text);
        
        String gameText;
        Button platformButton;
        Font textFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 18).getFont();
            
            
            for (int i = 0; i < platforms.getNumPlatforms(); i++) {

                gameText = platforms.getPlatform(i).getName(); 
                platformButton = new Button(gameText);
                    platformButton.setFont(textFont);
                    platformButton.setTextFill(Color.WHITE);
                    platformButton.setAlignment(Pos.CENTER_LEFT);
                    platformButton.setStyle("-fx-background-color: transparent;");
                    platformButton.setOnAction(new PlatformsMenu.ButtonHandler());
                    platformButton.setOnMouseEntered(new PlatformsMenu.HoverOn());
                    platformButton.setOnMouseExited(new PlatformsMenu.HoverOff());
                miniScrollPaneContent.getChildren().add(platformButton);
            }
            
        ScrollPane miniScrollPane = new ScrollPane(miniScrollPaneContent);
            int x = 400;
            int y = 300;
                miniScrollPane.setMinViewportHeight(y);
                miniScrollPane.setMinViewportWidth(x);
                miniScrollPane.setMaxHeight(y);
                miniScrollPane.setMinHeight(y);
                miniScrollPane.setMaxWidth(x);
                miniScrollPane.setMinWidth(x);
        
        VBox topLeftVBox = new VBox(platformList, miniScrollPane);
            topLeftVBox.setAlignment(Pos.CENTER);
            topLeftVBox.setSpacing(20);
                
        HBox topHBox = new HBox();
            topHBox.getChildren().addAll(topLeftVBox, sideStackPane);
            topHBox.setMinHeight(450);
            topHBox.setAlignment(Pos.CENTER_LEFT);
            topHBox.setPadding(new Insets(0,0,0,20));
            topHBox.setSpacing(10);
        VBox mainVBox = new VBox();
            mainVBox.getChildren().addAll(topHBox, closeButton); 
            mainVBox.setAlignment(Pos.CENTER);
            
        StackPane sp = new StackPane(mainVBox);
            sp.setMinSize(1000, 500);
            sp.setMaxSize(1000, 500);
            BackgroundSize backgroundSize = new BackgroundSize(1000, 500, false, false, false, false);
            Image image = new Image (new File(basePath + "images/platformsmenu/popup.gif").toURI().toString());
            sp.setAlignment(Pos.CENTER);
            sp.setBackground(new Background(new BackgroundImage(image, REPEAT, NO_REPEAT, CENTER, backgroundSize)));
                   
        dialogScene = new Scene(sp);
            dialogScene.getStylesheets().add(this.getClass().getResource("/css/PlatformsDialog.css").toExternalForm());
            dialogStage.setScene(dialogScene);
            dialogStage.show();
    }
    private void addPlatform() {
        sideStackPane.getChildren().clear();
        
        newPlatform = new Platform();
                newPlatform.setID(newPlatformNumber(platforms));
                
        Font smallFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 16).getFont();             
        newpip = new PlatformInfoPane(newPlatform, 20, 14, readGenerations(platforms));
        

        newTitle = new TextField(); 
            newTitle.setFont(new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont());
            newTitle.setPromptText(newPlatform.getName());
            newTitle.setMaxWidth(350);
            newTitle.setAlignment(Pos.CENTER_LEFT);
            
        Text topText = new Text("Add New Platform"); 
            topText.setFont(titleFont);
            topText.setFill(Color.WHITE);
            topText.setTextAlignment(TextAlignment.CENTER);   
            
        Text text = new Text("Create this platform?"); 
            text.setFont(titleFont);
            text.setFill(Color.web("#F03B87"));
            text.setTextAlignment(TextAlignment.CENTER);
            
        Button yes = new Button("Yes");
                yes.setFont(smallFont);
                yes.setTextFill(Color.WHITE);
                yes.setStyle("-fx-background-color: #66000000;");
                yes.setOnAction(new PlatformsMenu.ButtonHandler());
                yes.setOnMouseEntered(new PlatformsMenu.HoverOn());
                yes.setOnMouseExited(new PlatformsMenu.HoverOff());

            Button no = new Button("No");
                no.setFont(smallFont);
                no.setTextFill(Color.WHITE);
                no.setStyle("-fx-background-color: #66000000;");
                no.setOnAction(new PlatformsMenu.ButtonHandler());
                no.setOnMouseEntered(new PlatformsMenu.HoverOn());
                no.setOnMouseExited(new PlatformsMenu.HoverOff());
                
        HBox optionsHBox = new HBox(yes, no); 
            optionsHBox.setAlignment(Pos.CENTER);
            optionsHBox.setSpacing(3);
            optionsHBox.setMaxSize(100, 75);
            
        HBox hbox = new HBox(newpip.getSmallerImagePane(), newpip.getGridPane());
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);
            
        newpip.addTextFields();
        
        VBox editVBox = new VBox();
            editVBox.setAlignment(Pos.CENTER);
            editVBox.setSpacing(10);
            editVBox.getChildren().addAll(topText, newTitle, hbox, text, optionsHBox);
            
        sideStackPane.getChildren().add(editVBox);
        
    }
    
    private void editPlatformUpdate() {
        sideStackPane.getChildren().clear();
               
        Font smallFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 16).getFont();
        editpip = new PlatformInfoPane(selectedPlatform, 20, 14, readGenerations(platforms));
        
        newTitle = new TextField(); 
            newTitle.setFont(new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont());
            newTitle.setPromptText(selectedPlatform.getName());
            newTitle.setMaxWidth(350);
            newTitle.setAlignment(Pos.CENTER_LEFT);
            
        Text text = new Text("Save these changes?"); 
            text.setFont(titleFont);
            text.setFill(Color.web("#F03B87"));
            text.setTextAlignment(TextAlignment.CENTER);
            
        Button yes = new Button("Yes");
                yes.setFont(smallFont);
                yes.setTextFill(Color.WHITE);
                yes.setStyle("-fx-background-color: #66000000;");
                yes.setOnAction(new PlatformsMenu.ButtonHandler());
                yes.setOnMouseEntered(new PlatformsMenu.HoverOn());
                yes.setOnMouseExited(new PlatformsMenu.HoverOff());

            Button no = new Button("No");
                no.setFont(smallFont);
                no.setTextFill(Color.WHITE);
                no.setStyle("-fx-background-color: #66000000;");
                no.setOnAction(new PlatformsMenu.ButtonHandler());
                no.setOnMouseEntered(new PlatformsMenu.HoverOn());
                no.setOnMouseExited(new PlatformsMenu.HoverOff());
                
        HBox optionsHBox = new HBox(yes, no); 
            optionsHBox.setAlignment(Pos.CENTER);
            optionsHBox.setSpacing(3);
            optionsHBox.setMaxSize(100, 75);
            
        HBox hbox = new HBox(editpip.getSmallerImagePane(), editpip.getGridPane());
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);
            
        editpip.addTextFields();
            
        VBox editVBox = new VBox();
            editVBox.setAlignment(Pos.CENTER);
            editVBox.setSpacing(10);
            editVBox.getChildren().addAll(newTitle, hbox, text, optionsHBox);
            
        sideStackPane.getChildren().add(editVBox);
        
    }

    private void deletePlatformUpdate() {
        sideStackPane.getChildren().clear();
        
        Font smallFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", 16).getFont();
        
        Text name = new Text(selectedPlatform.getName()); 
            name.setFont(smallFont);
            name.setFill(Color.WHITE);
            name.setTextAlignment(TextAlignment.CENTER);
        
        ImagePane platformImage = new ImagePane(selectedPlatform.getPath(), 300, 175);
            platformImage.setAlignment(Pos.CENTER);
        
        Text text = new Text("Delete this platform?"); 
            text.setFont(titleFont);
            text.setFill(Color.web("#F03B87"));
            text.setTextAlignment(TextAlignment.CENTER);
            
        Button yes = new Button("Yes");
                yes.setFont(smallFont);
                yes.setTextFill(Color.WHITE);
                yes.setStyle("-fx-background-color: #66000000;");
                yes.setOnAction(new PlatformsMenu.ButtonHandler());
                yes.setOnMouseEntered(new PlatformsMenu.HoverOn());
                yes.setOnMouseExited(new PlatformsMenu.HoverOff());

            Button no = new Button("No");
                no.setFont(smallFont);
                no.setTextFill(Color.WHITE);
                no.setStyle("-fx-background-color: #66000000;");
                no.setOnAction(new PlatformsMenu.ButtonHandler());
                no.setOnMouseEntered(new PlatformsMenu.HoverOn());
                no.setOnMouseExited(new PlatformsMenu.HoverOff());
                
        HBox optionsHBox = new HBox(yes, no); 
            optionsHBox.setAlignment(Pos.CENTER);
            optionsHBox.setSpacing(3);
            optionsHBox.setMaxSize(100, 75);
            
        VBox deleteVBox = new VBox();
            deleteVBox.setAlignment(Pos.CENTER);
            deleteVBox.setSpacing(10);
            deleteVBox.getChildren().addAll(name, platformImage, text, optionsHBox);
            
        sideStackPane.getChildren().add(deleteVBox);
        
    }
    
    public Boolean validateTitle() {
        return !newTitle.getText().trim().isEmpty();
    }
    
    public void blankFieldsPopup() {
        
        secondDialogStage = new Stage();
            secondDialogStage.initModality(Modality.WINDOW_MODAL);
            secondDialogStage.setResizable(false);
            secondDialogStage.initStyle(StageStyle.UNDECORATED);
                    
        Font font = new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont();
        
        Text text = new Text("One or more fields\nhave been left blank.\n Please fill out\nall fields.");
           text.setFont(font);
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);                
                            
        Button dialogButton = new Button("dialogButton");
            dialogButton.setPrefSize(300,300);
            dialogButton.setStyle("-fx-background-color: transparent;");
            dialogButton.setTextFill(Color.TRANSPARENT);
            dialogButton.setOnMouseExited(new PlatformsMenu.HoverOff());
            
        StackPane sp = new StackPane(text, dialogButton);
            BackgroundSize backgroundSize = new BackgroundSize(300, 300, false, false, true, true);
            Image image = new Image (new File(basePath + "images/platformsmenu/popup.gif").toURI().toString());
            sp.setAlignment(Pos.CENTER);
            sp.setBackground(new Background(new BackgroundImage(image, REPEAT, NO_REPEAT, CENTER, backgroundSize)));
            sp.setMinSize(185, 310);                     

                   
        secondDialogScene = new Scene(sp);
            secondDialogStage.setScene(secondDialogScene);
            secondDialogStage.show();
                    
                    
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
    
    public void createdPlatformPopUp(String name) {
        
        secondDialogStage = new Stage();
            secondDialogStage.initModality(Modality.WINDOW_MODAL);
            secondDialogStage.setResizable(false);
            secondDialogStage.initStyle(StageStyle.UNDECORATED);
                    
        Font font = new FontGenerator(fontsPath + "superMarioMaker.ttf", 20).getFont();
        
        Text text = new Text(name + " has been successfully created and added!");
           text.setFont(font);
           text.setWrappingWidth(275);
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);                
                            
        Button dialogButton = new Button("dialogButton");
            dialogButton.setPrefSize(300,300);
            dialogButton.setStyle("-fx-background-color: transparent;");
            dialogButton.setTextFill(Color.TRANSPARENT);
            dialogButton.setOnMouseExited(new PlatformsMenu.HoverOff());
            
        StackPane sp = new StackPane(text, dialogButton);
            BackgroundSize backgroundSize = new BackgroundSize(300, 300, false, false, true, true);
            Image image = new Image (new File(basePath + "images/platformsmenu/popup.gif").toURI().toString());
            sp.setAlignment(Pos.CENTER);
            sp.setBackground(new Background(new BackgroundImage(image, REPEAT, NO_REPEAT, CENTER, backgroundSize)));
            sp.setMinSize(185, 310);                     

                   
        secondDialogScene = new Scene(sp);
            secondDialogStage.setScene(secondDialogScene);
            secondDialogStage.show();
                    
                    
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
    
    private class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
                      
            Button button = (Button) event.getSource();
            String sourceButton = button.getText();
                       
            if (sourceButton.equals("Back")) main();
            else if (sourceButton.equals("Delete\nPlatform")) {
                addPlatformBoolean = false;
                dialogPopUp(1);
            }
            else if (sourceButton.equals("Edit\nPlatform")) {
                addPlatformBoolean = false;
                dialogPopUp(2);
            }
            else if (sourceButton.equals("Close")) closeDialogs();
            else if (sourceButton.equals("Add\nPlatform")) {
                addPlatformBoolean = true;
                dialogPopUp(5); 
            }
            else if (sourceButton.equals("No")) {
                closeDialogs();
                if (addPlatformBoolean) dialogPopUp(5);
                else {
                    if(platformBoolean) dialogPopUp(2);
                    else dialogPopUp(1);
                }
            }
            else if (sourceButton.equals("Yes")) {
                if(addPlatformBoolean) {
                    if(validateTitle() && newpip.validateFields()) {
                        String ncp = newTitle.getText().trim();
                        closeDialogs();
                        dialogPopUp(6);
                        createdPlatformPopUp(ncp);
                    }
                    else blankFieldsPopup();
                }
                else {
                    closeDialogs();
                    if(platformBoolean) dialogPopUp(4);
                    else dialogPopUp(3);
                }
                createContent();
            }
            else {
                if(!addPlatformBoolean) {
                    setSelectedPlatform(sourceButton);
                    if(platformBoolean) editPlatformUpdate();
                    else deletePlatformUpdate();
                }
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
           
            
            
            if (sourceButton.equals("Add\nPlatform")) addPlatform2.setVisible(true);
            else if (sourceButton.equals("Back")) back2.setVisible(true);
            else if (sourceButton.equals("Edit\nPlatform")) editPlatform2.setVisible(true);
            else if (sourceButton.equals("Delete\nPlatform")) deletePlatform2.setVisible(true);
            else button.setTextFill(Color.web("#F03B87"));
            
            
        }
    }
    
    private class HoverOff implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            Button button = (Button) event.getSource();
            String sourceButton = button.getText();

            if (sourceButton.equals("dialogButton")) closeDialogs2();
            else if (sourceButton.equals("Add\nPlatform")) addPlatform2.setVisible(false);
            else if (sourceButton.equals("Back")) back2.setVisible(false);
            else if (sourceButton.equals("Edit\nPlatform")) editPlatform2.setVisible(false);
            else if (sourceButton.equals("Delete\nPlatform")) deletePlatform2.setVisible(false);
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
