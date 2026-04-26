<template>
  <div>
    <!-- 搜索表单 -->
    <el-form :model="searchForm" ref="searchForm" label-width="80px" inline>
      <el-form-item label="用户名" prop="user_name">
        <el-input v-model="searchForm.user_name" placeholder="请输入用户名" clearable style="width: 200px;"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="user_phone">
        <el-input v-model="searchForm.user_phone" placeholder="请输入手机号" clearable style="width: 200px;"></el-input>
      </el-form-item>
      <el-form-item label="日期" prop="date">
        <el-date-picker v-model="searchForm.date" type="date" placeholder="选择日期" format="yyyy-MM-dd"
          value-format="yyyy-MM-dd" style="width: 200px;" clearable>
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>


    <!-- 表格展示 -->
    <el-table :data="filteredBookinglist" style="width: 100%" :row-class-name="tableRowClassName">
      <el-table-column fixed prop="user_name" label="用户名"></el-table-column>
      <el-table-column prop="user_phone" label="手机" width="160"></el-table-column>
      <el-table-column prop="user_picture" label="头像">
        <template slot-scope="scope">
          <el-avatar :size="40" :src="scope.row.user_picture"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="bookingID" label="预定编号" width="100"></el-table-column>
      <el-table-column prop="date" label="日期" width="180"></el-table-column>
      <el-table-column prop="seat" label="座号"></el-table-column>
      <el-table-column prop="floor" label="楼层"></el-table-column>
      <el-table-column prop="start_time" label="开始时间" width="180"></el-table-column>
      <el-table-column prop="end_time" label="结束时间" width="180"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <el-button size="mini" type="primary" plain>操作</el-button>
        <el-button size="mini" type="danger" plain>删除</el-button>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { bookingmanage } from '@/api/dept';

export default {
  data() {
    return {
      bookinglist: [],
      filteredBookinglist: [], // 过滤后的数据
      loading: false, // 加载状态
      // 搜索表单数据
      searchForm: {
        user_name: '',
        user_phone: '',
        date: ''
      }
    }
  },
  methods: {
    async getAllbookingmsg() {
      this.loading = true;
      try {
        const res = await bookingmanage()
        this.bookinglist = res.data
        // 初始化时也应用过滤
        this.applyFilters();
        console.log(this.bookinglist);
      } catch (error) {
        console.error('获取预定信息失败:', error);
      } finally {
        this.loading = false;
      }
    },
    /**
     * 应用搜索过滤条件
     * 根据搜索表单的值过滤预定列表数据
     */
    applyFilters() {
      this.filteredBookinglist = this.bookinglist.filter(item => {
        // 用户名模糊匹配
        const matchName = !this.searchForm.user_name ||
          item.user_name.toLowerCase().includes(this.searchForm.user_name.toLowerCase());

        // 手机号模糊匹配
        const matchPhone = !this.searchForm.user_phone ||
          item.user_phone.includes(this.searchForm.user_phone);

        // 日期匹配
        const matchDate = !this.searchForm.date ||
          item.date === this.searchForm.date;

        return matchName && matchPhone && matchDate;
      });
    },
    /**
    * 处理搜索按钮点击事件
    * 应用当前搜索条件进行数据过滤
    */
    handleSearch() {
      this.applyFilters();
    },
    /**
     * 处理重置按钮点击事件
     * 清空搜索条件并重置数据
     */
    handleReset() {
      this.searchForm = {
        user_name: '',
        user_phone: '',
        date: ''
      };
      // 重置后显示全部数据
      this.applyFilters();
    },
    //设置表格行样式
    tableRowClassName({ row, rowIndex }) {
      if (row) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
      }
    }
  },
  mounted() {
    this.getAllbookingmsg()
  }
}
</script>

<style>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>