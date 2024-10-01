package com.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.HistoricoEntity;
import com.biblioteca.services.HistoricoService;

@RestController
@RequestMapping("/historico")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @GetMapping("/listar/{idUsuario}")
    public ResponseEntity<List<HistoricoEntity>> listarHistorico(@PathVariable Long idUsuario) {
        try {
            List<HistoricoEntity> historico = historicoService.listarHistorico(idUsuario);
            return ResponseEntity.ok(historico);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(null); // Forbidden access
        }
    }
}
