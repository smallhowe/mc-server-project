<script setup>
import {defineProps} from 'vue'
const props=defineProps(['avatar',"text",'imgStyle'])
</script>

<template>
  <div class="preview-avatar">
    <div class="preview">
      <img :src="avatar" :style="imgStyle" alt=""/>
    </div>
    <p>{{ text }}</p>
  </div>
</template>

<style scoped lang="less">
.preview-avatar{
  width: 200px;
  height: 200px;
  aspect-ratio: 1/1;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  position: relative;
  p{
    position: absolute;
    left: 50%;
    bottom: 0;
    transform: translate(-50%,-50%);
    font-size: 12px;
    color: #727272;
  }
}
.preview{
  width: 200px;
  height: 200px;
  overflow: hidden;
  border-radius: 50%;
  transform: scale(.6);
  img {
    width: 100%;
    height: 100%;
    display: block;
    object-fit: cover;
  }
}
</style>