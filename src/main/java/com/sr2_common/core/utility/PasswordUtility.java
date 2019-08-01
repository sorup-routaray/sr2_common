package com.sr2_common.core.utility;

import org.apache.commons.codec.binary.Base64;

public class PasswordUtility {

	private static final String PASSOWRD_GENERATOR_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
			+ "abcdefghijklmnopqrstuvxyz" + "!@#$%&*";

	public static String generateRandomPassword(int passwordLength) {
		StringBuilder sb = new StringBuilder(passwordLength);
		for (int counter = 0; counter < passwordLength; counter++) {
			int index = (int) (PASSOWRD_GENERATOR_CHAR.length() * Math.random());
			sb.append(PASSOWRD_GENERATOR_CHAR.charAt(index));
		}

		return sb.toString();
	}

	public static String encodetoBase64String(String planText) {
		byte[] encodeBase64 = Base64.encodeBase64(planText.getBytes());
		return new String(encodeBase64);
	}

	public static String decodeBase64String(String encodedText) {
		byte[] decodeBase64 = Base64.decodeBase64(encodedText.getBytes());
		return new String(decodeBase64);
	}
}
