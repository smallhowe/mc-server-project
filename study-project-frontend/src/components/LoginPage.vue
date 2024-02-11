<script setup>

import router from "@/router/index.js";
import {ref} from "vue";
import {postLogin} from "@/api/login.js";
import {getUserInfo} from "@/api/user.js";
import {useUserStore} from "@/stores/userStore.js";

const store = useUserStore();

const form = ref({
  username: '',
  password: '',
  remember: null
});

// 登录表单校验规则
const rules= ref({
  username:[
    {required:true,message:"请输入用户名"}
  ],
  password:[
    {required:true,message:"请输入密码"}
  ]
})


const login = async ()=>{
  const loading = ElLoading.service({
    text: "登录中",
  });

  try {
    const {data} = await postLogin(form.value)
    if (data.status !== 200){
      loading.close()
      return
    }
  }catch (err){
    console.error("发起登录请求出错:",err)
    loading.close()
    return
  }
  loading.close()

  await getUserInfo().then(res=>{
    if (res.data.status === 200){
      store.user=res.data.data
      router.push("/index")
    }
  })



}
</script>

<template>
  <div class="login">
    <div class="login-title">
      <h1>登录</h1>
      <p>在进入系统之前请先输入用户名和密码进行登录</p>
    </div>
    <div class="login-from">
      <el-form @keydown.enter="login" :model="form" :rules="rules" :hide-required-asterisk="true">
        <el-form-item prop="username">
          <el-input v-model="form.username" clearable placeholder="用户名/邮箱" prefix-icon="User"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="form.password"
              placeholder="密码"
              prefix-icon="Lock"
              show-password
              type="password"
          />
        </el-form-item>
        <el-row class="sel">
          <el-col :span="12"><el-checkbox v-model="form.remember" label="记住我" size="large" /></el-col>
          <el-col :span="12"><el-link @click="router.push('/forget')" target="_blank">忘记密码？</el-link></el-col>
        </el-row>
      </el-form>
      <el-button @click="login" plain type="success">登录</el-button>
      <el-divider content-position="center">
        没有账号
      </el-divider>
      <el-button @click="router.push('/register')" plain type="warning">注册账号</el-button>
    </div>
  </div>
</template>

<style scoped lang="less">
.login{
  margin-top:20vh;
  height: 100%;
  @media (max-width: 991px){
    margin-top:0;
  }
}
.login-title {
  text-align: center;
  font-size: 12px;
  white-space: nowrap;
  padding: 0 20px;
  h1{
    font-size: 24px;
    font-weight: bold;
  }
  p{
    padding: 1px;
    font-size: 14px;
  }
}

.login-from {
  margin-top: 20px;
  padding: 0 20px;

  .el-input {
  }

  .sel {
    margin-bottom: 20px;

    .el-col {
      display: flex;
      align-items: center;

      .el-link {
        margin-left: auto;
      }
    }

  }

  .el-button {
    display: block;
    margin: 10px auto;
    width: 80%;
  }
}
</style>