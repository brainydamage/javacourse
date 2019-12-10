package com.epam.javacourse.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HelloWorldServlet extends HttpServlet {

    //cache problem with cores and RAM flush. Solution: volatile/Atomic
    //volatile is better - faster? why else?
    //int counter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write( "Hi there" + "\n");

        HttpSession session = req.getSession();
        session.invalidate();

    }
}
