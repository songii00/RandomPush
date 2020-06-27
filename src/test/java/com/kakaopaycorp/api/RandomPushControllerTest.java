package com.kakaopaycorp.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

import com.kakaopaycorp.api.domain.event.api.RandomPushController;
import com.kakaopaycorp.api.domain.event.service.RandomPushService;

@Slf4j
@ActiveProfiles("local")
@WebMvcTest(controllers = RandomPushController.class)
public class RandomPushControllerTest {

	@MockBean
	private RandomPushService randomPushService;
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void init() {
	}

	@Test
	void 뿌리기_토큰값일치() throws Exception {

		given(randomPushService.publishToken()).willReturn("123");

		mockMvc.perform(get("/event/random-push/price"))
			   .andExpect(status().isOk())
			   .andExpect(content().json("{'code':'0','errorMessage':'','result':'123'}"));
	}

}