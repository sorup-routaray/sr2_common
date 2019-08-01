package com.sr2_common.core.utility.test;

import java.util.ArrayList;
import java.util.List;

import com.sr2_common.core.utility.PasswordUtility;

public class PasswordUtilityTest {

	public static void main(String[] args) {
		passwordGenerationTest();
		textEncodingAndDecodingtest();
		System.out.println(PasswordUtility.decodeBase64String(" I1AqWUUwYjJsOUt1SyRXQDJUNmY="));
	}

	private static void textEncodingAndDecodingtest() {
		String plaintext = "sorup";
		String encodetoBase64 = PasswordUtility.encodetoBase64String(plaintext);
		String decodeBase64String = PasswordUtility.decodeBase64String(encodetoBase64);
		System.out.println("Original Text : " + plaintext);
		System.out.println("Encoded Text : " + encodetoBase64);
		System.out.println("Decoded Text : " + decodeBase64String);
		if(decodeBase64String.equals(plaintext)) {
			System.out.println("Text encoding and decoding matches");
		} else {
			System.out.println("Encoding and decoding does not match.");
		}
	}

	private static void passwordGenerationTest() {
		List<String> passwordList = new ArrayList<>();
		int randomPasswordLength = 10;
		int iteration = 10000;
		for (int i = 0; i < iteration; i++) {
			String randomPassword = PasswordUtility.generateRandomPassword(randomPasswordLength);
			if(passwordList.contains(randomPassword)) {
				throw new RuntimeException("Duplicate password generated.");
			}
		}
		System.out.println("Password generation logic working fine for iteration " + iteration);
	}
}
