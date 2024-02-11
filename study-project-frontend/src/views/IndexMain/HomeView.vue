<script setup>
import TitleCard from "@/components/TitleCard.vue";
import NewsComponent from "@/components/NewsComponent.vue";
import ServerInfo from "@/components/ServerInfo.vue";
import {reactive, ref} from "vue";
import CarouselComponent from "@/components/CarouselComponent.vue";
import GotoDownload from "@/components/GotoDownload.vue";
import {useUserStore} from "@/stores/userStore.js";
import {getUserInfo, userSignIn} from "@/api/user.js";

const serverInfo=reactive({
  serverNowPlayerNum:5,
  serverMaxPlayerNum:30,
  serverStatus:1,
})

const store = useUserStore();

const loading=ref(false)
const isSignIn=ref(false)
const signIn = () => {
  loading.value = true
  userSignIn().then(async res => {
    const {data} = res
    if (data.status !== 200) {
      loading.value = false
      isSignIn.value = true
      return
    }
    let user = await getUserInfo()
    user=user.data.data
    console.log(user)
    store.user.exp = user.exp
    store.user.level = user.level
    store.user.nextExp = user.nextExp

    loading.value = false
    isSignIn.value = true
  })
}

</script>

<template>
  <div class="home-view">
    <title-card title="首页">
      <template #right>
        <el-button :loading="loading"
                   :disabled="isSignIn"
                    :type="isSignIn?'default':'primary'"
                   plain @click="signIn">{{isSignIn?'今日已签到':'点击签到'}}</el-button>
      </template>
    </title-card>

    <el-row justify="space-between">
      <!-- 主页左侧内容 -->
      <el-col class="left" :span="24" :md="16">
        <!-- 服务器信息 -->
        <ServerInfo :server-info="serverInfo"></ServerInfo>
        <CarouselComponent></CarouselComponent>
      </el-col>

      <!-- 主页右侧内容 -->
      <el-col class="right" :span="24" :md="8" >
        <!--公告-->
        <NewsComponent/>
        <GotoDownload/>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="less">
.home-view{
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
.el-row{
  .el-col{
    margin-top: 20px;
  }
}
.left,.right{
  div:not(:first-child){
    margin-top: 20px;
  }
}
.left{
  @media (min-width: 992px) {
    padding-right: 10px;
  }
}
.right{
  @media (min-width: 992px) {
    padding-left: 10px;
  }
  padding-bottom: 20px;
}
</style>