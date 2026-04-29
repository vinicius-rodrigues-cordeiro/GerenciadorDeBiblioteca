package com.biblioteca.filter;

import com.biblioteca.model.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebFilter("/*") // intercepta todas as requisições
public class FiltroAutenticacao implements Filter {

    // Rotas públicas — não precisam de login
    private static final List<String> ROTAS_PUBLICAS = List.of(
            "/login",
            "/logout",
            "/css",
            "/js",
            "/imagens"
    );

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request   = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri         = request.getRequestURI();
        String contextPath = request.getContextPath();

        // Verifica se é rota pública
        boolean rotaPublica = ROTAS_PUBLICAS.stream()
                .anyMatch(rota -> uri.startsWith(contextPath + rota));

        if (rotaPublica) {
            chain.doFilter(req, resp);
            return;
        }

        // Verifica se há sessão ativa
        HttpSession session       = request.getSession(false);
        Usuario usuarioLogado     = session != null
                ? (Usuario) session.getAttribute("usuarioLogado")
                : null;

        if (usuarioLogado == null) {
            // Não logado — redireciona para login
            response.sendRedirect(contextPath + "/login");
        } else {
            // Logado — deixa a requisição seguir
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}