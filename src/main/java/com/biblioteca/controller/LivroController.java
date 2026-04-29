package com.biblioteca.controller;

import com.biblioteca.model.Livro;
import com.biblioteca.service.LivroService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/livros/*")
public class LivroController extends HttpServlet {

    private final LivroService livroService = new LivroService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String busca    = request.getParameter("busca");
        String filtro   = request.getParameter("filtro");

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Livro> livros;

            if (busca != null && !busca.isBlank()) {
                livros = switch (filtro != null ? filtro : "titulo") {
                    case "autor"  -> livroService.buscarPorAutor(busca);
                    case "genero" -> livroService.buscarPorGenero(busca);
                    default       -> livroService.buscarPorTitulo(busca);
                };
            } else {
                livros = livroService.listarTodos();
            }

            request.setAttribute("livros", livros);
            request.getRequestDispatcher("/WEB-INF/views/livros/lista.jsp")
                    .forward(request, response);
        } else {
            int id = Integer.parseInt(pathInfo.substring(1));
            request.setAttribute("livro", livroService.buscarPorId(id));
            request.getRequestDispatcher("/WEB-INF/views/livros/detalhe.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idParam = request.getParameter("id");

            if (idParam != null && !idParam.isBlank()) {
                // Atualização
                Livro livro = livroService.buscarPorId(Integer.parseInt(idParam));
                livro.setTitulo(request.getParameter("titulo"));
                livro.setAutor(request.getParameter("autor"));
                livro.setEditora(request.getParameter("editora"));
                livro.setAnoPublicacao(Integer.parseInt(request.getParameter("anoPublicacao")));
                livro.setGenero(request.getParameter("genero"));
                livro.setDescricao(request.getParameter("descricao"));
                livro.setSumario(request.getParameter("sumario"));
                livroService.atualizar(livro);
            } else {
                // Cadastro
                Livro livro = new Livro(
                        request.getParameter("titulo"),
                        request.getParameter("autor"),
                        request.getParameter("editora"),
                        Integer.parseInt(request.getParameter("anoPublicacao")),
                        request.getParameter("genero"),
                        request.getParameter("descricao"),
                        request.getParameter("sumario")
                );
                livroService.cadastrar(livro);
            }

            response.sendRedirect(request.getContextPath() + "/livros");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/livros/cadastro.jsp")
                    .forward(request, response);
        }
    }
}
