<script setup>
import 'vue-cropper/dist/index.css'
import { VueCropper }  from "vue-cropper";
import {ref,reactive,defineProps,defineEmits,toRef} from 'vue';
import {useUserStore} from "@/stores/userStore.js";
import {Plus} from "@element-plus/icons-vue";
import PreviewAvatar from "@/components/PreviewAvatar.vue";
import {postUserUploadAvatar} from "@/api/user.js";

const userStore=useUserStore()
const userInfo=toRef(userStore,'user')


const props=defineProps({
  showDialog:{
    type:Boolean,
    default:false
  }
})
const emit=defineEmits(['update:showDialog'])
const cropper=ref()
// const imageUrl=ref('')
const options=reactive({
  img:'',
  outputSize:1,
  outputType:'png',
  autoCrop:true,
  autoCropHeight:200,
  autoCropWidth:200,
  fillColor:'#fff',
  mode:'cover',
  canMove:true,
  canScale:true,
  centerBox:true,
  fixed:true,
  fixedNumber:[1,1],
  fixedBox:true, //固定截图框大小
  canMoveBox:false, //截图框能否拖动
  info:false //裁剪框的大小信息
})

const handleAvatarSuccess = (response,uploadFile) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw)
  console.log(imageUrl.value)
}

const beforeAvatarUpload= (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('Avatar picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  return true
}

let fileName=ref('')
const postChange = (val) => {
  // 记录文件名
  fileName.value = val.name
  var reader = new FileReader()
  reader.readAsDataURL(val.raw)
  reader.onload = () => {
    options.img = reader.result
  }
};

let previewImg=ref({})
let imgUrl=ref('')
const handlePreview = (data) => {
  previewImg.value=data.img
  imgUrl.value=data.url
}

const uploadAvatar=()=>{
  const loading=ElLoading.service({
    lock:true,
    text:'正在上传头像中，请稍后...'
  })
  cropper.value.getCropBlob(async (data)=>{
    let formData=new FormData()
    formData.append('avatar',data,'avatar.png')
    const res=await postUserUploadAvatar(formData)
    if (res.data.status===200){
      await userStore.loadUserInfo()  //刷新用户信息
      emit('update:showDialog',false) //关闭对话框
    }
  })
  loading.close()
}
</script>

<template>
  <el-dialog
             :model-value="showDialog"
             @update:modelValue="emit('update:showDialog',$event)"
             :append-to-body="true"
             destroy-on-close
             style="min-width: 468px"
  >
    <div class="upload-avatar">
      <el-upload
          v-if="options.img===''"
          class="avatar-uploader"
          :auto-upload="false"
          :show-file-list="false"
          accept=".png,.jpg"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :on-change="postChange"
      >
        <div class="addImg">
          <el-icon><Plus/></el-icon>
        </div>
      </el-upload>

      <vue-cropper
          v-else
          ref="cropper"
          :img="options.img"
          :output-size="options.outputSize"
          :output-type="options.outputType"
          :auto-crop="options.autoCrop"
          :auto-crop-height="options.autoCropHeight"
          :auto-crop-width="options.autoCropWidth"
          :fill-color="options.fillColor"
          :can-move="options.canMove"
          :mode="options.mode"
          :can-scale="options.canScale"
          :center-box="options.centerBox"
          :fixed="options.fixed"
          :fixed-number="options.fixedNumber"
          :fixed-box="options.fixedBox"
          :can-move-box="options.canMoveBox"
          :info="options.info"
          class="cat"
          @realTime="handlePreview"
      ></vue-cropper>

      <div style=" margin: 0 20px; height: 100%;width: 1px;background-color: #ccc"></div>

      <PreviewAvatar v-if="options.img===''" :avatar="userInfo.avatarUrl" text="当前头像"></PreviewAvatar>
      <PreviewAvatar v-else :avatar="imgUrl" :img-style="previewImg" text="预览头像"></PreviewAvatar>
<!--      <div v-else class="show-preview">-->
<!--        <div class="preview">-->
<!--          <el-image :src="imgUrl" :style="previewImg" alt=""/>-->
<!--        </div>-->
<!--      </div>-->
    </div>

    <div class="total-btn" v-if="options.img!==''">
      <div class="re-select-avatar">
        <el-upload
            accept=".png,.jpg"
            :auto-upload="false"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :on-change="postChange"
        >
          <el-button type="primary" icon="Refresh">重新选择</el-button>
        </el-upload>
      </div>
      <div>
        <el-button type="success" icon="upload" @click="uploadAvatar">上传头像</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<style scoped lang="less">
.upload-avatar{
  display: flex;
  justify-content: center;
  width: 100%;
  height: 200px;
}
.avatar-uploader{
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.addImg{
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 36px;
  width: 200px;
  height: 200px;
  cursor: pointer;
  border: 1px dashed #ccc;
  color: #ccc;
  transition: all 20ms ease-in-out;
  &:hover{
    color: #00bdff;
    border-color: #00bdff;
  }
}
.cat{
  width: 200px;
  height: 200px;
}
.total-btn{
  margin-top: 20px;
  display: flex;
  justify-content: center;
  div{
    margin: 0 5px;
  }
}
.show-preview{
  width: 200px;
  height: 200px;
  display: flex;
  justify-content: center;
  align-items: center;
  .preview{
    width: 200px;
    height: 200px;
    overflow: hidden;
    border-radius: 50%;
    transform: scale(.7);
  }
}
</style>