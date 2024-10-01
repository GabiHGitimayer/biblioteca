package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.LivroEntity;
import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.repositories.EmprestimoRepository;
import com.biblioteca.repositories.LivroRepository;
import com.biblioteca.repositories.UsuarioRepository;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private MultaService multaService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private HistoricoService historicoService;
    
    @Autowired
    private LivroRepository livroRepository;

    public List<EmprestimoEntity> findAll() {
        List<EmprestimoEntity> emprestimos = emprestimoRepository.findAll();
        emprestimos.forEach(this::verificarStatusECalcularMulta);
        return emprestimos;
    }

    public Optional<EmprestimoEntity> findById(Long id) {
        Optional<EmprestimoEntity> emprestimo = emprestimoRepository.findById(id);
        emprestimo.ifPresent(this::verificarStatusECalcularMulta);
        return emprestimo;
    }

    public EmprestimoEntity save(EmprestimoEntity emprestimo) {
        UsuarioEntity usuario = usuarioRepository.findById(emprestimo.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        LivroEntity livro = livroRepository.findById(emprestimo.getLivro().getIdLivro())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        EmprestimoEntity salvo = emprestimoRepository.save(emprestimo);
        historicoService.registrarHistorico(salvo, null);

        return emprestimoRepository.save(emprestimo);
    }


    public void deleteById(Long id) {
        emprestimoRepository.deleteById(id);
    }

    private void verificarStatusECalcularMulta(EmprestimoEntity emprestimo) {
        emprestimo.verificarStatus();
        if ("Atrasado".equals(emprestimo.getStatusEmprestimo())) {
            multaService.calcularMulta(emprestimo);
        }
    }
}
