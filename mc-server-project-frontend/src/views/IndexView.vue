<script setup>
import NavMenu from "@/components/NavMenu.vue";
import {ref} from "vue";
import {useMsgStore} from "@/stores/msgStore.js";
const cachedComponents=ref(['HomeView','DownloadView','MessageView'])
const msgStore = useMsgStore();
msgStore.getMsgList(1)
</script>

<template>
  <div class="index-view">
    <NavMenu></NavMenu>
    <div class="index-bg">
      <el-scrollbar class="index-main">
          <router-view v-slot="{Component}">
            <transition name="el-fade-in" mode="out-in">
              <keep-alive :include="cachedComponents">
                <component :is="Component"/>
              </keep-alive>
            </transition>
          </router-view>
      </el-scrollbar>
    </div>
  </div>
</template>

<style scoped lang="less">
.index-view {
  display: flex;
  width: 100vw;
  height: 100vh;
  position: relative;
  min-width: 685px;
}
.index-bg{
  background-image: url("https://www.smallhowe.top/img/503454.jpg");
  background-size: cover;
  flex: 1;
  box-sizing: border-box;
}
.index-main{
  color: #181818;
  padding: 0 20px 0;
  box-sizing: border-box;
  overflow-y: hidden;
  div:first-child{
    margin-top: 20px;
  }
}
</style>