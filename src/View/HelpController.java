package View;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HelpController {

    @FXML
    ImageView imageHelp;

    @FXML
    private void initialize() {

        try {
            Image StartImage = new Image(new FileInputStream("resources/Images/fox2.jpg"));
            imageHelp.setImage(StartImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
