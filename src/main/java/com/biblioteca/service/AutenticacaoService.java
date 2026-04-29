package com.biblioteca.service;

import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Usuario;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AutenticacaoService {

    // Enum para o Controller saber exatamente o que aconteceu
    public enum ResultadoAutenticacao {
        SUCESSO,
        USUARIO_NAO_ENCONTRADO,
        SENHA_INCORRETA,
        USUARIO_BLOQUEADO
    }

    private final UsuarioDAO usuarioDAO;
    private ResultadoAutenticacao ultimoResultado;

    public AutenticacaoService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String email, String senha) {

        // 1. Validação básica de entrada
        if (email == null || email.isBlank() ||
                senha == null || senha.isBlank()) {
            ultimoResultado = ResultadoAutenticacao.USUARIO_NAO_ENCONTRADO;
            return null;
        }

        // 2. Busca o usuário pelo email
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario == null) {
            ultimoResultado = ResultadoAutenticacao.USUARIO_NAO_ENCONTRADO;
            return null;
        }

        // 3. Verifica se está bloqueado
        if (usuario.isBloqueado()) {
            ultimoResultado = ResultadoAutenticacao.USUARIO_BLOQUEADO;
            return null;
        }

        // 4. Compara o hash da senha
        String senhaHash = gerarHash(senha);
        if (!usuario.getSenhaHash().equals(senhaHash)) {
            registrarTentativaFalha(usuario);
            ultimoResultado = ResultadoAutenticacao.SENHA_INCORRETA;
            return null;
        }

        // 5. Login bem sucedido — reseta tentativas
        resetarTentativas(usuario);
        ultimoResultado = ResultadoAutenticacao.SUCESSO;
        return usuario;
    }

    // Registra falha e bloqueia após 3 tentativas
    private void registrarTentativaFalha(Usuario usuario) {
        usuario.setTentativasLogin(usuario.getTentativasLogin() + 1);
        usuario.setUltimaTentativa(java.time.LocalDateTime.now());

        if (usuario.getTentativasLogin() >= 3) {
            usuario.setBloqueado(true);
        }

        usuarioDAO.atualizarTentativas(usuario);
    }

    // Reseta tentativas após login bem sucedido
    private void resetarTentativas(Usuario usuario) {
        usuario.setTentativasLogin(0);
        usuario.setUltimaTentativa(null);
        usuarioDAO.atualizarTentativas(usuario);
    }

    // Gera SHA-256 da senha para comparar com o banco
    public static String gerarHash(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha.", e);
        }
    }

    public ResultadoAutenticacao getUltimoResultado() {
        return ultimoResultado;
    }
}

