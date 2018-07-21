package vglfx;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import vgl.Platform;

public class PlatformPane extends HBox {
    private final int width = 300;
    private final int height = 150;
    
    private int leftWrapping;
    private int rightWrapping;
    
    private Platform platform;
    private Pane imagePane;  
    private GridPane platformInfoPane;
    private Font textFont;
    private final String fontsPath;
    
    public PlatformPane(Platform p) {
        platform = p;
        imagePane = new ImagePane(platform.getPicture(), width, height);
        fontsPath = "resources/fonts/";
        textFont = new CustomFont(fontsPath + "joystick.ttf", 12).getFont();
        leftWrapping = 130;
        rightWrapping = 200;
        
        createPlatformPane();
    }
    
    public PlatformPane (Platform p, int w, int h, int f, int lw, int rw){
        platform = p;
        imagePane = new ImagePane(platform.getPicture(), w, h);
        fontsPath = "resources/fonts/";
        textFont = new CustomFont(fontsPath + "joystick.ttf", f).getFont();
        leftWrapping = lw;
        rightWrapping = rw;
        
        createPlatformPane();
    }
    
     private void createPlatformPane() {
        
        platformInfoPane = new GridPane();
        setLabels();
        setPlatformInformation();
        
        platformInfoPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        platformInfoPane.setVgap(15); //vertical gap in pixels
        platformInfoPane.setAlignment(Pos.CENTER);
        
        this.getChildren().addAll(imagePane, platformInfoPane);
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));
    }
     
     public void setLabels() {
        String[] array = {"Name:", 
            "Released:", 
            "Developer:", 
            "Manufacturer:", 
            "Generation:", 
            "Platform ID:",
            "Type:"};
        
        Text text;
        
        for (int i = 0; i < array.length; i++) {
            text = new Text();
            text.setText(array[i]);
            text.setFont(textFont);
            text.setTextAlignment(TextAlignment.RIGHT);
            text.setFill(Color.WHITE);
            text.setWrappingWidth(leftWrapping);
            platformInfoPane.add(text, 0, i); // 1 0 column=1 row=0
            platformInfoPane.setHalignment(text, HPos.RIGHT);
        }
    }
     
    public void setPlatformInformation() {
        
        Text text;
        
        Text text1 = new Text(platform.getName());
        Text text2 = new Text(platform.getRelease());
        Text text3 = new Text(platform.getDeveloper());
        Text text4 = new Text(platform.getManufacturer());
        Text text5 = new Text(platform.getGeneration());
        Text text6 = new Text(platform.getID() + new String());
        Text text7 = new Text(platform.getType());
        
        Text[] texts = {text1, text2, text3, text4, text5, text6, text7}; 
        
        for (int i = 0; i < texts.length; i++) {
            text = texts[i];
            text.setFont(textFont);
            text.setFill(Color.WHITE);
            text.setWrappingWidth(rightWrapping);
            platformInfoPane.add(text, 1, i); 
            platformInfoPane.setHalignment(text, HPos.LEFT);
            
        } 

    }
    
    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Pane getImagePane() {
        return imagePane;
    }

    public void setImagePane(Pane imagePane) {
        this.imagePane = imagePane;
    }

    public GridPane getPlatformInfoPane() {
        return platformInfoPane;
    }

    public void setPlatformInfoPane(GridPane platformInfoPane) {
        this.platformInfoPane = platformInfoPane;
    }

    public Font getTextFont() {
        return textFont;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }
    
}
