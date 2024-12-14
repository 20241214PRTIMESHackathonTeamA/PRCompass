import { defineStore } from 'pinia';
import axios from 'axios';
import type { components } from '@/api/schema'; // OpenAPIスキーマへのパスを指定

// 型定義をインポート
type JudgeResult = components['schemas']['JudgeResult'];

const API_PATH = 'https://prcompass.onrender.com'

export const useJudgeStore = defineStore('judge', {
  state: () => ({
    result: null as JudgeResult | null, // APIレスポンスを格納
    loading: false as boolean,
    error: null as string | null,
  }),

  actions: {
    // タイトルを判定する
    async judgeTitle(title: string) {
      this.loading = true;
      this.error = null;

      try {
        const response = await axios.post<JudgeResult>(API_PATH + '/judge', {
          title, // リクエストボディにタイトルを送信
        }, {
          headers: { 'Content-Type': 'application/json' }, // 必要に応じて追加
        });

        this.result = response.data; // レスポンスデータを格納
      } catch (err: unknown) {
        if (axios.isAxiosError(err)) {
          this.error = err.response?.data?.message || 'Failed to judge title';
        } else if (err instanceof Error) {
          this.error = err.message;
        } else {
          this.error = 'An unknown error occurred';
        }
      } finally {
        this.loading = false;
      }
    },
  },
});
