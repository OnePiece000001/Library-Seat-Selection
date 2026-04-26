<template>
  <div>
    <!-- 搜索表单 -->
    <el-form :model="searchForm" ref="searchForm" label-width="80px" inline>
      <el-form-item label="图书ID" prop="book_id">
        <el-input 
          v-model="searchForm.book_id" 
          placeholder="请输入图书ID" 
          clearable 
          style="width: 200px;"
        ></el-input>
      </el-form-item>
      <el-form-item label="图书名" prop="book_name">
        <el-input 
          v-model="searchForm.book_name" 
          placeholder="请输入图书名" 
          clearable 
          style="width: 200px;"
        ></el-input>
      </el-form-item>
      <el-form-item label="作者" prop="book_author">
        <el-input 
          v-model="searchForm.book_author" 
          placeholder="请输入作者" 
          clearable 
          style="width: 200px;"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="handleReset">重置</el-button>
        <el-button type="success" @click="showAddDialog">添加图书</el-button>
      </el-form-item>
    </el-form>

    <!-- 添加图书对话框 -->
    <el-dialog title="添加图书" :visible.sync="addDialogVisible" width="500px" :before-close="handleAddCancel">
      <el-form :model="addForm" :rules="addFormRules" ref="addForm" label-width="80px">
        <el-form-item label="图书ID" prop="book_id">
          <el-input v-model="addForm.book_id" placeholder="请输入图书ID"></el-input>
        </el-form-item>
        <el-form-item label="图书类型" prop="book_type">
          <el-input v-model="addForm.book_type" placeholder="请输入图书类型"></el-input>
        </el-form-item>
        <el-form-item label="图书名" prop="book_name">
          <el-input v-model="addForm.book_name" placeholder="请输入图书名"></el-input>
        </el-form-item>
        <el-form-item label="作者" prop="book_author">
          <el-input v-model="addForm.book_author" placeholder="请输入作者"></el-input>
        </el-form-item>
        <!-- 替换el-upload为原生input file -->
        <el-form-item label="封面" prop="book_photo">
          <div class="avatar-uploader">
            <!-- 预览图 -->
            <img v-if="addForm.book_photoUrl" :src="addForm.book_photoUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            <!-- 原生文件上传按钮 -->
            <input 
              type="file" 
              class="file-input"
              accept="image/jpeg,image/png,image/gif"
              @change="handleAddFileChange"
            />
          </div>
        </el-form-item>
        <el-form-item label="图书简介" prop="book_message">
          <el-input v-model="addForm.book_message" type="textarea" placeholder="请输入图书简介"></el-input>
        </el-form-item>
        <el-form-item label="数量" prop="book_num">
          <el-input-number v-model="addForm.book_num" :min="0" placeholder="请输入数量"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleAddCancel">取 消</el-button>
        <el-button type="primary" @click="handleAddConfirm">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 表格展示 -->
    <el-table :data="getPagedData()" style="width: 100%" :row-class-name="tableRowClassName">
      <el-table-column fixed prop="book_id" label="图书ID" width="100"></el-table-column>
      <el-table-column prop="book_name" label="图书名" width="200">
        <template slot-scope="scope">
          <el-input 
            v-if="scope.row.isEditing" 
            v-model="scope.row.book_name" 
            size="small">
          </el-input>
          <span v-else>{{ scope.row.book_name }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="book_author" label="作者" width="150">
        <template slot-scope="scope">
          <el-input 
            v-if="scope.row.isEditing" 
            v-model="scope.row.book_author" 
            size="small">
          </el-input>
          <span v-else>{{ scope.row.book_author }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="book_photo" label="封面">
        <template slot-scope="scope">
          <div v-if="scope.row.isEditing">
            <!-- 替换el-upload为原生input file -->
            <div class="avatar-uploader">
              <img v-if="scope.row.book_photo" :src="scope.row.book_photo" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              <input 
                type="file" 
                class="file-input"
                accept="image/jpeg,image/png,image/gif"
                @change="(e) => handleEditFileChange(e, scope.row)"
              />
            </div>
          </div>
          <div v-else>
            <el-image 
              :src="scope.row.book_photo" 
              :preview-src-list="[scope.row.book_photo]"
              style="width: 50px; height: 50px;"
              fit="cover">
            </el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="book_message" label="图书简介" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-input 
            v-if="scope.row.isEditing" 
            v-model="scope.row.book_message" 
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4 }"
            size="small">
          </el-input>
          <span v-else>{{ scope.row.book_message }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="book_num" label="数量">
        <template slot-scope="scope">
          <el-input 
            v-if="scope.row.isEditing" 
            v-model="scope.row.book_num" 
            size="small"
            type="number"
            :min="0">
          </el-input>
          <span v-else>{{ scope.row.book_num }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button 
            v-if="!scope.row.isEditing" 
            size="mini" 
            type="primary" 
            plain 
            @click="handleEdit(scope.row)">
            编辑
          </el-button>
          <el-button 
            v-else 
            size="mini" 
            type="success" 
            plain 
            @click="handleSave(scope.row)">
            保存
          </el-button>

          <el-button 
            v-if="!scope.row.isEditing" 
            size="mini" 
            type="danger" 
            plain 
            @click="handleDelete(scope.row)">
            删除
          </el-button>
          <el-button 
            v-else 
            size="mini" 
            type="warning" 
            plain 
            @click="handleCancel(scope.row)">
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
import { getbook, updatebook, deletebook, addbook } from '@/api/dept';

export default {
  data() {
    return {
      uploadHeaders: {},
      booklist: [],
      filteredBooklist: [],
      loading: false,
      searchForm: {
        book_id: '',
        book_name: '',
        book_author: ''
      },
      // 分页相关数据
      currentPage: 1, // 当前页码
      pageSize: 10, // 每页显示条数
      pageSizes: [10, 20, 30, 50], // 可选每页显示条数
      total: 0, // 总数据条数
      addDialogVisible: false,
      addForm: {
        book_id: '',
        book_type: '',
        book_name: '',
        book_author: '',
        book_photoUrl: '',
        book_photoFile: null,
        book_message: '',
        book_num: 0
      },
      addFormRules: {
        book_id: [{ required: true, message: '请输入图书ID', trigger: 'blur' }],
        book_type: [{ required: true, message: '请输入图书类型', trigger: 'blur' }],
        book_name: [{ required: true, message: '请输入图书名', trigger: 'blur' }],
        book_author: [{ required: true, message: '请输入作者', trigger: 'blur' }],
        book_photo: [
          { 
            required: true, 
            message: '请上传封面图片', 
            trigger: 'change',
            validator: (rule, value, callback) => {
              if (this.addForm.book_photoFile) {
                callback();
              } else {
                callback(new Error('请上传封面图片'));
              }
            }
          }
        ],
        book_message: [{ required: true, message: '请输入图书简介', trigger: 'blur' }],
        book_num: [
          { required: true, message: '请输入数量', trigger: 'blur' },
          { type: 'number', min: 0, message: '数量必须大于等于0', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    async getAllbook() {
      this.loading = true;
      try {
        const res = await getbook()
        this.booklist = Array.isArray(res?.data) ? res.data : [];
        this.applyFilters();
      } catch (error) {
        console.error('获取图书信息失败:', error);
      } finally {
        this.loading = false;
      }
    },
    applyFilters() {
      this.filteredBooklist = this.booklist.filter(item => {
        const matchId = !this.searchForm.book_id || item.book_id.toString().includes(this.searchForm.book_id);
        const matchName = !this.searchForm.book_name || item.book_name.toLowerCase().includes(this.searchForm.book_name.toLowerCase());
        const matchAuthor = !this.searchForm.book_author || item.book_author.toLowerCase().includes(this.searchForm.book_author.toLowerCase());
        return matchId && matchName && matchAuthor;
      });
      // 更新总数据条数
      this.total = this.filteredBooklist.length;
      // 重置当前页码
      this.currentPage = 1;
    },
    /**
     * 获取分页后的数据
     */
    getPagedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredBooklist.slice(start, end);
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
    handleSearch() {
      this.applyFilters();
    },
    handleReset() {
      this.searchForm = { book_id: '', book_name: '', book_author: '' };
      this.applyFilters();
    },
    showAddDialog() {
      this.addDialogVisible = true;
      this.$nextTick(() => {
        this.addForm = {
          book_id: '',
          book_type: '',
          book_name: '',
          book_author: '',
          book_photoUrl: '',
          book_photoFile: null,
          book_message: '',
          book_num: 0
        };
        this.$refs.addForm?.clearValidate();
      });
    },
    handleAddCancel() {
      this.addDialogVisible = false;
      this.$refs.addForm?.clearValidate();
    },
    // 添加图书的文件选择事件 - 仅本地预览，保存时统一上传
    handleAddFileChange(e) {
      const file = e.target.files[0];
      if (!file) return;

      // 上传前验证
      if (!this.beforeAvatarUpload(file)) {
        e.target.value = ''; // 清空选择的文件
        return;
      }

      this.addForm.book_photoFile = file;
      // 生成预览URL
      this.addForm.book_photoUrl = URL.createObjectURL(file);
      this.$refs.addForm?.validateField('book_photo');
    },
    // 编辑图书的文件选择事件 - 仅本地预览，保存时统一上传
    handleEditFileChange(e, row) {
      const file = e.target.files[0];
      if (!file) return;

      // 上传前验证
      if (!this.beforeAvatarUpload(file)) {
        e.target.value = '';
        return;
      }

      // 保存文件对象和本地预览URL，等到保存时再上传
      row._book_photoFile = file;
      row.book_photo = URL.createObjectURL(file);
    },
    // 上传前验证（复用原有逻辑）
    beforeAvatarUpload(file) {
      const isImage = ['image/jpeg', 'image/png', 'image/gif'].includes(file.type);
      const isLt20M = file.size / 1024 / 1024 < 20;

      if (!isImage) {
        this.$message.error('上传图片只能是 JPG/PNG/GIF 格式!');
        return false;
      }
      if (!isLt20M) {
        this.$message.error('上传图片大小不能超过 20MB!');
        return false;
      }
      return true;
    },
    // 添加图书确认（适配API封装）
    async handleAddConfirm() {
      this.$refs.addForm.validate(async (valid) => {
        if (valid) {
          try {
            if (!this.addForm.book_photoFile) {
              this.$message.error('请先上传封面图片');
              return;
            }
            
            const addData = new FormData();
            addData.append('book_id', this.addForm.book_id.trim());
            addData.append('book_type', this.addForm.book_type.trim());
            addData.append('book_name', this.addForm.book_name.trim());
            addData.append('book_author', this.addForm.book_author.trim());
            addData.append('book_message', this.addForm.book_message.trim());
            addData.append('book_num', this.addForm.book_num);
            addData.append('book_photo', this.addForm.book_photoFile);
            
            // 请求头（仅传token，Content-Type由API封装处理）
            const headers = {};
            const token = localStorage.getItem('token');
            if (token) {
              headers.Authorization = token;
            }
            
            // 调用添加接口（适配新的API封装）
            const res = await addbook(addData, { headers });
            
            if (res && res.code === 200) {
              // 构造新图书对象
              const newBook = {
                book_id: this.addForm.book_id.trim(),
                book_type: this.addForm.book_type.trim(),
                book_name: this.addForm.book_name.trim(),
                book_author: this.addForm.book_author.trim(),
                book_photo: this.addForm.book_photoUrl, // 暂时使用本地预览URL
                book_message: this.addForm.book_message.trim(),
                book_num: this.addForm.book_num
              };
              
              // 如果后端返回了新的图片URL，更新图片地址
              if (res.data && typeof res.data === 'object' && res.data.url) {
                newBook.book_photo = res.data.url;
              } else if (res.data && typeof res.data === 'string' && res.data.startsWith('http')) {
                newBook.book_photo = res.data;
              }
              
              this.booklist.push(newBook);
              this.applyFilters();
              
              this.$message.success('添加图书成功');
              this.addDialogVisible = false;
            } else {
              this.$message.error(res?.message || '添加图书失败');
            }
          } catch (error) {
            console.error('添加图书失败:', error);
            this.$message.error('添加图书失败，请重试');
          }
        } else {
          this.$message.error('请填写完整且正确的图书信息');
        }
      });
    },
    // 保存编辑（适配API封装）
    async handleSave(row) {
      try {
        if (!row.book_name?.trim()) {
          this.$message.error('图书名不能为空');
          return;
        }
        if (!row.book_author?.trim()) {
          this.$message.error('作者不能为空');
          return;
        }
        if (!row.book_photo) {
          this.$message.error('请上传封面图片');
          return;
        }
        if (row.book_num < 0 || isNaN(row.book_num)) {
          this.$message.error('图书数量必须是大于等于0的数字');
          return;
        }
        
        const updateData = new FormData();
        updateData.append('book_id', row.book_id);
        updateData.append('book_name', row.book_name.trim());
        updateData.append('book_author', row.book_author.trim());
        updateData.append('book_message', row.book_message || '');
        updateData.append('book_num', parseInt(row.book_num));
        
        // 只有上传了新文件时才传 book_photo 字段（与后端接口保持一致）
        if (row._book_photoFile) {
          updateData.append('book_photo', row._book_photoFile);
        }
        
        // 请求头（仅传token，Content-Type由API封装处理）
        const headers = {};
        const token = localStorage.getItem('token');
        if (token) {
          headers.Authorization = token;
        }
        
        // 调用更新接口（适配新的API封装）
        const res = await updatebook(updateData, { headers });
        if (res && res.code === 200) {
          // 如果后端返回了新的图片URL，更新图片地址
          if (res.data && typeof res.data === 'object' && res.data.url) {
            row.book_photo = res.data.url;
          } else if (res.data && typeof res.data === 'string' && res.data.startsWith('http')) {
            row.book_photo = res.data;
          }
          const originalIndex = this.booklist.findIndex(item => item.book_id === row.book_id);
          if (originalIndex !== -1) {
            this.$set(this.booklist, originalIndex, { ...row });
            delete this.booklist[originalIndex].isEditing;
            delete this.booklist[originalIndex].originalData;
            delete this.booklist[originalIndex]._book_photoFile;
          }
          this.$set(row, 'isEditing', false);
          delete row.originalData;
          delete row._book_photoFile;
          this.applyFilters();
          this.$message.success('修改成功');
        } else {
          this.handleCancel(row);
          this.$message.error(res?.message || '修改失败');
        }
      } catch (error) {
        console.error('修改失败:', error);
        this.handleCancel(row);
        this.$message.error('修改失败，请重试');
      }
    },
    handleEdit(row) {
      this.$set(row, 'isEditing', true);
      this.$set(row, 'originalData', { ...row });
    },
    handleCancel(row) {
      if (row.originalData) {
        Object.assign(row, row.originalData);
        this.$set(row, 'isEditing', false);
        delete row.originalData;
      }
    },
    async handleDelete(row) {
      try {
        await this.$confirm('确定要删除这本图书吗？', '提示', { type: 'warning' });
        const res = await deletebook(row.book_id);
        if (res && res.code === 200) {
          const index = this.booklist.findIndex(item => item.book_id === row.book_id);
          if (index !== -1) {
            this.booklist.splice(index, 1);
            this.applyFilters();
          }
          this.$message.success('删除成功');
        } else {
          this.$message.error(res?.message || '删除失败');
        }
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除失败:', error);
          this.$message.error('删除失败，请重试');
        } else {
          this.$message.info('已取消删除');
        }
      }
    },
    tableRowClassName({ rowIndex }) {
      if (rowIndex === 1) return 'warning-row';
      if (rowIndex === 3) return 'success-row';
      return '';
    }
  },
  mounted() {
    this.getAllbook();
  }
}
</script>

<style>
.el-table .warning-row { background: oldlace; }
.el-table .success-row { background: #f0f9eb; }
.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 50px;
  height: 50px;
}
.avatar-uploader:hover { border-color: #409EFF; }
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 50px;
  height: 50px;
  line-height: 50px;
  text-align: center;
}
.avatar {
  width: 50px;
  height: 50px;
  display: block;
}
/* 隐藏原生file input，保留点击区域 */
.file-input {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  cursor: pointer;
  z-index: 10;
}
</style>