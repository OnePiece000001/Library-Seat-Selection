<template>
  <div id="lgiondiv">
    <div class="formdiv">
      <div class="Passwordlogin">
        <div id="consultation-form" class="feed-form">
          <div class="title">
            <h1>登录</h1>
          </div>
          <!-- 手机账号输入框 -->
          <input v-model="userphone" name="phone" required="" placeholder="Phone" type="text">
          <!-- 密码输入框 -->
          <input v-model="password" name="password" required="" placeholder="Password" type="password">
          <!-- 注册时候再次输入密码 -->
            <input v-if="!pwd" v-model="passwordagain" name="passwordagain" required="" placeholder="Password" type="password">
          <!-- login -->
          <button @click="signIn" class="button_login">Login</button>
          <div v-if="pwd" class="tishibtn" @click="pwd = !pwd">Register an account</div>
          <div v-if="!pwd" class="tishibtn" @click="pwd = !pwd">Sign in by password</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { login, register } from '@/api/dept';
import { mapMutations, mapActions } from 'vuex';
export default {

  data() {
    return {
      pwd: true,//控制是否密码登录 true为登录 false为注册
      userphone: '',
      password: '',
      passwordagain: '',
    }
  },
  methods: {
    ...mapMutations(['SETUSER']),
    ...mapActions(['fetchUserInfo']),

    async signIn() {
      // 注册模式
      if (!this.pwd) {
        // 验证两次密码是否相同
        if (this.password !== this.passwordagain) {
          alert("两次输入的密码不一致，请重新输入");
          return;
        }
        // 验证手机号和密码不能为空
        if (!this.userphone || !this.password) {
          alert("手机号和密码不能为空");
          return;
        }
        try {
          const res = await register(this.userphone, this.password);
          console.log(res);
          if (res.code === 200) {
            alert("注册成功，请登录");
            // 切换到登录模式
            this.pwd = true;
            // 清空确认密码
            this.passwordagain = '';
          } else {
            alert(res.msg || "注册失败");
          }
        } catch (error) {
          console.error("注册请求出错:", error);
          alert("注册请求出错，请稍后重试");
        }
        return;
      }

      // 登录模式
      try {
        const loginres = await login(this.userphone, this.password)
        console.log(loginres);

        if (loginres.code === 200 && loginres.data) {
          // 调用 fetchUserInfo 获取完整用户信息
          const result = await this.fetchUserInfo(this.userphone)
          if (result.success) {
            this.$router.push('ShowAll')
          } else {
            alert(result.message || "获取用户信息失败");
          }
        } else {
          alert("登录失败：用户名或密码错误");
        }
      } catch (error) {
        console.error("请求出错:", error);
        alert("请求出错，请稍后重试");
      }
    },

  },
}
</script>

<style scoped>
#lgiondiv {
  background-image: url(../assets/loginbj.jpg);
  background-color: #000000;
  background-size: cover;
  background-position: center;
  width: 100vw;
  height: 100vh;
}

.formdiv {
  width: 50vw;
  height: 100vh;
  position: absolute;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.title,
.tishibtn,
.getbtn {
  text-align: center;
  color: rgb(54, 35, 24);
  user-select: none;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
}

.tishibtn,
.getbtn {
  font-weight: 800;
}

.title {
  margin-bottom: 15px;
}

.getbtn {
  background: #a4b17c;
  line-height: 55px;
  border-radius: 5px;
}

.feed-form {
  background-color: #ffffff88;
  border-radius: 10px;
  padding: 25px;
  margin-top: 36px;
  display: flex;
  flex-direction: column;
  width: 300px;
}

.feed-form input {
  height: 54px;
  border-radius: 5px;
  background: white;
  margin-bottom: 15px;
  border: none;
  padding: 0 20px;
  font-weight: 300;
  font-size: 14px;
  color: #000000;
}

.button_login:hover,
.feed-form input:hover {
  transform: scale(1.009);
  box-shadow: 0px 0px 3px 0px #212529;
}

.button_login {
  width: 100%;
  height: 54px;
  font-size: 14px;
  color: white;
  background: #a4b17c;
  border-radius: 5px;
  border: none;
  font-weight: 500;
  text-transform: uppercase;
  margin-bottom: 15px;
}
</style>