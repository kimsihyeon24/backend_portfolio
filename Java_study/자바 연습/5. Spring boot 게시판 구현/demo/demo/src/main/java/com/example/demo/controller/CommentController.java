package com.example.demo.controller;

import com.example.demo.domain.Comment;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor

public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public String writeComment(@PathVariable Long postId, Comment comment) {
        commentService.writeComment(postId, comment);
        // 저장이 끝나면 다시 게시글 상세 페이지로 "이동해!"라고 명령
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/comments/{postId}")
    public List<Comment> getComments(@PathVariable Long postId) {
        return commentService.findCommentsByPost(postId);
    }

    @PostMapping("/posts/{postId}/comments/{commentId}/delete")
    public String  deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return "redirect:/posts/" + commentId;
    }
}
