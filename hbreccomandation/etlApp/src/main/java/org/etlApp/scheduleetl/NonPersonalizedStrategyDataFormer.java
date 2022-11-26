package org.etlApp.scheduleetl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NonPersonalizedStrategyDataFormer {

	@Autowired
	PersonalizedStragetyRepository personalizedStragetyRepository;
	
	@Scheduled(cron ="@monthly")
	@PostConstruct
	void createRepository() {
		personalizedStragetyRepository.createTableForPersonalizedStrategyRepository();
	}
}
