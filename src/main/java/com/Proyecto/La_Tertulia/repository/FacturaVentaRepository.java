package com.Proyecto.La_Tertulia.repository;

import com.Proyecto.La_Tertulia.model.FacturaVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaVentaRepository extends JpaRepository<FacturaVenta, Long> {
}