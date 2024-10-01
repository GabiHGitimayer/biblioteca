package com.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/save")
    public ResponseEntity<UsuarioEntity> save(@RequestBody UsuarioEntity usuarioEntity) {
        try {
            return ResponseEntity.ok(usuarioService.saveUser(usuarioEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/listUsers")
    public ResponseEntity<List<UsuarioEntity>> listAll() {
        try {
            return ResponseEntity.ok(usuarioService.listAllUsers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/findById/{idUsuario}")
    public ResponseEntity<UsuarioEntity> findById(@Validated @PathVariable Long idUsuario) {
        try {
            return ResponseEntity.ok(usuarioService.findUserbyId(idUsuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/deletarUsuario/{idUsuario}")
    public ResponseEntity<Void> delete(@PathVariable Long idUsuario) {
        try {
            usuarioService.deleteUsuario(idUsuario);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/alterarUsuario/{idUsuario}")
    public ResponseEntity<UsuarioEntity> alterar(@PathVariable Long idUsuario, @RequestBody UsuarioEntity novaUsuarioEntity) {
        try {
            UsuarioEntity usuarioAlterado = usuarioService.alterarUsuario(idUsuario, novaUsuarioEntity);
            return ResponseEntity.ok(usuarioAlterado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
