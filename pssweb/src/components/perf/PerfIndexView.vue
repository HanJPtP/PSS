<!-- 考核组件 -->
<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item :to="{ path: 'perf' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>绩效管理</el-breadcrumb-item>
    <el-breadcrumb-item :to="{ path: 'perf' }">员工绩效管理</el-breadcrumb-item>
    </el-breadcrumb><br/>
         
    <!-- 表格数据 -->
    <el-table :data="staffsItem" style="width: 100%"  max-height="460" fit="fit"
        stripe="stripe" >
        <el-table-column align="center" label="序号" type="index" :index="indexMethod">
        </el-table-column>
        <el-table-column align="center" prop="jobno" label="工号"></el-table-column>
        <el-table-column align="center" prop="username" label="员工姓名"></el-table-column>

      
        <el-table-column align="center" prop="right" label="操作">
            <template slot-scope="scope">
              <el-button type="primary" size="small" title="绩效考核" @click="btnPerfSel(scope.row)">绩效考核</el-button>         
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
    <perf-option-view ref="perfOption"></perf-option-view>
  </div>
</template>
<script>
import PerfOptionView from '@/components/perf/PerfOptionView.vue';

export default {
 name:'PerfIndexView',
  data () {
    return {
        account:'',//用户名
        staffsItem:[],//要显示的信息
        total: 0, //分页控件总条数
        pagecount: 0, //分页控件总页数
        currentpage: 1, //分页控件当前页码
        pagesize: 5, //分页控件每页条数
    };
  },
  methods: {
      
      //序号
      indexMethod(index) {
        return index+1;
      },
      //员工列表
      staffList(){
          this.$axios
          .get("/api/quota/list",{params:{account:this.account,
          pageNo:this.currentpage,pageSize:this.pagesize}})
          .then(result=>{
              console.log(result.data);
              if(result.data.code===200){
                  
                  this.staffsItem=result.data.data.data;

                  //设置分页数据
                  this.currentpage=result.data.data.pageNo;//的前页
                  this.pagesize=result.data.data.pageSize;//每页条数
                  this.pagecount=result.data.data.totalPage;//总页数
                  this.total=result.data.data.totalNum;//总条数

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
      btnPerfSel(staffs){
          if(confirm("确定要对该员工进行绩效考核吗?")){
            this.$axios.get("/api/quota/getquota",{params:{account:this.account}})
            .then(result=>{
                console.log(result.data);
                this.$refs.perfOption.quotaItem = result.data.data;
                this.$refs.perfOption.staffs = staffs;
                this.$refs.perfOption.appname = this.account;
                this.$refs.perfOption.dialogOption = true;
            }).catch(e=>{
              this.$message({
              showClose: true,
              message: "服务器去火星了...",
              type: "error",
            });
            })
          }
      },
  },
  components: {PerfOptionView},
  computed: {},
  created(){
    this.account=localStorage.getItem("username");
    this.staffList();
  }
}

</script>
<style scoped>
</style>