<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="wrapper">
      <RouterView />
    </div>
  </header>
</template>
<style lang="css" scoped>
.wrapper {
  margin: 0 auto;
  text-align: center;
}
</style>
