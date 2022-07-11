<!-- 展示试卷 -->
<template>
  <div>
    <!-- 试卷显示开始 -->
    <template>
      <el-table :data="testPaperShow" border style="width: 100%">
        <el-table-column type="index" label="编号" width="50">
        </el-table-column>
        <el-table-column prop="testName" label="第几套试卷" width="120">
        </el-table-column>
        <el-table-column
          prop="counts"
          label="题目数量"
          width="120"
        ></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.status === 'n' ? 'primary' : 'success'"
              disable-transitions
              ><span v-if="scope.row.status === 'n'">不可用</span
              ><span v-if="scope.row.status === 'y'">可用</span></el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 'y'">
              <el-button @click="handleClick(scope.row.testid)" type="text"
                >禁用</el-button
              >
            </span>
            <span v-if="scope.row.status === 'n'">
              <el-button @click="recoveryClick(scope.row.testid)" type="text"
                >恢复</el-button
              >
            </span>
            <el-button type="text" @click="showItem(scope.row, scope.testName)"
              >查看</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </template>
    <!-- 试卷显示结束 -->
    <!-- 显示具体试卷开始 -->
    <el-dialog
      title="试卷"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose"
    >
      <div>
        <div v-for="(item, key) in testPaper" :key="key">
          <!-- 单选 -->
          <div v-if="item.pssTestItem.testtypeid == 1">
            <div>单选题目:{{ item.pssTestItem.testname }}</div>
            <ol type="A" id="ol1">
              <el-row :gutter="24" v-for="op in item.pssTestItems" :key="op.id">
                <el-col :span="16" :offset="4"
                  ><div class="grid-content bg-purple">
                    <li>
                      <span></span>{{ op.testname
                      }}<span v-if="op.testyn === 'y'">
                        &nbsp;&nbsp;&nbsp;&nbsp;正确答案</span
                      >
                    </li>
                  </div></el-col
                >
              </el-row>
            </ol>
          </div>
          <!-- 多选 -->
          <div v-if="item.pssTestItem.testtypeid == 2">
            <div>多选题目:{{ item.pssTestItem.testname }}</div>
            <ol type="A" id="ol1">
              <el-row :gutter="24" v-for="op in item.pssTestItems" :key="op.id">
                <el-col :span="16" :offset="4"
                  ><div class="grid-content bg-purple">
                    <li>
                      <span></span>{{ op.testname
                      }}<span v-if="op.testyn === 'y'">
                        &nbsp;&nbsp;&nbsp;&nbsp;正确答案</span
                      >
                    </li>
                  </div></el-col
                >
              </el-row>
            </ol>
          </div>
          <!-- 判断 -->
          <div v-if="item.pssTestItem.testtypeid == 3">
            <span>判断题目:{{ item.pssTestItem.testname }}</span>
            <span v-if="item.pssTestItem.testyn === 'y'">答案为:正确</span>
            <span v-if="item.pssTestItem.testyn === 'n'">答案为:错误</span>
          </div>
        </div>
        <el-row>
          <el-col :span="24">
            <!-- 添加按钮返回按钮开始 -->
            <div>
              <el-button type="primary" @click="downloadPaper()"
                >下载试卷</el-button
              >
            </div>
            <!-- 添加按钮返回按钮结束 -->
          </el-col>
        </el-row>
      </div>
    </el-dialog>
    <!-- 显示具体试卷结束 -->
    <el-row>
      <el-col :span="24">
        <!-- 添加按钮返回按钮开始 -->
        <div>
          <el-button type="primary" @click="goBack">返回</el-button>
        </div>
        <!-- 添加按钮返回按钮结束 -->
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "ExamTestShow",
  data() {
    return {
      testPaperShow: [],
      dialogVisible: false,
      testPaper: [],
      name: "",
      examTestPaperFo: {},
    };
  },
  created() {
    this.showTheTestPaper();
  },
  methods: {
    showTheTestPaper() {
      axios
        .get("/api/exam/showTest")
        .then((result) => {
          console.info(result.data.data);
          if (result.data.code === 200) {
            this.testPaperShow = result.data.data;
          } else {
            //不成立提示消息
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
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    //展示试卷开始
    showItem(row, testName) {
      this.testPaper = row.itemVos;
      this.name = testName;
      this.dialogVisible = true;
      this.examTestPaperFo = row;
    },
    //展示试卷结束
    //封禁试卷开始
    handleClick(testid) {
      this.function(testid, "n");
    },
    //封禁试卷结束
    //恢复试卷开始
    recoveryClick(testid) {
      this.function(testid, "y");
    },
    //恢复试卷结束
    //调用封禁,恢复的方法开始
    function(testid, status) {
      axios
        .get("/api/exam/updateTest", { params: { testid, status } })
        .then((result) => {
          if (result.data.code === 200) {
            this.$message({
              showClose: true,
              message: "修改成功",
              type: "success",
            });
          } else {
            //不成立提示消息
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
          this.showTheTestPaper();
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    //调用封禁,恢复的方法结束

    // 弹窗的开关开始
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    // 弹窗的开关结束
    downloadPaper() {
      axios
        .post("/api/exam/paperdownload", this.examTestPaperFo)
        .then((result) => {
          if (result.data.code === 200) {
            window.location.href = result.data.data;
          } else {
            //不成立提示消息
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
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    // 返回功能开始
    goBack() {
      this.$router.push({ name: "exam" });
    },
    // 返回功能结束
  },
  components: {},
  computed: {},
};
</script>
<style scoped></style>
