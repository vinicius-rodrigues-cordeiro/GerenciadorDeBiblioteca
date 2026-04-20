package com.biblioteca.service;

import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Usuario;
import com.biblioteca.model.UsuarioEstudante;
import com.biblioteca.model.UsuarioBibliotecario;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Autenticação
    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioDAO.autenticar(email, senha);

        if (usuario == null) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        if (usuario.isBloqueado()) {
            throw new RuntimeException("Usuário bloqueado");
        }

        return usuario;
    }

    // Validação de regras por tipo
    public void validarUsuario(Usuario usuario) {

        if (usuario.getTipo().equalsIgnoreCase("ESTUDANTE")) {

            if (!(usuario instanceof UsuarioEstudante)) {
                throw new RuntimeException("Tipo estudante inválido");
            }

            UsuarioEstudante est = (UsuarioEstudante) usuario;

            if (est.getRa() == null) {
                throw new RuntimeException("Estudante precisa ter RA");
            }

        } else if (usuario.getTipo().equalsIgnoreCase("BIBLIOTECARIO")) {

            if (!(usuario instanceof UsuarioBibliotecario)) {
                throw new RuntimeException("Tipo bibliotecário inválido");
            }

            UsuarioBibliotecario bib = (UsuarioBibliotecario) usuario;

            if (bib.getUnidadeId() == null) {
                throw new RuntimeException("Bibliotecário precisa ter unidade");
            }
        }
    }
}