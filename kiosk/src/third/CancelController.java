package third;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import first.CheckInController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.ConnectionUtil;
import util.SoundEffectUtil;
import variables.Payment;
import variables.Reservation;
import variables.ReservationDetail;
import variables.Room;
import variables.SmartKey;
import variables.User;

public class CancelController {
	
	Parent root;
	Stage primaryStage;
	Scene scene;
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	Payment p;
	User user;
	Reservation reser;
	ReservationDetail d;
	Room room;
	SmartKey k;
	DecimalFormat formatter; 
	
	@FXML private TextField NumberTF;
	@FXML private Pane pane;
	@FXML private TextField key;
	@FXML private boolean start = true;
	@FXML private Label uname;
	@FXML private Label indate;
	@FXML private Label outdate;
	@FXML private Label roomNum;
	@FXML private Label ptype;
	@FXML private Label tot;
	
	public CancelController() {
		Font.loadFont(CheckInController.class.getResource("/first/bm-Regular.ttf").toExternalForm(), 10);		
		connection = ConnectionUtil.connectdb();
		formatter = new DecimalFormat("###,###");
	}
	
	public void initVariable(User user, Reservation reser, ReservationDetail d, Payment p) throws ParseException {
     	this.user = user;
        this.reser = reser;
        this.d = d;
        this.p = p;
        
        //���� ����
        if (user != null) {
        	uname.setText(user.getName());
        }
        if (reser != null) {
        	Date date = new SimpleDateFormat("yyMMddHHmmss").parse(reser.getCheckinDate());
        	Date date2 = new SimpleDateFormat("yyMMddHHmmss").parse(reser.getCheckoutDate());
        	indate.setText(new SimpleDateFormat("yy-MM-dd").format(date));
        	outdate.setText(new SimpleDateFormat("yy-MM-dd").format(date2));
        }
        if (d != null) {
        	roomNum.setText(String.valueOf(d.getRoomNum()));
        }
        if (p != null) {
        	String type = (p.getPayType() == 0) ? "ī��": "����";
        	ptype.setText(type);
        	String tot_ = (p.getPayType() == 0) ? formatter.format(p.getPaidCard()): formatter.format(p.getPaidCash());
        	tot.setText(tot_ + " ��");
        }
     }
	
	/** �ڷΰ��� ��ư **/
	public void backMain() throws IOException {
		SoundEffectUtil.sound();
		Stage primaryStage = (Stage)pane.getScene().getWindow(); // ���� ������ ��������
		Parent root = null;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/first/checkIn.fxml"));
		root = loader.load();
	
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
	
	/** ���� ��ư ���� �� **/
    @FXML
    public void processNum(ActionEvent event) {
    	SoundEffectUtil.sound();
        if(start){
        	key.setText("");
            start = false;
        }
        String value = ((Button)event.getSource()).getText();
        key.setText(key.getText() + value);
    }
    
    @FXML
    public void eraseNumber(ActionEvent event) {
    	SoundEffectUtil.sound();
    	String a = key.getText(); 
    	if(a.length() != 0){ 
    		key.setText(a.substring(0,a.length()-1)); 
    	}  
    }
    
    @FXML
    public void resetNumbers(ActionEvent event) {
    	SoundEffectUtil.sound();
    	key.setText("");
        start = false;
    }
    
    public void showReserInfo() throws IOException, SQLException, ParseException {
		 SoundEffectUtil.sound();
		 
		 //Ű ����
		 String sql = "SELECT keyPasswd, rno, uno FROM smartkey WHERE keyPasswd = ?";      
 		
 		 preparedStatement = connection.prepareStatement(sql);
 		 preparedStatement.setString(1, key.getText());
 		 resultSet = preparedStatement.executeQuery();
	     
 		 if (!resultSet.isBeforeFirst()) {
 			key.setText("");
 			Alert alert = new Alert(AlertType.ERROR, "�������� �ʴ� ����Ʈ Ű �����Դϴ�.", ButtonType.YES);
 	    	alert.showAndWait();
 	    	return;
 		 }
 		 
	     while(resultSet.next()){
	    	 k = new SmartKey(resultSet.getInt("keyPasswd"), resultSet.getInt("rno"), resultSet.getInt("uno"));
	     }
	     
	     //���� ����
	     sql = "SELECT rno, uno, accomPeriod, numberOf, status, checkinDate, checkoutDate, paymentNum FROM reservation WHERE rno = ?";      
	 		
 		 preparedStatement = connection.prepareStatement(sql);
 		 preparedStatement.setInt(1, k.getRno());
 		 resultSet = preparedStatement.executeQuery();
	       
	     while(resultSet.next()){
	    	 reser = new Reservation(resultSet.getInt("uno"), resultSet.getInt("accomPeriod"), resultSet.getInt("numberOf"), resultSet.getInt("status"), 
	    			 resultSet.getString("checkinDate"), resultSet.getString("checkoutDate"), resultSet.getInt("paymentNum")); 
	     }
	     reser.setRno(k.getRno());
	     
	     //���� ���� üũ
	     if (reser.getStatus() != 0) {
	    	 	key.setText("");
	 			Alert alert = new Alert(AlertType.ERROR, "����� �� �����ϴ�. �����ڿ��� ���� �ϼ���.", ButtonType.YES);
	 	    	alert.showAndWait();
	 	    	return;
	 	 }
	     
	     //���� �� ����
	     sql = "SELECT roomNum, roomType, unitPrice, subTotal FROM reservationdetail WHERE rno = ?";      
	 		
 		 preparedStatement = connection.prepareStatement(sql);
 		 preparedStatement.setInt(1, reser.getRno());
 		 resultSet = preparedStatement.executeQuery();
	       
	     while(resultSet.next()){
	    	 d = new ReservationDetail(reser.getRno(), resultSet.getInt("roomNum"), resultSet.getInt("roomType"), resultSet.getInt("unitPrice"), resultSet.getInt("subTotal"));
	     }
	     
	     //���� ����
	     sql = "SELECT pno, authorized, payType, cardNum, cardExpDate, cardCompany, paidCard, paidCash, changeCash FROM payment WHERE pno = ?";      
	 		
 		 preparedStatement = connection.prepareStatement(sql);
 		 preparedStatement.setInt(1, reser.getPaymentNum());
 		 resultSet = preparedStatement.executeQuery();
	       
	     while(resultSet.next()){
	    	 //����
	    	 if (resultSet.getInt("payType") == 1)
	    		p = new Payment(resultSet.getInt("payType"), resultSet.getInt("pno"), resultSet.getInt("authorized"), resultSet.getInt("paidCash"), resultSet.getInt("changeCash"));
	    	 //ī��
	    	 else
	    		p = new Payment(resultSet.getInt("payType"), resultSet.getInt("pno"), resultSet.getInt("authorized"), resultSet.getInt("cardNum"), 
	    				resultSet.getString("cardExpDate"), resultSet.getString("cardCompany"), resultSet.getInt("paidCard"));
	     }
	     
	     //���� ����
	     sql = "SELECT uname, utel, jumin FROM user WHERE uno = ?";      
	 		
 		 preparedStatement = connection.prepareStatement(sql);
 		 preparedStatement.setInt(1, reser.getUno());
 		 resultSet = preparedStatement.executeQuery();
	       
	     while(resultSet.next()){
	    	 user = new User(reser.getUno(), resultSet.getString("jumin"), resultSet.getString("uname"), resultSet.getString("utel"));
	     }
	     
	     //ȭ�� �̵�
	     primaryStage = (Stage)pane.getScene().getWindow(); // ���� ������ ��������
	     FXMLLoader loader = new FXMLLoader(getClass().getResource("reserInfo.fxml"));
		 root = loader.load();
		   
		 CancelController controller = loader.<CancelController>getController();
		 controller.initVariable(user, reser, d, p);
		 Scene scene = new Scene(root);
		     
		 primaryStage.setScene(scene);
		 primaryStage.show(); 
	     
    }
    
    public void cancel() throws SQLException, IOException {
    	SoundEffectUtil.sound();
    	//sound
		Media sound = new Media(new File("resource/sound/cancel.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		
    	Alert alert = new Alert(AlertType.CONFIRMATION, "���� ����Ͻðڽ��ϱ�?", ButtonType.YES, ButtonType.CANCEL);
    	alert.showAndWait();

    	if (alert.getResult() == ButtonType.YES) {
    		//�� ��й�ȣ �ʱ�ȭ
    	    String sql = "update room set keyPasswd=? where roomNum=" + d.getRoomNum();
    	    preparedStatement = connection.prepareStatement(sql);
    	    preparedStatement.setInt(1, 0);
    	    preparedStatement.executeUpdate();
        	
        	//���� ����
    	    sql = "update reservation set status=? where rno=" + reser.getRno();
    	    preparedStatement = connection.prepareStatement(sql);
    	    preparedStatement.setInt(1, 1);
    	    preparedStatement.executeUpdate();
    	    
    	    
    	    //sound
    		sound = new Media(new File("resource/sound/complete_c.mp3").toURI().toString());
    		mediaPlayer = new MediaPlayer(sound);
    		mediaPlayer.play();
    		
    		Stage primaryStage = (Stage)pane.getScene().getWindow(); // ���� ������ ��������
    		Parent root = null;

    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/first/checkIn.fxml"));
    		root = loader.load();
    		
    	    Scene scene = new Scene(root);
    	    primaryStage.setScene(scene);
    	    primaryStage.centerOnScreen();
    	    primaryStage.show();
    	}
    }
    
}
