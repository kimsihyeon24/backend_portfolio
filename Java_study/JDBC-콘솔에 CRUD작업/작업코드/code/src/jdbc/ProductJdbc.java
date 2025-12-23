package jdbc;

import model.ProductData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductJdbc {
    // 데이터 삽입
    public int insert(String name, int price, int stock) throws SQLException {
        String sql = "INSERT INTO product(name, price, stock) VALUES (?, ?, ?)";

         Connection conn = DBUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setString(1, name);
         ps.setInt(2, price);
         ps.setInt(3, stock);

         return ps.executeUpdate(); // 성공하면 1

    }
    // 전체 조회
    public List<ProductData> findAll() throws SQLException {
        List<ProductData> list = new ArrayList<>();

        String sql = "SELECT id, name, price, stock FROM product";

        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ProductData p = new ProductData(
                    rs.getInt("Id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getInt("stock")
            );
            list.add(p);
        }
        return list;
    }

    // 데이터 삭제
    public int deleteById(String name) throws SQLException{
        String sql = "DELETE FROM product WHERE name = ?";

        var conn = DBUtil.getConnection();
        var ps = conn.prepareStatement(sql);

        ps.setString(1, name);
        return ps.executeUpdate(); // 성공: 1, 실패(없음): 0
    }

    // 데이터 수정
    public int update(ProductData p) throws SQLException{
        String sql = "UPDATE product SET name = ?, price = ?, stock = ? WHERE id = ?";

        var conn = DBUtil.getConnection();
        var ps = conn.prepareStatement(sql);

        ps.setString(1, p.getName());
        ps.setInt(2, p.getPrice());
        ps.setInt(3, p.getStock());
        ps.setInt(4, p.getId());

        return ps.executeUpdate(); // 성공: 1, 실패: 0
    }

}



