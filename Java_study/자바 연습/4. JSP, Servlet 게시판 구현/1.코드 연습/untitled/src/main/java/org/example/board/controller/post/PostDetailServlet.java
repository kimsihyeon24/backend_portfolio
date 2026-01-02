package org.example.board.controller.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.example.board.service.PostService;

import java.io.IOException;

@WebServlet("/posts/detail")
public class PostDetailServlet extends HttpServlet {
    private final PostService postService = new PostService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("post", postService.get(id));
            request.getRequestDispatcher("/WEB-INF/views/post/detail.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/posts");
        }
    }
}
