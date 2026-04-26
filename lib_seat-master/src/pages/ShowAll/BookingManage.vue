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
    <el-table :data="getPagedData()" style="width: 100%" :row-class-name="tableRowClassName">
      <el-table-column fixed prop="user_name" label="用户名"></el-table-column>
      <el-table-column prop="user_phone" label="手机" width="160"></el-table-column>
      <el-table-column prop="user_picture" label="头像">
        <template slot-scope="scope">
          <el-avatar :size="40" :src="scope.row.user_picture"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="bookingID" label="预定编号" width="100"></el-table-column>
      <el-table-column prop="date" label="日期" width="180"></el-table-column>
      <el-table-column prop="seat" label="座号">
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEditing" v-model="scope.row.seat" size="small">
          </el-input>
          <span v-else>{{ scope.row.seat }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="floor" label="楼层">
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEditing" v-model="scope.row.floor" size="small">
          </el-input>
          <span v-else>{{ scope.row.floor }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="start_time" label="开始时间" width="180">
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEditing" v-model="scope.row.start_time" size="small">
          </el-input>
          <span v-else>{{ scope.row.start_time }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="end_time" label="结束时间" width="180">
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEditing" v-model="scope.row.end_time" size="small">
          </el-input>
          <span v-else>{{ scope.row.end_time }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button v-if="!scope.row.isEditing" size="mini" type="primary" plain @click="handleEdit(scope.row)">
            操作
          </el-button>
          <el-button v-else size="mini" type="success" plain @click="handleSave(scope.row)">
            修改
          </el-button>

          <el-button v-if="!scope.row.isEditing" size="mini" type="danger" plain @click="handleDelete(scope.row)">
            删除
          </el-button>
          <el-button v-else size="mini" type="warning" plain @click="handleCancel(scope.row)">
            取消
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <div class="pagination-container" style="margin-top: 20px; text-align: right;">
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
import { bookingmanage, updatebooking, deletebooking } from '@/api/dept';

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
      },
      // 分页相关数据
      currentPage: 1, // 当前页码
      pageSize: 10, // 每页显示条数
      pageSizes: [10, 20, 30, 50], // 可选每页显示条数
      total: 0 // 总数据条数
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
      // 更新总数据条数
      this.total = this.filteredBookinglist.length;
      // 重置当前页码
      this.currentPage = 1;
    },
    /**
     * 获取分页后的数据
     */
    getPagedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredBookinglist.slice(start, end);
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
    /**
     * 处理编辑按钮点击事件
     * 将当前行设置为可编辑状态
     */
    handleEdit(row) {
      // 设置编辑状态，保存原始数据用于取消操作
      this.$set(row, 'isEditing', true);
      this.$set(row, 'originalData', {
        seat: row.seat,
        floor: row.floor,
        start_time: row.start_time,
        end_time: row.end_time
      });
    },
    /**
    * 处理保存按钮点击事件
    * 将修改后的数据发送到后端并更新列表
    */
    async handleSave(row) {
      try {
        // 发送修改数据到后端
        const res = await updatebooking(row.bookingID, row.seat, row.floor, row.start_time, row.end_time)
        if (res.code === 200) {
          const originalIndex = this.bookinglist.findIndex(item => item.bookingID === row.bookingID);
          if (originalIndex !== -1) {
            this.bookinglist[originalIndex] = { ...row };
            delete this.bookinglist[originalIndex].isEditing;
            delete this.bookinglist[originalIndex].originalData;

            // 结束编辑状态
            this.$set(row, 'isEditing', false);
            delete row.originalData;

            // 重新应用过滤
            this.applyFilters();

            this.$message({
              message: '修改成功',
              type: 'success'
            });
          }
        }else {
          // 如果后端修改不成功，恢复原数据
          this.handleCancel(row);
          this.$message.error('修改失败，请重试');
        }
      } catch (error) {
        this.$message.error('修改失败，请重试');
        console.error('修改数据失败:', error);
      }
    },
    /**
     * 处理取消按钮点击事件
     * 恢复原始数据并退出编辑状态
     */
    handleCancel(row) {
      if (row.originalData) {
        // 恢复原始数据
        row.seat = row.originalData.seat;
        row.floor = row.originalData.floor;
        row.start_time = row.originalData.start_time;
        row.end_time = row.originalData.end_time;
        
        // 删除编辑状态和原始数据
        this.$set(row, 'isEditing', false);
        delete row.originalData;
      }
    },
    /**
     * 处理删除按钮点击事件
     */
    handleDelete(row) {
      this.$confirm('确定要删除这个预定信息吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then( async () => {
        const res = await deletebooking(row.bookingID)
        
        // 如果删除成功
        if (res.code === 200) {

        const originalIndex = this.bookinglist.findIndex(item => item.bookingID === row.bookingID);
        if (originalIndex !== -1) {
          this.bookinglist.splice(originalIndex, 1);
        }
        
        // 重新应用过滤
        this.applyFilters();
        
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
        }else {
          this.$message.error('删除失败，请重试');
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
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