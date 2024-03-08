<script setup>
defineProps({
  topBgColor:String,
  bottomBgColor:String,
})
</script>

<template>
<div class="big-button" :style="{backgroundImage: `linear-gradient(to bottom, ${topBgColor}, ${bottomBgColor})`}">
  <span>
    <slot></slot>
  </span>
</div>
</template>

<style scoped lang="less">
.big-button {
  width: 100%;
  height: 100px;
  border-radius: 5px;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: bold;
  user-select: none;
  cursor: pointer;
  box-shadow: transparent 0 0 1px;
  transition: all .3s ease-in-out;
  &:hover{
    box-shadow: rgb(0, 51, 108) 0 0 10px;
    font-size: 22px;
  }
  span{
    font-weight: bold;
    letter-spacing: 2px;
  }
}
</style>