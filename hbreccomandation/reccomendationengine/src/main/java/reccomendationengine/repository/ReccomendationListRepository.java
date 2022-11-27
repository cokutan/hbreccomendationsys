package reccomendationengine.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class ReccomendationListRepository {
	@Autowired
	private EntityManager entityManager;

	private static final String PERSONALIZED_QUERY = "select\n"
			+ "	product_id \n"
			+ "from\n"
			+ "	nonpersonalized_strategy_data nsd2\n"
			+ "where\n"
			+ "	category_id in\n"
			+ "(\n"
			+ "	select\n"
			+ "				category_id\n"
			+ "	from(\n"
			+ "		select\n"
			+ "				p.category_id,\n"
			+ "			rank() over (partition by p.category_id\n"
			+ "		order by\n"
			+ "			bh.at_time desc) as rownum\n"
			+ "		from\n"
			+ "			browser_history bh\n"
			+ "		inner join products p on\n"
			+ "			bh.productid = p.product_id\n"
			+ "		\n"
			+ "		where\n"
			+ "				bh.userid = :userid) as t\n"
			+ "	where\n"
			+ "		t.rownum = 1\n"
			+ "	limit 3)\n"
			+ "order by\n"
			+ "	nsd2.number_of_distinct_users desc\n"
			+ "limit 10;";

	private static final String NON_PERSONALIZED_QUERY = "select\n"
			+ "	product_id \n"
			+ "from\n"
			+ "	nonpersonalized_strategy_data nsd2\n"
			+ "	\n"
			+ "order by\n"
			+ "	nsd2.number_of_distinct_users desc\n"
			+ "limit 10;\n";
	
	@Transactional
	public List<String> retrievePersonalizedStrategyData(String userid) {
		return entityManager.createNativeQuery(PERSONALIZED_QUERY)
				.setParameter("userid", userid)
				.getResultList();
	}
	
	@Transactional
	public List<String> retrieveNonPersonalizedStrategyData() {
		return entityManager.createNativeQuery(NON_PERSONALIZED_QUERY)
				.getResultList();
	}
}
