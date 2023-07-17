import axios from 'axios';

export default {

    get(id) {
        return axios.get(`/products/${id}`);
    },

    allProducts() {
        return axios.get('/products');
    },

    searchProducts(searchString) {
        return axios.get('/products/name?search='+searchString);
    }
}