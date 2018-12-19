package variables;

public class Manager {
	
	private String mID;
	private String mPW;
	
	public Manager(String mID, String mPW) {
		this.mID = mID;
		this.mPW = mPW;
	}

	public String getmID() {
		return mID;
	}

	public void setmID(String mID) {
		this.mID = mID;
	}

	public String getmPW() {
		return mPW;
	}

	public void setmPW(String mPW) {
		this.mPW = mPW;
	}
}
