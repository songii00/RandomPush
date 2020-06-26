package com.kakaopaycorp.api.event;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopaycorp.api.authorization.token.model.Token;
import com.kakaopaycorp.api.common.model.ApiResult;
import com.kakaopaycorp.api.event.model.RandomPush;
import com.kakaopaycorp.api.event.service.RandomPushService;

@RestController
@RequestMapping("/event/random-push")
public class RandomPushController {

	private final RandomPushService randomPushService;

	public RandomPushController(RandomPushService randomPushService) {
		this.randomPushService = randomPushService;
	}

	/**
	 * 뿌리기 API
	 *
	 * @return
	 */
	@GetMapping("/price")
	public ApiResult getRandomPushPrice(@ModelAttribute RandomPush.Request request) {
		// 토큰 가져오기
		Token token = randomPushService.publishToken(request);
		request.setToken(token);
		// 뿌리기 저장
		randomPushService.save(request);
		return new ApiResult<>(token.getTokenId());
	}

	/**
	 * 받기 API
	 *
	 * @return
	 */
	@PostMapping("/publish")
	public ApiResult<Integer> publish(@RequestBody RandomPush.Request request) {

		// 토큰 만료 체크
		if (request.getToken().isExpired()) {
			return ApiResult.fail("token is expired");
		}

		// 검증
		RandomPush existRandomPush = randomPushService.getRandomPush(new RandomPush.Search(request.getToken().getTokenId(),
																						   request.getRoomId()));
		RandomPush randomPush = new RandomPush(request);
		if (randomPushService.validate(existRandomPush, randomPush)) {
			return ApiResult.fail();
		}

		// 뿌리기 발급
		Integer publishPrice = randomPushService.publishToken(existRandomPush, randomPush);
		return new ApiResult(publishPrice);
	}

	/**
	 * 조회 API
	 *
	 * @return
	 */
	@GetMapping("/status")
	public ApiResult<RandomPush.Status> search(@RequestParam Token token) {
		RandomPush.Status randomPushStatus = randomPushService.getRandomPushStatus(token);
		return new ApiResult<>(randomPushStatus);
	}

	@ExceptionHandler
	public ApiResult handleException(Exception ex) {
		return ApiResult.fail(ex.getMessage());
	}
}
