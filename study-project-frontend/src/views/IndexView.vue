<script setup>
import {getLogOut} from "@/api/login.js";
import router from "@/router/index.js";
import {useUserStore} from "@/stores/userStore.js";
import NavMenu from "@/components/NavMenu.vue";

const store = useUserStore();
const logout=async ()=>{
  await getLogOut().then(()=>{
    store.user = null;
    router.replace("/login")
  })
}
</script>

<template>
  <div class="index-view">
    <NavMenu :userinfo="store.user" @logout="logout"></NavMenu>
    <div class="index-main">
      欢迎进入平台
      <el-button type="danger" @click="logout" plain >退出登录</el-button>
      <div style="height: 1000px;background: #181818;opacity: 0.5">666</div>
    </div>
  </div>
</template>

<style scoped lang="less">
.index-view {
  display: flex;
  width: 100vw;
  height: 100vh;
  position: relative;
  overflow-x: hidden;
  ::-webkit-scrollbar {
    width: 6px;
  }
  ::-webkit-scrollbar-thumb{
    background-color: rgba(150,150,150,0.8);
    border-radius: 6px;
  }
  ::-webkit-scrollbar-thumb:hover{
    background-color: rgba(150,150,150,0.9);
  }
}
.index-main{
  flex: 1;
  color: #181818;
  overflow-y: scroll;

  background-image: url("http://localhost:8085/img/503454.jpg");
  background-size: cover;
}

</style>