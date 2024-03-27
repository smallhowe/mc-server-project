<script setup>
import {ref,toRef} from "vue";
import TitleCard from "@/components/TitleCard.vue";
import MessageBlock from "@/components/MessageBlock.vue";
import {useMsgStore} from "@/stores/msgStore.js";

const msgStore=useMsgStore()


const current=ref(1)
const addMsg=()=>{
  if (current.value>=msgStore.totalPages) return
  current.value++
  msgStore.getMsgList(current.value)
}
const openMsgTab=ref(JSON.parse(localStorage.getItem('openMsgTab'))||[])
const msgList=toRef(msgStore,'msgList')

const activeTab=ref('0')

const addTab=(msg)=>{
  let flag=openMsgTab.value.some(item=>item.id===msg.id)
  if (flag){
    activeTab.value=msg.id
    return
  }
  openMsgTab.value.push(msg)
  localStorage.setItem('openMsgTab',JSON.stringify(openMsgTab.value))
  activeTab.value=msg.id
}
const removeTab=(targetName)=>{
  if (activeTab.value===targetName){
    openMsgTab.value.forEach((item,index)=>{
      if (item.id===targetName){
        const nextTab = openMsgTab.value[index + 1]|| openMsgTab.value[index-1];

        if (nextTab) {
          activeTab.value = nextTab.id
        }else{
         activeTab.value='0'
        }

      }
    })
  }
  openMsgTab.value = openMsgTab.value.filter(item => item.id !== targetName);
  localStorage.setItem('openMsgTab',JSON.stringify(openMsgTab.value))
}

let toggleTime=0
const changeTab=(targetName)=>{
  clearTimeout(toggleTime)

  activeTab.value='-1'
  toggleTime=setTimeout(()=>{
    activeTab.value=targetName
  },200)

}

</script>

<template>
  <div class="message-view">
    <TitleCard title="消息中心" icon="el-icon-message" />
    <el-card class="msg-box">
      <el-tabs
          :model-value="activeTab"
          type="card"
          class="demo-tabs"
          @tab-remove="removeTab"
          @tab-change="changeTab"

      >

        <div class="scroll">
        <transition name="el-zoom-in-top">
          <el-tab-pane class="msg-home" v-infinite-scroll="addMsg" :infinite-scroll-distance="20" name="0" label="主页">
              <MessageBlock v-if="msgList.length > 0" v-for="item in msgList" :key="item.id"
                            :title="item.title"
                            :content="item.content"
                            :date="new Date(item.createTime)"
                            @click="addTab(item)"
              ></MessageBlock>
        </el-tab-pane>

        </transition>
        <transition-group name="el-fade-in">
          <el-tab-pane
              v-for="(item,index) in openMsgTab"
              :key="index"
              :label="item.title"
              :name="item.id"
              :closable="true"
          >
            {{ item.content }}
          </el-tab-pane>
        </transition-group>
        </div>
      </el-tabs>
    </el-card>
  </div>
</template>

<style scoped lang="less">
.message-view{
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  padding-bottom: 40px;
  overflow-y: hidden;
}
.msg-box {
  margin-top: 20px;
  width: 100%;
  height: 100%;
}
.msg-home{
  overflow-y: auto;
  &::-webkit-scrollbar{
    width: 0;
    height: 0;
  }
}
.scroll{
  height: 685px;
  padding-bottom: 40px;
}
.el-tab-pane{
  padding: 15px 0;
 height: 685px;
}

</style>