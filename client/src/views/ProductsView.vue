<template>
  <div class="home">
    <div id="heading-line">
      <h1>
        Products
        <loading-spinner id="spinner" v-bind:spin="isLoading" />
      </h1>
      <p v-if="!$store.state.token">Welcome! You may browse anonymously as much as you wish, <br> but you must <router-link v-bind:to="{ name: 'login' }">Login</router-link> to add items to your shopping cart.</p>
      <div>
        <product-list />
      </div>
    </div>
  </div>
</template>

<script>
import LoadingSpinner from "../components/LoadingSpinner.vue";
import ProductList from "../components/ProductList.vue";
export default {
  name: "ProductsView",
  components: {
    LoadingSpinner, ProductList
  },
  data() {
    return {
      isLoading: false,
    };
  },

  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
  },

  methods: {},

  created() {},
};
</script>

<style scoped>
#spinner {
  color: green;
}

#heading-line {
  text-align: center;
  padding-top: 40px;
}

h1 {
  font-size: 28px;
  color: royalblue;
  font-weight: 600;
  letter-spacing: -1px;
  margin-left: 40px;
}
</style>