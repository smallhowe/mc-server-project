<script setup>
import BigButton from "@/components/BigButton.vue";

</script>

<template>
  <el-card class="download-block">
    <a href="http://smallhowe.top/resource/download/Minecraft.7z">
      <BigButton top-bg-color="#00c6e7" bottom-bg-color="#00efcb">Minecraft客户端</BigButton>
    </a>
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
a:not(:first-child){
  margin-top: 20px;
}
</style>