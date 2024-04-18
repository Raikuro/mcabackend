package com.mca.service;

import com.mca.exception.GameNotFoundException;
import com.mca.infrastructure.client.api.DefaultApi;
import com.mca.model.Game;
import com.mca.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.sql.Timestamp.from;
import static java.time.Instant.parse;

@Service
public class SagaService {

    @Value("${date}")
    private String date;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private DefaultApi gameSagaApi;

    public Set<Game> getSaga(String gameId) {
        Set<Integer> videogamesIdsInSaga = gameSagaApi
                .getGameSagaRelatedSagas(gameId)
                .onErrorMap(x -> new GameNotFoundException(gameId))
                .block()
                .stream()
                .map(Integer::valueOf)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Map<Integer, Game> gameMap = gameRepository.findGameInfoByIdsAndDate(videogamesIdsInSaga, from(parse(date)))
                .stream()
                .collect(Collectors.toMap((Game game) -> Integer.valueOf(game.getId()), Function.identity()));

        return videogamesIdsInSaga.stream()
                .map(gameMap::get)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
