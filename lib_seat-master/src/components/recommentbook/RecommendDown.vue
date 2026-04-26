<template>
  
  <div class="recommend-down">
    
    <div class="book-container" ref="bookContainer">
      <div
        v-for="(book, index) in booklist"
        :key="index"
        class="book-item"
      >
        <div class="book-image" >
          <img :src="book.book_photo" alt="Book Photo">
        </div>
        <div class="book-info">
          <h3>{{ book.book_name || '未命名书籍' }}</h3>
          <p>{{ book.book_author || '未知作者' }}</p>
          <p>{{ book.book_message || '未知简介' }}</p>
        </div>
      </div>
    </div>

    <div class="pagination-controls">
      <!-- <button @click="prevPage" :disabled="currentPage === 0">上一页</button>
      <span>第 {{ currentPage + 1 }} 页 / 共 {{ totalPages }} 页</span>
      <button @click="nextPage" :disabled="currentPage >= totalPages - 1">下一页</button> -->
      <button @click="prevPage">上一页</button>
      <span>第 1 页 / 共 1 页</span>
      <button @click="nextPage">下一页</button>
    </div>

  </div>
</template>

<script>
  import { getbook } from '@/api/dept';
export default {

  data() {
    return {
      currentPage: 0,
      booklist:[]
    };
  },

  methods: {
    // 跳转到书籍详情页
    // goToShowBook(bookid) {
    //   // this.$router.push({ name: 'ShowBook', params: { bookid } });
    //   alert('去阅读')
    // },
    // 上一页
    prevPage() {
      // if (this.currentPage > 0) {
      //   this.currentPage--;
      //   this.scrollToTop();
      // }
      alert('上一页')
    },
    // 下一页
    nextPage() {
      // if (this.currentPage < this.totalPages - 1) {
      //   this.currentPage++;
      //   this.scrollToTop();
      // }
      alert('下一页')
    },
    // 滚动到顶部
    scrollToTop() {
      // if (this.$refs.bookContainer) {
      //   this.$refs.bookContainer.scrollTop = 0;
      // }
      alert('回到顶部')
    },

    //获取推荐图书函数
    async getBook(){
      const res = await getbook()
      this.booklist = res.data
      console.log(this.booklist);
      
    },
  },
  mounted(){
    this.getBook()
  }
};
</script>

<style scoped>
.recommend-down {
  width: 100%;
  margin: 20px 0;
}

.pagination-controls {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-bottom: 15px;
}

.pagination-controls button {
  padding: 8px 15px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.pagination-controls button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.pagination-controls span {
  font-weight: bold;
  padding: 8px 0;
}

.book-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
  /* max-height: 750px; 5行的高度 */
  /* overflow-y: auto; */
  padding: 10px 0 10px 30px;
  /* border: 1px solid #ddd; */
  /* border-radius: 5px; */
}

.book-item {
  display: flex;
  width: 300px;
  height: 150px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
  transition: transform 0.3s;
}

.book-item:hover {
  transform: translateY(-5px);
}

.book-image {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #3b3b3b;
}

.book-image img {
  max-width: 100%;
  max-height: 100%;
  object-fit: cover;
}

.book-info {
  flex: 2;
  padding: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.book-info h3 {
  margin: 0;
  font-size: 18px; /* 书名字体比作者大 */
  color: #333;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.book-info p {
  margin: 5px 0 0 0;
  font-size: 14px;
  color: #666;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
</style>