<template>
  <div class="book-container">
    
    <div class="book-card" v-for="book in bookList" :key="book.id || book.book_no" @click="handleBookClick(book)">
        <img :src="book.book_photo || book.book_cover" :alt="book.book_name" class="book-cover" />
        <div class="book-info">
        <h3 class="book-name">{{ book.book_name }}</h3>
        <p class="book-author">作者:{{ book.book_author }}</p>
        <p class="book-no">编号: {{ book.book_no }}</p>
        <p class="book-stock">库存: {{ book.book_num || book.stock_num }}</p>
        <p class="book-type"> 类型:{{ book.book_type }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    bookList: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    handleBookClick(book) {
      // 存储书籍信息到sessionStorage
      sessionStorage.setItem('currentBook', JSON.stringify(book));
      // 路由跳转到BorrowCard
      this.$router.push('/ShowAll/BorrowCard');
    }
  }
}
</script>

<style scoped>
.book-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  padding: 20px;
}

.book-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
  padding: 15px;
  width: calc(20% - 16px);
  min-width: 180px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background-color: #fff;
}

.book-card:hover {
  background-color: #f1f9ffab;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.book-cover {
  width: 120px;
  height: 170px;
  object-fit: cover;
  margin-bottom: 10px;
}

.book-info {
  box-sizing: border-box;
  padding: 10px;
  width: 100%;
  text-align: center;
}

.book-name {
  font-size: 16px;
  font-weight: bold;
  margin: 0 0 5px 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.book-author {
  font-size: 14px;
  color: #666;
  margin: 0 0 3px 0;
}

.book-no {
  font-size: 12px;
  color: #999;
  margin: 0 0 3px 0;
}

.book-stock {
  font-size: 12px;
  color: #999;
  margin: 0 0 3px 0;
}

.book-type {
  font-size: 12px;
  color: #999;
  margin: 0;
}
</style>