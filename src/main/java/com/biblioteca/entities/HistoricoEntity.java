package com.biblioteca.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "historico")
public class HistoricoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorico;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioEntity usuario;
    
    @ManyToOne
    @JoinColumn(name = "idLivro", nullable = false)
    private LivroEntity livro;
    
    @ManyToOne
    @JoinColumn(name = "idEmprestimo", nullable = false)
    private EmprestimoEntity emprestimo;
    
    @ManyToOne
    @JoinColumn(name = "idDevolucao")
    private DevolucaoEntity devolucao;
    
    @Column(name = "dataEmprestimo")
    private Date dataEmprestimo;
    
    @Column(name = "dataDevolucao")
    private Date dataDevolucao;
}
