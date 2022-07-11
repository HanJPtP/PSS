<!-- 添加讲师组件 -->
<template>
  <div>
    <el-drawer
      title="修改校验数据"
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
              <!-- <el-form-item label="校验时间:">
                <el-input
                  placeholder="请输入校验时间"
                  type="text"
                  v-model="whinfor.checktime"
                ></el-input>
              </el-form-item> -->

              <div class="block">
                <span class="demonstration">校验时间</span>
                <el-date-picker
                  v-model="whinfor.checktime"
                  type="date"
                  placeholder="选择校验时间"
                >
                </el-date-picker>
              </div>

              <el-form-item label="校验信息:">
                <el-input
                  placeholder="请输入校验信息"
                  type="text"
                  v-model="whinfor.checkdata"
                ></el-input>
              </el-form-item>

              <el-form-item label="校验状态:">
                <el-input
                  placeholder="1为已校验 2为未校验"
                  type="text"
                  v-model="whinfor.checkstatus"
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
        .post("/api/check/update", this.whinfor)
        .then((result) => {
          if (result.data.code === 200) {
            this.$router.push({ name: "checkdetail" });
          } else {
            this.$message({
              showClose: true,
              message: "网络环境差。。。",
              type: "error",
            });
          }
        })
        .catch((e) => {
          console.info(1);
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
      this.dialogAdd = false;
      this.$router.push({ name: "checkdetail" });
    },
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
