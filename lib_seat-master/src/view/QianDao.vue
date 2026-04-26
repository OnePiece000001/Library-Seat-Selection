<template>
  <div class="seat-form">
    <h3>个人账号</h3>
    
    <!-- 座位名称输入框（自动填充，只读） -->
    <div class="form-item">
      <label>座位名称：</label>
      <input 
        type="text" 
        v-model="seatName" 
        readonly 
        placeholder="自动获取的座位信息"
      >
    </div>

    <!-- 手机号输入框 -->
    <div class="form-item">
      <label>手机号码：</label>
      <input 
        type="tel" 
        v-model="phoneNumber" 
        placeholder="请输入注册系统的手机号"
        maxlength="11"
      >
    </div>

    <button @click="qianDao" class="submit-btn">确认签到</button>
  </div>
</template>

<script>
import { qianDao } from '@/api/dept';

export default {
  name: "QianDao",
  data() {
    return {
      seatName: "", // 座位名称
      phoneNumber: "" // 手机号
    };
  },
  mounted() {
    this.getSeatNameFromQuery();
  },
  // 添加路由守卫，当路由参数变化时重新获取
  beforeRouteUpdate(to, from, next) {
    console.log('路由更新:', to);
    this.seatName = to.query.seat_name || "未获取到座位信息";
    next();
  },
  methods: {
    getSeatNameFromQuery() {
      // ✅ Vue2 正确获取 URL 中的 seat_name 参数
      console.log('当前路由信息:', this.$route);
      console.log('当前路由路径:', this.$route.path);
      console.log('当前查询参数:', this.$route.query);
      console.log('当前完整路径:', this.$route.fullPath);
      console.log('当前页面URL:', window.location.href);
      console.log('当前页面hash:', window.location.hash);

      const seatName = this.$route.query.seat_name;
      console.log('seat_name原始值:', seatName);
      console.log('seat_name类型:', typeof seatName);

      // 处理 seat_name 为 null 字符串或空值的情况
      if (!seatName || seatName === 'null' || seatName === 'undefined' || seatName === '`') {
        this.seatName = "未获取到座位信息";
        this.$message.error('二维码链接中座位信息为空，请重新生成二维码');
      } else {
        this.seatName = seatName;
      }
    },
    async qianDao() {
      if (!this.phoneNumber) {
        alert("请填写手机号码！");
        return;
      }
      try {
        const res = await qianDao(this.seatName, this.phoneNumber);
        if (res.code === 200) {
          alert("签到成功！");
        } else {
          alert(res.msg || "签到失败！");
        }
      } catch (error) {
        alert("签到失败，请稍后重试！");
      }
    }
  }
};
</script>

<style scoped>
.seat-form {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #eee;
  border-radius: 8px;
  text-align: center;
}

.seat-form h3 {
  margin-bottom: 20px;
  font-size: 24px;
  font-weight: bold;
}
.form-item {
  margin-bottom: 20px;
}
label {
  display: inline-block;
  width: 80px;
  font-weight: bold;
}
input {
  padding: 8px 12px;
  width: 250px;
  border: 1px solid #ccc;
  border-radius: 4px;
  outline: none;
}
input[readonly] {
  background-color: #f5f5f5;
  cursor: not-allowed;
}
.submit-btn {
  padding: 10px 30px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>