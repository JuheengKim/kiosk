package second;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import first.CheckInController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.ConnectionUtil;
import util.SoundEffectUtil;
import variables.Reservation;
import variables.ReservationDetail;
import variables.Room;
import variables.User;

/** room 리스트 **/
public class RoomListController{
	
	Scene scene;
	DecimalFormat formatter;    
	int number = 1;
	int roomPrice = 0;
	boolean selRoom = false;
	
	Room room;
	User user;
	Reservation reser;
	ReservationDetail d;
	
	// PagePane 관련
	private static final double CELL_HEIGHT = 340;
    private static final double CELL_WIDTH = 280;
    private static final double CELL_H_GAP = 15;
    private static int ITEM_COUNT;
    private int itemCount; // Count of items that fit on a page
    private int roomType = 1;
    int pageCount;
    
    //방 리스트 담는 리스트
    private ObservableList<Room> oblist;
    
    //compenent
	@FXML private Label roomName;
	@FXML private Label numberOf;
	@FXML private Label totPrice;
	@FXML private TextField stay;
	@FXML private Pagination pagination;
	@FXML private Button payBtn;
	@FXML private ToolBar toolbar;

	//디비
	Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    public RoomListController()  {
    	Font.loadFont(CheckInController.class.getResource("/first/bm-Regular.ttf").toExternalForm(), 10);
    	connection = ConnectionUtil.connectdb();
    	formatter = new DecimalFormat("###,###");
    }   
    
    //결제 방식 선택 화면에서 뒤로 가기 눌렀을 경우
    public void initVariable(User user, Reservation reser, ReservationDetail d, Room room){
    	
    	//정보 세팅
    	if (room != null) {
    		selRoom = true;
    		roomName.setText(room.getRoomNum() + " 호");
    		roomPrice = room.getPrice();
    		this.room = room;
    	}
    	if (reser != null) {
    		stay.setText(String.valueOf(reser.getAccomPeriod()));
    		calTotPrice();
    		 this.reser = reser;
    	}
       	
    	if (d != null) {
    		 this.d = d;
    	}
    	
    	this.user = user;
       
       
        
    }
    
    //데이터 받아오기
    public void getRoomList() {
    	oblist = FXCollections.observableArrayList();
    	
    	try {
    		String sql = "SELECT roomNum, roomType, keyPasswd, price, isOccupied, isCleaned, isSelected, imageName FROM Room WHERE roomType IN (" + roomType + ")";      
    		
    		preparedStatement = connection.prepareStatement(sql);
    		resultSet = preparedStatement.executeQuery();
	       
	       while(resultSet.next()){
	    	   oblist.add(new Room(resultSet.getInt("roomNum"), resultSet.getInt("roomType"), resultSet.getInt("keyPasswd"), resultSet.getInt("price")
	    			   ,(resultSet.getInt("isOccupied") != 0), (resultSet.getInt("isCleaned") != 0), (resultSet.getInt("isSelected") != 0), resultSet.getString("imageName")));  
	       	}
    	 }
    	 catch(Exception e){
    	    e.printStackTrace();
    	 }
    	
    	ITEM_COUNT = oblist.size();
    }

   private StackPane pageFactory (int pageIndex) {
       
	   TilePane page = new TilePane();
       StackPane pageContainer = new StackPane(page);
       pageContainer.setAlignment(Pos.CENTER);

       initPage(page);
       addListeners(pageContainer, page);
       fillPage(page, pageIndex);

       return pageContainer;
  } 
   
  private void initPage( TilePane page ) {
       page.setHgap(CELL_H_GAP);
       page.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
   }

   private void addListeners ( StackPane pageContainer, TilePane page) {
       pageContainer.heightProperty().addListener( observable -> {
           if (pageContainer.getWidth() != 0 && pageContainer.getHeight() != 0) {
               onResize(pageContainer, page);
           }
       });
       pageContainer.widthProperty().addListener( observable -> {
           if (pageContainer.getWidth() != 0 && pageContainer.getHeight() != 0) {
               onResize(pageContainer, page);
           }
       });
   }

   private void onResize ( StackPane pageContainer, TilePane page ) {
       int itemCountHorizontal = 2;
       int itemCountVertical = 1;
       int itemCount;
       
       itemCount = itemCountHorizontal * itemCountVertical;
       itemCount = ( ITEM_COUNT == 0 ) ? 1 : itemCount;

       pageCount = (int) Math.ceil((double)ITEM_COUNT / (double)itemCount);
       if (pageCount == 0) pageCount = 1;
      
       page.setMinWidth((CELL_WIDTH * itemCountHorizontal) + (CELL_H_GAP * (itemCountHorizontal-1)));
       page.setMaxWidth((CELL_WIDTH * itemCountHorizontal) + (CELL_H_GAP * (itemCountHorizontal-1)));
       page.setMinHeight((CELL_HEIGHT * itemCountVertical));
       page.setMaxHeight((CELL_HEIGHT * itemCountVertical));
      
      if ( pagination.getPageCount() != pageCount ) {
           Platform.runLater( () -> {
               pagination.setPageCount(pageCount);
           });
       } else if ( this.itemCount != itemCount ){
           this.itemCount = itemCount;
           page.getChildren().clear();
           fillPage(page, pagination.getCurrentPageIndex());
       }
      
      //pagination.consume();
   }

   private void fillPage ( TilePane page, int pageIndex ) {
       int startIndex = pageIndex * itemCount;
       int endIndex = Math.min( ITEM_COUNT, startIndex+itemCount );
       for ( int i = startIndex; i < endIndex; i++ ) {
    	   int index = i;
    	   Pane p = cellFactory(oblist.get(i));
    	   //룸 선택시 이벤트 (정보 채워지고 가격 계산함)
    	   p.setOnMousePressed(new EventHandler<MouseEvent>() {
   	        @Override
   	        public void handle(MouseEvent event) {
   	        	SoundEffectUtil.sound();
   	        	event.consume();
   	        	selRoom = true;
   	        	room = oblist.get(index);
   	        	roomName.setText(oblist.get(index).getRoomNum() + " 호"); 
   	        	roomPrice = oblist.get(index).getPrice();
   	        	calTotPrice();
   	        }
   	    });
           page.getChildren().add(p);
       }
   }

   private Pane cellFactory(Room room) {

       StackPane cell = new StackPane();
       cell.setMaxHeight(CELL_HEIGHT);
       cell.setMinHeight(CELL_HEIGHT);
       cell.setMaxWidth(CELL_WIDTH);
       cell.setMinWidth(CELL_WIDTH);
       cell.setAlignment(Pos.CENTER);

       VBox v = new VBox();
       //v.setFocusTraversable(false);
      
       
       File file = new File("resource/room/" + room.getImageName());
       if( !file.exists() ){
    	   file = new File("resource/room/no_image.png");
       }
       Image image = new Image(file.toURI().toString(), 280, 250, false, false);
       v.getChildren().add(new ImageView(image));
       v.getChildren().add(new Label(room.getRoomNum() + " 호"));
       v.getChildren().add(new Label(formatter.format(room.getPrice()) + " 원"));
       
       cell.getChildren().add(v);
       return cell;
   }
   
   /** - 버튼 누를 때 **/
   //@FXML
   public void minusStay() {
       int get = Integer.parseInt(stay.getText());
       if (get == 1) {
    	   stay.setText("1");
       } else {
    	   get -= 1;
    	   stay.setText(String.valueOf(get));
       }
       //방이 선택되었을 때 계산
       if (selRoom) 
    	   calTotPrice();
   }
   
   /** + 버튼 누를 때 **/
   //@FXML
   public void plusStay() {
       int get = Integer.parseInt(stay.getText());
       get += 1;
       stay.setText(String.valueOf(get));
       //방이 선택되었을 때 계산
       if (selRoom) 
    	   calTotPrice();
   }
   
   /** tab 버튼 누를 때 **/
   @FXML
   public void roomTypeChange(ActionEvent event) {
	   SoundEffectUtil.sound();
	   int i;
	   
	   roomType = Integer.parseInt(((Button)event.getSource()).getId());
	   for (i=0; i<4; i++) {
		   if ((i+1) == roomType) {
			   toolbar.lookup("#" + (i+1)).setStyle("-fx-background-color: #485E6C; -fx-text-fill: #FFFFFF");
		   }
		   else {
			   toolbar.lookup("#" + (i+1)).setStyle("-fx-background-color: beige; -fx-text-fill: #000000");
		   }
	   }
	   getRoomList();
	   pagination.setPageFactory(this::pageFactory);
   }
   
   /** 가격 계산 **/
   void calTotPrice() {
	   // (인원 * 가격) + (stay * 가격 * 1/2)
	   //return (number*roomPrice) + (Integer.parseInt(stay.getText()) * roomPrice/2);
	   totPrice.setText(formatter.format(number*roomPrice * Integer.parseInt(stay.getText())) + " 원");
   }
   
   @FXML
   public void goPay(ActionEvent event) throws IOException {
	   SoundEffectUtil.sound();
	   
	   if (!selRoom ) {
		 Alert alert = new Alert(AlertType.WARNING);
		 alert.setTitle("경고");
		 alert.setHeaderText("방을 선택해 주세요!");

		 alert.showAndWait();
	   } else {
		 
		   //sound
		   Media sound = new Media(new File("resource/sound/payfun.mp3").toURI().toString());
		   MediaPlayer mediaPlayer = new MediaPlayer(sound);
		   mediaPlayer.play();
		
		 Stage primaryStage = (Stage)payBtn.getScene().getWindow(); // 현재 윈도우 가져오기
		 Parent root = null;
		
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectPayment.fxml"));
		 root = loader.load();
		 
		//예약 정보 
		 if (reser == null) reser = new Reservation();
		 reser.setAccomPeriod(Integer.parseInt(stay.getText()));
		 reser.setUno(1);
		 reser.setNumberOf(number);
		 
		 //예약 상세 정보
		 if (d == null) d = new ReservationDetail();
		 d.setRoomNum(room.getRoomNum());
		 d.setRoomType(room.getRoomType());
		 d.setUnitPrice(room.getPrice());
		 d.setSubTotal(number*roomPrice * Integer.parseInt(stay.getText()));
		
		 //결제 controller에 데이터 보내기
		 PaymentController controller = loader.<PaymentController>getController();
		 controller.initVariable(user, reser, d, room, 0);
		
	     Scene scene = new Scene(root);    
	     primaryStage.setScene(scene);
	     primaryStage.show();
	 }
	 
	 

   }
   
   @FXML
   void initialize() {  
	  getRoomList();
      pagination.setPageFactory(this::pageFactory);
   }
	
}
