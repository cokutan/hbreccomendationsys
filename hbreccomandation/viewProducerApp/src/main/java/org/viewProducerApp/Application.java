package org.viewProducerApp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.viewProducerApp.viewproductmessageproducer.ViewProductMessage;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
@EnableKafka
public class Application {

    public static void main(String[] args) {
    	
        SpringApplication.run(Application.class, args);
    }
   
    @Bean
    ApplicationRunner applicationRunner(KafkaTemplate<String, ViewProductMessage> kafkaTemplate, ResourceLoader loader) {
        return args -> {
        	final JsonMapper mapper = new JsonMapper();
    		Resource resource = loader.getResource("classpath:product-views.json");
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
    						"viewProduct", viewProductMessage.getMessageid().toString(), viewProductMessage);

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

        };
    }

}