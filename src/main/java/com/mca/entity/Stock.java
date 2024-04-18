package com.mca.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.sql.Timestamp;


@Data
@Entity
public class Stock {
    @Id
    @GeneratedValue
    private int id;
    private boolean availability;
    private Timestamp lastUpdated;
    @ManyToOne
    private Videogame videogameId;
}