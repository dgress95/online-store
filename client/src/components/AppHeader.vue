<template>
  <header>
       <!-- <img id="header-img" src="C:\Users\Student\workspace\diane-gress-student-code\java\module-3\week-9\final-project\client\public\web-images\header.png"> -->
       <div class="header-info">
        <h1>SSGeek Online Store</h1>
        <h3>A great little store</h3>
       </div>
  </header>
</template>

<script>
export default {

}
</script>

<style scoped>
header {
    display: flex;
    height: 200px;
    width: 100%;
    background: rgb(63,94,251);
    background: linear-gradient(273deg, rgba(63,94,251,1) 0%, rgba(252,70,107,1) 100%);
    position: fixed;
    align-items: center;
    justify-content: center;
    top: 0;
    z-index: 1;
}

h1 {
  color: white;
  margin-bottom: 10px;
}

h3 {
  text-align: center;
  margin: 0;
  color: whitesmoke;
}

#header-img {
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
</style>