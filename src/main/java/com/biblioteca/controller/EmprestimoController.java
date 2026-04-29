package com.biblioteca.controller;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.EmprestimoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/emprestimos/*")
public class EmprestimoController extends HttpServlet {

    private final EmprestimoService emprestimoService = new EmprestimoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (pathInfo == null || pathInfo.equals("/")) {
            request.setAttribute("emprestimos",
                    emprestimoService.buscarPorUsuario(usuarioLogado.getId()));
            request.getRequestDispatcher("/WEB-INF/views/emprestimos/lista.jsp")
                    .forward(request, response);
        } else {
            int id = Integer.parseInt(pathInfo.substring(1));
            request.setAttribute("emprestimo", emprestimoService.buscarPorId(id));
            request.getRequestDispatcher("/WEB-INF/views/emprestimos/detalhe.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String acao = request.getParameter("acao");

            if ("devolver".equals(acao)) {
                // Finalizar empréstimo
                int id = Integer.parseInt(request.getParameter("id"));
                emprestimoService.finalizarEmprestimo(id);
            } else {
                // Realizar empréstimo
                int exemplarId = Integer.parseInt(request.getParameter("exemplarId"));
                int usuarioId  = Integer.parseInt(request.getParameter("usuarioId"));
                emprestimoService.realizarEmprestimo(exemplarId, usuarioId);
            }

            response.sendRedirect(request.getContextPath() + "/emprestimos");

        } catch (IllegalArgumentException | IllegalStateException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/emprestimos/lista.jsp")
                    .forward(request, response);
        }
    }
}
