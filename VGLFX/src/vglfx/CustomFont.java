package vglfx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.text.Font;

public class CustomFont {
    private Font font; 
    private String fontFile;
    private int size;

    
    public CustomFont(String f, int size) {
        fontFile = f;
        this.size = size;
        font = generateFont(fontFile, size);
    }
    
    private Font generateFont(String s, int size) {
        Font f; 
        
        try{
            f = Font.loadFont(new FileInputStream(new File(s)), size);
        }
        catch (FileNotFoundException ex) {
            f = new Font("Arial", size);
        }
        
        return f;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(String f, int size) {
        font = generateFont(f, size);
    }
    
    public void setFontFile(String s) {
        fontFile = s;
        font = generateFont(fontFile, size);
    }
    
    public String getFontFile() {
        return fontFile;
    }
    
    public void setFontSize(int s) {
        size = s;
        font = generateFont(fontFile, size);
    }
    
    public int getFontSize() {
        return size;
    }
}
