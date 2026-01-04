package com.example.demo.controller;

import com.example.demo.service.PostService;
import com.example.demo.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor // PostService를 자동으로 주입받기 위해 사용
public class PostController {

    private final PostService postService;

    // 1. 게시글 목록 조회
    @GetMapping("/posts")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Post> paging = postService.findAll(page);
        model.addAttribute("paging", paging);
        return "posts/postList";
    }

    // 2. 게시글 작성 화면 이동
    @GetMapping("/posts/new")
    public String createForm() {
        return "posts/createPostForm"; // templates/posts/createPostForm.html
    }

    // 3. 게시글 실제 저장 로직
    @PostMapping("/posts/new")
    public String create(@RequestParam("title") String title,
                         @RequestParam("content") String content,
                         @RequestParam("author") String author) {
        Post post = new Post(title, content, author);
        postService.save(post);
        return "redirect:/posts"; // 저장 후 목록 페이지로 리다이렉트
    }


    // 1. 상세 페이지 조회
    @GetMapping("/posts/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts/postDetail"; // templates/posts/postDetail.html 생성 예정
    }

    // 2. 게시글 삭제 로직
    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/posts"; // 삭제 후 목록으로 이동
    }

    // PostController.java에 추가

    // 1. 수정 폼으로 이동
    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts/editPostForm"; // 이따가 만들 HTML 파일명
    }

    // 2. 실제 수정 로직 처리
    @PostMapping("/posts/{id}/edit")
    public String edit(@PathVariable Long id,
                       @RequestParam("title") String title,
                       @RequestParam("content") String content) {

        postService.update(id, title, content); // 아까 만든 Service의 update 호출
        return "redirect:/posts/{id}"; // 수정한 글의 상세 페이지로 다시 이동
    }
}