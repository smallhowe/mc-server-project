<script setup>
import {ref,reactive} from "vue";
import {postGetCode} from "@/api/login.js";
import router from "@/router/index.js";

const active = ref(0);
//重置成功后倒计时
const countdown = reactive({
  time:0,
  interval:0
});
const resetSuccess=()=>{
  active.value = 3;
  countdown.time = 3;
  countdown.interval=setInterval(()=>{
    countdown.time--;
    if (countdown.time<0){
      goLogin()
    }
  },1000)
}
const goLogin=()=>{
  clearInterval(countdown.interval);
  router.replace('/login')
}
// 重置按钮
const forgetForm=ref()

const form=ref({
  email:'',
  password:'',
  password_repeat:'',
  code:''
});

const codeTime = ref(0);
const sendingEmail = ref(false);

const rules=ref({
  email: [
    {required: true, message: '邮箱不能为空', trigger: 'blur'},
    {type: 'email', message: '邮箱格式不正确'}
  ],
  code: {required: true, message: '验证码不能为空', trigger: 'blur'}
})

const isEmailValid=ref(false)
const onValidate=(prop,isValid)=> {
  if (prop === 'email'){
    isEmailValid.value = isValid;
  }
}
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
    codeTime.value = data.expire - 120;
    const timer = setInterval(() => {
      if (codeTime.value > 0)
        codeTime.value--
      else clearInterval(timer)
    },1000);
  }
  sendingEmail.value = false;
};
</script>

<template>
  <div class="container">
      <div class="step">
      <el-steps :active="active" align-center finish-status="success" process-status="finish">
        <el-step title="身份校验" />
        <el-step title="填写密码" />
        <el-step title="完成"/>
      </el-steps>
    </div>
    <div class="main">
      <transition name="el-fade-in" mode="out-in">
        <div v-if="active===0" class="forget">
        <div class="forget-title">
          <h1>忘记密码</h1>
          <p>请输入需要重置密码的电子邮件地址</p>
        </div>
        <el-form :model="form" :rules="rules" ref="forgetForm" class="forget-form" @validate="onValidate">
          <el-form-item prop="email">
            <el-input v-model="form.email" prefix-icon="Message" placeholder="请输入邮箱" tabindex="1"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <el-col :span="15" >
              <el-input v-model="form.code" prefix-icon="EditPen" placeholder="请输入验证码" tabindex="6"></el-input>
            </el-col>
            <el-col :span="8" :offset="1">
              <el-button type="success" :disabled="!isEmailValid || codeTime>0" :loading="sendingEmail" @click="getCode" tabindex="5">{{codeTime>0?codeTime+'秒后可重试':'获取验证码'}}</el-button>
            </el-col>
          </el-form-item>
          <el-button plain  class="submit-btn"  tabindex="7" @click="active=1">重置</el-button>
        </el-form>
      </div>
      </transition>
      <transition name="el-fade-in" mode="out-in">
        <div v-if="active===1" class="forget">
          <div class="forget-title">
            <h1>重置密码</h1>
            <p>请填写您的新密码，务必牢记，防止丢失</p>
          </div>
          <el-form :model="form" :rules="rules" ref="forgetForm" class="forget-form" @validate="onValidate">
            <el-form-item prop="password">
              <el-input v-model="form.password" prefix-icon="Lock" type="password" show-password :maxlength="16" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="password_repeat">
              <el-input v-model="form.password_repeat" prefix-icon="Lock" type="password" show-password :maxlength="16" placeholder="请再次输入密码"></el-input>
            </el-form-item>
            <el-button plain  class="submit-btn"  @click="resetSuccess">提交</el-button>
          </el-form>
        </div >
      </transition>
      <transition name="el-fade-in" mode="out-in">
        <div v-if="active===3" class="forget">
          <el-result
              icon="success"
              title="重置成功"
          >
            <template #sub-title>
              <p>密码已重置成功，{{countdown.time}}秒后将自动跳转到登录页</p>
            </template>
            <template #extra>
              <el-button type="primary" @click="goLogin">前往登录</el-button>
            </template>
          </el-result>
        </div>
      </transition>
    </div>
  </div>
</template>

<style scoped lang="less">
.container{
  height: 100%;
}
.step{
  margin-top: 5vh;
  @media (max-width: 991px){
    margin-top: 0;
  }
}
.main{
  height: 100%;
}
.forget{
  padding-top: 15vh;
  height: 100%;
  @media (max-width: 991px){
  padding-top: 20px;
  }
}
.forget-title {
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
.forget-form{
  display: flex;
  align-items: center;
  flex-direction: column;
  margin-top: 20px;
  padding: 0 20px;
  *{
    width: 100%;
  }
}
.submit-btn{
  margin-top: 50px;
  width: 70%;

}
</style>