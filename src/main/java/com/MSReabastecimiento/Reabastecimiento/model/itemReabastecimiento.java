package com.MSReabastecimiento.Reabastecimiento.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class itemReabastecimiento {
    
    private int idProducto;
    private int cantidad;

}
