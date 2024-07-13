package com.desarrollo.bankinc.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "controlSaldos")
@Table(name = "control_saldos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class controlSaldos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long  id;

    @Column(name = "id_tc", nullable = false)
    private long idTc;

    @Column(name = "saldo_actual", nullable = false)
    private Integer saldoActual;

    @Column(name = "saldo_anterior", nullable = false)
    private Integer saldoAnterior;

    @Column(name = "valor_ultima_recarga", nullable = false)
    private Integer valorUltimaRecarga;
}
