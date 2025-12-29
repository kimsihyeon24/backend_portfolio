package service;

import config.ConnectionManager;
import dao.AccountDao;
import model.Account;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TransferService {

    private final ConnectionManager connectionManager;
    private final AccountDao accountDao;

    public TransferService(ConnectionManager connectionManager, AccountDao accountDao) {
        this.connectionManager = connectionManager;
        this.accountDao = accountDao;
    }

    public List<Account> getAllAccounts() throws SQLException, ClassNotFoundException {
        try (Connection conn = connectionManager.getConnection()) {
            return accountDao.getAllAccounts(conn);
        }
    }

    public Account selectUserData(String username) throws SQLException {
        try (Connection conn = connectionManager.getConnection()) {
            return accountDao.findByUsername(conn, username);
        }
    }

    public void transfer(String from, String to, int amount) throws SQLException {
        Connection conn = connectionManager.getConnection();
        try {
            conn.setAutoCommit(false);

            Account fromAcc = accountDao.findByUsername(conn, from);
            Account toAcc   = accountDao.findByUsername(conn, to);

            if (fromAcc == null) throw new SQLException("출금 계좌 없음: " + from);
            if (toAcc == null)   throw new SQLException("입금 계좌 없음: " + to);
            if (amount <= 0)     throw new SQLException("금액 오류: " + amount);

            if (fromAcc.getBalance() < amount) {
                throw new SQLException("잔고 부족: " + from + " 현재=" + fromAcc.getBalance());
            }

            accountDao.changeBalance(conn, from, -amount); // 출금
            // 트랜잭션 테스트코드
//             if (true) throw new RuntimeException("중간 장애 발생!");

            accountDao.changeBalance(conn, to, +amount);   // 입금

//            System.out.println("이체 완료@@!!"); // 절대 커밋 전에 찍지마라... 커밋 안하고 되면 큰일난다.

            conn.commit();
            System.out.println("이체 완료@@!!");

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ignore) {}
            if (e instanceof SQLException) throw (SQLException) e;
            throw new SQLException("transfer 실패: " + e.getMessage(), e);
        } finally {
            try { conn.close(); } catch (Exception ignore) {}
        }
    }
}
