package com.biblioteca.model;

public class UsuarioEstudante extends Usuario {

    private Integer ra;
    private Integer unidadeId;

    public UsuarioEstudante() {
        setTipo("ESTUDANTE");
    }

    public Integer getRa() { return ra; }
    public void setRa(Integer ra) { this.ra = ra; }

    public Integer getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Integer unidadeId) { this.unidadeId = unidadeId; }
}