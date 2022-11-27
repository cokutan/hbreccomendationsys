package reccomendationengine.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import reccomendationengine.dto.BrowserHistoryDTO;
import reccomendationengine.model.BrowserHistory;

public class RecommendationListMapper {

	public BrowserHistoryDTO mapToDto(String userId, List<String> products,  String type) {
		BrowserHistoryDTO browserHistoryDTO = new BrowserHistoryDTO(userId,
				products, type);
		
		return browserHistoryDTO;
	}
}