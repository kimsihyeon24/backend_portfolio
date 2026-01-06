package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시판 전체 조회
//    @Transactional(readOnly = true)
//    public List<Post> findAll() {
//        return postRepository.findAll();
//    }

    // 페이지네이션 조회
    @Transactional(readOnly = true)
    public Page<Post> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "id"));
        return postRepository.findAll(pageable);
    }

    // 게시판 한개 조회
    @Transactional(readOnly = true)
    public Post findById(int id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 없음."));
    }

    // 게시판 작성
    public Post writePost(Post post) {
        postRepository.save(post);
        return post;
    }

    // 게시판 삭제
    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }

    // 게시판 수정
    public Post updatePost(int id, String title, String content) {
        Post updatePost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없어"));
        updatePost.updatePost(title, content);
        return updatePost;
    }



}
