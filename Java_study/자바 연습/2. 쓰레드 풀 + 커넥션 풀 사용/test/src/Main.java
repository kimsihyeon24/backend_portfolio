import concurrency.FixedThreadPollExecutor;
import concurrency.TaskExecutor;
import config.DataSourceProvider;
import dao.JdbcProductDao;
import dao.ProductDao;
import model.Product;
import service.ProductService;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Future;

class UserInterface {
    private final Scanner sc = new Scanner(System.in);
    private final ProductService productService;
    private final TaskExecutor executor;

    public UserInterface(ProductService productService, TaskExecutor executor) {
        this.productService = productService;
        this.executor = executor;
    }

    public void createProduct() {
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter product price: ");
        int price = Integer.parseInt(sc.nextLine());
        System.out.print("Enter product quantity: ");
        int quantity = Integer.parseInt(sc.nextLine());
        executor.submit(() -> {
            productService.createProductSql(name, price, quantity);
        });
        System.out.println("요청 접수 완료");
    }

    public void printAll() {
        Future<List<Product>> future = executor.submit(() -> productService.printAllSql());
        try {
            List<Product> list = future.get();
            for (Product p : list) {
                System.out.println("상품명: " + p.getName() + " 가격: " + p.getPrice() + " 수량: " + p.getQuantity());
                System.out.println("=============================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("이건 출력 과정을 쓰레드 처리 안함. 번호입력이 순서대로 안나와서");
    }

    public void updateProduct() {
        System.out.print("변경할 id: ");
        int productId = Integer.parseInt(sc.nextLine());
        System.out.print("새로운 이름: ");
        String name = sc.nextLine();
        System.out.print("새로운 가격: ");
        int price = Integer.parseInt(sc.nextLine());
        System.out.print("새로운 수량");
        int quantity = Integer.parseInt(sc.nextLine());
        executor.submit(() -> productService.updateProductSql(productId, name, price, quantity));


    }

    public void deleteProduct() {
        System.out.print("삭제할 id: ");
        int productName = Integer.parseInt(sc.nextLine());
        executor.submit(() -> productService.deleteProductSql(productName));
    }

    public void deleteProductAll() {
        executor.submit(productService::deleteProductAllSql);
    }

    public void run() {
        int menu = -1;
        System.out.println("[1] 상품생성\t [2] 전체조회\t [3] 상품갱신 \t [4] 특정상품 삭제 \t[5] 전체 삭제");
        while (menu != 0) {
            System.out.println("번호 입력: ");
            switch (menu = Integer.parseInt(sc.nextLine())) {
                case 1:
                    createProduct();
                    break;
                case 2:
                    printAll();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    deleteProductAll();
                    break;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        DataSourceProvider provider = new DataSourceProvider();
        ProductDao dao = new JdbcProductDao(provider);
        ProductService service = new ProductService(dao);
        TaskExecutor executor = new FixedThreadPollExecutor(5);
        UserInterface ui = new UserInterface(service, executor);
        ui.run();

        executor.shutdown();
    }
}