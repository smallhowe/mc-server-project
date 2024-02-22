<script setup>

import TitleCard from "@/components/TitleCard.vue";
import DownloadBlock from "@/components/DownloadBlock.vue";
</script>

<template>
  <div class="download-view">
    <TitleCard title="资源下载"></TitleCard>
    <div class="main">
      <DownloadBlock></DownloadBlock>
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
</style>