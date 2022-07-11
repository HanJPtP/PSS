<!-- 巡视单列表 -->
<template>
  <div id="task">
    <!-- 导航开始 -->
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '' }">工单as管理</el-breadcrumb-item>
          <el-breadcrumb-item>巡视单列表</el-breadcrumb-item>
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
            <el-form-item label="工单状态">
              <el-select
                v-model="formInline.problemtype"
                placeholder="请选择工单状态"
              >
                <el-option label="所有类型" :value="-1"></el-option>
                <el-option label="正常" :value="0"></el-option>
                <el-option label="维护维修" :value="1"></el-option>
                <el-option label="抢修装拆" :value="2"></el-option>
                <el-option label="未处理" :value="3"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="处理人编号">
              <el-input
                v-model="formInline.handlerno"
                placeholder="请输入负责人编号"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="queryTask">查询</el-button>
            </el-form-item>
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
            width="50px"
          ></el-table-column>
          <el-table-column
            align="center"
            width="250px"
            prop="orderno"
            label="工单编号"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="typename"
            label="工单类型"
            width="100px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="ordertitle"
            label="工单任务"
            width="300px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="starttime"
            :formatter="formateDate"
            label="开始时间"
            width="150px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="endtime"
            :formatter="formateDate"
            label="结束时间"
            width="150px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="arearno"
            label="区域编号"
            width="150px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="problemtype"
            label="问题类型"
            width="100px"
          >
            <template slot-scope="scope">
              <span v-if="scope.row.problemtype == '0'">
                <el-tag type="success" effect="dark">正常</el-tag>
              </span>
              <span v-if="scope.row.problemtype == '1'">
                <el-tag type="warning" effect="dark">维护维修</el-tag>
              </span>
              <span v-if="scope.row.problemtype == '2'">
                <el-tag type="success" effect="dark">抢修装拆</el-tag>
              </span>
              <span v-if="scope.row.problemtype == '3'">
                <el-tag type="success" effect="dark">未处理</el-tag>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            fixed="right"
            label="操作"
            width="250"
          >
            <template slot-scope="scope">
              <el-button
                type="primary"
                icon="el-icon-document"
                title="查看详情"
                @click="showdetail(scope.row)"
              ></el-button>
              <el-button
                type="primary"
                icon="el-icon-edit"
                title="填写"
                @click="showupdateProcess(scope.row)"
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
    <!-- 修改组件-->
    <update-patrol-view ref="updatepatrol"></update-patrol-view>
    <!-- 详情组件 -->
    <detail-view ref="showdetail"></detail-view>
  </div>
</template>

<script>
// 引入修改（填写）组件
import updatePatrolView from "@/components/orderprocess/processpatrol/UpdatePatrolView.vue";
//引入查看详情组件
import detailView from "@/components/orderprocess/processpatrol/DetailView.vue";

export default {
  name: "ListTaskView",
  data() {
    return {
      formInline: {
        orderno: "",
        handlerno: "",
        ordertypeid: 1,
        areano: "",
        problemtype: -1,
      },
      areaList: [],
      usersList: [],
      pssOrderTypes: [],
      toolsList: [],
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
      //情况this.taskItem
      this.taskItem = new Array();
      this.$axios
        .get("/api/processorder/list", {
          params: {
            orderno: this.formInline.orderno,
            handlerno: this.formInline.handlerno,
            ordertypeid: this.formInline.ordertypeid,
            areano: this.formInline.areano,
            problemtype: this.formInline.problemtype,
            pageNo: this.currentpage,
            pageSize: this.pagesize,
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
            this.areaList = result.data.data.listAllCname;
            this.usersList = result.data.data.usersList;
            this.toolsList = result.data.data.toolsList;
          } else {
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
    },
    changetime(mytime) {
      //转换时间格式 yyyy-MM-dd
      let year = mytime.getFullYear();
      let month = mytime.getMonth() + 1;
      if (month < 10) {
        month = "0" + month;
      }
      let date = mytime.getDate();
      if (date < 10) {
        date = "0" + date;
      }
      return year + "-" + month + "-" + date;
    },
    showupdateProcess(pssOrderProcess) {
      //显示修改讲师子组件
      //添加需要权限判断
      console.log(pssOrderProcess);
      this.$axios
        .post("/api/processorder/showadd")
        .then((result) => {
          if (result.data.code === 200) {
            //改变时间格式
            let st = new Date(pssOrderProcess.starttime);
            let et = new Date(pssOrderProcess.endtime);
            let starttime = this.changetime(st);
            let endtime = this.changetime(et);
            //显示修改页面
            this.$refs.updatepatrol.starttime = starttime;
            this.$refs.updatepatrol.endtime = endtime;
            this.$refs.updatepatrol.areaList = this.areaList;
            this.$refs.updatepatrol.usersList = this.usersList;
            this.$refs.updatepatrol.pssOrderProcess = pssOrderProcess;
            this.$refs.updatepatrol.gethandleroptions();
            this.$refs.updatepatrol.getareaoptions();
            this.$refs.updatepatrol.dialogVisible = true;
          } else {
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
    formateDate(row, column) {
      //转换时间格式
      let data = row[column.property];
      if (data == null) {
        return null;
      }
      let dt = new Date(data);
      return (
        dt.getFullYear() +
        "-" +
        (dt.getMonth() + 1) +
        "-" +
        dt.getDate() +
        " " +
        dt.getHours() +
        ":" +
        dt.getMinutes() +
        ":" +
        dt.getSeconds()
      );
    },
    showdetail(pssOrderProcess) {
      //查看详情showdetail
      console.log(pssOrderProcess.pssWhdataList);
      console.log(this.toolsList);
      this.$refs.showdetail.pssOrderProcess = pssOrderProcess;
      this.$refs.showdetail.handlerList = pssOrderProcess.handlerList;
      this.$refs.showdetail.toolsList = this.toolsList;
      this.$refs.showdetail.starttime = this.changetime(
        new Date(pssOrderProcess.starttime)
      );
      this.$refs.showdetail.endtime = this.changetime(
        new Date(pssOrderProcess.endtime)
      );
      this.$refs.showdetail.dialogVisible = true;
    },
  },
  components: {
    updatePatrolView,
    detailView,
  },
  computed: {},
  created() {
    this.formInline.problemtype = 3;
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
