package org.openjfx;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static float height = 1080;
    public static float width = 1920;

    public Text createText() {
        //Creating a Text object
        Text text = new Text();
        //Setting font to the text
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 150));

        //setting the position of the text
        text.setX(300);
        text.setY(200);
        text.setTextAlignment(TextAlignment.CENTER);

        //Setting the color
        text.setFill(Color.RED);

        //Setting the Stroke
        text.setStrokeWidth(2);

        // Setting the stroke color
        text.setStroke(Color.BLUE);

        //Setting the text to be added.
        text.setText("Movie database");
        return text;
    }

    public void addFlashingText(Text text) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), text);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    }

    //private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        Pane root = new Pane();


        File file = new File("src/main/java/org/openjfx/ricardo.mp4");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("/Users/jorgenr/TDT4145/Project2020/src/main/java/org/openjfx/ricardo.mp4").toExternalForm()));
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create flashing text:
        Text text = createText();
        addFlashingText(text);

        // Create input field:
        TextField textField = new TextField();
        textField.setMaxWidth(200);
        textField.setMaxHeight(20);
        textField.setLayoutX(width/2);
        textField.setLayoutY(height/2);

        root.getChildren().add( mediaView);
        root.getChildren().add(text);
        root.getChildren().add(textField);
        mediaPlayer.setCycleCount(10000);

        Scene scene = new Scene(root, width, height);

        stage.setScene(scene);
        stage.show();


        mediaPlayer.play();
        // scene = new Scene(loadFXML("primary"));
        // stage.setScene(scene);
       // stage.show();
    }

    //static void setRoot(String fxml) throws IOException {
    //    scene.setRoot(loadFXML(fxml));
    //}

    //private static Parent loadFXML(String fxml) throws IOException {
    //    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    //    return fxmlLoader.load();
    //}

    public static void main(String[] args) {
        DBConnector conn = new DBConnector();
        conn.connect();
        System.out.println("SUCCESS!");
        launch();
    }

}