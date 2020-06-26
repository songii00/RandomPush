package com.kakaopaycorp.api.authorization.token.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class TokenKeygen {

	private static final int keyLength = 3;
	private static final char[] characterTable = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
												  'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
												  'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

	public static String publishToken() {
		Random random = new Random(System.currentTimeMillis());
		int tableLength = characterTable.length;
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < keyLength; i++) {
			buffer.append(characterTable[random.nextInt(tableLength)]);
		}
		return buffer.toString();
	}

	public static String getHashKeyBy(String tokenId) {
		return Md5Crypto.cryptoMd5(tokenId);
	}

	static class Md5Crypto {
		static String cryptoMd5(String plaintext) {
			String MD5;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(plaintext.getBytes());
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer();

				for (int i = 0; i < byteData.length; i++) {
					sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
				}
				MD5 = sb.toString();

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				MD5 = null;
			}
			return MD5;
		}
	}
}
