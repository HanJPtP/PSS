<!-- 添加讲师组件 -->
<template>
  <div>
    <el-drawer
      title="添加出入库信息"
      :close-on-press-escape="true"
      :wrapperClosable="true"
      :visible.sync="dialogAdd"
      direction="rtl"
      :size="500"
      custom-class="demo-drawer"
      ref="drawer"
    >
      <div class="demo-drawer__content">
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="20">
            <el-form :inline="true">
              <el-form-item label="工单编号:">
                <el-input
                  placeholder="请输入工单编号"
                  type="text"
                  v-model="pssWhdata.orderno"
                ></el-input>
              </el-form-item>

              <div class="block">
                <span class="demonstration">入库时间</span>
                <el-date-picker
                  v-model="pssWhdata.intime"
                  type="datetime"
                  placeholder="选择入库时间"
                >
                </el-date-picker>
              </div>

              <div class="block">
                <span class="demonstration">出库时间</span>
                <el-date-picker
                  v-model="pssWhdata.outtime"
                  type="datetime"
                  placeholder="选择出库时间"
                >
                </el-date-picker>
              </div>

              <!-- <el-form-item label="入库时间">
                <el-input type="date" v-model="pssWhdata.intime"></el-input>
              </el-form-item>
              <el-form-item label="出库时间">
                <el-input type="date" v-model="pssWhdata.outtime"></el-input>
              </el-form-item> -->
              <!-- <el-form-item label="入库时间:">
                <el-input
                  placeholder="请输入入库时间"
                  type="text"
                  v-model="pssWhdata.intime"
                ></el-input>
              </el-form-item>

              <el-form-item label="出库时间:">
                <el-input
                  placeholder="请输入出库时间"
                  type="text"
                  v-model="pssWhdata.outtime"
                ></el-input>
              </el-form-item> -->

              <el-form-item label="类型:">
                <el-input
                  placeholder="入库为1,出库为2"
                  type="text"
                  v-model="pssWhdata.inouttype"
                ></el-input>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <div class="demo-drawer__footer">
          <el-row type="flex" justify="center" align="middle">
            <el-col :span="20">
              <el-button @click="btnexit">取 消</el-button>
              <el-button type="primary" @click="btnadd">添加</el-button>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "WhdataAdd",
  data() {
    return {
      dialogAdd: true, //控制内容是否显示，false表示隐藏
      pssWhdata: {
        //添加的数据对象
        orderno: "",
        intime: "",
        outtime: "",
        inouttype: "",
      },
    };
  },
  methods: {
    btnexit() {
      //取消按钮
      this.pssWhdata = {
        orderno: "",
        intime: "",
        outtime: "",
        inouttype: "",
      };
      this.dialogAdd = false;
      this.$router.push({ name: "whdatainout" });
    },
    btnadd() {
      console.log("123");
      console.log(this.pssWhdata);
      //添加
      axios
        .post("/api/inout/add", this.pssWhdata)
        .then((result) => {
          console.log(result.data.code);
          console.log("5555");
          if (result.data.code === 200) {
            this.$message({
              showClose: true,
              message: "添加出入库数据成功",
              type: "success",
            });
            this.$router.push({ name: "whdatainout" });
          } else {
            this.$message({
              showClose: true,
              message: "添加出入库数据失败",
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
  },
  components: {},
  computed: {},
  created() {},
};
</script>
<style scoped>
.el-input,
.el-select {
  width: 300px;
}
</style>
