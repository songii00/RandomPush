package com.kakaopaycorp.api.cipher;

public interface Crypto {
	String encrypt(String val);

	String decrypt(String val);
}
