<template>
  <div>
    <h1>图书借阅信息</h1>
    <div v-for="(item, index) in getPagedData()" :key="index" class="card">
      <div class="container">
        <div class="left">
          <div class="status-ind" :class="item.status"></div>
        </div>
        <div class="right">
          <div class="text-wrap">
            <p class="text-content">
              <a class="text-link" href="#">图书名称:</a> {{ item.book_name }}
              <a class="text-link" href="#">借阅人:</a> {{ username }}
            </p>
            <p class="time">ID:{{ item.book_no }}</p>
          </div>
          <div class="button-wrap">
            <button class="primary-cta">借阅时间: {{ item.borrow_time }}</button>
            <button class="secondary-cta">归还时间: {{ item.return_time }}</button>
            <button class="primary-cta">状态: {{ item.return_status }}</button>
            <button 
              v-if="item.return_status !== '已归还'" 
              class="return-cta" 
              @click="returnBook(item)"
            >归还书籍</button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="borrowList.length === 0" class="empty-message">
      暂无借阅信息
    </div> 
    
    <!-- 分页组件 -->
    <div v-if="borrowList.length > 0" class="pagination-container" style="margin-top: 20px; text-align: right;">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="pageSizes"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { getuserborrowmsg, updateuserborrow } from '@/api/dept';
import { mapState } from 'vuex';
export default {
  data() {
    return {
      borrowList: [],
      // 分页相关数据
      currentPage: 1, // 当前页码
      pageSize: 5, // 每页显示条数
      pageSizes: [5, 10, 20, 30, 50], // 可选每页显示条数
      total: 0 // 总数据条数
    }
  },
  methods: {
    async getBorrowList() {
      console.log("调用了getuserborrowmsg");
      
      console.log('调用 getuserborrowmsg，参数:', this.userPhone, this.username);
      const res = await getuserborrowmsg(this.userPhone, this.username);
      if (res.code === 200) {
        this.borrowList = res.data;
      } else {
        this.borrowList = [];
      }
      // 更新总数据条数
      this.total = this.borrowList.length;
      // 重置当前页码
      this.currentPage = 1;
    },
    /**
     * 获取分页后的数据
     */
    getPagedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.borrowList.slice(start, end);
    },
    /**
     * 处理页码变化
     */
    handleCurrentChange(current) {
      this.currentPage = current;
    },
    /**
     * 处理每页显示条数变化
     */
    handleSizeChange(size) {
      this.pageSize = size;
      this.currentPage = 1; // 重置页码
    },
    async returnBook(item) {
      const confirmReturn = confirm('是否归还书籍？')
      if (confirmReturn) {
        try {
          const data = {
            user_name: this.username,
            user_phone: this.userPhone,
            book_name: item.book_name,
            book_no: item.book_no,
            borrow_time: item.borrow_time,
            return_time: item.return_time,
            return_status: '已归还'
          }
          const res = await updateuserborrow(data)
          if (res.code === 200) {
            alert('归还成功')
            this.getBorrowList()
          } else {
            alert('归还失败')
          }
        } catch (error) {
          console.error('归还错误:', error)
          alert('归还失败')
        }
      }
    }
  },
  computed: {
    ...mapState(['userPhone','username'])
  },
  mounted() {
    this.getBorrowList();
  },
}
</script>

<style scoped>
.card {
  overflow: hidden;
  background-color: #f2f3f7;
  border-radius: 0.75em;
  cursor: pointer;
  transition: ease 0.2s;
  box-shadow: 1em 1em 1em #d8dae0b1, -0.75em -0.75em 1em #ffffff;
  border: 1.5px solid #f2f3f7;
  margin: 0 0 10px 0;
}

.card:hover {
  background-color: #d3ddf1;
  border: 1.5px solid #1677ff;
}

.container {
  margin-top: 1.25em;
  margin-bottom: 1.375em;
  margin-left: 1.375em;
  margin-right: 2em;
  display: flex;
  flex-direction: row;
  gap: 0.75em;
}
.empty-message {
  text-align: center;
  font-size: 24px;
  padding: 40px 0;
  color: #666;
}
.status-ind {
  width: 0.625em;
  height: 0.625em;
  margin: 0.375em 0;
  border-radius: 0.5em;
}

.status-ind.borrowed {
  background-color: #ff0000;
}

.status-ind.returned {
  background-color: #00ff00;
}

.text-wrap {
  display: flex;
  flex-direction: column;
  gap: 0.25em;
  color: #333;
}

.time {
  font-size: 0.875em;
  color: #777;
}

.text-link {
  font-weight: 500;
  text-decoration: none;
  color: black;
}

.button-wrap {
  display: flex;
  flex-direction: row;
  gap: 1em;
  align-items: center;
}

.secondary-cta {
  background-color: transparent;
  border: none;
  font-size: 15px;
  font-weight: 400;
  color: #666;
  cursor: pointer;
}

.primary-cta {
  font-size: 15px;
  background-color: transparent;
  font-weight: 600;
  color: #1677ff;
  border: none;
  border-radius: 1.5em;
  cursor: pointer;
}

.return-cta {
  font-size: 15px;
  background-color: #52c41a;
  font-weight: 600;
  color: #fff;
  border: none;
  border-radius: 1.5em;
  cursor: pointer;
  padding: 0.5em 1em;
}

.return-cta:hover {
  background-color: #73d13d;
  text-decoration: none;
}

button:hover {
  text-decoration: underline;
}

.right {
  display: flex;
  flex-direction: column;
  gap: 0.875em;
}
</style>