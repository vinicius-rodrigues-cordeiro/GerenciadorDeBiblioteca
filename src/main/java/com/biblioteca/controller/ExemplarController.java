package com.biblioteca.controller;

import com.biblioteca.model.Exemplar;
import com.biblioteca.service.ExemplarService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/exemplares/*")
public class ExemplarController extends HttpServlet {

    private final ExemplarService exemplarService = new ExemplarService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            int livroId = Integer.parseInt(request.getParameter("livroId"));
            request.setAttribute("exemplares", exemplarService.buscarPorLivro(livroId));
            request.getRequestDispatcher("/WEB-INF/views/exemplares/lista.jsp")
                    .forward(request, response);
        } else {
            int id = Integer.parseInt(pathInfo.substring(1));
            request.setAttribute("exemplar", exemplarService.buscarPorId(id));
            request.getRequestDispatcher("/WEB-INF/views/exemplares/detalhe.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Exemplar exemplar = new Exemplar(
                    Integer.parseInt(request.getParameter("livroId")),
                    Integer.parseInt(request.getParameter("unidadeId")),
                    request.getParameter("codigoPatrimonio")
            );

            exemplarService.cadastrar(exemplar);
            response.sendRedirect(request.getContextPath()
                    + "/exemplares?livroId=" + request.getParameter("livroId"));

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/exemplares/cadastro.jsp")
                    .forward(request, response);
        }
    }
}
