package vglfx;

import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ButtonImagePane extends StackPane {
    private Image image;
    private ImageView imageView;
    private Button button;
    private final int width;
    private final int height;
    
    protected ButtonImagePane(String picture, int width, int height) {
        image = new Image (new File(picture).toURI().toString());
        imageView = new ImageView(image);
        this.width = width;
        this.height = height;
        imageViewSettings();
    }
    
    private void imageViewSettings() {
        imageView.setCache(true);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        createButton();
        this.getChildren().addAll(imageView, button);
        ButtonImagePane.setAlignment(imageView, Pos.CENTER);
    }
    
    private void createButton() {
        button = new Button();
            button.setPrefWidth(width);
            button.setPrefHeight(height);
            button.setStyle("-fx-background-color: transparent;");
            button.setAlignment(Pos.CENTER);
            button.setTextFill(Color.TRANSPARENT);
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

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
    
}
