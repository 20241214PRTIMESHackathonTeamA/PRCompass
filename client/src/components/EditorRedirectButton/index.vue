<script setup lang="ts">
import {defineProps} from 'vue';

defineOptions({
  name: 'EditorRedirectButton',
})

const props = defineProps<{
  buttonText: string;
  url: string;
}>();

const redirectToUrl = () => {
  window.open(props.url, '_blank');
};
</script>

<template>
  <button @click="redirectToUrl" class="redirect-button">
    {{ buttonText }}
  </button>
</template>

<style scoped>
.redirect-button {
  /*background-color: #007bff;*/
  background-color: var(--theme-color);
  color: white;
  border: none;
  padding: 15px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 4px;
  transition-duration: 0.4s;
}

.redirect-button:hover {
  background-color: #0056b3;
}
</style>
