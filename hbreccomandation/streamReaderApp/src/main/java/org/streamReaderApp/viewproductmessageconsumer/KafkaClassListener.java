package org.streamReaderApp.viewproductmessageconsumer;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@KafkaListener(id = "class-level", topics = "viewProduct")
class KafkaClassListener {
	  Logger logger = LoggerFactory.getLogger(KafkaClassListener.class);

  @KafkaHandler(isDefault = true)
  void listen(ViewProductMessage message) {
	  logger.info("KafkaHandler[String] {}"," =============================== "+ message.getWhen());
	  logger.info("KafkaHandler[String] {}"," =============================== "+ message.getUserid());
	  
  }

  
  
}