<script setup>
import TitleCard from "@/components/TitleCard.vue";
import {useUserStore} from "@/stores/userStore.js";
import {ref,computed} from "vue";

const store = useUserStore();
const userinfo={
  ...store.user
}
const password=ref({
  oldPassword:null,
  newPassword:null,
  confirmPassword:null
})
const gameId=ref(null)

const activeIndex=ref('1')
const handleSelect=(index)=>{
  activeIndex.value="-1"
  setTimeout(()=>{
    activeIndex.value=index
  },200)
}
const userGroup=computed(()=>{
  if(userinfo.groups===1){
    return "管理员"
  }else{
    return "普通用户"
  }
})

</script>

<template>
  <div class="user-info">
    <TitleCard title="用户信息"></TitleCard>

    <el-card class="content">

      <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
      >
        <el-menu-item index="1">基本信息</el-menu-item>
        <el-menu-item index="2">修改密码</el-menu-item>
      </el-menu>
      <div style="height: 400px;overflow: hidden">
      <transition name="el-zoom-in-center">
          <el-form v-show="activeIndex === '1'" class="form" label-position="top">
            <el-form-item label="邮箱">
              <el-input v-model="userinfo.email" disabled></el-input>
            </el-form-item>
            <el-form-item label="用户名">
              <el-input v-model="userinfo.username" disabled ></el-input>
            </el-form-item>
            <el-form-item label="身份">
              <el-input v-model="userGroup" disabled></el-input>
            </el-form-item>
            <el-form-item label="游戏ID">
              <el-input v-model="gameId" placeholder="请输入游戏ID"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary">保存</el-button>
            </el-form-item>
          </el-form>
      </transition>
      <transition name="el-zoom-in-center">
          <el-form v-show="activeIndex === '2'" class="form" label-position="top">
            <el-form-item label="旧密码">
              <el-input v-model="password.oldPassword"></el-input>
            </el-form-item>
            <el-form-item label="新密码">
              <el-input v-model="password.newPassword"></el-input>
            </el-form-item>
            <el-form-item label="确认密码">
              <el-input v-model="password.confirmPassword"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary">重置</el-button>
            </el-form-item>
          </el-form>
      </transition>
      </div>
    </el-card>
  </div>
</template>

<style scoped lang="less">
.user-info {
  width: 100%;
  height: 100%;
}
.content{
  margin-top: 20px;
}
.form{
  margin-top: 20px;
}
</style>