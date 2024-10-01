package com.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.DevolucaoEntity;
import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.HistoricoEntity;
import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.entities.UsuarioEntity.TipoUsuario;
import com.biblioteca.repositories.HistoricoRepository;
import com.biblioteca.repositories.UsuarioRepository;

@Service
public class HistoricoService {

    
    @Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void registrarHistorico(EmprestimoEntity emprestimo, DevolucaoEntity devolucao) {
        HistoricoEntity historico = new HistoricoEntity();
        historico.setUsuario(emprestimo.getUsuario());
        historico.setLivro(emprestimo.getLivro());
        historico.setEmprestimo(emprestimo);
        historico.setDevolucao(devolucao);
        historicoRepository.save(historico);
    }
    
    public List<HistoricoEntity> listarHistorico(Long idUsuario) throws Exception {
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new Exception("Usuário não encontrado"));

        if (usuario.getTipoUsuario() != TipoUsuario.ADM) {
            throw new Exception("Acesso negado. Somente administradores podem acessar o histórico.");
        }

        return historicoRepository.findAll();
    }
}
