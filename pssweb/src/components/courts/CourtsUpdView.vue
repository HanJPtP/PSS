<!-- 讲师添加子组件 -->
<template>
  <div>
    <el-dialog
      title="修改台区业务"
      :close-on-press-escape="true"
      :close-on-click-modal="true"
      :center="true"
      width="50%"
      :visible.sync="dialogUpd"
      ref="drawer"
    >
      <div class="demo-drawer__content">
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="20">
            <!-- 表单开始 -->
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item label="台区名称:"
                >
                <el-input
                  placeholder="请输入台区名称"
                  type="text"
                  v-model="pssvillage.pssCourts.cname"
                ></el-input>
              </el-form-item>
              <el-form-item label="台区路线:">
                <el-input
                  placeholder="请输入台区路线"
                  type="text"
                  v-model="pssvillage.pssCircuit.circuit"
                ></el-input>
              </el-form-item>
              <el-form-item label="台区行政村:"
                >
                <el-input
                  placeholder="请输入台区行政村"
                  type="text"
                  v-model="pssvillage.village"
                ></el-input>
              </el-form-item>
              <el-form-item label="变压器的型号:"
                >
                <el-input
                  placeholder="请输入变压器的型号"
                  type="text"
                  v-model="pssvillage.pssCourts.transformernum"
                ></el-input>
              </el-form-item>
              <el-form-item label="变压器的容量:"
                >&nbsp;&nbsp;
                <el-input
                  placeholder="请输入变压器的容量(KV)"
                  type="text"
                  v-model="pssvillage.pssCourts.transformercap"
                ></el-input>
              </el-form-item>
              <el-form-item label="400伏线路条数:">
                <el-input
                  placeholder="请输入400伏线路条数(条)"
                  type="text"
                  v-model="pssvillage.pssCourts.vnumber"
                ></el-input>
              </el-form-item>
              <el-form-item label="400伏线路长度:">
                <el-input
                  placeholder="请输入400伏线路长度(米)"
                  type="text"
                  v-model="pssvillage.pssCourts.vlength"
                ></el-input>
              </el-form-item>
            </el-form>
            <!-- 表单结束 -->
            <div class="demo-drawer__footer">
              <el-row type="flex" justify="center" align="middle">
                <el-col :span="20">
                  <el-button @click="btnexit">取 消</el-button>
                  <el-button type="primary" @click="btnupdcourts"
                    >修改</el-button
                  >
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "CourtsUpdView",
  data() {
    return {
      dialogUpd: false, // 默认不显示添加内容
      // courts: {
      //   cname: "",
      //   transformernum: "",
      //   transformercap: "",
      //   vnumber: "",
      //   vlength: "",
      //   userid: "",
      // },
      // courtsid: "",
      pssvillage:{
        id:"",
        pssCourts:{
          id:"",
          cname: "",
          transformernum: "",
          transformercap: "",
          vnumber: "",
          vlength: "",
          usersid: "",
        },
        pssCircuit:{
          id:"",
          circuit:""
        },
        village:"",
      }
    };
  },
  methods: {
    indexMethod(index) {
      //表格自定义序号
      return index + 1;
    },
    btnexit() {
      (this.dialogUpd = false);
    },
    btnupdcourts() {
      this.pssvillage.pssCourts.usersid = localStorage.getItem("usersid");
      this.pssvillage.pssCourts.id = localStorage.getItem("courtsid");
      this.pssvillage.pssCircuit.id = localStorage.getItem("circuitid");
      console.log(this.pssvillage);
      // 修改台区
      this.$axios
        .post("/api/courts/upd",this.pssvillage)
        .then((result) => {
          if (result.data.code === 200) {
              this.$message({
                showClose: true,
                message: '修改台区成功',
                type: 'success'
              });
            this.btnexit();
            this.$forceUpdate();
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
  created() {
    // 取当前用户的userid
    this.pssvillage.pssCourts.usersid = localStorage.getItem("usersid");
  },
};
</script>
<style scoped>
.el-input,
.el-select {
  width: 300px;
}
</style>