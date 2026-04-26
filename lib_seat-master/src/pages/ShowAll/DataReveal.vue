<template>
  <div class="mpage">
    <el-row>
      <el-col :span="8"  style="padding-right:10px">

        <el-card class="box-card" >
        
          <div class="user">
              <!-- aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa -->
              <div>
                  <img :src="avatarUrl" alt="头像" />
              </div>
              <!-- aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa -->
              <div  class="userinfo">
                  <p class="name">{{ uname }}</p>
                  <p class="access">{{ shenfen }}</p> 
              </div>
          </div>

          <div class="logininfo"> 
              <p>上次登陆时间：<span>{{ loginTime }}</span></p>
              <p>上次登陆地点：<span>{{ loginLocation }}</span></p>
          </div>

        </el-card>


        <el-card style="margin-top:20px;height:400px">
          <el-table :data="tableData" style="width: 100%" margin-top>
            <el-table-column v-for="(val,key) in tableLabel" :prop="key" :label="val" :key="key" />
          </el-table>
        </el-card>

        <el-card style="margin-top:20px;height:130px">
          <div class="studytime">
            <div class="studytime-icon">
              <i class="el-icon-time"></i>
            </div>
            <div class="studytime-content">
              <p class="studytime-label">{{ studyTimeLabel }}</p>
              <p class="studytime-value">{{ studyTimeValue }}<span class="studytime-unit">{{ studyTimeUnit }}</span></p>
            </div>
          </div>
        </el-card>


      </el-col>

      <el-col :span="16"  style="padding-left:10px">
        
        <div class="num">
            <el-card v-for="item in countData" :key="item.name" :body-style="{display:'flex',padding: 0}">
              <i class="icon" :class="`el-icon-${item.icon}`" :style="{background:item.color}"></i>
              <div class="dtail">
                <p class="price">{{item.value}}</p>
                <p class="desc">{{item.name}}</p>
              </div>
            </el-card>
        </div>
        
        <el-card style="height:310px"> 
          <div ref="echarts1" style="height:290px"></div>
        </el-card>

        <div class="graph">
          <el-card style="height:330px"> 
            <div ref="echarts2" style="height:330px"></div>
          </el-card>
          <el-card style="height:330px">
            <div  style="height:330px">
              <div ref="echarts3" style="height:290px"></div>
            </div>
          </el-card>
        </div>

      </el-col>
    </el-row>
  </div>
</template>
<script>
import * as echarts from "echarts";
import { mapState } from 'vuex';


export default {
  
   mounted() {
    this.getecharts1();
    this.getecharts2();
    this.getecharts3();

  },
  methods: {
    
    getecharts1() {
      let myecharts1 = echarts.init(this.$refs.echarts1);
      const option = {
      title: {
        text: '图书借阅趋势'
      },
      tooltip: {},
      legend: {
        data: ['文学', '历史', '科技', '艺术', '教育']
      },
      xAxis: {
        data: ['1月', '2月', '3月', '4月', '5月', '6月']
      },
      yAxis: {
        name: '借阅数量'
      },
      
      series: [
        { name: '文学', type: 'line', data: [120, 132, 101, 134, 90, 230] },
        { name: '历史', type: 'line', data: [82, 93, 90, 94, 100, 180] },
        { name: '科技', type: 'line', data: [150, 172, 181, 194, 140, 210] },
        { name: '艺术', type: 'line', data: [62, 73, 80, 84, 90, 120] },
        { name: '教育', type: 'line', data: [100, 113, 120, 114, 100, 150] }
      ]
    }
    myecharts1.setOption(option);
    },
    getecharts2(){
      let myecharts2=echarts.init(this.$refs.echarts2)
      const option= {

      tooltip: {},
      legend: {
        data: ['座位预约', '图书借阅']
      },
      xAxis: { 
        data: ['周一', '周二', '周三', '周四', '周五', '周六','周日']
      },
      yAxis: {
        name: '数量'
      },
      color:["#2ec7c9","#b6a2de"],
      series:[{
        name:"座位预约",
        data:[45, 52, 49, 60, 55, 70, 65],
        type:"bar"
    
      },{
        name:"图书借阅",
        data:[30, 35, 40, 25, 45, 55, 50],
        type:"bar"
      }] 
    }
    myecharts2.setOption(option);
    },
    getecharts3(){
        let myecharts3=echarts.init(this.$refs.echarts3)
        const option={
          title: {
            text: '图书分类占比',
            left: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{b}: {c} ({d}%)'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series:{
            data:[
              { value: 30, name: '文学' },
              { value: 20, name: '历史' },
              { value: 25, name: '科技' },
              { value: 10, name: '艺术' },
              { value: 15, name: '教育' }
            ],
            type:"pie"
          }
        }
          myecharts3.setOption(option);
    },
    // handleChangeAvatar() {
    //   if (confirm('是否更换头像？')) {
    //     this.$refs.fileInput.click();
    //   }
    // },
    // handleFileChange(event) {
    //   const file = event.target.files[0];
    //   if (file) {
    //     alert(file);
    //     alert('头像上传成功');
        // const formData = new FormData();
        // formData.append('avatar', file);
        // formData.append('username', this.username);

        // axios.post('/api/upload-avatar', formData)
        //   .then(response => {
            // if (response.data.success) {
            //   // 假设后端返回图片的新 URL
            //   this.avatarUrl = response.data.avatarUrl;
            // }
        //     console.log('头像上传成功', response.data);
        //   })
        //   .catch(error => {
        //     console.error('头像上传失败', error);
        //   });
      // }
    // }
  },
   data(){        return{
          // 登录信息
          loginTime: '2025-3-18',
          loginLocation: '潍坊市寒亭区',
          // 学习时长信息
          studyTimeLabel: '今日学习时长',
          studyTimeValue: '3',
          studyTimeUnit: '小时',
          // 图书分类统计数据
          tableData: [
            { name: '文学', todayBorrow: 12, monthBorrow: 156, totalBorrow: 1234 },
            { name: '历史', todayBorrow: 8, monthBorrow: 98, totalBorrow: 876 },
            { name: '科技', todayBorrow: 15, monthBorrow: 203, totalBorrow: 1567 },
            { name: '艺术', todayBorrow: 6, monthBorrow: 78, totalBorrow: 654 },
            { name: '教育', todayBorrow: 10, monthBorrow: 134, totalBorrow: 987 }
          ],
          tableLabel:{
             name:"图书分类",
             todayBorrow:"今日借阅",
             monthBorrow:"本月借阅",
             totalBorrow:"总借阅"
          },
          // 图书馆统计数据
          countData: [
            { name: '今日已预约座位', value: 120, icon: 'warning', color: '#ff9800' },
            { name: '今日未预约座位', value: 80, icon: 'success', color: '#4caf50' },
            { name: '今日已借阅图书', value: 56, icon: 'book', color: '#2196f3' },
            { name: '本月已借阅图书', value: 890, icon: 'read', color: '#9c27b0' },
            { name: '本月已收藏图书', value: 234, icon: 'star-on', color: '#ff5722' },
            { name: '总藏书量', value: 12500, icon: 'collection', color: '#795548' }
          ]
        }

    },
    computed: {
      ...mapState(['username', 'userpicture', 'usertype']),
      avatarUrl() {
        return this.userpicture || require('@/assets/touxiang.png');
      },
      uname() {
        return this.username || '未知用户';
      },
      shenfen() {
        return this.usertype ? '管理员' : '普通用户';
      }
    }
}
</script>
<style lang="less" scoped>
.mpage{
  position: absolute;
  padding: .3rem 0;
}
 .user{
    padding-bottom: 20px;
    margin-bottom: 20px;
     border-bottom: 1px solid #ccc;
    display: flex;
    align-items: center;
    img{
        margin-right: 40px;
        height: 150px;
        width: 150px;
        border-radius: 50%;
    }
   .userinfo{
    .name{
       font-size: 32px;
       margin-bottom: 10px;
         }
    .access{
         
        color: #999999;
    }
   }
 }
 .logininfo{
    p{
     line-height: 28px;
     font-size: 14px;
     color: #999999;
        span{
            color:#666666;
            margin-left:60px;

        }
    }
   }
   .num{
    display: flex;
    flex-wrap: wrap ;
    justify-content: space-between;
    .icon{
      width: 80px;
      height: 80px;
      font-size: 30px;
      text-align: center;
      line-height: 80px;
      color: #fff;
    }
     .el-card{
      width: 32%;
      margin-bottom: 20px;
      padding: 0;
    }
   }
   .dtail{
    margin-left: 15px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    .price{
      font-size: 30px;
      margin-bottom: 10px;
      height: 30px;
      line-height: 30px ;
    }
    .desc{
      font-size: 14px;
      color:#999;
      text-align: center;
    }
   }
   .graph{
    margin-top: 20px;
    display: flex;
    justify-content: space-between;
    .el-card {
      width: 48%;
    }
   }
   .studytime {
     display: flex;
     align-items: center;
     justify-content: center;
     height: 100%;
     padding: 0 20px;
     .studytime-icon {
       width: 60px;
       height: 60px;
       background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
       border-radius: 50%;
       display: flex;
       align-items: center;
       justify-content: center;
       margin-right: 20px;
       i {
         font-size: 28px;
         color: #fff;
       }
     }
     .studytime-content {
       text-align: center;
       .studytime-label {
         font-size: 14px;
         color: #999;
         margin-bottom: 8px;
       }
       .studytime-value {
         font-size: 36px;
         font-weight: bold;
         color: #333;
         line-height: 1;
         .studytime-unit {
           font-size: 16px;
           font-weight: normal;
           color: #666;
           margin-left: 5px;
         }
       }
     }
   }

</style>