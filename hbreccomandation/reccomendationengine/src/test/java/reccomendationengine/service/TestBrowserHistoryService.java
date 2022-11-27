package reccomendationengine.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import reccomendationengine.Application;
import reccomendationengine.SharedPostgresqlnMemoryDBContainer;
import reccomendationengine.dto.BrowserHistoryDTO;
import reccomendationengine.mapper.BrowserHistoryMapper;
import reccomendationengine.model.BrowserHistory;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)

class TestBrowserHistoryService {
	@Autowired
	private BrowserHistoryService browserHistoryService;

	@ClassRule
    public static PostgreSQLContainer postgreSQLContainer = SharedPostgresqlnMemoryDBContainer.getInstance();


	@BeforeAll
	static public void startPostgres() {
		postgreSQLContainer.start();
	}
	
	@Test
	@Sql("classpath:test-data.sql")
	public void shouldReturnDTO() {

		new BrowserHistoryService();
		BrowserHistoryDTO test = browserHistoryService.retrieveTenLastViews("user-120");
		List<BrowserHistory> browserHistoryList = new ArrayList<BrowserHistory>();
		browserHistoryList
				.add(new BrowserHistory("product-3", "user-120", LocalDateTime.parse("2022-11-30T10:01:17.842")));
		browserHistoryList
				.add(new BrowserHistory("product-393", "user-120", LocalDateTime.parse("2022-11-26T10:01:17.842")));

		BrowserHistoryDTO browserHistoryDTO = new BrowserHistoryMapper().mapToDto("user-120", browserHistoryList);
		assertThat(test).isNotNull();
		assertThat(test).usingRecursiveComparison().isEqualTo(browserHistoryDTO);
		assertThat(test).extracting("type").isEqualTo("personalized");
	}
}
