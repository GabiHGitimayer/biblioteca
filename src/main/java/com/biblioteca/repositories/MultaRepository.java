package com.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.MultaEntity;

@Repository
public interface MultaRepository extends JpaRepository<MultaEntity, Long> {
    
}
