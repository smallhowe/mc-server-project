<script setup>

import {useUserStore} from "@/stores/userStore.js";
import {getUserInfo} from "@/api/user.js";
import router from "@/router/index.js";
import {ElMessage} from "element-plus"
import {useMsgStore} from "@/stores/msgStore.js";

const store = useUserStore();
const msgStore = useMsgStore();
getUserInfo().then(res=>{
  if (res.data.status===200){
    // console.log(res.data.data)
    store.user=res.data.data
    msgStore.getMsgList(1)
    router.replace("/index")
  }else{
    ElMessage.closeAll()
    router.replace("/login")
  }
})
</script>

<template>
    <router-view></router-view>
</template>

<style lang="less">
//.el-tabs__item.is-top{
//  background-color: white;
//}
</style>
