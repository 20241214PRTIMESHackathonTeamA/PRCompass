<script setup lang="ts">
import { ref } from 'vue'

defineOptions({
  name: 'JudgeInputTitle',
})

interface Emits {
  (event: 'judge-input-title', inputValue: string): void
}
interface Props {
  alreadyInput: string;
}

const emits = defineEmits<Emits>()
const props = defineProps<Props>()
const inputValue = ref(props.alreadyInput)

const handleButtonSubmit = () => {
  if (inputValue.value) {
    emits('judge-input-title', inputValue.value)
  }
}
</script>

<template>
  <div class="input-container">
    <input
      type="text"
      @keyup.enter="handleButtonSubmit()"
      v-model="inputValue"
    />
    <button @click="handleButtonSubmit()" type="button" class="judge-button">
      <img src="@/assets/check-icon-black.png" alt="search icon" width="30px" height="30px">
    </button>
  </div>
</template>

<style lang="css" scoped>
.input-container {
  width: 588px;
  height: 63px;
  font-size: 13px;

  display: inline-flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #ccc;
  border-radius: 4px;
  overflow: hidden;
  background-color: #fff;
}

.input-container input {
  height: 100%;
  width: 100%;
  border: none;
  outline: none;
  font-size: 16px;
  color: var(--color-black);
  padding-left: 13px;
}

.judge-button {
  width: 70px;
  height: 100%;
  background: none;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--color-white);
  font-size: 15px;
  border-left: 1px solid var(--color-light-gray);
}
.judge-button:hover {
  color: #fff;
  background-color: var(--theme-color);
}

</style>
