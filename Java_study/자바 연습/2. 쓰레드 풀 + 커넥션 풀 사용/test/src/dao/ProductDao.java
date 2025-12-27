package dao;

import model.Product;

import java.util.List;

public interface ProductDao {
    void insert(Product product);
    List<Product> printAll();
    void update(int id, String name, int price, int quantity);
    void deleteOne(int id);
    void deleteAll();
}
