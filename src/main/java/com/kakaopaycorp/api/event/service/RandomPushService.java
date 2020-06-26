package com.kakaopaycorp.api.event.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kakaopaycorp.api.authorization.token.model.Token;
import com.kakaopaycorp.api.authorization.token.service.TokenPublishService;
import com.kakaopaycorp.api.event.model.RandomPush;
import com.kakaopaycorp.api.event.repository.RandomPushDetailRepository;
import com.kakaopaycorp.api.event.repository.RandomPushRepository;

@Service
public class RandomPushService {

	private final TokenPublishService tokenPublishService;
	private final RandomPushRepository randomPushRepository;
	private final RandomPushDetailRepository randomPushDetailRepository;

	public RandomPushService(TokenPublishService tokenPublishService, RandomPushRepository randomPushRepository,
							 RandomPushDetailRepository randomPushDetailRepository) {
		this.tokenPublishService = tokenPublishService;
		this.randomPushRepository = randomPushRepository;
		this.randomPushDetailRepository = randomPushDetailRepository;
	}

	public Token publishToken(RandomPush.Request request) {
		return tokenPublishService.publishToken(request.getRoomId(), request.getUserId());
	}

	/**
	 * 뿌리기 저장
	 *
	 * @param request
	 */
	public void save(RandomPush.Request request) {
		List<RandomPush.Detail> randomPushDetailes = devide(request);
		Integer randomPushNo = randomPushRepository.save(new RandomPush(request));
		randomPushDetailes.stream().forEach(detail -> detail.setRandomPushNo(randomPushNo));
		randomPushDetailRepository.save(randomPushDetailes);
	}

	/**
	 * 뿌리기 분배
	 *
	 * @param request
	 * @return
	 */
	private List<RandomPush.Detail> devide(RandomPush.Request request) {
		int totalPushPrice = request.getTotalPushPrice();
		int userCount = request.getUserCount();
		List<Integer> randomPrices = getRandomPrices(totalPushPrice, userCount);

		return randomPrices.stream().map(randomPrice -> RandomPush.Detail.builder()
																		 .publishedPrice(randomPrice)
																		 .registDateTime(LocalDateTime.now())
																		 .registUserId(request.getUserId())
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

	public RandomPush.Status getRandomPushStatus(Token token) {
		RandomPush randomPush = randomPushRepository.findBy(new RandomPush.Search(token.getTokenId(),
																				  token.getRoomId()));

		if (!randomPush.getRegistUserId().equals(token.getUserId())) {
			throw new IllegalArgumentException("invalid userId");
		}
		List<RandomPush.Detail> details = randomPushDetailRepository.findByRandomPushNo(randomPush.getRandomPushNo());

		List<RandomPush.Status.publishedInfo> publishedInfos
				= details.stream()
						 .filter(detail -> detail.isUseYn())
						 .map(detail -> new RandomPush.Status.publishedInfo(detail.getPublishedPrice(), detail.getRegistUserId()))
						 .collect(Collectors.toList());


		return RandomPush.Status.builder()
								.pushTime(randomPush.getRegistDateTime())
								.pushPrice(randomPush.getPushPrice())
								.publishedPrice(randomPush.getPublishedPrice())
								.publishedInfos(publishedInfos)
								.build();
	}

	public RandomPush getRandomPush(RandomPush.Search search) {
		return randomPushRepository.findBy(search);
	}

	public boolean validate(RandomPush existRandomPush, RandomPush randomPush) {

		// 등록 사용자와 동일한 사용자인지 검증
		if (existRandomPush.getRegistUserId().equals(randomPush.getRegistUserId())) {
			return false;
		}
		List<RandomPush.Detail> usedRandomPushDetails =
				existRandomPush.getDetails().stream()
							   .filter(detail -> detail.getRegistUserId().equals(randomPush.getRegistUserId()))
							   .collect(Collectors.toList());

		// 뿌리기당 사용자는 한번만 받을 수 있음
		if (!CollectionUtils.isEmpty(usedRandomPushDetails)) {
			return false;
		}

		// 동일 대화방의 사용자만 받을 수 있음
		if (!randomPush.getRoomId().equals(existRandomPush.getRoomId())) {
			return false;
		}

		return true;
	}


	public Integer publishToken(RandomPush existRandomPush, RandomPush randomPush) {
		List<RandomPush.Detail> details = existRandomPush.getDetails();

		List<RandomPush.Detail> usableDetails = details.stream().filter(detail -> !detail.isUseYn()).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(usableDetails)) {
			throw new IllegalArgumentException("usableDetails is empty! radomPushNo : " + randomPush.getRandomPushNo());
		}

		RandomPush.Detail detail = usableDetails.get(0);
		detail.publish(randomPush.getRegistUserId());
		randomPushDetailRepository.save(detail);
		return detail.getPublishedPrice();
	}
}
