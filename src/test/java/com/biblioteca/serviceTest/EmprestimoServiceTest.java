package com.biblioteca.serviceTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.LivroEntity;
import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.repositories.EmprestimoRepository;
import com.biblioteca.repositories.LivroRepository;
import com.biblioteca.repositories.UsuarioRepository;
import com.biblioteca.services.EmprestimoService;
import com.biblioteca.services.HistoricoService;

@SpringBootTest
public class EmprestimoServiceTest {

    @Autowired
    EmprestimoService emprestimoService;

    @MockBean
    EmprestimoRepository emprestimoRepository;

    @MockBean
    UsuarioRepository usuarioRepository;

    @MockBean
    LivroRepository livroRepository;
    
    @MockBean
    HistoricoService historicoService;

    @Test
    @DisplayName("Testa salvar empréstimo")
    void saveEmprestimoTest() {

        LivroEntity livro = new LivroEntity();
        livro.setIdLivro(1L);


        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(1L);

        EmprestimoEntity emprestimo = new EmprestimoEntity();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);


        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

  
        Mockito.when(emprestimoRepository.save(Mockito.any(EmprestimoEntity.class)))
               .thenReturn(emprestimo);


        EmprestimoEntity savedEmprestimo = emprestimoService.save(emprestimo);


        Assertions.assertNotNull(savedEmprestimo);
        Assertions.assertEquals(usuario.getIdUsuario(), savedEmprestimo.getUsuario().getIdUsuario());
        Assertions.assertEquals(livro.getIdLivro(), savedEmprestimo.getLivro().getIdLivro());
        verify(historicoService).registrarHistorico(eq(emprestimo), isNull());
        verify(emprestimoRepository, times(2)).save(emprestimo);
    }



    @Test
    @DisplayName("Testa buscar empréstimo por ID")
    void findEmprestimoByIdTest() {
        LivroEntity livro = new LivroEntity();
        livro.setIdLivro(1L);

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(1L);

        EmprestimoEntity emprestimo = new EmprestimoEntity();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(new Date(0));
        emprestimo.setDataDevolucao(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000));

        Mockito.when(emprestimoRepository.findById(1L))
               .thenReturn(Optional.of(emprestimo));

        Optional<EmprestimoEntity> foundEmprestimo = emprestimoService.findById(1L);

        Assertions.assertTrue(foundEmprestimo.isPresent());
        Assertions.assertEquals(livro.getIdLivro(), foundEmprestimo.get().getLivro().getIdLivro());
    }

    @Test
    @DisplayName("Testa deletar empréstimo")
    void deleteEmprestimoTest() {
        emprestimoService.deleteById(1L);

        Mockito.verify(emprestimoRepository, Mockito.times(1)).deleteById(1L);
    }
}
