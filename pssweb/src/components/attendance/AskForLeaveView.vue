<!--  -->
<template>
  <div>
    <el-form label-width="80px" ref="form" :model="approval">
      <el-form-item label="审批类型">
        <el-select
          v-model="approval.approvalid"
          value=""
          placeholder="请选择审批类型"
          @change="btnchange"
        >
          <el-option
            :key="approvaltypeItem[1].id + ''"
            :label="approvaltypeItem[1].type"
            :value="approvaltypeItem[1].id"
          ></el-option>
          <el-option
            :key="approvaltypeItem[2].id + ''"
            :label="approvaltypeItem[2].type"
            :value="approvaltypeItem[2].id"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <!-- 请假 -->
    <div id="askforleave">
      <el-form ref="form" :model="approval" label-width="80px">
        <el-form-item label="请假类型">
          <el-select
            v-model="approval.leavetypeid"
            placeholder="请选择请假类型"
            value=""
          >
            <el-option
              v-for="leavetype in leavetypeItem"
              :key="leavetype.id + ''"
              :label="leavetype.type"
              :value="leavetype.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="开始时间">
          <el-date-picker
            v-model="approval.starttime"
            type="datetime"
            placeholder="选择开始时间" 
            value-format="yyyy-MM-dd HH:mm:ss"
            @change="getStarttimeDate"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
            v-model="approval.endtime"
            type="datetime"
            placeholder="选择结束时间"
             value-format="yyyy-MM-dd HH:mm:ss"
            @change="getEndtimeDate"
          >
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="请假时长">
          <el-table-column
            label="时间差"
            align="center"
            :formatter="formatGap"
            prop="entryTime"
          >
          </el-table-column>
        </el-form-item> -->
        <el-form-item label="请假事由">
          <el-input type="textarea" v-model="approval.leaveresoult"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="btnsaveleave">提交申请</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 出差 -->
    <div id="evection" style="display: none">
      <el-form ref="form" :model="approval" label-width="80px">
        <el-form-item label="开始时间">
          <el-date-picker
            v-model="approval.starttime"
            type="datetime"
            placeholder="选择开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            @change="getStarttimeDate"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
            v-model="approval.endtime"
            type="datetime"
            placeholder="选择结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            @change="getEndtimeDate"
          >
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="出差时长">
          <el-input v-model="approval.leaveTime"></el-input>
        </el-form-item> -->
        <el-form-item label="出差事由">
          <el-input type="textarea" v-model="approval.leaveresoult"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="btnaddevection">提交申请</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "AskForLeave",
  data() {
    return {
      approval: {
        starttime: "",
        endtime: "",
        leavetypeid: "",
        leaveresoult: "",
        // leaveTime: "",
        approvalid:3,
      },
      approvaltypeItem: [],
      leavetypeItem: [],
    };
  },
  methods: {
    onSubmit() {
      console.log("submit!");
    },
    btnchange() {
      if (this.approval.approvalid == "3") {
        document.getElementById("askforleave").style.display = "";
        document.getElementById("evection").style.display = "none";
      } else if (this.approval.approvalid == "2") {
        document.getElementById("askforleave").style.display = "none";
        document.getElementById("evection").style.display = "";
      }
    },
    // 显示要提交的审批类型
    loadapprovaltype() {
      this.$axios
        .get("/api/approvaltype/list")
        .then((result) => {
          if (result.data.code === 200) {
            this.approvaltypeItem = result.data.data;
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
    loadLeaveType() {
      this.$axios
        .get("/api/leavetype/list")
        .then((result) => {
          if (result.data.code === 200) {
            this.leavetypeItem = result.data.data;
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
    btnsaveleave() {
      console.log(this.approval);
      this.$axios.post("/api/approval/leave",this.approval)
      .then(result=>{
         console.log(result.data);
        if(result.data.code===200){
          this.$message({
            showClose:true,
            message:"添加成功",
            type:'success',
          })
        }else {
            this.$message({
              showClose: true,
              message: "添加失败",
              type: "error",
            });
          }
      }).catch(e=>{
        this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
          console.log(e);
      });
    },
    getStarttimeDate(date) {
      this.approval.starttime = date;
    },
    getEndtimeDate(date) {
      this.approval.endtime = date;
    },
    btnaddevection(){
      console.log(this.approval);
      this.$axios.post("/api//approval/evection",this.approval)
      .then(result=>{
         console.log(result.data);
        if(result.data.code===200){
          this.$message({
            showClose:true,
            message:"添加成功",
            type:'success',
          })
        }else {
            this.$message({
              showClose: true,
              message: "添加失败",
              type: "error",
            });
          }
      }).catch(e=>{
        this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
          console.log(e);
      });
    }
    // formatGap(row, column) {
    //   let staytimeGap = new Date().getTime() - new Date(row[column.property]).getTime();
    //   let stayHour = Math.floor(staytimeGap/(3600*1000));
    //   let leave1 = staytimeGap%(3600*1000);
    //   let stayMin = Math.floor(leave1/(60*1000));
    //   let leave2 = leave1%(60*1000);
    //   let staySec = Math.floor(leave2/1000);
    //   return stayHour + "时" + stayMin + "分" + staySec + "秒";
    //   },
  },
  components: {},
  computed: {},
  mounted: {},
  created() {
    this.loadapprovaltype();
    this.loadLeaveType();
  },
};
</script>
<style scoped>
</style>