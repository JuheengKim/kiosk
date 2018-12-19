package variables;

public class Payment {
	
	private int payType;   	/** 0: card, 1: cash **/
	private int pno;
	private int autorized;
	
	//Card
	private int cardNum;
	private String cardExpDate;
	private String cardCompany;
	private int paidCard;
	
	//Cash
	private int paidCash;
	private int changeCash;
	
	public Payment() {}
	
	public Payment(int payType, int pno, int autorized, int cardNum, String cardExpDate, String cardCompany, int paidCard) {
		this.payType = payType;
		this.pno = pno;
		this.autorized = autorized;
		this.cardNum = cardNum;
		this.cardExpDate = cardExpDate;
		this.cardCompany = cardCompany;
		this.paidCard = paidCard;
	}
	
	public Payment(int payType, int pno, int autorized, int paidCash, int changeCash) {
		this.payType = payType;
		this.pno = pno;
		this.autorized = autorized;
		this.paidCash = paidCash;
		this.changeCash = changeCash;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
	}

	public int getAutorized() {
		return autorized;
	}

	public void setAutorized(int autorized) {
		this.autorized = autorized;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public String getCardExpDate() {
		return cardExpDate;
	}

	public void setCardExpDate(String cardExpDate) {
		this.cardExpDate = cardExpDate;
	}

	public String getCardCompany() {
		return cardCompany;
	}

	public void setCardCompany(String cardCompany) {
		this.cardCompany = cardCompany;
	}

	public int getPaidCard() {
		return paidCard;
	}

	public void setPaidCard(int paidCard) {
		this.paidCard = paidCard;
	}

	public int getPaidCash() {
		return paidCash;
	}

	public void setPaidCash(int paidCash) {
		this.paidCash = paidCash;
	}

	public int getChangeCash() {
		return changeCash;
	}

	public void setChangeCash(int changeCash) {
		this.changeCash = changeCash;
	}
}
