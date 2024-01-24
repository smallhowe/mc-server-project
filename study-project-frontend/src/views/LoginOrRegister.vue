<script setup>


</script>

<template>
  <el-container>
    <el-row>
      <el-col class="left" :md="16">
        <el-image fit="cover" src="https://img.zcool.cn/community/014cdd5ce29993a801208f8b9dc378.jpg@2o.jpg"
                  style="height: 100%"></el-image>
      </el-col>
      <el-col  class="right" :md="8">
        <router-view v-slot="{Component}">
          <transition name="el-fade-in">
            <component v-show="true" :is="Component"/>
          </transition>
        </router-view>
      </el-col>
    </el-row>


  </el-container>
</template>

<style lang="less" scoped>
.el-container {
  width: 100vw;
  height: 100vh;
  .left {
    overflow: hidden;
    img {
      width: 100%;
      height: 100%;
    }
  }

  .right {
    overflow: hidden;
    background: white;
    padding: 20vh 0 0 0;
    @media (max-width: 991px) {
      padding: 10px 0;
    }

  }
}

</style>
