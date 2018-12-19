package manager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import first.CheckInController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.ConnectionUtil;
import util.SoundEffectUtil;
import variables.Room;

public class RoomManagementController {
	
	@FXML
	private TableView<Room> table;
	@FXML
    private TableColumn<Room, Image> imageColumn;
    @FXML
    private TableColumn<Room, String> numColumn;
    @FXML
    private TableColumn<Room, String> typeColumn;
    @FXML
    private TableColumn<Room, String> keyColumn;
    @FXML
    private TableColumn<Room, String> priceColumn;
    @FXML
    private TableColumn<Room, String> oppuColumn;
    @FXML
    private TableColumn<Room, String> cleanColumn;
    
    //방 리스트 담는 리스트
    private ObservableList<Room> oblist;

	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	@FXML
	private Pane pane;

	public RoomManagementController()  {
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
	
	//데이터 받아오기
    public void getRoomList() {
    	oblist = FXCollections.observableArrayList();
    	
    	try {
    		String sql = "SELECT roomNum, roomType, keyPasswd, price, isOccupied, isCleaned, isSelected, imageName FROM Room";      
    		
    		preparedStatement = connection.prepareStatement(sql);
    		resultSet = preparedStatement.executeQuery();
	       
	       while(resultSet.next()){
	    	   //이미지
	    	   File file = new File("resource/room/" + resultSet.getString("imageName"));
	           if( !file.exists() ){
	        	   file = new File("resource/room/no_image.png");
	           }
	           Image image = new Image(file.toURI().toString(), 280, 250, false, false);
	           System.out.println(file.toURI().toString() + "!!");
	    	   //데이터 넣기
	           oblist.add(new Room(String.valueOf(resultSet.getInt("roomNum")), String.valueOf(resultSet.getInt("roomType")), String.valueOf(resultSet.getInt("keyPasswd")), 
	        		   String.valueOf(resultSet.getInt("price")) ,String.valueOf((resultSet.getInt("isOccupied") != 0)), 
	        		   String.valueOf((resultSet.getInt("isCleaned") != 0)), image));

    	 }}
    	 catch(Exception e){
    	    e.printStackTrace();
    	 }
    	
    	
    	
    }
	
	@FXML
    public void initialize() {
		getRoomList();
		imageColumn.setCellFactory(new Callback<TableColumn<Room,Image>, TableCell<Room,Image>>() {
			
			@Override
			public TableCell<Room, Image> call(TableColumn<Room, Image> param) {
				//Set up the Table
		        TableCell<Room, Image> cell = new TableCell<Room, Image>() {
		            @Override
		        	public void updateItem(Image item, boolean empty) {
		            	System.out.println(item + "ggg");
		              if (item != null) {
		            	  
		            	  HBox box = new HBox();
		            	  //box.setSpacing(10);
		            	  ImageView imageview = new ImageView();
		      	        	imageview.setFitHeight(50);
		      	        	imageview.setFitWidth(50);
		      	        	imageview.setImage(item);
		      	        	// Attach the imageview to the cell
		      	        	box.getChildren().add(imageview);
		      	        	setGraphic(box);
		              }
		            }
		         };
		         
		         return cell;
				
			}
		});
		numColumn.setCellValueFactory(cellData -> cellData.getValue().getRoomNum_());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getRoomType_());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPrice_());
        //classColumn.setCellValueFactory(cellData -> cellData.getValue().classNumProperty().asObject());
        table.setItems(oblist);
    }
}
