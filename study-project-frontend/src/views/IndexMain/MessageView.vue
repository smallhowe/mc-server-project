<script setup>
import {ref} from "vue";
import TitleCard from "@/components/TitleCard.vue";
import MessageBlock from "@/components/MessageBlock.vue";

const msgList=ref([
  {id:'1',title:'新的消息',content:'这是一条消息'},
  {id:'2',title:'新的消息2',content:'这是一条消息'},
  {id:'3',title:'新的消息3',content:'这是一条消息'},
  {id:'4',title:'新的消息4',content:'这是一条消息'},
  {id:'5',title:'新的消息5',content:'这是一条消息'},
])

const activeTab=ref('0')

const addTab=(msg)=>{
  let flag=msgList.value.some(item=>item.id===msg.id)
  if (flag){
    activeTab.value=msg.id
    return
  }
  msgList.value.push(msg)
}
const removeTab=(targetName)=>{
  if (activeTab.value===targetName){
    msgList.value.forEach((item,index)=>{
      if (item.id===targetName){
        const nextTab = msgList.value[index + 1]|| msgList.value[index-1];

        if (nextTab) {
          activeTab.value = nextTab.id
        }else{
         activeTab.value='0'
        }

      }
    })
  }
  msgList.value = msgList.value.filter(item => item.id !== targetName);
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
          :active-name="activeTab"
          type="card"
          class="demo-tabs"
          @tab-remove="removeTab"
          @tab-change="changeTab"

      >
        <el-scrollbar class="scroll">
        <transition name="el-zoom-in-top">
          <el-tab-pane name="0" label="主页">
              <MessageBlock></MessageBlock>
              <MessageBlock></MessageBlock>
              <MessageBlock></MessageBlock>
              <MessageBlock></MessageBlock>
              <MessageBlock></MessageBlock>
              <MessageBlock></MessageBlock>
              <MessageBlock></MessageBlock>
              <MessageBlock></MessageBlock>
              <MessageBlock></MessageBlock>
        </el-tab-pane>
        </transition>
        <transition-group name="el-fade-in">
          <el-tab-pane
              v-for="(item,index) in msgList"
              :key="index"
              :label="item.title"
              :name="item.id"
              :closable="true"
          >
            {{ item.content }}
          </el-tab-pane>
        </transition-group>
        </el-scrollbar>
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
.scroll{
  height: 685px;
  padding-bottom: 40px;
}
.el-tab-pane{
  padding: 15px 0;
 height: 685px;
}

</style>