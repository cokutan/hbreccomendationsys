package org.viewProducerApp;

import java.io.File;
import java.io.IOException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.viewProducerApp.viewproductmessageproducer.ViewProductMessage;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Component
public class ViewProductApp {

	@Value("viewProduct")
	private String topicName;

	private KafkaTemplate<String, ViewProductMessage> kafkaTemplate;
	private ResourceLoader resourceLoader;
	
	public ViewProductApp(KafkaTemplate<String, ViewProductMessage> kafkaTemplate, ResourceLoader loader) {
		this.kafkaTemplate = kafkaTemplate;
		resourceLoader = loader;
	}

	@Scheduled(cron = "*/2 * * * * *")
	public void sendMessage() {

		final JsonMapper mapper = new JsonMapper();
		
		Resource resource = resourceLoader.getResource("classpath:product-views.json");
        File input=null;
		try {
			input = resource.getFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		try (MappingIterator<ViewProductMessage> it = mapper.readerFor(ViewProductMessage.class).readValues(input)) {
			while (it.hasNextValue()) {
				ViewProductMessage viewProductMessage = it.nextValue();
				ProducerRecord<String, ViewProductMessage> record = new ProducerRecord<String, ViewProductMessage>(
						topicName, viewProductMessage.getMessageid().toString(), viewProductMessage);

				kafkaTemplate.send(record);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}