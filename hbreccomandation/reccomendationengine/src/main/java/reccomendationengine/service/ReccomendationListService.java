package reccomendationengine.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reccomendationengine.dto.BrowserHistoryDTO;
import reccomendationengine.mapper.BrowserHistoryMapper;
import reccomendationengine.repository.BrowserHistoryRepository;
import reccomendationengine.repository.ReccomendationListRepository;

@Service
@Transactional
public class ReccomendationListService {
	private static final String PERSONALIZED = "personalized";
	private static final String NON_PERSONALIZED = "non-personalized";
	
	private static final int LEAST_NUMBER_FOR_RECCOMENDATION_LIST = 5;

	@Autowired
	private ReccomendationListRepository reccomendationListRepository;

	@Autowired
	private BrowserHistoryRepository browserHistoryRepository;

	@Transactional(readOnly = true)
	public BrowserHistoryDTO retrieveRecommendedProductsForUser(String userid) {
		if (browserHistoryRepository.existsByUserid(userid)) {
			return retrieveRelatedStrategyData(userid,
					reccomendationListRepository.retrievePersonalizedStrategyData(userid), PERSONALIZED);
		} else {
			return retrieveRelatedStrategyData(userid,
					reccomendationListRepository.retrieveNonPersonalizedStrategyData(), NON_PERSONALIZED);
		}
	}

	private BrowserHistoryDTO retrieveRelatedStrategyData(String userid, List<String> products, String type) {
		if (products.size() < LEAST_NUMBER_FOR_RECCOMENDATION_LIST) {
			products = Collections.emptyList();
		}
		BrowserHistoryDTO browserHistoryDTO = new BrowserHistoryMapper().mapToDto(userid, products, type);

		return browserHistoryDTO;
	}
}
