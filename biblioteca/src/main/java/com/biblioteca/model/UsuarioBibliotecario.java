package com.biblioteca.model;

public class UsuarioBibliotecario extends Usuario {

    private Integer ra;
    private Integer unidadeId;

    public UsuarioBibliotecario() {
        setTipo("BIBLIOTECARIO");
    }

    public Integer getRa() { return ra; }
    public void setRa(Integer ra) { this.ra = ra; }

    public Integer getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Integer unidadeId) { this.unidadeId = unidadeId; }
}