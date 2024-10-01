package com.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.HistoricoEntity;

@Repository
public interface HistoricoRepository extends JpaRepository<HistoricoEntity, Long> {
	
    
}
