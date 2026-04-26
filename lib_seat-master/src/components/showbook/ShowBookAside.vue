<template>
  <div>
    <el-menu
      default-active="1"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose">

      <el-menu-item index="1" @click="handleTop50Click">
        <i class="el-icon-menu"></i>
        <span slot="title">热度Top</span>
      </el-menu-item>

      <!-- 动态渲染书籍类型导航标签 -->
      <el-menu-item
        v-for="(type, index) in bookTypes"
        :key="index"
        :index="(index + 2).toString()"
        @click="handleMenuItemClick(type)">
        <i class="el-icon-menu"></i>
        <span slot="title">{{ type }}</span>
      </el-menu-item>

      
    </el-menu>
  </div>
</template>

<script>
export default {
  props: {
    bookTypes: {
      type: Array,
      default: () => []
    }
  },
  methods: {
     handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      handleMenuItemClick(type) {
        // 发送书籍类型给父组件
        this.$emit('select-book-type', type);
      },
       handleTop50Click() {
        // 触发Top50特殊事件
        this.$emit('select-top50');
      }
  },
}
</script>

<style scoped>

</style>