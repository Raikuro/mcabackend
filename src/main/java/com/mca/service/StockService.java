package com.mca.service;

import com.mca.entity.Stock;
import com.mca.exception.StockNotFoundException;
import com.mca.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Transactional
    public Stock updateStock(Stock update) {
        Stock current = stockRepository
                .findById(update.getId())
                .orElseThrow(() -> new StockNotFoundException(update.getId()));
        current.setAvailability(update.isAvailability());
        current.setLastUpdated(update.getLastUpdated());
        return stockRepository.save(current);
    }
}
