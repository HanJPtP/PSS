<!-- 显示生成的考试题目 -->
<template>
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
          <el-button type="primary" @click="goBack">返回</el-button>

          <el-button type="primary" @click="addTestPaper">生成试卷</el-button>
        </div>
        <!-- 添加按钮返回按钮结束 -->
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "ExamTestPaper",
  data() {
    return {
      testPaper: [],
    };
  },
  created() {
    this.testPaper = this.$route.params.testPaper;
    console.info(this.testPaper);
  },
  methods: {
    goBack() {
      this.$router.push({ name: "exam" });
    },
    //生成试卷开始
    addTestPaper() {
      axios
        .post("/api/test/addTestPaper", this.testPaper)
        .then((result) => {
          if (result.data.code === 200) {
            //如果成立跳转过去
            this.$router.push({ name: "exam" });
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
    //生成试卷结束
  },
  components: {},
  computed: {},
};
</script>
<style scoped></style>
