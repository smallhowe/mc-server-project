<script setup>
import {getNewsList} from "@/api/news.js";
import {ref} from "vue";

const newsList = ref([])
const total=ref(0)
const pageSize=ref(1)
const currentPage=ref(1)
const loading=ref(false)
const getNews=async ()=>{
  loading.value=true
  const {data}=await getNewsList(currentPage.value)
  ElMessage.closeAll()
  if (data.status!==200){
    loading.value=false
    return
  }
  newsList.value=data.data.records
  total.value=data.data.pages
  pageSize.value=data.data.size
  loading.value=false
}
getNews()
const getMonthAndDay=(dateTime)=>{
  const date=new Date(dateTime)
  let month=date.getMonth()+1
  month=String(month).padStart(2,'0')
  let day=date.getDate()
  day=String(day).padStart(2,'0')
  return month + '-' + day;
}
</script>

<template>
  <div class="news">
    <el-card class="news-box-card" v-loading="loading">
      <template #header>
        <div class="news-card-header">
          <span>公告</span>
        </div>
      </template>
      <div class="content">
        <div v-for="item in newsList" :key="item.id" class="news-item">
          <span class="news-date">{{getMonthAndDay(item.createTime)}}</span>
          <span class="news-title">{{ item.title }}</span>
        </div>
      </div>
      <template #footer><el-pagination :default-page-size="pageSize"
                                       background layout="prev, pager, next"
                                       @current-change="getNews"
                                       :total="total"
                                       v-model:current-page="currentPage"
      />
      </template>
    </el-card>
  </div>
</template>

<style scoped lang="less">
.el-pagination{
  justify-content: flex-end;
}
.content{

  height: 285px;
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