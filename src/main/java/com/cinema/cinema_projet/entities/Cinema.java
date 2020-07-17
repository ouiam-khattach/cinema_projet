package com.cinema.cinema_projet.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double longitude,latitude,altitude;
    private int nombreSalle;

   @OneToMany(mappedBy = "cinema")
    private Collection<Salle> salles;
   @ManyToOne
    private Ville ville;

}
//nom de l attribut et nom pas la classe
//    /*pour montrer que c est une relation bidirectionnelle = mappedBy*/