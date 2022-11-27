package reccomendationengine.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import reccomendationengine.SharedPostgresqlnMemoryDBContainer;
import reccomendationengine.model.BrowserHistory;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace=Replace.NONE)
class TestBrowserHistoryRepository {
	@Autowired
	private BrowserHistoryRepository repository;
	
	@ClassRule
    public static PostgreSQLContainer postgreSQLContainer = SharedPostgresqlnMemoryDBContainer.getInstance();


	@BeforeAll
	static public void startPostgres() {
		postgreSQLContainer.start();
	}
	
	@Test
	@Sql("classpath:test-data.sql")
	public void shouldListSingleUser() {

		List<BrowserHistory> test = repository.findByUseridOrderByAtTimeDesc("user-78", PageRequest.of(0, 10));
		assertThat(test).isNotEmpty();
		assertThat(test).singleElement();
	}

	@Test
	@Sql("classpath:test-data.sql")
	public void shouldListTenUsers() {

		List<BrowserHistory> test = repository.findByUseridOrderByAtTimeDesc("user-57", PageRequest.of(0, 10));
		assertThat(test).isNotEmpty();
		assertThat(test).size().isEqualTo(10);
		assertThat(test).first().hasFieldOrPropertyWithValue("when", LocalDateTime.parse("2022-11-27T10:01:45.881"));
	}
	
	@Test
	@Sql("classpath:test-data.sql")
	public void shouldDeleteBrowserHistory() {

		repository.deleteByProductidAndUserid("product-166","user-57");
		List<BrowserHistory> test = repository.findByUseridOrderByAtTimeDesc("user-57", PageRequest.of(0, 10));
		assertThat(test).isEmpty();
	}
}
