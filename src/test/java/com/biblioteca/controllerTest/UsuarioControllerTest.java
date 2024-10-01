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

import com.biblioteca.controllers.UsuarioController;
import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.services.UsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    UsuarioController usuarioController;

    @MockBean
    UsuarioService usuarioService;

    @Test
    @DisplayName("Testa a função de salvar um usuário")
    void saveUserTest() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNomeUsuario("João");

        Mockito.when(usuarioService.saveUser(Mockito.any(UsuarioEntity.class)))
               .thenReturn(usuario);

        ResponseEntity<UsuarioEntity> response = usuarioController.save(usuario);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("João", response.getBody().getNomeUsuario());
    }

    @Test
    @DisplayName("Testa a função de listar todos os usuários")
    void listAllUsersTest() {
        List<UsuarioEntity> usuarios = new ArrayList<>();
        usuarios.add(new UsuarioEntity(1L, "João", null, null, null));
        usuarios.add(new UsuarioEntity(2L, "Ana", null, null, null));

        Mockito.when(usuarioService.listAllUsers())
               .thenReturn(usuarios);

        ResponseEntity<List<UsuarioEntity>> response = usuarioController.listAll();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    @DisplayName("Testa a função de buscar um usuário por ID")
    void findUserByIdTest() {
        UsuarioEntity usuario = new UsuarioEntity(1L, "Carlos", null, null, null);

        Mockito.when(usuarioService.findUserbyId(1L))
               .thenReturn(usuario);

        ResponseEntity<UsuarioEntity> response = usuarioController.findById(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Carlos", response.getBody().getNomeUsuario());
    }

    @Test
    @DisplayName("Testa a função de deletar um usuário")
    void deleteUserTest() throws Exception {
        Mockito.doNothing().when(usuarioService).deleteUsuario(1L);

        ResponseEntity<Void> response = usuarioController.delete(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Testa a função de alterar um usuário")
    void updateUserTest() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity(1L, "João", null, null, null);
        UsuarioEntity novoUsuario = new UsuarioEntity(1L, "Ana", null, null, null);

        Mockito.when(usuarioService.alterarUsuario(1L, novoUsuario))
               .thenReturn(novoUsuario);

        ResponseEntity<UsuarioEntity> response = usuarioController.alterar(1L, novoUsuario);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Ana", response.getBody().getNomeUsuario());
    }
}
