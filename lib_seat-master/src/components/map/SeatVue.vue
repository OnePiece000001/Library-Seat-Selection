<template>
  <div
    class="seat"
    @mousedown="handleSeatMouseDown"
    @click="handleSeatClick"
    :style="{
      top: seatTop + 'px',
      left: seatLeft + 'px',
      cursor: usertype ? 'move' : 'pointer',
      backgroundColor: seat.have_people ? 'red' : '#fff'
    }"
  >
    {{ seat.seat_name }}
    <div v-if="usertype" class="delete-seat" @click="handleDeleteSeat">
      <svg t="1767937529851" class="icon" viewBox="0 0 1084 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5260" width="14" height="14"><path d="M1075.367897 60.004888c-14.219144 38.783658-283.168172 251.346076-453.54781 410.476973 158.523547 173.110163 254.847272 325.580622 275.308344 374.235 24.447128 58.264804 20.450865 141.701473-38.850008 176.070358-59.295768 34.491376-260.782973-217.824419-342.887555-321.283235-13.188179-18.884003-28.918043-38.242657-46.531203-57.907539-116.958326 151.148578-217.15072 318.205653-249.769007 345.429239-45.081729 37.696552-123.251292 43.928273-196.827449 18.450181-73.637403-25.539338 44.285538-160.279249 224.117385-404.418781a5041.844982 5041.844982 0 0 1 80.777598-94.991637C189.866388 387.290174 38.416687 277.227061 17.123698 225.913611-15.851854 146.774329 12.463942 8.51791 41.024721 6.404943 139.828885-0.918987 285.465376 137.031204 486.773948 333.072671 759.657995 55.952483 912.735804 1.745188 955.694358 0.530487l0.061245 0.061246c115.320011-6.537948 125.547995 43.310715 119.612294 59.413155z" fill="#ffffff" p-id="5261"></path></svg>
    </div>

    <!-- 预约对话框 -->
    <el-dialog
      title="座位预约"
      :visible="bookingDialogVisible"
      @update:visible="bookingDialogVisible = $event"
      width="400px"
      :close-on-click-modal="false"
      :append-to-body="true"
      :modal-append-to-body="true"
    >
      <div class="booking-info">
        <p><strong>座位名称：</strong>{{ seat.seat_name }}</p>
        <p><strong>楼层：</strong>{{ floorNo }}</p>

      </div>
      <el-form :model="bookingForm" label-width="100px">
        <el-form-item label="开始日期">
          <el-date-picker
            v-model="bookingForm.start_date"
            type="date"
            placeholder="选择日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            :picker-options="startDatePickerOptions"
            :clearable="false"
            @change="handleStartDateChange"
          />
        </el-form-item>
        <el-form-item label="开始时间">
          <el-select v-model="bookingForm.start_time" placeholder="选择时间" @change="handleStartTimeChange">
            <el-option
              v-for="time in timeOptions"
              :key="time"
              :label="time"
              :value="time"
              :disabled="isTimeDisabled(bookingForm.start_date, time, 'start')"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker
            v-model="bookingForm.end_date"
            type="date"
            placeholder="选择日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            :picker-options="endDatePickerOptions"
            :clearable="false"
            @change="handleEndDateChange"
          />
        </el-form-item>
        <el-form-item label="结束时间" :error="timeError">
          <el-select v-model="bookingForm.end_time" placeholder="选择时间" @change="handleEndTimeChange">
            <el-option
              v-for="time in timeOptions"
              :key="time"
              :label="time"
              :value="time"
              :disabled="isTimeDisabled(bookingForm.end_date, time, 'end')"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeBookingDialog">取 消</el-button>
        <el-button type="primary" @click="confirmBooking">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import dragZoom from '@/utils/dragZoom';
  import { mapState } from 'vuex';
  import { addbooking, getbookingtime, bookingmessage } from '@/api/dept';

  export default {
    props: {
      seat: {
        type: Object,
        required: true
      },
      scale:{
          type: Number,
          default: 1
      },
      floorNo: {
        type: String,
        required: true
      }
    },

    data() {
      return {
        seatLeft: 100,
        seatTop: 100,
        isDragging: false,
        dragState: {},
        seatSize: { width: 50, height: 50 },
        canvasSize: { width: 5000, height: 3000 },
        bookingDialogVisible: false,
        bookingForm: {
          start_date: '',
          start_time: '',
          end_date: '',
          end_time: ''
        },
        timeError: '',
        // 当前这个座位已经被站用的未来的时间段对象数组[{start_time:'2026-01-01 08:00',end_time:'2026-01-01 10:00'}，{start_time:'2026-01-01 10:30',end_time:'2026-01-01 12:00'}]
        time_period_list: [],
      }
    },
    computed: {
      ...mapState(['usertype','username','userPhone']),
      // 开始日期选择器配置
      startDatePickerOptions() {
        return {
          disabledDate: (date) => {
            const now = new Date();
            now.setHours(0, 0, 0, 0);
            const threeDaysLater = new Date(now);
            threeDaysLater.setDate(now.getDate() + 3);
            // 禁用今天之前和三天之后的日期
            return date < now || date > threeDaysLater;
          }
        };
      },
      // 结束日期选择器配置
      endDatePickerOptions() {
        return {
          disabledDate: (date) => {
            const now = new Date();
            now.setHours(0, 0, 0, 0);
            const threeDaysLater = new Date(now);
            threeDaysLater.setDate(now.getDate() + 3);
            // 禁用今天之前、三天之后、以及早于开始日期的日期
            if (date < now || date > threeDaysLater) return true;
            if (this.bookingForm.start_date) {
              const startDate = new Date(this.bookingForm.start_date);
              startDate.setHours(0, 0, 0, 0);
              return date < startDate;
            }
            return false;
          }
        };
      },
      // 时间选项（整点和半点）
      timeOptions() {
        const options = [];
        for (let hour = 8; hour <= 22; hour++) {
          const hourStr = String(hour).padStart(2, '0');
          options.push(`${hourStr}:00`);
          if (hour < 22) {
            options.push(`${hourStr}:30`);
          }
        }
        return options;
      }
    },
    methods: {
      handleSeatMouseDown(event) {
        if (this.usertype) {
          this.startSeatDrag(event);
        }
      },

      startSeatDrag(event) {
        event.stopPropagation();

        this.dragState = dragZoom.initDrag({
          isDragging: this.isDragging,
          startX: event.clientX,
          startY: event.clientY,
          currentTarget: event.target,
          top: this.seatTop,
          left: this.seatLeft,
          scale: this.scale || 1
        });

        this.isDragging = true;

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
            type: 'seat',
            canvasSize: this.canvasSize,
            seatSize: this.seatSize
          });

          if (result) {
            this.seatLeft = result.left;
            this.seatTop = result.top;
          }
        };

        this.dragState.dragHandler = dragHandler;
        window.addEventListener('mousemove', dragHandler);
        window.addEventListener('mouseup', this.stopSeatDrag);
      },

      stopSeatDrag() {
        const stopResult = dragZoom.stopDrag(this.dragState, this.stopSeatDrag);
        this.isDragging = stopResult.isDragging;
        this.dragState = {};

        this.$emit('seat-drag-stop', {
          id: this.seat.id,
          x: this.seatLeft,
          y: this.seatTop
        });
      },

      handleSeatClick() {
        console.log('座位被点击，usertype:', this.usertype);
        if (!this.usertype) {
          console.log('普通用户，显示预约对话框');
          this.showBookingDialog();
        } else {
          console.log('管理员用户，不显示对话框');
        }
      },

      async showBookingDialog() {
        console.log('showBookingDialog 被调用');

        // 先检查用户是否已有预约
        const hasExistingBooking = await this.checkUserBooking();
        if (hasExistingBooking) {
          // 用户已有预约，弹出确认对话框
          this.$confirm('您已有预约座位，是否继续预约？', '提示', {
            confirmButtonText: '继续预约',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            // 用户选择继续，显示预约对话框
            this.openBookingDialog();
          }).catch(() => {
            // 用户选择取消，结束流程
            console.log('用户取消预约');
          });
        } else {
          // 用户没有预约，直接显示预约对话框
          this.openBookingDialog();
        }
      },

      // 检查用户是否已有预约
      async checkUserBooking() {
        try {
          if (!this.userPhone) {
            console.error('用户未登录，无法检查预约信息');
            return false;
          }
          const res = await bookingmessage(this.userPhone);
          if (res.code === 200 && res.data && res.data.length > 0) {
            console.log('用户已有预约:', res.data);
            return true;
          }
          console.log('用户没有预约');
          return false;
        } catch (error) {
          console.error('检查用户预约信息失败:', error);
          return false;
        }
      },

      // 打开预约对话框
      openBookingDialog() {
        this.bookingForm = {
          start_date: '',
          start_time: '',
          end_date: '',
          end_time: ''
        };
        this.timeError = '';

        // 调用接口获取该座位已被占用的时间段
        this.fetchSeatTimePeriodList();

        this.$set(this, 'bookingDialogVisible', true);
        console.log('bookingDialogVisible 设置为:', this.bookingDialogVisible);
      },

      // 获取座位已被占用的时间段列表
      async fetchSeatTimePeriodList() {
        try {
          console.log('获取座位时间段列表，seat_name:', this.seat.seat_name);
          const res = await getbookingtime(this.seat.seat_name);
          if (res.code === 200) {
            this.time_period_list = res.data || [];
            console.log('获取到的座位时间段列表:', this.time_period_list);
          } else {
            console.error('获取座位时间段列表失败:', res.msg);
            this.time_period_list = [];
          }
        } catch (error) {
          console.error('获取座位时间段列表异常:', error);
          this.time_period_list = [];
        }
      },

      handleStartDateChange(val) {
        console.log('开始日期改变:', val);
        // 如果结束日期未选择，自动设置为开始日期
        if (val && !this.bookingForm.end_date) {
          this.bookingForm.end_date = val;
        }
      },

      handleEndDateChange(val) {
        console.log('结束日期改变:', val);
        this.timeError = '';

        if (!val) return;

        // 如果结束日期早于开始日期，提示错误
        if (this.bookingForm.start_date && val < this.bookingForm.start_date) {
          this.$message.warning('结束日期必须大于等于开始日期');
          this.bookingForm.end_date = '';
          return;
        }

        // 如果结束日期等于开始日期，且结束时间已选择，需要检查时间顺序
        if (val === this.bookingForm.start_date && this.bookingForm.end_time && this.bookingForm.start_time) {
          if (this.bookingForm.end_time <= this.bookingForm.start_time) {
            this.$message.warning('结束时间必须晚于开始时间');
            this.bookingForm.end_time = '';
          }
        }
      },

      handleStartTimeChange(val) {
        console.log('开始时间改变:', val);
        this.timeError = '';

        if (!val || !this.bookingForm.start_date) return;

        const startDateTime = `${this.bookingForm.start_date} ${val}`;

        // 检查时间是否与已占用时间段冲突
        if (this.isTimeConflict(startDateTime, this.bookingForm.end_date && this.bookingForm.end_time ? `${this.bookingForm.end_date} ${this.bookingForm.end_time}` : startDateTime)) {
          this.timeError = '该时间段已被占用';
          this.$message.warning('该时间段已被占用，请选择其他时间');
          this.bookingForm.start_time = '';
          return;
        }

        // 检查结束时间是否需要重置
        if (this.bookingForm.end_date && this.bookingForm.end_time) {
          const endDateTime = `${this.bookingForm.end_date} ${this.bookingForm.end_time}`;
          if (startDateTime >= endDateTime) {
            this.timeError = '结束时间必须晚于开始时间';
            this.$message.warning('结束时间必须晚于开始时间，请重新选择');
            this.bookingForm.end_time = '';
          }
        }
      },

      handleEndTimeChange(val) {
        console.log('结束时间改变:', val);
        this.timeError = '';

        if (!val || !this.bookingForm.end_date) return;

        const endDateTime = `${this.bookingForm.end_date} ${val}`;

        // 检查时间是否与已占用时间段冲突
        if (this.isTimeConflict(this.bookingForm.start_date && this.bookingForm.start_time ? `${this.bookingForm.start_date} ${this.bookingForm.start_time}` : endDateTime, endDateTime)) {
          this.timeError = '该时间段已被占用';
          this.$message.warning('该时间段已被占用，请选择其他时间');
          this.bookingForm.end_time = '';
          return;
        }

        // 检查结束时间是否晚于开始时间
        if (this.bookingForm.start_date && this.bookingForm.start_time) {
          const startDateTime = `${this.bookingForm.start_date} ${this.bookingForm.start_time}`;
          if (endDateTime <= startDateTime) {
            this.timeError = '结束时间必须晚于开始时间';
            this.$message.error('结束时间必须晚于开始时间');
            this.bookingForm.end_time = '';
          }
        }
      },

      // 检查某个时间是否被占用
      isTimeDisabled(date, time) {
        if (!date || this.time_period_list.length === 0) return false;

        const dateTime = `${date} ${time}`;
        const checkDate = new Date(dateTime);

        for (const period of this.time_period_list) {
          const occupiedStart = new Date(period.start_time);
          const occupiedEnd = new Date(period.end_time);

          // 检查该时间点是否在已占用时间段内（包含边界）
          // 只要选择的时间点落在已占用时间段范围内（含开始和结束时间），就禁用
          if (checkDate >= occupiedStart && checkDate <= occupiedEnd) {
            return true;
          }
        }
        return false;
      },

      // 检查时间段是否与已占用时间段冲突
      isTimeConflict(startTime, endTime) {
        if (!startTime || !endTime || this.time_period_list.length === 0) {
          return false;
        }

        const start = new Date(startTime);
        const end = new Date(endTime);

        for (const period of this.time_period_list) {
          const occupiedStart = new Date(period.start_time);
          const occupiedEnd = new Date(period.end_time);

          // 检查是否有重叠：新时间段的开始时间早于已占用结束时间，且新时间段的结束时间晚于已占用开始时间
          if (start < occupiedEnd && end > occupiedStart) {
            return true;
          }
        }
        return false;
      },

      closeBookingDialog() {
        console.log('关闭对话框');
        this.$set(this, 'bookingDialogVisible', false);
      },

      async confirmBooking() {
        if (!this.bookingForm.start_date || !this.bookingForm.start_time || !this.bookingForm.end_date || !this.bookingForm.end_time) {
          this.$message.error('请选择开始时间和结束时间');
          return;
        }

        const startDateTime = `${this.bookingForm.start_date} ${this.bookingForm.start_time}`;
        const endDateTime = `${this.bookingForm.end_date} ${this.bookingForm.end_time}`;

        if (startDateTime >= endDateTime) {
          this.timeError = '结束时间必须晚于开始时间';
          this.$message.error('结束时间必须晚于开始时间，请重新选择');
          return;
        }

        // 再次检查时间冲突
        if (this.isTimeConflict(startDateTime, endDateTime)) {
          this.timeError = '该时间段已被占用';
          this.$message.error('该时间段已被占用，请选择其他时间');
          return;
        }

        try {
          console.log('Vuex store 中的 userPhone:', this.userPhone);
          console.log('Vuex store 中的 username:', this.username);

          if (!this.userPhone) {
            this.$message.error('用户未登录，请先登录');
            console.error('userPhone 为空，无法预约');
            return;
          }

          const bookingData = {
            user_phone: this.userPhone,
            seat: this.seat.seat_name,
            floor: this.floorNo,
            start_time: startDateTime,
            end_time: endDateTime
          };

          console.log('========== 预约接口调用前数据 ==========');
          console.log('传递的数据对象:', bookingData);
          console.log('各字段类型:');
          console.log('  user_phone:', typeof bookingData.user_phone, '-', bookingData.user_phone);
          console.log('  seat:', typeof bookingData.seat, '-', bookingData.seat);
          console.log('  floor:', typeof bookingData.floor, '-', bookingData.floor);
          console.log('  start_time:', typeof bookingData.start_time, '-', bookingData.start_time);
          console.log('  end_time:', typeof bookingData.end_time, '-', bookingData.end_time);
          console.log('=======================================');

          const res = await addbooking(bookingData);
          console.log('后端响应:', res);

          if (res && res.code === 200) {
            this.$message.success('预约成功！');
            this.closeBookingDialog();
          } else {
            this.$message.error(res?.message || '预约失败，请重试');
          }
        } catch (error) {
          console.error('预约失败:', error);
          this.$message.error('预约失败，请稍后重试');
        }
      },

      handleDeleteSeat(event) {
        event.stopPropagation();
        this.$emit('seat-delete', this.seat.seat_name);
      }
    },
    mounted() {
      if (this.seat) {
        this.seatLeft = parseInt(this.seat.seat_x) || 0;
        this.seatTop = parseInt(this.seat.seat_y) || 0;
      }
    },
    beforeDestroy() {
      if (this.dragState.dragHandler) {
        window.removeEventListener('mousemove', this.dragState.dragHandler);
      }
      window.removeEventListener('mouseup', this.stopSeatDrag);
    }
  }
</script>

<style scoped>
  .delete-seat{
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
  .seat:hover .delete-seat{
    display: block;
  }
  .seat{
    user-select:none;
    text-align: center;
    line-height: 50px;
    width: 55px;
    height: 55px;
    background-color: #fff;
    border: 1px solid gray;
    border-radius: 10px;
    position: absolute;
  }
  .booking-info {
    margin-bottom: 20px;
    padding: 10px;
    background-color: #f5f7fa;
    border-radius: 4px;
  }
  .booking-info p {
    margin: 5px 0;
  }
</style>
