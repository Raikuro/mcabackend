package com.mca.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Videogame {
    @Id
    @GeneratedValue
    private int id;
    private String title;
}