package com.biblioteca.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.MultaEntity;
import com.biblioteca.services.EmprestimoService;
import com.biblioteca.services.MultaService;

@RestController
@RequestMapping("/multas")
public class MultaController {

    @Autowired
    private MultaService multaService;

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping("/calcular/{emprestimoId}")
    public ResponseEntity<MultaEntity> calcularMulta(@PathVariable Long emprestimoId) {
        try {
            Optional<EmprestimoEntity> emprestimo = emprestimoService.findById(emprestimoId);
            if (emprestimo.isPresent()) {
                MultaEntity multa = multaService.calcularMulta(emprestimo.get());
                return ResponseEntity.ok(multa);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
