package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final PostService postService;

    // 전체 데이터 받기
    @GetMapping("/posts")
    public ResponseEntity<Page<Post>> getAllPosts(@RequestParam(defaultValue = "0") int page) {
        Page<Post> posts = postService.findAll(page);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(postService.findById(id));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/write")
    public ResponseEntity<Post> writePost(@RequestBody Post post) {
        try {
            Post newPost = postService.writePost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable int id, @RequestBody Post post) {
        try {
            Post updatedPost = postService.updatePost(id, post.getTitle(), post.getContent());
            return ResponseEntity.ok(updatedPost);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
