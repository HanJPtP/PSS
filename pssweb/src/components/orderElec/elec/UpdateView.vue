<!--新增抄表单  -->
<template>
  <div>
    <el-dialog
      title="新增抄表单"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose"
    >
      <el-row type="flex" justify="center" align="middle">
        <el-col :span="18">
          <el-form :inline="true">
            <el-form-item label="抄写人">
               <el-select v-model="pssOrderElec.userno" placeholder="请选择">
                <el-option
                  v-for="item in usersList"
                  :key="item.id + ''"
                  :label="item.jobno"
                  :value="item.jobno">
                </el-option>
              </el-select>
            </el-form-item>
           <el-form-item label="区域">
              <el-select v-model="pssOrderElec.arearno" placeholder="请选择">
                <el-option
                  v-for="item in areaoptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="电表编号:">
              <el-input
                type="text"
                v-model="pssOrderElec.electicno"
              ></el-input>
              </el-form-item>
              <el-form-item label="电表起始码:">
              <el-input
                type="text"
                v-model="pssOrderElec.startnumber"
              ></el-input>
              </el-form-item>
              <el-form-item label="电表当前码:">
              <el-input
                type="text"
                v-model="pssOrderElec.endnumber"
              ></el-input>
            </el-form-item>
            <el-form-item label="抄写时间">
              <el-input type="date" v-model="edittime"></el-input>
            </el-form-item>
            
            <el-form-item label="用电有无异常">
              <el-select
                v-model="pssOrderElec.isexception"
                placeholder="请选择"
              >
                <el-option label="无" :value="0"></el-option>
                <el-option label="有" :value="1"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="异常情况描述:">
              <el-input
                type="textarea"
                rows="3"
                cols="30"
                v-model="pssOrderElec.exception"
              ></el-input>
            </el-form-item>
             <el-form-item label="备注:">
              <el-input
                type="text"
                v-model="pssOrderElec.other"
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
            <el-button type="primary" @click="addbtn"
              >修 改</el-button
            >
          </el-col>
        </el-row>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "AddView",
  data() {
    return {
      dialogVisible: false,
      pssOrderElec: {
          id: "",
        userno: "",
        arearno: "",
        electicno: "",
        startnumber: "",
        endnumber: "",
        electricsum: "",
        edittime:"",
        isexception:"",
        exception:"",
        other:""
      },
      edittime:'',
      areaoptions:[],
      usersList:[],
      areaList:[],
    };
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },addbtn(){
        this.$axios.post("/api/elec/update",this.pssOrderElec)
            .then(result=>{
                console.log(result.data);
                console.log(this.pssOrderElec);
                if(result.data.code===200){
                    //添加成功
                    this.$message({
                        showClose: true,
                        message: result.data.msg,
                        type: "success",
                    });
                    //隐藏弹窗
                    this.btnexit();
                    window.location.reload();
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
    },btnexit(){//取消按钮
          this. pssOrderElec= {
              id: "",
        userno: "",
        arearno: "",
        electicno: "",
        startnumber: "",
        endnumber: "",
        electricsum: "",
        edittime:"",
        isexcption:"",
        exception:"",
        other:""
      };
          this.dialogVisible=false;
      },getareaoptions(){
        this.areaoptions=new Array();
        for (let index = 0; index < this.areaList.length; index++) {
          let str = {value:'',label:''};
          str.value = this.areaList[index];
          str.label = this.areaList[index];
          this.areaoptions.push(str);
        }
      },
  },
  components: {},
  computed: {},
};
</script>
<style scoped></style>
