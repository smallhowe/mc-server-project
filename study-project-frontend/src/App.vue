<script setup>

import {useUserStore} from "@/stores/userStore.js";
import {getUserInfo} from "@/api/login.js";
import router from "@/router/index.js";
import {ElMessage} from "element-plus"

const store = useUserStore();
getUserInfo().then(res=>{
  if (res.data.status===200){
    console.log(res.data.data)
    store.user=res.data.data
    router.replace("/index")
  }else{
    ElMessage.closeAll()
  }
})
</script>

<template>
    <router-view></router-view>
</template>

<style lang="less">
</style>
