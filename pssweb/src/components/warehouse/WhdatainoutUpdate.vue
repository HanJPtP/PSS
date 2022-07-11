<!-- 添加讲师组件 -->
<template>
  <div>
    <el-drawer
      title="修改出入库数据"
      :close-on-press-escape="true"
      :wrapperClosable="true"
      :visible.sync="dialogEdit"
      direction="rtl"
      :size="500"
      custom-class="demo-drawer"
      ref="drawer"
    >
      <div class="demo-drawer__content">
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="20">
            <el-form :inline="true">
              <!-- <el-form-item label="入库时间:">
                <el-input
                  placeholder="请输入入库时间"
                  type="text"
                  :formatter="formateDate"
                  v-model="whinfor.intime"
                ></el-input>
              </el-form-item> -->

              <div class="block">
                <span class="demonstration">入库时间</span>
                <el-date-picker
                  v-model="whinfor.intime"
                  type="date"
                  placeholder="选择入库时间"
                >
                </el-date-picker>
              </div>

              <div class="block">
                <span class="demonstration">出库时间</span>
                <el-date-picker
                  v-model="whinfor.outtime"
                  type="date"
                  placeholder="选择出库时间"
                >
                </el-date-picker>
              </div>

              <!-- <el-form-item label="出库时间:">
                <el-input
                  placeholder="请输入出库时间"
                  type="text"
                  :formatter="formateDate"
                  v-model="whinfor.outtime"
                ></el-input>
              </el-form-item> -->

              <el-form-item label="类型:">
                <el-input
                  placeholder="1为入库,2为出库"
                  type="text"
                  v-model="whinfor.inouttype"
                ></el-input>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <div class="demo-drawer__footer">
          <el-row type="flex" justify="center" align="middle">
            <el-col :span="20">
              <el-button @click="btnexit">取 消</el-button>
              <el-button type="primary" @click="btnupdate">修改</el-button>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "WhdataUpdate",
  data() {
    return {
      dialogEdit: true, //控制内容是否显示，false表示隐藏
      whinfor: {},
    };
  },
  methods: {
    //修改四库信息
    btnupdate() {
      this.$axios
        .post("/api/inout/update", this.whinfor)
        .then((result) => {
          if (result.data.code === 200) {
            this.$router.push({ name: "psstool" });
          } else {
            this.$message({
              showClose: true,
              message: "网络环境差。。。",
              type: "success",
            });
          }
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "修改失败啊",
            type: "error",
          });
          console.log(e);
        });
    },
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
  components: {},
  computed: {},
  created() {
    this.whinfor = this.$route.params.whinfor;
  },
};
</script>
<style scoped>
.el-input,
.el-select {
  width: 300px;
}
</style>
