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

import com.biblioteca.controllers.MultaController;
import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.MultaEntity;
import com.biblioteca.services.EmprestimoService;
import com.biblioteca.services.MultaService;

import java.util.Optional;

@WebMvcTest(MultaController.class)
public class MultaControllerTest {

    @Autowired
    MultaController multaController;

    @MockBean
    MultaService multaService;

    @MockBean
    EmprestimoService emprestimoService;

    @Test
    @DisplayName("Testa a função de calcular multa para um empréstimo existente")
    void calcularMultaTest() {
        EmprestimoEntity emprestimo = new EmprestimoEntity();
        MultaEntity multa = new MultaEntity();

        Mockito.when(emprestimoService.findById(1L))
               .thenReturn(Optional.of(emprestimo));
        Mockito.when(multaService.calcularMulta(emprestimo))
               .thenReturn(multa);

        ResponseEntity<MultaEntity> response = multaController.calcularMulta(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Testa a função de calcular multa para um empréstimo inexistente")
    void calcularMultaNotFoundTest() {
        Mockito.when(emprestimoService.findById(1L))
               .thenReturn(Optional.empty());

        ResponseEntity<MultaEntity> response = multaController.calcularMulta(1L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
