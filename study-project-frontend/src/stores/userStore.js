import { ref } from 'vue'
import { defineStore } from 'pinia'
import {getUserInfo} from "@/api/user.js";

export const useUserStore = defineStore('user', () => {
  const user = ref(null)

  const loadUserInfo= async ()=>{
    const res = await getUserInfo();
    user.value=res.data.data
  }

  return { user, loadUserInfo}
})
