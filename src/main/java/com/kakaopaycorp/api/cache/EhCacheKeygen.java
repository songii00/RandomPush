package com.kakaopaycorp.api.cache;


import java.util.List;

public class EhCacheKeygen {

	private static final String DELIMETER = ":";
	private static final String KEY_DELIMETER = "!@#$";

	/**
	 * Cache Key 생성
	 *
	 * @param prefix
	 * @param ids
	 * @return
	 */
	public static String generateKey(String prefix, List<String> ids) {
		return EhCacheKeyPrefix.VERSION + DELIMETER + prefix + KEY_DELIMETER + String.join(KEY_DELIMETER, ids);
	}

}
