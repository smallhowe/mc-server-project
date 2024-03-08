<script setup>
import {computed, toRef,ref,watch} from "vue";
import {useUserStore} from "@/stores/userStore.js";
import {useServerStore} from "@/stores/serverStore.js";
import {getServerInfoRequest} from "@/api/server.js";
import {ElMessage} from "element-plus";


//自动更新按钮
const autoFlash = ref(Boolean(localStorage.getItem('autoFlash')) || false)
const autoFlashTimer=ref(0)
const changeAutoFlash=(val)=>{
  if (val){
    localStorage.setItem("autoFlash",'true')
  }else{
    localStorage.removeItem("autoFlash")
  }
}
watch(autoFlash,(val)=>{
  if (val){
    autoFlashTimer.value=setInterval(()=>{
      serverStore.getServerInfo()
    },2000)
  }else {
    clearInterval(autoFlashTimer.value)
  }
},{
  immediate: true
})

//用户信息
const store = useUserStore();
const userinfo=toRef(store,'user')

const getLevel=computed(() => {
  return userinfo.value.level;
})
const getNowTargetExp=computed(()=>{
  return userinfo.value.levelList.find(item => item.level === userinfo.value.level).exp;
})
const getNowExp=computed(()=>{
  if (!userinfo.value.exp) return 0;
  return userinfo.value.exp - getNowTargetExp.value;
})
const getNextExp=computed(()=>{
  return userinfo.value.nextExp - getNowTargetExp.value;
})
const getExpPercent=computed(()=>{
  let percentage = getNowExp.value / getNextExp.value * 100;
  return percentage.valueOf();
})

//服务器信息
const serverStore = useServerStore();
serverStore.getServerInfo();
const serverPlayer = toRef(serverStore,'players')
const serverStatus=toRef(serverStore,'status')

const getServerPlayerPercent=computed(()=>{
  if (!serverStatus.value) return 0
  let percentage = serverPlayer.value.online / serverPlayer.value.max * 100
  return Number.parseFloat(percentage.toFixed(2))
})


</script>

<template>
  <div class="server-info">
    <el-card>
      <el-row>
        <el-col class="auto-flash">
          <el-switch v-model="autoFlash" @change="changeAutoFlash" active-text="自动更新" />
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="8" class="server-status">
          <el-progress
              :duration="5"
              :indeterminate="true"
              :percentage="100"
              :status="serverStatus===1?'success':'exception'"
              type="circle"
          />
          <p :class="serverStatus===1?'server-status-online':'server-status-offline'">
            {{ serverStatus===1 ? '在线' : '离线' }}
          </p>
<!--          <el-button :type="serverInfo.serverStatus===1?'danger':'success'" @click="controlServe">-->
<!--            {{ serverInfo.serverStatus === 1 ? '关闭' : '开启' }}-->
<!--          </el-button>-->
          <p>服务器运行状态</p>
        </el-col>
        <!--服务器玩家数量-->
        <el-col :span="8" class="server-player">
          <el-progress :percentage="getServerPlayerPercent" type="circle"/>
          <p> {{serverPlayer.online}} / {{serverPlayer.max}} </p>
          <p>服务器当前玩家数量</p>
        </el-col>
        <!--玩家账户等级-->
        <el-col :span="8" class="server-account">
          <el-progress :percentage="getExpPercent" type="circle">
            <template #default>
              <h4>当前经验值</h4>
              <p v-if="getLevel<userinfo.levelList.length-1" class="exp">{{ getNowExp }}/{{ getNextExp }}</p>
              <p v-else class="exp" style="color: #f27304;font-weight: bold">MAX</p>
            </template>
          </el-progress>
          <p>Lv:{{ getLevel }}</p>
          <p>玩家等级</p>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<style lang="less" scoped>
.server-info {
}
.auto-flash{
  display: flex;
  justify-content: flex-end;
  user-select: none;
}
.el-row:not(:first-child){
  margin-top: 15px;
}
.server-status {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.server-status-online {
  color: #25f217;
}

.server-status-offline {
  color: #ff3737;
}

.server-player {
  display: flex;
  align-items: center;
  flex-direction: column;
}

.server-account {
  display: flex;
  align-items: center;
  flex-direction: column;
}

.exp {
  font-size: 14px;
  margin-top: 5px;
}
</style>