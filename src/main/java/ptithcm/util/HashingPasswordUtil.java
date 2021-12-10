package ptithcm.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashingPasswordUtil {
	static final String SALT = "inventories";

	public static String encrypt(String original_password) {
		String result = null;

		byte[] salt = SALT.getBytes();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] hashPass = md.digest(original_password.getBytes(StandardCharsets.US_ASCII));
			result = Base64.getEncoder().encodeToString(hashPass).substring(0, 32);
		} catch (NoSuchAlgorithmException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {
//		String rs = encrypt("1309");
//		System.out.println(rs); // IuWjE7OWh5CWe7OCFW/5KgNR7qpN4qT+
		String rs = encrypt("1234");
		System.out.println(rs); // V372EKby2RKLXJwEp6hykVkfd2IaVmV5
	}
}
