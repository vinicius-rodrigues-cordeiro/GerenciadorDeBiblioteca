package com.biblioteca.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String tipo; // ADMIN, COMUM, ESTUDANTE, BIBLIOTECARIO
    private LocalDate dataCadastro;
    private String telefone;
    private boolean bloqueado = false;
    private int tentativasLogin = 0;
    private LocalDateTime ultimaTentativa;

    public Usuario() {}

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }

    public int getTentativasLogin() { return tentativasLogin; }
    public void setTentativasLogin(int tentativasLogin) { this.tentativasLogin = tentativasLogin; }

    public LocalDateTime getUltimaTentativa() { return ultimaTentativa; }
    public void setUltimaTentativa(LocalDateTime ultimaTentativa) { this.ultimaTentativa = ultimaTentativa; }
}