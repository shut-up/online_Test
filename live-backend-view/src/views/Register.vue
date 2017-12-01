<template>
  <el-form :model="userForm" :rules="rules" ref="userForm" label-width="100px" class="demo-ruleForm login-container">
    <h3 class="title">用户注册</h3>
    <el-form-item label="账号：" prop="id">
      <el-input type="text" v-model="userForm.id" auto-complete="off" placeholder="2-10个字符"></el-input>
    </el-form-item>
    <el-form-item label="姓名：" prop="name">
      <el-input type="text" v-model="userForm.name" auto-complete="off" placeholder="2-8个字符"></el-input>
    </el-form-item>
    <el-form-item label="密码：" prop="password">
      <el-input type="password" v-model="userForm.password" auto-complete="off" placeholder="2-15个字符"></el-input>
    </el-form-item>
      <el-form-item label="选择身份：" prop="type">
        <el-select clearable v-model="userForm.type">
          <el-option label="学生" value="0"></el-option>
          <el-option label="教师" value="1"></el-option>
        </el-select>
      </el-form-item>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click="Register" :loading="loading">注册</el-button>
    </el-form-item>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click="Login" :loading="loading">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import { userRegister } from '../api/api';
  export default {
    data() {
      return {
        loading: false,
        userForm: {
          id: '',
          password: '',
          type: '',
          name: ''
        },
        rules: {
          id: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 2, max: 8, message: '长度在 2 到 8 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 2, max: 15, message: '长度在 2 到 15 个字符', trigger: 'blur' }
          ],
          type: [
              { required: true, message: '请选择身份', trigger: 'change' }
          ],
        },
        checked: true
      };
    },
    methods: {
      Login: function () {
          this.$router.push({ path: '/login' });  //跳到Login.vue页面
      },
      //用户注册
      Register: function () {
          this.$refs.userForm.validate((valid) => {
                if (valid) {
                    //获取表单参数
                    let userParas = Object.assign({}, this.userForm);
                    //console.log(userParas.id + "aaa" + userParas.type);
                    this.loading = true;
                    userRegister(userParas).then((res) => {
                        this.loading = false;
                        console.log(res.data);
                        if (res.data !== undefined) {
                            this.$message({
                                message: '注册成功',
                                type: 'success'
                            });
                            this.$router.push({path: '/login'});  //login.vue页面
                        }else {
                            this.$message({
                                message: '用户已存在',
                                type: 'error'
                            });
                            return false;
                        }
                    })
                }else {  //没通过表单验证（没输入账号，密码等）
                    console.log('error submit!!');
                    return false;
                }
            });
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