<!-- 添加题目的页面 -->
<template>
  <div>
    <el-row>
      <el-col :span="24">
        <!-- 题目开始 -->
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="题目:" style="margin: 20px 0">
            <el-input
              type="textarea"
              :autosize="{ minRows: 1, maxRows: 4 }"
              placeholder="请输入内容"
              v-model="pts.testname"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="是否为判断题:" style="margin: 20px 0">
            <el-radio v-model="checkjudge" label="y" @change="butcheck"
              >是</el-radio
            >
            <el-radio v-model="checkjudge" label="n" @change="butcheck"
              >否</el-radio
            >
          </el-form-item>
          <!-- 初始默认为隐藏的添加判断题开始 -->
          <div id="div2" style="display: none">
            <el-form-item label="正确与否:" style="margin: 20px 0">
              <el-radio v-model="pts.testyn" label="y">正确</el-radio>
              <el-radio v-model="pts.testyn" label="n">错误</el-radio>
            </el-form-item>
          </div>
          <!-- 初始默认为隐藏的添加判断题结束 -->
        </el-form>
        <!-- 题目结束 -->
      </el-col>
    </el-row>
    <!-- 初始默认为显示的添加选择题开始 -->
    <div id="div1">
      <el-row>
        <el-col :span="24">
          <el-button type="primary" @click="addOption">添加选项</el-button>
          <el-button type="primary" @click="delOption"
            >删除选项</el-button
          ></el-col
        >
      </el-row>
      <el-row>
        <el-col :span="24">
          <!-- 选项的添加删除开始 -->
          <div style="margin: 20px 0">选项:</div>
          <ol type="A">
            <div v-for="(item, key) in itemfo.pssTestItemList" :key="key">
              <el-form :inline="true" class="demo-form-inline">
                <el-form-item style="margin: 20px 0">
                  <li>
                    <el-input
                      type="textarea"
                      :autosize="{ minRows: 1, maxRows: 4 }"
                      placeholder="请输入内容"
                      v-model="item.testname"
                    >
                    </el-input>
                  </li>
                </el-form-item>
                <el-form-item label="是否为正确答案:" style="margin: 20px 0">
                  <el-radio v-model="item.testyn" label="y">是</el-radio>
                  <el-radio v-model="item.testyn" label="n">否</el-radio>
                </el-form-item>
              </el-form>
            </div>
          </ol>
          <!-- 选项的添加删除结束 -->
        </el-col>
      </el-row>
    </div>
    <!-- 初始默认为显示的添加选择题结束 -->
    <el-row>
      <el-col :span="24">
        <!-- 添加按钮返回按钮开始 -->
        <div>
          <el-button type="primary" @click="goBack">返回</el-button>

          <el-button type="primary" @click="addItem">添加</el-button>
        </div>
        <!-- 添加按钮返回按钮结束 -->
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "ExamAddView",
  data() {
    return {
      itemfo: {
        pssTestItem: {},
        pssTestItemList: [],
      },
      pts: {
        testname: "",
        status: "y",
        testyn: "",
      },
      checkjudge: "n",
      count: 0,
    };
  },
  created() {},
  methods: {
    addOption() {
      let psst = {
        testname: "",
        status: "y",
        testyn: "n",
      };
      this.itemfo.pssTestItemList.push(psst);
      this.count++;
    },
    delOption() {
      if (this.count > 0) {
        this.count--;
        this.itemfo.pssTestItemList.splice(this.count, 1);
      }
    },
    goBack() {
      this.$router.push({ name: "exam" });
    },
    addItem() {
      this.itemfo.pssTestItem = this.pts;
      console.info(this.itemfo);
      axios
        .post("/api/test/additem", this.itemfo)
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
    butcheck() {
      if (this.checkjudge === "n") {
        document.getElementById("div1").style.display = "";
        this.pts.testyn = "";
        document.getElementById("div2").style.display = "none";
      } else if (this.checkjudge === "y") {
        document.getElementById("div1").style.display = "none";
        this.pts.testyn = "y";
        document.getElementById("div2").style.display = "";
      }
    },
  },
  components: {},
  computed: {},
};
</script>
<style scoped></style>
