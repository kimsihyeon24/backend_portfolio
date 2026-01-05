package com.example.demo.service;

import com.example.demo.domain.Comment;
import com.example.demo.domain.Post;
import com.example.demo.repository.CommentRepository;

import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long writeComment(Long postId, Comment comment) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        comment.setPost(post);
        commentRepository.save(comment);

        return comment.getId();
    }

    public List<Comment> findCommentsByPost(Long commentId) {
        return commentRepository.findByPostId(commentId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment commnet = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        commentRepository.delete(commnet);
    }
}
