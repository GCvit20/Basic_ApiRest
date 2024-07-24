package com.application.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fabricante")
public class Maker {

    @Id //Establecemos que el id sea clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //JPA generara el ID unico
    private Long id;

    @Column(name = "nombre")
    private  String name;

    @OneToMany(mappedBy = "maker", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) //OneToMany(metodo de mappeo, tipo de movimiento, Fetch Lazy sirve para que no sobrecargue la lista, orphanRemoval (si eliminamos un maker, se eliminan los productos ))
    @JsonIgnore
    private List<Product> productList = new ArrayList<>();

}
