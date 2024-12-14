<script setup lang="ts">
import { ref } from 'vue'
import { useJudgeStore } from '@/stores/judgeStore'
import { useSimilarStore } from '@/stores/similarStore';

// Piniaストアを取得
const similarStore = useSimilarStore();

// Piniaストアを取得
const judgeStore = useJudgeStore()
const title = ref('')

// タイトルを判定する関数
// 類似タイトルを検索
const handleSearch = () => {
  if (title.value.trim()) {
    judgeStore.judgeTitle(title.value)
    similarStore.fetchSimilarTitles(title.value);
  }
};
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

      <div v-else-if="judgeStore.result" class="result">
        <h2>Judgment Results</h2>
        <ul>
          <li>
            News Value: {{ judgeStore.result.newsValue?.isValid ? 'Valid' : 'Invalid' }} (Reason:
            {{ judgeStore.result.newsValue?.reason }})
          </li>
          <li>
            Public Decency:
            {{ judgeStore.result.publicDecency?.isValid ? 'Valid' : 'Invalid' }} (Reason:
            {{ judgeStore.result.publicDecency?.reason }})
          </li>
          <li>
            Legal Compliance:
            {{ judgeStore.result.legalCompliance?.isValid ? 'Valid' : 'Invalid' }} (Reason:
            {{ judgeStore.result.legalCompliance?.reason }})
          </li>
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

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
