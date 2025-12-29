package dao;

import model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcAccountDao implements AccountDao {

    @Override
    public List<Account> getAllAccounts(Connection conn) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "select id, owner_name, balance from accounts";
        try (
                PreparedStatement ps =  conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                ) {
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("id"),
                        rs.getString("owner_name"),
                        rs.getInt("balance")
                );
                accounts.add(account);
            }
        }
        return accounts;
    }

    @Override
    public Account findByUsername(Connection conn, String owner_name) throws SQLException {
        String sql = "SELECT id, owner_name, balance FROM accounts WHERE owner_name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, owner_name);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new Account(
                        rs.getInt("id"),
                        rs.getString("owner_name"),
                        rs.getInt("balance")
                );
            }
        }
    }

    @Override
    public void changeBalance(Connection conn, String owner_name, int balance) throws SQLException {
//        String sql = "UPDATE accounts SET balance = balance + ? WHERE owner_name = ?";  여기서 한번에 입출금에 대한 거 매겨버림
        String sql = "UPDATE accounts SET balance = balance + ? WHERE owner_name = ? AND ( ? >= 0 OR balance + ? >= 0 )";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, balance);
            ps.setString(2, owner_name);
            ps.setInt(3, balance);
            ps.setInt(4, balance);

            int updated = ps.executeUpdate();
            if (updated != 1) {
                throw new SQLException("계좌 업데이트 실패(존재하지 않음): " + owner_name);
            }
        }
    }
}
