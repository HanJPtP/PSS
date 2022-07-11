import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {path: '/',redirect:"/login"},
{path: '/login',name: 'login',component: () => import( '../views/LoginView.vue')},
  {
    name: "askforleave",
    path: "/attend/askforleave",
       component: () => import("../components/attendance/AskForLeaveView.vue"),
  },
  {path: '/home', name: 'home', component: () => import('@/views/HomeView.vue'),children:[
    {path: '/user', name: 'user', component: () => import('@/components/user/UserManager.vue')},//管理列表
    {path: '/staff', name: 'staff', component: () => import('@/components/staff/StaffManager.vue')},//员工列表
    {path: '/courts', name: 'courts', component: () => import('../components/courts/CourtsIndexView.vue')},//台区列表
    {path: '/business', name: 'business', component: () => import('../components/business/BusinessIndexView.vue') },//新业务咨询
    {path: '/exam', name: 'exam', component: () => import('../components/exam/ExamIndexView.vue') },//考试题目
    {path: '/listtask', name: "listtask", component: () => import('@/components/orderTask/ListTaskView.vue') },
    {path: '/listpatrol',name:"listpatrol",component:() => import('@/components/orderprocess/processpatrol/ListPatrolView.vue')},
    {path: '/listtrouble',name:"listtrouble",component:() => import('@/components/orderprocess/processtrouble/ListTroubleView.vue')},
    {path: '/listemergent', name: "listemergent", component: () => import('@/components/orderprocess/processemergent/ListTroubleView.vue') },
    {path: '/eleclist',name:"eleclist",component:() => import('@/components/orderElec/elec/ListView.vue')},
    {path: '/elecdoor', name: "elecdoor", component: () => import('@/components/orderElec/door/ListView.vue') },
    {path: '/perf',name:"perf",component:() => import('@/components/perf/PerfIndexView.vue')},
  ]},
      { path:'/checklist', name: 'checklist', component: () => import('../components/warehouse/PssCheckIndex.vue') },
      { path: '/inoutlist', name: 'inoutlist', component: () => import('../components/warehouse/WhdatainoutUpdate.vue') },
      {path: '/whlist', name: 'whlist',component: () => import( '../components/warehouse/WhdataIndex.vue')},
  {
    path: "/attend/makecardup",
    name: "makecardup",
    component: () => import("../components/attendance/MakeCardUpView.vue"),
  },
  {
    path: "/attend/approval",
    name: "approvalList",
    component: () => import("../components/attendance/ApprovalView.vue"),
  },
    
  
  {path: '/exam/show',name: 'examShow',component: () => import( '../components/exam/ExamShowByIdView.vue')},
  {path: '/exam/add',name: 'examAdd',component: () => import( '../components/exam/ExamAddView.vue')},
  {path: '/exam/update',name: 'examUpdate',component: () => import( '../components/exam/ExamUpdateView.vue')},
  {path: '/exam/test',name: 'examTest',component: () => import( '../components/exam/ExamTestPaper.vue')},
  {path: '/exam/showtest',name: 'examShowTest',component: () => import( '../components/exam/ExamTestShow.vue')},
  {path: '/warehouse/list',name: 'warehouseList',component: () => import( '../components/warehouse/WhIndex.vue')},
  {path: '/whdata/list',name: 'whdataList',component: () => import( '../components/warehouse/WhdataIndex.vue')},
  {path: '/whdata/add',name: 'whdataAdd',component: () => import( '../components/warehouse/WhdataAdd.vue')},
  {path: '/whdata/update',name: 'whdataUpdate',component: () => import( '../components/warehouse/WhdataUpdate.vue')},
  {path: '/whdatainout/list',name: 'whdatainout',component: () => import( '../components/warehouse/WhdataInoutIndex.vue')}, 
   {
    path: '/whdatainout/detail',
    name: 'psstool',
    component: () => import( '../components/warehouse/Psstoolnum.vue')
  }, 
    {
    path: '/whdatainout/add',
    name: 'inoutadd',
    component: () => import( '../components/warehouse/WhdataInoutAdd.vue')
  }, 
    {
    path: '/whdatainout/update',
    name: 'inoutupdate',
    component: () => import( '../components/warehouse/WhdatainoutUpdate.vue')
  }, 
  {
    path: '/check/list',
    name: 'checklist',
    component: () => import( '../components/warehouse/PssCheckIndex.vue')
  }, 
   {
    path: '/psscheck/detail',
    name: 'checkdetail',
    component: () => import( '../components/warehouse/PssCheckDetail.vue')
  }, 
     {
    path: '/psscheck/update',
    name: 'checkupdate',
    component: () => import( '../components/warehouse/PssCheckUpdate.vue')
  },
  
]
const router = new VueRouter({
  routes,
});

export default router;

//配置路由守卫
router.beforeEach((to, from, next) => {
  //是否要到登录去
  if (to.path == "/login") {
    //如果要去登录，直接放行
    next();
  } else {
    let token = localStorage.getItem("rememberMe");
    token ? next() : next("/login");
  }
});
