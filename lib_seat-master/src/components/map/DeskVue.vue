<template>
  <div 
    class="desk1"
    :class="desk.desk_type"
    @mousedown="handleDeskMouseDown"
    :style="{ 
      top: deskTop + 'px' , 
      left: deskLeft + 'px',
      cursor: usertype ? 'move' : 'default' 
    }"
  >
    {{ desk.desk_name }}
    <div v-if="usertype" class="delete-desk" @click="handleDeleteDesk">
      <svg t="1767937529851" class="icon" viewBox="0 0 1084 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5260" width="14" height="14"><path d="M1075.367897 60.004888c-14.219144 38.783658-283.168172 251.346076-453.54781 410.476973 158.523547 173.110163 254.847272 325.580622 275.308344 374.235 24.447128 58.264804 20.450865 141.701473-38.850008 176.070358-59.295768 34.491376-260.782973-217.824419-342.887555-321.283235-13.188179-18.884003-28.918043-38.242657-46.531203-57.907539-116.958326 151.148578-217.15072 318.205653-249.769007 345.429239-45.081729 37.696552-123.251292 43.928273-196.827449 18.450181-73.637403-25.539338 44.285538-160.279249 224.117385-404.418781a5041.844982 5041.844982 0 0 1 80.777598-94.991637C189.866388 387.290174 38.416687 277.227061 17.123698 225.913611-15.851854 146.774329 12.463942 8.51791 41.024721 6.404943 139.828885-0.918987 285.465376 137.031204 486.773948 333.072671 759.657995 55.952483 912.735804 1.745188 955.694358 0.530487l0.061245 0.061246c115.320011-6.537948 125.547995 43.310715 119.612294 59.413155z" fill="#ffffff" p-id="5261"></path></svg>
    </div>
  </div>
</template>

<script>
    import { mapState } from 'vuex'
    import dragZoom from '@/utils/dragZoom';
export default {
    props: {
    desk: {
      type: Object,
      required: true
    },
    scale:{
        type: Number,
        default: 1
    }
  },
    data() {
        return {
            deskLeft: 100, // 初始左偏移
          deskTop: 100,  // 初始上偏移
          isDragging: false,
          dragState: {}, // 拖动状态缓存
          deskSize: { width: 80, height: 120 }, // 桌位尺寸（与样式一致）
          canvasSize: { width: 5000, height: 3000 } // 画布尺寸（与LibMap.vue一致）
        }
    },
    methods: {
        handleDeskMouseDown(event) {
          if (this.usertype) {
            this.startDeskDrag(event);
          }
        },
        
        startDeskDrag(event) {
        // 阻止事件冒泡（避免触发画布拖动）
        event.stopPropagation();
        
        // 初始化拖动状态
        this.dragState = dragZoom.initDrag({
          isDragging: this.isDragging,
          startX: event.clientX,
          startY: event.clientY,
          currentTarget: event.target,
          top: this.deskTop,
          left: this.deskLeft,
          scale: this.scale || 1 // 从父组件获取画布缩放比例
        });
        this.isDragging = true;
        
        // 定义拖动处理函数（绑定this）
        const dragHandler = (e) => {
          const result = dragZoom.calculateDrag({
            isDragging: this.isDragging,
            startX: this.dragState.startX,
            startY: this.dragState.startY,
            clientX: e.clientX,
            clientY: e.clientY,
            prevTop: this.dragState.prevTop,
            prevLeft: this.dragState.prevLeft,
            scale: this.dragState.scale,
            type: 'seat', // 使用 'seat' 类型，因为桌子和座位的拖动逻辑相同
            canvasSize: this.canvasSize,
            seatSize: this.deskSize
          });
          if (result) {
            this.deskLeft = result.left;
            this.deskTop = result.top;
          }
        };
         this.dragState.dragHandler = dragHandler;
        window.addEventListener('mousemove', dragHandler);
        window.addEventListener('mouseup', this.stopDeskDrag);
        },
      stopDeskDrag() {
        // 停止拖动并清理事件
        const stopResult = dragZoom.stopDrag(this.dragState, this.stopDeskDrag);
        this.isDragging = stopResult.isDragging;
        this.dragState = {};
        
        // 触发自定义事件，将新位置传递给父组件
        this.$emit('desk-drag-stop', {
          id: this.desk.id,
          x: this.deskLeft,
          y: this.deskTop
        });
      },
      
      // 处理桌子删除
      handleDeleteDesk(event) {
        event.stopPropagation(); // 阻止事件冒泡
        this.$emit('desk-delete', this.desk.desk_name);
      }


    },
    mounted() {
         // 初始化位置
      if (this.desk && this.desk.desk_x && this.desk.desk_y) {
        this.deskLeft = parseInt(this.desk.desk_x) || 100;
        this.deskTop = parseInt(this.desk.desk_y) || 100;
      }
    },
    computed: {
        ...mapState(['usertype'])
    },
    beforeDestroy() {
      // 组件销毁时清理事件
      if (this.dragState.dragHandler) {
        window.removeEventListener('mousemove', this.dragState.dragHandler);
      }
      window.removeEventListener('mouseup', this.stopDeskDrag);
    }
}
</script>

<style scoped>
  .delete-desk{
    position: absolute;
    top: -10px;
    right: -10px;
    width: 20px;
    height: 20px;
    line-height: 20px;
    text-align: center;
    background-color: red;
    color: #fff;
    border-radius: 50%;
    cursor: pointer;
    display: none;
  }
  .desk1:hover .delete-desk{
    display: block;
  }
  .desk2:hover .delete-desk{
    display: block;
  }
.desk1{
    width: 80px;
    height: 120px;
}
.desk2{
    width: 120px;
    height: 80px;
}
.desk1,.desk2{
    user-select: none;
    background-color: rgb(110, 77, 28);
    border-radius: 10px;
     position: absolute; /* 添加绝对定位支持拖动 */
}
</style>