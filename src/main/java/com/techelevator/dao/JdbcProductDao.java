package com.techelevator.dao;

import com.techelevator.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcProductDao(DataSource datasource) {
        jdbcTemplate = new JdbcTemplate(datasource);
    }
    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, product_sku, name, description, price, image_name " +
                "FROM product;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            products.add(mapRowToProduct(results));
        }
        return products;
    }

    @Override
    public List<Product> searchBySKU(String sku) {
        List<Product> matchSku = new ArrayList<>();
        String sql = "SELECT product_id, product_sku, name, description, price, image_name " +
                "FROM product " +
                "WHERE product_sku ILIKE ?;";
        String searchString = "%" + sku + "%";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, searchString);
        while (results.next()) {
            Product product = mapRowToProduct(results);
            matchSku.add(product);
        }
        return matchSku;
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> matchName = new ArrayList<>();
        String sql = "SELECT product_id, product_sku, name, description, price, image_name " +
                "FROM product " +
                "WHERE name ILIKE ?;";
        String searchString = "%" + name + "%";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, searchString);
        while (results.next()) {
            Product product = mapRowToProduct(results);
            matchName.add(product);
        }
        return matchName;
    }

    @Override
    public Product getProductByCartItemId(int cartItemId) {
        Product cartItemProduct = new Product();
        String sql = "SELECT p.product_id, p.product_sku, p.name, p.description, p.price, p.image_name " +
                "FROM product as p " +
                "JOIN cart_item ON p.product_id = cart_item.product_id " +
                "WHERE cart_item.cart_item_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, cartItemId);
        if (result.next()) {
            cartItemProduct = mapRowToProduct(result);
        }
        return cartItemProduct;
    }

    @Override
    public List<Product> getProductsForUser(int userId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_id, p.product_sku, p.name, p.description, p.price, p.image_name " +
                "FROM product as p " +
                "JOIN cart_item ON p.product_id = cart_item.product_id " +
                "JOIN users ON cart_item.user_id = users.user_id " +
                "WHERE users.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()) {
            products.add(mapRowToProduct(results));
        }
        return products;
    }

    @Override
    public Product getProductById(int productId) {
        Product product = null;
        String sql = "SELECT product_id, product_sku, name, description, price, image_name " +
                "FROM product " +
                "WHERE product_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, productId);
        if (result.next()) {
            product = mapRowToProduct(result);
            return product;
        } else {
            return null;
        }
    }

    @Override
    public Product create(Product newProduct) {
        String sql = "INSERT into product (product_sku, name, description, price, image_name) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "RETURNING product_id;";
        int productID = jdbcTemplate.queryForObject(sql, Integer.class, newProduct.getProductSku(), newProduct.getName(),
                newProduct.getDescription(), newProduct.getPrice(), newProduct.getImageName());
        newProduct.setProductId(productID);
        return newProduct;
    }

    @Override
    public void delete(int productId) {
        String deleteProductSql1 = "delete from cart_item " +
                "where product_id = ?;";
        jdbcTemplate.update(deleteProductSql1, productId);
        String deleteProductSql = "delete from product " +
                "where product_id = ?;";
        jdbcTemplate.update(deleteProductSql, productId);
    }

    @Override
    public Product update(Product modifiedProduct) {
        String sql = "UPDATE product SET product_sku=?, name=?, description=?, price=?, image_name=?\n" +
                "WHERE product_id=?;";
        jdbcTemplate.update(sql, modifiedProduct.getProductSku(), modifiedProduct.getName(), modifiedProduct.getDescription(),
                modifiedProduct.getPrice(), modifiedProduct.getImageName(), modifiedProduct.getProductId());
        return getProductById(modifiedProduct.getProductId());
    }

    /*
     * Helper method to convert a SqlRowSet into a Product object.
     */
    private Product mapRowToProduct(SqlRowSet rowSet) {
        Product product = new Product();
        product.setProductId(rowSet.getInt("product_id"));
        product.setProductSku(rowSet.getString("product_sku"));
        product.setName(rowSet.getString("name"));
        if (rowSet.getString("description") != null) {
            product.setDescription(rowSet.getString("description"));
        }
        product.setPrice(rowSet.getBigDecimal("price"));
        if (rowSet.getString("image_name") != null) {
            product.setImageName(rowSet.getString("image_name"));
        }
        return product;
    }
}
