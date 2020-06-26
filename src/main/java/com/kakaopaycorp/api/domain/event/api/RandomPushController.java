package com.kakaopaycorp.api.domain.event.api;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopaycorp.api.domain.event.dto.ApiResult;
import com.kakaopaycorp.api.domain.event.dto.RandomPushRequestDto;
import com.kakaopaycorp.api.domain.event.dto.RandomPushSearchDto;
import com.kakaopaycorp.api.domain.event.dto.RandomPushStatusDto;
import com.kakaopaycorp.api.domain.event.model.RandomPush;
import com.kakaopaycorp.api.domain.event.service.RandomPushService;

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
	public ApiResult getRandomPushPrice(@ModelAttribute RandomPushRequestDto requestDto) {
		// 토큰 가져오기
		String token = randomPushService.publishToken(requestDto);
		requestDto.setToken(token);
		// 뿌리기 저장
		randomPushService.save(requestDto);
		return new ApiResult<>(token);
	}

	/**
	 * 받기 API
	 *
	 * @return
	 */
	@PostMapping("/publish")
	public ApiResult<Integer> publish(@RequestBody RandomPushRequestDto requestDto) {

		// 토큰 만료 체크
		if (randomPushService.isExpired(requestDto)) {
			return ApiResult.fail("token is expired");
		}

		// 검증
		RandomPush existRandomPush = randomPushService.getRandomPush(new RandomPushSearchDto(requestDto.getToken(),
																							 requestDto.getRoomId(),
																							 null));
		RandomPush randomPush = new RandomPush(requestDto);
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
	public ApiResult<RandomPushStatusDto> search(@RequestBody RandomPushRequestDto requestDto) {
		RandomPushStatusDto randomPushStatus = randomPushService.getRandomPushStatus(requestDto);
		return new ApiResult<>(randomPushStatus);
	}

	@ExceptionHandler
	public ApiResult handleException(Exception ex) {
		return ApiResult.fail(ex.getMessage());
	}
}
