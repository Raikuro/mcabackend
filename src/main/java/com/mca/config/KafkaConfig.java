package com.mca.config;

import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.mca.infrastructure.KafkaMessageProducer;
import com.mca.infrastructure.model.VideoGameEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.util.ResourceUtils;

@EnableKafka
@Configuration
public class KafkaConfig implements CommandLineRunner {
	@Autowired
	KafkaMessageProducer kafkaMessageProducer;

	@Value("classpath:events.csv")
	Resource resourceFile;

	@Autowired
	private Gson gson;
	@Override
	public void run(String... args) throws Exception {
		try {
			List<VideoGameEvent> stocks = Files
					.readAllLines(ResourceUtils.getFile(resourceFile.getURL()).toPath()).stream()
					.map(line -> convertStock(Arrays.asList(line.trim().split(",")))).collect(Collectors.toList());
			stocks.forEach(stock -> kafkaMessageProducer.sendMessage(String.valueOf(stock.getStock_id()),gson.toJson(stock).toString()));
		} catch (Exception e) {
			new Exception("Error in producer");
		}
	}

	public VideoGameEvent convertStock(List<String> stock) {
		return VideoGameEvent.builder().stock_id(Long.parseLong(stock.get(0)))
				.availability(Boolean.parseBoolean(stock.get(1)))
				.time_update(Timestamp.valueOf(
						LocalDateTime.parse(stock.get(2), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"))))
				.build();
	}
}
