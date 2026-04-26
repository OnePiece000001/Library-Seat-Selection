<template>
  <div>
    <!-- 遍历分类后的书籍数据，传递给子组件 -->
    <!-- 注意：Vue2中v-for遍历对象需先通过Object.entries转换为可遍历数组 -->
    <div v-for="([type, books], index) in classifiedBooks" :key="index" class="book-type-group">
      <h3>{{ type }}</h3>
      <!-- 传递当前分类下的所有书籍给子组件 RecommendUp -->
      <RecommendUp :bookList="books" />
    </div>
  </div>
</template>

<script>
import RecommendUp from '@/components/recommentbook/RecommendUp.vue';
import { getrecombook } from '@/api/dept';
import { mapState } from 'vuex';

export default {
  components: {
    RecommendUp,
  },
  data() {
    return {
      bookList: []
    }
  },
  computed: {
    ...mapState(['userPhone']),
    classifiedBooks() {
      // 边界处理：避免bookList为空时报错
      if (!this.bookList || this.bookList.length === 0) {
        return [];
      }
      // 利用reduce方法按book_type分组，逻辑与之前一致
      const groupObj = this.bookList.reduce((acc, currentBook) => {
        const bookType = currentBook.book_type;
        // 如果累加器中没有该类型，初始化一个空数组
        if (!acc[bookType]) {
          acc[bookType] = [];
        }
        // 将当前书籍推入对应类型的数组中
        acc[bookType].push(currentBook);
        // 返回累加器，继续迭代
        return acc;
      }, {}); // 初始值为一个空对象
      
      // 关键：将分组后的对象转换为二维数组，支持v-for遍历
      // 转换格式：[["古典", [书籍1]], ["科技", [书籍2]], ["玄幻", [书籍3]]]
      return Object.entries(groupObj);
    }
  },
  methods: {
    //获取推荐图书函数
    async getRecommentBook() {
      try {
        if (!this.userPhone) {
          this.$message.warning('请先登录');
          return;
        }
        const res = await getrecombook(this.userPhone);
        if (res.code === 200 && res.data) {
          this.bookList = res.data;
        } else {
          this.bookList = [];
        }
      } catch (error) {
        console.error('获取推荐书籍失败:', error);
        this.$message.error('获取推荐书籍失败，请重试');
        this.bookList = [];
      }
    },
  },
  mounted() {
    this.getRecommentBook();
  }
}
</script>

<style></style>