package br.com.sistemadesupermercado.application.web.filter;

import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by wagner on 14/10/15.
 */
//@WebFilter(urlPatterns = "/*")
public class LogSessionIdFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String path = httpRequest.getServletPath();

        if (!excludeFromFilter(path)) {
            MDC.put("username", getUserName());
            MDC.put("sessionId", getSessionId(httpRequest));
            MDC.put("clientIpAddress", getClientIpAddr(httpRequest));

            MDC.put("userAgent", httpRequest.getHeader("user-agent"));
            MDC.put("requestUri", httpRequest.getRequestURI());
        }

        chain.doFilter(request, response);

    }

    private String getSessionId(HttpServletRequest httpRequest) {
        String sessionId = null;
        HttpSession session = httpRequest.getSession();
        if (session != null) {
            sessionId = session.getId();
        }
        return sessionId;
    }

    private String getUserName() {
        String userName = null;

        SecurityContext context = SecurityContextHolder.getContext();

        if (context != null) {
            Authentication authentication = context.getAuthentication();
            if (authentication != null) {
                userName = authentication.getName();
            }
        }
        return userName;
    }

    @Override
    public void destroy() {

    }

    private static Pattern excludeUrls = Pattern.compile(".*(css|js|gif|jpe?g|png)(.xhtml)?.*$", Pattern.CASE_INSENSITIVE);

    private boolean excludeFromFilter(String path) {
        return excludeUrls.matcher(path).matches() || path.startsWith("/javax.faces.resource");
    }

    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
