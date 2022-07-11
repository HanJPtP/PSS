<!-- 审批界面 -->
<template>
  <div>
    <!-- 条件查询开始 -->
    <el-row>
      <el-col :span="10">
        <el-form :inline="true" class="demo-form-inline">
          <div style="margin-top: 15px">
            <el-form-item label="审批类型：">
              <el-select
                clearable
                v-model="approvalid"
                value=""
                placeholder="请选择审批类型"
                @change="queryApproval"
              >
                <el-option
                  v-for="approval in approvaltypeItem"
                  :key="approval.id + ''"
                  :label="approval.type"
                  :value="approval.id"
                ></el-option>
              </el-select>
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
          :data="approval.data"
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
            width="350px"
            prop="approvalno"
            label="审批编号"
          ></el-table-column>
          <el-table-column
            align="center"
            width="150px"
            prop="users.username"
            label="姓名"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="approvaltype.type"
            label="审批类型"
            width="300px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="approvaltime"
            label="审批时间"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="notes"
            label="审批备注"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="status"
            label="审批状态"
            width="120px"
          >
            <!-- 可以通过template的slot-scope这个属性获得当前行的所有数据 -->
            <template slot-scope="scope">
              <span v-if="scope.row.status == '0'">
                <el-tag type="warning" effect="dark">未处理</el-tag>
              </span>
              <span v-if="scope.row.status != '0'">
                <el-tag type="success" effect="dark">已处理</el-tag>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            fixed="right"
            label="操作"
            width="150px"
          >
            <template slot-scope="scope">
              <el-button
                type="primary"
                icon="el-icon-edit"
                title="审批"
                @click="btnupdate(scope.row)"
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
    <!-- 调用审批请假组件 -->
    <leave-approval ref="leave"></leave-approval>
    <cardup-view ref="cardup"></cardup-view>
    <evection-approval ref="evection"></evection-approval>
  </div>
</template>

<script>
import CareupView from "./CardupView.vue";
import EvectionApproval from "./EvectionApproval.vue";
import LeaveApproval from "./LeaveApproval.vue";
export default {
  name: "ApprovalView",
  data() {
    return {
      approvalid: "", //查询条件
      approval: [],
      approvaltypeItem: [],
      total: 0, //分页控件总条数
      pagecount: 0, //分页控件总页数
      currentpage: 1, //分页控件当前页码
      pagesize: 5, //分页控件每页条数
    };
  },
  methods: {
    loadapprovaltype() {
      this.$axios
        .get("/api/approvaltype/list")
        .then((result) => {
          if (result.data.code === 200) {
            this.approvaltypeItem = result.data.data;
            // console.log(approvaltypeItem);
          } else {
            this.$message({
              showClose: true,
              message: "网络环境差。。。",
              type: "error",
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
    //多条件查询审批信息
    queryApproval() {
      this.$axios
        .get("/api/approval/list", {
          params: {
            approvalid: this.approvalid,
            pageNo: this.currentpage,
            pageSize: this.pagesize,
          },
        })
        .then((result) => {
          // console.log(result.data);
          if (result.data.code === 200) {
            this.approval = result.data.data;
            // console.log(approval);
            //设置分页数据
            this.total = result.data.data.totalNum;
            this.pagecount = result.data.data.totalPage;
            this.currentpage = result.data.data.pageNo;
            this.pagesize = result.data.data.pageSize;
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
      this.queryApproval();
    },
    chageCurrent(current) {
      //改变页码时事件，接收一个被改变后的页码
      this.currentpage = current; //当前页码改为1
      //调用查询方法
      this.queryApproval();
    },
    btnupdate(approval) {
      this.$axios
        .post("/api/approval/updateshow")
        .then((result) => {
          if (result.data.code === 200) {
            //通过子组件的引用传递，获得子组件中的变量
            if (this.approval.approvalid = 2) {
              this.$refs.leave.approval = approval;
              this.$refs.leave.dialogLeave = true;
            }
            if (this.approval.approvalid = 1) {
              this.$refs.cardup.approval = approval;
              this.$refs.cardup.dialogCard = true;
            }
            if (this.approval.approvalid = 3) {
              this.$refs.evection.approval = approval;
              this.$refs.evection.dialogEvection = true;
            }
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
  },
  components: {
    LeaveApproval,
    CareupView,
    EvectionApproval,
  },
  computed: {},
  mounted: {},
  created() {
    this.loadapprovaltype();
    this.queryApproval();
  },
};
</script>
<style scoped>
</style>