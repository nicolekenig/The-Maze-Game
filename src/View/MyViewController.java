package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.misc.Cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class MyViewController implements IView,Observer {

    private MyViewModel viewModel;
    @FXML
    public TextField textField_mazeRows;
    @FXML
    public TextField textField_mazeColumns;
    @FXML
    public MazeDisplayer mazeDisplayer;
    @FXML
    public CharacterDisplayer characterDisplayer;
    @FXML
    public SolutionDisplayer solutionDisplayer;
    @FXML
    public Label lbl_player_row;
    @FXML
    public Label lbl_player_col;
    @FXML
    public javafx.scene.control.Button btn_generateMaze;
    @FXML
    public javafx.scene.control.Button btn_SolveMaze;


    @FXML
    public MediaPlayer music;
    @FXML
    public ImageView imageView;
    //    @FXML
//    public Pane Pane;


    @FXML
    private void initialize(){
        btn_SolveMaze.setDisable(true);
        Media musicFile = new Media(new File("resources/Music/CCRedAlert2Music.mp3").toURI().toString());
        music = new MediaPlayer(musicFile);
        music.setCycleCount(3);
        music.play();
        try {
            Image StartImage = new Image(new FileInputStream("resources/Images/startImage.jpeg"));
            imageView.setImage(StartImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public StringProperty characterPositionRowBind = new SimpleStringProperty(); //For Binding
    public StringProperty characterPositionColumnBind = new SimpleStringProperty();//For Binding

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        bindProperties(viewModel);
    }

    private void bindProperties(MyViewModel viewModel) {
        lbl_player_row.textProperty().bind(viewModel.characterRowPositionToUpdate);
        lbl_player_col.textProperty().bind(viewModel.characterColPositionToUpdate);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            displayMaze(viewModel.getMaze());
        }
    }

    @Override
    public void displayMaze(Maze maze) {
        mazeDisplayer.setMaze(maze);
        mazeDisplayer.requestFocus();
        displayerCharacter(maze);
        solutionDisplayer.clear();

    }

    public void generateMaze() {
        int row = Integer.valueOf(textField_mazeRows.getText());
        int col = Integer.valueOf(textField_mazeColumns.getText());
        if(row <=2 || col <=2)
            showAlert("Message","Invalid maze size.\nPlease enter new row number and column number bigger then 2.");
        else{
//            Pane.setVisible(false);
            imageView.setVisible(false);
            mazeDisplayer.setVisible(true);
            characterDisplayer.setVisible(true);
            btn_SolveMaze.setDisable(false);
            viewModel.generateMaze(row, col);
            displayMaze(viewModel.getMaze());
        }
    }

    public void solveMaze(){
        showAlert("Solving Maze","Solving Maze ...");
        displaySolution(viewModel.getSolution());
        characterDisplayer.setVisible(true);
//        displayerCharacter(viewModel.getMaze());
    }

    public void displaySolution(Solution solution){
        Maze maze = viewModel.getMaze();
        solutionDisplayer.setMaze(maze.getRow(),maze.getCol());
        solutionDisplayer.setSolution(solution);
        displayerCharacter(maze);
    }

    public void displayerCharacter(Maze maze){
        characterDisplayer.setVisible(true);
        characterDisplayer.requestFocus();
        int characterPositionRow = viewModel.getCharacterRowPosition();
        int characterPositionColumn = viewModel.getCharacterColPosition();
        characterDisplayer.setMaze(maze.getMaze().length, maze.getMaze()[0].length);
        characterDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        this.characterPositionRowBind.set(characterPositionRow + "");
        this.characterPositionColumnBind.set(characterPositionColumn + "");
    }

    public void showAlert(String Titlemessage , String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Titlemessage);
        alert.setContentText(message);
        alert.show();
    }

    public void keyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode());
        if(viewModel.checkIfArraveToEndingPoint()) {
            try {
                music.pause();
                Media musicFile = new Media(new File("resources/Music/Queen-WeAreTheChampions.mp3").toURI().toString());
                music = new MediaPlayer(musicFile);
                music.play();
                Image winImage = new Image(new FileInputStream("resources/Images/winImage.jpeg"));
                imageView.setImage(winImage);
                mazeDisplayer.setVisible(false);
                characterDisplayer.setVisible(false);
                btn_SolveMaze.setDisable(true);
//                Pane.setVisible(true);
                imageView.setVisible(true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        keyEvent.consume();
    }

    public void zoom(ScrollEvent scrollEvent){
            this.mazeDisplayer.zoom(scrollEvent);
            this.characterDisplayer.zoom(scrollEvent);
        if(scrollEvent.getSource().equals(this.solutionDisplayer)){
            this.solutionDisplayer.zoom(scrollEvent);
        }
        scrollEvent.consume();
    }

    public void playMusic(){
        music.play();
    }

    public void muteMusic(){
        music.pause();
    }

    public void setResizeEvent(Scene scene) {
        long width = 0;
        long height = 0;
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width: " + newSceneWidth);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                System.out.println("Height: " + newSceneHeight);
            }
        });
    }

    public void About(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("About Controller");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 580, 500);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
        }

    }

    public void save(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Maze");
        fileChooser.setInitialDirectory(new File("./resources/Mazes")); // default location to save the maze.
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("text files","*.txt"));
        File fileToSave = fileChooser.showSaveDialog(null); // ownerWindow: where the user chose to save the maze .
//        File fileToSave = fileChooser.showSaveDialog((Stage)mazeDisplayer.getScene().getWindow());
        if(fileToSave != null)
        {
//            fileToSave = new File(fileToSave.getAbsoluteFile()+".txt"); //the user chose to save the maze not in the default directory
            viewModel.save(fileToSave);
            System.out.println("maze saved");
        }
    }

    public void load(){
        btn_SolveMaze.setDisable(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Maze");
        fileChooser.setInitialDirectory(new File("./resources/Mazes")); //FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("text files(*.txt)",".txt");  // ExtensionFilter: the type of file the user can upload
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("text files","*.txt"));
        File file = fileChooser.showOpenDialog(null); // ownerWindow: where the user chose to load the maze from .
//        File file = fileChooser.showOpenDialog((Stage)mazeDisplayer.getScene().getWindow());
        if(file != null) {
            viewModel.load(file); //the user chose to load a maze not from the default directory
            System.out.println("maze load");
            generateLoadMaze();
//            displayMaze(viewModel.getMaze());
        }

    }

    public void generateLoadMaze() {
        Maze maze = viewModel.getMaze();
        setTextField_mazeRows(new TextField(String.valueOf(maze.getRow())));
        setTextField_mazeColumns(new TextField(String.valueOf(maze.getCol())));
//        Pane.setVisible(false);
        imageView.setVisible(false);
//        mazeDisplayer.setVisible(true);
        characterDisplayer.setVisible(true);
        displayMaze(maze);

    }

    public void New() {
//        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
//        ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
//        Alert alert = new Alert(Alert.AlertType.WARNING,"Generate new Maze?\nYou might lose your current maze.",yes,no);
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get().equals("yes")){
////            clear();
//            btn_SolveMaze.setDisable(true);
//            mazeDisplayer.setVisible(false);
//            characterDisplayer.setVisible(false);
//            solutionDisplayer.setVisible(false);
//            Pane.setVisible(true);
//            btn_generateMaze.setDisable(true);
//    }
        generateMaze();

    }


//    private void clear(){
//        if(viewModel.getMaze() != null){
//            mazeDisplayer.clear();
//            characterDisplayer.clear();
//            solutionDisplayer.clear();
//        }
//    }


    public void properties(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Properties Controller");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Properties.fxml").openStream());
            Scene scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }

    }

    public void help(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Help Controller");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("help.fxml").openStream());
            Scene scene = new Scene(root, 700, 700);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }

    }

    public void exit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
            viewModel.exit();
        }
    }

    public void setCharacterPositionRowBind(String characterPositionRowBind) {
        this.characterPositionRowBind.set(characterPositionRowBind);
    }

    public void setCharacterPositionColumnBind(String characterPositionColumnBind) {
        this.characterPositionColumnBind.set(characterPositionColumnBind);
    }

    public String getCharacterPositionRowBind() {
        return characterPositionRowBind.get();
    }

    public StringProperty characterPositionRowBindProperty() {
        return characterPositionRowBind;
    }

    public String getCharacterPositionColumnBind() {
        return characterPositionColumnBind.get();
    }

    public StringProperty characterPositionColumnBindProperty() {
        return characterPositionColumnBind;
    }


    public TextField getTextField_mazeRows() {
        return textField_mazeRows;
    }

    public void setTextField_mazeRows(TextField textField_mazeRows) {
        this.textField_mazeRows = textField_mazeRows;
    }

    public TextField getTextField_mazeColumns() {
        return textField_mazeColumns;
    }

    public void setTextField_mazeColumns(TextField textField_mazeColumns) {
        this.textField_mazeColumns = textField_mazeColumns;
    }

    public Label getLbl_player_row() {
        return lbl_player_row;
    }

    public void setLbl_player_row(Label lbl_player_row) {
        this.lbl_player_row = lbl_player_row;
    }

    public Label getLbl_player_col() {
        return lbl_player_col;
    }

    public void setLbl_player_col(Label lbl_player_col) {
        this.lbl_player_col = lbl_player_col;
    }

}
