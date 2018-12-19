package variables;

public class Reservation {
	
	private int rno;
	private int uno;
	private int accomPeriod;
	private int numberOf;
	private int status;   // 0: 입실 전, 1: 취소, 2: 입실, 3:체크아웃
	private String checkinDate;
	private String checkoutDate;
	private int paymentNum;
	
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
}
