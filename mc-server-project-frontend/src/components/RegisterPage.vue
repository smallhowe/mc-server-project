<script setup>
import {ref} from "vue";
import router from "@/router/index.js";
import {ElMessage} from "element-plus";
import {postGetCode,postRegister} from "@/api/register.js";

const form=ref({
  email:'',
  username:'',
  password:'',
  password_repeat:'',
  code:''
});
const codeTime = ref(0);
const sendingEmail = ref(false);
// 表单验证规则
const checkUserName=(rule,value,callback)=>{
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/u.test(value)){
    callback(new Error('用户名只能是字母、数字、汉字'))
  }
  else {
    callback()
  }
}
const checkPassRepeat=(rule,value,callback)=>{
  if (value === '') {
    callback(new Error('请输入密码'))
  } else if (value !== form.value.password) {
    callback(new Error("两次密码不一致"))
  } else {
    callback()
  }
}
const rules=ref({
  email: [
    {required: true, message: '邮箱不能为空', trigger: 'blur'},
    {type: 'email', message: '邮箱格式不正确'}
  ],
  username: [
    {validator: checkUserName, trigger: 'blur'},
    {min: 2, max: 10, message: '用户名长度在2-10之间', trigger: 'blur'}
  ],
  password: [
      {required: true, message: '密码不能为空', trigger: 'blur'},
      {min: 6, max: 16, message: '密码长度在6-16之间', trigger: 'blur'}
  ],
  password_repeat: {validator: checkPassRepeat, trigger: 'blur'},
  code: {required: true, message: '验证码不能为空', trigger: 'blur'},
})

// 邮箱验证
const isEmailValid=ref(false)
const onValidate=(prop,isValid)=> {
  if (prop === 'email'){
    isEmailValid.value = isValid;
  }
}
// 验证码
const getCode = async () => {
  sendingEmail.value = true;
  const {data} = await postGetCode({email: form.value.email});

  if (data.status === 200){
    codeTime.value = 60;
    const timer = setInterval(() => {
      if (codeTime.value > 0)
        codeTime.value--
      else clearInterval(timer)
    },1000);
  }else if (data.status === 401) {
    codeTime.value = data.data.expire - 120;
    const timer = setInterval(() => {
      if (codeTime.value > 0)
        codeTime.value--
      else clearInterval(timer)
    },1000);
  }
  sendingEmail.value = false;
};
const name_repeat_msg=ref('')
// 注册按钮
const registerForm=ref()
const onRegister = async () => {
  const isCheck=await registerForm.value.validate((isValid) => {
    //校验注册表单
    if (!isValid) {
      ElMessage({
        type: 'warning',
        message: '请完整填写注册信息',
        grouping: true
      })
    }
  })
  if (!isCheck) return;

  const {email, username, password, code} = form.value;
  const {data} = await postRegister({
    email,
    username,
    password,
    code
  });

  if (data.status === 200){
    setTimeout(()=>{
      router.push('/login')
    },2000)
  }else if (data.status === 401){
    ElMessage.closeAll();
    name_repeat_msg.value = data.msg;
  }
};

</script>

<template>
  <div class="register">
    <div class="register-title">
      <h1>注册新用户</h1>
      <p>欢迎注册我们的平台,请在下方填写相关信息</p>
    </div>
    <el-form class="register-form" :rules="rules" :model="form" @validate="onValidate"  ref="registerForm" >
      <el-form-item prop="email" >
        <el-input v-model="form.email" prefix-icon="Message" placeholder="请输入邮箱" tabindex="1"></el-input>
      </el-form-item>
      <el-form-item :error="name_repeat_msg"  prop="username">
        <el-input v-model="form.username" prefix-icon="User" @input="name_repeat_msg=''" placeholder="请输入用户名" tabindex="2"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="form.password" prefix-icon="Lock" type="password" show-password :maxlength="16" placeholder="请输入密码" tabindex="3"></el-input>
      </el-form-item>
      <el-form-item prop="password_repeat">
        <el-input v-model="form.password_repeat" prefix-icon="Lock" type="password" show-password :maxlength="16" placeholder="请再次输入密码" tabindex="4"></el-input>
      </el-form-item>
      <el-form-item prop="code">
          <el-col :span="15" >
            <el-input v-model="form.code" prefix-icon="EditPen" placeholder="请输入验证码" tabindex="6"></el-input>
          </el-col>
          <el-col :span="8" :offset="1">
            <el-button type="success" :disabled="!isEmailValid || codeTime>0" :loading="sendingEmail" @click="getCode" tabindex="5">{{codeTime>0?codeTime+'秒后可重试':'获取验证码'}}</el-button>
          </el-col>
      </el-form-item>
      <el-button plain type="primary"  class="register-btn" @click="onRegister" tabindex="7">注册</el-button>
    </el-form>
    <div class="bottom">
      <el-divider content-position="center"><el-link disabled style="color: #666">已有账号？</el-link>&nbsp;<el-link @click="router.push('/login')" type="primary">立即登录</el-link></el-divider>
    </div>
  </div>
</template>

<style scoped lang="less">
.register{
  margin-top:20vh;
  height: 100%;
  @media (max-width: 991px){
    margin-top:0;
  }
}
.register-title {
  text-align: center;
  padding: 0 20px;
  white-space: nowrap;
  h1{
    font-size: 24px;
    font-weight: bold;
  }
  p{
    font-size: 14px;
  }
}
.register-form{
  display: flex;
  align-items: center;
  flex-direction: column;
  margin-top: 20px;
  padding: 0 20px;
  *{
    width: 100%;
  }
}
.bottom{
  margin-top: 50px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0 20px;
  white-space: nowrap;
}
.el-button{
  width: 100%;
}
.el-divider{
  margin: 40px 0;
}
</style>