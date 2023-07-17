package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao {

    /**
     * Get a product from the datastore with the specified id.
     * If the id is not found, return null.
     *
     * @param productId The id of the product to return.
     * @return The matching Product object, or null if the productId is not found.
     */
    Product getProductById(int productId);

    List<Product> getProductsForUser(int userId);

    public Product getProductByCartItemId(int cartItemId);

    /**
     * Get all products from the datastore ordered alphabetically by name.
     *
     * @return List of all Product objects.
     */
    List<Product> getAll();

    /**
     * Gets all products from the datastore that contain the search criteria in either the name or SKU.
     * @param searchTerm the string to search by
     * @return
     */
//    List<Product> search(String searchTerm);

    /**
     * Adds a new product to the datastore.
     *
     * @param newProduct the Product object to add.
     * @return The added Product object with its new id and any default values filled in.
     */
    Product create(Product newProduct);

    /**
     * Update a product in the datastore.
     *
     * @param modifiedProduct The Product object to update.
     */
    Product update(Product modifiedProduct);

    /**
     * Removes a product from the datastore.
     *
     * @param productId The id of the product to remove. If the id is not found, no error will occur.
     * @return count of bookmarks removed
     */
    void delete(int productId);

    List<Product> searchBySKU(String sku);

    List<Product> searchByName(String name);











}
