<template>
    <div id="root">
        <dv-full-screen-container class="beijing">
            <!-- <dv-scroll-ranking-board :config="config" class="content" style="width:18.2rem;height:10.3rem;color:orange;"/> -->

            <dv-border-box-11 title="图书馆数据中心">

                <div class="show">
                    
                        <div class="showa">
                            
                                <div @click="huijia" class="flchairsheng">
                                    
                                </div>
                                <div v-for="(item, index) in floorData" :key="index" class="flchairsheng">
                                    <span>{{ item.floor }}</span>
                                    <p>{{ item.remaining }}个</p>
                                </div>
                            <dv-decoration-10 style="width:100%;height:5px;position: absolute;top: 150px;" />                            
                        </div>
                    
                        <div class="showb">
                            <div class="showb1">
                                <span>排行榜前十</span>
                                <dv-scroll-ranking-board :config="config" style="width:300px;height:700px" />
                            </div>

                            <div class="showb2">
                                    <div class="showb20">
                                        <dv-flyline-chart-enhanced :config="config3" :dev="true" style="width:800px;height:500px;" />
                                    </div>
                                
                                
                                <div class="showb21">
                                  <div ref="borrowTrend" style="width:635px;height:300px" />
                                </div>
                                <div class="showb22">
                                  <div>
                                    <span>座 <br> 位<br>占<br>用<br>率</span>
                                  </div>
                                  <div>
                                      <dv-percent-pond :config="config1" style="width:590px;height:190px;" />
                                  </div>
                                </div>
                                <div ref="showb23" class="showb23">

                                </div>
                            </div>
                        </div>
                </div>
            </dv-border-box-11>
        </dv-full-screen-container>
    </div>
</template>

<script>
import axios from 'axios';
import * as echarts from 'echarts';
export default {
    data() {
        return {
          // 楼层剩余座位数据
          floorData: [
            { floor: 'floor3剩余', remaining: 124 },
            { floor: 'floor3剩余', remaining: 124 },
            { floor: 'floor4剩余', remaining: 224 },
            { floor: 'floor4剩余', remaining: 224 },
            { floor: 'floor4剩余', remaining: 224 },
            { floor: 'floor4剩余', remaining: 224 },
            { floor: 'floor4剩余', remaining: 224 },
            { floor: 'floor4剩余', remaining: 224 }
          ],
          //排行前十
            config:{
                 data: [
                    {name: "张三",value: 82},
                    {name: "里斯",value: 76},
                    {name: "王五",value: 75},
                    {name: "赵六",value: 70},
                    {name: "赵云",value: 69},
                    {name: "关飞",value: 66},
                    {name: "章鱼",value: 65},
                    {name: "张哈哈",value: 65},
                    {name: "士大夫",value: 63},
                    {name: "的风格",value: 59}
                  ],
                  rowNum:10 
            },
          //进度池
            config1:{
                value:66,
                borderWidth: 5,
                borderRadius: 10,
                borderGap: 5
            },

          //飞线图
            config3:{
              points: [
                {
                  name: '图书馆',
                  coordinate: [0.75, 0.55],
                  halo: {
                    show: true,
                  },
                  text: {
                    show: false
                  }
                },
                {
                  name: '开发',
                  coordinate: [0.2, 0.1]
                },
                {
                  name: '体育馆',
                  coordinate: [0.29, 0.1]
                },
                {
                  name: '鸢飞公寓',
                  coordinate: [0.35, 0.13]
                },
                {
                  name: '实验楼',
                  coordinate: [0.15, 0.2]
                },
                {
                  name: '白浪河餐厅',
                  coordinate: [0.25, 0.35]
                },
                {
                  name: '培真书院',
                  coordinate: [0.05, 0.38]
                },
                {
                  name: '求是书院',
                  coordinate: [0.15, 0.4],
                  halo: {
                    show: true,
                    color: '#8378ea'
                  }
                },
                {
                  name: '泰山书院',
                  coordinate: [0.22, 0.45]
                },
                {
                  name: '禄禧楼',
                  coordinate: [0.2, 0.56],
                  halo: {
                    show: true,
                    color: '#37a2da'
                  }
                },
                {
                  name: '食堂',
                  coordinate: [0.5, 0.45]
                },
                {
                  name: 'E楼',
                  coordinate: [0.5, 0.57]
                },
                {
                  name: 'D楼',
                  coordinate: [0.5, 0.7]
                },
                {
                  name: '国际交流中心',
                  coordinate: [0.5, 0.85]
                },
                {
                  name: 'C楼',
                  coordinate: [0.63, 0.85]
                },
                {
                  name: 'F楼',
                  coordinate: [0.61, 0.58]
                },
                {
                  name: '云门书院',
                  coordinate: [0.61, 0.47]
                },
                {
                  name: '松林书院',
                  coordinate: [0.83, 0.5]
                },
                {
                  name: '博雅书院',
                  coordinate: [0.93, 0.5]
                }
                ,
                {
                  name: '鸢都书院',
                  coordinate: [0.83, 0.65]
                },
                {
                  name: '翰林书院',
                  coordinate: [0.93, 0.65]
                },
                {
                  name: 'A楼',
                  coordinate: [0.91, 0.85]
                },
                {
                  name: 'B楼',
                  coordinate: [0.81, 0.85]
                }
              ],
              lines: [
                {
                  source: 'B楼',
                  target: '图书馆'
                },
                {
                  source: 'A楼',
                  target: '图书馆'
                },
                {
                  source: '翰林书院',
                  target: '图书馆'
                },
                {
                  source: '鸢都书院',
                  target: '图书馆'
                },
                {
                  source: '博雅书院',
                  target: '图书馆'
                },
                {
                  source: '开发',
                  target: '图书馆'
                },
                {
                  source: '体育馆',
                  target: '图书馆'
                },
                {
                  source: '鸢飞公寓',
                  target: '图书馆'
                },
                {
                  source: '求是书院',
                  target: '图书馆',
                  color: '#fb7293',
                  width: 2
                },
                {
                  source: '禄禧楼',
                  target: '图书馆',
                  color: '#fb7293',
                  width: 2
                },
                {
                  source: 'D楼',
                  target: '图书馆'
                },
                {
                  source: '国际交流中心',
                  target: '图书馆'
                },
                {
                  source: 'C楼',
                  target: '图书馆'
                },
                {
                  source: 'F楼',
                  target: '图书馆'
                },
                {
                  source: '云门书院',
                  target: '图书馆'
                },
                {
                  source: '松林书院',
                  target: '图书馆'
                },
                {
                  source: '实验楼',
                  target: '禄禧楼',
                  color: '#37a2da'
                },
                {
                  source: '白浪河餐厅',
                  target: '禄禧楼',
                  color: '#37a2da'
                },
                {
                  source: '培真书院',
                  target: '禄禧楼',
                  color: '#37a2da'
                },
                {
                  source: 'E楼',
                  target: '求是书院',
                  color: '#8378ea'
                },
                {
                  source: '食堂',
                  target: '求是书院',
                  color: '#8378ea'
                },
                {
                  source: '泰山书院',
                  target: '求是书院',
                  color: '#8378ea'
                }
              ],
              text: {
                show: true,
              },
              k: 0.5,
              bgImgSrc: '../assets/map1.png'
            }


        }
    },
    methods:{
      huijia(){
        this.$router.push({ name: 'ShowAll' });
      }
    },
    async created() {
        try {
//**************************************************************************************************
            // 假设接口地址为 /api/getSitingStudents
            const response = await axios.get('/api/getSitingStudents');
//**************************************************************************************************
            // 将接口返回的数据赋值给 sitingarr
            this.data = response.data;
        } catch (error) {
            console.error('获取数据失败:', error);
        }
    // 6666666666666666666666666666666666666666666666666666666666
      // 图书借阅趋势图表
      let borrowChart = echarts.init(this.$refs.borrowTrend);
      borrowChart.setOption({
        title: {
          text: '图书借阅趋势',
          textStyle: {
            color: '#ffffff',
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['借阅量'],
          textStyle: {
            color: '#ffffff'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
          axisLabel: {
            color: '#ffffff'
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            color: '#ffffff'
          }
        },
        series: [
          {
            name: '借阅量',
            type: 'line',
            data: [120, 132, 101, 134, 90, 230, 210, 180, 220, 250, 230, 280],
            smooth: true,
            lineStyle: {
              color: '#4CAF50'
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(76, 175, 80, 0.5)' },
                { offset: 1, color: 'rgba(76, 175, 80, 0.1)' }
              ])
            }
          }
        ]
      });

      // 每日到馆人数图表
      let myChart1 = echarts.init(this.$refs.showb23);
      myChart1.setOption({
        title: {
          text: '每日到馆人数',
          textStyle: {
            color: '#ffffff'
          }
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['到馆人数'],
          textStyle: {
            color: '#ffffff'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
          axisLabel: {
            color: '#ffffff'
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            color: '#ffffff'
          }
        },
        series: [
          {
            name: '到馆人数',
            type: 'line',
            data: [450, 520, 480, 510, 620, 850, 780],
            smooth: true,
            lineStyle: {
              color: '#3498db'
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(52, 152, 219, 0.5)' },
                { offset: 1, color: 'rgba(52, 152, 219, 0.1)' }
              ])
            }
          }
        ]
      })
    }
}
</script>
<style>
    #root{
        height: 100vh;
        background-image: url('../assets/bg.png');
        background-repeat: no-repeat;
        background-position: center center;
        background-size: cover; 
    }
    .beijing{
        padding-top: 10px;
        /* background-color: #000000; */
    }
    .show{
        position: absolute;
        top: 66px;
        left: 30px;
        width: 1860px;
        height: 960px;
    }
    .showa,.showb{
        width: 1860px;
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        align-items: center;
    }
    .showa{
        height: 150px;
    }
    .flchairsheng{
        display: inline-block;
        /* border-top: 2px solid #153ca0; */
        width: 180px;
        height: 120px;
        /* background-color: #06163E; */
    }
    .flchairsheng:nth-child(1){
      background-image: url('../assets/首页.png');
      background-repeat: no-repeat;
      background-position: center center;
      background-size: contain;
    }
    .flchairsheng span{
        display: block;
        font-size: 20px;
        color: #fdfdfd;
        font-weight: 1000;
        padding: 10px;
    }
    .flchairsheng p{
        display: block;
        font-size: 20px;
        color: #e05532;
        font-weight: 1000;
        margin: 20px 20px 20px 90px;
    }
    .showb{
        margin-top: 20px;
        height: 800px;
    }
    .showb1{
        /* border-top: 2px solid #153ca0; */
        width: 290px;
        padding: 40px 50px 50px 50px;
        margin: 0 5px;
        height: 710px;
        /* background-color: #06163E; */
    }
    .showb1 span{
        color: #ffffff;
        font-size: 20px;
        font-weight: 1000;
    }
    /* ****************************** */
    .showb2{
        width: 1460px;
        height: 800px;
        position: relative;
    }
.showb20,.showb21,.showb22,.showb23{
        /* background-color: #06163E; */
        margin: 0 5px 5px 10px;
        /* border-top: 2px solid #153ca0; */
    }
    .showb20{
      height: 500px;
      width: 800px;
      background-image: url("../assets/map3.png");
      background-repeat: no-repeat;
      background-size:100% 100%;
      position: absolute;
    }
    .showb21{
      width: 635px;
      height: 300px;
      position: absolute;
      right: 0;
    }
    .showb22{
      width: 635px;
      height: 190px;
      position: absolute;
      right: 0;
      top: 310px;
      display: flex;
      flex: 1;
    }
    .showb22 span{
      display: block;
      color: #ffffff;
      font-size: 25px;
      font-weight: 1000;
      margin: 10px;
    }
    .showb23{
      width: 1445px;
      height: 280px;
      position: absolute;
      bottom: 0;

    }


</style>