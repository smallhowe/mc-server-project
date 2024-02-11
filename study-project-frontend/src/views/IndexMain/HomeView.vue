<script setup>
import TitleCard from "@/components/TitleCard.vue";
import NewsComponent from "@/components/NewsComponent.vue";
import ServerInfo from "@/components/ServerInfo.vue";
import {reactive,ref} from "vue";
import CarouselComponent from "@/components/CarouselComponent.vue";
import GotoDownload from "@/components/GotoDownload.vue";

const serverInfo=reactive({
  serverNowPlayerNum:5,
  serverMaxPlayerNum:30,
  serverStatus:1,
})



const accountLevel=reactive({
  exp:0,
  levels:[
    {level:0,exp:0},
    {level:1,exp:100},
    {level:2,exp:300},
    {level:3,exp:600},
    {level:4,exp:1000},
    {level:5,exp:1500},
    {level:6,exp:2000},
    {level:7,exp:2600},
    {level:8,exp:3300},
    {level:9,exp:4100},
    {level:10,exp:5000},
  ]
})

const loading=ref(false)
const signIn= ()=>{
  loading.value=true


}

</script>

<template>
  <div class="home-view">
    <title-card title="首页">
      <template #right>
        <el-button :loading="loading"

                    type="primary"
                   plain @click="signIn">点击签到</el-button>
      </template>
    </title-card>

    <el-row justify="space-between">
      <!-- 主页左侧内容 -->
      <el-col class="left" :span="24" :md="16">
        <!-- 服务器信息 -->
        <ServerInfo :server-info="serverInfo" :account-level="accountLevel"></ServerInfo>
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