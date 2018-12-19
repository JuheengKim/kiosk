package util;

import java.util.HashMap;
import org.json.simple.JSONObject;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

/** ���� **/
public class SMSUtil {
	public static void sendSms(String msg, String num) throws CoolsmsException {
		String api_key = "NCSTQB8DXOZR4YNB";
		String api_secret = "9JDU1QKFLYWHLJNLYRFT6KDHFRC98B4G";
		Message coolsms = new Message(api_key, api_secret);

		HashMap<String, String> set = new HashMap<String, String>();
		set.put("to", num); // ���Ź�ȣ
		set.put("from", "01023840718"); // �߽Ź�ȣ
		set.put("text", msg); // ���ڳ���
		set.put("type", "sms"); // ���� Ÿ��
	
		 try {
		      JSONObject obj = (JSONObject) coolsms.send(set);
		      System.out.println(obj.toString());
		 } catch (CoolsmsException e) {
		      System.out.println(e.getMessage());
		      System.out.println(e.getCode());
		 }
	}
}

