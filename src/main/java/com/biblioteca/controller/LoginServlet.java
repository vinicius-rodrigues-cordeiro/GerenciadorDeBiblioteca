package com.biblioteca.controller;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        Usuario usuario = authService.autenticar(email, senha);

        if (usuario != null) {

            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("tipo", usuario.getTipo());
            session.setMaxInactiveInterval(1800);

            response.setStatus(HttpServletResponse.SC_OK);

            out.print("{"
                    + "\"status\":\"success\","
                    + "\"nome\":\"" + usuario.getNome() + "\","
                    + "\"tipo\":\"" + usuario.getTipo() + "\""
                    + "}");

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            out.print("{"
                    + "\"status\":\"error\","
                    + "\"message\":\"Email ou senha inválidos\""
                    + "}");
        }

        out.flush();
    }
}