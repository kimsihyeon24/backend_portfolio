import jdbc.ProductJdbc;
import model.ProductData;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

class UserInterface {
    ProductJdbc productJdbc = new ProductJdbc();

    public int insertProduct() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("이름: ");
        String name = sc.nextLine();
        System.out.println("가격: ");
        int price = sc.nextInt();
        System.out.println("재고: ");
        int stock = sc.nextInt();
        return productJdbc.insert(name, price, stock);
    }

    public void readProduct() throws SQLException {
        List<ProductData> list = productJdbc.findAll();
        for (ProductData p : list) {
            System.out.println(p.getId() + "\t" + p.getName() +"\t" + p.getPrice() + "\t" + p.getStock());
            System.out.println("===============================");
        }

    }

    public int deleteProduct() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("삭제할 이름: ");
        String name = sc.nextLine();
        return productJdbc.deleteById(name);
    }

    public int updateProduct() throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.print("수정할 상품 id: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("새 상품명: ");
        String name = sc.nextLine();

        System.out.print("새 가격: ");
        int price = Integer.parseInt(sc.nextLine());

        System.out.print("새 재고: ");
        int stock = Integer.parseInt(sc.nextLine());

        ProductData p = new ProductData(id, name, price, stock);
        return productJdbc.update(p);
    }

    public void run() throws SQLException {
        int menu = -1;
        Scanner sc = new Scanner(System.in);
        while (menu != 0) {
            System.out.println("[1] 상품 추가\t [2] 상품 조회\t [3] 상품 삭제\t [4]상품 수정\t[0] 종료");
            switch (menu = sc.nextInt()) {
                case 1:
                    int result1 = insertProduct();
                    if (result1 == 1) System.out.println("상품추가 되었습니다.");
                    else System.out.println("상품추가에 실패했습니다. ㅠㅠ");
                    break;
                case 2:
                    readProduct();
                    break;
                case 3:
                    if (deleteProduct() == 1) System.out.println("상품삭제되었습니다.");
                    else System.out.println("삭제실패했습니다.");
                    break;
                case 4:
                    int result4  = updateProduct();
                    if (result4 > 0) {
                        System.out.println(result4 + "개의 상품이 수정되었습니다.");
                    } else {
                        System.out.println("해당 상품명이 존재하지 않습니다.");
                    }
                    break;
                case 0:
                    System.out.println("종료합니다.");
                    break;
                default:
                    System.out.println("다시 입력하시오");
            }
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) throws SQLException {
        UserInterface ui = new UserInterface();
        ui.run();
    }
}