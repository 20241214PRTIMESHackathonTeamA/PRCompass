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

import { useTitleRecommendStore } from '@/stores/titleRecommendStore'

// Piniaストアを取得
const titleRecommendStore = useTitleRecommendStore()

// 推奨タイトルを取得する
const fetchRecommendations = () => {
  titleRecommendStore.fetchRecommendations()
}

// コンポーネントがマウントされた時にデータを取得
fetchRecommendations()
</script>


<template>
  <main class="main">
    <RouterLink to="/">
      <Logo class="logo" />
    </RouterLink>
    <Introduction class="introduction" />
    <InputTitle class="input-title" @submit-input-arrow="handleInputTitleSubmit" />
    <OrLine class="or-line" />

    <h1>Recommended Titles</h1>

    <!-- ローディング中の表示 -->
    <div v-if="titleRecommendStore.loading">Loading...</div>

    <!-- エラーの表示 -->
    <div v-else-if="titleRecommendStore.error">Error: {{ titleRecommendStore.error }}</div>

    <!-- 推奨タイトルのリスト -->
    <ul v-else>
      <li v-for="(topic, index) in titleRecommendStore.recommendations" :key="index">
        {{ topic }}
      </li>
    </ul>

    <!-- 再取得ボタン -->
    <button @click="fetchRecommendations">Reload Recommendations</button>
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

