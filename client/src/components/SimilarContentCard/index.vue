<script setup lang="ts">
import noImgUrl from "@/assets/no-img.png"
import { computed } from "vue"

defineOptions({
  name: "SimilarContentCard"
})

interface Props {
  title?: string;
  thumbnailUrl?: string | undefined;
  postUrl?: string;
}

const props = defineProps<Props>()

// Computed thumbnail URL logic
const computedThumbnailUrl = computed(() => {
  return props.thumbnailUrl && props.thumbnailUrl.startsWith("https")
    ? props.thumbnailUrl
    : noImgUrl
})
</script>

<template>
  <div class="similar-content-card-wrapper">
    <a :href="props.postUrl" target="_blank">
      <div class="card-img-container">
        <img class="card-img" :src="computedThumbnailUrl" :alt="props.title" />
      </div>
    </a>
    <div class="card-title">
      {{ props.title }}
    </div>
  </div>
</template>


<style lang="css" scoped>
.similar-content-card-wrapper {
  width: 588px;
  display: flex;
  justify-content: flex-start; /* 左寄せ */
  align-items: flex-start;
  font-size: 12px;
  background-color: var(--color-white);
  padding: 5px;
}

.card-img-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.card-img {
  width: 165px;
  height: 92px;
  margin-right: 10px;
  object-fit: cover; /* 画像の縦横比を維持 */
  border: 1px solid #ccc; /* 画像枠 */
  border-radius: 4px; /* 少し丸みを持たせる */
}

.card-title {
  display: flex;
  align-items: flex-start; /* 左上揃え */
  text-align: left; /* テキストを左揃え */
  word-wrap: break-word; /* 長い単語を折り返し */
  flex-grow: 1; /* 残りの幅を使用 */
  font-size: 14px;
  color: #333;
  font-weight: bold;
}
</style>
