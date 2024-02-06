<script setup>
import {computed} from "vue";

const {accountLevel, serverInfo} = defineProps(['accountLevel', 'serverInfo'])


const serverPlayerNum = computed(() => {
  let percentage = serverInfo.serverNowPlayerNum / serverInfo.serverMaxPlayerNum * 100;
  return Number.parseFloat(percentage.toFixed(2))
})
const controlServe = () => {
  serverInfo.serverStatus = serverInfo.serverStatus === 1 ? 0 : 1
}


const getLevel = computed(() => {
  let levels = accountLevel.levels
  for (let i = levels.length - 1; i >= 0; i--) {
    if (accountLevel.exp >= levels[i].exp) {
      return levels[i].level;
    }
  }
})
const getNowExp = computed(() => {
  let levels = accountLevel.levels
  for (let i = levels.length - 1; i >= 0; i--) {
    if (accountLevel.exp >= levels[i].exp) {
      return accountLevel.exp - levels[i].exp;
    }
  }
})
const getNextExp = computed(() => {
  // 获取等级列表
  let levels = accountLevel.levels
  let level = 0
  for (let i = 0; i < levels.length; i++) {
    console.log(levels[i].level, "比较", getLevel.value)

    if (getLevel.value === levels[i].level) {
      level = levels[i + 1].exp - levels[i].exp
      break
    }
  }
  return level
})
const getExpPercent = computed(() => {
  if (getLevel.value < 10)
    return getNowExp.value / getNextExp.value * 100
  else
    return 100
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
              <p v-if="getLevel<10" class="exp">{{ getNowExp }}/{{ getNextExp }}</p>
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