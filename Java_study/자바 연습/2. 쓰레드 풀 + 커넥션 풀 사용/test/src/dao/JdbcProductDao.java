package dao;

import model.Product;
import config.DataSourceProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {

    private final DataSourceProvider dataSourceProvider;

    public JdbcProductDao(DataSourceProvider dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }

    @Override
    public void insert(Product product) {
        String sql = "INSERT INTO product(name, price, quantity) VALUES (?, ?, ?)";
        try (
                Connection conn = dataSourceProvider.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            Thread.sleep(10000);
            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.executeUpdate();
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println("추가 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> printAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT name, price, quantity FROM product";
        try (
                Connection conn = dataSourceProvider.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
            ) {
            while (rs.next()) {
                Product p = new Product(
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("quantity")
                );
                list.add(p);
            }
        }
        catch (SQLException e)  {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void update(int id, String newName, int newPrice, int newQuantity) {
        String sql = "UPDATE product SET name=?, price=?, quantity=? WHERE id=?";

        try (
                Connection conn = dataSourceProvider.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, newName);
            ps.setInt(2, newPrice);
            ps.setInt(3, newQuantity);
            ps.setInt(4, id);

            ps.executeUpdate();
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println("업데이트 완료했습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteOne(int id) {
        String sql = "DELETE FROM product WHERE id=?";

        try (
                Connection conn = dataSourceProvider.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println("삭제 완료했습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM product";
        try (
                Connection conn = dataSourceProvider.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            ps.executeUpdate();
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println("전체 삭제 완료했습니다.");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
