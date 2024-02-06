<script setup>
import {inject,onMounted} from "vue";

</script>

<template>
  <div class="news">
    <el-card class="news-box-card">
      <template #header>
        <div class="news-card-header">
          <span>公告</span>
        </div>
      </template>
      <div v-for="o in 4" :key="o" class="news-item">
        <span class="news-date">09-12</span>
        <span class="news-title">{{ '全新的新闻 - ' + o }}</span>
      </div>
      <template #footer><el-pagination :default-page-size="8" background layout="prev, pager, next" :total="8" /></template>
    </el-card>
  </div>
</template>

<style scoped lang="less">
.el-pagination{
  justify-content: flex-end;
}
.news-item{
  display: flex;
  align-items: center;
  box-sizing: border-box;
  &:not(:first-child){
    margin-top: 20px;
  }
  .news-date{
    font-size: 12px;
    color: #ccc;
    line-height: 12px;
  }
  .news-title{
    margin-left: 10px;
    font-size: 18px;
    line-height: 18px;

  }
}
</style>