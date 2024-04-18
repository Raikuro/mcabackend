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

        List<Game> gameInfoByIds = gameRepository.findGameInfoByIdsAndDate(videogamesIdsInSaga,
                Timestamp.from(Instant.parse(date)));

        Map<Integer, Game> gameMap = gameInfoByIds
                .stream()
                .collect(Collectors.toMap((Game game) -> Integer.valueOf(game.getId()), Function.identity()));

        return videogamesIdsInSaga.stream()
                .map(gameMap::get)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
