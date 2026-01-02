package org.example.board.controller.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.example.board.service.PostService;

import java.io.IOException;

@WebServlet("/posts/write")
public class PostWriteServlet extends HttpServlet {
    private final PostService postService = new PostService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/post/write.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        if (title == null || title.isBlank() || content == null || content.isBlank()) {
            request.setAttribute("error", "제목과 내용을 입력하세요");
            request.getRequestDispatcher("/WEB-INF/views/post/write.jsp")
                    .forward(request, response);
            return;
        }

        postService.write(title, content);
        response.sendRedirect(request.getContextPath() + "/posts");
    }
}
