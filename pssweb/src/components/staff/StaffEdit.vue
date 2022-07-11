<!--员工修改-->
<template>
  <div>
    <el-dialog
        center
        title="修改员工"
        :visible.sync="StaffEditVisible"
        width="480px"
        style="margin:0 auto"
        >
        
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="20">
           <el-form  :inline="true">
               <el-form-item label="注册账号:">
                  <el-input placeholder="请输入注册账号" type="text" v-model="staff.account"></el-input>
                </el-form-item>
                <el-form-item label="初始密码:">   
                  <el-input placeholder="请输入初始密码" type="password" v-model="staff.password"></el-input>
                </el-form-item>
                <el-form-item label="真实姓名:">
                  <el-input placeholder="请输入真实姓名" type="text" v-model="staff.username"></el-input>
                </el-form-item>
                <el-form-item label="联系方式:">
                  <el-input placeholder="请输入联系方式" type="text" v-model="staff.phone"></el-input>
                </el-form-item>
                <el-form-item label="所属职位:">
                    <el-select clearable v-model="staff.postid" placeholder="请选择所属职位" value="">
                        <el-option v-for="post in postList" :key="post.id+''" :label="post.pname" :value="post.id"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
          </el-col>
        </el-row>

        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="btnUserEdit()">修改</el-button>&nbsp;&nbsp;&nbsp;&nbsp;
            <el-button @click="btnexit">取 消</el-button>
        </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
 name:'StaffEdit',
  data () {
    return {
        staff:{
            account:'',//账号
            password:'',//密码
            username:'',//用户名
            phone:'',//手机
            postid:'',//职位
            status:'y'
        },
        postList:'',//职位列表
        StaffEditVisible: false//控制内容是否显示，false表示隐藏
    };
  },
  methods: {
        //取消按钮
        btnexit(){
          this.StaffEditVisible=false;
        },
        //修改员工
        btnUserEdit(){
          this.$axios.post('/api/staff/edit',this.user)
          .then(result=>{
              if(result.data.code===200){
                   this.$message({
                    showClose: true,
                    message: '修改员工成功',
                    type: 'success'
                    });

                    //隐藏自己
                    this.btnexit();
                    //调用父组件中查询列表数据的方法
                    this.$parent.staffList();
              }else{
                  this.$message({
                    showClose: true,
                    message: result.data.msg,
                    type: 'error'
                    });
              }
          }).catch(e=>{
              this.$message({
              showClose: true,
              message: '服务器去火星了...',
              type: 'error'
            });
            console.log(e);
          })
        }
  },
  components: {},
  computed: {},
  created(){
      
  }
}

</script>
<style scoped>
</style>