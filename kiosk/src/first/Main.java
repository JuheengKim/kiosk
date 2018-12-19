package first;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

	public void start(Stage stage) throws IOException {
		
		//sound
		Media sound = new Media(new File("resource/sound/main.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		
		Font.loadFont(getClass().getResourceAsStream("bmdohyeon.ttf"), 14);
		Parent root = FXMLLoader.load(getClass().getResource("checkIn.fxml"));
		stage.setScene(new Scene(root));
		stage.setTitle("chanel Hotel Kiosk");
		stage.show();
		stage.setResizable(false);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
