package com.kakaopaycorp.api.domain.event.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kakaopaycorp.api.domain.event.dto.RandomPushRequestDto;
import com.kakaopaycorp.api.domain.event.dto.RandomPushSearchDto;
import com.kakaopaycorp.api.domain.event.dto.RandomPushStatusDto;
import com.kakaopaycorp.api.domain.event.model.RandomPush;
import com.kakaopaycorp.api.domain.event.model.RandomPushDetail;
import com.kakaopaycorp.api.domain.event.repository.RandomPushDetailRepository;
import com.kakaopaycorp.api.domain.event.repository.RandomPushRepository;

@Service
public class RandomPushService {

	private final RandomPushRepository randomPushRepository;
	private final RandomPushDetailRepository randomPushDetailRepository;

	public RandomPushService(RandomPushRepository randomPushRepository,
							 RandomPushDetailRepository randomPushDetailRepository) {
		this.randomPushRepository = randomPushRepository;
		this.randomPushDetailRepository = randomPushDetailRepository;
	}

	/**
	 * 뿌리기 저장
	 *
	 * @param requestDto
	 */
	public void save(RandomPushRequestDto requestDto) {
		List<RandomPushDetail> randomPushDetailes = devide(requestDto);
		Integer randomPushNo = randomPushRepository.save(new RandomPush(requestDto));
		randomPushDetailes.stream().forEach(detail -> detail.setRandomPushNo(randomPushNo));
		randomPushDetailRepository.save(randomPushDetailes);
	}

	/**
	 * 뿌리기 금액 분배
	 *
	 * @param requestDto
	 * @return
	 */
	private List<RandomPushDetail> devide(RandomPushRequestDto requestDto) {
		int totalPushPrice = requestDto.getTotalPushPrice();
		int userCount = requestDto.getUserCount();
		List<Integer> randomPrices = getRandomPrices(totalPushPrice, userCount);

		return randomPrices.stream().map(randomPrice -> RandomPushDetail.builder()
																		.publishedPrice(randomPrice)
																		.registDateTime(LocalDateTime.now())
																		.registUserId(requestDto.getUserId())
																		.build()).collect(Collectors.toList());
	}

	private List<Integer> getRandomPrices(int totalPushPrice, int userCount) {

		ArrayList<Integer> randomPrices = new ArrayList<>();
		for (int i = 0; i < userCount; i++) {

			//맨 마지막 숫자의 경우
			if (i == userCount - 1) {
				randomPrices.add(totalPushPrice);
				continue;
			}

			int price = ((int) (Math.random() * totalPushPrice) + 100) / 100;
			totalPushPrice -= price;
			randomPrices.add(price);
		}
		return randomPrices;
	}

	public RandomPushStatusDto getRandomPushStatus(RandomPushRequestDto requestDto) {
		RandomPush randomPush = randomPushRepository.findBy(new RandomPushSearchDto(requestDto.getToken(), requestDto.getRoomId(), null));

		// 뿌린 사람 자신만 조회
		if (!randomPush.getRegistUserId().equals(requestDto.getUserId())) {
			throw new IllegalArgumentException("invalid userId");
		}

		// 뿌린 건에 대해 7일간 조회
		if (randomPush.getRegistDateTime().plusDays(7).isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("expire search request");
		}

		List<RandomPushDetail> details = randomPushDetailRepository.findByRandomPushNo(randomPush.getRandomPushNo());
		List<RandomPushStatusDto.publishedInfo> publishedInfos
				= details.stream()
						 .filter(RandomPushDetail::isUseYn)
						 .map(detail -> new RandomPushStatusDto.publishedInfo(detail.getPublishedPrice(), detail.getRegistUserId()))
						 .collect(Collectors.toList());


		return RandomPushStatusDto.builder()
								  .pushTime(randomPush.getRegistDateTime())
								  .pushPrice(randomPush.getPushPrice())
								  .publishedPrice(randomPush.getPublishedPrice())
								  .publishedInfos(publishedInfos)
								  .build();
	}

	public RandomPush getRandomPush(RandomPushSearchDto searchDto) {
		return randomPushRepository.findBy(searchDto);
	}

	/**
	 * 뿌리기 검증
	 *
	 * @param existRandomPush
	 * @param randomPush
	 * @return
	 */
	public boolean validate(RandomPush existRandomPush, RandomPush randomPush) {

		// 등록 사용자와 동일한 사용자인지 검증
		if (existRandomPush.getRegistUserId().equals(randomPush.getRegistUserId())) {
			return false;
		}
		List<RandomPushDetail> usedRandomPushDetails =
				existRandomPush.getDetails().stream()
							   .filter(RandomPushDetail::isUseYn)
							   .collect(Collectors.toList());

		// 사용할 수 있는 받기 목록이 없음
		if (CollectionUtils.isEmpty(usedRandomPushDetails)) {
			return false;
		}

		long matchedUserCount = usedRandomPushDetails.stream()
													 .filter(detail -> detail.getPublishUserId().equals(randomPush.getRegistUserId()))
													 .count();

		// 뿌리기당 사용자는 한번만 받을 수 있음
		if (matchedUserCount > 0) {
			return false;
		}

		// 동일 대화방의 사용자만 받을 수 있음
		if (!randomPush.getRoomId().equals(existRandomPush.getRoomId())) {
			return false;
		}

		return true;
	}


	/**
	 * 뿌리기 발급
	 *
	 * @param existRandomPush
	 * @param randomPush
	 * @return
	 */
	public Integer publish(RandomPush existRandomPush, RandomPush randomPush) {
		List<RandomPushDetail> details = existRandomPush.getDetails();

		List<RandomPushDetail> usableDetails = details.stream()
													  .filter(detail -> !detail.isUseYn())
													  .collect(Collectors.toList());
		RandomPushDetail detail = usableDetails.get(0);

		detail.publish(randomPush.getRegistUserId());

		randomPushDetailRepository.save(detail);
		randomPushRepository.increasePublishedPrice(detail.getPublishedPrice());

		return detail.getPublishedPrice();
	}

	/**
	 * 뿌린 건 유효시간 만료 체크
	 *
	 * @param randomPush
	 * @return
	 */
	public boolean isExpired(RandomPush randomPush) {
		return LocalDateTime.now().minusMinutes(10).isAfter(randomPush.getRegistDateTime());
	}

	/**
	 * 토큰 발급
	 *
	 * @return
	 */
	public String publishToken() {
		String token = TokenKeygen.publishToken();
		return getHashKeyBy(token);
	}

	private String getHashKeyBy(String token) {
		return TokenKeygen.getHashKeyBy(token);
	}
}
