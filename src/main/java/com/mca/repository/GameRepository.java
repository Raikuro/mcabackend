package com.mca.repository;

import com.mca.model.Game;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class GameRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Game> findGameInfoByIdsAndDate(Set<Integer> ids, Timestamp date) {
        Query query = entityManager.createNativeQuery(
                "SELECT " +
                        "CAST(VG.ID as varchar(20)) as id, VG.TITLE, COALESCE(P.PRICE, -1) AS PRICE, S.AVAILABILITY " +
                    "FROM " +
                        "VIDEOGAME VG " +
                    "LEFT JOIN (" +
                        "SELECT P.VIDEOGAME_ID, P.PRICE " +
                        "FROM PROMOTION P " +
                        "WHERE (P.VIDEOGAME_ID, P.VALID_FROM) IN (" +
                            "SELECT P2.VIDEOGAME_ID, MAX(P2.VALID_FROM) " +
                            "FROM PROMOTION P2 " +
                            "WHERE P2.VALID_FROM < :date " +
                            "GROUP BY P2.VIDEOGAME_ID" +
                        ")" +
                    ") P ON VG.ID = P.VIDEOGAME_ID " +
                    "LEFT JOIN STOCK S ON VG.ID = S.VIDEOGAME_ID " +
                    "WHERE VG.ID IN :ids ", Game.class);
        query.setParameter("ids", ids);
        query.setParameter("date", date);
        return query.getResultList();
    }

}
