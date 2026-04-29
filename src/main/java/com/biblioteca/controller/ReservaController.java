package com.biblioteca.controller;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.ReservaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/reservas/*")
public class ReservaController extends HttpServlet {

    private final ReservaService reservaService = new ReservaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        request.setAttribute("reservas",
                reservaService.buscarPorUsuario(usuarioLogado.getId()));
        request.getRequestDispatcher("/WEB-INF/views/reservas/lista.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String acao = request.getParameter("acao");
            HttpSession session = request.getSession(false);
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

            if ("cancelar".equals(acao)) {
                // Cancelar reserva
                int id = Integer.parseInt(request.getParameter("id"));
                reservaService.cancelarReserva(id);
            } else {
                // Realizar reserva
                int livroId   = Integer.parseInt(request.getParameter("livroId"));
                int unidadeId = Integer.parseInt(request.getParameter("unidadeId"));
                reservaService.realizarReserva(livroId, usuarioLogado.getId(), unidadeId);
            }

            response.sendRedirect(request.getContextPath() + "/reservas");

        } catch (IllegalArgumentException | IllegalStateException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/reservas/lista.jsp")
                    .forward(request, response);
        }
    }
}
