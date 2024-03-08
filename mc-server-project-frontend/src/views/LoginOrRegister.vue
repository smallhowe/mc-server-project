<script setup>
</script>

<template>
  <el-container>
    <el-row>
      <el-col class="left" :md="16">
        <el-image fit="cover" src="https://www.smallhowe.top/img/login-bg.jpg" style="height: 100%"></el-image>
      </el-col>
      <el-col  class="right" :md="8">
        <router-view v-slot="{Component}">
          <transition name="el-fade-in" mode="out-in">
            <component  :is="Component"/>
          </transition>
        </router-view>
        <div class="ICP">
          <el-link type="info" href="https://beian.miit.gov.cn/" target="_blank">
            粤ICP备2023154423号
          </el-link>
        </div>
      </el-col>
    </el-row>
  </el-container>
</template>

<style lang="less" scoped>
.el-container {
  width: 100vw;
  height: 100vh;
  min-width: 340px;
  .left {
    overflow: hidden;
    width: 100%;
    @media (max-width: 991px) {
    }

  }

  .right {
    overflow: auto;
    background: white;
    display: flex;
    flex-direction: column;
    @media (max-width: 991px) {
      padding: 10px 0;
    }
  }
}
.el-row{
  width: 100%;
}
.ICP{
  margin-top: 50px;
  display: flex;
  justify-content: center;
}
</style>
