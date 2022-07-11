<!--  -->
<template>
  <div>
    <el-dialog
      title="填写工单"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose"
    >
      <el-row type="flex" justify="center" align="middle">
        <el-col :span="20">
          <el-form
            :label-position="labelPosition"
            label-width="100px"
            :model="pssOrderProcess"
          >
            <el-form-item label="工单编号">
              <el-input :disabled="true" v-model="pssOrderProcess.orderno"></el-input>
            </el-form-item>
            <el-form-item label="工单类型">
              <el-input :disabled="true" v-model="pssOrderProcess.typename"></el-input>
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
              <el-input type="date" :disabled="true" v-model="starttime"></el-input>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-input type="date" :disabled="true" v-model="endtime"></el-input>
            </el-form-item>
            <el-form-item label="处理人">
              <el-table
          :data="handlerList"
          border
          fit="fit"
          stripe="stripe"
        >
         <el-table-column
            align="center"
            prop="jobno"
            label="工 号"
          ></el-table-column>
          <el-table-column
            align="center"
            width="150px"
            prop="username"
            label="姓 名"
          ></el-table-column>
          </el-table>
            </el-form-item>
            <el-form-item label="区域">
              <el-input :disabled="true" v-model="pssOrderProcess.arearno"></el-input>
            </el-form-item>
            <el-form-item label="遇到的问题">
              <el-input
                placeholder="问题说明"
                type="textarea"
                :rows="5"
                :cols="25"
                :disabled="true"
                v-model="pssOrderProcess.trouble"
              ></el-input>
              </el-form-item>
              <el-form-item label="问题的解决">
              <el-input
                placeholder="问题的解决"
                type="textarea"
                :rows="5"
                :cols="25"
                :disabled="true"
                v-model="pssOrderProcess.solution"
              ></el-input>
              </el-form-item>

              <el-form-item label="工具详情">
              <el-table
          :data="toolsList"
          border
          fit="fit"
          stripe="stripe"
        >
         <el-table-column
            align="center"
            prop="toolno"
            width="200px"
            label="工具编号"
          ></el-table-column>
          <el-table-column
            align="center"
            width="150px"
            prop="toolname"
            label="工具名"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="style"
            label="工具种类"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="standard"
            label="规格"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="nums"
            label="借用数量"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="userno"
            label="领用人工号"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="username"
            label="领用人姓名"
          ></el-table-column>
           <el-table-column
            align="center"
            :formatter="formateDate"
            prop="borrowtime"
            width="200px"
            label="借用时间"
          ></el-table-column>
           <el-table-column
            align="center"
            :formatter="formateDate"
            prop="returntime"
            width="200px"
            label="归还时间"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="status"
            label="借用情况"
          >
          <template slot-scope="scope">
              <span v-if="scope.row.status == '0'">
                <el-tag type="success" effect="dark">未借用</el-tag>
              </span>
              <span v-if="scope.row.status == '1'">
                <el-tag type="warning" effect="dark">借用中</el-tag>
              </span>
              <span v-if="scope.row.status == '2'">
                <el-tag type="success" effect="dark">已归还</el-tag>
              </span>
               <span v-if="scope.row.status == '3'">
                <el-tag type="success" effect="dark">无需归还</el-tag>
              </span>
            </template>
          </el-table-column>
          </el-table>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="15">
            <el-button @click="btnexit">退 出</el-button>
          </el-col>
        </el-row>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {
      dialogVisible: false,
      labelPosition: 'right',
      pssOrderProcess: {},
      handlerList:[],
      toolsList:[],
      starttime:"",
      endtime:"",
    };
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    btnexit() {
        this.pssOrderProcess=new Array();
      this.handlerList=new Array();
      this.toolsList=new Array();
      this.starttime="",
      this.endtime="",
      this.pssOrderProcess = "";
      this.dialogVisible = false;
    },formateDate(row, column){
      //转换时间格式
      let data = row[column.property]
      if(data == null){
        return null;
      }
      let dt = new Date(data);
      return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds();  
    },
  },
  components: {},
  computed: {},
};
</script>
<style scoped></style>
