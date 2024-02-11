<script setup>
import NavMenu from "@/components/NavMenu.vue";

</script>

<template>
  <div class="index-view">
    <NavMenu></NavMenu>
    <div class="index-bg">
      <el-scrollbar class="index-main">
        <router-view v-slot="{Component}">
          <transition name="el-fade-in">
            <component v-show="true" :is="Component"/>
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
  background-image: url("http://localhost:8085/img/503454.jpg");
  background-size: cover;
  flex: 1;
  box-sizing: border-box;
}
.index-main{
  color: #181818;
  padding: 0 20px 0;
  div:first-child{
    margin-top: 20px;
  }
}

</style>