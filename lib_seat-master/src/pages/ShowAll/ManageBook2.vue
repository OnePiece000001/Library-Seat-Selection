<template>
  <div>
    <el-table :data="filteredBooklist" style="width: 100%" :row-class-name="tableRowClassName">
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
  </div>
</template>

<script>
import { searchbook, updatebook, deletebook } from '@/api/dept';

export default {
  data() {
    return {
      booklist: [],
      filteredBooklist: [],
      loading: false,
      searchinfo: this.$route.query.searchinfo || ''
    }
  },
  methods: {
    async getAllbook(searchInfo = this.searchinfo) {
      if (this.loading) return;
      this.loading = true;
      try {
        const res = await searchbook(searchInfo);
        let bookData = [];
        if (res?.code === 200 && res.data) {
          bookData = Array.isArray(res.data) ? res.data : [res.data];
        }
        this.booklist = bookData;
        this.filteredBooklist = [...this.booklist];
      } catch (error) {
        console.error('获取图书失败:', error);
        this.$message.error('获取图书列表失败');
      } finally {
        this.loading = false;
      }
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
    beforeAvatarUpload(file) {
      const isImage = ['image/jpeg', 'image/png', 'image/gif'].includes(file.type);
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isImage) {
        this.$message.error('仅支持JPG/PNG/GIF格式');
        return false;
      }
      if (!isLt2M) {
        this.$message.error('图片大小不能超过2MB');
        return false;
      }
      return true;
    },
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
          this.$message.error('数量必须≥0');
          return;
        }
        
        const updateData = new FormData();
        updateData.append('book_id', row.book_id);
        updateData.append('book_name', row.book_name.trim());
        updateData.append('book_author', row.book_author.trim());
        updateData.append('book_message', row.book_message || '');
        updateData.append('book_num', parseInt(row.book_num));
        
        // 只有上传了新文件时才传 bookPhoto 字段（修改接口用 bookPhoto）
        if (row._book_photoFile) {
          updateData.append('bookPhoto', row._book_photoFile);
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
          const index = this.booklist.findIndex(item => item.book_id === row.book_id);
          if (index !== -1) {
            this.$set(this.booklist, index, { ...row });
            delete this.booklist[index].isEditing;
            delete this.booklist[index].originalData;
            delete this.booklist[index]._book_photoFile;
          }
          this.$set(row, 'isEditing', false);
          delete row.originalData;
          delete row._book_photoFile;
          this.filteredBooklist = [...this.booklist];
          this.$message.success('修改成功');
        } else {
          this.handleCancel(row);
          this.$message.error(res?.message || '修改失败');
        }
      } catch (error) {
        console.error('修改失败:', error);
        this.handleCancel(row);
        this.$message.error('修改失败');
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
        await this.$confirm('确定删除？', '提示', { type: 'warning' });
        const res = await deletebook(row.book_id);
        if (res && res.code === 200) {
          const index = this.booklist.findIndex(item => item.book_id === row.book_id);
          if (index !== -1) {
            this.booklist.splice(index, 1);
            this.filteredBooklist = [...this.booklist];
          }
          this.$message.success('删除成功');
        } else {
          this.$message.error(res?.message || '删除失败');
        }
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除失败:', error);
          this.$message.error('删除失败');
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
  watch: {
    '$route.query.searchinfo'(newVal) {
      if (newVal && newVal !== this.searchinfo) {
        this.searchinfo = newVal;
        this.getAllbook(newVal);
      }
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