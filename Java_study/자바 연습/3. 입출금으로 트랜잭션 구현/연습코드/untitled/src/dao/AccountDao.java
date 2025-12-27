package dao;

import model.Account;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
    List<Account> getAllAccounts(Connection conn) throws SQLException;
    Account findByUsername(Connection conn, String owner_name) throws SQLException;
    void changeBalance(Connection conn, String owner_name, int balance) throws SQLException;
}
