<!--新增抄表单  -->
<template>
  <div>
    <el-dialog
      title="修改欠费单"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose"
    >
      <el-row type="flex" justify="center" align="middle">
        <el-col :span="18">
          <el-form :inline="true">
            <el-form-item label="户号:">
              <el-input
                type="text"
                v-model="pssDoorArear.doorno"
              ></el-input>
              </el-form-item>
              <el-form-item label="区域">
              <el-select v-model="pssDoorArear.areano" placeholder="请选择">
                <el-option
                  v-for="item in areaoptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
              <el-form-item label="所欠电量:">
              <el-input
                type="text"
                v-model="pssDoorArear.earrear"
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
      pssDoorArear: {
        id: "",
        doorno: "",
        areano: "",
        earrear: "",
        parrear: "",  //所欠电费
      },
      areaoptions:[],
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
      //电费

      this.pssDoorArear.parrear=this.pssDoorArear.earrear*0.588;
      //保留两位小数
      this.pssDoorArear.parrear = this.pssDoorArear.parrear.toFixed(2);
        this.$axios.post("/api/doorarea/update",this.pssDoorArear)
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
          this. pssDoorArear= {
            id: "",
        doorno: "",
        arearno: "",
        earrear: "",
        parrear: "",  //所欠电费
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
