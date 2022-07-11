<!-- 进行请假审批 -->
<template>
  <div>
    <el-drawer
      title="进行审批"
      :close-on-press-escape="true"
      :wrapperClosable="true"
      :visible.sync="dialogEvection"
      direction="rtl"
      :size="500"
      custom-class="demo-drawer"
      ref="drawer"
    >
      <div class="demo-drawer__content">
        <el-row>
          <el-col :span="20">
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item label="审批编号：">
                <el-input
                  placeholder="请输入审批编号："
                  type="text"
                  v-model="approval.approvalno"
                  :disabled="true"
                ></el-input>
              </el-form-item>
              <el-form-item label="申请人姓名：">
                <el-input
                  placeholder="请输入申请人姓名"
                  type="text"
                  v-model="approval.users.username"
                  :disabled="true"
                ></el-input>
              </el-form-item>
              <el-form-item label="审批类型：">
                <el-input
                  placeholder="请输入审批类型"
                  type="text"
                  v-model="approval.approvaltype.type"
                  :disabled="true"
                ></el-input>
              </el-form-item>
              <el-form-item label="开始时间：">
                <el-input
                  placeholder="请输入开始时间"
                  type="text"
                  v-model="approval.starttime"
                  :disabled="true"
                ></el-input>
              </el-form-item>
              <el-form-item label="结束时间：">
                <el-input
                  placeholder="请输入结束时间"
                  type="text"
                  v-model="approval.endtime"
                  :disabled="true"
                ></el-input>
              </el-form-item>
              <el-form-item label="请假时长：">
                <el-input
                  placeholder="请输入出差时长"
                  type="text"
                  v-model="approval.leavealltime"
                  :disabled="true"
                ></el-input>
              </el-form-item>
              <el-form-item label="申请时间：">
                <el-input
                  placeholder="请输入申请时间"
                  type="text"
                  v-model="approval.approvaltime"
                  :disabled="true"
                ></el-input>
              </el-form-item>
              <el-form-item label="出差理由：">
                <el-input
                  placeholder="请输入出差理由 "
                  type="text"
                  v-model="approval.leaveresoult"
                  :disabled="true"
                ></el-input>
              </el-form-item>
              <el-form-item label="审批备注">
                <el-input type="textarea" v-model="approval.notes"></el-input>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <div class="demo-drawer__footer">
          <el-row type="flex" justify="center" align="middle">
            <el-col :span="20">
              <el-button type="primary" @click="btnagree">同意</el-button>
              <el-button type="primary" @click="btnrefush">拒绝</el-button>
              <el-button @click="btnexit">取 消</el-button>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {
      dialogEvection: false,
      approval: {},
    };
  },
  methods: {
     btnexit(){//取消按钮
          // this.approval={};
          this.dialogEvection=false;
      },
      btnagree(){
        this.$axios.post('/api/approval/agree',this.approval)
          .then(result=>{
              if(result.data.code===200){
                   this.$message({
                    showClose: true,
                    message: '审批成功',
                    type: 'success'
                    });
                    //隐藏自己
                    this.btnexit();
                    //刷新列表
                   this.$router.go(0);
                    //this.$forceUpdate();
              }else{
                  this.$message({
                    showClose: true,
                    message: '审批失败',
                    type: 'error'
                    });
              }
          }).catch(e=>{
              this.$message({
              showClose: true,
              message: '网络环境差。。。',
              type: 'error'
            });
            console.log(e);
          })
      },
      btnrefush(){
        this.$axios.post('/api/approval/refuse',this.approval)
          .then(result=>{
              if(result.data.code===200){
                   this.$message({
                    showClose: true,
                    message: '审批成功',
                    type: 'success'
                    });
                    //隐藏自己
                    this.btnexit();
                    //刷新列表
                    this.$router.go(0);
                    //this.$forceUpdate();
              }else{
                  this.$message({
                    showClose: true,
                    message: '审批失败',
                    type: 'error'
                    });
              }
          }).catch(e=>{
              this.$message({
              showClose: true,
              message: '网络环境差。。。',
              type: 'error'
            });
            console.log(e);
          })
      }
  },
  components: {},
  computed: {},
  mounted: {},
};
</script>
<style scoped>
</style>