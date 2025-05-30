package com.MSReabastecimiento.Reabastecimiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MSReabastecimiento.Reabastecimiento.model.Reabastecimiento;

@Repository
public interface reabastecimientoRepository extends JpaRepository<Reabastecimiento, Integer> {

    List<Reabastecimiento> findByIdProveedor(int idProveedor);
    
}
