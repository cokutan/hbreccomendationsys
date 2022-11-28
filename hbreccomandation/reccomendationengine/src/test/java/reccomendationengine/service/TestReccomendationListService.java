package reccomendationengine.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import reccomendationengine.SharedPostgresqlnMemoryDBContainer;
import reccomendationengine.dto.BrowserHistoryDTO;
import reccomendationengine.mapper.BrowserHistoryMapper;
import reccomendationengine.model.BrowserHistory;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
class TestReccomendationListService {
	@Autowired
	private ReccomendationListService reccomendationListService;

	@ClassRule
	public static PostgreSQLContainer postgreSQLContainer = SharedPostgresqlnMemoryDBContainer.getInstance();

	@BeforeAll
	static public void startPostgres() {
		postgreSQLContainer.start();
	}

	@Test
	@Sql("classpath:test-data.sql")
	public void shouldReturnPersonalizedDTO() {

		new BrowserHistoryService();
		BrowserHistoryDTO test = reccomendationListService.retrieveRecommendedProductsForUser("user-120");
		List<String> productList = Stream.of("product-56", "product-393", "product-3", "product-466", "product-458",
				"product-715", "product-755", "product-730", "product-448").toList();
		BrowserHistoryDTO browserHistoryDTO = new BrowserHistoryMapper().mapToDto("user-120", productList,
				"personalized");
		assertThat(test).isNotNull();
		assertThat(test).usingRecursiveComparison().isEqualTo(browserHistoryDTO);
		assertThat(test).extracting("type").isEqualTo("personalized");
	}

	@Test
	@Sql("classpath:test-data.sql")
	public void shouldReturnEmptyListHistoryDTO() {

		new BrowserHistoryService();
		BrowserHistoryDTO test = reccomendationListService.retrieveRecommendedProductsForUser("user-xx");
		List<String> productList = Stream.of("product-757", "product-955", "product-294", "product-811",
		"product-942", "product-268", "product-48", "product-706", "product-323", "product-938").toList();
		BrowserHistoryDTO browserHistoryDTO = new BrowserHistoryMapper().mapToDto("user-xx", productList,
				"non-personalized");
		assertThat(test).isNotNull();
		assertThat(test).usingRecursiveComparison().isEqualTo(browserHistoryDTO);
		assertThat(test).extracting("type").isEqualTo("non-personalized");
	}

}
