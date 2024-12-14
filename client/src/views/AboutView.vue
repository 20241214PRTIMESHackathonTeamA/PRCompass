<script setup lang="ts">
import { ref } from 'vue'
import { useJudgeStore } from '@/stores/judgeStore'
import { useSimilarStore } from '@/stores/similarStore'
import { useTitleStore } from '@/stores/titleStore';
import JudgeResult from '@/components/JudgeResult/index.vue'
import JudgeInputTitle from '@/components/JudgeInputTitle/index.vue'


// Piniaストアを取得
const similarStore = useSimilarStore()
const titleStore = useTitleStore() // global stateから、homeで入力されたtitle取得

// Piniaストアを取得
const judgeStore = useJudgeStore()
const title = ref(titleStore.getTitleName)

interface isDoneStatusObjType {
  isTitleDone: boolean;
  isCheckDone: boolean;
  isSimilarDone: boolean;
}

const isDoneStatusObj = ref<isDoneStatusObjType>({
  isTitleDone: false,
  isCheckDone: false,
  isSimilarDone: false,
})

// 類似タイトルを検索
const handleJudgeClick = (inputValue: string) => {
  title.value = inputValue
  if (title.value.trim()) {
    judgeStore.judgeTitle(title.value)
    similarStore.fetchSimilarTitles(title.value)
  }
}
</script>

<template>
  <main class="main">
    <div>
      <!-- 入力フォーム -->
      <h2 class="section-name">テーマを決定</h2>
      <JudgeInputTitle
        :already-input="title"
        @judge-input-title="handleJudgeClick"
        class="judge-input-title"
      >
      </JudgeInputTitle>

      <!-- 判定結果の表示 -->
      <div v-if="judgeStore.error" class="error">Error: {{ judgeStore.error }}</div>

      <div class="judge-results-wrapper">
        <!-- 掲載チェックの見出し -->
        <h2 class="section-name">掲載チェック</h2>
        <div class="judge-results">
          <JudgeResult class="judge-result"
            :label="'ニュースバリュー'"
            :loading="judgeStore.loading"
            :result="judgeStore.result?.newsValue"
          />
          <JudgeResult class="judge-result"
            :label="'公序良俗'"
            :loading="judgeStore.loading"
            :result="judgeStore.result?.publicDecency"
          />
          <JudgeResult class="judge-result"
            :label="'法規制'"
            :loading="judgeStore.loading"
            :result="judgeStore.result?.legalCompliance"
          />
        </div>
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
.main {
  display: flex;
  margin: 0 auto;
  width: 650px;
  text-align: center;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.section-name {
  text-align: left;
  font-weight: bold;
  margin-top: 112px;
  margin-bottom: 30px; /* JudgeResultとの間隔を調整 */
}
.judge-input-title {
  display: flex;
  justify-content: flex-start;
}
.judge-results-wrapper .judge-results {
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
}

</style>
