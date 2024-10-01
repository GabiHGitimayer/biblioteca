package com.biblioteca.controllerTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.biblioteca.controllers.EmprestimoController;
import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.services.EmprestimoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(EmprestimoController.class)
public class EmprestimoControllerTest {
    @Autowired
    EmprestimoController emprestimoController;

    @MockBean
    EmprestimoService emprestimoService;

    @Test
    @DisplayName("Testa criar empréstimo")
    void createEmprestimoTest() {
        EmprestimoEntity emprestimo = new EmprestimoEntity();
        Mockito.when(emprestimoService.save(Mockito.any(EmprestimoEntity.class)))
               .thenReturn(emprestimo);
        ResponseEntity<EmprestimoEntity> response = emprestimoController.createEmprestimo(emprestimo);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Testa criar empréstimo com exceção")
    void createEmprestimoExceptionTest() {
        EmprestimoEntity emprestimo = new EmprestimoEntity();
        Mockito.when(emprestimoService.save(Mockito.any(EmprestimoEntity.class)))
               .thenThrow(new RuntimeException("Erro ao salvar"));
        ResponseEntity<EmprestimoEntity> response = emprestimoController.createEmprestimo(emprestimo);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    @DisplayName("Testa listar todos os empréstimos")
    void listAllEmprestimosTest() {
        List<EmprestimoEntity> emprestimos = new ArrayList<>();
        emprestimos.add(new EmprestimoEntity());
        Mockito.when(emprestimoService.findAll())
               .thenReturn(emprestimos);
        ResponseEntity<List<EmprestimoEntity>> response = emprestimoController.getAllEmprestimos();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Testa listar todos os empréstimos quando lista vazia")
    void listAllEmprestimosEmptyTest() {
        Mockito.when(emprestimoService.findAll())
               .thenReturn(new ArrayList<>());
        ResponseEntity<List<EmprestimoEntity>> response = emprestimoController.getAllEmprestimos();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().isEmpty());
    }

    @Test
    @DisplayName("Testa buscar empréstimo por ID")
    void findEmprestimoByIdTest() {
        EmprestimoEntity emprestimo = new EmprestimoEntity();
        Mockito.when(emprestimoService.findById(1L))
               .thenReturn(Optional.of(emprestimo));
        ResponseEntity<EmprestimoEntity> response = emprestimoController.getEmprestimoById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Testa buscar empréstimo por ID inexistente")
    void findEmprestimoByIdNotFoundTest() {
        Mockito.when(emprestimoService.findById(1L))
               .thenReturn(Optional.empty());
        ResponseEntity<EmprestimoEntity> response = emprestimoController.getEmprestimoById(1L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Testa atualizar empréstimo")
    void updateEmprestimoTest() {
        EmprestimoEntity emprestimo = new EmprestimoEntity();
        emprestimo.setIdEmprestimo(1L);
        Mockito.when(emprestimoService.findById(1L))
               .thenReturn(Optional.of(emprestimo));
        Mockito.when(emprestimoService.save(Mockito.any(EmprestimoEntity.class)))
               .thenReturn(emprestimo);
        ResponseEntity<EmprestimoEntity> response = emprestimoController.updateEmprestimo(1L, emprestimo);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Testa atualizar empréstimo inexistente")
    void updateEmprestimoNotFoundTest() {
        EmprestimoEntity emprestimo = new EmprestimoEntity();
        emprestimo.setIdEmprestimo(1L);
        Mockito.when(emprestimoService.findById(1L))
               .thenReturn(Optional.empty());
        ResponseEntity<EmprestimoEntity> response = emprestimoController.updateEmprestimo(1L, emprestimo);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Testa deletar empréstimo")
    void deleteEmprestimoTest() {
        Mockito.doNothing().when(emprestimoService).deleteById(1L);
        ResponseEntity<Void> response = emprestimoController.deleteEmprestimo(1L);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Testa deletar empréstimo com exceção")
    void deleteEmprestimoExceptionTest() {
        Mockito.doThrow(new RuntimeException("Erro ao deletar")).when(emprestimoService).deleteById(1L);
        ResponseEntity<Void> response = emprestimoController.deleteEmprestimo(1L);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}