package org.example.taskscheduling.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type; // Example: "Fire, Water"
    private String category;
    private String description;
    private String image; // URL for the Pokémon image
    private int height; // In decimetres
    private int weight; // In hectograms
}

