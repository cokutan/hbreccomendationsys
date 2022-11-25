package org.streamReaderApp.viewproductmessageconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.streamReaderApp.model.BrowserHistory;

@Component
@KafkaListener(id = "class-level", topics = "viewProduct")
class KafkaClassListener {
	Logger logger = LoggerFactory.getLogger(KafkaClassListener.class);

	@Autowired
	CrudRepositoryForBrowserHistory repository;
	
	@KafkaHandler(isDefault = true)
	void listen(ViewProductMessage message) {
		// logger.info("KafkaHandler[String] {}"," =============================== "+
		// message.getWhen());
		logger.info("KafkaHandler[String] {}", " =============================== " + message.getUserid());

		BrowserHistory browerserHistory = new BrowserHistory(message.getProperties().getProductid(),
				message.getUserid(), message.getWhen());
		
		 repository.save(browerserHistory);
	}

}