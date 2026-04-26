<template>
    <div>
        <h2>收藏书籍</h2>
        <p v-if="bookList.length === 0">暂无收藏书籍</p>
        <BookCard :book-list="bookList"></BookCard>
    </div>
</template>

<script>

import BookCard from "@/components/showbook/BookCard.vue";
import { getShouCangbook } from '@/api/dept';
import { mapState } from 'vuex';

export default {
    components: {
        BookCard
    },
    data() {
        return {
            bookList: [],
        }
    },
    computed: {
        ...mapState(['userPhone'])
    },
    mounted() {
        this.getShouCangBooks();
    },
    methods: {
        async getShouCangBooks() {
            try {
                if (!this.userPhone) {
                    this.$message.warning('请先登录');
                    return;
                }
                const res = await getShouCangbook(this.userPhone);
                if (res.code === 200 && res.data) {
                    this.bookList = res.data;
                } else {
                    this.bookList = [];
                }
            } catch (error) {
                console.error('获取收藏书籍失败:', error);
                this.$message.error('获取收藏书籍失败，请重试');
                this.bookList = [];
            }
        }
    }
}
</script>

<style scoped></style>