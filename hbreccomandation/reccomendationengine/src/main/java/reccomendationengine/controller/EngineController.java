package reccomendationengine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reccomendationengine.dto.BrowserHistoryDTO;
import reccomendationengine.service.BrowserHistoryService;
import reccomendationengine.service.ReccomendationListService;

@RestController
@RequestMapping("/rest/hbreccomendation")
public class EngineController {
	@Autowired
	private BrowserHistoryService browserHistoryService;

	@Autowired
	private ReccomendationListService reccomendationListService;

	@GetMapping(value = "/user-histories/{userId}")
	public BrowserHistoryDTO listUserHistory(@PathVariable String userId) {
		return browserHistoryService.retrieveTenLastViews(userId);
	}

	@DeleteMapping(value = "/delete-history/{userId}/{productId}")
	public void deleteUserHistory(@PathVariable String userId, @PathVariable String productId) {
		browserHistoryService.deleteViews(userId, productId);
	}

	@GetMapping(value = "/reccomendations/{userId}")
	public BrowserHistoryDTO listReccomendations(@PathVariable String userId) {
		return reccomendationListService.retrieveRecommendedProductsForUser(userId);
	}
}
