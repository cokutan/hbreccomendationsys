package org.viewProducerApp.viewproductmessageconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.viewProducerApp.viewproductmessageproducer.ViewProductMessage;


@Component
@KafkaListener(id = "class-level", topics = "viewProduct")
class KafkaClassListener {
	  Logger logger = LoggerFactory.getLogger(KafkaClassListener.class);

  @KafkaHandler(isDefault = true)
  void listen(ViewProductMessage message) {
	  logger.info("KafkaHandler[String] {}", message.getMessageid()+" =============================== ");
  }

  
  
}