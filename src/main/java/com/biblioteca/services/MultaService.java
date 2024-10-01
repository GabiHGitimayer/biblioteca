package com.biblioteca.services;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.MultaEntity;
import com.biblioteca.repositories.MultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class MultaService {

    @Autowired
    private MultaRepository multaRepository;

    public MultaEntity calcularMulta(EmprestimoEntity emprestimo) {
        Date hoje = new Date();
        long diasAtraso = (hoje.getTime() - emprestimo.getDataDevolucao().getTime()) / (1000 * 60 * 60 * 24);
        if (diasAtraso > 0) {
            MultaEntity multa = new MultaEntity();
            multa.setEmprestimo(emprestimo);
            BigDecimal multaPorDia = new BigDecimal("5.00");
            multa.setValorMulta(multaPorDia.multiply(BigDecimal.valueOf(diasAtraso)));
            multa.setDataCalculo(hoje);
            return multaRepository.save(multa);
        }
        return null;
    }
}
