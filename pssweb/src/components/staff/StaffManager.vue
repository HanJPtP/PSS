<!--  -->
<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item :to="{ path: 'home' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>角色管理</el-breadcrumb-item>
    <el-breadcrumb-item :to="{ path: 'staff' }">员工列表</el-breadcrumb-item>
    </el-breadcrumb><br/>

    <!-- 条件查询 -->
    <el-form :inline="true"  class="demo-form-inline">
      
      <!-- 职位部门选择 -->
      <el-form-item label="职位：">
        <el-select v-model="postid" clearable placeholder="请选择职位">
        <el-option
          v-for="item in postList"
          :key="item.id"
          :label="item.pname"
          :value="item.id">
        </el-option>
      </el-select>
      </el-form-item>
      
      <!-- 员工名查询 -->
      <el-form-item label="员工名：">
          <el-input placeholder="请输入员工名" v-model="name">
          <el-button slot="append" icon="el-icon-search" @click="staffList()">搜索</el-button>
          </el-input>
      </el-form-item>

      <el-form-item>
          <el-button type="primary" @click="showStaffAdd()">添加</el-button>
      </el-form-item>
    </el-form>
    
    <!-- 表格数据 -->
    <el-table :data="tableData" border style="width: 100%"  max-height="460" fit >
        <el-table-column align="center" label="序号" type="index" :index="indexMethod" width="80px">
        </el-table-column>
        <el-table-column align="center" prop="jobno" label="工号" width="100px"></el-table-column>
        <el-table-column align="center" prop="username" label="用户名" width="160px"></el-table-column>
        <el-table-column align="center" prop="phone" label="手机号" width="200px"></el-table-column>
        <el-table-column align="center" prop="loginTime" label="最近登录时间">
        </el-table-column>
        <el-table-column align="center" prop="loginIp" label="最近登录IP" width="160px"></el-table-column>

         <el-table-column align="center" prop="postid" label="职位" width="100px">
            <template slot-scope="scope">
                <span v-if="scope.row.postid==4">安全质量员</span>
                <span v-if="scope.row.postid==5">运维技术员</span>
                <span v-if="scope.row.postid==6">客户服务员</span>
                <span v-if="scope.row.postid==7">综合柜员</span>
                <span v-if="scope.row.postid==8">客户代表</span>
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
              <el-button type="success" icon="el-icon-key" size="small" title="授权" @click="btnStaffPerm(scope.row.id)"></el-button>
              <el-button type="primary" icon="el-icon-edit" size="small" title="编辑" @click="showStaffEdit(scope.row)"></el-button>
              <el-button type="danger" icon="el-icon-delete" size="small" title="删除" @click="btnStaffDelete(scope.row.id)"></el-button>         
            </template>
        </el-table-column>

    </el-table>

    <!-- 分页 -->
    <el-pagination
      background
      style="text-align: center; margin-top: 20px;"
      :page-sizes="[5, 10, 20, 40]"  
      layout=" prev, pager, next, sizes, total"
      :page-size="pagesize" 
      :total="total"
      :page-count="pagecount"
      :current-page="currentpage"

        @size-change="changeSize"
        @current-change="changePage"
        @prev-click="changePage"
        @next-click="changePage">
    </el-pagination>

    <!-- 调用添加员工对话框 -->
    <staff-add ref="staffadd"></staff-add>
    <!-- 调用修改员工对话框 -->
    <staff-edit ref="staffedit"></staff-edit>
    <!-- 调用员工权限对话框 -->
    <staff-perm ref="staffperm"></staff-perm>

  </div>
</template>

<script>
import StaffAdd from './StaffAdd.vue';
import StaffEdit from './StaffEdit.vue';
import StaffPerm from './StaffPerm.vue';

export default {
 name:'StaffManager',
  data () {
    return {
        name:"",//查询条件
        username:'',//用户名
        postid:'',//选择的职位
        postList:'',//职位列表
        tableData:[],//要显示的信息
        total: 0, //分页控件总条数
        pagecount: 0, //分页控件总页数
        currentpage: 1, //分页控件当前页码
        pagesize: 5, //分页控件每页条数
    };
  },
  methods: {
    //显示添加员工页面
      showStaffAdd(){
        //存储部门职位
        this.$refs.staffadd.postList=this.postList;
        this.$refs.staffadd.StaffAddVisible=true;//显示树控件
      },
      //显示修改员工页面
      showStaffEdit(user){
        //存储部门职位
        this.$refs.staffedit.postList=this.postList;
        this.$refs.staffedit.staff=user;
        this.$refs.staffedit.StaffEditVisible=true;//显示树控件
      },
      //显示员工权限的页面
      btnStaffPerm(userid){
        this.$refs.staffperm.loadPerm(userid);
        this.$refs.staffperm.StaffPermVisible=true;//显示树控件
      },
      
      //序号
      indexMethod(index) {
        return index+1;
      },

      //员工列表
      staffList(){
          this.$axios
          .get("/api/staff/list",{params:{name:this.name,username:this.username,postid:this.postid,
          pageNo:this.currentpage,pageSize:this.pagesize}})
          .then(result=>{
              console.log(result.data);
              if(result.data.code===200){
              
                  this.postList=result.data.data.postList;
                  this.tableData=result.data.data.pageInfo.data;

                  //设置分页数据
                  this.currentpage=result.data.data.pageInfo.pageNo;//的前页
                  this.pagesize=result.data.data.pageInfo.pageSize;//每页条数
                  this.pagecount=result.data.data.pageInfo.totalPage;//总页数
                  this.total=result.data.data.pageInfo.totalNum;//总条数

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
      },

      //改变每页条数3
      changeSize(pagesize){
          this.currentpage=1;
          this.pagesize=pagesize;
          this.staffList();
      },

      //改变当前页
      changePage(current){
          this.currentpage=current;
          this.staffList();
      },

      //删除员工信息
      btnStaffDelete(id){
        this.$confirm("确定删除该员工吗?", "系统提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.$axios
            .get("/api/staff/delete",{params:{id}})
            .then((result) => {
              console.log(result.data);
              if (result.data.code === 200) {
                this.$message({
                    showClose: true,
                    message: '删除员工成功',
                    type: 'success'
                });
                //刷新列表
                this.staffList();
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
      }
  },
  components: {
    StaffAdd,StaffEdit,StaffPerm
  },
  computed: {},
  created(){
    this.username=localStorage.getItem("username");
    this.staffList();
  }

}

</script>
<style scoped>
</style>