package Games;

import Necessities.FontGenerator;
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
import libraryprogram.Game;
import Necessities.ImagePane;

public final class GameInfoPane extends HBox {
    private final Game game; 
    private VBox vbox;
    private GridPane gridPane;
    private ImagePane gameImage;
    
    public final String fontsPath = "/Users/asia/Applications/VideoGameLibrary/LibraryProgramFiles/resources/fonts/";
    private final Font largeFont; 
    private final Font smallFont;
    
    GameInfoPane(Game g, int x, int y) {
        game = g;
        largeFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", x).getFont();
        smallFont = new FontGenerator(fontsPath + "superMarioMaker.ttf", y).getFont();
        createGameInfoPane();
    }
    
    public void createGameInfoPane() {
        createVBox();
        createGameImage();
        
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(gameImage, vbox);
        this.setSpacing(20);
    }
    
    public void createVBox() {
        createGridPane();
        
        vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);            
        Text title;
//            if (game.getTitle().length() < 45) title = new Text(game.getTitle());
//            else title = new Text(game.getTitle().substring(0, 45) + "...");
            title = new Text(game.getTitle());
            title.setWrappingWidth(550);
            title.setFill(Color.WHITE);
            title.setTextAlignment(TextAlignment.LEFT);
            title.setFont(largeFont);
            
        StackPane titleStackPane = new StackPane();
            titleStackPane.setAlignment(Pos.CENTER_LEFT);
            titleStackPane.getChildren().add(title);
            
        vbox.getChildren().addAll(titleStackPane, gridPane);
    }
    
    public void createGridPane() {
        
        gridPane = new GridPane();
            gridPane.setPadding(new Insets(0,0,0,25));
        
        Text release = new Text("Released: ");
            release.setFill(Color.web("#F1E14B"));
            release.setTextAlignment(TextAlignment.RIGHT);
            release.setFont(smallFont);
            
        Text publisher = new Text("Publisher: ");
            publisher.setFill(Color.web("#F1E14B"));
            publisher.setTextAlignment(TextAlignment.RIGHT);
            publisher.setFont(smallFont);
            
        Text developer = new Text("Developer: ");
            developer.setFill(Color.web("#F1E14B"));
            developer.setTextAlignment(TextAlignment.RIGHT);
            developer.setFont(smallFont);
        
        Text genre = new Text("Genre: ");
            genre.setFill(Color.web("#F1E14B"));
            genre.setTextAlignment(TextAlignment.RIGHT);
            genre.setFont(smallFont);
            
        Text platform = new Text("Platform: ");
            platform.setFill(Color.web("#F1E14B"));
            platform.setTextAlignment(TextAlignment.RIGHT);
            platform.setFont(smallFont);
            
        Text id = new Text("ID Number: ");
            id.setFill(Color.web("#F1E14B"));
            id.setTextAlignment(TextAlignment.RIGHT);
            id.setFont(smallFont);
            
        Text status = new Text("Status: ");
            status.setFill(Color.web("#F1E14B"));
            status.setTextAlignment(TextAlignment.RIGHT);
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
                    
        Text release2 = new Text(game.getRelease());
            release2.setFill(Color.WHITE);
            release2.setTextAlignment(TextAlignment.LEFT);
            release2.setFont(smallFont);
            
        Text publisher2 = new Text(game.getPublisher());
            publisher2.setFill(Color.WHITE);
            publisher2.setTextAlignment(TextAlignment.LEFT);
            publisher2.setFont(smallFont);
            
        Text developer2 = new Text(game.getDeveloper());
            developer2.setFill(Color.WHITE);
            developer2.setTextAlignment(TextAlignment.LEFT);
            developer2.setFont(smallFont);
        
        Text genre2 = new Text(game.getGenre());
            genre2.setFill(Color.WHITE);
            genre2.setTextAlignment(TextAlignment.LEFT);
            genre2.setFont(smallFont);
            
        Text platform2 = new Text(game.getPlatform());
            platform2.setFill(Color.WHITE);
            platform2.setTextAlignment(TextAlignment.LEFT);
            platform2.setFont(smallFont);
            
        Text id2 = new Text(game.getID() + "");
            id2.setFill(Color.WHITE);
            id2.setTextAlignment(TextAlignment.LEFT);
            id2.setFont(smallFont);
            
        Text status2 = new Text(game.getStatus());
            status2.setFill(Color.WHITE);
            status2.setTextAlignment(TextAlignment.LEFT);
            status2.setFont(smallFont);
        
        gridPane.add(release2, 1, 0);
        gridPane.add(publisher2, 1, 1);
        gridPane.add(developer2, 1, 2);
        gridPane.add(genre2, 1, 3);
        gridPane.add(platform2, 1, 4);
        gridPane.add(id2, 1, 5);
        gridPane.add(status2, 1, 6);  
        
    }
    
    public void createGameImage() {
        gameImage = new ImagePane(game.getPath(), 225, 300);
    }
   
}
