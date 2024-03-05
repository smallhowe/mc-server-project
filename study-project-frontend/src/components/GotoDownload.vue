<script setup>

import router from "@/router/index.js";

const downloadJava=()=>{
  window.open("https://www.oracle.com/cn/java/technologies/downloads/")
}
</script>

<template>
  <div class="goto-download">
    <el-card>
      <template #header>
        <div class="card-header">
          资源下载
        </div>
      </template>
      <div class="download-group">
        <span>请确保安装好Java后再运行客户端</span>
        <button class="block btn download-client" @click="router.push('/index/download')">下载客户端</button>
        <button class="block btn download-java" @click="downloadJava">下载Java</button>
      </div>
    </el-card>
  </div>
</template>

<style scoped lang="less">
.download-group{
  button:not(:first-child){
    margin-top: 15px;
  }
}
.block{
  display: block;
  width: 100%;
}
.btn{
  height: 50px;
  color: white;
  border-radius: 5px;
  font-size: 14px;
  transition: all 0.2s ease-in;
  cursor: pointer;
  font-weight: bold;
  &:hover {
    box-shadow: #777777 0 0 5px 1px;
    font-size: 16px;
  }
  border: none;
  box-sizing: border-box;
}
.download-client{
  background-color: rgba(147, 66, 4, 0.87);
  border-top: #24c717 solid 5px;
}
.download-java{
  background:linear-gradient(rgb(255, 255, 255), rgb(220, 220, 220));
  color: #ff7b7b;

  border: 1px solid #97bad0;
}
</style>