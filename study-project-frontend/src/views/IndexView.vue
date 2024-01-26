<script setup>
import {getLogOut} from "@/api/login.js";
import router from "@/router/index.js";

const logout=async ()=>{
  await getLogOut().then(()=>{
    router.replace("/login")
  })
}
</script>

<template>
  <div>
    欢迎进入平台
    <el-button type="danger" @click="logout" plain >退出登录</el-button>
  </div>
</template>

<style scoped lang="less">

</style>