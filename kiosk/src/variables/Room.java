package variables;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

@SuppressWarnings("rawtypes")
public class Room extends RecursiveTreeObject<Room> {
	
	public int roomNum;    // room 번호
	public int roomType;   
	public int keyPasswd;
	public int price;
	public boolean isOccupied;
	public boolean isCleaned;
	public boolean isSelected;
	public String imageName;
	public Image img;
	
	//tableview 쓰려고
	public StringProperty roomNum_;
	public StringProperty roomType_;
	public StringProperty keyPasswd_;
	public StringProperty price_;
	public StringProperty isOccupied_;
	public StringProperty isCleaned_;
	public Image image_;
	
	public Room(int roomNum, int roomType, int keyPasswd, int price, boolean isOccupied, boolean isCleaned, boolean isSelected, String imageName) {
		this.roomNum = roomNum;
		this.roomType = roomType;
		this.keyPasswd = keyPasswd;
		this.price = price;
		this.isOccupied = isOccupied;
		this.isCleaned = isCleaned;
		this.isSelected = isSelected;
		this.imageName = imageName;
	}

	public Room(String roomNum, String roomType, String keyPasswd, String price, String isOccupied, String isCleaned, Image image) {
		this.roomNum_ = new SimpleStringProperty(roomNum);
		this.roomType_ = new SimpleStringProperty(roomType);
		this.keyPasswd_ = new SimpleStringProperty(keyPasswd);
		this.price_ = new SimpleStringProperty(price);
		this.isOccupied_ = new SimpleStringProperty(isOccupied);
		this.isCleaned_ = new SimpleStringProperty(isCleaned);
		this.image_ = image;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getRoomType() {
		return roomType;
	}

	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}

	public int getKeyPasswd() {
		return keyPasswd;
	}

	public void setKeyPasswd(int keyPasswd) {
		this.keyPasswd = keyPasswd;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public boolean isCleaned() {
		return isCleaned;
	}

	public void setCleaned(boolean isCleaned) {
		this.isCleaned = isCleaned;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}


	public StringProperty getRoomNum_() {
		return roomNum_;
	}


	public void setRoomNum_(StringProperty roomNum_) {
		this.roomNum_ = roomNum_;
	}


	public StringProperty getRoomType_() {
		return roomType_;
	}


	public void setRoomType_(StringProperty roomType_) {
		this.roomType_ = roomType_;
	}


	public StringProperty getKeyPasswd_() {
		return keyPasswd_;
	}


	public void setKeyPasswd_(StringProperty keyPasswd_) {
		this.keyPasswd_ = keyPasswd_;
	}


	public StringProperty getPrice_() {
		return price_;
	}


	public void setPrice_(StringProperty price_) {
		this.price_ = price_;
	}


	public StringProperty getIsOccupied_() {
		return isOccupied_;
	}


	public void setIsOccupied_(StringProperty isOccupied_) {
		this.isOccupied_ = isOccupied_;
	}


	public StringProperty getIsCleaned_() {
		return isCleaned_;
	}


	public void setIsCleaned_(StringProperty isCleaned_) {
		this.isCleaned_ = isCleaned_;
	}


	public Image getImage_() {
		return image_;
	}


	public void setImage_(Image image_) {
		this.image_ = image_;
	}

}
