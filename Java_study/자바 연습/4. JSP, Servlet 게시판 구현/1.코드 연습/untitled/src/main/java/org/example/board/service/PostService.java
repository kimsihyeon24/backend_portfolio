package org.example.board.service;

import org.example.board.dao.PostDao;
import org.example.board.model.Post;

import java.util.List;

public class PostService {
    private final PostDao postDao = new PostDao();

    public List<Post> list() {
        return postDao.findAll();
    }

    public Post get(int id) {
        return postDao.findById(id);
    }

    public void write(String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        postDao.save(post);
    }

    public void delete(int id) {
        postDao.deleteById(id);
    }

    // 수정까지 할 거면
    public void edit(int id, String title, String content) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setContent(content);
        postDao.update(post);
    }
}
