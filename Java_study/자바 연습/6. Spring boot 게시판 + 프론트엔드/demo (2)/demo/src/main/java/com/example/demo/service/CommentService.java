package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 조회
    @Transactional(readOnly = true)
    public List<Comment> findAllComment (int postId) {
        return commentRepository.findAllByPostId(postId);
    }

    // 한개 조회
    @Transactional(readOnly = true)
    public Comment findCommentById(int commentId) {
        return commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("댓글 존재 안함"));
    }

    // 생성
    public List<Comment> crearteComment (int postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 존재하지 않습니다."));

        comment.setPost(post);
        commentRepository.save(comment);
        return findAllComment(postId);
    }

    // 삭제
    public List<Comment> deleteComment (int postId, int commentId) {
        commentRepository.deleteById(commentId);
        return findAllComment(postId);
    }

    // 수정
    public List<Comment> updateComment (int postId, Comment comment) {
        Comment updatedComment = findCommentById(comment.getId());
        updatedComment.updateComment(comment.getContent());
        return findAllComment(postId);
    }


}
