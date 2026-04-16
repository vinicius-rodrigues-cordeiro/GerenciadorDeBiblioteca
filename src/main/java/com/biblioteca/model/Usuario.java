package com.biblioteca.model;

import java.time.LocalDate;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String tipo;
    private String status;
    private LocalDate dataCadastro;

    // 🔹 Construtor vazio (ESSENCIAL)
    public Usuario() {
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = LocalDate.now();
        this.status = "ATIVO";
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

    public int getIdUsuario() { return idUsuario; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getTipo() { return tipo; }
    public String getStatus() { return status; }
    public LocalDate getDataCadastro() { return dataCadastro; }

    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setStatus(String status) { this.status = status; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }

    public boolean isAtivo() {
        return status != null && status.equalsIgnoreCase("ATIVO");
    }
}
