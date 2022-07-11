<!--员工权限-->
<template>
  <div>
      <el-dialog
        center
        title="员工权限"
        :visible.sync="UserPermVisible"
        width="480px"
        style="margin:0 auto"
        >
        <div class="demo-drawer__content" style="margin-left: 80px;">
            <el-tree
                :data="permsItem"
                show-checkbox
                node-key="id"
                ref="tree"
                highlight-current
                :props="defaultProps"
                :default-checked-keys="permids">
            </el-tree>
            
        </div>  
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="btnPerms()">授权</el-button>&nbsp;&nbsp;&nbsp;&nbsp;
            <el-button @click="btnexit">取消</el-button>
        </span> 
    </el-dialog>

  </div>
</template>

<script>
export default {
 name:'UserPerm',
  data () {
    return {
        userid:'',//当前用户的id
        permids:[],//用户拥有的权限id
        permsItem: [{
          id:'',
          label:'',
          children: []
        }],
        defaultProps: {
          children: 'childList',
          label: 'name'
        },
        UserPermVisible:false,//控制内容是否显示，false表示隐藏
    };
  },
  methods: {
      //加载所有权限
      loadPerm(userid){
        this.userid=userid;
        this.$axios.get('/api/user/perms',{params:{userid}})
        .then(result=>{
            console.log(result.data);
            if(result.data.code===200){

                this.permids=result.data.data.userPerm;
                this.permsItem=result.data.data.permsList;   
         }   
        }).catch(e=>{
          this.$message({
            showClose: true,
            message: "服务器去火星了...",
            type: "error",
          });
          console.log(e);
        })
    },
    //取消按钮
    btnexit(){
        this.userid='';
        this.permsitem=[];
        this.userpermsitems=[];
        this.UserPermVisible=false;
    },

    //授权按钮
    btnPerms(){
      //获得所有状态为全选的节点值
      let key1=this.$refs.tree.getCheckedKeys();
      //获得所有半选状态节点的值
      let key2=this.$refs.tree.getHalfCheckedKeys();
      //拼接字符串
      let key=key1.concat(key2).join(",");

      this.$axios.get('/api/user/addPerms',{params:{userid:this.userid,permids:key}})
      .then(result=>{
        console.log(result.data);
        if(result.data.code===200){
          this.$message({
            showClose: true,
            message:"用户授权成功...",
            type: "success",
          });
        }else{
          this.$message({
            showClose: true,
            message: result.data.msg,
            type: "warning",
          });
        }

        this.btnexit();//清空树控件
      }).catch(e=>{
        this.$message({
            showClose: true,
            message: "服务器去火星了...",
            type: "error",
          });
          console.log(e);
      })     
    }

  },
  components: {},
  computed: {},
  created(){
    }
}

</script>
<style scoped>
</style>