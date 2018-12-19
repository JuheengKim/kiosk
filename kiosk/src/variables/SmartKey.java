package variables;

public class SmartKey {
	
	private int kno;     
	private int keyPasswd;
	private int rno;
	private int uno;
	
	public SmartKey(int keyPasswd, int rno, int uno) {
		this.keyPasswd = keyPasswd;
		this.rno = rno;
		this.uno = uno;
	}

	public int getKno() {
		return kno;
	}

	public void setKno(int kno) {
		this.kno = kno;
	}

	public int getKeyPasswd() {
		return keyPasswd;
	}

	public void setKeyPasswd(int keyPasswd) {
		this.keyPasswd = keyPasswd;
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
}
