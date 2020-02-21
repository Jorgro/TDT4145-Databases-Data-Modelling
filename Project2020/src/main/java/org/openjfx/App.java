package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    //private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        StackPane root = new StackPane();
        File file = new File("src/main/java/org/openjfx/ricardo.mp4");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("/Users/jorgenr/TDT4145/Project2020/src/main/java/org/openjfx/ricardo.mp4").toExternalForm()));
        MediaView mediaView = new MediaView(mediaPlayer);

        root.getChildren().add( mediaView);

        Scene scene = new Scene(root, 1024, 768);

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