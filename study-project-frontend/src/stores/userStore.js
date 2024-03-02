import { ref } from 'vue'
import { defineStore } from 'pinia'
import {getUserInfo} from "@/api/user.js";

export const useUserStore = defineStore('user', () => {
  const user = ref(null)


  const loadUserInfo = ()=>{
    return new Promise(async (resolve, reject) => {
      await getUserInfo().then(res=>{
        if (res.data.status===200){
          user.value=res.data.data
          resolve()
        }else {
          //后端有响应，但是状态码不为200
          reject(res.data.msg)
        }
      }).catch(err=>{
        //后端无响应的结果
        reject(err)
      })
    })
  }

  return { user, loadUserInfo }
})
