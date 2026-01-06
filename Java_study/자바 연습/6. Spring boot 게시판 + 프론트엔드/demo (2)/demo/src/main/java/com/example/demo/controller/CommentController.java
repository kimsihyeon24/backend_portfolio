package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comment/{postId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable int postId) {
        return ResponseEntity.ok(commentService.findAllComment(postId));
    }

    @PostMapping("/comment/write/{postId}")
    public ResponseEntity<List<Comment>> writeComment(@PathVariable int postId, @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.crearteComment(postId, comment));
    }

    @DeleteMapping("/comment/delete/{postId}/{commentId}")
    public ResponseEntity<List<Comment>> deleteComment(@PathVariable int postId, @PathVariable int commentId) {
        return ResponseEntity.ok(commentService.deleteComment(postId, commentId));
    }

    @PutMapping("/comment/update/{postId}")
    public ResponseEntity<List<Comment>> updateComment(@PathVariable int postId, @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.updateComment(postId, comment));
    }
}
