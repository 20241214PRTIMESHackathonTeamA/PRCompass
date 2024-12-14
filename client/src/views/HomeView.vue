<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import Logo from '@/components/Logo/index.vue'
import Introduction from '@/components/Introduction/index.vue'
import OrLine from '@/components/OrLine/index.vue'
import InputTitle from '@/components/InputTitle/index.vue'
import RecommendTitle from '@/components/RecommendTitle/index.vue'
import LoadingIcon from '@/components/RoadingIcon/index.vue'

import { useTitleRecommendStore } from '@/stores/titleRecommendStore'
import router from '@/router'
// Piniaストアを取得
const titleRecommendStore = useTitleRecommendStore()
// 推奨タイトルを取得する
const fetchRecommendations = () => {
  titleRecommendStore.fetchRecommendations()
}
const handleInputTitleSubmit = (value: string) => {
  router.push('/about')
}
const handleRecommendSubmit = (value: string) => {
  router.push('/about')
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
    <div class="description">手動で入力</div>
    <InputTitle class="input-title" @submit-input-arrow="handleInputTitleSubmit" />
    <OrLine class="or-line" />

    <div class="description">レコメンド by Notion</div>
    <!-- 再取得ボタン -->
    <button @click="fetchRecommendations">
      Reload Recommendations
    </button>

    <!-- ローディング中の表示 -->
    <div v-if="titleRecommendStore.loading">
      <LoadingIcon class="loading-icon" />
    </div>
    <!-- エラーの表示 -->
    <div v-else-if="titleRecommendStore.error">
      Error: {{ titleRecommendStore.error }}
    </div>
    <!-- 推奨タイトルのリスト -->
    <div v-else class="recommend-area">
      <div v-for="(topic, index) in titleRecommendStore.recommendations" :key="index">
        <RecommendTitle
          class="recommend-title"
          :topic-name="topic"
          @submit-recommend-button="handleRecommendSubmit"
        />
      </div>
    </div>
  </main>
</template>

<style lang="css" scoped>
.main {
  display: flex;
  margin: 0 auto;
  width: 680px;
  text-align: center;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.description {
  width: 100%;
  display: flex;
  justify-content: flex-start;
  font-weight: bold;
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
.loading-icon {
  margin-top: 20px;
  margin-bottom: 20px;
}
.recommend-area {
  margin-bottom: 100px;
}
.recommend-title {
  margin-bottom: 40px;
}
</style>

