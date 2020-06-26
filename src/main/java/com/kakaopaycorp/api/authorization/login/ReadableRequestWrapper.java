package com.kakaopaycorp.api.authorization.login;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ReadableRequestWrapper extends HttpServletRequestWrapper {
	private final Map<String, String[]> modifiableParameters;
	private Map<String, String[]> allParameters = null;

	public ReadableRequestWrapper(final HttpServletRequest request, final Map<String, String[]> allParameters) {
		super(request);
		modifiableParameters = new TreeMap<>();
		modifiableParameters.putAll(allParameters);
	}

	@Override
	public String getParameter(final String name) {
		String[] strings = getParameterMap().get(name);
		if (strings != null) {
			return strings[0];
		}
		return super.getParameter(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		if (allParameters == null) {
			allParameters = new TreeMap<>();
			allParameters.putAll(super.getParameterMap());
			allParameters.putAll(modifiableParameters);
		}
		return Collections.unmodifiableMap(allParameters);
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(getParameterMap().keySet());
	}

	@Override
	public String[] getParameterValues(final String name) {
		return getParameterMap().get(name);
	}
}
