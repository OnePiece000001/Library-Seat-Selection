<template>
  <div>
    学生搜索书籍{{ searchinfo }}
    <!-- <div 
      class="booklis" 
      v-for="book in booklist" 
      :key="book.id || index"
      @click="navigateToBook(book.bookid)"
      style="cursor: pointer;"
    >
      <div class="booklis-left">
        <img 
          :src="book.bookphoto || 'https://picsum.photos/200/200?random=book'" 
          alt="书籍封面"
        >
      </div>
      <div class="booklis-right">
        <h2><p>{{ "《" + book.bookname + "》"}}</p></h2>
        <hr>
        <p>{{book.zuozhe || '未知作者'}}</p>
      </div>
    </div> -->
  </div>
</template>

<script>
import {searchbook} from "@/api/dept"
export default {
  data() {
    return {
      searchinfo: this.$route.query.searchinfo  || '',
      booklist:[]
    };
  },
  methods: {
    // navigateToBook(bookId) {
    //   if (bookId) {
    //     this.$router.push({
    //       name: 'ShowBook',
    //       params: { bookid: bookId }
    //     });
    //   } else {
    //     console.error('缺少bookid参数');
    //   }
    // },
    async getSearchbook(searchinfo){
      try{
        if (!searchinfo) {
          alert("请输入搜索信息");
          return;
        }
        let book_name = searchinfo.trim();
        const res = await searchbook(book_name);
        if (res.code === 200) {
          this.booklist = res.data || [];
          console.log(res);
          
        }
      } catch (error) {
        console.error("请求出错:", error);
        alert("请求出错，请稍后重试");
      }
    }
  },
  watch: {
    '$route.query.searchinfo'(newVal) {
      if (newVal) {
        this.searchinfo = newVal;
        this.getSearchbook(this.searchinfo);
      }
    }
  },
  mounted() {
    // 从路由参数获取搜索信息
    const searchinfo = this.$route.query.searchinfo;
    if (searchinfo) {
      this.searchinfo = searchinfo;
      this.getSearchbook(this.searchinfo);
    }
  },
};
</script>

<style scoped>
.booklis {
  display: flex;
  flex: 1;
  justify-content: flex-start;
  height: 100px;
  margin: 10px;
  box-shadow: 0 5px 5px rgba(0,0,0,0.1);
  transition: transform 0.2s;
}

.booklis:hover {
  transform: translateY(-2px);
}

.booklis-left {
  width: 75px;
  height: 100px;
}

img {
  width: 75px;
  height: 100px;
  object-fit: cover;
}

.booklis-right {
  width: 100%;
  padding: 10px;
  height: 100px;
}
</style>    