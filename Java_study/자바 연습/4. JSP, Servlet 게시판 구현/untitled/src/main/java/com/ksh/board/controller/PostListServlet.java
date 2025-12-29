package com.ksh.board.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/posts")
public class PostListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 임시 데이터 (나중에 DB로 교체)
        request.setAttribute("title", "게시판 목록");

        request.getRequestDispatcher("/WEB-INF/views/post/list.jsp")
                .forward(request, response);
    }
}
