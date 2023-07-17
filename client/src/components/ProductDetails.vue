<template>
<div>
    <article class="product-card">
    <div class="product-image"></div>
    <div class="card-info">
    <div class="sku">{{ product.productSku }}</div>
      <div class="product-name action" @click="viewProduct(product.productId)" :data-id="product.productId">{{ product.name }}
      </div>
      <div class="description">{{ product.description }}</div>
    </div>
    <div class="card-footer">
      <div class="price">{{ formatPrice(product.price) }}</div>
      <button class="cart-button">
      <div class="cart" v-if="$store.state.token" @click="addToCart(product.productId)">
        <font-awesome-icon class="cart-icon" icon="fa-solid fa-cart-plus" /></div></button>
    </div>
  </article>
  <div class="back">
      <button class="button-normal"><router-link :to="{ name: 'home' }">Return to Product List</router-link></button>
    </div>
</div>
</template>

<script>
import productService from '../services/ProductService';
import cartService from '../services/CartService';
import store from '../store/index';

export default {
    name: 'product-details',
    data() {
        return {
            product: {
                productId: null,
                name: '',
                productSku: null,
                description: '',
                imageName: '',
                price: null
            }
        };
    },
    created() {
        productService.get(this.$route.params.productId).then((response) => {
            this.product = response.data;
        });
    }, 
    methods: {
    formatPrice(price) {
      return new Intl.NumberFormat('en-US', { currency: 'USD', style: 'currency' }).format(price);
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

}
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
 padding-top: 20px;
 border-top: 1px solid #ddd;
}

.sku {
    font-size: 11px;
    font-style: italic;
}

.product-name {
    font-size: 20px;
    font-weight: 700;
    padding-bottom: 5px;
}

.description {
  padding-bottom: 10px;
}

.back {
    margin-top: 10px;
    text-align: center;
}

.button-normal {
  border: none;
  outline: none;
  background-color: royalblue;
  padding: 10px 20px;
  border-radius: 10px;
  color: #fff;
  font-size: 16px;
  transform: .3s ease;
  margin-top: 20px;
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

.price {
  font-weight: 500;
}

a {
    text-decoration: none;
    color: white;
}

button:hover {
  background-color: rgb(56, 90, 194);
}

.cart-button:hover {
  background-color: rgb(56, 90, 194);
}


</style>