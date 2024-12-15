<script setup lang="ts">
import { RouterLink } from 'vue-router'
import Logo from '@/components/Logo/index.vue'
import Introduction from '@/components/Introduction/index.vue'
import OrLine from '@/components/OrLine/index.vue'
import InputTitle from '@/components/InputTitle/index.vue'
import RecommendTitle from '@/components/RecommendTitle/index.vue'
import LoadingIcon from '@/components/RoadingIcon/index.vue'

import router from '@/router'
import { useTitleRecommendStore } from '@/stores/titleRecommendStore'
import { useTitleStore } from '@/stores/titleStore'

const titleRecommendStore = useTitleRecommendStore()
// 推奨タイトルを取得する store
const fetchRecommendations = (reload: boolean) => {
  titleRecommendStore.fetchRecommendations(reload)
}

// Aboutページで使うためのgetterとsetter
const titleStore = useTitleStore()

const handleInputTitleSubmit = (value: string) => {
  titleStore.setTitleName(value)
  router.push('/about')
}
const handleRecommendSubmit = (topic: string) => {
  titleStore.setTitleName(topic)
  router.push('/about')
}

// コンポーネントがマウントされた時にデータを取得
fetchRecommendations(false)
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

    <div class="description">
      レコメンド by Notion
      <!-- 再取得ボタンを画像に変更 -->
      <img src="../assets/reload.png" alt="Reload" class="reload-icon" @click="fetchRecommendations(true)" />
    </div>

    <!-- ローディング中の表示 -->
    <div v-if="titleRecommendStore.loading">
      <LoadingIcon class="loading-icon" />
    </div>
    <!-- エラーの表示 -->
    <div v-else-if="titleRecommendStore.error">Error: {{ titleRecommendStore.error }}</div>
    <!-- 推奨タイトルのリスト -->
    <div v-else class="recommend-area">
      <div v-for="(topic, index) in titleRecommendStore.recommendations" :key="index">
        <RecommendTitle
          class="recommend-title"
          :topic-name="topic"
          @submit-recommend-button="handleRecommendSubmit(topic)"
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
  align-items: center; /* 画像とテキストを縦方向に中央揃え */
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

/* 画像のスタイルを追加 */
.reload-icon {
  width: 24px;
  height: 24px;
  margin-left: 10px;
  cursor: pointer;
}
</style>
