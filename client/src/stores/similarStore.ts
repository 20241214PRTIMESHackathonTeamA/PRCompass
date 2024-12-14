import { defineStore } from 'pinia';
import axios from 'axios';
import type { components } from '@/api/schema'; // OpenAPIスキーマのパスを指定

// 型定義をインポート
type SimilarResponse = components['schemas']['SimilarResponse'];

const API_PATH = 'https://prcompass.onrender.com'


export const useSimilarStore = defineStore('similar', {
  state: () => ({
    similarTitles: [] as SimilarResponse[], // APIレスポンスを格納
    loading: false as boolean,
    error: null as string | null,
  }),

  actions: {
    // タイトルに基づいて類似タイトルを取得
    async fetchSimilarTitles(title: string) {
      this.loading = true;
      this.error = null;

      try {
        const response = await axios.post<SimilarResponse[]>(API_PATH + '/similar', {
          title, // リクエストボディにタイトルを送信
        }, {
          headers: { 'Content-Type': 'application/json' },
        });

        this.similarTitles = response.data; // レスポンスデータを格納
      } catch (err: unknown) {
        if (axios.isAxiosError(err)) {
          this.error = err.response?.data?.message || 'Failed to fetch similar titles';
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
