<script setup>
import {computed,toRefs} from "vue";
import {useUserStore} from "@/stores/userStore.js";

const {serverInfo} = defineProps(['serverInfo'])

const store = useUserStore();

const userinfo=toRefs(store.user)
const getLevel=computed(() => {
  return userinfo.level
})
const getNowTargetExp=computed(()=>{
  return userinfo.levelList.value.find(item=>item.level===userinfo.level.value).exp
})
const getNowExp=computed(()=>{
  return userinfo.exp.value-getNowTargetExp.value;
})
const getNextExp=computed(()=>{

  return userinfo.nextExp.value-getNowTargetExp.value;
})
const serverPlayerNum = computed(() => {
  let percentage = serverInfo.serverNowPlayerNum / serverInfo.serverMaxPlayerNum * 100;
  return Number.parseFloat(percentage.toFixed(2))
})

const getExpPercent=computed(()=>{
  let percentage = getNowExp.value / getNextExp.value * 100;
  return percentage.valueOf();
})
</script>

<template>
  <div class="server-info">
    <el-card>
      <el-row :gutter="10">
        <el-col :span="8" class="server-status">
          <el-progress
              :duration="5"
              :indeterminate="true"
              :percentage="100"
              :status="serverInfo.serverStatus===1?'success':'exception'"
              type="circle"
          />
          <p :class="serverInfo.serverStatus===1?'server-status-online':'server-status-offline'">
            {{ serverInfo.serverStatus === 1 ? '在线' : '离线' }}
          </p>
<!--          <el-button :type="serverInfo.serverStatus===1?'danger':'success'" @click="controlServe">-->
<!--            {{ serverInfo.serverStatus === 1 ? '关闭' : '开启' }}-->
<!--          </el-button>-->
          <p>服务器运行状态</p>
        </el-col>
        <!--服务器玩家数量-->
        <el-col :span="8" class="server-player">
          <el-progress :percentage="serverPlayerNum" type="circle"/>
          <p>{{ serverInfo.serverNowPlayerNum }} / {{ serverInfo.serverMaxPlayerNum }}</p>
          <p>服务器当前玩家数量</p>
        </el-col>
        <!--玩家账户等级-->
        <el-col :span="8" class="server-account">
          <el-progress :percentage="getExpPercent" type="circle">
            <template #default>
              <h4>当前经验值</h4>
              <p  class="exp">{{ getNowExp }}/{{ getNextExp }}</p>
<!--              <p v-else class="exp" style="color: #f27304;font-weight: bold">MAX</p>-->
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