package util;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;
public class ConnectionUtil {
    Connection conn = null;
    public static Connection connectdb()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://ec2-18-191-143-160.us-east-2.compute.amazonaws.com:3306/hotel","root","0718");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kioskdb?serverTimezone=UTC&useSSL=false","root","0718");
            return conn;
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}