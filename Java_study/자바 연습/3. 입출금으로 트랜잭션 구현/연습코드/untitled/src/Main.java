import config.ConnectionManager;
import dao.AccountDao;
import dao.JdbcAccountDao;
import model.Account;
import service.TransferService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

class UserInterface {

    private final TransferService transferService;
    private final Scanner sc = new Scanner(System.in);

    public UserInterface(TransferService transferService) {
        this.transferService = transferService;
    }

    public void run() {
        while (true) {
            System.out.println("\n==== 계좌 트랜잭션 연습 ====");
            System.out.println("1. 계좌 조회");
            System.out.println("2. 이체");
            System.out.println("3. 전체 조회");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int menu = Integer.parseInt(sc.nextLine());

            try {
                switch (menu) {
                    case 1 -> showAccount();
                    case 2 -> transfer();
                    case 3 -> getAccounts();
                    case 0 -> {
                        System.out.println("종료합니다.");
                        return;
                    }
                    default -> System.out.println("잘못된 입력");
                }
            } catch (Exception e) {
                System.out.println("오류: " + e.getMessage());
            }
        }
    }
    private void getAccounts() throws SQLException, ClassNotFoundException {
        List<Account> accounts = transferService.getAllAccounts();
        for (Account account : accounts) {
            System.out.println("id: " + account.getId() +  " username: " + account.getUsername() + " balance: " + account.getBalance());
        }
    }

    private void showAccount() throws Exception {
        System.out.print("조회할 사용자명: ");
        String username = sc.nextLine();

        Account account = transferService.selectUserData(username);
        if (account == null) {
            System.out.println("계좌가 없습니다.");
            return;
        }

        System.out.println("ID: " + account.getId());
        System.out.println("USER: " + account.getUsername());
        System.out.println("BALANCE: " + account.getBalance());
    }

    private void transfer() throws Exception {
        System.out.print("출금 사용자명: ");
        String from = sc.nextLine();
        System.out.print("입금 사용자명: ");
        String to = sc.nextLine();
        System.out.print("금액: ");
        int amount = Integer.parseInt(sc.nextLine());

        transferService.transfer(from, to, amount);
        System.out.println("이체 완료@@!!");
    }
}


public class Main {
    public static void main(String[] args) {
        AccountDao accountDao = new JdbcAccountDao();
        ConnectionManager connectionManager = new ConnectionManager();
        TransferService transferService = new TransferService(connectionManager, accountDao);
        UserInterface ui = new UserInterface(transferService);
        ui.run();
    }
}