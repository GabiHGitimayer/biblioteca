package com.biblioteca.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.biblioteca.entities.DevolucaoEntity;
import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.LivroEntity;
import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.repositories.DevolucaoRepository;
import com.biblioteca.repositories.EmprestimoRepository;
import com.biblioteca.services.DevolucaoService;
import com.biblioteca.services.HistoricoService;
import com.biblioteca.services.MultaService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DevolucaoServiceTest {

    @Mock
    private DevolucaoRepository devolucaoRepository;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private MultaService multaService;

    @Mock
    private HistoricoService historicoService;

    @InjectMocks
    private DevolucaoService devolucaoService;

    @Test
    @DisplayName("Testa registrar devolução com sucesso")
    void testRegistrarDevolucaoSucesso() {

        Long idEmprestimo = 1L;
        
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(1L);

        LivroEntity livro = new LivroEntity();
        livro.setIdLivro(1L);

        EmprestimoEntity emprestimo = new EmprestimoEntity();
        emprestimo.setIdEmprestimo(idEmprestimo);
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(new Date(System.currentTimeMillis() - 86400000));
        emprestimo.setDataDevolucao(new Date(System.currentTimeMillis() + 86400000));

        DevolucaoEntity devolucao = new DevolucaoEntity();
        devolucao.setEmprestimo(emprestimo);


        when(emprestimoRepository.findById(idEmprestimo)).thenReturn(Optional.of(emprestimo));
        when(devolucaoRepository.save(any(DevolucaoEntity.class))).thenReturn(devolucao);
        doNothing().when(historicoService).registrarHistorico(any(EmprestimoEntity.class), any(DevolucaoEntity.class));


        DevolucaoEntity savedDevolucao = devolucaoService.registrarDevolucao(idEmprestimo, devolucao);


        assertNotNull(savedDevolucao);
        assertEquals(emprestimo, savedDevolucao.getEmprestimo());
        assertEquals("Devolvido", emprestimo.getStatusEmprestimo());
        verify(emprestimoRepository).findById(idEmprestimo);
        verify(devolucaoRepository).save(any(DevolucaoEntity.class));
        verify(historicoService).registrarHistorico(emprestimo, savedDevolucao);
        verify(emprestimoRepository).save(emprestimo);
    }

    @Test
    @DisplayName("Testa ID do livro incorreto")
    void testIdLivroIncorreto() {

        Long idEmprestimo = 1L;
        Long idLivroCorreto = 2L;
        Long idLivroIncorreto = 3L;

        LivroEntity livroCorreto = new LivroEntity();
        livroCorreto.setIdLivro(idLivroCorreto);

        EmprestimoEntity emprestimo = new EmprestimoEntity();
        emprestimo.setIdEmprestimo(idEmprestimo);
        emprestimo.setLivro(livroCorreto);

        DevolucaoEntity devolucao = new DevolucaoEntity();
        LivroEntity livroIncorreto = new LivroEntity();
        livroIncorreto.setIdLivro(idLivroIncorreto);
        EmprestimoEntity emprestimoIncorreto = new EmprestimoEntity();
        emprestimoIncorreto.setLivro(livroIncorreto);
        devolucao.setEmprestimo(emprestimoIncorreto);

        when(emprestimoRepository.findById(idEmprestimo)).thenReturn(Optional.of(emprestimo));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            devolucaoService.registrarDevolucao(idEmprestimo, devolucao);
        });

        String expectedMessage = "ID do livro incorreto";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        verify(emprestimoRepository).findById(idEmprestimo);
    }

    @Test
    @DisplayName("Testa cálculo de multa para devolução atrasada")
    void testCalculoMultaDevoulcaoAtrasada() {
        Long idEmprestimo = 1L;
        
        LivroEntity livro = new LivroEntity();
        livro.setIdLivro(1L);

        EmprestimoEntity emprestimo = new EmprestimoEntity();
        emprestimo.setIdEmprestimo(idEmprestimo);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(new Date(System.currentTimeMillis() - 172800000));
        emprestimo.setDataDevolucao(new Date(System.currentTimeMillis() - 86400000));

        DevolucaoEntity devolucao = new DevolucaoEntity();
        devolucao.setEmprestimo(emprestimo);

        when(emprestimoRepository.findById(idEmprestimo)).thenReturn(Optional.of(emprestimo));
        when(devolucaoRepository.save(any(DevolucaoEntity.class))).thenReturn(devolucao);
        doNothing().when(historicoService).registrarHistorico(any(EmprestimoEntity.class), any(DevolucaoEntity.class));

        DevolucaoEntity savedDevolucao = devolucaoService.registrarDevolucao(idEmprestimo, devolucao);

        assertNotNull(savedDevolucao);
        assertEquals(emprestimo, savedDevolucao.getEmprestimo());
        assertEquals("Devolvido", emprestimo.getStatusEmprestimo());
        verify(emprestimoRepository).findById(idEmprestimo);
        verify(devolucaoRepository).save(any(DevolucaoEntity.class));
        verify(historicoService).registrarHistorico(emprestimo, savedDevolucao);
        verify(emprestimoRepository).save(emprestimo);
        verify(multaService).calcularMulta(emprestimo);
    }
}