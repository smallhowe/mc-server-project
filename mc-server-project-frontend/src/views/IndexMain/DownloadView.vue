<script setup>
import TitleCard from "@/components/TitleCard.vue";
import BigButton from "@/components/BigButton.vue";
import DownloadCard from "@/components/DownloadCard.vue";
import {getDownloadSourceList} from "@/api/resource.js";
import {ref} from "vue";

const resList=ref([])
getDownloadSourceList().then(res=>{
  resList.value=res.data.data
  console.log(resList.value)
})
const client={
  title: "Minecraft客户端",
  version: "1.20.1",
  size: "1.12 GB",
  url: "https://www.smallhowe.top/resource/download/Minecraft客户端.7z",
  content:"完整版客户端下载"
}
const mods={
  title: "Mods文件",
  version: "1.20.1",
  size: "488.4 MB",
  url: "https://www.smallhowe.top/resource/download/mods.zip",
  content: "如有客户端，可选择下载此mods文件"
}
</script>

<template>
  <div class="download-view">
    <TitleCard title="资源下载"></TitleCard>
    <div class="main">
      <el-card class="download-block">
        <DownloadCard v-for="data in resList" :key="data.id" :data="data"></DownloadCard>
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