package com.mca.infrastructure;

import com.google.gson.Gson;
import com.mca.entity.Stock;
import com.mca.exception.StockNotFoundException;
import com.mca.infrastructure.model.VideoGameEvent;
import com.mca.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Timestamp;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DirtiesContext
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceUnitTest {

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @Mock
    private Acknowledgment acknowledgment;

    @Mock
    private StockService stockService;

    @Mock
    private Gson gson;

    @Test
    public void messageIsProcess(){
        String timestamp = "2023-01-31T04:49:44.832Z";
        boolean availability = true;
        int id = 5;
        String message = String.format("%d,%b,%s", id, availability, timestamp);
        when(gson.fromJson(eq(message), eq(VideoGameEvent.class)))
                .thenReturn(VideoGameEvent.builder()
                        .stock_id((long) id)
                        .availability(availability)
                        .time_update((Timestamp.from(Instant.parse(timestamp))))
                        .build());

        kafkaConsumerService.consumeMessage(message, acknowledgment);

        Stock expected = Stock.builder()
                .id(id)
                .availability(availability)
                .lastUpdated(Timestamp.from(Instant.parse(timestamp)))
                .build();
        verify(stockService).updateStock(eq(expected));
        verify(acknowledgment).acknowledge();
    }

    @Test
    public void messageIdIsGreaterThanIntegerMax(){
        String timestamp = "2023-01-31T04:49:44.832Z";
        boolean availability = true;
        int id = 5;
        String message = String.format("%d,%b,%s", id, availability, timestamp);
        when(gson.fromJson(eq(message), eq(VideoGameEvent.class)))
                .thenReturn(VideoGameEvent.builder()
                        .stock_id(((long)Integer.MAX_VALUE) + 1)
                        .availability(availability)
                        .time_update((Timestamp.from(Instant.parse(timestamp))))
                        .build());

        kafkaConsumerService.consumeMessage(message, acknowledgment);
        verify(stockService, times(0)).updateStock(any());
        verify(acknowledgment).acknowledge();
    }

    @Test
    public void messageIsProcessButNoPresentInDB(){
        String timestamp = "2023-01-31T04:49:44.832Z";
        boolean availability = true;
        int id = 5;
        String message = String.format("%d,%b,%s", id, availability, timestamp);
        when(gson.fromJson(eq(message), eq(VideoGameEvent.class)))
                .thenReturn(VideoGameEvent.builder()
                        .stock_id((long) id)
                        .availability(availability)
                        .time_update((Timestamp.from(Instant.parse(timestamp))))
                        .build());
        when(stockService.updateStock(any())).thenThrow(new StockNotFoundException(id));

        kafkaConsumerService.consumeMessage(message, acknowledgment);

        verify(acknowledgment).acknowledge();
    }

}