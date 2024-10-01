package com.biblioteca.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.DevolucaoEntity;
import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.repositories.DevolucaoRepository;
import com.biblioteca.repositories.EmprestimoRepository;

@Service
public class DevolucaoService {
    @Autowired
    private DevolucaoRepository devolucaoRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private MultaService multaService;
    
    @Autowired
    private HistoricoService historicoService;

    public DevolucaoEntity registrarDevolucao(Long idEmprestimo, DevolucaoEntity devolucaoEntity) {
        EmprestimoEntity emprestimoEntity = emprestimoRepository.findById(idEmprestimo)
            .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        if (devolucaoEntity.getEmprestimo() == null || 
            !emprestimoEntity.getLivro().getIdLivro().equals(devolucaoEntity.getEmprestimo().getLivro().getIdLivro())) {
            throw new RuntimeException("ID do livro incorreto");
        }

        devolucaoEntity.setDataDevolucao(new Date(System.currentTimeMillis()));
        devolucaoEntity.setEmprestimo(emprestimoEntity);

        if (devolucaoEntity.getDataDevolucao().after(new Date(emprestimoEntity.getDataDevolucao().getTime()))) {
            verificarStatusECalcularMulta(emprestimoEntity);
        }

        emprestimoEntity.setStatusEmprestimo("Devolvido");
        emprestimoRepository.save(emprestimoEntity);

        DevolucaoEntity devolucaoSalva = devolucaoRepository.save(devolucaoEntity);
        historicoService.registrarHistorico(emprestimoEntity, devolucaoSalva);

        return devolucaoSalva;
    }




    private void verificarStatusECalcularMulta(EmprestimoEntity emprestimo) {
        emprestimo.verificarStatus();
        if ("Atrasado".equals(emprestimo.getStatusEmprestimo())) {
            multaService.calcularMulta(emprestimo);
        }
    }


    public List<DevolucaoEntity> listAllDevolucao() {
        return devolucaoRepository.findAll();
    }

    public DevolucaoEntity findDevolucaoById(Long idDevolucao) {
        return devolucaoRepository.findById(idDevolucao)
            .orElseThrow(() -> new RuntimeException("Devolução não encontrada"));
    }
}
