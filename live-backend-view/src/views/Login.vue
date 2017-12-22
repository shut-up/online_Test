<template>
  <el-form :model="userForm" :rules="rules" ref="userForm" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">系统登录</h3>
    <el-form-item prop="id">
      <el-input type="text" v-model="userForm.accountNumber" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="userForm.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="Login" :loading="loading">登录</el-button>
    </el-form-item>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.preven="Register" :loading="loading">注册</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import { userLogin,userRegister } from '../api/api';
  export default {
    data() {
      return {
        loading: false,
        userForm: {
          accountNumber: '',
          password: ''
        },
        rules: {
            accountNumber: [
            { required: true, message: '请输入账号', trigger: 'blur' },
          ],
            password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
          ]
        },
        checked: true
      };
    },
    methods: {
      Login: function() {
          this.$refs.userForm.validate((valid) => {
              if (valid) {
                  //获取表单参数
                  let userParas = Object.assign({}, this.userForm);
                  this.loading = true;
                  userLogin(userParas).then((res) => {
                      this.loading = false;
                      //console.log("res"+res.data.name);
                      if (res.data !== undefined) {
                          if(this.checked) {
                              localStorage.setItem('name', res.data.name);   //将name存到localStorage
                              localStorage.setItem('accountNumber', res.data.accountNumber);   //将name存到localStorage
                              localStorage.setItem('type', res.data.type);   //将type存到localStorage
                          }else{
                              sessionStorage.setItem('name', res.data.name);   //将name存到sessionStorage
                              sessionStorage.setItem('accountNumber', res.data.accountNumber);   //将name存到sessionStorage
                              sessionStorage.setItem('type', res.data.type);   //将type存到sessionStorage
                          }
                          this.$message({
                              message: '登录成功',
                              type: 'success'
                          });
                          if(res.data.type == 0) {  //学生登录跳转到学生页面
                              this.$router.push({path: '/myExam'});
                          }else {                 //教师登录跳转到教师页面
                              this.$router.push({path: '/choiceQuestion'});
                          }
                      } else{
                          this.$message({
                              message: '用户名或密码错误',
                              type: 'error'
                          });
                      }
                  })
              }else {  //没通过表单验证（没输入账号，密码等）
                  console.log('error submit!!');
                  return false;
              }
          });
      },
      //用户注册
      Register: function () {
          this.$router.push({ path: '/register' });  //跳到Register.vue页面
      },
    }
  }

</script>

<style lang="scss" scoped>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>