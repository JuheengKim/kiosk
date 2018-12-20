package variables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reservation {
	
	private int rno;
	private int uno;
	private int accomPeriod;
	private int numberOf;
	private int status;   // 0: 입실 전, 1: 취소, 2: 입실, 3:체크아웃
	private String checkinDate;
	private String checkoutDate;
	private int paymentNum;
	
	public StringProperty accomPeriod_;
	public StringProperty numberOf_;
	public StringProperty status_;
	public StringProperty checkinDate_;
	public StringProperty checkoutDate_;
	public StringProperty roomNum_;
	public StringProperty unmae_;
	
	public Reservation() {}

	public Reservation(int uno, int accomPeriod, int numberOf, int status, String checkinDate, String checkoutDate, int paymentNum) {
		this.uno = uno;
		this.accomPeriod = accomPeriod;
		this.numberOf = numberOf;
		this.status = status;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.paymentNum = paymentNum;
	}
	
	public Reservation(String accomPeriod, String numberOf, String status, String checkinDate, String checkoutDate, String roomNum, String uname) {
		this.accomPeriod_ = new SimpleStringProperty(accomPeriod);
		this.numberOf_ = new SimpleStringProperty(numberOf);
		this.status_ = new SimpleStringProperty(status);
		this.checkinDate_ = new SimpleStringProperty(checkinDate);
		this.checkoutDate_ = new SimpleStringProperty(checkoutDate);
		this.roomNum_ = new SimpleStringProperty(roomNum);
		this.unmae_ = new SimpleStringProperty(uname);
	}
	
	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
	}

	public int getUno() {
		return uno;
	}

	public void setUno(int uno) {
		this.uno = uno;
	}

	public int getAccomPeriod() {
		return accomPeriod;
	}

	public void setAccomPeriod(int accomPeriod) {
		this.accomPeriod = accomPeriod;
	}

	public int getNumberOf() {
		return numberOf;
	}

	public void setNumberOf(int numberOf) {
		this.numberOf = numberOf;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public int getPaymentNum() {
		return paymentNum;
	}

	public void setPaymentNum(int paymentNum) {
		this.paymentNum = paymentNum;
	}

	public StringProperty getAccomPeriod_() {
		return accomPeriod_;
	}

	public void setAccomPeriod_(StringProperty accomPeriod_) {
		this.accomPeriod_ = accomPeriod_;
	}

	public StringProperty getNumberOf_() {
		return numberOf_;
	}

	public void setNumberOf_(StringProperty numberOf_) {
		this.numberOf_ = numberOf_;
	}

	public StringProperty getStatus_() {
		return status_;
	}

	public void setStatus_(StringProperty status_) {
		this.status_ = status_;
	}

	public StringProperty getCheckinDate_() {
		return checkinDate_;
	}

	public void setCheckinDate_(StringProperty checkinDate_) {
		this.checkinDate_ = checkinDate_;
	}

	public StringProperty getCheckoutDate_() {
		return checkoutDate_;
	}

	public void setCheckoutDate_(StringProperty checkoutDate_) {
		this.checkoutDate_ = checkoutDate_;
	}

	public StringProperty getRoomNum_() {
		return roomNum_;
	}

	public void setRoomNum_(StringProperty roomNum_) {
		this.roomNum_ = roomNum_;
	}

	public StringProperty getUnmae_() {
		return unmae_;
	}

	public void setUnmae_(StringProperty unmae_) {
		this.unmae_ = unmae_;
	}
}
