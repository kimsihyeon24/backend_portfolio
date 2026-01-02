package org.example.board.controller.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.example.board.service.PostService;

import java.io.IOException;

@WebServlet("/posts/delete")
public class PostDeleteServlet extends HttpServlet {
    private final PostService postService = new PostService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            postService.delete(id);
        } catch (Exception ignored) {}

        response.sendRedirect(request.getContextPath() + "/posts");
    }
}
