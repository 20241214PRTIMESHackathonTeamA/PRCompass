<script setup lang="ts">
import { ref } from 'vue'
import { useJudgeStore } from '@/stores/judgeStore'
import { useSimilarStore } from '@/stores/similarStore'
import { useTitleStore } from '@/stores/titleStore';
import JudgeResult from '@/components/JudgeResult/index.vue'
import JudgeInputTitle from '@/components/JudgeInputTitle/index.vue'

// 進捗の丸と線のステータスを管理
interface StepStatusType {
  isTitleDecided: boolean,
  isJudgeCompleted: boolean,
  isSimilarTitlesFound: boolean,
}
const stepStatus = ref<StepStatusType>({
  isTitleDecided: false,
  isJudgeCompleted: false,
  isSimilarTitlesFound: false,
})

// Piniaストアを取得
const similarStore = useSimilarStore()
const titleStore = useTitleStore() // global stateから、homeで入力されたtitle取得

// Piniaストアを取得
const judgeStore = useJudgeStore()
const title = ref(titleStore.getTitleName)

// 類似タイトルを検索
const handleJudgeClick = (inputValue: string) => {
  title.value = inputValue
  if (title.value.trim()) {
    //update green circle status
    stepStatus.value.isTitleDecided = true

    judgeStore.judgeTitle(title.value).then(() => {
      stepStatus.value.isJudgeCompleted = true
    })

    similarStore.fetchSimilarTitles(title.value).then(() => {
      stepStatus.value.isSimilarTitlesFound = similarStore.similarTitles.length > 0
    })
  }
}

</script>

<template>
  <main class="main">
    <div class="steps-wrapper">
      <div class="section-margin"></div>
      <!-- 入力フォーム -->
      <div class="step circle" :class="{ completed: stepStatus.isTitleDecided }"></div>
      <h2 class="section-name">テーマを決定</h2>
      <JudgeInputTitle
        :already-input="title"
        @judge-input-title="handleJudgeClick"
        class="judge-input-title"
      >
      </JudgeInputTitle>
      <div v-if="judgeStore.error" class="error">
        Error: {{ judgeStore.error }}
      </div>


      <!-- 掲載チェック -->
      <div class="judge-results-wrapper">
        <div class="section-margin"></div>
        <div class="step circle" :class="{ completed: stepStatus.isJudgeCompleted }"></div>
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

      <div>
        <div class="section-margin"></div>
        <div class="step circle" :class="{ completed: stepStatus.isJudgeCompleted }"></div>
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
/* 進捗を表すstepsの位置を調整 */
.step {
  position: relative;
  left: -55px;
  top: 27px;
}
.circle {
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background-color: var(--color-gray);
  margin-right: 20px;
  flex-shrink: 0;
  transition: background-color 0.3s;
}
.section-margin {
  margin-top: 112px;
}
.section-name {
  text-align: left;
  font-weight: bold;
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
.judge-results-wrapper {
  margin-bottom: 57px;
}
.completed {
  background-color: var(--color-light-green);
}

</style>

