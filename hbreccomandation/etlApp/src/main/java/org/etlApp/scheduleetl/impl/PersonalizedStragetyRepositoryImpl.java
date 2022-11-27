package org.etlApp.scheduleetl.impl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.etlApp.scheduleetl.PersonalizedStragetyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonalizedStragetyRepositoryImpl implements PersonalizedStragetyRepository {
	
	@Autowired
	private EntityManager em;

	String query = "drop table if exists nonpersonalized_strategy_data; create table nonpersonalized_strategy_data as (select count(*) as number_of_distinct_users,product_id , category_id from\n"
			+ "\n"
			+ "(select distinct\n"
			+ "	user_id as userid,\n"
			+ "	p.category_id, oi.product_id \n"
			+ "from\n"
			+ "	order_items oi\n"
			+ "inner join orders o on\n"
			+ "	o.order_id = oi.order_id\n"
			+ "inner join products p on\n"
			+ "	p.product_id = oi.product_id) as temp\n"
			+ "	\n"
			+ "group by product_id, category_id\n"
			+ ");";
	
	
	@Override
	@Transactional
	public void createTableForNonPersonalizedStrategyRepository() {
		em.createNativeQuery(query).executeUpdate();
		
	}


	
	

}
