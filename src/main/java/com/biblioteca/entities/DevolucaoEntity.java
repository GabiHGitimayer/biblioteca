package com.biblioteca.entities;

import java.sql.Date;

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
@Table(name = "devolucao")
public class DevolucaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDevolucao;

    @ManyToOne
    @JoinColumn(name = "idEmprestimo", nullable = false)
    private EmprestimoEntity emprestimo;

    @Column(name = "dataDevolucao")
    private Date dataDevolucao;


}
