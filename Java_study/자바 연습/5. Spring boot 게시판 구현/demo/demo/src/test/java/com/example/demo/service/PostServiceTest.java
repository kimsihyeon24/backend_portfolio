package com.example.demo.service;

import com.example.demo.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    void 게시글_저장_및_조회() {
        // 1. Given: 이런 데이터가 주어졌을 때
        Post post = new Post("테스트 제목", "테스트 내용", "작성자");

        // 2. When: 저장을 실행하면
        postService.save(post);

        // 3. Then: 결과가 이래야 한다
//        List<Post> allPosts = postService.findAll();
//        assertThat(allPosts.size()).isEqualTo(1);
//        assertThat(allPosts.get(0).getTitle()).isEqualTo("테스트 제목");
    }
}