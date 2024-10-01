package com.biblioteca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name = "nomeUsuario")
    @NotBlank
    private String nomeUsuario;

    @Column(name = "cpf", unique = true)
    @NotBlank
    private String cpf;

    @Column(name = "senha")
    @NotBlank
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoUsuario")
    private TipoUsuario tipoUsuario;
    
    public enum TipoUsuario {
        ADM, FUNCIONARIO, USUARIO
    }
}
