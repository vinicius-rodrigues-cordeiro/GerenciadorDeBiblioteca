package com.biblioteca.dao;

import com.biblioteca.database.DatabaseConnection;
import com.biblioteca.model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public boolean inserir(Livro livro) {
        String sql = """
                INSERT INTO livros (titulo, autor, editora, ano_publicacao, genero, descricao, sumario)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getEditora());
            ps.setInt(4, livro.getAnoPublicacao());
            ps.setString(5, livro.getGenero());
            ps.setString(6, livro.getDescricao());
            ps.setString(7, livro.getSumario());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    livro.setId(keys.getInt(1));
                }
            }
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir livro: " + e.getMessage(), e);
        }
    }

    public Livro buscarPorId(int id) {
        String sql = "SELECT * FROM livros WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearLivro(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro por ID: " + e.getMessage(), e);
        }
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        String sql = "SELECT * FROM livros WHERE titulo LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            List<Livro> livros = new ArrayList<>();

            while (rs.next()) {
                livros.add(mapearLivro(rs));
            }
            return livros;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro por título: " + e.getMessage(), e);
        }
    }

    public List<Livro> buscarPorAutor(String autor) {
        String sql = "SELECT * FROM livros WHERE autor LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + autor + "%");
            ResultSet rs = ps.executeQuery();
            List<Livro> livros = new ArrayList<>();

            while (rs.next()) {
                livros.add(mapearLivro(rs));
            }
            return livros;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro por autor: " + e.getMessage(), e);
        }
    }

    public List<Livro> buscarPorGenero(String genero) {
        String sql = "SELECT * FROM livros WHERE genero LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + genero + "%");
            ResultSet rs = ps.executeQuery();
            List<Livro> livros = new ArrayList<>();

            while (rs.next()) {
                livros.add(mapearLivro(rs));
            }
            return livros;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro por gênero: " + e.getMessage(), e);
        }
    }

    public List<Livro> listarTodos() {
        String sql = "SELECT * FROM livros";
        List<Livro> livros = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                livros.add(mapearLivro(rs));
            }
            return livros;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar livros: " + e.getMessage(), e);
        }
    }

    public boolean atualizar(Livro livro) {
        String sql = """
                UPDATE livros SET titulo = ?, autor = ?, editora = ?,
                ano_publicacao = ?, genero = ?, descricao = ?, sumario = ?
                WHERE id = ?
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getEditora());
            ps.setInt(4, livro.getAnoPublicacao());
            ps.setString(5, livro.getGenero());
            ps.setString(6, livro.getDescricao());
            ps.setString(7, livro.getSumario());
            ps.setInt(8, livro.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar livro: " + e.getMessage(), e);
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM livros WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            // ON DELETE CASCADE — exemplares vinculados serão deletados automaticamente
            throw new RuntimeException("Erro ao deletar livro: " + e.getMessage(), e);
        }
    }

    private Livro mapearLivro(ResultSet rs) throws SQLException {
        Timestamp createdAt = rs.getTimestamp("created_at");
        return new Livro(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getString("editora"),
                rs.getInt("ano_publicacao"),
                rs.getString("genero"),
                rs.getString("descricao"),
                rs.getString("sumario"),
                createdAt != null ? createdAt.toLocalDateTime() : null
        );
    }
}
