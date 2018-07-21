package vglfx;

import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ImagePane extends StackPane {
    private Image image;
    private ImageView imageView;
    
    protected ImagePane(String picture, int width, int height) {
        image = new Image (new File(picture).toURI().toString());
        imageView = new ImageView(image);
        imageViewSettings(width,height);
    }
    
    private void imageViewSettings(int width, int height) {
        imageView.setCache(true);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        this.getChildren().add(imageView);
        this.setAlignment(imageView, Pos.CENTER);
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
    
}