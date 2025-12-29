package org.example.board.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/posts")
public class PostListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("msg", "Hello Servlet");
        request.getRequestDispatcher("/WEB-INF/views/post/list.jsp")
                .forward(request, response);
    }
}
