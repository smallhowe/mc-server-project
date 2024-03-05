<script setup>
import {getNewsList} from "@/api/news.js";
import {ref} from "vue";
import {getMonthAndDay,formatDate} from "@/utils/DateUtils.js";

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

//公告内容模态框
const showNews=ref(false)
const newsObj=ref({
  id:0,
  title:'',
  content:'',
  author:'',
  createTime:''
})
const clickNewsHandle=(data)=>{
  console.log(data)
  showNews.value = true;
  newsObj.value=data
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
        <div @click="clickNewsHandle(item)" v-for="item in newsList" :key="item.id" class="news-item">
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

    <el-dialog :title="newsObj.title" v-model="showNews" destroy-on-close>

      <div class="news-other">
        <span>发布者：{{newsObj.author}}</span>
        <span>发布日期：{{formatDate(newsObj.createTime)}}</span>
      </div>

      <p class="news-content" v-html="newsObj.content"></p>

    </el-dialog>

  </div>
</template>

<style scoped lang="less">
.el-pagination{
  justify-content: flex-end;
}
.content{
  user-select: none;
  height: 300px;
}
.news-item{
  display: flex;
  align-items: center;
  box-sizing: border-box;
  padding: 10px 0;
  cursor: pointer;
  background: linear-gradient(to right, #fa1f1f, #f9d423, #25f217, #00bdff)
              no-repeat right bottom;
  background-size: 0 2px;
  transition: background-size 0.3s ease-in;
  &:hover{
    background-position: left bottom;
    background-size: 100% 2px;
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
.news-other{
  display: flex;
  justify-content: space-between;
  span{
    color: #666;
    font-size: 12px;
  }
}
.news-content{
  padding: 20px 0;
  font-size: 16px;
  color: #1c1c1c;
}
</style>