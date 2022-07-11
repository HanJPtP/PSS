<!-- 添加讲师组件 -->
<template>
  <div>
    <el-drawer
      title="修改四库数据"
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
              <el-form-item label="物品编号:">
                <el-input
                  placeholder="请输入物品编号"
                  type="text"
                  v-model="whinfor.whnameno"
                ></el-input>
              </el-form-item>

              <el-form-item label="物品名称:">
                <el-input
                  placeholder="请输入物品名称"
                  type="text"
                  v-model="whinfor.dataname"
                ></el-input>
              </el-form-item>

              <el-form-item label="物品数量:">
                <el-input
                  placeholder="请输入物品数量"
                  type="text"
                  v-model="whinfor.numbers"
                ></el-input>
              </el-form-item>

              <el-form-item label="物品类型">
                <el-select
                  v-model="whinfor.whhouseid"
                  placeholder="请选择物品类型"
                  value=""
                >
                  <el-option
                    v-for="whhouse in whhouseitem"
                    :key="whhouse.id"
                    :label="whhouse.whname"
                    :value="whhouse.id"
                  ></el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="物品规格:">
                <el-input
                  placeholder="请输入物品规格"
                  v-model="whinfor.standard"
                ></el-input>
              </el-form-item>

              <el-form-item label="所属部门">
                <el-select
                  v-model="whinfor.unitid"
                  placeholder="请选择所属部门"
                  value=""
                >
                  <el-option
                    v-for="unit in unititem"
                    :key="unit.id + ''"
                    :label="unit.unitname"
                    :value="unit.id"
                  ></el-option>
                </el-select>
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
      whhouseitem: [], //种类信息
      unititem: [], //部门信息
    };
  },
  methods: {
    //修改四库信息
    btnupdate() {
      this.$axios
        .post("/api/data/update", this.whinfor)
        .then((result) => {
          if (result.data.code === 200) {
            this.$router.push({ name: "whdataList" });
          } else {
            this.$message({
              showClose: true,
              message: "网络环境差。。。",
              type: "success",
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
    loadSubect() {
      // 加载工具类型信息
      this.$axios
        .get("/api/unit/list")
        .then((result) => {
          if (result.data.code === 200) {
            this.unititem = result.data.data;
            console.log(result.data.data);
          } else {
            this.$message({
              showClose: true,
              message: "网络环境差。。。",
              type: "success",
            });
          }
        })
        .catch((e) => {
          console.info(1);
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
      this.pssWhdata = {
        whnameno: "",
        dataname: "",
        numbers: "",
        whhouseid: "",
        standard: "",
        unitid: "",
      };
      this.dialogAdd = false;
      this.$router.push({ name: "whdataList" });
    },
    loadwhouse() {
      // 加载工具类型信息
      this.$axios
        .get("/api/warehouse/list")
        .then((result) => {
          if (result.data.code === 200) {
            this.whhouseitem = result.data.data;
            console.log(result.data.data);
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
  },
  components: {},
  computed: {},
  created() {
    this.loadSubect();
    this.loadwhouse();
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
