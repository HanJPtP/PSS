<!--  -->
<template>
  <div>
    <el-dialog
      title="添加工单"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose"
    >
      <el-row type="flex" justify="center" align="middle">
        <el-col :span="18">
          <el-form :inline="true" :rules="rules" :model="pssOrderTask"  ref="pssOrderTask">
            <el-form-item label="工单负责人">
              <el-select v-model="pssOrderTask.userno" placeholder="请选择">
                <el-option
                  v-for="item in usersList"
                  :key="item.id + ''"
                  :label="item.jobno"
                  :value="item.jobno">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="工单类型:">
              <el-select
                v-model="pssOrderTask.ordertypeid"
                placeholder="请选择工单类型"
              >
                <el-option label="请选择工单类型" :value="-1"></el-option>
                <el-option
                  v-for="types in pssOrderTypes"
                  :key="types.id + ''"
                  :label="types.typename"
                  :value="types.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="工单任务:">
              <el-input
                type="textarea"
                rows="3"
                cols="34"
                v-model="pssOrderTask.worktask"
              ></el-input>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-input type="date" v-model="pssOrderTask.starttime"></el-input>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-input type="date" v-model="pssOrderTask.endtime"></el-input>
            </el-form-item>
            <el-form-item label="工单备注:">
              <el-input
                type="textarea"
                rows="3"
                cols="34"
                v-model="pssOrderTask.ordermsg"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="15">
            <el-button @click="btnexit">取 消</el-button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <el-button type="primary" @click="addbtn('pssOrderTask')"
              >添 加</el-button
            >
          </el-col>
        </el-row>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "AddTaskView",
  data() {
    return {
      dialogVisible: false,
      pssOrderTask: {
        userno: "",
        ordertypeid: -1,
        worktask: "",
        starttime: "",
        endtime: "",
        ordermsg: "",
      },
      pssOrderTypes: [],
      usersList:[],
      rules:{
        //编写表单验证规则
        userno: [
            { required: true, message: '请输入工单负责人', trigger: 'blur' },
          ],
        worktask: [
            { required: true, message: '请输入工单任务', trigger: 'blur' },
          ],
          starttime: [
            { type: 'date', required: true, message: '请选择日期', trigger: 'change' },
          ],
          endtime: [
            { type: 'date', required: true, message: '请选择日期', trigger: 'change' },
          ]
      }
    };
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },addbtn(pssOrderTask){
        this.$refs[pssOrderTask].validate((valid)=>{
         if(valid){
        this.$axios.post("/api/taskorder/add",this.pssOrderTask)
            .then(result=>{
                console.log(result.data);
                console.log(this.pssOrderTask);
                if(result.data.code===200){
                    //添加成功
                    this.$message({
                        showClose: true,
                        message: result.data.msg,
                        type: "success",
                    });
                    //隐藏弹窗
                    // window.location.reload();
                    // this.$router.push({name:'taskData'});
                    this.btnexit();
                    
          }else{
            this.$message({
            showClose: true,
            message: result.data.msg,
            type: "warning",
          });
          }
     }).catch(e=>{
           this.$message({
            showClose: true,
            message: "网络环境差。。。",
            type: "error",
          });
          console.log(e);
        });
         }
        });
    },btnexit(){//取消按钮
          this.pssOrderTask={
        id:"",
        userno: "",
        ordertypeid: -1,
        worktask: "",
        starttime: "",
        endtime: "",
        ordermsg: "",
        ordertypeid:"",
        grade:""
      };
          this.dialogVisible=false;
      },
  },
  components: {},
  computed: {},
};
</script>
<style scoped></style>
