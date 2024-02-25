<script setup>

defineProps({
  title:{
    type:String,
    default:"这是标题"
  },
  content:{
    type:String,
    default:"这是content"
  },
  date:{
    type:Date,
    default:new Date()
  }
})

const formatDate=(date)=>{
  let year=date.getFullYear()
  let month=date.getMonth()+1
  let day=date.getDate()
  let hour=date.getHours()
  let minute=date.getMinutes()

  return year+"年"+month.toString().padStart(2,"0")+"月"+day+"日 "+hour+":"+minute.toString().padStart(2,"0")
}
</script>

<template>
  <div class="message-block">
    <div class="top">
      <div class="title">{{title}}</div>
      <div class="date">{{formatDate(date)}}</div>
    </div>
    <div class="content" v-html="content"></div>
  </div>
</template>

<style scoped lang="less">
.message-block{
  cursor: pointer;
  background: white;
  border: rgba(215, 215, 215, 0.41) 1px solid;
  padding: 15px;
  border-radius: 5px;
  width: 100%;
  transition: background-color 0.2s ease-in;
  margin-bottom: 20px;
}
.message-block:hover{
  background: #f1f1f1;
}
.top{
  display: flex;
  align-items: center;
}
.title {
  font-size: 16px;
  font-weight: bold;
  display: block;
  padding: 0;
}
.date{
  color: #a9a9a9;
  font-size: 16px;
  display: block;
  padding: 0;
  margin-left: 10px;
  height: 100%;
}
.content{
  font-size: 16px;
  padding: 10px 0;
  text-indent: 10px;
  text-overflow: ellipsis;
}

</style>