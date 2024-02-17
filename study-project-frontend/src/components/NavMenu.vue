<script setup>
import { ref,toRef } from 'vue'
import {useUserStore} from "@/stores/userStore.js";
import {getLogOut} from "@/api/login.js";
import router from "@/router/index.js";

const store=useUserStore()

const userinfo = toRef(store,'user')

const isCollapse = ref(true)
const groupImgLoading=ref(true)
const logout=async ()=>{
  await getLogOut().then(()=>{
    store.user = null;
    router.replace("/login")
  })
}

const controlNav=()=>{
  if (isCollapse.value){
    isCollapse.value = false
    setTimeout(()=>{
      let user = document.querySelector('.user');
      user.classList.add('user-active')
    },400)
  }else{
    let user = document.querySelector('.user');
    user.classList.remove('user-active')
    isCollapse.value = true
  }
}

</script>

<template>
  <div class="nav-menu">
    <el-menu
        default-active="/index/home"
        class="el-menu-vertical-demo"
        :router="true"
        :collapse="isCollapse"
    >
      <el-menu-item class="is-collapse" @click="controlNav">
        <el-icon><Expand v-show="isCollapse" /> <Fold v-show="!isCollapse" /></el-icon>
      </el-menu-item>
        <el-menu-item v-if="userinfo!==null" class="user">
          <div class="avatar">
            <el-avatar :src="userinfo.avatarUrl"/>
          </div>
          <div class="username">
            <span>{{userinfo.username}}</span>
          </div>
        </el-menu-item>
        <el-menu-item route="/index/home" index="/index/home">
          <el-icon><House /></el-icon>
          <template #title>首页</template>
        </el-menu-item>
        <el-menu-item route="/index/message" index="/index/message">
          <el-icon><Message /></el-icon>
          <template #title>消息</template>
        </el-menu-item>

      <el-tooltip :persistent="true" placement="right">
        <el-menu-item >
          <el-icon><ChatLineRound /></el-icon>
          <span>加入群聊</span>
        </el-menu-item>
        <template #content>
          <div class="join-group">
            <div>加入群聊</div>
            <el-image v-loading="groupImgLoading"
                      element-loading-text="加载中..."
                      element-loading-background="#000"
                      :class="{'el-img-loading':groupImgLoading}"
                      src="http://localhost:8085/img/QQGroup-small.png"
                      loading="lazy"
                      :preview-teleported="true"
                      :hide-on-click-modal="true"
                      :previewSrcList="['http://localhost:8085/img/QQGroup.jpg']"
                      @load="groupImgLoading=false"
            >
            </el-image>
          </div>
        </template>
      </el-tooltip>

      <el-menu-item index="/index/download">
        <el-icon><FolderOpened /></el-icon>
        <template #title>
          <span>下载资源</span>
        </template>
      </el-menu-item>

      <el-sub-menu index="4">
        <template #title>
          <el-icon><setting /></el-icon>
          <span>设置</span>
        </template>
        <el-menu-item index="/index/user/1">
          <el-icon><User /></el-icon>
          <span>账户信息</span>
        </el-menu-item>
      </el-sub-menu>

      <el-menu-item class="logout" @click="logout">
        <el-icon><SwitchButton /></el-icon>
        <template #title>
          <span>退出登录</span>
        </template>
      </el-menu-item>
    </el-menu>

  </div>
</template>

<style scoped lang="less">
.el-menu{
  scrollbar-width: none;
  height: 100%;
  overflow-y: scroll;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
}
.is-collapse{
  display: flex;
  background: orange;
  position: relative;
  align-items: center;
  justify-content: right;
  .el-icon {
    font-size: 24px;
    color: white;
  }
}
.user{
  box-sizing: border-box;
  flex-direction: column;
  height: 50px;
  transition: all 0.3s ease-in-out;
  overflow: hidden;
  justify-content: flex-start;
  .avatar{
    margin-top: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
    user-select: none;
  }
  .username{
    margin-top: 15px;
    line-height: normal;
    position: relative;
    .el-icon{
      position: absolute;
      font-size: 14px;
      top: 0.25rem;
      color: rgba(0,0,0,0.3);
      transition: color .3s ease-in;
      &:hover{
        color: rgba(0,0,0,0.8);
      }
    }
  }
}
.user-active{
  height: 90px;
}
.join-group{
  width: 100px;
  text-align: center;
  .el-image{
    width: 100%;
  }
}
.el-img-loading {
  width: 100px;
  height: 100px;
}
.logout:hover{
  *{
    color: #fc3c3c;
  }
}

</style>