package com.techelevator.controller;

import com.techelevator.dao.ProductDao;
import com.techelevator.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping( path = "/products")
public class ProductController {

    private ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/{id}",  method = RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {
        Product product = productDao.getProductById(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return product;
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Product> search(@RequestParam(defaultValue = "") String sku,
                                @RequestParam(defaultValue = "") String name) {
        if (!name.equals("")) {
            return productDao.searchByName(name);
        }
        if (!sku.equals("")) {
            return productDao.searchBySKU(sku);
        }
        return productDao.getAll();
    }

}
