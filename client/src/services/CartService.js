import axios from 'axios';

export default {

    getCart() {
        return axios.get('/cart');
    },

    addProductToCart(productId) {
        return axios.post('/cart/items', productId);
    },

    removeItemFromCart(itemId) {
        return axios.delete(`/cart/items/${itemId}`);
    },

    clearCart() {
        return axios.delete('/cart');
    }
}