package com.techelevator.dao;

import com.techelevator.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

public class JdbcProductDaoTests extends BaseDaoTests{

    protected static final Product PRODUCT_1 = new Product(1,"SKU-001", "Product 1", "Product description 1.", new BigDecimal(14.99), "Product001.jpg");
    protected static final Product PRODUCT_2 = new Product(2, "SKU-002", "Product 2", "Product description 2.", new BigDecimal(21.99), "Product002.jpg");
    protected static final Product PRODUCT_3 = new Product(3, "SKU-003", "Product name 3", "Product description 3.",  new BigDecimal(3.59), "Product003.jpg");

    private JdbcProductDao dao;

    private Product testProduct;

    @Before
    public void setup() {
        dao = new JdbcProductDao(dataSource);
        testProduct = new Product(99, "SKU-099", "Product name 99", "Product description 99.",  new BigDecimal(13.59), "Product099.jpg");
    }

    @Test
    public void getAll_returns_list_of_all_products() {
        List<Product> products = dao.getAll();

        Assert.assertEquals("getAll failed to return all products", 7, products.size());
        assertProductsMatch("getAllProducts returned wrong or partial data", PRODUCT_1, products.get(0));
        assertProductsMatch("getAllProducts returned wrong or partial data", PRODUCT_2, products.get(1));
    }

    @Test
    public void getByProductId_returns_correct_product() {
        Product result = dao.getProductById(PRODUCT_1.getProductId());
        Assert.assertNotNull("getByProductId returned null", result);
        assertProductsMatch("getByProductId returned wrong or partial data", PRODUCT_1, result);

        result = dao.getProductById(PRODUCT_2.getProductId());
        assertProductsMatch("getByProductId returned wrong or partial data", PRODUCT_2, result);
    }

    @Test
    public void getByProductId_returns_null_when_id_not_found() {
        Product product = dao.getProductById(99);
        Assert.assertNull("getProductById failed to return null for id not in database", product);
    }

    @Test
    public void create_returns_product_with_id_and_expected_values() {
        Product createdProduct = dao.create(testProduct);

        Assert.assertNotNull("createProduct returned null", createdProduct);

        Integer newId = createdProduct.getProductId();
        Assert.assertTrue("create failed to return a product with an id", newId > 0);

        testProduct.setProductId(newId);
        assertProductsMatch("create returned product with wrong or partial data", testProduct, createdProduct);
    }

    @Test
    public void searchSKU_returns_expected_products() {
        List<Product> products = dao.searchBySKU("SKU");
        Assert.assertEquals("getAll failed to return all products", 7, products.size());
        assertProductsMatch("searchSKU returned wrong or partial data", PRODUCT_1, products.get(0));

        List<Product> products1 = dao.searchBySKU("002");
        assertProductsMatch("searchSKU returned wrong or partial data", PRODUCT_2, products1.get(0));
    }

    @Test
    public void delete_removes_product() {
        dao.delete(PRODUCT_1.getProductId());
        Product product = dao.getProductById(PRODUCT_1.getProductId());
        Assert.assertNull(product);

        List<Product> allProducts = dao.getAll();
        Assert.assertEquals("delete removed the wrong number of products", 6, allProducts.size());
    }

    private void assertProductsMatch(String message, Product expected, Product actual) {
        Assert.assertEquals(message, expected.getProductId(), actual.getProductId());
        Assert.assertEquals(message, expected.getProductSku(), actual.getProductSku());
        Assert.assertEquals(message, expected.getName(), actual.getName());
        Assert.assertEquals(message, expected.getDescription(), actual.getDescription());
        Assert.assertEquals(message, expected.getPrice(), actual.getPrice());
        Assert.assertEquals(message, expected.getImageName(), actual.getImageName());
    }
}
