package com.cinema.cinema_projet.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nomClient;
    private double prix;
    //@Column(unique = false ,nullable = true)
    private Integer codePayement;
    private boolean reserve;
    @ManyToOne
    private Place place;

   @ManyToOne
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Projection projection;
}
