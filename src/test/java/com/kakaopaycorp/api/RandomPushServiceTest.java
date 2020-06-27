package com.kakaopaycorp.api;

import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.slf4j.Slf4j;

import com.kakaopaycorp.api.domain.event.model.RandomPush;
import com.kakaopaycorp.api.domain.event.model.RandomPushDetail;
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
	void 뿌린건_유효시간_만료_체크() {
		RandomPush randomPush = new RandomPush();
		given(randomPush.getRegistDateTime()).willReturn(LocalDateTime.now().minusMinutes(15));
		Assertions.assertTrue(randomPushService.isExpired(randomPush));
	}

	@Test
	void 뿌리기_검증_동일사용자() {
		RandomPush randomPush = new RandomPush();
		given(randomPush.getRegistUserId()).willReturn("1234");
		Assertions.assertFalse(randomPushService.validatePublish(randomPush, randomPush));
	}

	@Test
	void 뿌리기_검증_이미발급받은사용자() {
		RandomPush randomPush = RandomPush.builder()
										  .registUserId("abc")
										  .roomId("1").build();
		RandomPushDetail randomPushDetail = RandomPushDetail.builder()
															.useYn(true)
															.registUserId("ghi")
															.publishUserId("abc")
															.build();
		List<RandomPushDetail> randomPushDetailLists = new ArrayList<>();
		randomPushDetailLists.add(randomPushDetail);
		RandomPush existRandomPush = RandomPush.builder()
											   .registUserId("ghi")
											   .roomId("1")
											   .details(randomPushDetailLists)
											   .build();

		Assertions.assertFalse(randomPushService.validatePublish(existRandomPush, randomPush));
	}

	@Test
	void 뿌리기_발급() {
		RandomPush randomPush = RandomPush.builder()
										  .registUserId("abc")
										  .build();
		RandomPushDetail randomPushDetail = RandomPushDetail.builder()
															.useYn(true)
															.registUserId("ghi")
															.publishedPrice(1000)
															.build();
		List<RandomPushDetail> randomPushDetailLists = new ArrayList<>();
		randomPushDetailLists.add(randomPushDetail);
		RandomPush existRandomPush = RandomPush.builder()
											   .registUserId("ghi")
											   .details(randomPushDetailLists)
											   .build();
		Assertions.assertEquals(1000, randomPushService.publish(existRandomPush, randomPush));
	}
}
