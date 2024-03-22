<script setup>
import TitleCard from "@/components/TitleCard.vue";
import {useUserStore} from "@/stores/userStore.js";
import {ref,reactive,computed} from "vue";
import {postBindUserGameId, postResetPassword} from "@/api/user.js";
import {ElMessage} from "element-plus";
import {formatDate} from "@/utils/DateUtils.js";

const store = useUserStore();
const userinfo={
  ...store.user
}

const activeIndex=ref('1')
const handleSelect=(index)=>{
  activeIndex.value="-1"
  setTimeout(()=>{
    activeIndex.value=index
  },200)
}
const userGroup=computed(()=>{
  if(userinfo.groups===1){
    return "管理员"
  }else{
    return "普通用户"
  }
})

const disableSaveBtn=ref(true)
const bindGameId= async ()=>{
  const res=await postBindUserGameId(form1.gameId)
  if (res.data.status===200){
    store.user.gameId=form1.gameId
  }
}

//用户基本信息表单
const form1=reactive({
  email:userinfo.email,
  username:userinfo.username,
  createTime:formatDate(userinfo.createTime),
  gameId:userinfo.gameId
})
const checkGameId=(rule,value,callback)=>{
  console.log(value)
  if(value===''||value===userinfo.gameId){
    disableSaveBtn.value=true
    callback()
  } else if(!/^[a-zA-Z0-9_]+$/u.test(value)){
    callback(new Error("游戏ID格式：大小写英文、数字、下划线,字数在2-16之间"))
  }else{
    disableSaveBtn.value=false
    callback()
  }
}
const gameIdRule=[
  {validator:checkGameId,trigger: 'change'},
  {min:2,max:16,message:'长度应在2-16之间',trigger:'blur'}
]

//修改密码表单
const form2=reactive({
  oldPassword:'',
  newPassword:'',
  confirmPassword:''
})
const checkPassRepeat=(rule,value,callback)=>{
  if (value === '') {
    callback(new Error('请输入密码'))
  } else if (value !== form2.newPassword) {
    callback(new Error("两次密码不一致"))
  } else {
    callback()
  }
}
const updatePassRules=ref({
  oldPassword: [
    {required: true, message: '旧密码不能为空', trigger: 'blur'},
    {min: 6, max: 16, message: '密码长度在6-16之间', trigger: 'blur'}
  ],
  newPassword: [
    {required: true, message: '新密码不能为空', trigger: 'blur'},
    {min: 6, max: 16, message: '密码长度在6-16之间', trigger: 'blur'}
  ],
  confirmPassword: [
    {required: true, message: '确认密码不能为空', trigger: 'blur'},
    {validator: checkPassRepeat, trigger: 'blur'}
  ],
})

//修改密码
const updatePassForm=ref()
const onUpdatePass=async ()=>{
  console.log(updatePassForm)

  const isCheck=await updatePassForm.value.validate((isValid)=>{
    if (!isValid) {
      ElMessage({
        type: 'warning',
        message: '请完整填写注册信息',
        grouping: true
      })
    }
  })
  if (!isCheck) return;

  const res=await postResetPassword(form2.oldPassword,form2.newPassword)
  if (res.data.status!==200){
    return
  }
  window.location.reload()

}
</script>

<template>
  <div class="user-info">
    <TitleCard title="用户信息"></TitleCard>

    <el-card class="content">

      <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
      >
        <el-menu-item index="1">基本信息</el-menu-item>
        <el-menu-item index="2">修改密码</el-menu-item>
      </el-menu>
      <div style="height: 500px;overflow: hidden">
      <transition name="el-zoom-in-center" mode="out-in">
          <el-form v-show="activeIndex === '1'" :model="form1" class="form" label-position="top">
            <el-form-item prop="email" label="邮箱">
              <el-input v-model="form1.email" disabled></el-input>
            </el-form-item>
            <el-form-item prop="username" label="用户名">
              <el-input v-model="form1.username" disabled ></el-input>
            </el-form-item>
            <el-form-item label="身份">
              <el-input :value="userGroup" disabled></el-input>
            </el-form-item>
            <el-form-item label="创建日期">
              <el-input :value="form1.createTime" disabled></el-input>
            </el-form-item>
            <el-form-item prop="gameId" label="游戏ID" :rules="gameIdRule">
              <el-input v-model="form1.gameId" @input="disableSaveBtn=false" placeholder="请输入游戏ID"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button @click="bindGameId" :disabled="disableSaveBtn" type="primary">保存</el-button>
            </el-form-item>
          </el-form>
      </transition>
      <transition name="el-zoom-in-center">
        <el-form v-show="activeIndex === '2'" :model="form2" :rules="updatePassRules" ref="updatePassForm" class="form" label-position="top">
          <el-form-item prop="oldPassword" label="旧密码">
            <el-input type="password" show-password v-model="form2.oldPassword"></el-input>
          </el-form-item>
          <el-form-item prop="newPassword" label="新密码">
            <el-input type="password" show-password v-model="form2.newPassword"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword" label="确认密码">
            <el-input type="password" show-password v-model="form2.confirmPassword"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="onUpdatePass" type="primary">重置</el-button>
          </el-form-item>
        </el-form>
      </transition>
      </div>
    </el-card>
  </div>
</template>

<style scoped lang="less">
.user-info {
  width: 100%;
  height: 100%;
}
.content{
  margin-top: 20px;
}
.form{
  margin-top: 20px;
}
</style>