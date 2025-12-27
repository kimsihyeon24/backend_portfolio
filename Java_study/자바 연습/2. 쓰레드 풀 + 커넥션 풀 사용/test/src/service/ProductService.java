package service;

import dao.ProductDao;
import model.Product;

import java.util.List;

public class ProductService {

    private final ProductDao productDao;
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void createProductSql(String name, int price, int quantity) {
        Product product = new Product(name, price, quantity);
        productDao.insert(product);
    }

    public List<Product> printAllSql() {
        return productDao.printAll();
    }

    public void updateProductSql(int id, String name, int price, int quantity) {
        productDao.update(id, name, price, quantity);
    }

    public void deleteProductSql(int id) {
        productDao.deleteOne(id);
    }

    public void deleteProductAllSql() {
        productDao.deleteAll();
    }

}
