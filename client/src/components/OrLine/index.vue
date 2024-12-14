<script setup lang="ts">
defineOptions({
  name: 'OrLine',
})
</script>

<template>
  <div class="or-container">
    <div class="or-line"></div>
    <span class="or-circle">OR</span>
  </div>
</template>

<style scoped lang="css">
.or-container {
  position: relative;
  text-align: center;
  width: 680px;
}

.or-line {
  border-top: 1px solid var(--color-gray);
  height: 1px;
}

.or-circle {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: var(--color-gray);
  border-radius: 50%;
  padding: 0.75em 0.75em;
  font-size: 12px;
  font-weight: bold;
  color: var(--color-black);
}
</style>
