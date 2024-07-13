package com.desarrollo.bankinc.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "infoTarjetas")
@Table(name = "info_tarjetas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class infoTarjetas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "nombre_titular", nullable = false, length = 100)
    private String nombreTitular;

    @Column(name = "apellido_titular", nullable = false, length = 100)
    private String apellidoTitular;

    @Column(name = "numero_tc", nullable = false, length = 16)
    private String numeroTc;

    @Column(name = "numero_tc_enmascarada", nullable = false, length = 16)
    private String numeroTcEnmascarada;

    @Column(name = "fecha_tc", nullable = false)
    private Date fechaTc;

    @Column(name = "id_producto", nullable = false)
    private long idProducto;

    @Column(name = "ind_activo", nullable = false)
    private Boolean indActivo;
}
