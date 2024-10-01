package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.LivroEntity;
import com.biblioteca.repositories.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<LivroEntity> findAll() {
        return livroRepository.findAll();
    }

    public Optional<LivroEntity> findById(Long id) {
        return livroRepository.findById(id);
    }

    public LivroEntity save(LivroEntity livro) {
        return livroRepository.save(livro);
    }

    public void deleteById(Long id) {
        livroRepository.deleteById(id);
    }
}
