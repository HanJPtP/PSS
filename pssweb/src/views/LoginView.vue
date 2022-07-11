<!-- 登录界面 -->
<template>
  <div id="login-main">
      <el-row type="flex" justify="center" align="middle">
        <el-col :span="6">
          <span style="font-size: 20px;color: #fff;margin-left: 24px;">WONIU供电所后台管理系统</span>
          <el-form :rules="rules" :model="userForm"  ref="userForm">
            <el-form-item label="账号" prop="username" class="item" style="margin-top: 18px;color: black;">
              <el-input type="text" v-model="userForm.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" class="item">
              <el-input type="password" v-model="userForm.password"></el-input>
            </el-form-item>
            <el-form-item style="text-align:center;">
              <el-button type="primary" @click="btnlogin('userForm')">登录</el-button>&nbsp;&nbsp;&nbsp;&nbsp;
              <el-button @click="btnreset()">重置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
  </div>
</template>

<script>
export default {
  name: "LoginView",
  data() {
    return {
      userForm:{
            username:"",
            password:""
        },
      rules: {
        username: [
          { required: true, message: "请输入账号", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    //登录按钮
    btnlogin(userForm){
      //调用表单验证结果
       this.$refs[userForm].validate((valid)=>{
         if(valid){
             //提交表单
            let formData=new URLSearchParams({
              "username":this.userForm.username,
              "password":this.userForm.password
            })
            //发送请求
            this.$axios.post('/api/login',formData.toString())
            .then(result=>{
              console.log(result.data);
              if(result.data.code===200){
                //保存服务器返回的token
                let token=result.data.msg;
                console.log(token);
                console.log(this.userForm.username);
                
                localStorage.setItem("rememberMe",token);
                localStorage.setItem("username",this.userForm.username);

                //跳到首页
                this.$router.push({name:"home"});
              }else{
                this.$message({
                  showClose: true,
                  message: result.data.msg,
                  type: 'warning'
                });
              }
            }).catch(e=>{
              console.log(e);
              this.$message({
                showClose: true,
                message: '服务器去火星了...',
                type: 'error'
              });
            });
        } else {
          return false;
        }
      });
    },
    resetForm(userForm) {
      this.$refs[userForm].resetFields();
    },

  },
  components: {},
  computed: {},
};
</script>
<style scoped>
#login-main {
  width: 100%;
  height: 100%;
  background-image:url('@/assets/登录背景.jpg');
}
.el-row {
  height: 100%;
}
.el-col{
  background-image:url('@/assets/111.webp');
  background-size: cover;
  border-radius: 10px;
  padding: 40px;
}
</style>
<style>
.item .el-form-item__label{
  color: azure;
}
</style>
