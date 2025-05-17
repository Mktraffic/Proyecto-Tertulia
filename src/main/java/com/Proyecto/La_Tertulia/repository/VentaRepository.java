package com.Proyecto.La_Tertulia.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Proyecto.La_Tertulia.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
      List<Venta> findByFechaVentaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
