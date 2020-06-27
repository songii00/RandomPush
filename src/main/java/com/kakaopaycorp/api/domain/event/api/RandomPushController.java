package com.kakaopaycorp.api.domain.event.api;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopaycorp.api.domain.event.dto.ApiResultDto;
import com.kakaopaycorp.api.domain.event.dto.RandomPushRequestDto;
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
	public ApiResultDto getRandomPushPrice(@ModelAttribute RandomPushRequestDto requestDto) {
		// 토큰 가져오기
		String token = randomPushService.publishToken();
		requestDto.setToken(token);
		// 뿌리기 저장
		randomPushService.save(requestDto);
		return new ApiResultDto<>(token);
	}

	/**
	 * 받기 API
	 *
	 * @return
	 */
	@PostMapping("/publish")
	public ApiResultDto<Integer> publish(@RequestBody RandomPushRequestDto requestDto) {
		RandomPush existRandomPush = randomPushService.getRandomPush(new RandomPushRequestDto.Search(requestDto.getToken(),
																									 requestDto.getRoomId(),
																									 null));
		// 토큰 만료 체크
		if (randomPushService.isExpired(existRandomPush)) {
			return ApiResultDto.fail("token is expired");
		}

		// 뿌리기 검증
		RandomPush randomPush = requestDto.toEntity();
		if (randomPushService.validate(existRandomPush, randomPush)) {
			return ApiResultDto.fail("validation fail");
		}

		// 뿌리기 발급
		Integer publishPrice = randomPushService.publish(existRandomPush, randomPush);
		return new ApiResultDto(publishPrice);
	}

	/**
	 * 조회 API
	 *
	 * @return
	 */
	@GetMapping("/status")
	public ApiResultDto<RandomPushRequestDto.Status> search(@RequestBody RandomPushRequestDto requestDto) {
		RandomPushRequestDto.Status randomPushStatus = randomPushService.getRandomPushStatus(requestDto);
		return new ApiResultDto<>(randomPushStatus);
	}

	@ExceptionHandler
	public ApiResultDto handleException(Exception ex) {
		return ApiResultDto.fail(ex.getMessage());
	}
}
