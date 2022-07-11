<!-- 派工单列表 -->
<template>
  <div id="task">
     <el-dialog
      title="工具选择"
      :visible.sync="dialogVisible"
      width="80%"
      :before-close="handleClose"
    >
    <!-- 条件查询开始 -->
    <el-row>
      <el-col :span="24">
        <el-form :inline="true" class="demo-form-inline">
          <div style="margin-top: 15px">
            <el-form-item label="物品名">
              <el-input
                v-model="dataname"
                placeholder="请输入物品名"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="queryTools">查询</el-button>
            </el-form-item>
           
          </div>
        </el-form>
      </el-col>
    </el-row>
    <!-- 条件查询结束 -->
    <!-- 数据列表开始 -->
    <el-row>
      <el-col :span="24">
        <el-table
          :data="Items"
          border
          style="width: 100%"
          fit="fit"
          stripe="stripe"
        >
          <el-table-column
            align="center"
            label="序号"
            type="index"
            :index="indexMethod"
            width="50px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="whnameno"
            label="物品编号"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="dataname"
            label="物品名"
            width="100px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="numbers"
            label="物品剩余数量"
            width="100px"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="whname"
            label="物品样式"
            width="100px"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="standard"
            label="规格"
            width="100px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="unitname"
            label="部门"
            width="100px"
          ></el-table-column>
          <el-table-column
            align="center"
            label="选择"
            width="150"
          >
          <template slot-scope="scope">
             <el-checkbox v-model="scope.row.whhouseid" :true-label="scope.row.id" false-label="0" v-on:click.stop.native  @change="chec(scope.row)" style="padding-right:50px;">选择</el-checkbox>
          </template>
          </el-table-column>
           <el-table-column
            align="center"
            label="选择数量"
            width="200px"
          >
            <template slot-scope="scope">
               <el-input-number v-model="scope.row.numbers" size="mini" @change="numChange(scope.row)" :min="0" :max="Number(scope.row.numbers)"></el-input-number>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <!-- 数据列表结束 -->
    <!-- 分页开始 -->
    <el-row>
      <el-col :span="24">
        <div style="margin-top: 15px; text-align: center">
          <el-pagination
            background
            :page-sizes="[5, 10, 15, 20, 25]"
            :page-size="pagesize"
            layout="prev,pager,next,sizes,total"
            :total="total"
            :page-count="pagecount"
            :current-page="currentpage"
            @size-change="chageSize"
            @current-change="chageCurrent"
            @prev-click="chageCurrent"
            @next-click="chageCurrent"
          >
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <!-- 分页结束 -->
     <span slot="footer" class="dialog-footer">
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="15">
            <el-button @click="btnexit">取 消</el-button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <el-button type="primary" @click="changes"
              >完成</el-button
            >
          </el-col>
        </el-row>
      </span>
    </el-dialog>

  </div>



</template>

<script>

export default {
  name: "ToolsListView",
  data() {
    return {
      nums:'',
      numss:[],
      checks:"",
      dataname: "",
      dialogVisible: false,
      Items: [], //要显示的数据
      total: 0, //分页控件总条数
      pagecount: 0, //分页控件总页数
      currentpage: 1, //分页控件当前页码
      pagesize: 5, //分页控件每页条数
      whdataPageInfo: {},
      ids:[],
      tools:[],
      orderno: "",
    };
  },
  methods: {
  chec(item){
    //选择
    let tag = -1;
    if(this.ids.length <= 0){
      this.ids.push(item.id)
    }else{
      for(let i = 0; i < this.ids.length; i++){
        if(this.ids[i] == item.id){
          this.ids.splice(i,1);
          console.log("11aa");
          tag=0;
          break;
        }
      }
      console.log(tag);
      if(tag==-1){
        console.log("1sa");
        this.ids.push(item.id);
      }
    }
   
  },
    changes(){
      if(confirm('确定已选择完成了吗？')){
      //完成
      let f = -1;
    for(let i = 0;i < this.ids.length;i++){
      for(let j=0;j<this.tools.length;j++){
        console.log(this.tools[j].id);
        console.log(this.ids[i]);
        if(this.tools[j].id==this.ids[i]){
          f=0;
          break;
        }
      }
      if(f==-1){
          alert("序号"+this.ids[i]+":已选择但没有选择数量");
          return;
        }
        f=-1;
    }
    let g = -1;
    for(let i = 0;i < this.tools.length;i++){
      for(let j=0;j<this.ids.length;j++){
        if(this.tools[i].id == this.ids[j]){
            g=0;
        }
      }
      if(g==-1){
         //未选择，但改变了数量情况
          this.tools.splice(i,1);
      }
      g=-1;
      }
      //已完成选择，将选择结果传给其父组件
      let t = this.tools;
      this.$emit("gettools",t);
      }
      this.btnexit();
      

  },numChange(item){
    //数量  改变触发
    console.log(item.id);
    console.log(item.whnameno);
    console.log(item.numbers);
    let tl = {id:item.id,whnameno:item.whnameno,numbers:item.numbers};
    let f = -1;
    if(this.tools.length<=0){
      this.tools.push(tl);
    }else{
      for(let i=0;i<this.tools.length;i++){
        if(item.id==this.tools[i].id){
          this.tools[i].numbers=item.numbers;
          f=0;
          break;
        }
      }
      if(f==-1){
        this.tools.push(tl);
      }
    }
  },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },btnexit(){
      //取消按钮
      console.log(this.ids);
      this.ids=new Array();
      this.tools=new Array();
      this.dialogVisible=false;
    },
    //多条件查询信息
    queryTools() {
      this.$axios
        .get("/api/processorder/toolsList", {
          params: {
            dataname:this.dataname,
            pageNo: this.currentpage,
            pageSize: this.pagesize
          },
        })
        .then((result) => {
          console.log("2");
          console.log(result.data);
          console.log("1");
          console.log(this.dataname);
          if (result.data.code === 200) {
            this.Items = result.data.data.data;
            //设置分页数据
            this.total = result.data.data.totalNum;
            this.pagecount = result.data.data.totalPage;
            this.currentpage = result.data.data.pageNo;
            this.pagesize = result.data.data.pageSize;
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
    indexMethod(index) {
      //表格自定义序号
      return index + 1;
    },
    chageSize(pagesize) {
      //改变每页条数下拉框事件，通过形参获得当前选中的条数
      this.currentpage = 1; //当前页码改为1
      this.pagesize = pagesize;
      //调用查询方法
      this.queryTools();
    },
    chageCurrent(current) {
      //改变页码时事件，接收一个被改变后的页码
      this.currentpage = current; //当前页码改为1
      //调用查询方法
      this.queryTools();
    },

  },
  components: {
  },
  computed: {},
};
</script>
<style scoped>
#task {
  width: 100%;
  height: 100%;
}
</style>
