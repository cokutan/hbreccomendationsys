package org.viewProducerApp;

import java.io.File;
import java.io.IOException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.kafka.core.KafkaTemplate;
import org.viewProducerApp.viewproductmessageproducer.ViewProductMessage;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

//@Service
public class ViewProductApp {

	@Value("viewProduct")
	private String topicName;

	private KafkaTemplate<String, ViewProductMessage> kafkaTemplate;
	private ResourceLoader resourceLoader;
	
	public ViewProductApp(KafkaTemplate<String, ViewProductMessage> kafkaTemplate, ResourceLoader loader) {
		this.kafkaTemplate = kafkaTemplate;
		this.resourceLoader = loader;
	}

//@Scheduled(cron = "* * * * * *")
	public void sendMessage() {

		final JsonMapper mapper = new JsonMapper();
		mapper.registerModule(new JavaTimeModule());
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
				Thread.sleep(2000);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}