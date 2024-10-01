package com.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.EmprestimoEntity;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoEntity, Long> {
    
}
