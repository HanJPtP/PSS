<!-- 考核选项子组件 -->
<template>
  <div>
    <el-dialog
      title="考核选项"
      :close-on-press-escape="true"
      :close-on-click-modal="true"
      :center="true"
      width="70%"
      :visible.sync="dialogOption"
      ref="drawer"
    >
      <div class="demo-drawer__content">
        <!-- 表格数据 -->
        <el-table
          :data="quotaItem"
          border
          style="width: 100%"
          max-height="460"
          fit
        >
          <el-table-column
            align="center"
            label="序号"
            type="index"
            :index="indexMethod"
          >
          </el-table-column>
          <el-table-column
            align="center"
            prop="quotaName"
            label="考核指标"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="quotaStand"
            label="考核标准"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="quotaContex"
            label="指标评级"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="totalSco"
            label="指标总分"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="weight"
            label="指标权重"
          ></el-table-column>
          <el-table-column align="center" label="指标得分" width="200px">
            <template slot-scope="scope">
              <el-input-number
                v-model="scope.row.score"
                :min="0"
                :max="100"
              ></el-input-number>
            </template>
          </el-table-column>
          <!-- <el-table-column align="center" prop="right" label="操作">
                <template slot-scope="scope">
                  <el-button type="primary" size="small" title="绩效考核" @click="btnPerfSel(scope.row)">绩效考核</el-button>         
                </template>
            </el-table-column> -->
          <!-- 表格数据 -->
        </el-table>
        <div class="demo-drawer__footer">
          <el-row type="flex" justify="center" align="middle">
            <el-col :span="6">
              <el-button @click="btnexit">取 消</el-button>
              <el-button type="primary" @click="btnsavePerf()">提交</el-button>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "PerfOPtionView",
  data() {
    return {
      dialogOption: false, // 默认不显示添加内容
      staffs: {},
      quotaItem: [],
      appname: "",
    };
  },
  methods: {
    indexMethod(index) {
      //表格自定义序号
      return index + 1;
    },
    btnexit() {
      // 清除数据内容
      (this.dialogOption = false), // 默认不显示添加内容
        (this.staffs = {}),
        (this.quotaItem = []);
    },
    btnsavePerf() {
      let pssPerfsItem = {
        quotaItem: [],
        staffs: this.staffs,
        appname: this.appname,
      };
      let q = this.quotaItem;
      pssPerfsItem.quotaItem = q;
      console.log(pssPerfsItem);
      this.$axios
        .post("/api/quota/saveperf", pssPerfsItem)
        .then((result) => {
          if (result.data.code === 200) {
            alert("添加成功");
            this.dialogOption = false;
          } else {
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "error",
            });
          }
        })
        .catch((e) => {
          console.log(e);
          this.$message({
            showClose: true,
            message: "当前网络差!",
            type: "error",
          });
        });
    },
  },
  computed: {},
  components: {},
  created() {},
};
</script>
<style scoped>
.el-input,
.el-select {
  width: 300px;
}
.demo-drawer__footer {
  margin-top: 25px;
}
</style>
