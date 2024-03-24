<script setup>
defineProps({
  data:{
    type: Object,
    default: {
      title: '未知文件名',
      version: '1.20.1',
      size: '1 GB',
      url: '#',
      content: '这是文件的内容'
    }
  }
})
</script>

<template>
  <div class="download-card">
    <el-card class="box-card">
      <template #header>
          <strong style="font-weight: bold;">{{data.title}}</strong>
          <el-link :href="data.url" style="float: right; padding: 3px 0" type="primary" :underline="false">下载</el-link>
      </template>
      <el-row class="top">
        <el-col :span="12">版本：{{data.version}}</el-col>
        <el-col :span="12">大小：{{data.size}}</el-col>
      </el-row>
      <div class="content">
        {{data.content}}
      </div>
    </el-card>
  </div>
</template>

<style scoped lang="less">
.top{
  font-size: 14px;
  color: #838383;
  &>*:last-child{
    text-align: right;
  }
}
.content{
  margin-top: 5px;
}
</style>