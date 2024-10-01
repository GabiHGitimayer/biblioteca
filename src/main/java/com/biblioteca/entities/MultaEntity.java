package com.biblioteca.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "multa")
public class MultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMulta;

    @ManyToOne
    @JoinColumn(name = "idEmprestimo", nullable = false)
    private EmprestimoEntity emprestimo;

    @Column(name = "valorMulta", nullable = false)
    private BigDecimal valorMulta;

    @Column(name = "dataCalculo", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCalculo;

    public void calcularMulta() {
        Date hoje = new Date();
        long diasAtraso = (hoje.getTime() - emprestimo.getDataDevolucao().getTime()) / (1000 * 60 * 60 * 24);
        if (diasAtraso > 0) {
            BigDecimal multaPorDia = new BigDecimal("5.00");
            valorMulta = multaPorDia.multiply(BigDecimal.valueOf(diasAtraso));
            dataCalculo = hoje;
        } else {
            valorMulta = BigDecimal.ZERO;
        }
    }
}
