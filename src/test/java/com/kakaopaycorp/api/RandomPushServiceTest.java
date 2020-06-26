package com.kakaopaycorp.api;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.slf4j.Slf4j;

import com.kakaopaycorp.api.domain.event.repository.RandomPushDetailRepository;
import com.kakaopaycorp.api.domain.event.repository.RandomPushRepository;
import com.kakaopaycorp.api.domain.event.service.RandomPushService;

/*@Slf4j
@ActiveProfiles("local")
@SpringBootTest(classes = RandomPushServiceTest.TestConfig.class)*/
@Slf4j
@ActiveProfiles("local")
/*@RunWith(SpringRunner.class)*/
@SpringBootTest
public class RandomPushServiceTest {

	/*public static class TestConfig {
		@Bean
		public RandomPushService randomPushService(@Autowired @Qualifier("tokenPublishService") TokenPublishService tokenPublishService,
												   @Autowired @Qualifier("randomPushRepository") RandomPushRepository randomPushRepository,
												   @Autowired @Qualifier("randomPushDetailRepository")
														   RandomPushDetailRepository randomPushDetailRepository) {
			return new RandomPushService(tokenPublishService,
										 randomPushRepository,
										 randomPushDetailRepository);
		}

		@Bean
		public RandomPushRepository randomPushRepository() {
			return new RandomPushRepository();
		}

		@Bean
		public RandomPushDetailRepository randomPushDetailRepository() {
			return new RandomPushDetailRepository();
		}

		@Bean
		public TokenPublishService tokenPublishService(@Autowired @Qualifier("tokenRepository")
															   TokenRepository tokenRepository) {
			return new TokenPublishService(tokenRepository);
		}

		@Bean
		public TokenRepository tokenRepository() {
			return new TokenRepository();
		}
	}*/

	@Autowired
	private RandomPushService randomPushService;
	@MockBean
	private RandomPushRepository randomPushRepository;
	@MockBean
	private RandomPushDetailRepository randomPushDetailRepository;


	@BeforeEach
	void init() {
		//given(token.getRoomId()).willReturn("1");
		//given(token.getUserId()).willReturn("wks4j1004");
	}

	@Test
	public void 테스트() {
		//randomPushService.getRandomPush();

		given(randomPushRepository.findBy())

		randomPushService.getRandomPush()


	}


}
