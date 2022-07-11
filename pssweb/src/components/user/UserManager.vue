<!--员工管理-->
<template>
  <div>
    <!-- 面包屑 -->
    <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item :to="{ path: 'home' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>角色管理</el-breadcrumb-item>
    <el-breadcrumb-item :to="{ path: 'user' }">管理列表</el-breadcrumb-item>
    </el-breadcrumb><br/></div>

    <!-- 条件查询 -->
    <el-form :inline="true"  class="demo-form-inline">
    
      <!-- 员工名查询 -->
      <el-form-item label="员工名：">
          <el-input placeholder="请输入员工名" v-model="name">
          <el-button slot="append" icon="el-icon-search" @click="userList()">搜索</el-button>
          </el-input>
      </el-form-item>

      <el-form-item>
          <el-button type="primary" @click="showUserAdd()">添加</el-button>
      </el-form-item>
    </el-form>
    
    <!-- 表格数据 -->
    <el-table :data="tableData" border style="width: 100%" max-height="460" fit >
        <el-table-column align="center" label="序号" type="index" :index="indexMethod" width="80px">
        </el-table-column>
        <el-table-column align="center" prop="jobno" label="工号" width="100px"></el-table-column>
        <el-table-column align="center" prop="username" label="用户名" width="160px"></el-table-column>
        <el-table-column align="center" prop="phone" label="手机号" width="200px"></el-table-column>
        <el-table-column align="center" prop="loginTime" label="最近登录时间">
        </el-table-column>
        <el-table-column align="center" prop="loginIp" label="最近登录IP" width="160px"></el-table-column>

        <el-table-column align="center" prop="pid" label="职位" width="100px">
            <template slot-scope="scope">
                <span v-if="scope.row.pid==1">所长</span>
                <span v-if="scope.row.pid==2">综合班班长</span>
                <span v-if="scope.row.pid==3">配电营业班班长</span>
             </template>
        </el-table-column>

        <el-table-column align="center" prop="status" label="状态" width="100px">
            <template slot-scope="scope">
                <span v-if="scope.row.status=='y'">
                  <el-tag type="success" effect="dark">在职</el-tag>
                </span>
                <span v-if="scope.row.status=='n'">
                  <el-tag type="danger" effect="dark">离职</el-tag>
                </span>
             </template>
        </el-table-column>
      
        <el-table-column align="center" prop="right" label="操作" width="210px">
            <template slot-scope="scope">
              <el-button type="success" icon="el-icon-key" size="small" title="授权" @click="btnUserPerm(scope.row.id)"></el-button>
              <el-button type="primary" icon="el-icon-edit" size="small" title="编辑" @click="showUserEdit(scope.row)"></el-button>
              <el-button type="danger" icon="el-icon-delete" size="small" title="删除" @click="btnUserDelete(scope.row.id)"></el-button>          
            </template>
        </el-table-column>

    </el-table>

    <!-- 调用添加员工对话框 -->
    <user-add ref="useradd"></user-add>
    <!-- 调用修改员工对话框 -->
    <user-edit ref="useredit"></user-edit>
    <!-- 调用员工权限对话框 -->
    <user-perm ref="userperm"></user-perm>

  </div>
</template>

<script>
import UserAdd from './UserAdd.vue';
import UserEdit from './UserEdit.vue';
import UserPerm from './UserPerm.vue';

export default {
 name:'UserManager',
  data () {
    return {
      name:"",//查询条件   
      tableData:[],//要显示的信息
      postList:'',//职位列表
    };
  },
  methods: {
      //显示添加员工页面
      showUserAdd(){
        //存储部门职位
        this.$refs.useradd.postList=this.postList;
        this.$refs.useradd.UserAddVisible=true;//显示树控件
      },
      //显示修改员工页面
      showUserEdit(user){
        //存储部门职位
        this.$refs.useredit.postList=this.postList;
        this.$refs.useredit.user=user;
        this.$refs.useredit.UserEditVisible=true;//显示树控件
      },
      //显示员工权限的页面
      btnUserPerm(userid){
        this.$refs.userperm.loadPerm(userid);
        this.$refs.userperm.UserPermVisible=true;//显示树控件
      },
      //删除员工信息
      btnUserDelete(id){
        this.$confirm("确定删除该管理吗?", "系统提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.$axios
            .get("/api/user/delete",{params:{id}})
            .then((result) => {
              console.log(result.data);
              if (result.data.code === 200) {
                this.$message({
                    showClose: true,
                    message: '删除员工成功',
                    type: 'success'
                });
                //刷新列表
                this.userList();
              }else{
                this.$message({
                    showClose: true,
                    message: result.data.msg,
                    type: 'warning'
                });
              }
            })
            .catch((e) => {
              this.$message({
                showClose: true,
                message: "服务器去火星了...",
                type: "error",
              });
              console.log(e);
            });
        });

      },
      //序号
      indexMethod(index) {
        return index+1;
      },

      //用户列表
      userList(){
          this.$axios
          .get("/api/user/list",{params:{name:this.name}})
          .then(result=>{
              console.log(result.data);
              if(result.data.code===200){

                  this.postList=result.data.data.postList;
                  this.tableData=result.data.data.pageInfo;

              }else{
                  this.$message({
                    showClose: true,
                    message: result.data.msg,
                    type: "warning",
                  })
              }
          }).catch(e=>{
              this.$message({
              showClose: true,
              message: "服务器去火星了...",
              type: "error",
            });
          })
      }
  },
  components: {
      UserAdd,UserEdit,UserPerm
    },
  computed: {},
  created(){
    this.userList(); 
  }
}

</script>
<style scoped>

</style>