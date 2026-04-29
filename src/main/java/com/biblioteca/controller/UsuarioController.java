package com.biblioteca.controller;

import com.biblioteca.model.*;
import com.biblioteca.service.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/usuarios/*")
public class UsuarioController extends HttpServlet {

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            request.setAttribute("usuarios", usuarioService.listarTodos());
            request.getRequestDispatcher("/WEB-INF/views/usuarios/lista.jsp")
                    .forward(request, response);
        } else {
            int id = Integer.parseInt(pathInfo.substring(1));
            request.setAttribute("usuario", usuarioService.buscarPorId(id));
            request.getRequestDispatcher("/WEB-INF/views/usuarios/detalhe.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String tipo     = request.getParameter("tipo");
            String nome     = request.getParameter("nome");
            String email    = request.getParameter("email");
            String senha    = request.getParameter("senha");
            String telefone = request.getParameter("telefone");

            Usuario usuario = switch (tipo.toUpperCase()) {
                case "ESTUDANTE" -> {
                    UsuarioEstudante e = new UsuarioEstudante(
                            nome, email, senha,
                            Integer.parseInt(request.getParameter("ra")));
                    e.setTelefone(telefone);
                    yield e;
                }
                case "BIBLIOTECARIO" -> {
                    UsuarioBibliotecario b = new UsuarioBibliotecario(
                            nome, email, senha,
                            Integer.parseInt(request.getParameter("unidadeId")));
                    b.setTelefone(telefone);
                    yield b;
                }
                case "ADMIN" -> {
                    UsuarioAdministrador a = new UsuarioAdministrador(nome, email, senha);
                    a.setTelefone(telefone);
                    yield a;
                }
                default -> {
                    Usuario u = new Usuario(nome, email, senha, Usuario.Tipo.COMUM);
                    u.setTelefone(telefone);
                    yield u;
                }
            };

            usuarioService.cadastrar(usuario);
            response.sendRedirect(request.getContextPath() + "/usuarios");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/usuarios/cadastro.jsp")
                    .forward(request, response);
        }
    }
}
