package com.Proyecto.La_Tertulia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Proyecto.La_Tertulia.model.Aguardiente;

@Repository
public interface AguardienteRepository extends JpaRepository<Aguardiente, Long>{

}
