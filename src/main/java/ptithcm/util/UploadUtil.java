package ptithcm.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UploadUtil {
	private Properties properties = null;

	private static UploadUtil instance = null;

	String proFileName = "upload.properties";

	private UploadUtil() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(proFileName);
		if (inputStream != null) {
			properties = new Properties();
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static UploadUtil getInstance() {
		if (instance == null) {
//			 synchronized (UploadUtil.class) {
//				 instance = new UploadUtil();
//			 }
			instance = new UploadUtil();

		}
		return instance;
	}

	public String getValue(String key) {
		if (properties.containsKey(key)) {
			return properties.getProperty(key);
		}
		return null;
	}
}
