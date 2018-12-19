package manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import first.CheckInController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.ConnectionUtil;
import util.SoundEffectUtil;

public class LoginController {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String peopleNum_ = null;
	
	@FXML
	private TextField id;
	@FXML
	private TextField pw;
	@FXML
	private Pane pane;

	public LoginController()  {
    	Font.loadFont(CheckInController.class.getResource("/first/bm-Regular.ttf").toExternalForm(), 10);
    	connection = ConnectionUtil.connectdb();
    	if (pane != null) pane.getChildren().add(new FullCalendarView(YearMonth.now()).getView()) ;
    			
    }
	
	/** 로그인 화면에서 뒤로가기 버튼 **/
	public void backMain() throws IOException {
		SoundEffectUtil.sound();
		Stage primaryStage = (Stage)pane.getScene().getWindow(); // 현재 윈도우 가져오기
		Parent root = null;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/first/checkIn.fxml"));
		root = loader.load();
	
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
	
	/** 로그인 버튼 
	 * @throws SQLException 
	 * @throws IOException **/
	public void login(ActionEvent event) throws SQLException, IOException {
		
		SoundEffectUtil.sound();
		Stage primaryStage = (Stage)id.getScene().getWindow(); // 현재 윈도우 가져오기
		Parent root = null;
		
		int count = 0;
		
		connection = ConnectionUtil.connectdb();
		String sql = "SELECT * from manager WHERE mID = ? AND mPW = ?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, id.getText());
		preparedStatement.setString(2, pw.getText());
		resultSet = preparedStatement.executeQuery();

		if(resultSet.next()) {
		    count++;
		}
		
		//로그인 성공시
		if (count> 0) {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("roomlist.fxml"));
	 		 root = loader.load();
	 		
	 	     Scene scene = new Scene(root);    
	 	     primaryStage.setScene(scene);
	 	     primaryStage.show();
		}
	}
}
