<script setup lang="ts">
import { ref } from 'vue'
import { RouterLink } from 'vue-router'

defineOptions({
  name: 'RecommendTitle',
})

interface Props {
  topicName?: string
}

interface Emits {
  (event: 'submit-recommend-button'): void
}

const emits = defineEmits<Emits>()
const props = defineProps<Props>()

const handleButtonSubmit = () => {
  emits('submit-recommend-button')
}


</script>

<template>
  <div class="recommend-container">
    <div class="recommend-name">
      {{ props?.topicName }}
    </div>
    <RouterLink to="/about" class="arrow-link">
      <button
        @click="handleButtonSubmit()"
        type="button"
        class="arrow-button"
      >
        <img src="@/assets/icon_nav_right_arrow_.png" />
      </button>
    </RouterLink>
  </div>

</template>

<style lang="css" scoped>
.recommend-container {
  width: 682px;
  height: 73px;
  font-size: 13px;

  display: inline-flex;
  align-items: center;
  border: 1px solid #ccc;
  border-radius: 4px;
  overflow: hidden;
  background-color: #fff;
  justify-content: space-between;
}
.recommend-container .recommend-name {
  display: flex;
  align-items: center;

  height: 100%;
  border: none;
  outline: none;
  font-size: 16px;
  color: var(--color-black);
  padding-left: 13px;
}
.arrow-button {
  width: 45px;
  height: 100%;
  background: none;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}
.arrow-button:hover {
  background-color: var(--color-light-gray);
}

</style>
