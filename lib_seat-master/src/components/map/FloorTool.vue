<template>
  <div class="floortool">

    <!-- <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
      <el-radio-button :label="false">展开</el-radio-button>
      <el-radio-button :label="true">收起</el-radio-button>
    </el-radio-group> -->
    <el-menu default-active="F1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" @select="handleFloorSelect"
      :collapse="usertype">
      <el-submenu index="1">
        <template slot="title">
          <svg t="1767670190169" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5879" width="25" height="25"><path d="M0 0h1024v1024H0z" fill="#FFFFFF" opacity=".01" p-id="5880"></path><path d="M896 85.333333h-192c-25.6 0-42.666667 17.066667-42.666667 42.666667v149.333333H512c-25.6 0-42.666667 17.066667-42.666667 42.666667V469.333333H320c-25.6 0-42.666667 17.066667-42.666667 42.666667v149.333333H128c-25.6 0-42.666667 17.066667-42.666667 42.666667V896c0 25.6 17.066667 42.666667 42.666667 42.666667h768c25.6 0 42.666667-17.066667 42.666667-42.666667V128c0-25.6-17.066667-42.666667-42.666667-42.666667z m-42.666667 768H170.666667v-106.666666h149.333333c25.6 0 42.666667-17.066667 42.666667-42.666667V554.666667H512c25.6 0 42.666667-17.066667 42.666667-42.666667V362.666667h149.333333c25.6 0 42.666667-17.066667 42.666667-42.666667V170.666667H853.333333v682.666666zM128 443.733333c25.6 0 42.666667-17.066667 42.666667-42.666666V307.2h106.666666c25.6 0 42.666667-17.066667 42.666667-42.666667s-17.066667-42.666667-42.666667-42.666666H170.666667V170.666667h106.666666c25.6 0 42.666667-17.066667 42.666667-42.666667s-17.066667-42.666667-42.666667-42.666667H128c-25.6 0-42.666667 17.066667-42.666667 42.666667v273.066667c0 21.333333 17.066667 42.666667 42.666667 42.666666z" p-id="5881"></path></svg>
          <span slot="title">楼层选择</span>
        </template>
        <el-menu-item-group>
          <span slot="title">楼层选择</span>
          <el-menu-item v-for="floor in Floorlist" :key="floor.floor_no" :index="floor.floor_no">
            {{ floor.floor_no }}
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu v-if="usertype" index="2">
        <template slot="title">
        <svg t="1767670436346" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8900" width="25" height="25"><path d="M1002.666667 160A74.666667 74.666667 0 0 0 928 85.333333h-832a74.453333 74.453333 0 0 0-8.533333 149.333334 32.64 32.64 0 0 0-2.133334 10.453333l-24.32 682.666667A32 32 0 0 0 91.733333 960h1.066667a32 32 0 0 0 32-30.933333L140.586667 490.666667h743.253333l15.146667 438.4a32 32 0 0 0 32 30.933333h1.066666a32 32 0 0 0 30.933334-33.066667L938.666667 233.6a74.666667 74.666667 0 0 0 64-73.6zM96 149.333333h832a10.666667 10.666667 0 0 1 0 21.333334h-832a10.666667 10.666667 0 0 1 0-21.333334z m51.413333 85.333334H490.666667v192H142.933333l6.4-180.266667a31.786667 31.786667 0 0 0-1.92-11.733333zM554.666667 426.666667v-192h320l6.613333 192z" fill="#3E3E3E" p-id="8901"></path><path d="M416 309.333333m-32 0a32 32 0 1 0 64 0 32 32 0 1 0-64 0Z" fill="#3E3E3E" p-id="8902"></path><path d="M608 309.333333m-32 0a32 32 0 1 0 64 0 32 32 0 1 0-64 0Z" fill="#3E3E3E" p-id="8903"></path></svg>
          <span slot="title">创建桌子</span>
        </template>
        <el-menu-item-group>
          <span slot="title">创建桌子</span>
          <el-menu-item index="1-1">创建横桌</el-menu-item>
          <el-menu-item index="1-2">创建纵桌</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-menu-item v-if="usertype" index="3">
        <svg t="1767670793268" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="10829" width="25" height="25"><path d="M888 976a32 32 0 0 1-64 0V744H720v136a32 32 0 0 1-64 0V744H368v136a32 32 0 0 1-64 0V744H200v232a32 32 0 0 1-64 0V536a159.2 159.2 0 0 1 40-105.6V64a40 40 0 0 1 40-40h592a40 40 0 0 1 40 40v366.4a159.2 159.2 0 0 1 40 105.6v440zM784 104a16 16 0 0 0-16-16H256a16 16 0 0 0-16 16v282.272A159.296 159.296 0 0 1 296 376h432a159.296 159.296 0 0 1 56 10.272V104z m40 456a120 120 0 0 0-120-120H320a120 120 0 0 0-120 120v120h624V560z" fill="#333333" p-id="10830"></path></svg>
        <span slot="title">创建椅子</span>
      </el-menu-item>
      <el-menu-item v-if="usertype" index="4">
        <svg t="1767670933880" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="11872" width="25" height="25"><path d="M690.816 591.36a32 32 0 0 1 42.432-15.68l174.464 80.512a49.088 49.088 0 0 1 0 89.152l-375.296 173.184a49.152 49.152 0 0 1-41.088 0l-375.296-173.248a49.088 49.088 0 0 1 0-89.088l174.4-80.512a32 32 0 0 1 26.816 58.112L172.16 700.8l339.712 156.8 339.648-156.8-145.088-66.944a32 32 0 0 1-15.616-42.496z" p-id="11873" fill="#2c2c2c" data-spm-anchor-id="a313x.search_index.0.i9.56f93a81Sotcep" class="selected"></path><path d="M491.328 131.776a49.152 49.152 0 0 1 41.088 0l44.8 20.736a32 32 0 1 1-26.752 58.048l-38.592-17.792L172.16 349.44l339.712 156.8 169.6-78.272a32 32 0 0 1 26.752 58.112L532.48 567.232a49.088 49.088 0 0 1-41.088 0L116.032 394.048a49.024 49.024 0 0 1 0-89.024l375.296-173.248z" p-id="11874" fill="#2c2c2c" data-spm-anchor-id="a313x.search_index.0.i7.56f93a81Sotcep" class="selected"></path><path d="M786.88 100.992a32 32 0 0 1 32 32v85.312h85.312a32 32 0 0 1 0 64h-85.312v85.376a32 32 0 0 1-64 0V282.304H669.44a32 32 0 0 1 0-64h85.376V132.992a32 32 0 0 1 32-32z" p-id="11875" fill="#2c2c2c" data-spm-anchor-id="a313x.search_index.0.i8.56f93a81Sotcep" class="selected"></path></svg>
        <span slot="title">添加楼层</span>
      </el-menu-item>
      <el-menu-item v-if="usertype" index="5">
        <svg t="1767800643392" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4951" width="25" height="25"><path d="M128 128v768h768.085333L896 358.997333 665.002667 128H128z m768 853.333333H128c-47.061333 0-85.333333-38.272-85.333333-85.333333V128C42.666667 80.938667 80.938667 42.666667 128 42.666667h572.373333L981.333333 323.669333V896c0 47.061333-38.272 85.333333-85.333333 85.333333z" fill="#2c2c2c" p-id="4952"></path><path d="M298.666667 426.666667h256V128H298.666667v298.666667z m277.12 85.333333H277.504C242.133333 512 213.333333 483.2 213.333333 447.829333V42.666667h426.666667v405.162666c0 35.370667-28.8 64.170667-64.213333 64.170667z" fill="#2c2c2c" p-id="4953"></path></svg>
        <span slot="title">保存</span>
      </el-menu-item>
      <el-submenu v-if="usertype" index="6">
        <template slot="title">
          <svg t="1767940192984" class="icon" viewBox="0 0 1084 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="10879" width="25" height="25"><path d="M1075.367897 60.004888c-14.219144 38.783658-283.168172 251.346076-453.54781 410.476973 158.523547 173.110163 254.847272 325.580622 275.308344 374.235 24.447128 58.264804 20.450865 141.701473-38.850008 176.070358-59.295768 34.491376-260.782973-217.824419-342.887555-321.283235-13.188179-18.884003-28.918043-38.242657-46.531203-57.907539-116.958326 151.148578-217.15072 318.205653-249.769007 345.429239-45.081729 37.696552-123.251292 43.928273-196.827449 18.450181-73.637403-25.539338 44.285538-160.279249 224.117385-404.418781a5041.844982 5041.844982 0 0 1 80.777598-94.991637C189.866388 387.290174 38.416687 277.227061 17.123698 225.913611-15.851854 146.774329 12.463942 8.51791 41.024721 6.404943 139.828885-0.918987 285.465376 137.031204 486.773948 333.072671 759.657995 55.952483 912.735804 1.745188 955.694358 0.530487l0.061245 0.061246c115.320011-6.537948 125.547995 43.310715 119.612294 59.413155z" fill="#2c2c2c" p-id="10880"></path></svg>
          <span slot="title">删除楼层</span>
        </template>
        <el-menu-item-group>
          <span slot="title">删除楼层</span>
          <el-menu-item v-for="floor in Floorlist" :key="floor.floor_no" :index="'delete-' + floor.floor_no">
            {{ floor.floor_no }}
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>

    <!-- 添加楼层弹窗 -->
    <el-dialog
      title="添加楼层"
      :visible.sync="addFloorDialogVisible"
      width="30%"
      append-to-body
    >
      <el-form :model="addFloorForm" :rules="addFloorRules" ref="addFloorForm">
        <el-form-item label="楼层名称" prop="floor_name">
          <el-input v-model="addFloorForm.floor_name" placeholder="请输入楼层名称"></el-input>
        </el-form-item>
        <el-form-item label="楼层编号" prop="floor_no">
          <el-input v-model="addFloorForm.floor_no" placeholder="请输入楼层编号（如F1、F2等）"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addFloorDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAddFloorForm">确定</el-button>
      </span>
    </el-dialog>

    <!-- <button class="button">创建横桌</button>
    <button class="button">创建纵桌</button>
    <button class="button">创建椅子</button>
    <button v-for="(item, index) in floorlist" :key="index" class="button">{{ item.floor_no }}</button>
    <button class="button">添加楼层</button> -->
  </div>
</template>

<script>
import { mapState } from 'vuex';
import { getfloor, addfloor, deletefloor } from '@/api/dept';
export default {

  data() {
    return {
      //点击添加楼层按钮，提交后刷新楼层列表
      // 静态楼层列表数据
      Floorlist: [
        // {
        //   floo_name:'研发层',
        //   floor_no:'F1',
        // },
        // {
        //   floo_name:'销售层',
        //   floor_no:'F2',
        // },
        // {
        //   floo_name:'采购层',
        //   floor_no:'F3',
        // },
        // {
        //   floo_name:'采购层',
        //   floor_no:'F4',
        // }
      ],
      // 添加楼层弹窗相关
      addFloorDialogVisible: false, // 弹窗显示状态
      addFloorForm: {
        floor_name: '', // 楼层名称
        floor_no: '' // 楼层编号
      },
      addFloorRules: {
        floor_name: [
          { required: true, message: '请输入楼层名称', trigger: 'blur' }
        ],
        floor_no: [
          { required: true, message: '请输入楼层编号', trigger: 'blur' },
          { pattern: /^F\d+$/, message: '楼层编号必须是F+数字格式（如F1、F2等）', trigger: 'blur' },
          { validator: this.checkFloorNoUnique, trigger: 'blur' }
        ]
      },
      // isCollapse: true,
      
    }
  },
  methods: {
     async getfloor() {
      const res = await getfloor();
      if (res.code === 200) {
        this.Floorlist = res.data;
      }
    },
    
    // 验证楼层编号是否唯一
    checkFloorNoUnique(rule, value, callback) {
      // 检查当前楼层列表中是否已存在相同的floor_no
      const isDuplicate = this.Floorlist.some(floor => floor.floor_no === value);
      if (isDuplicate) {
        callback(new Error('该楼层编号已存在，请输入其他编号'));
      } else {
        callback();
      }
    },
    
    // 提交添加楼层表单
    async submitAddFloorForm() {
      // 验证表单
      this.$refs.addFloorForm.validate(async (valid) => {
        if (valid) {
          try {
            // 调用addfloor API，提交表单数据
            const res = await addfloor(this.addFloorForm.floor_name, this.addFloorForm.floor_no);
            if (res.code === 200) {
              // 提交成功，刷新楼层列表
              await this.getfloor();
              // 关闭弹窗
              this.addFloorDialogVisible = false;
              // 重置表单
              this.$refs.addFloorForm.resetFields();
              // 提示成功
              this.$message.success('添加楼层成功');
            } else {
              // 提交失败，提示错误
              this.$message.error('添加楼层失败：' + res.msg);
            }
          } catch (error) {
            // 网络错误或其他异常
            console.error('添加楼层失败：', error);
            this.$message.error('添加楼层失败，请稍后重试');
          }
        } else {
          // 表单验证失败
          return false;
        }
      });
    },
    
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    handleFloorSelect(key) {
      console.log(111111111111111111111111111111111111111);
      console.log(this.Floorlist);
      console.log(111111111111111111111111111111111111111);
      // 检查是否是删除楼层操作
      if (key && key.startsWith('delete-')) {
        // 提取floor_no
        const floor_no = key.replace('delete-', '');
        // 询问用户是否确认删除
        this.$confirm('是否删除楼层？删除后楼层内的桌椅信息将同步删除', '删除楼层', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          try {
            // 调用deletefloor API
            const floor = this.Floorlist.find(f => f.floor_no === floor_no);
            console.log('floor对象:', floor);
            console.log('floor_no:', floor_no);
            console.log('floor_name:', floor ? floor.floor_name : 'floor未找到');
            if (floor) {
              const res = await deletefloor(floor_no, floor.floor_name);
              if (res.code === 200) {
                // 删除成功，刷新楼层列表
                await this.getfloor();
                // 通知父组件删除该楼层的桌子和座位
                this.$emit('delete-floor', floor_no);
                // 显示成功提示
                this.$message.success('楼层删除成功');
              } else {
                // 删除失败，显示错误提示
                this.$message.error('楼层删除失败：' + res.msg);
              }
            }
          } catch (error) {
            // 网络错误或其他异常
            console.error('楼层删除异常:', error);
            this.$message.error('楼层删除失败，请检查网络连接或稍后重试');
          }
        }).catch(() => {
          // 用户取消删除
          this.$message.info('已取消删除楼层');
        });
      } 
      // 只有当点击的是楼层菜单项时，才触发floor-select事件
      // 楼层菜单项的index以"F"开头（如F1、F2等）
      else if (key && key.startsWith('F')) {
        // 触发自定义事件，将选中的楼层传递给父组件
        this.$emit('floor-select', key);
      } else {
        // 处理功能菜单点击事件
        this.handleFunctionMenuClick(key);
      }
    },
    
    // 处理功能菜单点击事件
    handleFunctionMenuClick(key) {
      // 根据菜单index判断功能类型，并触发相应的自定义事件
      switch (key) {
        case '1-1': // 创建横桌（desk2）
          this.$emit('create-desk', { type: 'desk2' });
          break;
        case '1-2': // 创建纵桌（desk1）
          this.$emit('create-desk', { type: 'desk1' });
          break;
        case '3': // 创建椅子
          this.$emit('create-seat');
          break;
        case '4': // 添加楼层
          // 打开添加楼层弹窗
          this.addFloorDialogVisible = true;
          break;
        case '5': // 保存
          this.$emit('save-map');
          break;
        default:
          break;
      }
    }
  },
  computed:{
    ...mapState(["usertype"])     
  },
  mounted() {
    this.getfloor();
  },


}
</script>

<style scoped>
.floortool {
  user-select: none;
  margin: 10px 0 0 10px;
  min-width: 110px;
  max-height: 500px;
  position: absolute;
  z-index: 1000;
  /* border-radius: 10px; */
  /* overflow-y: auto; */
  /* scrollbar-width: none; */
  /* -ms-overflow-style: none; */
  /* border: 1px solid rgb(197, 197, 197); */
}

/* 核心：隐藏 Chrome/Safari 滚动条 */
/* 完全隐藏滚动条 */
/* .floortool::-webkit-scrollbar {
  display: none;
} */

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 170px;
  min-height: 10px;
  overflow: hidden;
}
</style>