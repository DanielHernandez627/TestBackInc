package com.desarrollo.bankinc.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "controlTransacciones")
@Table(name = "control_transacciones")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class controlTransacciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long  id;

    @Column(name = "nombre_comercio", nullable = false)
    private String nombrecomercio;

    @Column(name = "valor_compra", nullable = false)
    private Integer valorcompra;

    @Column(name = "fecha_compra", nullable = false)
    private Date fechacompra;

    @Column(name = "id_tc", nullable = false)
    private long idtc;

}
