package org.example.board.controller.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.example.board.service.PostService;

import java.io.IOException;

@WebServlet("/posts/edit")
public class PostEditServlet extends HttpServlet {
    private final PostService postService = new PostService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("post", postService.get(id));
            request.getRequestDispatcher("/WEB-INF/views/post/edit.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/posts");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");

            if (title.isBlank() || content.isBlank()) {
                request.setAttribute("error", "제목과 내용을 입력하세요");
                request.setAttribute("post", postService.get(id));
                request.getRequestDispatcher("/WEB-INF/views/post/edit.jsp")
                        .forward(request, response);
                return;
            }

            postService.edit(id, title, content);
            response.sendRedirect(request.getContextPath() + "/posts/detail?id=" + id);

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/posts");
        }
    }
}
