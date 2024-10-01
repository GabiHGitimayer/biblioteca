package com.biblioteca.serviceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.MultaEntity;
import com.biblioteca.repositories.MultaRepository;
import com.biblioteca.services.MultaService;

@SpringBootTest
public class MultaServiceTest {

    @Autowired
    MultaService multaService;

    @MockBean
    MultaRepository multaRepository;

    @Test
    @DisplayName("Testa cálculo de multa com atraso")
    void calcularMultaComAtrasoTest() {
        EmprestimoEntity emprestimo = new EmprestimoEntity();
        emprestimo.setDataDevolucao(new java.util.Date(System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000));

        MultaEntity multa = new MultaEntity();
        multa.setValorMulta(new java.math.BigDecimal("25.00"));

        Mockito.when(multaRepository.save(Mockito.any(MultaEntity.class)))
               .thenReturn(multa);

        MultaEntity calculatedMulta = multaService.calcularMulta(emprestimo);

        Assertions.assertEquals(new java.math.BigDecimal("25.00"), calculatedMulta.getValorMulta());
    }
}
