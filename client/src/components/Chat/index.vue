<template>
  <div class="chat-container">
    <!-- チャットボックス -->
    <div class="chat-box">
      <div
        v-for="(message, index) in messages"
        :key="index"
        :class="['message', message.role]"
        v-bind:style="getMessageStyle(message, index)"
      >
        <!-- <span v-if="message.role === 'assistant'" class="bot-avatar">🤖</span> -->
        <p>{{ message.content }}</p>
      </div>
      <!-- ローディングインジケーター -->
      <div v-if="isLoading" class="loading-indicator">
        <div class="dot dot1"></div>
        <div class="dot dot2"></div>
        <div class="dot dot3"></div>
      </div>
    </div>

    <!-- ユーザー入力フォーム -->
    <div class="input-container">
      <input
        v-model="userInput"
        @keyup.enter="sendMessage"
        placeholder="回答を入力してください。"
        :disabled="isLoading"
      />
      <button @click="sendMessage" :disabled="isLoading">送る</button>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import { useChatStore } from '@/stores/chatStore'
import { useTitleStore } from '@/stores/titleStore'

export default defineComponent({
  setup() {
    const chatStore = useChatStore()
    chatStore.clearMessage();

    // Piniaストアを取得
    const titleStore = useTitleStore() // global stateから、homeで入力されたtitle取得
    const title = ref(titleStore.getTitleName)

    const userInput = ref(
      title.value +
        'というテーマでPRTIMESを用いてプレスリリースを作成したいです。たたき台を作っていただけないでしょうか。たたき台の作成に不足している情報があれば質問してください。'
    )

    const sendMessage = async () => {
      if (userInput.value.trim()) {
        await chatStore.sendMessage(userInput.value)
        userInput.value = '' // 入力フィールドをリセット
      }
    }

    // メッセージのスタイルを動的に変更（ボットのメッセージが上に来るように）
    const getMessageStyle = (message: { role: string; content: string }, index: number) => {
      return {
        animation:
          message.role === 'assistant' && index === chatStore.messages.length - 1
            ? 'fadeIn 0.5s ease-out'
            : '',
      }
    }

    return {
      userInput,
      messages: chatStore.messages,
      isLoading: chatStore.isLoading,
      sendMessage,
      getMessageStyle,
    }
  },
})
</script>

<style scoped>
.chat-container {
  font-family: Arial, sans-serif;
  padding: 20px;
  width: 550px;
  margin: 0 auto;
}

h1 {
  text-align: center;
  color: #4caf50;
}

.chat-box {
  max-height: 400px;
  overflow-y: auto;
  margin-bottom: 20px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 8px;
}

.message {
  display: flex;
  align-items: flex-start;
  margin-bottom: 15px;
  padding: 8px;
  border-radius: 15px;
  position: relative;
}

.message.user {
  background-color: #e0f7fa;
  align-self: flex-end;
  text-align: right;
}

.message.assistant {
  background-color: #f1f1f1;
  align-self: flex-start;
  text-align: left;
}

.message p {
  margin: 0;
  padding: 5px;
  word-wrap: break-word;
  max-width: 80%;
}

.bot-avatar {
  position: absolute;
  left: -30px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 30px;
}

.input-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 10px;
}

.input-container input {
  width: 80%;
  padding: 10px;
  margin-right: 10px;
  border-radius: 25px;
  border: 1px solid #ddd;
  font-size: 16px;
}

.input-container button {
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 25px;
  cursor: pointer;
}

.input-container button:disabled {
  background-color: #ddd;
  cursor: not-allowed;
}

.loading-indicator {
  display: flex;
  justify-content: center;
  gap: 5px;
}

.dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: #4caf50;
  animation: bounce 1.4s infinite ease-in-out;
}

.dot1 {
  animation-delay: 0s;
}

.dot2 {
  animation-delay: 0.2s;
}

.dot3 {
  animation-delay: 0.4s;
}

@keyframes bounce {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
</style>

