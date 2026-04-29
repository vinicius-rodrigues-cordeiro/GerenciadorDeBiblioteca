package com.biblioteca.controller;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.MultaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/multas/*")
public class MultaController extends HttpServlet {

    private final MultaService multaService = new MultaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        request.setAttribute("multas",
                multaService.buscarPorUsuario(usuarioLogado.getId()));
        request.getRequestDispatcher("/WEB-INF/views/multas/lista.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            multaService.registrarPagamento(id);
            response.sendRedirect(request.getContextPath() + "/multas");

        } catch (IllegalArgumentException | IllegalStateException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/multas/lista.jsp")
                    .forward(request, response);
        }
    }
}
