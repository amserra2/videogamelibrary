package Necessities;

import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ImagePane extends StackPane {
    private Image image;
    private ImageView imageView;
    private int width;
    private int height;
    
    public ImagePane(String picture, int w, int h) {
        image = new Image (new File(picture).toURI().toString());
        width = w;
        height = h;
        imageView = new ImageView(image);
        imageViewSettings(width,height);
    }
    
    private void imageViewSettings(int width, int height) {
        imageView.setCache(true);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        this.getChildren().add(imageView);
        ImagePane.setAlignment(imageView, Pos.CENTER);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getImagePaneWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getImagePaneHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    
    
}
