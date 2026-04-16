package com.biblioteca.service;

import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Usuario;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // 📌 Cadastro de usuário
    public boolean cadastrarUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new RuntimeException("Usuário inválido");
        }

        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new RuntimeException("Email é obrigatório");
        }

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new RuntimeException("Senha é obrigatória");
        }

        if (usuario.getTipo() == null || usuario.getTipo().isBlank()) {
            throw new RuntimeException("Tipo de usuário é obrigatório");
        }

        // 📌 Padronização (sem mexer no status)
        usuario.setTipo(usuario.getTipo().toUpperCase());

        // 📌 Regra futura
        if ("ESTUDANTE".equals(usuario.getTipo())) {
            // validar RA futuramente
        }

        // 👉 futuramente:
        // return usuarioDAO.create(usuario);

        return true;
    }

    // 📌 Buscar por email
    public Usuario buscarPorEmail(String email) {

        if (email == null || email.isBlank()) {
            return null;
        }

        return usuarioDAO.readByEmail(email);
    }
}