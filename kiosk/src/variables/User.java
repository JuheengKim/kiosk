package variables;

public class User {
	
	private int userNum;
	private String jumin;
	private String name;
	private String utel;
	
	public User(int userNum, String jumin, String name, String utel) {
		this.userNum = userNum;
		this.jumin = jumin;
		this.name = name;
		this.utel = utel;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public String getJumin() {
		return jumin;
	}

	public void setJumin(String jumin) {
		this.jumin = jumin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUtel() {
		return utel;
	}

	public void setUtel(String utel) {
		this.utel = utel;
	}
	
}
