package com.biblioteca.controllerTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.biblioteca.controllers.LivroController;
import com.biblioteca.entities.LivroEntity;
import com.biblioteca.services.LivroService;

@WebMvcTest(LivroController.class)
public class LivroControllerTest {
    @Autowired
    LivroController livroController;

    @MockBean
    LivroService livroService;

    @Test
    @DisplayName("Testa salvar livro")
    void saveLivroTest() {
        LivroEntity livro = new LivroEntity();
        livro.setTitulo("Persuasão");
        Mockito.when(livroService.save(Mockito.any(LivroEntity.class)))
               .thenReturn(livro);
        ResponseEntity<LivroEntity> response = livroController.createLivro(livro);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Persuasão", response.getBody().getTitulo());
    }

    @Test
    @DisplayName("Testa salvar livro com erro")
    void saveLivroErrorTest() {
        LivroEntity livro = new LivroEntity();
        Mockito.when(livroService.save(Mockito.any(LivroEntity.class)))
               .thenThrow(new RuntimeException("Erro ao salvar"));
        ResponseEntity<LivroEntity> response = livroController.createLivro(livro);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    @DisplayName("Testa listar todos os livros")
    void listAllLivrosTest() {
        List<LivroEntity> livros = new ArrayList<>();
        livros.add(new LivroEntity(1L, "Harry Potter", null, null, null, null, null));
        livros.add(new LivroEntity(2L, "Percy Jackson", null, null, null, null, null));
        Mockito.when(livroService.findAll())
               .thenReturn(livros);
        ResponseEntity<List<LivroEntity>> response = livroController.getAllLivros();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    @DisplayName("Testa listar todos os livros com erro")
    void listAllLivrosErrorTest() {
        Mockito.when(livroService.findAll())
               .thenThrow(new RuntimeException("Erro ao buscar livros"));
        ResponseEntity<List<LivroEntity>> response = livroController.getAllLivros();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    @DisplayName("Testa buscar livro por ID")
    void findLivroByIdTest() {
        LivroEntity livro = new LivroEntity(1L, "Persuasão", null, null, null, null, null);
        Mockito.when(livroService.findById(1L))
               .thenReturn(Optional.of(livro));
        ResponseEntity<LivroEntity> response = livroController.getLivroById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Persuasão", response.getBody().getTitulo());
    }

    @Test
    @DisplayName("Testa buscar livro por ID inexistente")
    void findLivroByIdNotFoundTest() {
        Mockito.when(livroService.findById(1L))
               .thenReturn(Optional.empty());
        ResponseEntity<LivroEntity> response = livroController.getLivroById(1L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Testa atualizar livro")
    void updateLivroTest() {
        LivroEntity existingLivro = new LivroEntity(1L, "Senhor dos Aneis", "J. R. R. Tolkien", "Aventura", "ISBN", 1954, 5);
        LivroEntity updatedLivro = new LivroEntity(1L, "Senhor dos Aneis - A Sociedade do Anel", "J. R. R. Tolkien", "Aventura", "ISBN", 1954, 5);
        
        Mockito.when(livroService.findById(1L)).thenReturn(Optional.of(existingLivro));
        Mockito.when(livroService.save(Mockito.any(LivroEntity.class))).thenReturn(updatedLivro);

        ResponseEntity<LivroEntity> response = livroController.updateLivro(1L, updatedLivro);
        
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Senhor dos Aneis - A Sociedade do Anel", response.getBody().getTitulo());
    }

    @Test
    @DisplayName("Testa atualizar livro inexistente")
    void updateLivroNotFoundTest() {
        LivroEntity updatedLivro = new LivroEntity(1L, "As duas torres", "J. R. R. Tolkien", "Aventura", "ISBN", 1954, 5);
        
        Mockito.when(livroService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<LivroEntity> response = livroController.updateLivro(1L, updatedLivro);
        
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Testa deletar livro")
    void deleteLivroTest() {
        Mockito.doNothing().when(livroService).deleteById(1L);
        ResponseEntity<Void> response = livroController.deleteLivro(1L);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Testa deletar livro com erro")
    void deleteLivroErrorTest() {
        Mockito.doThrow(new RuntimeException("Erro ao deletar")).when(livroService).deleteById(1L);
        ResponseEntity<Void> response = livroController.deleteLivro(1L);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}