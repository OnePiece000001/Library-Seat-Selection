<template>
  <div class="searchdiv">
    <el-row>
      <!-- 标题 -->
      <el-col :span="3">
        <div class="title">
          XXX图书馆
        </div>
      </el-col>
      <!-- 搜索框 -->
      <el-col :span="19">
        <div class="searchinput">
          <div class="group">
            <svg class="icon" aria-hidden="true" viewBox="0 0 24 24">
              <g>
                <path
                  d="M21.53 20.47l-3.66-3.66C19.195 15.24 20 13.214 20 11c0-4.97-4.03-9-9-9s-9 4.03-9 9 4.03 9 9 9c2.215 0 4.24-.804 5.808-2.13l3.66 3.66c.147.146.34.22.53.22s.385-.073.53-.22c.295-.293.295-.767.002-1.06zM3.5 11c0-4.135 3.365-7.5 7.5-7.5s7.5 3.365 7.5 7.5-3.365 7.5-7.5 7.5-7.5-3.365-7.5-7.5z">
                </path>
              </g>
            </svg>
            <input 
              placeholder="Search Book" 
              type="search" 
              class="input"
              v-model="searchText"
              @keyup.enter="handleSearchEnter"
              >
          </div>
        </div>

      </el-col>
      <!-- 头像 -->
      <el-col :span="2">

        <div class="touxiang">
          <div class="username">
            {{ username }}
          </div>
          <el-dropdown trigger="click" @command="handleCommand">
            <el-tooltip class="item" effect="dark" :content='`用户名:${username}账户:${userPhone}`' placement="bottom-end">
              <el-avatar size="medium" :src="userpicture" class="avatar-dropdown"></el-avatar>
            </el-tooltip>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="editProfile">编辑个人信息</el-dropdown-item>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>

        <!-- 编辑个人信息弹窗 -->
        <el-dialog title="编辑个人信息" :visible.sync="editDialogVisible" width="400px">
          <el-form :model="editForm" label-width="100px" :rules="editRules" ref="editForm">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="editForm.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="userPhone">
              <el-input v-model="editForm.userPhone" placeholder="请输入手机号"></el-input>
            </el-form-item>
            <el-form-item label="头像">
              <input type="file" ref="avatarInput" @change="handleFileChange" accept="image/*">
              <div v-if="editForm.userpicture" style="margin-top: 10px;">
                <img :src="editForm.userpicture" style="width: 60px; height: 60px; border-radius: 50%; object-fit: cover;">
              </div>
            </el-form-item>
            <el-form-item label="修改密码" prop="password">
              <el-input v-model="editForm.password" placeholder="请输入新密码（留空则不修改）" type="password" show-password></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="handleSaveProfile" :loading="saving">确 定</el-button>
          </span>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import { updateuserinfo } from '@/api/dept';

export default {
  data() {
    return {
      searchText:'',
      editDialogVisible: false,
      saving: false,
      editForm: {
        username: '',
        userPhone: '',

        userpicture: '',
        password: ''
      },
      selectedFile: null,
      editRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        userPhone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleCommand(command) {
      if (command === 'logout') {
        this.handleLogout();
      } else if (command === 'editProfile') {
        this.handleEditProfile();
      }
    },
    handleLogout() {
      this.$confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 清除登录信息
        localStorage.removeItem('token');
        this.$store.commit('CLEARUSER');
        this.$message.success('退出登录成功');
        this.$router.push('/');
      }).catch(() => {});
    },
    handleEditProfile() {
      // 打开编辑弹窗并填充当前用户信息
      this.editForm = {
        username: this.username,
        userPhone: this.userPhone,

        userpicture: this.userpicture,
        password: ''
      };
      this.selectedFile = null;
      this.editDialogVisible = true;
    },
    handleFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        // 上传前验证
        if (!this.beforeAvatarUpload(file)) {
          event.target.value = ''; // 清空选择的文件
          return;
        }
        
        this.selectedFile = file;
        // 预览图片
        const reader = new FileReader();
        reader.onload = (e) => {
          this.editForm.userpicture = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },
    // 上传前验证
    beforeAvatarUpload(file) {
      const isImage = ['image/jpeg', 'image/png', 'image/gif'].includes(file.type);
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        this.$message.error('上传图片只能是 JPG/PNG/GIF 格式!');
        return false;
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
        return false;
      }
      return true;
    },
    async handleSaveProfile() {
      this.$refs.editForm.validate(async valid => {
        if (valid) {
          this.saving = true;
          try {
            // 构建 FormData
            const formData = new FormData();
            formData.append('user_name', this.editForm.username);
            formData.append('user_phone', this.userPhone);
            formData.append('user_newphone', this.editForm.userPhone);
            if (this.editForm.password) {
              formData.append('password', this.editForm.password);
            }
            if (this.selectedFile) {
              formData.append('user_picture', this.selectedFile);
            }

            const res = await updateuserinfo(formData);
            if (res.code === 200) {
              // 更新 Vuex 中的用户信息
              this.$store.commit('SETUSER', {
                user_name: this.editForm.username,
                user_phone: this.editForm.userPhone,
                user_picture: res.data?.user_picture || this.editForm.userpicture,
                user_type: this.usertype ? 1 : 0
              });
              this.$message.success('个人信息更新成功');
              this.editDialogVisible = false;
            } else {
              this.$message.error(res.msg || '更新失败');
            }
          } catch (error) {
            console.error('更新个人信息失败:', error);
            this.$message.error('更新失败，请稍后重试');
          } finally {
            this.saving = false;
          }
        }
      });
    },
    handleSearchEnter() {
      // 去除首尾空格
      const searchContent = this.searchText.trim();
      if (!searchContent) {
        this.$message.warning('请输入搜索内容');
        return;
      }

      // 判断usertype是否为真（管理员/普通用户）
      if (!this.usertype) {
        // 普通用户：跳转到SelectBook组件并传递搜索参数
        this.$router.push({
          name: 'SelectBook', // 确保路由配置中该组件的name为SelectBook
          query: { searchinfo: searchContent }
        }).catch(err => {
          // 忽略重复导航错误
          if (err.name !== 'NavigationDuplicated') {
            console.error(err);
          }
        });
      } else {
        // 管理员：跳转到ManageBook2组件并传递搜索参数
        this.$router.push({
          name: 'ManageBook2', // 跳转到ManageBook2组件
          query: { searchinfo: searchContent }
        }).catch(err => {
          // 忽略重复导航错误
          if (err.name !== 'NavigationDuplicated') {
            console.error(err);
          }
        });
      }
    }
  },
  computed: {
    ...mapState(['userpicture', 'username', 'userPhone','usertype'])
  }
}
</script>

<style scoped>
.title {
  line-height: 60px;
  font-size: 29px;
  font-weight: 800;
}

.username {
  font-size: 15px;
  font-weight: 600;
  margin-right: 10px;
}

.touxiang {
  display: flex;
  justify-content: end;
  align-items: center;
}

.avatar-dropdown {
  cursor: pointer;
}

.title,
.touxiang,
.searchinput {
  height: 60px;
}

.searchdiv {
  height: 100%;
}

.searchinput {
  display: flex;
  justify-content: center;
  align-items: center;
}

.group {
  display: flex;
  line-height: 28px;
  align-items: center;
  position: relative;
  width: 690px;
}

.input {
  width: 100%;
  height: 40px;
  line-height: 28px;
  padding: 0 1rem;
  padding-left: 2.5rem;
  border: 2px solid transparent;
  border-radius: 8px;
  outline: none;
  background-color: #f3f3f4;
  color: #0d0c22;
  transition: .3s ease;
}

.input::placeholder {
  color: #9e9ea7;
}

.input:focus,
input:hover {
  outline: none;
  /* border-color: rgba(234, 76, 137, 0.4); */
  border-color: #52a6ff;
  background-color: #fff;
  box-shadow: 0 0 0 4px rgba(76, 105, 234, 0.1);
}

.icon {
  position: absolute;
  left: 1rem;
  fill: #9e9ea7;
  width: 1rem;
  height: 1rem;
}
</style>