<script setup>
import BigButton from "@/components/BigButton.vue";
import DownloadCard from "@/components/DownloadCard.vue";

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
  <el-card class="download-block">
    <DownloadCard :data="client"></DownloadCard>
    <DownloadCard :data="mods"></DownloadCard>
    <a href="https://www.oracle.com/cn/java/technologies/downloads/" target="_blank">
      <BigButton @click="" top-bg-color="#fc6363" bottom-bg-color="#fa884f">访问Oracle下载Java</BigButton>
    </a>
  </el-card>
</template>

<style scoped lang="less">
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