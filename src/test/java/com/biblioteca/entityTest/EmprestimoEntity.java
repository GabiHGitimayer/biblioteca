package com.biblioteca.entityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.LivroEntity;
import com.biblioteca.entities.UsuarioEntity;

@ExtendWith(MockitoExtension.class)
class EmprestimoEntityTest {

    private EmprestimoEntity emprestimo;

    @Mock
    private UsuarioEntity usuarioMock;

    @Mock
    private LivroEntity livroMock;

    @BeforeEach
    void setUp() {
        emprestimo = new EmprestimoEntity();
        emprestimo.setUsuario(usuarioMock);
        emprestimo.setLivro(livroMock);
    }

    @Test
    void prePersist_DeveDefinirStatusECalcularDataDevolucao() {
      
        Calendar cal = Calendar.getInstance();
        Date dataEmprestimo = cal.getTime();
        emprestimo.setDataEmprestimo(dataEmprestimo);


        emprestimo.prePersist();

        assertEquals("Emprestado", emprestimo.getStatusEmprestimo());
        assertNotNull(emprestimo.getDataDevolucao());
        assertTrue(emprestimo.getDataDevolucao().after(dataEmprestimo));
    }

    @Test
    void calcularDataDevolucao_DeveIgnorarFinsDeSemanaNoPrazo() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 1);
        Date dataEmprestimo = cal.getTime();
        emprestimo.setDataEmprestimo(dataEmprestimo);

        emprestimo.prePersist();

  
        Calendar calExpected = Calendar.getInstance();
        calExpected.setTime(dataEmprestimo);
        calExpected.add(Calendar.DAY_OF_MONTH, 9);

        Calendar calResult = Calendar.getInstance();
        calResult.setTime(emprestimo.getDataDevolucao());

        assertEquals(calExpected.get(Calendar.DAY_OF_MONTH), calResult.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    void verificarStatus_QuandoEmprestimoEstáNoPrazo_DeveRetornarNoPrazo() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        emprestimo.setDataDevolucao(cal.getTime());

        emprestimo.verificarStatus();


        assertEquals("No Prazo", emprestimo.getStatusEmprestimo());
    }

    @Test
    void verificarStatus_QuandoEmprestimoEstáAtrasado_DeveRetornarAtrasado() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        emprestimo.setDataDevolucao(cal.getTime());


        emprestimo.verificarStatus();

        assertEquals("Atrasado", emprestimo.getStatusEmprestimo());
    }

}