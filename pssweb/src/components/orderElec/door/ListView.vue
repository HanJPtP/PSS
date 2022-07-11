<!-- 抄表单列表 -->
<template>
  <div id="task">
    <!-- 导航开始 -->
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '' }">欠费表工单管理</el-breadcrumb-item>
          <el-breadcrumb-item>欠电费表列表</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <!-- 导航结束 -->
    <!-- 条件查询开始 -->
    <el-row>
      <el-col :span="24">
        <el-form :inline="true" class="demo-form-inline">
          <div style="margin-top: 15px">
             <el-form-item label="户号">
              <el-input
                v-model="formInline.doorno"
                placeholder="请输入户号"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="queryTask">查询</el-button>
            </el-form-item>
            <el-button
              type="primary"
              icon="el-icon-user-solid"
              @click="addview"
              >添加欠费表</el-button
            >
          </div>
        </el-form>
      </el-col>
    </el-row>
    <!-- 条件查询结束 -->
    <!-- 数据列表开始 -->
    <el-row>
      <el-col :span="24">
        <el-table
          :data="taskItem"
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
            width="100px"
          ></el-table-column>
          <el-table-column
            align="center"
            width="400px"
            prop="doorno"
            label="户号"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="areano"
            label="区域"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="earrear"
            label="所欠电量(千瓦时)"
            width="200px"
          ></el-table-column>
           
           <el-table-column
            align="center"
            prop="parrear"
            label="所欠费用(元）"
            width="200px"
          ></el-table-column>
          <el-table-column
            align="center"
            fixed="right"
            label="操作"
            width="200"
          >
          <template slot-scope="scope">
              <el-button
                type="primary"
                icon="el-icon-document"
                title="修改"
                @click="updateview(scope.row)"
              ></el-button>
              <el-button
                type="primary"
                icon="el-icon-edit"
                title="删除"
                @click="del(scope.row.id)"
              ></el-button>
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
      <!-- 修改组件-->
  <add-view ref="adds"></add-view>
<!-- 详情组件 -->
  <update-view ref="updates"></update-view>


  </div>



</template>

<script>

// 引入新增（填写）组件
import addView from "@/components/orderElec/door/AddView.vue";
//引入查看详情组件
import updateView from "@/components/orderElec/door/UpdateView.vue";

export default {
  name: "ListDoorView",
  data() {
    return {
      formInline: {
        doorno: "",
      },
      areaoptions:[],
      areaList: [],
      taskItem: [], //要显示的数据
      total: 0, //分页控件总条数
      pagecount: 0, //分页控件总页数
      currentpage: 1, //分页控件当前页码
      pagesize: 5, //分页控件每页条数
    };
  },
  methods: {
    //多条件查询讲师信息
    queryTask() {console.log(this.formInline);
        //情况this.taskItem
        this.taskItem=new Array();
      this.$axios
        .get("/api/doorarea/list", {
          params: {
            doorno: this.formInline.doorno,
            pageNo: this.currentpage,
            pageSize: this.pagesize
          },
        })
        .then((result) => {
            
            console.log(result.data);
          if (result.data.code === 200) {
                this.taskItem=result.data.data.pageInfo.data;
                        //设置分页数据
                this.total = result.data.data.pageInfo.totalNum;
                this.pagecount = result.data.data.pageInfo.totalPage;
                this.currentpage = result.data.data.pageInfo.pageNo;
                this.pagesize = result.data.data.pageInfo.pageSize;
                this.areaList = result.data.data.listAllCname;
            
          } else {
               this.areaList = result.data.data.listAllCname;
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
              
            this.getareaoptions();
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
      this.queryTask();
    },
    chageCurrent(current) {
      //改变页码时事件，接收一个被改变后的页码
      this.currentpage = current; //当前页码改为1
      //调用查询方法
      this.queryTask();
    },addview(){
      //新增
      this.$refs.adds.areaList=this.areaList;
      this.$refs.adds.getareaoptions();
     this.$refs.adds.dialogVisible=true;

    },updateview(pssDoorArear){
        //修改
       
        this.$refs.updates.pssDoorArear = pssDoorArear;
      this.$refs.updates.areaList=this.areaList;
      this.$refs.updates.getareaoptions();

     this.$refs.updates.dialogVisible=true;

    },getareaoptions(){
        this.areaoptions=new Array();
        for (let index = 0; index < this.areaList.length; index++) {
          let str = {value:'',label:''};
          str.value = this.areaList[index];
          str.label = this.areaList[index];
          this.areaoptions.push(str);
        }
      },del(id){
          if(confirm('确定删除吗？')){
              console.log(id);
              this.$axios.get('/api/doorarea/delete',{params:{id}})
                .then(result=>{
                if(result.data.code==200){
                     this.$message({
              showClose: true,
              message: result.data.msg,
              type: "success",
            });
            this.queryTask();
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
      }
  },
  components: {
    addView,
    updateView
  },
  computed: {},
  created() {
    this.queryTask();
    
  },
};
</script>
<style scoped>
#task {
  width: 100%;
  height: 100%;
}
</style>
