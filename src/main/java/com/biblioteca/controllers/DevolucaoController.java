package com.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.DevolucaoEntity;
import com.biblioteca.services.DevolucaoService;

@RestController
@RequestMapping("/devolucoes")
public class DevolucaoController {
    @Autowired
    DevolucaoService devolucaoService;

    @PostMapping("/saveDevolucoes/{idEmprestimo}")
    public ResponseEntity<DevolucaoEntity> registrarDevolucao(
            @PathVariable Long idEmprestimo, 
            @RequestBody DevolucaoEntity devolucaoEntity) {

        try {
            DevolucaoEntity devolucaoRegistrada = devolucaoService.registrarDevolucao(idEmprestimo, devolucaoEntity);
            return ResponseEntity.ok(devolucaoRegistrada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    @GetMapping("/listDevolucoes")
    public ResponseEntity<List<DevolucaoEntity>> listAll() {
        try {
            return ResponseEntity.ok(devolucaoService.listAllDevolucao());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/findById/{idDevolucao}")
    public ResponseEntity<DevolucaoEntity> findById(@PathVariable Long idDevolucao) {
        try {
            return ResponseEntity.ok(devolucaoService.findDevolucaoById(idDevolucao));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
