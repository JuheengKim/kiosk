package first;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import second.RoomListController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.ConnectionUtil;
import util.SoundEffectUtil;
import variables.User;

public class CheckInController {
	
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	User user;
	String peopleNum_ = null;
	
	@FXML
	private TextField NumberTF;
	@FXML
	private TextField name;
	@FXML
	private TextField jumin;
	@FXML
	private TextField phoneNum;
	@FXML
	private boolean start = true;
	@FXML
	private Label peopleNum;
	@FXML
	private Label completeAdult;
	@FXML
	private Button checkin;
	
	private boolean phoneNum_switch = false;
		
	public CheckInController() {
		Font.loadFont(CheckInController.class.getResource("/first/bm-Regular.ttf").toExternalForm(), 10);
		
		connection = ConnectionUtil.connectdb();
		if (name != null) name.setText("전정국");
		if (jumin != null) jumin.setText("970901");
	}
	

	//관리자 모드 (로그인 화면) 
	public void managerLogin() throws IOException {
		 SoundEffectUtil.sound();
		 Stage primaryStage = (Stage)checkin.getScene().getWindow(); // 현재 윈도우 가져오기
		 Parent root = null;

		 FXMLLoader loader = new FXMLLoader(getClass().getResource("/manager/managerLogin.fxml"));
		 root = loader.load();
		
	     Scene scene = new Scene(root);
	     primaryStage.setScene(scene);
	     primaryStage.centerOnScreen();
	     primaryStage.show();
	}
	
	@FXML
	public void checkInclicked(ActionEvent e) throws IOException {
		SoundEffectUtil.sound();
       	//sound
		Media sound = new Media(new File("resource/sound/number.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		Parent selectNumber = FXMLLoader.load(getClass().getResource("selectNumber.fxml"));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(new Scene(selectNumber));
		stage.show();
	}
	
	/** 체크인 취소 버튼 **/
	@FXML
	public void cancelclicked(ActionEvent event) throws IOException {
		SoundEffectUtil.sound();
		//sound
		Media sound = new Media(new File("resource/sound/smtkinpu.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		
		Parent selectNumber = FXMLLoader.load(getClass().getResource("/third/inputInfo.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(selectNumber));
		stage.show();
	}

	
	@FXML
	public void backCheckIn(ActionEvent e) throws IOException {
		SoundEffectUtil.sound();
		Parent root = FXMLLoader.load(getClass().getResource("checkIn.fxml"));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	@FXML
	public void backSelectNumber(ActionEvent e) throws IOException {
		SoundEffectUtil.sound();
		Parent selectNumber = FXMLLoader.load(getClass().getResource("selectNumber.fxml"));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(new Scene(selectNumber));
		stage.show();
	}
	
	int i = 0;
	
	@FXML
	public void incrementNumber(ActionEvent e) {
		SoundEffectUtil.sound();
		i++;
		String tmp = Integer.toString(i);
		NumberTF.setText(tmp);
	}
	
	@FXML
	public void decrementNumber(ActionEvent e) {
		SoundEffectUtil.sound();
		i--;
		String tmp = Integer.toString(i);
		NumberTF.setText(tmp);
	}
	
	
	private void initVaribles(String num) {
		peopleNum_ = num; 
	    peopleNum.setText(peopleNum_);
	}
	
	@FXML
	public void adult(ActionEvent e) throws IOException, SQLException {
		
		if(Integer.parseInt(NumberTF.getText())<1)
		{
	          String alertStr = "숙박인원은 1명이상만 가능합니다!";
	          Alert alert = new Alert(AlertType.ERROR);
	          alert.setContentText(alertStr);
	          alert.showAndWait();
		}
		else
		{
			
			SoundEffectUtil.sound();
			//sound
			Media sound = new Media(new File("resource/sound/adult.mp3").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
			
			Stage primaryStage = (Stage)NumberTF.getScene().getWindow(); // 현재 윈도우 가져오기
			Parent root = null;
		  
			FXMLLoader loader = new FXMLLoader(getClass().getResource("adult.fxml"));
			root = loader.load();
		
			//결제 controller에 데이터 보내기
			CheckInController controller = loader.< CheckInController>getController();
			controller.initVaribles(NumberTF.getText());
	   
			Scene scene = new Scene(root);    
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
	}
	
	int j = 0;
	public void adultIncrement() {
		SoundEffectUtil.sound();
		if(j < Integer.parseInt(peopleNum.getText()))
		{
			j++;
		}
		else if(j==Integer.parseInt(peopleNum.getText()) )
		{
	          String alertStr = "다음 화면으로 넘어가세요!";
	          Alert alert = new Alert(AlertType.ERROR);
	          alert.setContentText(alertStr);
	          alert.showAndWait();
		}
		else
		{
			;
		}
			
			
		String ttt = Integer.toString(j);
		completeAdult.setText(ttt);
	}
	
	
	public void privateInfo(ActionEvent e) throws IOException {
		
		if(Integer.parseInt(completeAdult.getText())!=Integer.parseInt(peopleNum.getText()))
		{
	          String alertStr = "성인인증이 완료되지 않았습니다!";
	          Alert alert = new Alert(AlertType.ERROR);
	          alert.setContentText(alertStr);
	          alert.showAndWait();
			
		}
		else
		{
			SoundEffectUtil.sound();	
			//sound
			Media sound = new Media(new File("resource/sound/phone.mp3").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
			
			Parent selectNumber = FXMLLoader.load(getClass().getResource("privateInfo.fxml"));
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setScene(new Scene(selectNumber));
			stage.show();
	
		}
	
		
	}

	// phone
	/** 숫자 버튼 누를 때 **/
    @FXML
    public void processNum(ActionEvent event) {
    	SoundEffectUtil.sound();
        if(start){
        	phoneNum.setText("");
            start = false;
        }
        String value = ((Button)event.getSource()).getText();
        phoneNum.setText(phoneNum.getText() + value);
    }
    
    @FXML
    public void eraseNumber(ActionEvent event) {
    	SoundEffectUtil.sound();
    	String a = phoneNum.getText(); 
    	if(a.length() != 0){ 
    		phoneNum.setText(a.substring(0,a.length()-1)); 
    	}  
    }
    
    @FXML
    public void resetNumbers(ActionEvent event) {
    	SoundEffectUtil.sound();
    	phoneNum.setText("");
        start = false;
    }
	
	/** 확인 버튼 누를 때 
	 * @throws SQLException 
	 * @throws IOException **/
	public void confirmAction(ActionEvent event) throws SQLException, IOException {
		SoundEffectUtil.sound();
		//핸드폰번호 유효성
        if(!Pattern.matches("(010|011|016|017|018?019)(.+)(.{4})", phoneNum.getText()))
        {
            String alertStr = "유효하지 않은 번호입니다. 다시 입력하세요.";
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(phoneNum.getText());
            alert.setContentText(alertStr);
            alert.showAndWait();
            phoneNum.setText("");
            return;
        }
        
        phoneNum_switch = true;
	 }
	
	/** 확인 버튼 누를 때 
	 * @throws SQLException 
	 * @throws IOException **/
	public void roomList(ActionEvent event) throws SQLException, IOException {
		
		if(phoneNum_switch == false)
		{
            String alertStr = "휴대폰 번호를 입력하세요!";
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(alertStr);
            alert.showAndWait();
			
		}
		else
		{
			
			SoundEffectUtil.sound();
			Stage primaryStage = (Stage)name.getScene().getWindow(); // 현재 윈도우 가져오기
			Parent root = null;
			
		    //공백 체크
		    if(phoneNum.getText().length() == 0)
		    {
		          String alertStr = "연락처를 입력해주세요!!!";
		          Alert alert = new Alert(AlertType.INFORMATION);
		          alert.setHeaderText(phoneNum.getText());
		          alert.setContentText(alertStr);
		          alert.showAndWait();
		    } 
		    else 
		    {
		        
		        connection = ConnectionUtil.connectdb();
		        String sql = "INSERT INTO User (uname, utel, jumin) VALUES(?,?,?)";
		        
		        preparedStatement = connection.prepareStatement(sql);
		
		        preparedStatement.setString(1, name.getText());
		        preparedStatement.setString(3, jumin.getText());
		        preparedStatement.setString(2, phoneNum.getText());
		   
	 	       	int uno = preparedStatement.executeUpdate();
	 	       	
	 	       	//sound
	 			Media sound = new Media(new File("resource/sound/selroom.mp3").toURI().toString());
	 			MediaPlayer mediaPlayer = new MediaPlayer(sound);
	 			mediaPlayer.play();
		 		
		 		 FXMLLoader loader = new FXMLLoader(getClass().getResource("/second/RoomList.fxml"));
		 		 root = loader.load();
		 		 
		 		 user = new User(uno, jumin.getText(), name.getText(), phoneNum.getText());
		
		 		 //결제 controller에 데이터 보내기
		 		 RoomListController controller = loader.<RoomListController>getController();
		 		 controller.initVariable(user, null, null, null);
		 		
		 	     Scene scene = new Scene(root);    
		 	     primaryStage.setScene(scene);
		 	     primaryStage.show();
		 	     
		 	    
		    }
		}
	} 
}
