package com.biblioteca.model;

import java.time.LocalDate;

public class Usuario {
    private int idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String tipo;
    private LocalDate dataCadastro;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = LocalDate.now();
    }

    public Usuario(String nome, String email, String senha, String tipo) {
        this(nome, email, senha);
        this.tipo = tipo;
    }


    public Usuario(String nome, String email, String senha, String tipo, LocalDate dataCadastro) {
        this(nome, email, senha, tipo);
        this.dataCadastro = dataCadastro;
    }

    public Usuario(int idUsuario, String nome, String email, String senha, String tipo, LocalDate dataCadastro) {
        this(nome, email, senha, tipo, dataCadastro);
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
