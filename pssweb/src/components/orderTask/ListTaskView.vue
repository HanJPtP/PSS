<!-- 派工单列表 -->
<template>
  <div id="task">
    <!-- 导航开始 -->
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '' }">工单管理</el-breadcrumb-item>
          <el-breadcrumb-item>派工单列表</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <!-- 导航结束 -->
    <!-- 条件查询开始 -->
    <el-row>
      <el-col :span="24">
        <el-form :inline="true" class="demo-form-inline">
          <div style="margin-top: 15px">
            <el-form-item label="工单编号">
              <el-input
                v-model="formInline.orderno"
                placeholder="请输入工单编号"
              ></el-input>
            </el-form-item>
            <el-form-item label="工单类型">
              <el-select
                v-model="formInline.ordertypeid"
                placeholder="请选择工单类型"
              >
              <el-option label="请选择工单类型" :value="-1"></el-option>
                <el-option
                  v-for="types in pssOrderTypes"
                  :key="types.id + ''"
                  :label="types.typename"
                  :value="types.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="负责人编号">
              <el-input
                v-model="formInline.userno"
                placeholder="请输入负责人编号"
              ></el-input>
            </el-form-item>
            <el-form-item label="工单状态">
              <el-select
                v-model="formInline.orderstatus"
                placeholder="请选择工单状态"
              >
               <el-option label="所有工单状态" :value="-1"></el-option>
                <el-option label="未发放" value="0"></el-option>
                <el-option label="未完成" value="1"></el-option>
                <el-option label="待校验" value="2"></el-option>
                <el-option label="已完成" value="3"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="queryTask">查询</el-button>
            </el-form-item>
            <el-button
              type="primary"
              icon="el-icon-user-solid"
              @click="showAddTask"
              >添加派工单</el-button
            >
          </div>
        </el-form>
      </el-col>
    </el-row>
    <!-- 条件查询结束 -->
    <!-- 数据列表开始 -->
    <el-row>
      <el-col :span="24">
        <el-table
          :data="taskItem"
          border
          style="width: 100%"
          fit="fit"
          stripe="stripe"
        >
          <el-table-column
            align="center"
            label="序号"
            type="index"
            :index="indexMethod"
            width="80px"
          ></el-table-column>
          <el-table-column
            align="center"
            width="170px"
            prop="orderno"
            label="工单编号"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="pssOrderType.typename"
            label="工单类型"
            width="100px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="userno"
            label="负责人编号"
            width="150px"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="worktask"
            label="工单任务"
            width="300px"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="starttime"
            :formatter="formateDate"
            label="开始时间"
            width="140px"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="endtime"
            :formatter="formateDate"
            label="结束时间"
            width="140px"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="ordermsg"
            label="工单备注"
            width="150px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="orderstatus"
            label="工单状态"
            width="100px"
          >
            <!-- 可以通过template的slot-scope这个属性获得当前行的所有数据 -->
            <template slot-scope="scope">
              <span v-if="scope.row.orderstatus == '0'">
                <el-tag type="success" effect="dark">未发放</el-tag>
              </span>
              <span v-if="scope.row.orderstatus == '1'">
                <el-tag type="warning" effect="dark">未完成</el-tag>
              </span>
              <span v-if="scope.row.orderstatus == '2'">
                <el-tag type="success" effect="dark">待校验</el-tag>
              </span>
              <span v-if="scope.row.orderstatus == '3'">
                <el-tag type="warning" effect="dark">已完成</el-tag>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="grade"
            label="工单评分"
            width="50px"
          ></el-table-column>
          <el-table-column
            align="center"
            fixed="right"
            label="操作"
            width="150"
          >
          <template slot-scope="scope">
              <el-button
                type="primary"
                icon="el-icon-edit"
                title="修改"
                @click="showupdate(scope.row)"
              ></el-button>
              <el-button
                type="primary"
                icon="el-icon-bottom"
                title="下达"
                @click="addprocess(scope.row)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <!-- 数据列表结束 -->
    <!-- 分页开始 -->
    <el-row>
      <el-col :span="24">
        <div style="margin-top: 15px; text-align: center">
          <el-pagination
            background
            :page-sizes="[5, 10, 15, 20, 25]"
            :page-size="pagesize"
            layout="prev,pager,next,sizes,total"
            :total="total"
            :page-count="pagecount"
            :current-page="currentpage"
            @size-change="chageSize"
            @current-change="chageCurrent"
            @prev-click="chageCurrent"
            @next-click="chageCurrent"
          >
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <!-- 分页结束 -->
      <!-- 添加组件-->
  <add-task-view ref="addTask"></add-task-view>
  <!-- 修改组件-->
  <update-task-view ref="updateTask"></update-task-view>
  </div>



</template>

<script>

// 引入添加组件
import addTaskView from "@/components/orderTask/AddTaskView.vue";
// 引入修改组件
import updateTaskView from "@/components/orderTask/UpdateTaskView.vue";

export default {
  name: "ListTaskView",
  data() {
    return {
      formInline: {
        orderno: "",
        ordertypeid: -1,
        userno: "",
        orderstatus: -1
      },
      pssOrderTypes: [],
      usersList:[],
      taskItem: [], //要显示的数据
      total: 0, //分页控件总条数
      pagecount: 0, //分页控件总页数
      currentpage: 1, //分页控件当前页码
      pagesize: 5, //分页控件每页条数
    };
  },
  methods: {
    //多条件查询讲师信息
    queryTask() {
      this.$axios
        .get("/api/taskorder/list", {
          params: {
            orderno: this.formInline.orderno,
            ordertypeid: this.formInline.ordertypeid,
            userno: this.formInline.userno,
            orderstatus:this.formInline.orderstatus,
            pageNo: this.currentpage,
            pageSize: this.pagesize
          },
        })
        .then((result) => {
          console.log(result.data);
          if (result.data.code === 200) {
            this.taskItem = result.data.data.pageInfo.data;

            //设置分页数据
            this.total = result.data.data.pageInfo.totalNum;
            this.pagecount = result.data.data.pageInfo.totalPage;
            this.currentpage = result.data.data.pageInfo.pageNo;
            this.pagesize = result.data.data.pageInfo.pageSize;
            this.pssOrderTypes = result.data.data.pssOrderTypes;
            this.usersList = result.data.data.usersList;
            console.log(result.data.data.pageInfo);
          } else {
            this.pssOrderTypes = result.data.data.pssOrderTypes;
            this.usersList = result.data.data.usersList;
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络环境差。。。",
            type: "error",
          });
          console.log(e);
        });
    },
    indexMethod(index) {
      //表格自定义序号
      return index + 1;
    },
    chageSize(pagesize) {
      //改变每页条数下拉框事件，通过形参获得当前选中的条数
      this.currentpage = 1; //当前页码改为1
      this.pagesize = pagesize;
      //调用查询方法
      this.queryTask();
    },
    chageCurrent(current) {
      //改变页码时事件，接收一个被改变后的页码
      this.currentpage = current; //当前页码改为1
      //调用查询方法
      this.queryTask();
    },showAddTask(){
       //添加需要权限判断
       console.log(this.usersList);
        this.$axios.post('/api/taskorder/showadd')
        .then(result=>{
          if(result.data.code===200){
            //显示添加页面
            this.$refs.addTask.pssOrderTypes=this.pssOrderTypes;
            this.$refs.addTask.usersList=this.usersList;
            this.$refs.addTask.dialogVisible=true;
          }else{
            this.$message({
            showClose: true,
            message: result.data.msg,
            type: "warning",
          });
          }
     }).catch(e=>{
           this.$message({
            showClose: true,
            message: "网络环境差。。。",
            type: "error",
          });
          console.log(e);
        });
     

    },changetime(mytime){
      //转换时间格式 yyyy-MM-dd
      let year = mytime.getFullYear();
      let month = mytime.getMonth() + 1;
      if(month <10){
        month='0'+month;
      }
      let date = mytime.getDate();
      if(date < 10){
        date = '0'+date;
      }
      return year + '-' + month + '-' + date;
    },
    showupdate(pssOrderTask) {
      //显示修改讲师子组件
      //添加需要权限判断
      console.log(pssOrderTask);
        this.$axios.post('/api/taskorder/showupdate')
        .then(result=>{
          if(result.data.code===200){
            //改变时间格式
            let st = new Date(pssOrderTask.starttime);
            let et = new Date(pssOrderTask.endtime);
            let starttime = this.changetime(st)
            let endtime = this.changetime(et); 
            console.log(starttime);
            //显示添加页面
            this.$refs.updateTask.starttime= starttime;
            this.$refs.updateTask.endtime= endtime;
            this.$refs.updateTask.pssOrderTypes=this.pssOrderTypes;
            this.$refs.updateTask.pssOrderTask=pssOrderTask;
            this.$refs.updateTask.usersList=this.usersList;
            this.$refs.updateTask.dialogVisible=true;
          }else{
            this.$message({
            showClose: true,
            message: result.data.msg,
            type: "warning",
          });
          }
     }).catch(e=>{
           this.$message({
            showClose: true,
            message: "网络环境差。。。",
            type: "error",
          });
          console.log(e);
        });
    },addprocess(pssOrderTask){
         this.$axios.post('/api/taskorder/addprocess',pssOrderTask)
        .then(result=>{
          if(result.data.code===200){
            this.$message({
            showClose: true,
            message: result.data.msg,
            type: "success",
          });
          }else{
            this.$message({
            showClose: true,
            message: result.data.msg,
            type: "warning",
          });
          }
     }).catch(e=>{
           this.$message({
            showClose: true,
            message: "网络环境差。。。",
            type: "error",
          });
          console.log(e);
        });
    },formateDate(row, column){
      //转换时间格式
      let data = row[column.property]
      if(data == null){
        return null;
      }
      let dt = new Date(data);
      return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds();  
    }
  },
  components: {
    addTaskView,
    updateTaskView
  },
  computed: {},
  created() {
    this.queryTask();
  },
};
</script>
<style scoped>
#task {
  width: 100%;
  height: 100%;
}
</style>
