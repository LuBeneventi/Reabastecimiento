package com.MSReabastecimiento.Reabastecimiento.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reabastecimiento {
    
    private int idReabastecimiento;
    private int idProveedor;
    private LocalDate fechaCreacion;

    @Enumerated(EnumType.STRING)
    private estadoReabastecimiento estado;
    private List<itemReabastecimiento> items = new ArrayList<>();

}
