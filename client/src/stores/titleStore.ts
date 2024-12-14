import { defineStore } from 'pinia'

export const useTitleStore = defineStore('titleStore', {
  state: () => ({
    titleName: '' as string,
  }),
  getters: {
    getTitleName: (state) => state.titleName,
  },
  actions: {
    setTitleName(value: string) {
      this.titleName = value
    },
  }
})
