package Platforms;

import Necessities.FontGenerator;
import Necessities.ImagePane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import library.Platform;

public final class PlatformInfoPane extends VBox {
    private final Platform platform; 
    private HBox hbox;
    private GridPane gridPane;
    private ImagePane platformImage;
    
    public final String fontsPath = "resources/fonts/";
    private final Font largeFont; 
    private final Font smallFont;
    
    private Text title; 
    
    PlatformInfoPane(Platform p, int x, int y) {
        platform = p;
        largeFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", x).getFont();
        smallFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", y).getFont();
        createPlatformInfoPane();
    }
    
    public void createPlatformInfoPane() {
        createHBox();

        title = new Text();
            if (platform.getName().length() < 60) title = new Text(platform.getName());
            else title = new Text(platform.getName().substring(0, 60) + "...");
            title.setFill(Color.WHITE);
            title.setTextAlignment(TextAlignment.LEFT);
            title.setFont(largeFont);
            
        StackPane titleStackPane = new StackPane();
            titleStackPane.setAlignment(Pos.CENTER);
            titleStackPane.getChildren().add(title);
        
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(hbox, titleStackPane);
        this.setSpacing(25);
    }
    
    public void createHBox() {
        createGridPane();
        createPlatformImage();
        
        hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);            
            hbox.getChildren().addAll(platformImage, gridPane);
    }
    
    public void createGridPane() {
        
        gridPane = new GridPane();
            gridPane.setPadding(new Insets(0,0,0,25));
        
        Text release = new Text("Released: ");
            release.setFill(Color.web("#F1E14B"));
            release.setTextAlignment(TextAlignment.RIGHT);
            release.setFont(smallFont);
            
        Text developer = new Text("Developer: ");
            developer.setFill(Color.web("#F1E14B"));
            developer.setTextAlignment(TextAlignment.RIGHT);
            developer.setFont(smallFont);
            
        Text manufacturer = new Text("Manufacturer: ");
            manufacturer.setFill(Color.web("#F1E14B"));
            manufacturer.setTextAlignment(TextAlignment.RIGHT);
            manufacturer.setFont(smallFont);
        
        Text generation = new Text("Generation: ");
            generation.setFill(Color.web("#F1E14B"));
            generation.setTextAlignment(TextAlignment.RIGHT);
            generation.setFont(smallFont);
            
        Text id = new Text("ID Number: ");
            id.setFill(Color.web("#F1E14B"));
            id.setTextAlignment(TextAlignment.RIGHT);
            id.setFont(smallFont);
            
        Text type = new Text("Type: ");
            type.setFill(Color.web("#F1E14B"));
            type.setTextAlignment(TextAlignment.RIGHT);
            type.setFont(smallFont);
         
        gridPane.add(release, 0, 0);
        gridPane.add(developer, 0, 1);
        gridPane.add(manufacturer, 0, 2);
        gridPane.add(generation, 0, 3);
        gridPane.add(id, 0, 4);
        gridPane.add(type, 0, 5);
        
        GridPane.setHalignment(release, HPos.RIGHT);
        GridPane.setHalignment(developer, HPos.RIGHT);
        GridPane.setHalignment(manufacturer, HPos.RIGHT);
        GridPane.setHalignment(generation, HPos.RIGHT);
        GridPane.setHalignment(id, HPos.RIGHT);
        GridPane.setHalignment(type, HPos.RIGHT);
                    
        Text release2 = new Text(platform.getRelease());
            release2.setFill(Color.WHITE);
            release2.setTextAlignment(TextAlignment.LEFT);
            release2.setFont(smallFont);
            
        Text developer2 = new Text(platform.getDeveloper());
            developer2.setFill(Color.WHITE);
            developer2.setTextAlignment(TextAlignment.LEFT);
            developer2.setFont(smallFont);
        
        Text manufacturer2 = new Text(platform.getManufacturer());
            manufacturer2.setFill(Color.WHITE);
            manufacturer2.setTextAlignment(TextAlignment.LEFT);
            manufacturer2.setFont(smallFont);
            
        Text generation2 = new Text(platform.getGeneration());
            generation2.setFill(Color.WHITE);
            generation2.setTextAlignment(TextAlignment.LEFT);
            generation2.setFont(smallFont);
            
        Text id2 = new Text(platform.getID() + "");
            id2.setFill(Color.WHITE);
            id2.setTextAlignment(TextAlignment.LEFT);
            id2.setFont(smallFont);
            
        Text type2 = new Text(platform.getType());
            type2.setFill(Color.WHITE);
            type2.setTextAlignment(TextAlignment.LEFT);
            type2.setFont(smallFont);
        
        gridPane.add(release2, 1, 0);
        gridPane.add(developer2, 1, 1);
        gridPane.add(manufacturer2, 1, 2);
        gridPane.add(generation2, 1, 3);
        gridPane.add(id2, 1, 4);
        gridPane.add(type2, 1, 5);  
        
    }
    
    public void createPlatformImage() {
        platformImage = new ImagePane(platform.getPath(), 300, 175);
    }
}
