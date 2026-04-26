<template>
    <div class="borrow-card-container">
        <div v-if="currentBook" class="book-detail">
            <div class="book-info">
                <h3>{{ currentBook.book_name }}</h3>
                <p>作者: {{ currentBook.book_author }}</p>
                <p>编号: {{ currentBook.book_no }}</p>
                <p>库存: {{ currentBook.book_num || currentBook.stock_num }}</p>
                <p>类型: {{ currentBook.book_type }}</p>
                <p>描述: {{ currentBook.book_message || currentBook.book_msg }}</p>
                <!-- 借阅按钮 -->
                <div v-if="!isFromCollection" class="borrow-btn">
                    <a class="fancy" @click="borrowBook">
                        <span class="top-key"></span>
                        <span class="text">借阅书籍</span>
                        <span class="bottom-key-1"></span>
                        <span class="bottom-key-2"></span>
                    </a>
                </div>
                <div v-if="!isFromCollection" class="shoucang-btn">
                    <a class="fancy" @click="addShouCangBook">
                        <span class="top-key"></span>
                        <span class="text">加入收藏</span>
                        <span class="bottom-key-1"></span>
                        <span class="bottom-key-2"></span>
                    </a>
                </div>
                <div v-else class="shoucang-btn">
                    <a class="fancy" @click="removeShouCangBook">
                        <span class="top-key"></span>
                        <span class="text">取消收藏</span>
                        <span class="bottom-key-1"></span>
                        <span class="bottom-key-2"></span>
                    </a>
                </div>
            </div>
            <div class="container">
                <div class="card_box">
                    <span></span>
                    <img :src="currentBook.book_photo || currentBook.book_cover" :alt="currentBook.book_name" class="book-cover" />
                </div>
            </div>
        </div>
        <div v-else class="no-book">
            未选择书籍
        </div>
        
        <!-- 借阅对话框 -->
        <el-dialog
            title="借阅书籍"
            :visible.sync="dialogVisible"
            width="500px"
            @close="resetForm"
        >
            <el-form :model="borrowForm" :rules="rules" ref="borrowForm" label-width="100px">
                <el-form-item label="借阅时间" prop="borrow_time">
                    <el-date-picker
                        v-model="borrowForm.borrow_time"
                        type="datetime"
                        placeholder="选择借阅时间"
                        style="width: 100%"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="归还时间" prop="return_time">
                    <el-date-picker
                        v-model="borrowForm.return_time"
                        type="datetime"
                        placeholder="选择归还时间"
                        style="width: 100%"
                    ></el-date-picker>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleBorrow">确认借阅</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import { borrowbook, addShouCangbook, deleteShouCangbook } from '@/api/dept';
import { Message } from 'element-ui';

export default {
    computed: {
        ...mapState(['username', 'userPhone'])
    },
    data() {
        return {
            currentBook: null,
            dialogVisible: false,
            isFromCollection: false,
            borrowForm: {
                borrow_time: '',
                return_time: ''
            },
            rules: {
                borrow_time: [
                    { required: true, message: '请选择借阅时间', trigger: 'change' },
                    {
                        validator: (rule, value, callback) => {
                            if (!value) {
                                return callback(new Error('请选择借阅时间'));
                            }
                            const now = new Date();
                            if (new Date(value) < now) {
                                callback(new Error('借阅时间应大于等于当前时间'));
                            } else {
                                callback();
                            }
                        },
                        trigger: 'change'
                    }
                ],
                return_time: [
                    { required: true, message: '请选择归还时间', trigger: 'change' },
                    {
                        validator: (rule, value, callback) => {
                            if (!value) {
                                return callback(new Error('请选择归还时间'));
                            }
                            if (!this.borrowForm.borrow_time) {
                                return callback(new Error('请先选择借阅时间'));
                            }
                            const borrowTime = new Date(this.borrowForm.borrow_time);
                            const returnTime = new Date(value);
                            const oneDay = 24 * 60 * 60 * 1000; // 一天的毫秒数
                            if (returnTime < borrowTime + oneDay) {
                                callback(new Error('归还时间应大于借阅时间一天的时长'));
                            } else {
                                callback();
                            }
                        },
                        trigger: 'change'
                    }
                ]
            }
        }
    },
    created() {
        // 从sessionStorage获取书籍信息
        const bookStr = sessionStorage.getItem('currentBook');
        if (bookStr) {
            this.currentBook = JSON.parse(bookStr);
            // 判断是否来自收藏页面（收藏页面的书籍信息包含book_photo字段）
            this.isFromCollection = !!this.currentBook.book_photo;
        }
    },
    beforeDestroy() {
        // 清理sessionStorage中的数据
        sessionStorage.removeItem('currentBook');
    },
    methods: {
        borrowBook() {
            this.dialogVisible = true;
        },
        async addShouCangBook() {
            try {
                if (!this.userPhone) {
                    Message.warning('请先登录');
                    return;
                }
                if (!this.currentBook || !this.currentBook.book_no) {
                    Message.warning('未选择书籍或书籍信息不完整');
                    return;
                }
                
                const res = await addShouCangbook(this.userPhone, this.currentBook.book_no);
                
                if (res.code === 200) {
                    Message.success('添加收藏成功');
                } else {
                    Message.error('添加收藏失败：' + (res.message || '未知错误'));
                }
            } catch (error) {
                console.error('添加收藏失败:', error);
                Message.error('添加收藏失败，请重试');
            }
        },
        async removeShouCangBook() {
            try {
                if (!this.userPhone) {
                    Message.warning('请先登录');
                    return;
                }
                if (!this.currentBook || !this.currentBook.book_no) {
                    Message.warning('未选择书籍或书籍信息不完整');
                    return;
                }
                
                const res = await deleteShouCangbook(this.userPhone, this.currentBook.book_no);
                
                if (res.code === 200) {
                    Message.success('取消收藏成功');
                    // 返回上一页
                    this.$router.back();
                } else {
                    Message.error('取消收藏失败：' + (res.message || '未知错误'));
                }
            } catch (error) {
                console.error('取消收藏失败:', error);
                Message.error('取消收藏失败，请重试');
            }
        },
        async handleBorrow() {
            // 表单验证
            this.$refs.borrowForm.validate(async (valid) => {
                if (valid) {
                    try {
                        // 准备借阅数据
                        const borrowData = {
                            user_name: this.username,
                            user_phone: this.userPhone,
                            book_no: this.currentBook.book_no,
                            book_name: this.currentBook.book_name,
                            borrow_time: this.borrowForm.borrow_time,
                            return_time: this.borrowForm.return_time
                        };
                        
                        // 调用借阅接口
                        const res = await borrowbook(borrowData);
                        
                        // 处理接口返回结果
                        if (res.code === 200) {
                            Message.success('借阅成功');
                            
                            this.dialogVisible = false;
                            this.resetForm();
                        } else {
                            Message.error('借阅失败：' + res.message);
                        }
                    } catch (error) {
                        console.error('借阅失败:', error);
                        Message.error('借阅失败，请重试');
                    }
                }
            });
        },
        resetForm() {
            if (this.$refs.borrowForm) {
                this.$refs.borrowForm.resetFields();
            }
            this.borrowForm = {
                borrow_time: '',
                return_time: ''
            };
        }
    }
}
</script>

<style scoped>
.borrow-card-container {
    padding: 20px;
}

.book-detail {
    display: flex;
    margin-top: 20px;
    background-color: #f5f5f5;
    padding: 20px;
    border-radius: 8px;
}

.book-cover {
    width: 500px;
    height: 720px;

    margin-right: 30px;
    object-fit: cover;
    border-radius: 20px;
}

.book-info {
    flex: 1;
    position: relative;
}

.book-info h3 {
    margin: 0 0 15px 0;
    font-size: 24px;
    color: #333;
}

.book-info p {
    margin: 10px 0;
    font-size: 16px;
    color: #666;
}

.no-book {
    text-align: center;
    padding: 50px 0;
    color: #999;
    font-size: 18px;
}

/* From Uiverse.io by mrhyddenn */
.container {
    display: flex;
    align-items: center;
    justify-content: center;
}

.card_box {
    width: 500px;
    height: 720px;
    border-radius: 20px;
    position: relative;
    box-shadow: 0 25px 50px rgba(0, 0, 0, 0.55);
    cursor: pointer;
    transition: all .3s;
}

.card_box:hover {
    transform: scale(0.9);
}

.card_box span {
    position: absolute;
    overflow: hidden;
    width: 200px;
    height: 200px;
    top: -10px;
    left: -10px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.card_box span::before {
    content: 'Premium';
    position: absolute;
    width: 160%;
    height: 50px;
    background-image: linear-gradient(45deg, #ff6547 0%, #ffb144 51%, #ff7053 100%);
    transform: rotate(-45deg) translateY(-20px);
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    font-weight: 600;
    letter-spacing: 0.1em;
    text-transform: uppercase;
    box-shadow: 0 5px 10px rgba(0, 0, 0, 0.23);
}

.card_box span::after {
    content: '';
    position: absolute;
    width: 10px;
    bottom: 0;
    left: 0;
    height: 10px;
    z-index: -1;
    box-shadow: 140px -140px #cc3f47;
    background-image: linear-gradient(45deg, #FF512F 0%, #F09819 51%, #FF512F 100%);
}

/* From Uiverse.io by cssbuttons-io */ 
.fancy {
 background-color: transparent;
 border: 2px solid #4ca0ff;
 border-radius: 0;
 box-sizing: border-box;
 color: #fff;
 cursor: pointer;
 display: inline-block;
 float: right;
 font-weight: 700;
 letter-spacing: 0.05em;
 margin: 0;
 outline: none;
 overflow: visible;
 padding: 1.25em 2em;
 position: relative;
 text-align: center;
 text-decoration: none;
 text-transform: none;
 transition: all 0.3s ease-in-out;
 user-select: none;
 font-size: 13px;
}

.fancy::before {
 content: " ";
 width: 1.5625rem;
 height: 2px;
 background: #4ca0ff;
 top: 50%;
 left: 1.5em;
 position: absolute;
 transform: translateY(-50%);
 transform-origin: center;
 transition: background 0.3s linear, width 0.3s linear;
}

.fancy .text {
 font-size: 1.125em;
 line-height: 1.33333em;
 padding-left: 2em;
 display: block;
 text-align: left;
 transition: all 0.3s ease-in-out;
 text-transform: uppercase;
 text-decoration: none;
 color: #4ca0ff;
}

.fancy .top-key {
 height: 2px;
 width: 1.5625rem;
 top: -2px;
 left: 0.625rem;
 position: absolute;
 background: #e8e8e8;
 transition: width 0.5s ease-out, left 0.3s ease-out;
}

.fancy .bottom-key-1 {
 height: 2px;
 width: 1.5625rem;
 right: 1.875rem;
 bottom: -2px;
 position: absolute;
 background: #e8e8e8;
 transition: width 0.5s ease-out, right 0.3s ease-out;
}

.fancy .bottom-key-2 {
 height: 2px;
 width: 0.625rem;
 right: 0.625rem;
 bottom: -2px;
 position: absolute;
 background: #e8e8e8;
 transition: width 0.5s ease-out, right 0.3s ease-out;
}

.fancy:hover {
 color: white;
 background: #4ca0ff;
}

.fancy:hover::before {
 width: 0.9375rem;
 background: white;
}

.fancy:hover .text {
 color: white;
 padding-left: 1.5em;
}

.fancy:hover .top-key {
 left: -2px;
 width: 0px;
}

.fancy:hover .bottom-key-1,
 .fancy:hover .bottom-key-2 {
 right: 0;
 width: 0;
}
.borrow-btn {
    position: absolute;
    top: 20px;
    right: 50px;
}

.shoucang-btn {
    position: absolute;
    top: 20px;
    right: 200px;
}
</style>