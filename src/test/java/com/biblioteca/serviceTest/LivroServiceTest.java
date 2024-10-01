package com.biblioteca.serviceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.biblioteca.entities.LivroEntity;
import com.biblioteca.repositories.LivroRepository;
import com.biblioteca.services.LivroService;

import java.util.Optional;

@SpringBootTest
public class LivroServiceTest {

    @Autowired
    LivroService livroService;

    @MockBean
    LivroRepository livroRepository;

    @Test
    @DisplayName("Testa salvar livro")
    void saveLivroTest() {
        LivroEntity livro = new LivroEntity();
        livro.setTitulo("Senhor dos Aneis");

        Mockito.when(livroRepository.save(Mockito.any(LivroEntity.class)))
               .thenReturn(livro);

        LivroEntity savedLivro = livroService.save(livro);

        Assertions.assertEquals("Senhor dos Aneis", savedLivro.getTitulo());
    }

    @Test
    @DisplayName("Testa buscar livro por ID")
    void findLivroByIdTest() {
        LivroEntity livro = new LivroEntity(1L, "Senhor dos Aneis", null, null, null, null, null);

        Mockito.when(livroRepository.findById(1L))
               .thenReturn(Optional.of(livro));

        Optional<LivroEntity> foundLivro = livroService.findById(1L);

        Assertions.assertTrue(foundLivro.isPresent());
        Assertions.assertEquals("Senhor dos Aneis", foundLivro.get().getTitulo());
    }

    @Test
    @DisplayName("Testa deletar livro")
    void deleteLivroTest() {
        livroService.deleteById(1L);

        Mockito.verify(livroRepository, Mockito.times(1)).deleteById(1L);
    }
}
