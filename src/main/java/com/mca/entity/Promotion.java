package com.mca.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
public class Promotion {
    @Id
    @GeneratedValue
    private int id;
    private Timestamp validFrom;
    private BigDecimal price;
    @ManyToOne
    private Videogame videogameId;
}