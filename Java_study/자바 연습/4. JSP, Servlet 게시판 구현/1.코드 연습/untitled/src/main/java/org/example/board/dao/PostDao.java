package org.example.board.dao;

import org.example.board.model.Post;
import org.example.board.util.Db; // 형님이 쓰는 커넥션 유틸로 맞춰
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDao {

    public List<Post> findAll() {
        String sql = "SELECT id, title, content, created_at FROM posts ORDER BY id DESC";
        List<Post> result = new ArrayList<>();

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setContent(rs.getString("content"));
                p.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                result.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Post findById(int id) {
        String sql = "SELECT id, title, content, created_at FROM posts WHERE id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setContent(rs.getString("content"));
                p.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Post post) {
        String sql = "INSERT INTO posts(title, content) VALUES(?, ?)";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM posts WHERE id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Post post) {
        String sql = "UPDATE posts SET title = ?, content = ? WHERE id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setInt(3, post.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
