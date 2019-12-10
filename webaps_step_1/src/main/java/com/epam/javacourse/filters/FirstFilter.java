package com.epam.javacourse.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirstFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("FirstFilter is going to filter");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String param = request.getParameter("filter");

        if(!"first".equals(param)){
            filterChain.doFilter(request, response);
            System.out.println("FirstFilter has been passed");
            return;
        }

        System.out.println("Filter 1: STOP");
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.getWriter().write("Request have been filtered by FirstFilter");
    }

    public void destroy() {
        System.out.println("FirstFilter has finished filtering");
    }
}
