package com.biblioteca.serviceTest;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.biblioteca.entities.DevolucaoEntity;
import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.HistoricoEntity;
import com.biblioteca.entities.LivroEntity;
import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.repositories.HistoricoRepository;
import com.biblioteca.repositories.UsuarioRepository;
import com.biblioteca.services.HistoricoService;

@SpringBootTest
public class HistoricoServiceTest {

    @Autowired
    private HistoricoService historicoService;

    @MockBean
    private HistoricoRepository historicoRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    private UsuarioEntity adminUsuario;
    private UsuarioEntity regularUsuario;
    private LivroEntity livro;
    private EmprestimoEntity emprestimo;
    private DevolucaoEntity devolucao;

    @BeforeEach
    void setUp() {
        adminUsuario = new UsuarioEntity();
        adminUsuario.setIdUsuario(1L);
        adminUsuario.setTipoUsuario(UsuarioEntity.TipoUsuario.ADM);

        regularUsuario = new UsuarioEntity();
        regularUsuario.setIdUsuario(2L);
        regularUsuario.setTipoUsuario(UsuarioEntity.TipoUsuario.USUARIO);

        livro = new LivroEntity();
        livro.setIdLivro(1L);

        emprestimo = new EmprestimoEntity();
        emprestimo.setIdEmprestimo(1L);
        emprestimo.setUsuario(regularUsuario);
        emprestimo.setLivro(livro);

        devolucao = new DevolucaoEntity();
        devolucao.setIdDevolucao(1L);
        devolucao.setEmprestimo(emprestimo);
    }

    @Test
    @DisplayName("Testa listar histórico para administrador")
    void listarHistoricoAdminTest() throws Exception {
        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(adminUsuario));
        List<HistoricoEntity> historico = List.of(new HistoricoEntity());
        Mockito.when(historicoRepository.findAll()).thenReturn(historico);

        List<HistoricoEntity> resultado = historicoService.listarHistorico(1L);
        Assertions.assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Testa listar histórico para usuário não administrador")
    void listarHistoricoNaoAdminTest() {
        Mockito.when(usuarioRepository.findById(2L)).thenReturn(Optional.of(regularUsuario));

        Assertions.assertThrows(Exception.class, () -> {
            historicoService.listarHistorico(2L);
        });
    }

    @Test
    @DisplayName("Testa listar histórico para usuário não encontrado")
    void listarHistoricoUsuarioNaoEncontradoTest() {
        Mockito.when(usuarioRepository.findById(3L)).thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> {
            historicoService.listarHistorico(3L);
        });
    }

    @Test
    @DisplayName("Testa registrar histórico")
    void registrarHistoricoTest() {
        Mockito.when(historicoRepository.save(Mockito.any(HistoricoEntity.class)))
               .thenAnswer(invocation -> invocation.getArgument(0));

        historicoService.registrarHistorico(emprestimo, devolucao);

        Mockito.verify(historicoRepository, Mockito.times(1)).save(Mockito.any(HistoricoEntity.class));
    }
}