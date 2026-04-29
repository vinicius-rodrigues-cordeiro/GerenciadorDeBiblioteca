package com.biblioteca.controller;

import com.biblioteca.model.Unidade;
import com.biblioteca.service.UnidadeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/unidades/*")
public class UnidadeController extends HttpServlet {

    private final UnidadeService unidadeService = new UnidadeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            request.setAttribute("unidades", unidadeService.listarTodas());
            request.getRequestDispatcher("/WEB-INF/views/unidades/lista.jsp")
                    .forward(request, response);
        } else {
            int id = Integer.parseInt(pathInfo.substring(1));
            request.setAttribute("unidade", unidadeService.buscarPorId(id));
            request.getRequestDispatcher("/WEB-INF/views/unidades/detalhe.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Verifica se é atualização ou cadastro
            String idParam = request.getParameter("id");

            if (idParam != null && !idParam.isBlank()) {
                // Atualização
                Unidade unidade = unidadeService.buscarPorId(Integer.parseInt(idParam));
                unidade.setNome(request.getParameter("nome"));
                unidade.setEndereco(request.getParameter("endereco"));
                unidade.setTelefone(request.getParameter("telefone"));
                unidade.setHorarioFuncionamento(request.getParameter("horarioFuncionamento"));
                unidadeService.atualizar(unidade);
            } else {
                // Cadastro
                Unidade unidade = new Unidade(
                        request.getParameter("nome"),
                        request.getParameter("endereco"),
                        request.getParameter("telefone"),
                        request.getParameter("horarioFuncionamento")
                );
                unidadeService.cadastrar(unidade);
            }

            response.sendRedirect(request.getContextPath() + "/unidades");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/unidades/cadastro.jsp")
                    .forward(request, response);
        }
    }
}
