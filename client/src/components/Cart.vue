<template>
  <div id="cart-section">
      <div id="cart-items">
  <h2 id="cart-items-header">Cart Items</h2>
  <button @click="clearCart()">Clear All Cart Items</button>
  <table class="cart-table">
    <thead>
      <tr>
        <th>Quantity</th>
        <th>Name</th>
        <th>Price</th>
        <th>Extended Price</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="item in cart.cartItems" :key="item.cartItemId">
        <td>{{ item.quantity }}</td>
        <td>{{ item.product.name }}</td>
        <td>${{ item.product.price }}</td>
        <td>${{ calculateExtendedPrice(item) }}</td>
        <button @click="removeFromCart(item.cartItemId)">X</button>
      </tr>
    </tbody>
  </table>
  </div>

    <div id="cart-summary">
  <h2 id="cart-summary-header">Cart Summary</h2>
  <table class="cart-table">
    <tr>
      <td>Subtotal:</td>
      <td>${{ cart.subtotal }}</td>
    </tr>
    <tr>
      <td class="cart-tax">Tax:</td>
      <td class="cart-tax">${{ calculateTax() }}</td>
    </tr>
    <tr>
      <td class="bold-cart">Total:</td>
      <td class="bold-cart">${{ cart.totalWithTax }}</td>
    </tr>
  </table>
</div>
</div>
</template>

<script>
import cartService from '../services/CartService'
import store from '../store/index';

export default {
  data() {
    return {
      cart: {
        cartId: 0,
        userId: 0,
        subtotal: 0,
        totalWithTax: 0,
        cartItems: [
          {
            cartItemId: 0,
            userId: 0,
            quantity: 0,
            productId: 0,
            product: {
              productId: 0,
              productSku: '',
              name: '',
              description: '',
              price: 0,
              imageName: '',
            },
          },
        ],
      },
    };
  },
  created() {
      this.fetchCart();
    },
    methods: {
        fetchCart() {
            cartService.getCart().then(response => {
          this.cart = response.data;
        })
        .catch(error => {
          console.error('Failed to retrieve cart:', error);
        });
        },
        
        calculateExtendedPrice(item) {
        return (item.quantity * item.product.price).toFixed(2);
        },
        
        calculateTax() {
        return (this.cart.totalWithTax - this.cart.subtotal).toFixed(2);
        },
        
        removeFromCart(itemId) {
            cartService.removeItemFromCart(itemId)
            .then(() => {
                store.commit('SET_CART_MESSAGE', 'Item removed from cart');
                store.dispatch('CLEAR_CART_MESSAGE');
                this.fetchCart();
            })
            .catch(error => {
                console.error('Failed to remove product from cart:', error);
                });
        },

        clearCart() {
            cartService.clearCart()
            .then(() => {
                store.commit('SET_CART_MESSAGE', 'All items removed from cart');
                store.dispatch('CLEAR_CART_MESSAGE');
                this.fetchCart();
            })
            .catch(error => {
                console.error('Failed to remove product from cart:', error);
                });
        }
    },
};
</script>

<style scoped>

#cart-section {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: center;
}

#cart-summary {
    padding: 20px;
    margin-top: 180px;
    background-color: #f3f3f3;
    width: 250px;
    height: 250px;
    border-radius: 10px;
    text-align: center;
}

#cart-items-header {
    font-size: 28px;
    color: rgb(63,94,251);
    font-weight: 600;
    letter-spacing: -1px;
    margin-top: 60px;
}

#cart-summary-header {
    font-size: 28px;
    color: rgb(63,94,251);
    font-weight: 600;
    letter-spacing: -1px;
}

.cart-table {
    table-layout: auto;
    margin: 25px 0;
    font-size: 0.9em;
    font-family: sans-serif;
    border-collapse: collapse;
}

.cart-table thead, tr {
    color: rgb(63,94,251);
    text-align: left;
    border-radius: 15px;
}

.cart-table th,
.cart-table td {
    padding: 12px 15px;
}

.cart-table tbody tr {
    border-bottom: 1px solid #dddddd;
}

.cart-table tbody tr:nth-of-type(even) {
    background-color: #f3f3f3;
}

.cart-table tbody tr:last-of-type {
    border-bottom: 2px solid rgb(63,94,251);
}

button {
  border: none;
  outline: none;
  background-color: royalblue;
  padding: 10px 20px;
  border-radius: 10px;
  color: #fff;
  font-size: 16px;
  transform: .3s ease;
}

button:hover {
  background-color: rgb(56, 90, 194);
}

.bold-cart {
    font-weight: 700;
}

.cart-tax {
    font-style: italic;
}

@media (max-width: 910px) {
    #cart-summary {
    padding: 20px;
    background-color: #f3f3f3;
    width: 250px;
    margin-top: 0px;
    border-radius: 10px;
    text-align: center;
}
}

</style>