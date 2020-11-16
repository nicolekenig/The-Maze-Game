package View;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AboutController {

    @FXML
    ImageView imageAbout1;
    @FXML
    ImageView imageAbout2;
    @FXML
    ImageView imageAbout3;
    @FXML
    ImageView imageAbout4;
    @FXML
    ImageView imageAbout5;

    @FXML
    private void initialize() {

        try {
            Image StartImage = new Image(new FileInputStream("resources/Images/goal.jpg"));
            imageAbout1.setImage(StartImage);
            imageAbout2.setImage(StartImage);
            imageAbout3.setImage(StartImage);
            imageAbout4.setImage(StartImage);
            imageAbout5.setImage(StartImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
