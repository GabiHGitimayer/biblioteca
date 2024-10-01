package com.biblioteca.controllerTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.biblioteca.controllers.DevolucaoController;
import com.biblioteca.entities.DevolucaoEntity;
import com.biblioteca.services.DevolucaoService;


@WebMvcTest(DevolucaoController.class)
public class DevolucaoControllerTest {

    @Autowired
    DevolucaoController devolucaoController;

    @MockBean
    DevolucaoService devolucaoService;

    @Test
    @DisplayName("Testa salvar devolução")
    void saveDevolucaoTest() {
        DevolucaoEntity devolucao = new DevolucaoEntity();

        Mockito.when(devolucaoService.registrarDevolucao(Mockito.anyLong(), Mockito.any(DevolucaoEntity.class)))
               .thenReturn(devolucao);

        ResponseEntity<DevolucaoEntity> response = devolucaoController.registrarDevolucao(1L, devolucao);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Testa listar todas as devoluções")
    void listAllDevolucoesTest() {
        List<DevolucaoEntity> devolucoes = new ArrayList<>();
        devolucoes.add(new DevolucaoEntity());

        Mockito.when(devolucaoService.listAllDevolucao())
               .thenReturn(devolucoes);

        ResponseEntity<List<DevolucaoEntity>> response = devolucaoController.listAll();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().size());
    }


    @Test
    @DisplayName("Testa buscar devolução por ID")
    void findDevolucaoByIdTest() {
        DevolucaoEntity devolucao = new DevolucaoEntity();

        Mockito.when(devolucaoService.findDevolucaoById(1L))
               .thenReturn(devolucao);

        ResponseEntity<DevolucaoEntity> response = devolucaoController.findById(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }
}
