<template>
    <tr id="product-info">
      <td class="sku">{{ product.productSku }}</td>
      <td class="product-name" @click="viewProduct(product.productId)" :data-id="product.productId">{{ product.name }}</td>
      <td class="description">{{ product.description }}</td>
      <td class="price">{{ formatPrice(product.price) }}</td>
      <td class="cart" v-if="$store.state.token" @click="addToCart(product.productId)">
        <font-awesome-icon
                icon="fa-solid fa-cart-plus"
                title="Add to Cart"
            ></font-awesome-icon>
        </td>
      <div v-else></div>
    </tr>
</template>

<script>
import cartService from '../services/CartService';
import store from '../store/index';

export default {
    name: 'product-table',
    props: {
    product: {
      type: Object,
      required: true
    }
  },
  methods: {
    formatPrice(price) {
      return new Intl.NumberFormat('en-US', { currency: 'USD', style: 'currency' }).format(price);
    },

    viewProduct(product) {
        this.$router.push({ name: 'Product', params: { productId: product } })
    },

    addToCart(productId) {
      const quantity = 1;
      const product = {
        productId,
        quantity
      };
      cartService.addProductToCart(product)
      .then(() => {
          store.commit('SET_CART_MESSAGE', 'Item added to cart');
          store.dispatch('CLEAR_CART_MESSAGE');
      })
      .catch(error => {
          console.error('Failed to add product to cart:', error);
        });
    },

    fillHeart() {
      // Handle favorite icon click event
    }
  }
};
</script>

<style scoped>

#product-info {
    padding: 0.5rem;
    table-layout: fixed;
}

.sku {
    font-size: 11px;
    font-style: italic;
    padding-left: 10px;
}
.product-name {
    font-size: 18px;
    font-weight: 700;
    padding-bottom: 5px;
}

.product-name:hover {
  cursor: pointer;
}

.cart {
    text-align: center;
}
</style>