package View;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PropertiesController {

    @FXML
    Label search;
    @FXML
    Label generate;
    @FXML
    Label size;


    @FXML
    private void initialize() {
        Scanner scanner;
        try {

            scanner = new Scanner(new File("resources/config.properties"));
            String date = scanner.nextLine();

            String threadPoolSize = scanner.nextLine();
            size.setText(threadPoolSize.substring(15));
            String SearchingAlgorithm = scanner.nextLine();
            search.setText(SearchingAlgorithm.substring(19));
            String GeneratorAlgorithm = scanner.nextLine();
            generate.setText(GeneratorAlgorithm.substring(14));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
