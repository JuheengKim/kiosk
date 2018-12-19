package second;

import java.io.File;
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class CompleteController {
	
	Parent root;
	Stage primaryStage;
	Scene scene;
	String phoneNum;
	
	@FXML private Pane pane;
	@FXML private Label prt_phoneNum;
	@FXML private Label message;
	
	public void initVariable(String phoneNum, int type) {
		//sound
		Media sound = new Media(new File("resource/sound/complete.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		
		if (type == 0) {
			message.setText("완료 되었습니다. 카드를 빼 주세요!");
		} else {
			message.setText("완료 되었습니다.");
		}
		
		prt_phoneNum.setText(phoneNum);
		
    	Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent event) {
         	   try {
         		   
         		   	//sound
         			Media sound = new Media(new File("resource/sound/main.mp3").toURI().toString());
         			MediaPlayer mediaPlayer = new MediaPlayer(sound);
         			mediaPlayer.play();
         		   
         			primaryStage = (Stage)pane.getScene().getWindow(); // 현재 윈도우 가져오기
         			
         			FXMLLoader loader = new FXMLLoader(getClass().getResource("/first/checkIn.fxml"));
         			root = loader.load();
         		    Scene scene = new Scene(root);
         		     
         		    primaryStage.setScene(scene);
         		    primaryStage.show();
         		                       
         	   } catch (IOException e) {
					e.printStackTrace();
         	   } 
            }
        });
        new Thread(sleeper).start();
    	
    }
	
	@FXML
	void initialize() {}
}
