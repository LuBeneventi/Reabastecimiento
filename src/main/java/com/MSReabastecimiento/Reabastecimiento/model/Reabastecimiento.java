package com.MSReabastecimiento.Reabastecimiento.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reabastecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReabastecimiento;

    @Column(nullable = false)
    private int idProveedor;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaCreacion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private estadoReabastecimiento estado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<itemReabastecimiento> items = new ArrayList<>();

}
