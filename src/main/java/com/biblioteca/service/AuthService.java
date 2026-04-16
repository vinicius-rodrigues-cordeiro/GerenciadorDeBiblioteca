package com.biblioteca.service;

import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Usuario;

public class AuthService {

    private UsuarioDAO usuarioDAO;

    public AuthService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String email, String senha) {

        if (email == null || email.isBlank() ||
                senha == null || senha.isBlank()) {
            return null;
        }

        Usuario usuario = usuarioDAO.readByEmail(email);

        if (usuario == null) {
            return null;
        }

        // 🔒 usa regra centralizada do model
        if (!usuario.isAtivo()) {
            return null;
        }

        // 🔑 valida senha
        if (usuario.getSenha().equals(senha)) {
            return usuario;
        }

        return null;
    }
}
