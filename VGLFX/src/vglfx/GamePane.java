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
import vgl.Game;

public class GamePane extends HBox {
    private final int width = 175;
    private final int height = 250;
    
    private int leftWrapping;
    private int rightWrapping;
    
    private Game game;
    private Pane imagePane;
    private GridPane gameInfoPane;
    private Font textFont;
    private final String fontsPath;
    
    public GamePane (Game g) {
        game = g;
        imagePane = new ImagePane(g.getPicture(), width, height);
        fontsPath = "resources/fonts/";
        textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", 10).getFont();
        leftWrapping = 100;
        rightWrapping = 200;
        
        createGamePane();
    }
    
    public GamePane (Game g, int w, int h, int f, int lw, int rw){
        game = g;
        imagePane = new ImagePane(g.getPicture(), w, h);
        fontsPath = "resources/fonts/";
        textFont = new CustomFont(fontsPath + "pacmanArcade.ttf", f).getFont();
        leftWrapping = lw;
        rightWrapping = rw;
        
        createGamePane();
    }
    
    private void createGamePane() {
        
        gameInfoPane = new GridPane();
        setLabels();
        setGameInformation();
        
        gameInfoPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        gameInfoPane.setVgap(15); //vertical gap in pixels
        gameInfoPane.setAlignment(Pos.CENTER_LEFT);
        
        this.getChildren().addAll(imagePane, gameInfoPane);
        this.setSpacing(20);
        this.setStyle("-fx-background-color: #000000;");
        this.setPadding(new Insets(10,10,10,10));
    }
    
    public void setLabels() {
        String[] array = {"Title:", 
            "Released:", 
            "Publisher:",
            "Developer:", 
            "Genre:", 
            "Platform:", 
            "Catalog Number:",
            "Started/\nCompleted:"};
        
        Text text;
        
        for (int i = 0; i < array.length; i++) {
            text = new Text();
            text.setText(array[i]);
            text.setFont(textFont);
            text.setTextAlignment(TextAlignment.RIGHT);
            text.setFill(Color.WHITE);
            text.setWrappingWidth(leftWrapping);
            gameInfoPane.add(text, 0, i); // 1 0 column=1 row=0
            gameInfoPane.setHalignment(text, HPos.RIGHT);
        }
    }
    
    public void setGameInformation() {
        
        Text text;
        
        Text text1 = new Text(game.getTitle());
        Text text2 = new Text(game.getRelease());
        Text text3 = new Text(game.getPublisher());
        Text text4 = new Text(game.getDeveloper());
        Text text5 = new Text(game.getGenre());
        Text text6 = new Text(game.getPlatform());
        Text text7 = new Text(game.getID() + new String());
        Text text8 = new Text(game.getStatus());
        
        Text[] texts = {text1, text2, text3, text4, text5, text6, text7, text8}; 
        
        for (int i = 0; i < texts.length; i++) {
            text = texts[i];
            text.setFont(textFont);
            text.setFill(Color.WHITE);
            text.setWrappingWidth(rightWrapping);
            gameInfoPane.add(text, 1, i); 
            gameInfoPane.setHalignment(text, HPos.LEFT);
            
        } 

    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Pane getImagePane() {
        return imagePane;
    }

    public void setImagePane(Pane imagePane) {
        this.imagePane = imagePane;
    }

    public GridPane getGameInfoPane() {
        return gameInfoPane;
    }

    public void setGameInfoPane(GridPane gameInfoPane) {
        this.gameInfoPane = gameInfoPane;
    }

    public Font getTextFont() {
        return textFont;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }
    
}