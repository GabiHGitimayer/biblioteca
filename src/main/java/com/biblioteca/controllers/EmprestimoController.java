package com.biblioteca.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.services.EmprestimoService;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;
    
    @PostMapping("/emprestimo")
    public ResponseEntity<EmprestimoEntity> createEmprestimo(@RequestBody EmprestimoEntity emprestimo) {
        try {
            EmprestimoEntity salvo = emprestimoService.save(emprestimo);
            return ResponseEntity.ok(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/localizar")
    public ResponseEntity<List<EmprestimoEntity>> getAllEmprestimos() {
        try {
            List<EmprestimoEntity> emprestimos = emprestimoService.findAll();
            return ResponseEntity.ok(emprestimos);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/localizar/{id}")
    public ResponseEntity<EmprestimoEntity> getEmprestimoById(@PathVariable Long id) {
        try {
            Optional<EmprestimoEntity> emprestimo = emprestimoService.findById(id);
            if (emprestimo.isPresent()) {
                return ResponseEntity.ok(emprestimo.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PutMapping("/renovar/{id}")
    public ResponseEntity<EmprestimoEntity> updateEmprestimo(@PathVariable Long id, @RequestBody EmprestimoEntity emprestimoDetails) {
        try {
            Optional<EmprestimoEntity> emprestimoOpt = emprestimoService.findById(id);
            if (emprestimoOpt.isPresent()) {
                EmprestimoEntity updatedEmprestimo = emprestimoOpt.get();          
                updatedEmprestimo.setDataEmprestimo(emprestimoDetails.getDataEmprestimo());
                updatedEmprestimo.setDataDevolucao(emprestimoDetails.getDataDevolucao());
                return ResponseEntity.ok(emprestimoService.save(updatedEmprestimo));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deleteEmprestimo(@PathVariable Long id) {
        try {
            emprestimoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }
}
