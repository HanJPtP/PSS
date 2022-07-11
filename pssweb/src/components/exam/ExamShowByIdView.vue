<!-- 根据id显示题目 -->
<template>
  <div>
    <el-row :gutter="24">
      <el-col :span="18" :offset="3"
        ><div class="grid-content bg-purple">
          <span>题目:</span>{{ subject.testname }}
        </div></el-col
      >
    </el-row>
    <div id="div1s">
      <ol type="A" id="ol1">
        <!--eslint-disable-next-line-->
        <el-row :gutter="24" v-for="op in options" :key="op.id">
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
    <div id="div2s">
      <span v-if="subject.testyn === 'y'">答案为:正确</span>
      <span v-if="subject.testyn === 'n'">答案为:错误</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "ExamShowByIdView",
  data() {
    return {
      subject: {},
      options: [],
    };
  },
  created() {
    this.showItem(this.$route.params.id);
  },
  methods: {
    showItem(id) {
      axios
        .get("/api/test/showitemvo", { params: { id } })
        .then((result) => {
          if (result.data.code === 200) {
            console.info(result.data.data);
            this.subject = result.data.data.pssTestItem;
            this.options = result.data.data.pssTestItems;
            if (this.options.length > 0) {
              document.getElementById("div1s").style.display = "";
              document.getElementById("div2s").style.display = "none";
            } else if (this.options.length <= 0) {
              document.getElementById("div2s").style.display = "";
              document.getElementById("div1s").style.display = "none";
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
            message: "网络波动异常",
            type: "error",
          });
        });
    },
  },
  components: {},
  computed: {},
};
</script>
<style scoped>
#ol1 .el-row {
  margin-top: 10px;
}
</style>
