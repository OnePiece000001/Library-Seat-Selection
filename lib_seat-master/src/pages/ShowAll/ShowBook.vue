<template>
    <div>
        <el-container style="height: 89vh;">
            <el-aside width="200px">
                <ShowBookAside :book-types="bookTypes" @select-book-type="handleSelectBookType" @select-top50="handleSelectTop50"></ShowBookAside>
            </el-aside>
            <el-main style="padding: 0px;">
                <BookCard :book-list="filteredBookList"></BookCard>
            </el-main>
        </el-container>
    </div>
</template>

<script>

import ShowBookAside from "@/components/showbook/ShowBookAside.vue";
import BookCard from "@/components/showbook/BookCard.vue";
import { getallbook, gettop50 } from "@/api/dept";
export default {
    components: {
        ShowBookAside,
        BookCard
    },
    data() {
        return {
            bookList: [
            ],
            top50: [],
            bookTypes: [],
            selectedBookType: '', // 选中的书籍类型
            filteredBookList: [] // 筛选后的书籍列表
        }
    },
    mounted() {
        this.initBookList();         //接口完善后取消注释
        this.getrecommendtop50();     //接口完善后取消注释
        this.calculateBookTypes();
        this.filteredBookList = this.top50; // 初始化显示top50书籍
        console.log(this.top50);
        
    },
    methods: {
        async getrecommendtop50() {
            try {
                // 调用接口获取书籍信息
                const res = await gettop50();
                if (res.code === 200) {
                    this.top50 = res.data;
                    this.filteredBookList = this.top50;
                }
            } catch (error) {
                console.error('获取top50书籍信息失败:', error);
            }
        },
        async initBookList() {
            try {
                // 调用接口获取书籍信息
                const res = await getallbook();
                console.log(res);
                console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                if (res.code === 200) {
                    this.bookList = res.data;
                    // 计算书籍类型数组
                    this.calculateBookTypes();
                    this.filteredBookList = this.top50; // 初始化显示top50书籍
                }
            } catch (error) {
                console.error('获取书籍信息失败:', error);
                // 使用默认数据
                this.bookList = [
                    {
                        id: 1,
                        book_cover: "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2550244442.jpg",
                        book_name: "红楼梦",
                        book_author: "曹雪芹",
                        book_no: "9787506365427",
                        stock_num: 100,
                        book_type: "古典"
                    },
                    {
                        id: 2,
                        book_cover: "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2550244442.jpg",
                        book_name: "红楼梦",
                        book_author: "曹雪芹",
                        book_no: "9787506365427",
                        stock_num: 100,
                        book_type: "科技"
                    },
                    {
                        id: 3,
                        book_cover: "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2550244442.jpg",
                        book_name: "红楼梦",
                        book_author: "曹雪芹",
                        book_no: "9787506365427",
                        stock_num: 100,
                        book_type: "玄幻"
                    }
                ];
                // 计算书籍类型数组
                this.calculateBookTypes();
                this.filteredBookList = this.top50; // 初始化显示top50书籍
            }
        },

        calculateBookTypes() {
            // 使用Set去重获取书籍类型
            const typeSet = new Set();
            this.bookList.forEach(book => {
                typeSet.add(book.book_type);
            });
            // 转换为数组
            this.bookTypes = Array.from(typeSet);
            this.filteredBookList = this.top50; // 初始化显示top50书籍
        },

        handleSelectBookType(type) {
            // 根据选中的书籍类型筛选bookList
            this.filteredBookList = this.bookList.filter(book => book.book_type === type);
        },
         handleSelectTop50() {
            // 将top50内容赋值给filteredBookList
            this.filteredBookList = this.top50;
        }
    }
}
</script>

<style scoped></style>