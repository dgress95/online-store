<template>
  <article class="product-card">
    <div class="product-image"></div>
    <div class="card-info">
      <div class="product-name action tooltip" @click="viewProduct(product.productId)" :data-id="product.productId"><span class="tooltiptext">Click for more info!</span>{{ product.name }}</div>
    </div>
    <div class="card-footer">
      <div class="price">{{ formatPrice(product.price) }}</div>
      <button class="cart-button" v-if="$store.state.token"><div class="cart" @click="addToCart(product.productId)">
        <font-awesome-icon icon="fa-solid fa-cart-plus" /></div></button>
    </div>
  </article>
</template>

<script>
import cartService from '../services/CartService';
import store from '../store/index';

export default {
    name: 'product-card',
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
  }
};
</script>

<style scoped>
.product-card {
  width: 350px;
  height: 350px;
  padding: .8em;
  background: #f5f5f5;
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
}

.product-image {
  background: rgb(63,94,251);
  background: linear-gradient(355deg, rgba(63,94,251,1) 0%, rgba(252,70,107,1) 100%);
  height: 50%;
  width: 100%;
  border-radius: .5rem;
  transition: .3s ease;
}

.card-info {
 padding-top: 10%;
}

.card-footer {
 width: 100%;
 display: flex;
 justify-content: space-between;
 align-items: center;
 padding-top: 30px;
 border-top: 1px solid #ddd;
}

.sku {
    font-size: 11px;
    font-style: italic;
}

.price {
  font-weight: 500;
}

.product-name {
    font-size: 20px;
    font-weight: 700;
    padding-bottom: 15px;
}

.description {
  padding-bottom: 10px;
}

.tooltip {
  position: relative;
  display: inline-block;
  cursor: pointer;
}

.tooltiptext {
  visibility: hidden;
  width: 200px;
  background-color: rgb(63,94,251);
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 0;

  /* Position the tooltip */
  position: absolute;
  z-index: 1;
  top: 90%;
}

.tooltip:hover .tooltiptext {
  visibility: visible;
}

.cart-button {
  border: none;
  outline: none;
  background-color: royalblue;
  padding: 10px 20px;
  border-radius: 10px;
  color: #fff;
  font-size: 16px;
  transform: .3s ease;
}

.cart-button:hover {
  background-color: rgb(56, 90, 194);
}
</style>