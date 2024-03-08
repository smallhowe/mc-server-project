<script setup>
defineProps({
  title:String
})
</script>

<template>
  <div class="title-card">
    <el-card shadow="always">
      <div class="title-body">
        <span>{{title}}</span>
        <slot name="right"></slot>
      </div>
    </el-card>

  </div>
</template>

<style scoped lang="less">
.title-card{

  font-size: 16px;
  box-sizing: border-box;
  span{
    font-weight: bold;
  }
}
.title-body{
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>