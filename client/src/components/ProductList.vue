<template>
<section>
    <div id="search-area">
        <div id="icons">
            <font-awesome-icon
                v-bind:class="{ 'view-icon': true, active: cardView }"
                v-on:click="cardView = true"
                icon="fa-solid fa-grip"
                title="View tiles"
            ></font-awesome-icon>
            <font-awesome-icon
                v-bind:class="{ 'view-icon': true, active: !cardView }"
                v-on:click="cardView = false"
                icon="fa-solid fa-table"
                title="View table"
            ></font-awesome-icon>
        </div>
        <input id="search-bar" type="text" placeholder="Search products" v-model="filter" @keydown.enter="search" />
    </div>
    <div id="product-cards" v-if="cardView">
        <ProductCard
        v-for="product in filteredProducts"
        :key="product.productId"
        :product="product"
      ></ProductCard>
    </div>
    <div id="product-table" v-else>
        <table id="table-product">
            <thead>
            <tr>
                <th>Sku</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th v-if="$store.state.token">Add to Cart</th>
            </tr>
            </thead>
            <tbody>
            <ProductTable
            v-for="product in filteredProducts"
            :key="product.productId"
            :product="product"
          ></ProductTable>
            </tbody>
        </table>
    </div>
</section>
</template>

<script>

import ProductCard from './ProductCardStyle.vue'
import ProductTable from './ProductTableStyle.vue'
import productService from '../services/ProductService'

export default {
    name: 'product-list',
    components: {
        ProductCard,
        ProductTable
    },
    created() {
        this.displayProducts();
    },
    data() {
    return {
        filter: '',
        products: [],
        cardView: true
    };
  },
  computed: {
      filteredProducts() {
          return this.products.filter(product => 
          product.name.toLowerCase().includes(this.filter.toLowerCase()));
      }
  },
  methods: {
      
    displayProducts() {
        productService.allProducts().then((response) => {
            this.products = response.data;
        });
    },
    
    search() {}
  },
};
</script>

<style scoped>

.view-icon {
  font-size: 1.2rem;
  margin-right: 7px;
  padding: 3px;
  color: #444;
  border-radius: 3px;
}

.view-icon.active {
  background-color: rgba(252,70,107);
}

.view-icon:not(.active) {
  font-size: 1.2rem;
  margin-right: 7px;
  cursor: pointer;
}

.view-icon:not(.active):hover {
  color:rgb(63,94,251);
  background-color: rgba(255, 255, 255, 0.7);
}

#product-cards {
    display: flex;
    flex-wrap: wrap;
    gap: 30px;
    justify-content: center;
    margin-bottom: 10px;
}

#product-table {
  display: flex;
  justify-content: center;
}

#search-area {
    display: flex;
    margin: 10px 40px;
}

#search-bar {
    margin-left: auto;
    margin-bottom: 10px;
    font-family: inherit;
    font-size: inherit;
    background-color: #f4f2f2;
    border: none;
    color: #646464;
    padding: 0.7rem 1rem;
    border-radius: 30px;
    width: 10em;
    transition: all ease-in-out .5s;
}

#search-bar:hover, .search__input:focus {
  box-shadow: 0 0 1em #00000013;
}

#search-bar:focus {
  outline: none;
  background-color: #f0eeee;
}

#search-bar::-webkit-input-placeholder {
  font-weight: 100;
  color: #ccc;
}


/* Table styles */
#table-product {
    table-layout: auto;
    margin: 25px 0;
    font-size: 0.9em;
    font-family: sans-serif;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    border-collapse: collapse;
    width: 100%;
}

#table-product thead, tr {
    background-color: #f3f3f3;
    color: #acacac;
    text-align: left;
    border-radius: 15px;
}

#table-product th,
#table-product td {
    padding: 15px;
}

#table-product tbody tr {
    border-bottom: 1px solid #dddddd;
}

#table-product tbody tr:nth-of-type(odd) {
    background-color: rgb(63,94,251);
    color: #f3f3f3;
}

#table-product tbody tr:last-of-type {
    border-bottom: 2px solid rgb(63,94,251);
}

#table-product th:first-child {
  border-radius: 10px 0 0 0;
}

#table-product th:last-child {
  border-radius: 0 10px 0 0;
  text-align: center;
}

#table-product th:not(:first-child):not(:last-child) {
  border-radius: 0;
}

</style>