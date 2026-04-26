<template>
    <div>
        <h1>图书借阅管理</h1>
        
        <!-- 搜索表单 -->
        <el-form :model="searchForm" ref="searchForm" label-width="80px" inline>
            <el-form-item label="用户名" prop="user_name">
                <el-input v-model="searchForm.user_name" placeholder="请输入用户名" clearable style="width: 200px;"></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="user_phone">
                <el-input v-model="searchForm.user_phone" placeholder="请输入手机号" clearable style="width: 200px;"></el-input>
            </el-form-item>
            <el-form-item label="图书名称" prop="book_name">
                <el-input v-model="searchForm.book_name" placeholder="请输入图书名称" clearable style="width: 200px;"></el-input>
            </el-form-item>
            <el-form-item label="图书编号" prop="book_no">
                <el-input v-model="searchForm.book_no" placeholder="请输入图书编号" clearable style="width: 200px;"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="handleSearch">查询</el-button>
                <el-button @click="handleReset">重置</el-button>
            </el-form-item>
        </el-form>

        <!-- 表格展示 -->
        <el-table :data="getPagedData()" style="width: 100%">
            <el-table-column fixed prop="user_name" label="用户名" width="120"></el-table-column>
            <el-table-column prop="user_phone" label="手机" width="160"></el-table-column>
            <el-table-column prop="book_name" label="图书名称"></el-table-column>
            <el-table-column prop="book_no" label="图书编号" width="120"></el-table-column>
            <el-table-column prop="borrow_time" label="借阅时间" width="180"></el-table-column>
            <el-table-column prop="return_time" label="归还时间" width="180">
                <template slot-scope="scope">
                    <el-date-picker 
                        v-if="scope.row.isEditing" 
                        v-model="scope.row.return_time" 
                        type="date" 
                        placeholder="选择归还日期" 
                        format="yyyy-MM-dd" 
                        value-format="yyyy-MM-dd" 
                        size="small"
                    >
                    </el-date-picker>
                    <span v-else>{{ scope.row.return_time }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="return_status" label="归还状态">
                <template slot-scope="scope">
                    <el-select 
                        v-if="scope.row.isEditing" 
                        v-model="scope.row.return_status" 
                        placeholder="请选择归还状态" 
                        size="small"
                    >
                        <el-option label="已归还" value="已归还"></el-option>
                        <el-option label="未归还" value="未归还"></el-option>
                    </el-select>
                    <span v-else>{{ scope.row.return_status }}</span>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="250">
                <template slot-scope="scope">
                    <el-button v-if="!scope.row.isEditing" size="mini" type="primary" plain @click="handleEdit(scope.row)">
                        编辑
                    </el-button>
                    <el-button v-else size="mini" type="success" plain @click="handleSave(scope.row)">
                        保存
                    </el-button>

                    <el-button v-if="!scope.row.isEditing" size="mini" type="danger" plain @click="handleDelete(scope.row)">
                        删除
                    </el-button>
                    <el-button v-if="scope.row.isEditing" size="mini" type="warning" plain @click="handleCancel(scope.row)">
                        取消
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <div class="pagination-container" style="margin-top: 20px; text-align: right;">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="pageSizes"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
import { manageuserborrow, updateuserborrow, deleteuserborrow } from '@/api/dept';

export default {
    data() {
        return {
            borrowlist: [
                {
                    user_name: '张三',
                    user_phone: '13800000000',
                    book_name: '图书1',
                    book_no: '123456',
                    borrow_time: '2023-01-01',
                    return_time: '2023-01-07',
                    return_status: '已归还'
                },
                {
                    user_name: '李四',
                    user_phone: '13800000001',
                    book_name: '图书2',
                    book_no: '654321',
                    borrow_time: '2023-01-05',
                    return_time: '2023-01-12',
                    return_status: '未归还'
                },
                {
                    user_name: '王五',
                    user_phone: '13800000002',
                    book_name: '图书3',
                    book_no: '345678',
                    borrow_time: '2023-01-10',
                    return_time: '2023-01-17',
                    return_status: '已归还'
                }
            ],
            filteredBorrowlist: [], // 过滤后的数据
            searchForm: {
                user_name: '',
                user_phone: '',
                book_name: '',
                book_no: ''
            },
            // 分页相关数据
            currentPage: 1, // 当前页码
            pageSize: 10, // 每页显示条数
            pageSizes: [10, 20, 30, 50], // 可选每页显示条数
            total: 0 // 总数据条数
        }
    },
    methods: {
        async getAllBorrowMsg() {
            try {
                // 正常调用接口并使用返回的数据
                const res = await manageuserborrow();
                if (res.code === 200 && res.data) {
                    this.borrowlist = res.data;
                } else {
                    this.$message.error(res.msg || '获取借阅信息失败');
                }
                this.applyFilters();
            } catch (error) {
                console.error('获取借阅信息失败:', error);
                this.$message.error('获取借阅信息失败，请稍后重试');
                this.applyFilters();
            }
        },
        
        // 应用搜索过滤条件
        applyFilters() {
            this.filteredBorrowlist = this.borrowlist.filter(item => {
                // 用户名模糊匹配
                const matchName = !this.searchForm.user_name ||
                    item.user_name.toLowerCase().includes(this.searchForm.user_name.toLowerCase());
                
                // 手机号模糊匹配
                const matchPhone = !this.searchForm.user_phone ||
                    item.user_phone.includes(this.searchForm.user_phone);
                
                // 图书名称模糊匹配
                const matchBookName = !this.searchForm.book_name ||
                    item.book_name.toLowerCase().includes(this.searchForm.book_name.toLowerCase());
                
                // 图书编号模糊匹配
                const matchBookNo = !this.searchForm.book_no ||
                    item.book_no.includes(this.searchForm.book_no);
                
                return matchName && matchPhone && matchBookName && matchBookNo;
            });
            // 更新总数据条数
            this.total = this.filteredBorrowlist.length;
            // 重置当前页码
            this.currentPage = 1;
        },
        /**
         * 获取分页后的数据
         */
        getPagedData() {
            const start = (this.currentPage - 1) * this.pageSize;
            const end = start + this.pageSize;
            return this.filteredBorrowlist.slice(start, end);
        },
        /**
         * 处理页码变化
         */
        handleCurrentChange(current) {
            this.currentPage = current;
        },
        /**
         * 处理每页显示条数变化
         */
        handleSizeChange(size) {
            this.pageSize = size;
            this.currentPage = 1; // 重置页码
        },
        
        // 处理搜索按钮点击事件
        handleSearch() {
            this.applyFilters();
        },
        
        // 处理重置按钮点击事件
        handleReset() {
            this.searchForm = {
                user_name: '',
                user_phone: '',
                book_name: '',
                book_no: ''
            };
            this.applyFilters();
        },
        
        // 处理编辑按钮点击事件
        handleEdit(row) {
            // 设置编辑状态，保存原始数据用于取消操作
            this.$set(row, 'isEditing', true);
            this.$set(row, 'originalData', {
                return_time: row.return_time,
                return_status: row.return_status
            });
        },
        
        // 处理保存按钮点击事件
        async handleSave(row) {
            try {
                // 发送修改数据到后端
                const res = await updateuserborrow({
                    user_name: row.user_name,
                    user_phone: row.user_phone,
                    book_name: row.book_name,
                    book_no: row.book_no,
                    borrow_time: row.borrow_time,
                    return_time: row.return_time,
                    return_status: row.return_status
                });
                
                if (res.code === 200) {
                    // 后端修改成功后更新本地列表
                    const index = this.borrowlist.findIndex(item => 
                        item.user_name === row.user_name && 
                        item.user_phone === row.user_phone && 
                        item.book_name === row.book_name && 
                        item.book_no === row.book_no && 
                        item.borrow_time === row.borrow_time
                    );
                    if (index !== -1) {
                        // 如果当前行在borrowlist中，更新对应的数据
                        this.borrowlist[index] = {
                            ...row,
                            isEditing: false
                        };
                        delete this.borrowlist[index].originalData;
                    } else {
                        // 如果当前行不在borrowlist中，添加到列表中
                        const newRow = {
                            ...row,
                            isEditing: false
                        };
                        delete newRow.originalData;
                        this.borrowlist.push(newRow);
                    }
                    
                    // 结束编辑状态
                    this.$set(row, 'isEditing', false);
                    delete row.originalData;
                    
                    // 重新应用搜索过滤
                    this.applyFilters();
                    
                    this.$message({
                        message: '保存成功',
                        type: 'success'
                    });
                } else {
                    // 如果后端保存不成功，恢复原数据
                    this.handleCancel(row);
                    this.$message.error('保存失败，请重试');
                }
            } catch (error) {
                this.$message.error('保存失败，请重试');
                console.error('保存数据失败:', error);
            }
        },
        
        // 处理取消按钮点击事件
        handleCancel(row) {
            if (row.originalData) {
                // 恢复原始数据
                row.return_time = row.originalData.return_time;
                row.return_status = row.originalData.return_status;
                
                // 删除编辑状态和原始数据
                this.$set(row, 'isEditing', false);
                delete row.originalData;
            }
        },
        
        // 处理删除按钮点击事件
        async handleDelete(row) {
            this.$confirm('确定要删除这条借阅记录吗？', '删除确认', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                try {
                    // 调用deleteuserborrow接口，将该行的所有信息发送给后端
                    const res = await deleteuserborrow({
                        user_name: row.user_name,
                        user_phone: row.user_phone,
                        book_no: row.book_no,
                    });
                    
                    if (res.code === 200) {
                        // 后端删除成功后更新本地列表borrowlist
                        const index = this.borrowlist.findIndex(item => 
                            item.user_name === row.user_name && 
                            item.user_phone === row.user_phone && 
                            item.book_name === row.book_name && 
                            item.book_no === row.book_no && 
                            item.borrow_time === row.borrow_time
                        );
                        if (index !== -1) {
                            this.borrowlist.splice(index, 1);
                        }
                        
                        // 重新应用搜索过滤
                        this.applyFilters();
                        
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                    } else {
                        this.$message.error('删除失败，请重试');
                    }
                } catch (error) {
                    this.$message.error('删除失败，请重试');
                    console.error('删除数据失败:', error);
                }
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        }
    },
    
    mounted() {
        this.getAllBorrowMsg();
    }
}
</script>

<style scoped>
/* 标题样式 */
h1 {
    text-align: center;
    margin-bottom: 20px;
    color: #333;
}

/* 搜索表单样式 */
.el-form {
    margin-bottom: 20px;
    padding: 15px;
    background-color: #f5f7fa;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* 表格样式 */
.el-table {
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

/* 操作按钮样式 */
.el-button {
    margin-right: 5px;
}

/* 编辑状态样式 */
.el-table__row .isEditing {
    background-color: #f0f9eb;
}
</style>