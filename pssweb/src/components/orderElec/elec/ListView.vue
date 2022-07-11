<!-- 抄表单列表 -->
<template>
  <div id="task">
    <!-- 导航开始 -->
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '' }">抄表工单管理</el-breadcrumb-item>
          <el-breadcrumb-item>抄表单列表</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <!-- 导航结束 -->
    <!-- 条件查询开始 -->
    <el-row>
      <el-col :span="24">
        <el-form :inline="true" class="demo-form-inline">
          <div style="margin-top: 15px">
             <el-form-item label="抄写人工号">
              <el-select v-model="formInline.userno" placeholder="请选择">
              <el-option
                v-for="item in handleroptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            </el-form-item>
             <el-form-item label="区域">
              <el-select v-model="formInline.areano" placeholder="请选择">
              <el-option
                v-for="item in areaoptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            </el-form-item>
            <el-form-item label="电表编号">
              <el-input
                v-model="formInline.electricno"
                placeholder="请输入电表编号"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="queryTask">查询</el-button>
            </el-form-item>
            <el-button
              type="primary"
              icon="el-icon-user-solid"
              @click="addview"
              >添加抄表单</el-button
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
            width="50px"
          ></el-table-column>
          <el-table-column
            align="center"
            width="120px"
            prop="electicno"
            label="电表编号"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="arearno"
            label="区域"
            width="100px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="userno"
            label="抄写人编号"
            width="100px"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="edittime"
            :formatter="formateDate"
            label="抄写时间"
            width="150px"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="startnumber"
            label="电表起始码"
            width="150px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="endnumber"
            label="电表当前码"
            width="150px"
          ></el-table-column>
           <el-table-column
            align="center"
            prop="electicsum"
            label="用电量(千瓦时）"
            width="100px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="isexception"
            label="用电有无异常"
            width="80px"
          >
            <template slot-scope="scope">
              <span v-if="scope.row.isexception == '0'">
                <el-tag type="success" effect="dark">无</el-tag>
              </span>
             <span v-if="scope.row.isexception == '1'">
                <el-tag type="success" effect="dark">有</el-tag>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="exception"
            label="异常情况描述"
            width="200px"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="other"
            label="备注"
            width="100px"
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
import addView from "@/components/orderElec/elec/AddView.vue";
//引入查看详情组件
import updateView from "@/components/orderElec/elec/UpdateView.vue";

export default {
  name: "ListView",
  data() {
    return {
      formInline: {
        userno: "",
        areano: "",
        electricno: "",
      },
      handleroptions:[],
      areaoptions:[],
      areaList: [],
      usersList:[],
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
        .get("/api/elec/list", {
          params: {
            userno: this.formInline.userno,
            electricno: this.formInline.electricno,
            areano: this.formInline.areano,
            pageNo: this.currentpage,
            pageSize: this.pagesize
          },
        })
        .then((result) => {
            
            console.log(result.data);
          if (result.data.code === 200) {
                this.taskItem=result.data.data.pageInfo.data;
                for (let i = 0; i <  this.taskItem.length; i++) {
                     this.taskItem[i].electicsum = this.taskItem[i].endnumber - this.taskItem[i].startnumber;
                }
                        //设置分页数据
                this.total = result.data.data.pageInfo.totalNum;
                this.pagecount = result.data.data.pageInfo.totalPage;
                this.currentpage = result.data.data.pageInfo.pageNo;
                this.pagesize = result.data.data.pageInfo.pageSize;
                this.areaList = result.data.data.listAllCname;
                this.usersList = result.data.data.usersList;
            
          } else {
               this.areaList = result.data.data.listAllCname;
                this.usersList = result.data.data.usersList;
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
              
            this.gethandleroptions();
            this.getareaoptions();
            console.log(this.areaoptions);
            console.log(this.handleroptions)
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
    },changetime(mytime){
      //转换时间格式 yyyy-MM-dd
      let year = mytime.getFullYear();
      let month = mytime.getMonth() + 1;
      if(month <10){
        month='0'+month;
      }
      let date = mytime.getDate();
      if(date < 10){
        date = '0'+date;
      }
      return year + '-' + month + '-' + date;
    },formateDate(row, column){
      //转换时间格式
      let data = row[column.property]
      if(data == null){
        return null;
      }
      let dt = new Date(data);
      return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds();  
    },addview(){
      //新增
      this.$refs.adds.areaList=this.areaList;
      this.$refs.adds.usersList=this.usersList;
      this.$refs.adds.getareaoptions();
     this.$refs.adds.dialogVisible=true;

    },updateview(pssOrderElec){
        //修改
        let mytime = new Date(pssOrderElec.edittime);
        let edittime = this.changetime(mytime);
        this.$refs.updates.pssOrderElec = pssOrderElec;
        this.$refs.updates.edittime=edittime;
      this.$refs.updates.areaList=this.areaList;
      this.$refs.updates.usersList=this.usersList;
      this.$refs.updates.getareaoptions();

     this.$refs.updates.dialogVisible=true;

    },gethandleroptions(){
        this.handleroptions=new Array();
        //得到用户+编号集合
        for (let index = 0; index <this.usersList.length; index++) {
          let str = {value:'',label:''};
          str.value=this.usersList[index].jobno;
          //这里还需要判断员工身份，是做事的人才被列入
          str.label='工号:' + this.usersList[index].jobno + "---姓名:'" + this.usersList[index].username;
          this.handleroptions.push(str);
           
        }
        console.log(this.handleroptions);
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
              this.$axios.get('/api/elec/delete',{params:{id}})
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
