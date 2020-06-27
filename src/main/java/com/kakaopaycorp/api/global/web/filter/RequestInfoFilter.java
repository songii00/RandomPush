package com.kakaopaycorp.api.global.web.filter;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import com.kakaopaycorp.api.domain.event.dto.RandomPushRequestDto;


@Slf4j
@WebFilter(urlPatterns = "/event/*")
public class RequestInfoFilter implements Filter {

	private static final String USER_ID = "X-USER-ID";
	private static final String ROOM_ID = "X-ROOM-ID";


	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;

		Map<String, String[]> originParameters = request.getParameterMap();
		Map<String, String[]> parameterMap = new TreeMap<>();

		for (String key : originParameters.keySet()) {
			parameterMap.put(key, originParameters.get(key));
		}
		parameterMap.put(RandomPushRequestDto.getNameFromUserId(), new String[]{request.getHeader(USER_ID)});
		parameterMap.put(RandomPushRequestDto.getNameFromRoomId(), new String[]{request.getHeader(ROOM_ID)});

		ReadableRequestInfoWrapper readableRequestWrapper = new ReadableRequestInfoWrapper(request, parameterMap);
		filterChain.doFilter(readableRequestWrapper, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
