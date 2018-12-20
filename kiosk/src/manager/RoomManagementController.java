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
	
	//방 등록 화면
	@FXML private Pane pane;
	@FXML private TextField roomNum;
	@FXML private TextField price;
	@FXML private MenuButton sel;
	@FXML private ImageView roomImage;
	@FXML private MenuItem sel_0, sel_1, sel_2, sel_3;
	
	private String filepath;
	Image image = null;
	int roomType = -1;
	
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
    		String sql = "SELECT roomNum, roomType, keyPasswd, price, isOccupied, isCleaned, isSelected, imageName FROM room";      
    		
    		preparedStatement = connection.prepareStatement(sql);
    		resultSet = preparedStatement.executeQuery();
	       
    		String title = "";
	       while(resultSet.next()){
	    	   //이미지
	    	   File file = new File("resource/room/" + resultSet.getString("imageName"));
	           if( !file.exists() ){
	        	   file = new File("resource/room/no_image.png");
	           }
	           image = new Image(file.toURI().toString());
	    	   
	    	  /* File file = new File("resource/room/" + resultSet.getString("imageName"));
	    	   if( !file.exists() ){
	    		   title = "resource/room/no_image.png";
	           } else {
	        	   title = "resource/room/" + resultSet.getString("imageName");
	           }*/
	    	   //데이터 넣기
	           oblist.add(new Room(String.valueOf(resultSet.getInt("roomNum")), String.valueOf(resultSet.getInt("roomType")), String.valueOf(resultSet.getInt("keyPasswd")), 
	        		   String.valueOf(resultSet.getInt("price")) ,String.valueOf((resultSet.getInt("isOccupied") != 0)), 
	        		   String.valueOf((resultSet.getInt("isCleaned") != 0)), image));

    	 }}
    	 catch(Exception e){
    	    e.printStackTrace();
    	 }	
    }
    
    /** 이미지 파일 선택 **/
    public void choseFile() {
    	
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Select Room Image files");
    	
    	File file = new File(".");
        String rootPath = file.getAbsoluteFile().getPath();

    	fileChooser.setInitialDirectory(new File(rootPath));
    	fileChooser.getExtensionFilters().addAll(
    	        new ExtensionFilter("jpg file", "*.jpg"), new ExtensionFilter("png file", "*.png"));

    	Stage primaryStage = (Stage)pane.getScene().getWindow(); // 현재 윈도우 가져오기
    	List<File> selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
    	
    	//여러 파일 선택
    	if (selectedFiles.size() > 1) {
    		
    	}

    	if (selectedFiles != null) {
    		filepath = selectedFiles.get(0).getAbsolutePath();
    		File file_ = new File(filepath);
    		
    		image = new Image(file_.toURI().toString());
    		roomImage.setImage(image);
    	}  	
    }
    
    /** room 등록폼 
     * @throws IOException **/
    public void roomInputForm() throws IOException {
    	
    	SoundEffectUtil.sound();
		Stage primaryStage = (Stage)pane.getScene().getWindow(); // 현재 윈도우 가져오기
		Parent root = null;

		 FXMLLoader loader = new FXMLLoader(getClass().getResource("/manager/roomInput.fxml"));
 		 root = loader.load();
 		
 	     Scene scene = new Scene(root);    
 	     primaryStage.setScene(scene);
 	     primaryStage.show();
		
    }
    
    
    /** room 등록 
     * @throws SQLException **/
    public void insertRoom() throws SQLException {
    	
    	Alert alert;
    	if (roomType == -1) {
    		alert = new Alert(AlertType.ERROR, "타입을 선택해 주세요.", ButtonType.YES, ButtonType.CANCEL);
        	alert.showAndWait();
    		return;
    	}

    	if (roomNum.getText().equals("")) {
    		alert = new Alert(AlertType.ERROR, "방번호를 입력해 주세요.", ButtonType.YES, ButtonType.CANCEL);
        	alert.showAndWait();
    		return;
    	}
    	
    	if (price.getText().equals("")) {
    		alert = new Alert(AlertType.ERROR, "가격을 입력해 주세요.", ButtonType.YES, ButtonType.CANCEL);
        	alert.showAndWait();
    		return;
    	}
    	
    	//file 생성
    	File outputFile = new File(System.getProperty("user.dir") + "\\resource\\room\\dd.png");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
          ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

        String sql = "INSERT INTO room (roomNum, roomType, price, imageName, isOccupied, isCleaned, isSelected, keyPasswd) VALUES(?,?,?,?,?,?,?,?)";
        
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, roomNum.getText());
        preparedStatement.setInt(2, roomType);
        preparedStatement.setString(3, price.getText());
        preparedStatement.setString(4, roomNum.getText() + ".png");   
        preparedStatement.setInt(5, 0);
        preparedStatement.setInt(6, 1);
        preparedStatement.setInt(7, 0);
        preparedStatement.setInt(8, 0);
        preparedStatement.executeUpdate();
	       	
    }
	
	@FXML
    public void initialize() {
		
		getRoomList();
		
		imageColumn.setCellFactory(param -> {
				
		       final ImageView imageview = new ImageView();
		       imageview.setFitHeight(50);
		       imageview.setFitWidth(50);

		       //Set up the Table
		       TableCell<Room, Image> cell = new TableCell<Room, Image>() {
		           public void updateItem(Image item, boolean empty) {
		        	   //System.out.println(item.getImage_());
		             if (item != null) {
		            	
		                 imageview.setImage(item);
		             }
		           }
		        };
		        cell.setGraphic(imageview);
		        return cell;
		   });
		numColumn.setCellValueFactory(cellData -> cellData.getValue().getRoomNum_());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getRoomType_());
        keyColumn.setCellValueFactory(cellData -> cellData.getValue().getKeyPasswd_());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPrice_());
        oppuColumn.setCellValueFactory(cellData -> cellData.getValue().getIsOccupied_());
        cleanColumn.setCellValueFactory(cellData -> cellData.getValue().getIsCleaned_());
        
        table.setItems(oblist);
		
		//방 번호 input 이벤트
		if (roomNum != null) {
			roomNum.textProperty().addListener((obs, oldText, newText) -> {
		        if (!roomNum.getText().matches("-?([1-9][0-9]*)?")) { 
		        	roomNum.setText(roomNum.getText().substring(0,roomNum.getText().length()-1));
		        } 
			});
		}
		//가격 input 이벤트
		if (price != null) {
			price.textProperty().addListener((obs, oldText, newText) -> {
				if (!price.getText().matches("-?([1-9][0-9]*)?")) { 
					price.setText(price.getText().substring(0, price.getText().length()-1));
				} 
			});
		}
		//메뉴 버튼 이벤트
		if (sel != null) {
			sel_0.setOnAction(event -> {
				roomType = 0;
			    sel.setText(sel_0.getText());
			});
			sel_1.setOnAction(event -> {
				roomType = 1;
				sel.setText(sel_1.getText());
			});
			sel_2.setOnAction(event -> {
				roomType = 2;
				sel.setText(sel_2.getText());
			});
			sel_3.setOnAction(event -> {
				roomType = 3;
				sel.setText(sel_3.getText());
			});
		}
    }
}
