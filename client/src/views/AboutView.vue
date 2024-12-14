<script setup lang="ts">
import { ref } from 'vue'
import { useJudgeStore } from '@/stores/judgeStore'
import { useSimilarStore } from '@/stores/similarStore'
import JudgeResult from '@/components/JudgeResult/index.vue'
import { useTitleStore } from '@/stores/titleStore';

// Piniaストアを取得
const similarStore = useSimilarStore()
const titleStore = useTitleStore()

// Piniaストアを取得
const judgeStore = useJudgeStore()
const title = ref(titleStore.getTitleName)

// タイトルを判定する関数
// 類似タイトルを検索
const handleSearch = () => {
  if (title.value.trim()) {
    judgeStore.judgeTitle(title.value)
    similarStore.fetchSimilarTitles(title.value)
  }
}
</script>

<template>
  <main class="main">
    <div>
      <h1>Judge Title</h1>

      <!-- 入力フォーム -->
      <form @submit.prevent="handleSearch">
        <input v-model="title" placeholder="Enter title to judge" required />
        <button type="submit" :disabled="judgeStore.loading">
          {{ judgeStore.loading ? 'Judging...' : 'Judge' }}
        </button>
      </form>

      <!-- 判定結果の表示 -->
      <div v-if="judgeStore.error" class="error">Error: {{ judgeStore.error }}</div>

      <div class="result">
        <!-- 掲載チェックの見出し -->
        <h2 class="title">掲載チェック</h2>
        <ul class="judge-results">
          <JudgeResult
            :label="'ニュースバリュー'"
            :loading="judgeStore.loading"
            :result="judgeStore.result?.newsValue"
          />
          <JudgeResult
            :label="'公序良俗'"
            :loading="judgeStore.loading"
            :result="judgeStore.result?.publicDecency"
          />
          <JudgeResult
            :label="'法規制'"
            :loading="judgeStore.loading"
            :result="judgeStore.result?.legalCompliance"
          />
        </ul>
      </div>
    </div>
    <div>
      <h1>Find Similar Titles</h1>

      <!-- エラーの表示 -->
      <div v-if="similarStore.error" class="error">Error: {{ similarStore.error }}</div>

      <!-- 類似タイトルのリスト -->
      <div v-else-if="similarStore.similarTitles.length">
        <h2>Similar Titles</h2>
        <ul>
          <li v-for="(title, index) in similarStore.similarTitles" :key="index">
            <h3>{{ title.title }}</h3>
            <p><strong>Corporation:</strong> {{ title.corporationName }}</p>
            <p><strong>Published:</strong> {{ title.publishdDatetime }}</p>
            <p><strong>Likes:</strong> {{ title.like_count }}</p>
            <a :href="title.postUrl" target="_blank">Read More</a>
          </li>
        </ul>
      </div>

      <!-- 検索結果がない場合のメッセージ -->
      <div v-else>No similar titles found.</div>
    </div>
  </main>
</template>

<style scoped>
/* 掲載チェックの見出し */
.title {
  text-align: left;
  font-weight: bold;
  margin-bottom: 30px; /* JudgeResultとの間隔を調整 */
  padding-left: 20px; /* 左端を揃えるためのパディング */
}

/* JudgeResultの親要素に同じパディングを適用 */
.judge-results {
  padding-left: 20px;
}

/* その他スタイル */
.main {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
