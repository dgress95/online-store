<template>
  <nav>
    <ul>
            <li><router-link v-bind:to="{ name: 'home' }">Home</router-link></li>
            <li>
                <router-link v-bind:to="{ name: 'cart' }" v-if="$store.state.token">Cart</router-link>
            </li>
            <li class="message">{{ cartMessage }}</li>
            <li class="right">
            <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token">Logout</router-link>
            <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link>
            </li>
        </ul>
  </nav>
</template>

<script>
import { mapState } from 'vuex';

export default {
computed: {
    ...mapState(['cartMessage']),
  }
}
</script>

<style scoped>
a {
    text-decoration: none;
    color: white;
    font-weight: 700;
    letter-spacing: 1px;
}

nav {
    padding: 15px 35px 0 0;
    height: 40px;
    background-color: #3f5efb;
    position: fixed;
    width: 100%;
    z-index: 1;
}

.message {
    margin-left: auto;
    flex-grow: 1;
    text-align: center;
    color: whitesmoke;
}

nav ul {
    display: flex;
    list-style-type: none;
    text-transform: uppercase;
    justify-content: space-between;
    align-items: center;
    padding: 5;
    margin: 0px;
}

li {
    flex-grow: 0;
    margin-right: 10px;
    margin-left: 10px;
}

.right {
    margin-left: auto;
}

</style>