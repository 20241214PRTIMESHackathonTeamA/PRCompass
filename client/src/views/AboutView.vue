<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useJudgeStore } from '@/stores/judgeStore'
import { useSimilarStore } from '@/stores/similarStore'
import { useTitleStore } from '@/stores/titleStore';
import JudgeResult from '@/components/JudgeResult/index.vue'

import JudgeInputTitle from '@/components/JudgeInputTitle/index.vue'
import SimilarContentCard from '@/components/SimilarContentCard/index.vue'
import EditorRedirectButton from '@/components/EditorRedirectButton/index.vue'
import loadingImg from '@/assets/loading.svg'

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

const resetStatus = () => {
  stepStatus.value.isTitleDecided = false
  stepStatus.value.isJudgeCompleted = false
  stepStatus.value.isSimilarTitlesFound = false
}

// 類似タイトルを検索
const handleJudgeClick = (inputValue?: string) => {
  const currentTitle = inputValue || title.value;
  if (!currentTitle.trim()) {
    return; // タイトルが空なら処理しない
  }
  resetStatus();

  title.value = currentTitle;
  stepStatus.value.isTitleDecided = true;

  judgeStore.judgeTitle(currentTitle).then(() => {
    stepStatus.value.isJudgeCompleted = true;
  });

  similarStore.fetchSimilarTitles(currentTitle).then(() => {
    stepStatus.value.isSimilarTitlesFound = similarStore.similarTitles.length > 0;
  });
};

onMounted(() => {
  handleJudgeClick();
});

</script>

<template>
  <main class="main">
    <div class="section-margin"></div>
    <div class="back-link">
      <RouterLink class="back-router-anchor" to="/">
        <img src="@/assets/left-arrow.png" class="left-arrow-img" alt="back page" width="12px" height="12px"/>
         Back
      </RouterLink>
    </div>
    <!-- 入力フォーム -->
    <div class="step-line first" :class="{ completed: stepStatus.isTitleDecided }">
      <!--
        <div class="step circle" :class="{ completed: stepStatus.isTitleDecided }"></div>
      -->
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
    </div>


    <!-- 掲載チェック -->
    <div class="judge-results-wrapper">
      <div class="section-margin"></div>
      <div class="step-line second" :class="{ completed: stepStatus.isJudgeCompleted }">
        <!--
        <div class="step circle" :class="{ completed: stepStatus.isJudgeCompleted }"></div>
        -->
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

    <div class="similar-wrapper">
      <div class="section-margin "></div>
      <div class="step-line third" :class="{ completed: stepStatus.isSimilarTitlesFound }">
        <!--
        <div class="step circle" :class="{ completed: stepStatus.isSimilarTitlesFound }"></div>
        -->
        <h2 class="section-name">似ている記事</h2>
        <!-- エラーの表示 -->
        <div sclass="similar-show-area">
          <div v-if="similarStore.error" class="error">Error: {{ similarStore.error }}</div>
          <!-- 類似タイトルのリスト -->
          <div v-else-if="similarStore.similarTitles.length">
            <div v-for="(title, index) in similarStore.similarTitles" :key="index">
              <SimilarContentCard
                class="similar-content-card"
                :title="title.title"
                :thumbnail-url="title.thumbnailUrl"
                :post-url="title.postUrl"
              />
            </div>
          </div>
          <!-- 検索結果がない場合のメッセージ -->
          <img v-else-if="similarStore.loading"
            :src="loadingImg"
            alt="loading"/>
          <div v-else>No similar titles found.</div>
      </div>
    </div>
      </div>
      <EditorRedirectButton
        class="editor-link-button"
        buttonText="記事を書いてみる！" url="https://preditor.prtimes.com/"/>
  </main>
</template>

<style scoped>
.editor-link-button {
  margin-top: 80px;
}
.back-link {
  width: 100%;
  margin-bottom: 30px;
  display: flex;
  justify-content: flex-start;
  text-align: left;
  margin-left: -10px;
}
.back-router-anchor {
  text-decoration: none;
  color: var(--theme-color);
}
.main {
  display: flex;
  margin-inline: auto;
  margin-bottom: 100px;
  width: 650px;
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
.step-line {
  margin-left: -45px;
  padding-left: 20px;
  border-left: solid 3px var(--color-gray);
}
.step-line.completed {
  border-left: solid 3px var(--color-light-green);
}
.section-margin {
  margin-top: 100px;
}
.section-name {
  text-align: left;
  font-weight: bold;
  max-width: 588px;
  margin-bottom: 30px;
}
.judge-input-title {
  display: flex;
  justify-content: flex-start;
  margin-bottom: 30px; /* JudgeResultとの間隔を調整 */
}
.judge-results-wrapper .judge-results {
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
}
.judge-results-wrapper {
  margin-bottom: 57px;
}
.circle.completed {
  background-color: var(--color-light-green);
}
.similar-wrapper {
  display: flex;
  justify-content: flex-start;
  width: 565px;
}
.similar-content-card {
  margin-bottom: 10px;
}
.error {
  color: red;
}

</style>

