<script setup>
import request from "@/utils/request.js";
import {ref} from "vue";
import {ElMessage} from "element-plus";

const props = defineProps({
  modelValue: {
    type: Boolean,
    required: true
  },
  form:{
    type:Object,
    default:{
      title: '默认标题',
      version: '默认版本',
      content: ''
    },
  }
})
const emit=defineEmits(['update:modelValue','update:form','uploadSuccess'])
const upload=ref()
const withFile=ref(false)
const showTips=ref(false)
const getFileHandle=(e)=>{
  console.log(e.status)
  if (e.status==='ready')
    withFile.value = true
  else{
    showTips.value=false
  }
}
const startUpload=()=>{
  showTips.value = true
  upload.value.submit()
}
const stopUpload = () => {
  showTips.value = false;
  upload.value.abort();
  upload.value.clearFiles();
  withFile.value = false;
}
const uploadSuccessHandle = () => {
  emit('uploadSuccess',null)
  emit('update:modelValue',false)
  ElMessage.success('上传成功')
}
const uploadFailureHandle = () => {
  emit('update:modelValue',false)
  ElMessage.error('上传失败')
}
const beforeCloseModal=(done)=>{
  if (!showTips.value){
    done()
  }
}
</script>

<template>
  <el-dialog title="文件更新"
             :model-value="modelValue"
             @update:modelValue="emit('update:modelValue',$event)"
             :append-to-body="true"
             destroy-on-close
             :before-close="beforeCloseModal"
  >
    <el-alert
        v-show="showTips"
        title="正在上传中"
        type="warning"
        description="请等待上传结束，期间内请不要关闭此窗口"
        show-icon
        class="tips"
    />
    <el-form :model="form">
      <el-form-item label="标题">
        <el-input v-model="form.title" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="版本">
        <el-input v-model="form.version" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="文件">
        <el-upload
            ref="upload"
            class="upload-demo"
            style="width: 100%"
            :data="form"
            :action="request.getUri()+'/api/res/upload'"
            drag
            :with-credentials="true"
            :limit="1"
            :auto-upload="false"
            :on-change="getFileHandle"
            :on-success="uploadSuccessHandle"
            :on-error="uploadFailureHandle"
        >
          <el-icon class="el-icon--upload"><upload-filled /></el-icon>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        </el-upload>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.content" type="textarea" maxlength="200" :rows="5" :show-word-limit="true"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div style="display: flex;justify-content: flex-end" class="dialog-footer">
        <div v-show="!showTips">
          <el-button @click="emit('update:modelValue',false)">取 消</el-button>
          <el-button type="success" @click="startUpload">更 新</el-button>
        </div>
        <div v-show="showTips">
          <el-button @click="stopUpload" type="danger">终 止</el-button>
        </div>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped lang="less">
.tips{
  margin-bottom: 20px;
}
</style>