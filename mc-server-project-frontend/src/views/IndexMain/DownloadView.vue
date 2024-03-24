<script setup>
import TitleCard from "@/components/TitleCard.vue";
import BigButton from "@/components/BigButton.vue";
import DownloadCard from "@/components/DownloadCard.vue";
import {getDownloadSourceList} from "@/api/resource.js";
import {ref} from "vue";

const resList=ref([])

const getList=()=>{
  getDownloadSourceList().then(res=>{
    resList.value=res.data.data
  })
}
getList()

</script>

<template>
  <div class="download-view">
    <TitleCard title="资源下载"></TitleCard>
    <div class="main">
      <el-card class="download-block">
        <DownloadCard v-for="(data,index) in resList"
                      :key="data.id"
                      :data="data"
                      :type="index"
                      @upload-success="getList"
        ></DownloadCard>
        <a href="https://www.oracle.com/cn/java/technologies/downloads/" target="_blank">
          <BigButton @click="" top-bg-color="#fc6363" bottom-bg-color="#fa884f">访问Oracle下载Java</BigButton>
        </a>
      </el-card>
    </div>
  </div>
</template>

<style scoped lang="less">
.download-view{
  width: 100%;
  height: 100%;
}
.main{
  margin-top: 20px;
}
.download-block{
  box-sizing: border-box;
}
a{
  display: block;
  text-decoration: none;
}
.download-card:not(:first-child){
  margin-top: 20px;
}
a:not(:first-child){
  margin-top: 20px;
}
</style>