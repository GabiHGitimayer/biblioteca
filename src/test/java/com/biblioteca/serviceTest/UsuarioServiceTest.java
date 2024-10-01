package com.biblioteca.serviceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.repositories.UsuarioRepository;
import com.biblioteca.services.UsuarioService;

import java.util.Optional;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @MockBean
    UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Testa salvar usuário")
    void saveUserTest() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNomeUsuario("Matheus");

        Mockito.when(usuarioRepository.save(Mockito.any(UsuarioEntity.class)))
               .thenReturn(usuario);

        UsuarioEntity savedUser = usuarioService.saveUser(usuario);

        Assertions.assertEquals("Matheus", savedUser.getNomeUsuario());
    }

    @Test
    @DisplayName("Testa buscar usuário por ID")
    void findUserByIdTest() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(1L);

        Mockito.when(usuarioRepository.findById(1L))
               .thenReturn(Optional.of(usuario));

        UsuarioEntity foundUser = usuarioService.findUserbyId(1L);

        Assertions.assertEquals(1L, foundUser.getIdUsuario());
    }

    @Test
    @DisplayName("Testa deletar usuário")
    void deleteUserTest() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(1L);

        Mockito.when(usuarioRepository.findById(1L))
               .thenReturn(Optional.of(usuario));

        usuarioService.deleteUsuario(1L);

        Mockito.verify(usuarioRepository, Mockito.times(1)).delete(usuario);
    }

    @Test
    @DisplayName("Testa alterar usuário")
    void updateUserTest() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNomeUsuario("João");
        UsuarioEntity novoUsuario = new UsuarioEntity();
        novoUsuario.setNomeUsuario("Ana");

        Mockito.when(usuarioRepository.findById(1L))
               .thenReturn(Optional.of(usuario));
        Mockito.when(usuarioRepository.save(Mockito.any(UsuarioEntity.class)))
               .thenReturn(novoUsuario);

        UsuarioEntity updatedUser = usuarioService.alterarUsuario(1L, novoUsuario);

        Assertions.assertEquals("Ana", updatedUser.getNomeUsuario());
    }
}
