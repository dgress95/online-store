<template>
  <div>
      <h1>Product Details</h1>
    <div id="product-details">
      <ProductDetails />
    </div>
  </div>
</template>

<script>
import ProductDetails from '../components/ProductDetails.vue'

export default {
    components: {
        ProductDetails
    }
};
</script>

<style scoped>
h1 {
  margin-top: 80px;
  text-align: center;
}

#product-details {
  display: flex;
  justify-content: center;
}
</style>