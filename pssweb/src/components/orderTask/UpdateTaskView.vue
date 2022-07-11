<!--  -->
<template>
  <div>
    <el-dialog
      title="修改工单"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose"
    >
      <el-row type="flex" justify="center" align="middle">
        <el-col :span="18">
          <el-form :inline="true">
            <el-form-item label="工单负责人">
              <el-select v-model="pssOrderTask.userno" placeholder="请选择">
                <el-option
                  v-for="item in usersList"
                  :key="item.id + ''"
                  :label="item.jobno"
                  :value="item.jobno"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="工单类型:">
              <el-select
                v-model="pssOrderTask.ordertypeid"
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
            <el-form-item label="工单任务:">
              <el-input
                type="textarea"
                rows="3"
                cols="34"
                v-model="pssOrderTask.worktask"
              ></el-input>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-input type="date" v-model="starttime"></el-input>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-input type="date" v-model="endtime"></el-input>
            </el-form-item>
            <el-form-item label="工单状态">
              <el-select
                v-model="pssOrderTask.orderstatus"
                placeholder="请选择工单状态"
              >
                <el-option label="未发放" :value="0"></el-option>
                <el-option label="未完成" :value="1"></el-option>
                <el-option label="待校验" :value="2"></el-option>
                <el-option label="已完成" :value="3"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="工单备注:">
              <el-input
                type="textarea"
                rows="3"
                cols="34"
                v-model="pssOrderTask.ordermsg"
              ></el-input>
            </el-form-item>
            <el-form-item label="工单评分">
              <el-select
                v-model="pssOrderTask.grade"
                placeholder="请选择工单评分"
              >
                <el-option label="-5分" :value="-5"></el-option>
                <el-option label="-4分" :value="-4"></el-option>
                <el-option label="-3分" :value="-3"></el-option>
                <el-option label="-2分" :value="-2"></el-option>
                <el-option label="-1分" :value="-1"></el-option>
                <el-option label="0分" :value="0"></el-option>
                <el-option label="1分" :value="1"></el-option>
                <el-option label="2分" :value="2"></el-option>
                <el-option label="3分" :value="3"></el-option>
                <el-option label="4分" :value="4"></el-option>
                <el-option label="5分" :value="5"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="20">
            <el-button @click="returntool" type="primary">归还工具</el-button>
            <el-button @click="btnexit">取 消</el-button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <el-button type="primary" @click="updatebtn">修 改</el-button>
          </el-col>
        </el-row>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "AddTaskView",
  data() {
    return {
      dialogVisible: false,
      pssOrderTask: {
        id: "",
        orderno: "",
        userno: "",
        ordertypeid: -1,
        worktask: "",
        starttime: "",
        endtime: "",
        ordermsg: "",
        orderstatus: "",
        grade: "",
      },
      pssOrderTypes: [],
      usersList: [],
      starttime: "",
      endtime: "",
    };
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    updatebtn() {
      this.$axios
        .post("/api/taskorder/update", this.pssOrderTask)
        .then((result) => {
          console.log(result.data);
          console.log(this.pssOrderTask);
          if (result.data.code === 200) {
            //添加成功
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "success",
            });
            //隐藏弹窗
            this.btnexit();
            this.$forceUpdate();
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
    btnexit() {
      //取消按钮
      this.pssOrderTask = {
        id: "",
        userno: "",
        ordertypeid: -1,
        worktask: "",
        starttime: "",
        endtime: "",
        ordermsg: "",
        ordertypeid: "",
        grade: "",
      };
      this.dialogVisible = false;
    },
    updatebtn() {
      this.$axios
        .post("/api/taskorder/update", this.pssOrderTask)
        .then((result) => {
          console.log(result.data);
          console.log(this.pssOrderTask);
          if (result.data.code === 200) {
            //修改成功
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "success",
            });
            //隐藏弹窗
            this.btnexit();
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
    returntool() {
      //归还工具（状态时待校验时）
      this.$axios
        .get("/api/taskorder/tback", {
          params: { orderno: this.pssOrderTask.orderno },
        })
        .then((result) => {
          if (result.data.code === 200) {
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "success",
            });
            //归还成功，修改后台数据库
            this.$axios
              .get("/api/inout/auto", {
                params: { orderno: this.pssOrderTask.orderno },
              })
              .then((result) => {
                if (result.data.code === 200) {
                  this.$message({
                    showClose: true,
                    message: result.data.msg,
                    type: "success",
                  });
                }
                //隐藏弹窗
                this.btnexit();
              })
              .catch((e) => {
                this.$message({
                  showClose: true,
                  message: "网络环境差。。。",
                  type: "error",
                });
                console.log(e);
              });
          } else {
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
            this.btnexit();
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
  components: {},
  computed: {},
};
</script>
<style scoped></style>
