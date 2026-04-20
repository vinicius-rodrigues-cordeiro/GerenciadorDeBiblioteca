package com.biblioteca.model;

public class UsuarioEstudante extends Usuario {

    private Integer ra;

    public UsuarioEstudante() {
        setTipo("ESTUDANTE");
    }

    public Integer getRa() { return ra; }
    public void setRa(Integer ra) { this.ra = ra; }
}