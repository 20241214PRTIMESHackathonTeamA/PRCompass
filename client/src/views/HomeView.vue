<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import Logo from '@/components/Logo/index.vue'
import Introduction from '@/components/Introduction/index.vue'
import OrLine from '@/components/OrLine/index.vue'
</script>

<template>
  <main class="main">
    <RouterLink to="/">
      <Logo class="logo"/>
    </RouterLink>
    <Introduction class="introduction"/>
    <OrLine class="or-line"/>
  </main>
</template>

<style lang="css" scoped>
.main {
  display: flex;
  margin: 0 auto;
  text-align: center;
  flex-direction: column; /* Stack items vertically */
  align-items: center;    /* Center horizontally */
  justify-content: center;/* Center vertically */
}
.logo {
  margin-bottom: 35px;
}
.or-line {
  margin: 66px;
}

</style>

