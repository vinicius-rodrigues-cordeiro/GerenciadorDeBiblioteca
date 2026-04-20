package com.biblioteca.model;

public class UsuarioBibliotecario extends Usuario {

    private Integer unidadeId;

    public UsuarioBibliotecario() {
        setTipo("BIBLIOTECARIO");
    }

    public Integer getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Integer unidadeId) { this.unidadeId = unidadeId; }
}