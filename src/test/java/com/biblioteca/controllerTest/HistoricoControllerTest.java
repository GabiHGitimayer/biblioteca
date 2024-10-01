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

import com.biblioteca.controllers.HistoricoController;
import com.biblioteca.entities.HistoricoEntity;
import com.biblioteca.services.HistoricoService;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(HistoricoController.class)
public class HistoricoControllerTest {

    @Autowired
    HistoricoController historicoController;

    @MockBean
    HistoricoService historicoService;

    @Test
    @DisplayName("Testa listar histórico por ID de usuário")
    void listarHistoricoTest() throws Exception {
        List<HistoricoEntity> historico = new ArrayList<>();
        historico.add(new HistoricoEntity());

        Mockito.when(historicoService.listarHistorico(1L))
               .thenReturn(historico);

        ResponseEntity<List<HistoricoEntity>> response = historicoController.listarHistorico(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Testa listar histórico com acesso negado")
    void listarHistoricoAcessoNegadoTest() throws Exception {
        Mockito.when(historicoService.listarHistorico(1L))
               .thenThrow(new Exception("Acesso negado"));

        ResponseEntity<List<HistoricoEntity>> response = historicoController.listarHistorico(1L);

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
