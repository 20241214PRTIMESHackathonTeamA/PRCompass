<script setup lang="ts">
import { ref } from 'vue'
import { RouterLink } from 'vue-router'

defineOptions({
  name: 'InputTitle',
})

const inputValue = ref('')

interface Emits {
  (event: 'submit-input-arrow', inputValue: string): void
}

const emits = defineEmits<Emits>()
const handleButtonSubmit = () => {
  emits('submit-input-arrow', inputValue.value)
}
</script>

<template>
  <div class="input-container">
    <input
      type="text"
      placeholder="どんなことがありましたか？"
      @keyup.enter="handleButtonSubmit()"
      v-model="inputValue"
    />
    <RouterLink to="/about" class="arrow-link">
      <button @click="handleButtonSubmit()" type="button" class="arrow-button">
        <img src="@/assets/icon_nav_right_arrow_.png" />
      </button>
    </RouterLink>
  </div>
</template>

<style lang="css" scoped>
.input-container {
  width: 682px;
  height: 73px;
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
  border: none;
  outline: none;
  font-size: 16px;
  color: var(--color-black);
  padding-left: 13px;
}

.input-container input::placeholder {
  color: var(--color-gray);
}

.arrow-link {
  display: flex;
  height: 100%;
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
