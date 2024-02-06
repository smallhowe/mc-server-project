<script setup>

</script>

<template>
  <div class="carousel">
    <el-carousel height="400px" trigger="click">
      <el-carousel-item v-for="item in 4" :key="item">
        <h3>{{ item }}</h3>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<style scoped lang="less">
.carousel{
  border-radius: 5px;
  overflow: hidden;
  box-shadow: #343434 0 1px 10px -2px;
}
</style>