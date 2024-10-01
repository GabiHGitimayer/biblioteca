package com.biblioteca.entities;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emprestimo")
public class EmprestimoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmprestimo;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "idLivro", nullable = false)
    private LivroEntity livro;

    @Column(name = "dataEmprestimo", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataEmprestimo;

    @Column(name = "dataDevolucao")
    @Temporal(TemporalType.DATE)
    private Date dataDevolucao;

    @Column(name = "statusEmprestimo")
    private String statusEmprestimo;
    

    @PrePersist
    public void prePersist() {
        this.statusEmprestimo = "Emprestado";
        this.dataDevolucao = calcularDataDevolucao(this.dataEmprestimo, 7);
    }

    private Date calcularDataDevolucao(Date dataInicial, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataInicial);
        int diasAdicionados = 0;

        while (diasAdicionados < dias) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                diasAdicionados++;
            }
        }

        return cal.getTime();
    }

    public void verificarStatus() {
        Date hoje = new Date();
        if (hoje.after(dataDevolucao)) {
            statusEmprestimo = "Atrasado";
        } else {
            statusEmprestimo = "No Prazo";
        }
    }
}
