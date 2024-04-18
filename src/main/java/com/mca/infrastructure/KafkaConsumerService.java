package com.mca.infrastructure;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mca.entity.Stock;
import com.mca.exception.StockNotFoundException;
import com.mca.infrastructure.model.VideoGameEvent;
import com.mca.service.StockService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import static java.lang.Math.toIntExact;

@Log4j2
@Service
public class KafkaConsumerService {

    @Autowired
    private StockService stockService;

    @Autowired
    private Gson gson;

    @KafkaListener(topics = "MCA", groupId = "group_id")
    public void consumeMessage(String message, Acknowledgment acknowledgment) {
        log.info(message);
        try {
            VideoGameEvent event = gson.fromJson(message, VideoGameEvent.class);
            Stock stock = Stock.builder()
                    .id(toIntExact(event.getStock_id()))
                    .availability(event.isAvailability())
                    .lastUpdated(event.getTime_update())
                    .build();
            stockService.updateStock(stock);
        } catch (StockNotFoundException | JsonSyntaxException | ArithmeticException exception){
            log.warn(exception);
        }
        acknowledgment.acknowledge();
    }
}