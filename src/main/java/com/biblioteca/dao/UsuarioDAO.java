package com.biblioteca.dao;

import com.biblioteca.model.Usuario;
import com.biblioteca.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario readByEmail(String email) {

        String sql = "SELECT * FROM usuario WHERE email = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(rs.getInt("idUsuario"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setTipo(rs.getString("tipo"));
                    usuario.setStatus(rs.getString("status"));

                    if (rs.getDate("dataCadastro") != null) {
                        usuario.setDataCadastro(rs.getDate("dataCadastro").toLocalDate());
                    }

                    return usuario;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}