<!-- 添加讲师组件 -->
<template>
  <div>
    <el-drawer
      title="添加四库"
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
              <el-form-item label="物品编号:">
                <el-input
                  placeholder="请输入物品编号"
                  type="text"
                  v-model="pssWhdata.whnameno"
                ></el-input>
              </el-form-item>

              <el-form-item label="物品名称:">
                <el-input
                  placeholder="请输入物品名称"
                  type="text"
                  v-model="pssWhdata.dataname"
                ></el-input>
              </el-form-item>

              <el-form-item label="物品数量:">
                <el-input
                  placeholder="请输入物品数量"
                  type="text"
                  v-model="pssWhdata.numbers"
                ></el-input>
              </el-form-item>

              <el-form-item label="物品类型">
                <el-select
                  v-model="pssWhdata.whhouseid"
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
                  v-model="pssWhdata.standard"
                ></el-input>
              </el-form-item>

              <el-form-item label="所属部门">
                <el-select
                  v-model="pssWhdata.unitid"
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
        whnameno: "",
        dataname: "",
        numbers: "",
        whhouseid: "",
        standard: "",
        unitid: "",
      },
      whhouseitem: [], //种类信息
      unititem: [], //部门信息
    };
  },
  methods: {
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
    btnadd() {
      //添加
      this.$axios
        .post("/api/data/add", this.pssWhdata)
        .then((result) => {
          if (result.data.code === 200) {
            this.$message({
              showClose: true,
              message: "添加仓库数据成功",
              type: "success",
            });
            this.$router.push({ name: "whdataList" });
          } else {
            this.$message({
              showClose: true,
              message: "添加仓库数据失败",
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
    //调用加载物品类型的方法
    this.loadSubect();
    //调用加载所属部门的方法
    this.loadwhouse();
  },
};
</script>
<style scoped>
.el-input,
.el-select {
  width: 300px;
}
</style>
