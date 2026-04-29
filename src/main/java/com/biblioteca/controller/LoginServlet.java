package com.biblioteca.controller;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.AutenticacaoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AutenticacaoService autenticacaoService = new AutenticacaoService();

    // Exibe a página de login
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    // Processa o formulário de login
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = autenticacaoService.autenticar(email, senha);

        switch (autenticacaoService.getUltimoResultado()) {
            case SUCESSO -> {
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuario);
                session.setMaxInactiveInterval(1800);

                // Redireciona conforme o tipo
                switch (usuario.getTipo()) {
                    case ADMIN         -> response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    case BIBLIOTECARIO -> response.sendRedirect(request.getContextPath() + "/bibliotecario/dashboard");
                    case ESTUDANTE,
                         COMUM         -> response.sendRedirect(request.getContextPath() + "/home");
                }
            }
            case USUARIO_BLOQUEADO -> {
                request.setAttribute("erro", "Usuário bloqueado. Entre em contato com a biblioteca.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
            case SENHA_INCORRETA -> {
                request.setAttribute("erro", "Senha incorreta. Tente novamente.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
            case USUARIO_NAO_ENCONTRADO -> {
                request.setAttribute("erro", "Usuário não encontrado.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        }
    }
}