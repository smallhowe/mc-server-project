<script setup>
import {useUserStore} from "@/stores/userStore.js";
import {toRef,ref} from "vue";
import UpdateResource from "@/components/admin/UpdateResource.vue";
import {formatDate} from "@/utils/DateUtils.js";

const userStore = useUserStore();
const user=toRef(userStore.user)
const props=defineProps({
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
const emit=defineEmits(['uploadSuccess'])

const showDialog = ref(false)
const form=ref({
  id: props.data.id,
  title: props.data.title,
  version: props.data.version,
  content: props.data.content
})

</script>

<template>
  <div class="download-card">
    <el-card class="box-card">
      <template #header>
          <div class="header">
            <strong style="font-weight: bold;">{{data.title}}</strong>
            <div>
              <el-link v-if="user.groups===1" @click="showDialog=true"  type="danger" :underline="false">更新</el-link>
              <el-link :href="data.url"  type="primary" :underline="false">下载</el-link>
            </div>
          </div>
      </template>
      <el-row class="top">
        <el-col :span="12">操作者：{{data.operator}}</el-col>
        <el-col :span="12">更新日期：{{formatDate(data.updateTime)}}</el-col>
        <el-col :span="12">版本：{{data.version}}</el-col>
        <el-col :span="12">大小：{{data.size}}</el-col>
      </el-row>
      <div class="content">
        {{data.content}}
      </div>
    </el-card>
    <!--  管理员操作 -->
    <UpdateResource v-model="showDialog" v-model:form="form" @upload-success="emit('uploadSuccess',$event)" ></UpdateResource>
  </div>

</template>

<style scoped lang="less">
.header{
  display: flex;
  justify-content: space-between;
  &>div:last-child{
    &>*{
      margin: 0 10px;
    }
  }
}
.top{
  font-size: 14px;
  color: #838383;
  &>*:nth-child(2n){
    text-align: right;
  }
}
.content{
  margin-top: 5px;
  font-size: 16px;
}

</style>