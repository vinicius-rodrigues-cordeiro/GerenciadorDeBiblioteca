package com.biblioteca.dao;

import com.biblioteca.database.DatabaseConnection;
import com.biblioteca.model.Usuario;
import com.biblioteca.model.UsuarioEstudante;
import com.biblioteca.model.UsuarioBibliotecario;
import java.sql.*;

public class UsuarioDAO {

    public Usuario buscarPorId(int id) {
    String sql = "SELECT * FROM usuario WHERE id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {

                String tipo = rs.getString("tipo");
                Usuario u;

                // Decide qual classe instanciar
                if ("ESTUDANTE".equalsIgnoreCase(tipo)) {
                    UsuarioEstudante est = new UsuarioEstudante();
                    est.setRa(rs.getObject("ra", Integer.class));
                    u = est;

                } else if ("BIBLIOTECARIO".equalsIgnoreCase(tipo)) {
                    UsuarioBibliotecario bib = new UsuarioBibliotecario();
                    bib.setUnidadeId(rs.getObject("unidade_id", Integer.class));
                    u = bib;

                } else {
                    u = new Usuario();
                }

                // 🔹 Dados comuns
                u.setIdUsuario(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setTipo(tipo);
                u.setTelefone(rs.getString("telefone"));
                u.setBloqueado(rs.getBoolean("bloqueado"));
                u.setTentativasLogin(rs.getInt("tentativas_login"));

                return u;
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}