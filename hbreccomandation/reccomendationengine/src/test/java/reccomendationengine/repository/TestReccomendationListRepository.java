package reccomendationengine.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.util.Arrays;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import reccomendationengine.SharedPostgresqlnMemoryDBContainer;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
class TestReccomendationListRepository {

	@Autowired
	private ReccomendationListRepository repository;

	@ClassRule
	public static PostgreSQLContainer postgreSQLContainer = SharedPostgresqlnMemoryDBContainer.getInstance();

	@BeforeAll
	
	static public void startPostgres() {
		postgreSQLContainer.start();
	}

	@Test
	@Sql("classpath:test-data.sql")
	public void shouldList9Product() {
		List<String> test = repository.retrievePersonalizedStrategyData("user-120");
		assertThat(test).size().isEqualTo(9);
		assertThat(test).usingRecursiveComparison()
				.isEqualTo(Arrays.asList(new String[] { "product-56", "product-393", "product-3", "product-466",
						"product-458", "product-715", "product-755", "product-730", "product-448" }));
	}

	@Test
	@Sql("classpath:test-data.sql")
	public void shouldReturnEmptyList() {
		List<String> test = repository.retrievePersonalizedStrategyData("user-None");
		assertThat(test).isEmpty();
	}
	
	@Test
	@Sql("classpath:test-data.sql")
	public void shouldReturnNonpersonalizedList() {
		List<String> test = repository.retrieveNonPersonalizedStrategyData();
		assertThat(test).size().isEqualTo(10);
		assertThat(test).usingRecursiveComparison()
		.isEqualTo(Arrays.asList(new String[] { "product-757", "product-955", "product-294", "product-811",
				"product-942", "product-268", "product-48", "product-706", "product-323", "product-938" }));
	}
}
