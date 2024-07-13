package com.desarrollo.bankinc.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "productos")
@Table(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Productos {

    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "nombre_producto",nullable = false)
    private String nombre_producto;

    @Column(name = "codigo", nullable = false)
    private String codigo;
}
