package com.biblioteca.entityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.MultaEntity;

@ExtendWith(MockitoExtension.class)
class MultaEntityTest {

    @Mock
    private EmprestimoEntity emprestimoMock;

    private MultaEntity multa;

    @BeforeEach
    void setUp() {
        multa = new MultaEntity();
        multa.setEmprestimo(emprestimoMock);
    }

    @Test
    void calcularMulta_QuandoLivroEstáAtrasado_DeveCalcularMultaCorretamente() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        Date dataDevolucao = cal.getTime();
        
        when(emprestimoMock.getDataDevolucao()).thenReturn(dataDevolucao);


        multa.calcularMulta();


        assertEquals(new BigDecimal("25.00"), multa.getValorMulta());
        assertNotNull(multa.getDataCalculo());
        verify(emprestimoMock, times(1)).getDataDevolucao();
    }

    @Test
    void calcularMulta_QuandoLivroNaoEstáAtrasado_DeveRetornarMultaZero() {
    
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 5); 
        Date dataDevolucao = cal.getTime();
        
        when(emprestimoMock.getDataDevolucao()).thenReturn(dataDevolucao);

    
        multa.calcularMulta();


        assertEquals(BigDecimal.ZERO, multa.getValorMulta());
        verify(emprestimoMock, times(1)).getDataDevolucao();
    }

    @Test
    void calcularMulta_QuandoLivroEstáAtrasadoUmDia_DeveCalcularMultaParaUmDia() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date dataDevolucao = cal.getTime();
        
        when(emprestimoMock.getDataDevolucao()).thenReturn(dataDevolucao);

        multa.calcularMulta();

   
        assertEquals(new BigDecimal("5.00"), multa.getValorMulta());
        assertNotNull(multa.getDataCalculo());
        verify(emprestimoMock, times(1)).getDataDevolucao();
    }

}
