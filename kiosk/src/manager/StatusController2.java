package manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;

import first.CheckInController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.ConnectionUtil;
import util.SoundEffectUtil;
import variables.Reservation;
import variables.Room;

public class StatusController2 {
	
	@FXML
	private TableView<Reservation> table;
	@FXML
    private TableColumn<Reservation, Image> imageColumn;
    @FXML
    private TableColumn<Reservation, String> roomNumCol;
    @FXML
    private TableColumn<Reservation, String> periodCol;
    @FXML
    private TableColumn<Reservation, String> uNameCol;
    @FXML
    private TableColumn<Reservation, String> statusCol;
    @FXML
    private TableColumn<Reservation, String> numberOfCol;
    
    //방 리스트 담는 리스트
    private ObservableList<Reservation> oblist;
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	//방 등록 화면
	@FXML private Pane pane;
	
	private String filepath;
	Image image = null;
	int roomType = -1;
	
	public StatusController2()  {
    	Font.loadFont(CheckInController.class.getResource("/first/bm-Regular.ttf").toExternalForm(), 10);
    	connection = ConnectionUtil.connectdb();
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

    public void getReserList(int status) {
    	oblist = FXCollections.observableArrayList();
    	
    	try {
    		String sql = "SELECT a.accomPeriod as accomPeriod, a.numberOf as numberOf, a.status as status, a.checkinDate as checkinDate, a.checkoutDate as checkoutDate, b.roomNum as roomNum, c.uname as uname"
    				+ " FROM reservation a LEFT OUTER JOIN reservationdetail b ON a.rno = b.rno "
    				+ "LEFT OUTER JOIN user c ON a.uno = c.uno";      
    		
    		preparedStatement = connection.prepareStatement(sql);
    		resultSet = preparedStatement.executeQuery();

	       while(resultSet.next()){
	    	   
	    	   //데이터 넣기
	           oblist.add(new Reservation(String.valueOf(resultSet.getInt("accomPeriod")), String.valueOf(resultSet.getInt("numberOf")), String.valueOf(resultSet.getInt("status")), 
	        		   resultSet.getString("checkinDate") , resultSet.getString("checkoutDate"), String.valueOf(resultSet.getInt("roomNum")), resultSet.getString("uname")));

	           
	       }
	       
	       
	       sql = "SELECT SUM(subTotal) as 1 FROM reservationdetail";      
   		
   			preparedStatement = connection.prepareStatement(sql);
   			resultSet = preparedStatement.executeQuery();
System.out.println("오니");
	       while(resultSet.next()){   
	    	   int total = resultSet.getInt("1");
	    	   System.out.println(total);
	           
	       }
    	}
    	
    	 catch(Exception e){
    	    e.printStackTrace();
    	 }	
    }
 
	
	@FXML
    public void initialize() {
		
		getReserList(0);

		roomNumCol.setCellValueFactory(cellData -> cellData.getValue().getRoomNum_());
	    periodCol.setCellValueFactory(cellData -> cellData.getValue().getAccomPeriod_());
	    uNameCol.setCellValueFactory(cellData -> cellData.getValue().getUnmae_());
	    statusCol.setCellValueFactory(cellData -> cellData.getValue().getStatus_());
	    numberOfCol.setCellValueFactory(cellData -> cellData.getValue().getNumberOf_());
       
        
        table.setItems(oblist);
		

    }
}
