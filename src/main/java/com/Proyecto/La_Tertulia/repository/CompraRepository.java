package com.Proyecto.La_Tertulia.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Proyecto.La_Tertulia.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByFechaCompraBetween(LocalDate inicio, LocalDate fin);
}
