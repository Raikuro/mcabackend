package com.mca.repository;

import com.mca.entity.Stock;
import org.springframework.data.repository.ListCrudRepository;

public interface StockRepository extends ListCrudRepository<Stock, Integer> {
}
