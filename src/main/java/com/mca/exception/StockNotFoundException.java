package com.mca.exception;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class StockNotFoundException extends RuntimeException{
    private int stockId;
}
