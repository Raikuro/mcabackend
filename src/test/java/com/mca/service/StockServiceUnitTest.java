package com.mca.service;

import com.mca.entity.Stock;
import com.mca.entity.Videogame;
import com.mca.exception.StockNotFoundException;
import com.mca.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StockServiceUnitTest {

    @InjectMocks
    private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    @Test
    public void updateStock(){
        String timestamp = "2023-01-31T04:49:44.832Z";
        boolean availability = true;
        int id = 5;
        Timestamp updateTimestamp = Timestamp.from(Instant.parse(timestamp).plus(1, ChronoUnit.DAYS));
        Stock updateStock = new Stock(id, !availability, updateTimestamp, null);
        Videogame videogame = new Videogame();
        videogame.setId(id);
        Stock currentStock = new Stock(id, availability, Timestamp.from(Instant.parse(timestamp)), videogame);
        Stock finalStock = new Stock(id, updateStock.isAvailability(), updateStock.getLastUpdated(), videogame);
        when(stockRepository.findById(eq(id))).thenReturn(Optional.of(currentStock));

        stockService.updateStock(updateStock);

        verify(stockRepository).save(eq(finalStock));
    }

    @Test
    public void updateStockButStockNotFound(){
        String timestamp = "2023-01-31T04:49:44.832Z";
        boolean availability = true;
        int id = 5;
        Timestamp updateTimestamp = Timestamp.from(Instant.parse(timestamp).plus(1, ChronoUnit.DAYS));
        Stock updateStock = new Stock(id, !availability, updateTimestamp, null);
        when(stockRepository.findById(eq(id))).thenReturn(Optional.empty());

        assertThrows(StockNotFoundException.class, () -> stockService.updateStock(updateStock));
    }

}