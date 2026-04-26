<template>
  <div class="fuyuansu">
    <div
      v-for="(book, index) in bookList"
      :key="index"
      class="zi1"
      @click="goToShowBook(book)"
    >
      <div class="bookmsg">
        <p>《{{ book.book_name }}》</p>
        <p>{{ book.book_author }}</p>
      </div>
      <img :src="book.book_photo || book.book_cover" alt="Book Photo">
    </div>
  </div>
</template>

<script>
export default {
  props: {
    bookList: {
      type: Array, // 定义数据类型为数组
      required: true, // 必传项，确保父组件传递该数据
      default: () => [] // 默认值为空数组，避免数据未传递时出现报错
    }
  },

  
  methods: {
    
    goToShowBook(book){
      // 将书籍信息存储到sessionStorage中
        sessionStorage.setItem('currentBook', JSON.stringify(book));
        // 路由跳转到BorrowCard组件
        this.$router.push({ name: 'BorrowCard' });
    }
  },

  
};
</script>

<style scoped>
  .bookmsg{
    position: absolute;
    width: 180px;
    margin: 10px;
    font-weight: 600;
    font-size: 20px;
    color: rgb(235, 233, 233);
  }
.fuyuansu {
  display: flex;
  gap: 1rem;
  margin: 0 0 1.5rem 0;
  position: relative;
  scrollbar-gutter: always;
  padding: 3rem 0 3rem 2rem;
  margin: 0;
  overflow-x: scroll;
  -webkit-overflow-scrolling: touch;
  scrollbar-color: #ffffff #ffffff;
}

.fuyuansu::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}

.fuyuansu::-webkit-scrollbar-thumb {
  background: #434343;
  border-radius: 10px;
  box-shadow: 
    inset 2px 2px 2px rgba(255, 255, 255, 0.25),
    inset -2px -2px 2px rgba(0, 0, 0, 0.25);
}

.fuyuansu::-webkit-scrollbar-track {
  background: linear-gradient(to right,
      #434343,
      #434343 1px,
      #262626 1px,
      #262626);
}

.zi1 {
  overflow: hidden;
  border-radius: 16px;
  background: linear-gradient(85deg, #434343, #262626);
  color: #fff;
  display: flex;
  flex-direction: column;
  transition: 0.2s;
  margin: 0;
  min-width: 215px;
  min-height: 290px;
  box-shadow: -2rem 0 3rem -2rem black;
}
img{
    width: 215px;
    height: 290px;
}
.zi1:not(:first-child) {
  margin-left: -130px;
}

.zi1:hover, .zi1:focus-within {
  transform: translate(0, -1rem) rotate(3deg);
  z-index: 10;
}

.zi1:hover ~ div, .zi1:focus-within ~ div {
  transform: translateX(130px);
}

.zi1:first-child:hover, .zi1:first-child:focus-within {
  transform: translate(-0.5rem, -1rem) rotate(3deg);
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .zi1 {
    min-width: 220px;
  }

  .zi1:not(:first-child) {
    margin-left: -30px;
  }

  .zi1:hover ~ div, .zi1:focus-within ~ div {
    transform: translateX(30px);
  }
}

@media (max-width: 800px) {
  .zi1 {
    min-width: 190px;
  }

  .zi1:not(:first-child) {
    margin-left: -10px;
  }

  .zi1:hover ~ div, .zi1:focus-within ~ div {
    transform: translateX(10px);
  }
}
</style>