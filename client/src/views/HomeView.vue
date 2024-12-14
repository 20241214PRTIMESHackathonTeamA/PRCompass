<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import Logo from '@/components/Logo/index.vue'
import Introduction from '@/components/Introduction/index.vue'
import OrLine from '@/components/OrLine/index.vue'
import InputTitle from '@/components/InputTitle/index.vue'


const handleInputTitleSubmit = (value: string) => {
  //TODO: request post server
  console.log(value)
}
</script>

<template>
  <main class="main">
    <RouterLink to="/">
      <Logo class="logo"/>
    </RouterLink>
    <Introduction class="introduction"/>
    <InputTitle
      class="input-title"
      @submit-input-arrow="handleInputTitleSubmit"
    />
    <OrLine class="or-line"/>
  </main>
</template>

<style lang="css" scoped>
.main {
  display: flex;
  margin: 0 auto;
  text-align: center;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.logo {
  margin-bottom: 35px;
}
.introduction {
  margin-bottom: 80px;
}
.input-title {
  margin-bottom: 66px;
}
.or-line {
  margin-bottom: 66px;
}

</style>

