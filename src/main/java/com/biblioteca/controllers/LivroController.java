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

import com.biblioteca.entities.LivroEntity;
import com.biblioteca.services.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping("/cadastrar")
    public ResponseEntity<LivroEntity> createLivro(@RequestBody LivroEntity livro) {
        try {
            LivroEntity salvo = livroService.save(livro);
            return ResponseEntity.ok(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
    
    @GetMapping("/localizar")
    public ResponseEntity<List<LivroEntity>> getAllLivros() {
        try {
            List<LivroEntity> livros = livroService.findAll();
            return ResponseEntity.ok(livros);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/localizar/{id}")
    public ResponseEntity<LivroEntity> getLivroById(@PathVariable Long id) {
        try {
            Optional<LivroEntity> livro = livroService.findById(id);
            if (livro.isPresent()) {
                return ResponseEntity.ok(livro.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<LivroEntity> updateLivro(@PathVariable Long id, @RequestBody LivroEntity livroDetails) {
        try {
            Optional<LivroEntity> livroOpt = livroService.findById(id);
            if (livroOpt.isPresent()) {
                LivroEntity updatedLivro = livroOpt.get();
                updatedLivro.setTitulo(livroDetails.getTitulo());
                updatedLivro.setAutor(livroDetails.getAutor());
                updatedLivro.setGenero(livroDetails.getGenero());
                updatedLivro.setIsbn(livroDetails.getIsbn());
                updatedLivro.setAnoPublicacao(livroDetails.getAnoPublicacao());
                updatedLivro.setQuantExemplares(livroDetails.getQuantExemplares());
                return ResponseEntity.ok(livroService.save(updatedLivro));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        try {
            livroService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }
}
