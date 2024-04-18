package com.mca.controller;

import com.mca.model.Game;
import com.mca.service.SagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class SagaController implements GameApi {

    @Autowired
    private SagaService sagaService;

    @Override
    public ResponseEntity<Set<Game>> gameGameIdSagaGet(String gameId) {
        return new ResponseEntity<>(sagaService.getSaga(gameId), HttpStatus.OK);
    }
}
