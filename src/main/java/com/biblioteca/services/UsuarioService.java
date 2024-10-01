package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEntity saveUser(UsuarioEntity usuarioEntity) {
        return usuarioRepository.save(usuarioEntity);
    }

    public List<UsuarioEntity> listAllUsers() {
        return usuarioRepository.findAll();
    }

    public UsuarioEntity findUserbyId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deleteUsuario(Long idUsuario) throws Exception {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new Exception("Usuário não encontrado"));
        
        usuarioRepository.delete(usuarioEntity);
    }

    public UsuarioEntity alterarUsuario(Long idUsuario, UsuarioEntity novoUsuarioEntity) throws Exception{
        Optional<UsuarioEntity> usuarioExistenteOpt = usuarioRepository.findById(idUsuario);
        
        if (usuarioExistenteOpt.isPresent()) {
            UsuarioEntity usuarioExistente = usuarioExistenteOpt.get();
            usuarioExistente.setNomeUsuario(novoUsuarioEntity.getNomeUsuario());
            usuarioExistente.setCpf(novoUsuarioEntity.getCpf());
            usuarioExistente.setSenha(novoUsuarioEntity.getSenha());
            usuarioExistente.setTipoUsuario(novoUsuarioEntity.getTipoUsuario());
            return usuarioRepository.save(usuarioExistente);
        }
        else {
            throw new Exception("Usuário não encontrado!");
        }
    }
}
