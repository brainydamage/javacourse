package com.epam.javacourse.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecondFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("SecondFilter is going to filter");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String param = request.getParameter("filter");

        if(!"second".equals(param)){
            filterChain.doFilter(request, response);
            System.out.println("SecondFilter has been passed");
            return;
        }

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.getWriter().write("Request have been filtered by SecondFilter");
    }

    public void destroy() {
        System.out.println("SecondFilter has finished filtering");
    }
}
