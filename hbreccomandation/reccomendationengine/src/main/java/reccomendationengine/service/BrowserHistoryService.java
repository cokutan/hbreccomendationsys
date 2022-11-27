package reccomendationengine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reccomendationengine.dto.BrowserHistoryDTO;
import reccomendationengine.mapper.BrowserHistoryMapper;
import reccomendationengine.model.BrowserHistory;
import reccomendationengine.repository.BrowserHistoryRepository;

@Service
@Transactional
public class BrowserHistoryService {
	@Autowired
	private BrowserHistoryRepository browserHistoryRepository;
	
	
	@Transactional(readOnly = true)
	public BrowserHistoryDTO retrieveTenLastViews(String userid) {
		List<BrowserHistory> browserHistoryList = browserHistoryRepository.findByUseridOrderByAtTimeDesc(userid, PageRequest.of(0, 10));
		
		BrowserHistoryDTO browserHistoryDTO = new BrowserHistoryMapper().mapToDto(userid, browserHistoryList);
		
		return browserHistoryDTO;
	}
	
	@Transactional
	public void deleteViews(String userid, String productid) {
		browserHistoryRepository.deleteByProductidAndUserid(productid, userid);
	}
}
