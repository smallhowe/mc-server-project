<script setup>
import {ref} from "vue";
import {getCarouselListRequest} from "@/api/carousel.js";

const carouselList = ref([]);

const getCarouselList = async () => {
  const {data} = await getCarouselListRequest();
  // console.log(data)
  if (data.status!==200) return;
  carouselList.value = data.data;
};
getCarouselList()


</script>

<template>
  <div class="carousel">
    <el-carousel height="100%"  trigger="click">
      <el-carousel-item class="carousel-item" v-for="(item,index) in carouselList" :key="index">
        <h1 class="carousel-title">{{item.title}}</h1>
        <el-image class="carousel-img"
                  fit="cover"
                  :src="item.imgUrl"
        ></el-image>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<style scoped lang="less">
.carousel{
  border-radius: 5px;
  overflow: hidden;
  box-shadow: #343434 0 1px 10px -2px;
  .el-carousel{
    height: 300px;
    @media (min-width: 991px){
      height: 500px;
    }
  }
}

.carousel-title{
  position: absolute;
  color: #ffffff;
  text-shadow: white 1px 1px 20px;
  user-select: none;
  bottom: 40px;
  left: 40px;
  z-index: 1000;
}
.carousel-img{
  width: 100%;
  height: 100%;
}
</style>