<!-- 讲师添加子组件 -->
<template>
  <div>
    <el-dialog
      title="添加台区业务"
      :close-on-press-escape="true"
      :close-on-click-modal="true"
      :center="true"
      width="60%"
      :visible.sync="dialogAdd"
      ref="drawer"
    >
      <div class="demo-drawer__content">
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="20">
            <!-- 表单开始 -->
            <el-form
              :inline="true"
              class="demo-form-inline"
              :model="courts"
              :rules="rules"
              ref="courts"
            >
              <el-form-item label="台区名称:" prop="cname"
                >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <el-input
                  placeholder="请输入台区名称"
                  type="text"
                  v-model="courts.cname"
                ></el-input>
              </el-form-item>
              <el-form-item label="变压器的型号:" prop="transformernum"
                >&nbsp;&nbsp;
                <el-input
                  placeholder="请输入变压器的型号"
                  type="text"
                  v-model="courts.transformernum"
                ></el-input>
              </el-form-item>
              <el-form-item label="变压器的容量:" prop="transformercap"
                >&nbsp;&nbsp;
                <el-input
                  placeholder="请输入变压器的容量(KV)"
                  type="text"
                  v-model="courts.transformercap"
                ></el-input>
              </el-form-item>
              <el-form-item label="400伏线路条数:" prop="vnumber">
                <el-input
                  placeholder="请输入400伏线路条数(条)"
                  type="number"
                  v-model="courts.vnumber"
                ></el-input>
              </el-form-item>
              <el-form-item label="400伏线路长度:" prop="vlength">
                <el-input
                  placeholder="请输入400伏线路长度(米)"
                  type="number"
                  v-model="courts.vlength"
                ></el-input>
              </el-form-item>
            </el-form>
            <!-- 表单结束 -->
            <div class="demo-drawer__footer">
              <el-row type="flex" justify="center" align="middle">
                <el-col :span="20">
                  <el-button @click="btnexit">取 消</el-button>
                  <el-button type="primary" @click="btnsavecourts('courts')"
                    >下一步</el-button
                  >
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
        <el-dialog
          width="60%"
          title="添加台区路线"
          :visible.sync="pss_circuit"
          :center="true"
          append-to-body
        >
          <div slot="footer" class="dialog-footer">
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item label="台区路线:" prop="entryId">
                <el-input
                  placeholder="请输入台区路线,路线以,间隔"
                  type="textarea"
                  rows="4"
                  cols="100"
                  v-model="circuitStr"
                ></el-input>
              </el-form-item>
            </el-form>
            <el-button @click="pss_circuit = false">取 消</el-button>
            <el-button type="primary" @click="btncircuit">下一步</el-button>
          </div>

          <el-dialog
            width="60%"
            title="添加台区行政村"
            :center="true"
            :visible.sync="pss_village"
            append-to-body
          >
            <div v-for="(item, key) in itemcircuit.circuitItem" :key="key">
              <el-form :inline="true" class="demo-form-inline">
                <el-form-item style="margin: 20px 0">
                  <p>台区路线: {{ item.circuit }}</p>
                  <p>台区行政村</p>
                  <el-input
                    placeholder="请输入台区行政村,多个以','间隔!!!"
                    v-model="item.village"
                  >
                  </el-input>
                </el-form-item>
              </el-form>
            </div>
            <el-button @click="pss_village = false">取 消</el-button>
            <el-button type="primary" @click="btnvillage">提交</el-button>
          </el-dialog>
        </el-dialog>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "CourtsAddView",
  data() {
    return {
      dialogAdd: false, // 默认不显示添加内容
      pss_circuit: false, //内层嵌套,添加路线
      pss_village: false,
      courts: {
        cname: "",
        transformernum: "",
        transformercap: "",
        vnumber: "",
        vlength: "",
        userid: "",
      },
      courtsid: "",
      circuitStr: "",
      itemcircuit: {
        circuitItem: [],
      },
      // 验证规则:
      rules: {
        cname: [
          { required: true, message: "请输入台区名称", trigger: "blur" },
          {
            min: 2,
            max: 20,
            message: "长度在 2 到 10 个字符",
            trigger: "blur",
          },
        ],
        transformernum: [
          {
            required: true,
            message: "请输入变压器的型号",
            trigger: "change",
          },
          {
            min: 2,
            max: 20,
            message: "长度在 2 到 20 个字符",
            trigger: "blur",
          },
        ],
        transformercap: [
          { required: true, message: "请输入变压器的容量", trigger: "blur" },
          {
            min: 2,
            max: 20,
            message: "长度在 2 到 20 个字符",
            trigger: "blur",
          },
        ],
        vnumber: [
          { required: true, message: '输入400伏线路条数不能为空',trigger: "blur"},
        ],
        vlength:[
          { required: true, message: '输入400伏线路长度不能为空',trigger: "blur"},
        ],
      },
    };
  },
  methods: {
    indexMethod(index) {
      //表格自定义序号
      return index + 1;
    },
    btnexit() {
      //取消按钮
      // 清除数据内容
      (this.courts = {
        cname: "",
        transformernum: "",
        transformercap: "",
        vnumber: "",
        vlength: "",
        usersid: "",
      }),
        (this.circuitItem = []),
        (this.circuitStr = ""),
        (this.courtsid = ""),
        (this.pss_circuit = false),
        (this.pss_village = false),
        (this.dialogAdd = false);
    },
    btnsavecourts(courts) {
      this.$refs[courts].validate((valid) => {
        if (valid) {
          // 添加台区
          this.$axios
            .post("/api/courts/add", this.courts)
            .then((result) => {
              if (result.data.code === 200) {
                //   this.$message({
                //     showClose: true,
                //     message: '添加台区成功',
                //     type: 'success'
                //   });
                this.courtsid = result.data.data;
                // 打开二级窗口
                this.pss_circuit = true;
                // // 隐藏窗口
                // this.btnexit();
                // // 刷新列表
                // // this.$router.push({name:'teacherdata'});
                // this.$forceUpdate();
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
        } else {
          console.log("error btnsavecourts!!");
          return false;
        }
      });
    },
    // 添加路线函数
    btncircuit() {
      this.$axios
        .get("/api/circuit/add", {
          params: { courtsid: this.courtsid, circuitStr: this.circuitStr },
        })
        .then((result) => {
          console.log(result.data);
          this.itemcircuit.circuitItem = result.data.data;
          this.pss_village = true;
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
    //添加行政村
    btnvillage() {
      console.log(this.itemcircuit.circuitItem);
      this.$axios
        .post("/api/village/add", this.itemcircuit)
        .then((result) => {
          if (result.data.code === 200) {
            this.$message({
              showClose: true,
              message: "添加台区信息成功",
              type: "success",
            });
            // 隐藏窗口
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
    this.courts.usersid = localStorage.getItem("usersid");
  },
};
</script>
<style scoped>
.el-input,
.el-select {
  width: 300px;
}
</style>