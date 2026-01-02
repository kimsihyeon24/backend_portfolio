package org.example.board.controller.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.example.board.service.PostService;

import java.io.IOException;

@WebServlet("/posts")
public class PostListServlet extends HttpServlet {
    private final PostService postService = new PostService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("posts", postService.list());
        request.getRequestDispatcher("/WEB-INF/views/post/list.jsp")
                .forward(request, response);
    }
}
