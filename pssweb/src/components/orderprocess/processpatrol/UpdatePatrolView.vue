<!--  -->
<template>
  <div>
    <el-dialog
      title="填写工单"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <el-row type="flex" justify="center" align="middle">
        <el-col :span="20">
          <el-form :inline="true">
            <el-form-item label="工单编号">
              <el-input
                placeholder="工单编号"
                type="text"
                :disabled="true"
                v-model="pssOrderProcess.orderno"
              ></el-input>
            </el-form-item>
            <el-form-item label="工单类型">
              <el-input
                placeholder="工单类型"
                type="text"
                :disabled="true"
                v-model="pssOrderProcess.typename"
              ></el-input>
            </el-form-item>
            <el-form-item label="工单任务">
              <el-input
                placeholder="工单任务"
                type="text"
                :disabled="true"
                v-model="pssOrderProcess.ordertitle"
              ></el-input>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-input
                type="date"
                :disabled="true"
                v-model="starttime"
              ></el-input>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-input
                type="date"
                :disabled="true"
                v-model="endtime"
              ></el-input>
            </el-form-item>
            <br />
            <el-form-item label="添加处理人">
              <el-select
                v-model="pssOrderProcess.handlerList"
                multiple
                placeholder="请选择"
              >
                <el-option
                  v-for="item in handleroptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <br />
            <el-form-item label="负责的区域">
              <el-select v-model="pssOrderProcess.arearno" placeholder="请选择">
                <el-option
                  v-for="item in areaoptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <br />
            <el-form-item label="所需工具">
              <el-button @click="gettools" size="mini" type="primary"
                >选择</el-button
              >
            </el-form-item>
            <br />
            <el-form-item label="遇到的问题">
              <el-button @click="getproblem" size="mini" type="primary"
                >填写</el-button
              >
            </el-form-item>
            <br />
            <el-form-item label="问题处理说明">
              <el-input
                type="textarea"
                :rows="5"
                :cols="50"
                placeholder="请输入内容"
                v-model="pssOrderProcess.solution"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="问题类型">
              <el-select
                v-model="pssOrderProcess.problemtype"
                placeholder="请选择问题类型"
              >
                <el-option label="正常" :value="0"></el-option>
                <el-option label="维护维修" :value="1"></el-option>
                <el-option label="抢修装拆" :value="2"></el-option>
                <el-option label="未处理" :value="3"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="15">
            <el-button @click="btnexit">取 消</el-button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <el-button type="primary" @click="doit">提 交</el-button>
          </el-col>
        </el-row>
      </span>
    </el-dialog>

    <!-- 引入工具选择列表组件 -->
    <tools-list-view ref="listtools" v-on:gettools="gettool"></tools-list-view>
    <!-- 遇到的问题组件 -->
    <about-problem-view
      ref="aboutproblem"
      v-on:getfile1="getfiles1"
      @gettext1="gettexts1"
    ></about-problem-view>
  </div>
</template>

<script>
import toolsListView from "@/components/orderprocess/processpatrol/ToolsListView.vue";
import aboutProblemView from "@/components/orderprocess/processpatrol/AboutProblemView.vue";

export default {
  name: "AddTaskView",
  data() {
    return {
      handleroptions: [],
      handlers: [],
      areaoptions: [],
      usersList: [],
      dialogVisible: false,
      pssOrderProcess: {
        id: "",
        orderno: "",
        ordertypeid: -1,
        ordertitle: "",
        starttime: "",
        endtime: "",
        handlerno: "",
        arearno: "",
        trouble: "",
        solution: "",
        tools: "",
        problemtype: "",
        handlerList: [],
        pssWhdataList: [],
        toolsNumsList: [],
        typename: "",
      },
      areaList: [],
      pssOrderTypes: [],
      starttime: "",
      endtime: "",
      tools: [], //工具选择的结果集
      psstools: [], //psstools对象集合
      // protext:'', //遇到问题的文字描述
      proimages: [], //遇到问题的图片描述
    };
  },
  methods: {
    gettexts1(msgs) {
      //得到遇到问题的文字描述
      this.pssOrderProcess.trouble = msgs;
    },
    getfiles1(msgs) {
      //得到遇到问题的图片描述
      this.proimages = msgs;
    },
    gettool(val) {
      //得到选择的工具
      //接收子组件的值
      this.tools = val;
      //赋值psstools
      for (let i = 0; i < this.tools.length; i++) {
        let str = { orderno: "", ordertypeid: "", tools: "", nums: "" };
        str.orderno = this.pssOrderProcess.orderno;
        str.ordertypeid = this.pssOrderProcess.ordertypeid;
        str.tools = this.tools[i].whnameno;
        str.nums = this.tools[i].numbers;
        this.psstools.push(str);
      }
      //将值赋给pssOrderProcess
      this.pssOrderProcess.toolsNumsList = this.psstools;
    },
    doit() {
      //提交
      if (confirm("确定1提交吗？")) {
        console.log("asdsdasdas");
        //handlerno赋值
        for (let i = 0; i < this.pssOrderProcess.handlerList.length; i++) {
          this.pssOrderProcess.handlerno +=
            "," + this.pssOrderProcess.handlerList[i];
        }

        this.pssOrderProcess.handlerList = new Array();
        console.log("11--11");
        console.log(this.pssOrderProcess);
        this.$axios
          .post("/api/processorder/add", this.pssOrderProcess)
          .then((result) => {
            console.log("aaa>>>>>>>>>>>>>>", result.data);
            if (result.data.code === 200) {
              console.log("v_____");
              //添加成功
              this.$message({
                showClose: true,
                message: result.data.msg,
                type: "success",
              });

              //调用后台controller，修改数据库
              this.$axios
                .get("/api/inout/auto", {
                  params: { orderno: this.pssOrderProcess.orderno },
                })
                .then((result) => {
                  console.log("b____________b");
                  if (result.data.code === 200) {
                    this.$message({
                      showClose: true,
                      message: "工具借用成功",
                      type: "success",
                    });
                  }
                  //隐藏弹窗
                  this.btnexit();
                })
                .catch((e) => {
                  this.$message({
                    showClose: true,
                    message: "网络环境差。。。",
                    type: "error",
                  });
                  console.log(e);
                });
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
              message: "网络环境差。。。",
              type: "error",
            });
            console.log(e);
          });
      }
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    btnexit() {
      //取消按钮
      (this.pssOrderProcess = {
        id: "",
        orderno: "",
        ordertypeid: -1,
        ordertitle: "",
        starttime: "",
        endtime: "",
        handlerno: "",
        arearno: "",
        trouble: "",
        solution: "",
        tools: "",
        problemtype: "",
        handlerList: [],
        pssWhdataList: [],
        toolsNumsList: [],
        typename: "",
      }),
        (this.dialogVisible = false);
    },
    updatebtn() {
      this.$axios
        .post("/api/taskorder/update", this.pssOrderTask)
        .then((result) => {
          console.log(result.data);
          console.log(this.pssOrderTask);
          if (result.data.code === 200) {
            //修改成功
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "success",
            });
            console.log("a----------a");
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
            message: "网络环境差。。。",
            type: "error",
          });
          console.log(e);
        });
    },
    gethandleroptions() {
      this.handleroptions = new Array();
      //得到用户+编号集合
      for (let index = 0; index < this.usersList.length; index++) {
        let str = { value: "", label: "" };
        str.value = this.usersList[index].jobno;
        //这里还需要判断员工身份，是做事的人才被列入
        str.label =
          "工号:" +
          this.usersList[index].jobno +
          "---姓名:'" +
          this.usersList[index].username;
        this.handleroptions.push(str);
      }
      console.log(this.handleroptions);
    },
    getareaoptions() {
      this.areaoptions = new Array();
      for (let index = 0; index < this.areaList.length; index++) {
        let str = { value: "", label: "" };
        str.value = this.areaList[index];
        str.label = this.areaList[index];
        this.areaoptions.push(str);
      }
    },
    gettools() {
      this.$refs.listtools.queryTools();
      this.$refs.listtools.orderno = this.pssOrderProcess.orderno;
      this.$refs.listtools.dialogVisible = true;
    },
    getproblem() {
      this.$refs.aboutproblem.dialogVisible = true;
    },
  },
  components: {
    toolsListView,
    aboutProblemView,
  },
  computed: {},
  created() {},
};
</script>
<style scoped></style>
