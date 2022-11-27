package reccomendationengine.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import reccomendationengine.dto.BrowserHistoryDTO;
import reccomendationengine.model.BrowserHistory;

public class BrowserHistoryMapper {

	public BrowserHistoryDTO mapToDto(String userid, List<BrowserHistory> browserHistoryList) {
		ArrayList<String> products = browserHistoryList.stream().map(BrowserHistory::getProductid).collect(Collectors
		        .toCollection(ArrayList::new));
		return mapToDto(userid, products, "personalized" );
	}

	public BrowserHistoryDTO mapToDto(String userid, List<String> products, String type) {
		BrowserHistoryDTO browserHistoryDTO = new BrowserHistoryDTO(userid,
				 products, type);
		
		return browserHistoryDTO;
	}
}