<template>
    <div>
        <el-menu 
            default-active="/ShowAll/DataReveal" 
            class="el-menu-vertical-demo" 
            router
        >   
            <!-- 共有 -->
            <el-menu-item index="/ShowAll/DataReveal">
                <span slot="title">图书馆数据信息</span>
            </el-menu-item>
            <el-menu-item index="/ShowAll/LibMap">
                <span v-if="usertype" slot="title">座位设置</span>
                <span v-if="!usertype" slot="title">座位设置</span>
            </el-menu-item>
            
            
            <!-- 管理员 -->
            <!-- <el-menu-item v-if="usertype" index="/ShowAll/UserMessage">
                <span slot="title">用户信息</span>
            </el-menu-item> -->
            <el-menu-item v-if="usertype" index="/ShowAll/ManageBook">
                <span slot="title">图书管理</span>
            </el-menu-item>
            
            <el-menu-item v-if="usertype" index="/ShowAll/BookingManage">
                <span slot="title">座位预约管理</span>
            </el-menu-item>
            
            
                
            <el-menu-item v-if="usertype" index="/ShowAll/BookBorrowManage">
                <span slot="title">图书借阅管理</span>
            </el-menu-item>
 
            <!-- 学生用户 -->
            <el-menu-item v-if="!usertype" index="/ShowAll/RecommendBook">
                <span slot="title">图书推荐</span>
            </el-menu-item>
            <el-menu-item v-if="!usertype" index="/ShowAll/ShowBook">
                <span slot="title">图书详情</span>
            </el-menu-item>
            
            <el-menu-item v-if="!usertype" index="/ShowAll/BookingMessage">
                <span slot="title">座位预约信息</span>
            </el-menu-item>
            <el-menu-item v-if="!usertype" index="/ShowAll/HistoryBookingMsg">
                <span slot="title">历史预约信息</span>
            </el-menu-item>
            <el-menu-item v-if="!usertype" index="/ShowAll/BookBorrow">
                <span slot="title">图书借阅信息</span>
            </el-menu-item>
            <el-menu-item v-if="!usertype" index="/ShowAll/ShouCang">
                <span slot="title">收藏藏书籍</span>
            </el-menu-item>
            <el-menu-item index="/VirtualScreen">
                <span slot="title">数据展示大屏</span>
            </el-menu-item>
            <el-menu-item index="/ShowAll/SeatQRcode">
                <span slot="title">座位二维码</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
    computed:{
        ...mapState(['usertype'])
    }
}
</script>

<style></style>