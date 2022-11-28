package org.etlApp.scheduleetl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NonPersonalizedStrategyDataFormer {

	@Autowired
	PersonalizedStragetyRepository personalizedStragetyRepository;

	@Scheduled(cron = "@monthly")
	public void createRepository() {
		personalizedStragetyRepository.createTableForNonPersonalizedStrategyRepository();
	}
}
