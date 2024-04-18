package com.mca.exception;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class GameNotFoundException extends RuntimeException{
    private String gameId;
}
