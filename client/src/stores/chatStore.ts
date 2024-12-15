// Chat Store
import { defineStore } from 'pinia';
import axios from 'axios';

interface ChatState {
  messages: { role: string; content: string }[];
  isLoading: boolean;
}

export const useChatStore = defineStore('chat', {
  state: (): ChatState => ({
    messages: [],
    isLoading: false,
  }),
  actions: {
    // メッセージを送信する
    async sendMessage(userInput: string) {
      // ユーザーメッセージを追加
      this.messages.push({ role: 'user', content: userInput });
      this.isLoading = true;

      try {
        // APIへのリクエスト
        const response = await axios.post(
          'https://prcompass.onrender.com/chat', // サーバーのエンドポイント
          { messages: this.messages }, // メッセージを送信
          {
            headers: {
              'Content-Type': 'application/json',
            },
          }
        );

        // サーバーからのレスポンスを追加
        const assistantMessage = response.data.messages.find(
          (msg: { role: string; content: string }) => msg.role === 'assistant'
        );
        if (assistantMessage) {
          this.messages.push(assistantMessage); // ボットのメッセージを追加
        }
      } catch (error) {
        console.error('Error communicating with API:', error);
      } finally {
        this.isLoading = false;
      }
    },
    async clearMessage() {
      this.messages = [];
    }
  },
});
