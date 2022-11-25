package org.viewProducerApp.viewproductmessageproducer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	    props.put(JsonSerializer.TYPE_MAPPINGS, "viewproductmessage: org.viewProducerApp.viewproductmessageproducer.ViewProductMessage");

		return props;
	}

	@Bean
	public ProducerFactory<String, ViewProductMessage> producerFactory() {

		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaTemplate<String, ViewProductMessage> kafkaTemplate() {
		KafkaTemplate<String, ViewProductMessage> kafkaTemplate = new KafkaTemplate<>(producerFactory());
		kafkaTemplate.setProducerListener(new ProducerListener<String, ViewProductMessage>() {
			@Override
			public void onSuccess(ProducerRecord<String, ViewProductMessage> producerRecord,
					RecordMetadata recordMetadata) {
				//logger.info("OOOOOO"+producerRecord.value().getWhen());
				logger.info("ACK from ProducerListener message: {} offset:  {}", producerRecord.value().messageid,
						recordMetadata.offset());
			}
		});
		return kafkaTemplate;
	}

}