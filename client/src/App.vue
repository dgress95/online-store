<template>
  <div id="app">
    <main-nav id="main-nav" />
    <app-header id="app-header" />
    <main id="main-content">
      <router-view />
    </main>
    <app-footer id="app-footer"/>
  </div>
</template>

<script>
import AppFooter from "./components/AppFooter.vue";
import AppHeader from "./components/AppHeader.vue";
import MainNav from "./components/MainNav.vue";
export default {
  components: {AppHeader, AppFooter, MainNav}
}
</script>


<style scoped>

</style>