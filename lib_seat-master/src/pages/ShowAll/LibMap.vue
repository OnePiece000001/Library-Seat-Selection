<template>
  <!-- 画布容器：负责监听滚轮缩放和鼠标拖动 -->
  <div id="canvas-container" @wheel="handleWheel" ref="canvasWrapper">
    <!-- 楼层选择 -->
    <FloorTool 
      ref="floorTool" 
      @floor-select="handleFloorSelect" 
      @create-desk="handleCreateDesk" 
      @create-seat="handleCreateSeat"
      @save-map="handleSaveMap"
      @delete-floor="handleDeleteFloor"
    />
    <!-- 可拖动缩放的画布主体 -->
    <div id="canvas-content" @mousedown.self="startDrag" @mouseup.self="stopDrag" @mouseleave.self="stopDrag"
      :style="{ transform: `translate(${translateX}px, ${translateY}px) scale(${scale})` }">
      <SeatVue :scale="scale" v-for="seat in filteredSeatList" :key="seat.id" :seat="seat" :floor-no="currentFloor" @seat-drag-stop="handleSeatDragStop" @seat-delete="handleDeleteSeat" />
      <DeskVue :scale="scale" v-for="desk in filteredDeskList" :key="desk.id" :desk="desk" @desk-drag-stop="handleDeskDragStop" @desk-delete="handleDeleteDesk" />
    </div>
  </div>
</template>

<script>
import FloorTool from '@/components/map/FloorTool.vue';
import SeatVue from '@/components/map/SeatVue.vue';
import DeskVue from '@/components/map/DeskVue.vue';
import dragZoom from '@/utils/dragZoom.js'; // 引入通用工具
import { getseat, getdesk, savedeskseat, deleteseat, deletedesk, getcurrent_time_seat_list, getOccupiedSeats } from '@/api/dept';
import { mapState } from 'vuex';

export default {
  name: 'DraggableCanvas',
  components: {
    SeatVue,
    FloorTool,
    DeskVue
  },
  data() {
    return {
      // 缩放相关
      scale: 1,          // 画布缩放比例
      zoomStep: 0.1,     // 每次滚轮缩放的步长
      minScale: 0.1,     // 最小缩放比例
      maxScale: 3,       // 最大缩放比例

      // 拖动相关
      isDragging: false, // 是否正在拖动
      startX: 0,         // 拖动起始X坐标
      startY: 0,         // 拖动起始Y坐标
      translateX: 0,     // 画布X轴偏移量
      translateY: 0,     // 画布Y轴偏移量
      dragState: {},      // 拖动状态缓存
      // 桌位 座位 楼层信息
      currentFloor: 'F1', // 当前选中的楼层，默认F1
      deskList: [],
      seatList: [],
      current_time_seat_list: [], // 当前半小时时间段被预约的座位号列表['CF1001','CF1002',...]
      
    };
  },
  computed: {
    ...mapState(['usertype']),

    // 过滤后的座位列表（按楼层过滤并添加have_people字段）
    filteredSeatList() {
      return this.seatList
        .filter(seat => seat.floor_no === this.currentFloor)
        .map(seat => ({
          ...seat,
          have_people: this.current_time_seat_list.includes(seat.seat_name)
        }));
    },
    // 过滤后的桌子列表
    filteredDeskList() {
      return this.deskList.filter(desk => desk.floor_no === this.currentFloor);
    }
  },
  methods: {
    async getSeatList() {
      const res = await getseat();
      if (res.code === 200) {
        this.seatList = res.data;
        console.log(res.data);

      }
    },
    async getDeskList() {
      const res = await getdesk();
      if (res.code === 200) {
        this.deskList = res.data;
        console.log(res.data);
      }
    },
    // 获取当前半小时时间段的座位列表
    async getCurrentTimeSeatList() {
      const now = new Date();
      // 计算当前半小时的开始时间
      const startTime = new Date(now);
      startTime.setMinutes(now.getMinutes() < 30 ? 0 : 30, 0, 0);
      // 计算当前半小时的结束时间
      const endTime = new Date(startTime);
      endTime.setMinutes(startTime.getMinutes() + 30);
      
      // 格式化时间为字符串
      const formatTime = (date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');
        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      };
      
      const start_time = formatTime(startTime);
      const end_time = formatTime(endTime);
      
      console.log('当前时间段:', start_time, '到', end_time);
      
      try {
        const res = await getcurrent_time_seat_list(start_time, end_time);
        if (res.code === 200) {
          this.current_time_seat_list = res.data || [];
          console.log('当前时间段被预约的座位列表:', this.current_time_seat_list);
        }
      } catch (error) {
        console.error('获取当前时间段座位列表失败:', error);
      }
    },

    // 处理座位拖动停止事件
    handleSeatDragStop(dragInfo) {
      // 找到对应的座位并更新位置
      const seatIndex = this.seatList.findIndex(seat => seat.id === dragInfo.id);
      if (seatIndex !== -1) {
        // 更新座位位置，转换为带px的字符串格式
        this.seatList[seatIndex].seat_x = dragInfo.x + 'px';
        this.seatList[seatIndex].seat_y = dragInfo.y + 'px';
        console.log('座位位置更新:', this.seatList[seatIndex]);
      }
    },
    
    // 处理桌子拖动停止事件
    handleDeskDragStop(dragInfo) {
      // 找到对应的桌子并更新位置
      const deskIndex = this.deskList.findIndex(desk => desk.id === dragInfo.id);
      if (deskIndex !== -1) {
        // 更新桌子位置，转换为带px的字符串格式
        this.deskList[deskIndex].desk_x = dragInfo.x + 'px';
        this.deskList[deskIndex].desk_y = dragInfo.y + 'px';
        console.log('桌子位置更新:', this.deskList[deskIndex]);
      }
    },
    
    // 处理创建桌子事件
    handleCreateDesk({ type }) {
      // 生成唯一ID
      const newId = this.generateUniqueId();
      
      // 生成desk_no，格式为3位数字，从001开始递增
      const deskNo = this.generateItemNo('desk');
      
      // 生成desk_name，格式为D+floor_no+desk_no
      const deskName = `D${this.currentFloor}${deskNo}`;
      
      // 创建新桌子对象
      const newDesk = {
        id: newId,
        desk_name: deskName,
        desk_no: deskNo,
        floor_no: this.currentFloor,
        desk_type: type,
        desk_x: '100px',
        desk_y: '100px'
      };
      
      // 将新桌子添加到deskList中
      this.deskList.push(newDesk);
      console.log('创建了新桌子:', newDesk);
    },
    
    // 处理创建椅子事件
    handleCreateSeat() {
      // 生成唯一ID
      const newId = this.generateUniqueId();
      
      // 生成seat_no，格式为3位数字，从001开始递增
      const seatNo = this.generateItemNo('seat');
      
      // 生成seat_name，格式为C+floor_no+seat_no
      const seatName = `C${this.currentFloor}${seatNo}`;
      
      // 创建新椅子对象
      const newSeat = {
        id: newId,
        seat_name: seatName,
        seat_no: seatNo,
        floor_no: this.currentFloor,
        seat_x: '100px',
        seat_y: '100px'
      };
      
      // 将新椅子添加到seatList中
      this.seatList.push(newSeat);
      console.log('创建了新椅子:', newSeat);
    },
    
    // 生成唯一ID
    generateUniqueId() {
      // 生成一个基于时间戳和随机数的唯一ID
      return Date.now() + '_' + Math.floor(Math.random() * 1000);
    },
    
    // 生成物品编号（desk_no或seat_no）
    generateItemNo(type) {
      let maxNo = 0;
      let list = [];
      
      if (type === 'desk') {
        // 获取当前楼层的所有桌子
        list = this.deskList.filter(desk => desk.floor_no === this.currentFloor);
      } else if (type === 'seat') {
        // 获取当前楼层的所有椅子
        list = this.seatList.filter(seat => seat.floor_no === this.currentFloor);
      }
      
      // 找出最大的编号
      list.forEach(item => {
        const no = parseInt(item[type === 'desk' ? 'desk_no' : 'seat_no']);
        if (no > maxNo) {
          maxNo = no;
        }
      });
      
      // 生成新编号，格式为3位数字
      return (maxNo + 1).toString().padStart(3, '0');
    },
    
    // 处理楼层选择事件   切换了楼层后，需要刷新当前楼层的桌子和椅子
    handleFloorSelect(floor) {
      // 更新当前选中的楼层
      this.currentFloor = floor;
      console.log('当前选中楼层:', this.currentFloor);
      // 刷新当前楼层的桌子和椅子
      this.getDeskList();
      this.getSeatList();
    },
    
    // 处理保存地图事件
    async handleSaveMap() {
      // console.log("哈哈哈哈哈哈哈哈哈");
      // console.log(this.deskList);
      // console.log(this.seatList);
      try {
        // 调用savedeskseat API，传递deskList和seatList数据
        const res = await savedeskseat({
          deskList: this.deskList,
          seatList: this.seatList

        });
        
        // 处理响应结果
        if (res.code === 200) {
          // 保存成功，显示成功提示
          this.$message.success('地图数据保存成功');
          console.log('地图数据保存成功:', res.data);
        } else {
          // 保存失败，显示失败提示
          this.$message.error('地图数据保存失败：' + (res.msg || '未知错误'));
          console.error('地图数据保存失败:', res);
        }
      } catch (error) {
        // 网络错误或其他异常
        console.error('地图数据保存异常:', error);
        this.$message.error('地图数据保存失败，请检查网络连接或稍后重试');
      }
    },
    
    // 处理删除座位事件
    async handleDeleteSeat(seatName) {
      try {
        // 调用deleteseat API，传递seat_name参数
        const res = await deleteseat(seatName);
        
        // 处理响应结果
        if (res.code === 200) {
          // 删除成功，更新seatList
          this.seatList = this.seatList.filter(seat => seat.seat_name !== seatName);
          // 显示成功提示
          this.$message.success('座位删除成功');
          console.log('座位删除成功:', seatName);
        } else {
          // 删除失败，显示失败提示
          this.$message.error('座位删除失败：' + (res.msg || '未知错误'));
          console.error('座位删除失败:', res);
        }
      } catch (error) {
        // 网络错误或其他异常
        console.error('座位删除异常:', error);
        this.$message.error('座位删除失败，请检查网络连接或稍后重试');
      }
    },
    
    // 处理删除桌子事件
    async handleDeleteDesk(deskName) {
      try {
        // 调用deletedesk API，传递desk_name参数
        const res = await deletedesk(deskName);
        
        // 处理响应结果
        if (res.code === 200) {
          // 删除成功，更新deskList
          this.deskList = this.deskList.filter(desk => desk.desk_name !== deskName);
          // 显示成功提示
          this.$message.success('桌子删除成功');
          console.log('桌子删除成功:', deskName);
        } else {
          // 删除失败，显示失败提示
          this.$message.error('桌子删除失败：' + (res.msg || '未知错误'));
          console.error('桌子删除失败:', res);
        }
      } catch (error) {
        // 网络错误或其他异常
        console.error('桌子删除异常:', error);
        this.$message.error('桌子删除失败，请检查网络连接或稍后重试');
      }
    },
    
    // 处理删除楼层事件
    handleDeleteFloor(floor_no) {
      // 删除该楼层的所有桌子
      this.deskList = this.deskList.filter(desk => desk.floor_no !== floor_no);
      // 删除该楼层的所有座位
      this.seatList = this.seatList.filter(seat => seat.floor_no !== floor_no);
      console.log(`已删除楼层 ${floor_no} 的所有桌子和座位`);
    },
    
    // 处理滚轮缩放（复用通用逻辑）
    handleWheel(event) {
      const floorToolElement = this.$refs.floorTool?.$el;
      if (floorToolElement && floorToolElement.contains(event.target)) {
        return;
      }
      event.preventDefault();

      // 复用通用缩放逻辑
      this.scale = dragZoom.calculateScale({
        scale: this.scale,
        deltaY: event.deltaY,
        zoomStep: this.zoomStep,
        minScale: this.minScale,
        maxScale: this.maxScale
      });
    },

    // 开始拖动（复用通用逻辑）
    startDrag(event) {
      // 初始化画布拖动状态
      this.dragState = dragZoom.initDrag({
        isDragging: this.isDragging,
        startX: event.clientX,
        startY: event.clientY,
        translateX: this.translateX,
        translateY: this.translateY,
        scale: this.scale,
        currentTarget: event.target
      });

      this.isDragging = true;

      // 定义画布拖动处理函数
      const dragHandler = (e) => {
        const result = dragZoom.calculateDrag({
          isDragging: this.isDragging,
          startX: this.dragState.startX,
          startY: this.dragState.startY,
          clientX: e.clientX,
          clientY: e.clientY,
          prevTranslateX: this.dragState.prevTranslateX,
          prevTranslateY: this.dragState.prevTranslateY,
          scale: this.dragState.scale,
          type: 'canvas'
        });

        if (result) {
          this.translateX = result.translateX;
          this.translateY = result.translateY;
        }
      };

      this.dragState.dragHandler = dragHandler;
      window.addEventListener('mousemove', dragHandler);
    },

    // 停止拖动（复用通用逻辑）
    stopDrag() {
      const stopResult = dragZoom.stopDrag(this.dragState);
      this.isDragging = stopResult.isDragging;
      this.dragState = {};
    },

    // 获取当前半小时时间段被预约的座位号列表
    async fetchOccupiedSeats() {
      try {
        const res = await getOccupiedSeats();
        if (res.code === 200) {
          this.current_time_seat_list = res.data || [];
          console.log('当前时间段被预约的座位列表:', this.current_time_seat_list);
        }
      } catch (error) {
        console.error('获取当前时间段被预约的座位列表失败:', error);
      }
    },
  },
  beforeDestroy() {
    // 清理事件
    if (this.dragState.dragHandler) {
      window.removeEventListener('mousemove', this.dragState.dragHandler);
    }
  },
  async mounted() {
    // 初始化时获取所有桌子信息和座位信息
    this.getDeskList();
    this.getSeatList();
    // 获取当前半小时时间段被预约的座位号列表
    await this.fetchOccupiedSeats();
  },  
};
</script>

<style scoped>
/* 画布容器：限定可视区域，超出部分隐藏 */
#canvas-container {
  width: 100%;
  /* 占满屏幕宽度 */
  height: 100%;
  /* 占满屏幕高度 */
  overflow: hidden;
  /* 隐藏超出容器的画布部分 */
  position: relative;
  /* 作为画布的定位父级 */
  background-color: #f8f9fa;
  /* 浅灰色背景，区分画布 */
  cursor: grab;
  /* 鼠标样式：可拖动 */
}

#canvas-container:active {
  cursor: grabbing;
  /* 拖动时的鼠标样式 */
}

/* 画布主体：可拖动缩放的空白画布 */
#canvas-content {
  width: 5000px;
  /* 画布原始宽度（可自定义） */
  height: 3000px;
  /* 画布原始高度（可自定义） */
  position: absolute;
  /* 绝对定位，支持translate偏移 */
  top: 0;
  left: 0;
  background-color: #f4f5e4;
  /* 画布白色背景 */
  border: 1px solid #dee2e6;
  /* 浅灰色边框，标识画布范围 */
  transition: transform 0.05s ease;
  /* 拖动/缩放过渡，更丝滑 */
}
</style>