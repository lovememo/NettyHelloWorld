package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConstUtil {
	public static String SFTP_SERVER_DIR = "";
	public static String SFTP_LOCAL_DIR  = "";
	public static String SERVER_IP	= "";
	public static String SERVER_PORT = "";

	static {
		Properties pro = new Properties();
		try {
			FileInputStream in = new FileInputStream("app.properties");
			pro.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SFTP_SERVER_DIR = pro.getProperty("SFTP_SERVER_DIR");
		SFTP_LOCAL_DIR = pro.getProperty("SFTP_LOCAL_DIR");
		SERVER_IP = pro.getProperty("SERVER_IP");
		SERVER_PORT = pro.getProperty("SERVER_PORT");
	}
}
