<template>
  <div>
    <!-- 搜索表单 -->
    <el-form :model="searchForm" ref="searchForm" label-width="100px" inline>
      <el-form-item label="座位名称" prop="seat_name">
        <el-input v-model="searchForm.seat_name" placeholder="请输入座位名称" clearable style="width: 200px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 楼层标签 -->
    <div class="floor-tabs" v-if="Object.keys(groupedSeatList).length > 0">
      <el-tag 
        v-for="floor in floorList" 
        :key="floor"
        :type="activeFloor === floor ? 'primary' : ''"
        class="floor-tag"
        @click="selectFloor(floor)"
      >
        {{ floor }} 层
      </el-tag>
    </div>

    <!-- 当前楼层的座位列表 -->
    <div v-if="activeFloor && currentFloorSeats.length > 0" class="current-floor">
      <h3 class="floor-title">{{ activeFloor }} 层</h3>
      
      <!-- 表格展示 -->
      <el-table :data="getPagedData()" style="width: 100%" border>
        <el-table-column prop="seat_name" label="座位名称" min-width="150"></el-table-column>
        <el-table-column prop="seat_no" label="座位编号" min-width="120"></el-table-column>
        <el-table-column prop="floor_no" label="楼层" min-width="100"></el-table-column>
        <el-table-column label="操作" fixed="right" width="120">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" plain @click="handleViewQRCode(scope.row)">
              查看二维码
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
          :total="currentFloorSeats.length">
        </el-pagination>
      </div>
    </div>

    <!-- 空数据提示 -->
    <el-empty v-if="Object.keys(groupedSeatList).length === 0" description="暂无数据"></el-empty>
    <el-empty v-else-if="!activeFloor || currentFloorSeats.length === 0" description="当前楼层暂无数据"></el-empty>

    <!-- 二维码弹窗 -->
    <el-dialog title="座位二维码" :visible.sync="qrCodeDialogVisible" width="400px" center>
      <div class="qr-code-container">
        <p class="seat-name">座位名称：{{ currentSeatName }}</p>
        <img v-if="qrCodeUrl" :src="qrCodeUrl" alt="二维码" class="qr-code-image">
        <div v-else class="loading-text">加载中...</div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="qrCodeDialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="downloadQRCode" :disabled="!qrCodeUrl">下载二维码</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getseat, getCode } from '@/api/dept';

export default {
  data() {
    return {
      seatList: [],
      filteredSeatList: [],
      loading: false,
      searchForm: {
        seat_name: ''
      },
      qrCodeDialogVisible: false,
      qrCodeUrl: '',
      currentSeatName: '',
      // 楼层相关
      activeFloor: '', // 当前激活的楼层
      // 分页相关
      currentPage: 1, // 当前页码
      pageSize: 10, // 每页显示条数
      pageSizes: [10, 20, 30, 50] // 可选每页显示条数
    }
  },
  computed: {
    // 按楼层分组的数据
    groupedSeatList() {
      const groups = {};
      this.filteredSeatList.forEach(item => {
        const floor = item.floor_no || '未知';
        if (!groups[floor]) {
          groups[floor] = [];
        }
        groups[floor].push(item);
      });
      return groups;
    },
    // 楼层列表
    floorList() {
      return Object.keys(this.groupedSeatList);
    },
    // 当前楼层的座位列表
    currentFloorSeats() {
      if (!this.activeFloor) return [];
      return this.groupedSeatList[this.activeFloor] || [];
    }
  },
  mounted() {
    this.getSeatList();
  },
  watch: {
    // 监听分组数据变化，自动选择第一个楼层
    groupedSeatList: {
      handler(newGroups) {
        const floors = Object.keys(newGroups);
        if (floors.length > 0 && !this.activeFloor) {
          this.activeFloor = floors[0];
        }
      },
      immediate: true
    }
  },
  methods: {
    // 获取座位列表
    async getSeatList() {
      this.loading = true;
      try {
        const res = await getseat();
        this.seatList = res.data || [];
        this.filteredSeatList = [...this.seatList];
      } catch (error) {
        console.error('获取座位信息失败:', error);
        this.$message.error('获取座位信息失败');
      } finally {
        this.loading = false;
      }
    },
    // 搜索
    handleSearch() {
      this.applyFilters();
    },
    // 重置
    handleReset() {
      this.searchForm.seat_name = '';
      this.applyFilters();
    },
    // 应用筛选
    applyFilters() {
      if (!this.searchForm.seat_name) {
        this.filteredSeatList = [...this.seatList];
      } else {
        const keyword = this.searchForm.seat_name.toLowerCase();
        this.filteredSeatList = this.seatList.filter(item => {
          return item.seat_name && item.seat_name.toLowerCase().includes(keyword);
        });
      }
      // 重置页码
      this.currentPage = 1;
    },
    // 选择楼层
    selectFloor(floor) {
      this.activeFloor = floor;
      this.currentPage = 1; // 切换楼层时重置页码
    },
    // 获取分页后的数据
    getPagedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.currentFloorSeats.slice(start, end);
    },
    // 处理页码变化
    handleCurrentChange(current) {
      this.currentPage = current;
    },
    // 处理每页显示条数变化
    handleSizeChange(size) {
      this.pageSize = size;
      this.currentPage = 1; // 重置页码
    },
    // 查看二维码
    async handleViewQRCode(row) {
      console.log('当前行数据:', row);
      console.log('座位名称:', row.seat_name);
      this.currentSeatName = row.seat_name;
      this.qrCodeUrl = '';
      this.qrCodeDialogVisible = true;
      try {
        console.log('请求参数 seat_name:', row.seat_name);
        const res = await getCode(row.seat_name);
        console.log('接口返回完整数据:', JSON.stringify(res, null, 2));
        console.log('res.code:', res?.code);
        console.log('res.data:', res?.data);
        if (res && res.code === 200 && res.data) {
          // 处理 data 中可能包含的反引号和空格
          let qrUrl = res.data.trim();
          if (qrUrl.startsWith('`') && qrUrl.endsWith('`')) {
            qrUrl = qrUrl.slice(1, -1).trim();
          }
          this.qrCodeUrl = qrUrl;
        } else {
          this.$message.error(res?.msg || '获取二维码失败');
        }
      } catch (error) {
        console.error('获取二维码失败:', error);
        this.$message.error('获取二维码失败');
      }
    },
    // 下载二维码
    downloadQRCode() {
      if (!this.qrCodeUrl) return;
      const link = document.createElement('a');
      link.href = this.qrCodeUrl;
      link.download = `座位二维码_${this.currentSeatName}.png`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      this.$message.success('下载成功');
    }
  }
}
</script>

<style scoped>
.floor-tabs {
  margin: 20px 0;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.floor-tag {
  cursor: pointer;
  padding: 8px 16px;
  font-size: 14px;
  transition: all 0.3s;
}

.floor-tag:hover {
  opacity: 0.8;
}

.current-floor {
  margin-bottom: 30px;
}

.floor-title {
  padding: 10px 15px;
  background-color: #f5f7fa;
  border-left: 4px solid #409eff;
  margin-bottom: 15px;
  font-size: 16px;
  font-weight: bold;
  color: #303133;
}

.qr-code-container {
  text-align: center;
  padding: 20px;
}

.seat-name {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #303133;
}

.qr-code-image {
  max-width: 100%;
  height: auto;
  border: 1px solid #eee;
  border-radius: 8px;
}

.loading-text {
  color: #909399;
  padding: 50px 0;
}
</style>
