<template>
  <div>
    
 
    <h1>历史预约记录</h1>
    <div v-for="item in getPagedData()" :key="'history-' + item.bookingID" class="card">
      <div class="container">
        <div class="left">
          <div class="status-ind"></div>
        </div>
        <div class="right">
          <div class="text-wrap">
            <p class="text-content">
              <a class="text-link" href="#">START:</a> {{ item.start_time }}
              <a class="text-link" href="#">END:</a> {{ item.end_time }}
            </p>
            <p class="time">ID:{{ item.bookingID }}</p>
          </div>
          <div class="button-wrap">
            <button class="primary-cta">Floor: {{ item.floor }} Seat: {{ item.seat }} </button>
            <button class="secondary-cta">Data: {{ item.date }}</button>
            <button class="secondary-cta2">Status: {{ item.seat_status }}</button>
            <!-- <button class="cancel-cta" @click="cancelBooking(item.bookingID)">取消预约</button> -->
          </div>
        </div>
      </div>
    
      
    </div>
    <div v-if="historylist.length === 0" class="empty-message">
      暂无历史预约记录
    </div>
    
    <!-- 分页组件 -->
    <div v-if="historylist.length > 0" class="pagination-container" style="margin-top: 20px; text-align: right;">
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
import { bookingmessage } from '@/api/dept';
import { mapState } from 'vuex';
export default {
  data() {
    return {
      historylist: [],
      // 分页相关数据
      currentPage: 1, // 当前页码
      pageSize: 5, // 每页显示条数
      pageSizes: [5, 10, 20, 30, 50], // 可选每页显示条数
      total: 0 // 总数据条数
    }
  },
  methods: {
    
    async gethistorymsg() {
      const res = await bookingmessage(this.userPhone,'Y')
      console.log('历史预约数据:', res)
      this.historylist = res.data || []
      // 更新总数据条数
      this.total = this.historylist.length
      // 重置当前页码
      this.currentPage = 1
    },
    /**
     * 获取分页后的数据
     */
    getPagedData() {
      const start = (this.currentPage - 1) * this.pageSize
      const end = start + this.pageSize
      return this.historylist.slice(start, end)
    },
    /**
     * 处理页码变化
     */
    handleCurrentChange(current) {
      this.currentPage = current
    },
    /**
     * 处理每页显示条数变化
     */
    handleSizeChange(size) {
      this.pageSize = size
      this.currentPage = 1 // 重置页码
    },
    
  },
  computed: {
    ...mapState(['userPhone'])
  },
  mounted() {
    this.gethistorymsg()
  },
}
</script>

<style scoped>
.card {
  /* width: fit-content; */
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

.status-ind {
  width: 0.625em;
  height: 0.625em;
  background-color: #ff0000;
  margin: 0.375em 0;
  border-radius: 0.5em;
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

.empty-message {
  text-align: center;
  font-size: 24px;
  padding: 40px 0;
  color: #666;
}

.button-wrap {
  display: flex;
  flex-direction: row;
  gap: 1em;
  align-items: center;
}

.secondary-cta,.secondary-cta2 {
  background-color: transparent;
  border: none;
  font-size: 15px;
  font-weight: 400;
  color: #666;
  cursor: pointer;
}
.secondary-cta2 {
  font-weight: 500;
  color: #be3636;
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

.cancel-cta {
  font-size: 15px;
  background-color: #ff4d4f;
  font-weight: 600;
  color: #fff;
  border: none;
  border-radius: 1.5em;
  cursor: pointer;
  padding: 0.5em 1em;
}

.cancel-cta:hover {
  background-color: #ff7875;
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