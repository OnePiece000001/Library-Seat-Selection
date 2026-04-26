import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter)

import LoginVue from "@/view/LoginVue.vue";
import ShowAll from "@/view/ShowAll.vue";
import BookingManage from "@/pages/ShowAll/BookingManage.vue";
import BookingMessage from "@/pages/ShowAll/BookingMessage.vue";
import DataReveal from "@/pages/ShowAll/DataReveal.vue";
import LibMap from "@/pages/ShowAll/LibMap.vue";
import ManageBook from "@/pages/ShowAll/ManageBook.vue";
import UserMessage from "@/pages/ShowAll/UserMessage.vue";
import RecommendBook from "@/pages/ShowAll/RecommendBook.vue";
import SelectBook from "@/pages/ShowAll/SelectBook.vue";
import ManageBook2 from "@/pages/ShowAll/ManageBook2.vue";
import BookBorrowManage from "@/pages/ShowAll/BookBorrowManage.vue";
import BookBorrow from "@/pages/ShowAll/BookBorrow.vue";
import ShowBook from "@/pages/ShowAll/ShowBook.vue";
import BorrowCard from "@/pages/ShowAll/BorrowCard.vue";
import VirtualScreen from "@/view/VirtualScreen.vue";
import SeatQRcode from "@/pages/ShowAll/SeatQRcode.vue";
import QianDao from "@/view/QianDao.vue";
import HistoryBookingMsg from "@/pages/ShowAll/HistoryBookingMsg.vue";
import ShouCang from "@/pages/ShowAll/ShouCang.vue";

export default new VueRouter({
    mode:"hash",
    routes:[
        {
            path:'/',
            component:LoginVue
        },
        {
            path:'/ShowAll',
            name:'ShowAll',
            component:ShowAll,
            redirect:'/ShowAll/DataReveal',
            children:[
                {
                    // 收藏书籍 学生
                    name:'ShouCang',
                    path:'ShouCang',
                    component:ShouCang
                },
                {
                    // 详情 借阅 学生
                    name:'BorrowCard',
                    path:'BorrowCard',
                    component:BorrowCard
                },
                {
                    // 检索书籍列表 用户
                    name:'SelectBook',
                    path:'SelectBook',
                    component:SelectBook
                },
                {
                    // 预约管理 管理员
                    name:'BookingManage',
                    path:'BookingManage',
                    component:BookingManage
                },
                {
                    //预约信息 学生
                    path:'BookingMessage',
                    component:BookingMessage
                },
                {
                    //历史预约信息 学生
                    path:'HistoryBookingMsg',
                    component:HistoryBookingMsg
                },
                {
                    //数据信息 学生 管理员
                    path:'DataReveal',
                    component:DataReveal
                },
                {
                    //选座座位设置 学生 管理员
                    path:'LibMap',
                    component:LibMap
                },
                {
                    //图书管理 管理员
                    name:'ManageBook',
                    path:'ManageBook',
                    component:ManageBook
                },
                {
                    //图书管理 管理员
                    name:'ManageBook2',
                    path:'ManageBook2',
                    component:ManageBook2
                },
                {
                    //用户信息  管理员
                    path:'UserMessage',
                    component:UserMessage
                },
                {
                    //图书推荐  学生
                    path:'RecommendBook',
                    component:RecommendBook
                },
                {
                    //图书借阅管理  管理员
                    path:'BookBorrowManage',
                    component:BookBorrowManage
                },
                {
                    //图书借阅信息  学生
                    path:'BookBorrow',
                    component:BookBorrow
                },
                {
                    //图书详情  学生
                    path:'ShowBook',
                    component:ShowBook
                },
                {
                    path:'SeatQRcode',
                    component:SeatQRcode
                }
            ]
        },
        {
            path:'/VirtualScreen',
            component:VirtualScreen
        },
        {
            path:'/QianDao',
            component:QianDao
        }
    ]
})