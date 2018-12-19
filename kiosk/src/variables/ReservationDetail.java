package variables;

public class ReservationDetail {
	
	private int rno;    // room ¹øÈ£
	private int roomNum;
	private int roomType;
	private int unitPrice;
	private int subTotal;
	
	public ReservationDetail() {}
	public ReservationDetail(int rno, int roomNum, int roomType, int unitPrice, int subTotal) {
		this.rno = rno;
		this.roomNum = roomNum;
		this.roomType = roomType;
		this.unitPrice = unitPrice;
		this.subTotal = subTotal;
	}

	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
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

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}
}
