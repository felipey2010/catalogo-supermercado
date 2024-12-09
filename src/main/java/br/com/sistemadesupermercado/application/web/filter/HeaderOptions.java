package br.com.sistemadesupermercado.application.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wagner on 04/01/16.
 */
//@WebFilter(urlPatterns = "/*")
public class HeaderOptions implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader("X-FRAME-OPTIONS", "SameOrigin");

        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }

}