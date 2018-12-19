package second;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import first.CheckInController;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

/** 결제 **/
public class PaymentController {
	
	Parent root;
	Stage primaryStage;
	Scene scene;
	DecimalFormat formatter;
	SimpleDateFormat df;
	int type; 						/** 0: card, 1: cash **/
	
	Payment p = new Payment();
	User user;
	Reservation reser;
	ReservationDetail d;
	Room room;
	SmartKey k;
	
	@FXML private Pane pane;
	@FXML private Pane cardinputPane;
	@FXML private Pane reqpay_cardPan;
	@FXML private Label prt_phoneNum;
	@FXML private Label req_pay;
	
	//디비
	Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String sql = "";
    
    public PaymentController()  {
    	Font.loadFont(CheckInController.class.getResource("/first/bm-Regular.ttf").toExternalForm(), 10);
    	connection = ConnectionUtil.connectdb();
    	formatter = new DecimalFormat("###,###");
    }   
    
    public void initVariable(User user, Reservation reser, ReservationDetail d, Room room, int type) {
    	this.user = user;
        this.reser = reser;
        this.d = d;
        this.room = room;
        this.type = type;
    }
    
    /** 결제 방식 선택 화면 **/
    
  //카드 결제
    @FXML
    public void selCard(ActionEvent event) throws IOException {
    	
    	SoundEffectUtil.sound();
    	
    	//sound
		Media sound = new Media(new File("resource/sound/cardinpu.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	    
	    primaryStage = (Stage)pane.getScene().getWindow(); // 현재 윈도우 가져오기
		root = null;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CardInput.fxml"));
		root = loader.load();
		
		PaymentController controller = loader.<PaymentController>getController();
		controller.initVariable(user, reser, d, room, 0);
		
	     Scene scene = new Scene(root);
	     
	     primaryStage.setScene(scene);
	     primaryStage.show();
    }
    
    //현금 결제
    @FXML
    public void selCash(ActionEvent event) throws IOException {
    	
    	SoundEffectUtil.sound();
    	//sound
		Media sound = new Media(new File("resource/sound/cashinpu.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	    
	    primaryStage = (Stage)pane.getScene().getWindow(); // 현재 윈도우 가져오기
		root = null;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CashInput.fxml"));
		root = loader.load();
		
		PaymentController controller = loader.<PaymentController>getController();
		controller.initVariable(user, reser, d, room, 1);
		
	     Scene scene = new Scene(root);
	     
	     primaryStage.setScene(scene);
	     primaryStage.show();
    }
    
    public void backRoomList() throws IOException {
		 SoundEffectUtil.sound();
		 primaryStage = (Stage)pane.getScene().getWindow(); // 현재 윈도우 가져오기
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomList.fxml"));
		 root = loader.load();
		
		 RoomListController controller = loader.<RoomListController>getController();
		 controller.initVariable(user, reser, d, room);
		
	     Scene scene = new Scene(root);
	     
	     primaryStage.setScene(scene);
	     primaryStage.show();
	}
    
    public void backSelPay() throws IOException {
		 SoundEffectUtil.sound();
		 
		 //sound
		 Media sound = new Media(new File("resource/sound/payfun.mp3").toURI().toString());
		 MediaPlayer mediaPlayer = new MediaPlayer(sound);
		 mediaPlayer.play();
		 
		 primaryStage = (Stage)pane.getScene().getWindow(); // 현재 윈도우 가져오기
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectPayment.fxml"));
		 root = loader.load();
		
		 PaymentController controller = loader.<PaymentController>getController();
		 controller.initVariable(user, reser, d, room, type);
		
	     Scene scene = new Scene(root);
	     
	     primaryStage.setScene(scene);
	     primaryStage.show();
	}
    
    /** 카드 투입 요청 화면 **/
    @FXML
    public void requestPay(ActionEvent event) throws IOException {
    	//sound
		Media sound = new Media(new File("resource/sound/ing.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	    
	    primaryStage = (Stage)pane.getScene().getWindow(); // 현재 윈도우 가져오기
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RequestPay.fxml"));
		root = loader.load();
		
		PaymentController controller = loader.<PaymentController>getController();
		controller.initVariable(user, reser, d, room, type);
	    Scene scene = new Scene(root);
	     
	    primaryStage.setScene(scene);
	    primaryStage.show();
    	
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
            @SuppressWarnings({ "static-access"})
			public void handle(WorkerStateEvent event) {
         	   try {
         		   
         		   if (type == 0) {
         			   //카드 결제 정보 INSERT         		   
	         		   sql = "INSERT INTO payment (authorized, payType, cardNum, cardExpDate, cardCompany, paidCard) "
	         		   		+ "VALUES (?, ?, ?, ?, ?, ?)"; 
	         		  	         
	         		   preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS); 
	         		   preparedStatement.setInt(1, 0);
	         		   preparedStatement.setInt(2, type);
	         		   preparedStatement.setInt(3, 123456789);
	         		   preparedStatement.setInt(4, 1908);
	         		   preparedStatement.setString(5, "KB국민은행");
	         		   preparedStatement.setInt(6, d.getSubTotal());
         		   } else {
         			   //현금 결제 정보 INSERT    
         			   System.out.println("여기오낭");
	         		   sql = "INSERT INTO payment (authorized, payType, paidCash, changeCash) "
	         		   		+ "VALUES (?, ?, ?, ?)"; 
	         		  	         
	         		   preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS); 
	         		   preparedStatement.setInt(1, 0);
	         		   preparedStatement.setInt(2, type);
	         		   preparedStatement.setInt(3, 50000);
	         		   preparedStatement.setInt(4, 30000);
         		   }
         		   preparedStatement.executeUpdate();
         		   
         		   resultSet = preparedStatement.getGeneratedKeys();
         		   
         		   if (resultSet.next()) {
         			   p.setPno(resultSet.getInt(1));
         		   }
         		   
         		   System.out.println(p.getPno());
         		   //체크인 날짜와 체크아웃 날짜 생성
         		  Calendar cal = Calendar.getInstance();
         		  cal.setTime(new Date());
         		  df = new SimpleDateFormat ( "yyMMddHHmmss", Locale.KOREA );
         		  String checkin = df.format(cal.getTime());
         		 
         		  cal.add(Calendar.DATE, reser.getAccomPeriod());
         		  String checkout = df.format(cal.getTime());
         		           		   System.out.println("체크인 " + checkin + "  , 체크아웃 " + checkout );
         		           		  
         		   //예약 정보 INSERT
         		   sql = "INSERT INTO reservation (uno, accomPeriod, numberOf, status, checkinDate, checkoutDate, paymentNum) "
           		   		+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
         		  
         		   preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);
         		   preparedStatement.setInt(1, user.getUserNum());
        		   preparedStatement.setInt(2, reser.getAccomPeriod());
        		   preparedStatement.setInt(3, reser.getNumberOf());
        		   preparedStatement.setInt(4, 0);
        		   preparedStatement.setString(5, checkin);
        		   preparedStatement.setString(6, checkout);
        		   preparedStatement.setInt(7, p.getPno());
        		   preparedStatement.executeUpdate();
        		   resultSet = preparedStatement.getGeneratedKeys();
        		   if (resultSet.next()) {
        			   reser.setRno(resultSet.getInt(1));
        		   }
        		   
         		   //예약 상세 정보 INSERT
        		   sql = "INSERT INTO reservationdetail (rno, roomNum, roomType, unitPrice, subTotal) "
              		   		+ "VALUES (?, ?, ?, ?, ?)";
        		   preparedStatement = connection.prepareStatement(sql);
        		   preparedStatement.setInt(1, reser.getRno());
        		   preparedStatement.setInt(2, room.getRoomNum());
        		   preparedStatement.setInt(3, room.getRoomType());
        		   preparedStatement.setInt(4, d.getUnitPrice());
        		   preparedStatement.setInt(5, d.getSubTotal());
        		   preparedStatement.executeUpdate();
        		  
        		   
        		   //스마트 키 정보 INSERT
        		   k = new SmartKey(createKeyPasswd(), reser.getRno(), 20);        
        		   sql = "INSERT INTO smartkey (keyPasswd, rno, uno) VALUES (?, ?, ?)";
        		   preparedStatement = connection.prepareStatement(sql);
        		   preparedStatement.setInt(1, k.getKeyPasswd());
        		   preparedStatement.setInt(2, k.getRno());
        		   preparedStatement.setInt(3, k.getUno());
        		   preparedStatement.executeUpdate();
        		   
         		   //룸 비밀번호 UPDATE
        		   sql = "update room set keyPasswd=? where roomNum=" + room.getRoomNum();
        		   preparedStatement = connection.prepareStatement(sql);
        		   preparedStatement.setInt(1, k.getKeyPasswd());
        		   preparedStatement.executeUpdate();
        		   
        		   //SMSUtil.sendSms(String.valueOf(k.getKeyPasswd()), "01099983072");
         		   
        		   root = null;
        		   FXMLLoader loader = new FXMLLoader(getClass().getResource("completeReser.fxml"));
        		   root = loader.load();
         		   
    			   CompleteController controller = loader.<CompleteController>getController();
         		   controller.initVariable(user.getUtel(), type);
         		   Scene scene = new Scene(root);
         		     
         		   primaryStage.setScene(scene);
         		   primaryStage.show(); 
        		   
         	   } catch (SQLException e) {
				e.printStackTrace();
			} /*catch (CoolsmsException e) {
				e.printStackTrace();
			}*/ catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }
        });
        new Thread(sleeper).start();
    }
    
    //스마트 키 생성
    int createKeyPasswd() {
        Random random = new Random(System.currentTimeMillis());
        
        int range = (int)Math.pow(10, 4);
        int trim = (int)Math.pow(10, 3);
        int result = random.nextInt(range)+trim;
         
        if(result>range){
            result = result - trim;
        }
        
        return result;
    }
  
   @FXML
   void initialize() {}
}
