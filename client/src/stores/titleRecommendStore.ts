import { defineStore } from 'pinia';
import axios from 'axios';
import type { components } from '@/api/schema'

// RecommendResponse型をインポート
type RecommendResponse = components['schemas']['RecommendResponse'];

const API_PATH = 'https://prcompass.onrender.com'

export const useTitleRecommendStore = defineStore('titleRecommend', {
  state: () => ({
    recommendations: [] as string[], // APIから取得するニューストピック
    loading: false as boolean,
    error: null as string | null,
  }),

  actions: {
    // APIからタイトルの推奨リストを取得する
    async fetchRecommendations() {
      this.loading = true;
      this.error = null;

      try {
        const response = await axios.get<RecommendResponse>(API_PATH + '/title-recommend');
        this.recommendations = response.data.newsTopics || [];
      } catch (err: unknown) {
        // unknown型として扱い、安全に処理する
        if (axios.isAxiosError(err)) {
          // Axiosエラーの場合
          this.error = err.response?.data?.message || 'Failed to fetch recommendations';
        } else if (err instanceof Error) {
          // 一般的なErrorオブジェクトの場合
          this.error = err.message;
        } else {
          // それ以外の型（想定外の場合）
          this.error = 'An unknown error occurred';
        }
      } finally {
        this.loading = false;
      }
    },
  }
});
