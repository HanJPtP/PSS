package com.woniu.controller.szlController;

import com.woniu.dao.redis.UserRedisDao;
import com.woniu.pojo.PssPerms;
import com.woniu.pojo.PssPost;
import com.woniu.pojo.PssStaffs;
import com.woniu.pojo.PssUsers;
import com.woniu.service.impl.PssPermsService;
import com.woniu.service.impl.PssPostService;
import com.woniu.service.impl.PssUsersService;
import com.woniu.util.CusAccessObjectUtil;
import com.woniu.util.ResponseResult;
import com.woniu.util.TokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
public class PssUsersController {

    @Resource
    private UserRedisDao userRedisDao;

    @Resource
    private PssPermsService permsService;

    @Resource
    private PssUsersService pssUsersService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private PssPostService pssPostService;

    public static PssUsers publicUser;

    /**
     * 菜单导航
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("menu")
    public ResponseResult<Object> getMenu(HttpServletRequest request) throws Exception {

        String tokenStr = request.getHeader("rememberMe");
        String username = TokenUtil.getUserName(tokenStr);

        System.out.println(username);
        //从redis获得user对象
        PssUsers user = userRedisDao.findById(username).get();
        publicUser=user;
        //获得请求的地址
        String ipAddress = CusAccessObjectUtil.getIpAddress(request);
        //更新用户的最近登录时间和IP
        user.setLoginTime(LocalDateTime.now());
        user.setLoginIp(ipAddress);

        //更新数据库
        pssUsersService.UserUpdate(user);

        List<PssPerms> pssPerms = permsService.PermsMenu(username);

        ResponseResult<Object> responseResult=null;
        if(pssPerms.size()>0){
            responseResult=new ResponseResult<>(200, pssPerms);
        }else {
            responseResult=new ResponseResult<>(2001, "该用户没有可用菜单，请联系管理员");
        }
        return responseResult;
    }

    /**
     * 管理列表
     * @return
     */
    @GetMapping("user/list")
    public ResponseResult<Object> userList(){

        //用户数据
        List<PssUsers> userList = pssUsersService.userList();
        List<PssPost> postList = null;
        List<PssPost> postList1 = pssPostService.postList(0);
        List<PssPost> postList2 = pssPostService.postList(1);

        if (publicUser.getStatus().equals("s")){//超级管理员
            postList1.addAll(postList2);
            postList=postList1;
        }else if(publicUser.getStatus().equals("y") && publicUser.getPId()==1){//所长
            postList=postList2;
        }

        //存储数据
        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("postList", postList);
        userMap.put("pageInfo",userList);

        ResponseResult<Object> responseResult=null;
        if(userList.size()>0){
            responseResult=new ResponseResult<>(200, "OK",userMap);
        }else {
            responseResult=new ResponseResult<>(2001, "加载管理失败！");
        }
        return responseResult;
    }

    /**
     * 添加管理
     * @param user
     * @return
     */
    @PostMapping("user/add")
    public ResponseResult<Void> userAdd(@RequestBody PssUsers user){
        //获得用户输入的密码
        String pwd = user.getPassword();
        //进行加密，重新设置
        String password = passwordEncoder.encode(pwd);
        user.setPassword(password);

        int n = pssUsersService.UserAdd(user);
        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(3002, "添加员工失败！");
        }
        return responseResult;
    }

    /**
     * 修改管理
     * @param user
     * @return
     */
    @PostMapping("user/edit")
    public ResponseResult<Void> userEdit(@RequestBody PssUsers user){

        int n = pssUsersService.UserUpdate(user);

        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(3002, "修改管理失败！");
        }
        return responseResult;
    }

    /**
     * 删除管理
     * @param id
     * @return
     */
    @GetMapping("user/delete")
    public ResponseResult<Void> userDelete(@RequestParam(value = "id",required = false,defaultValue = "")Integer id){

        int n = pssUsersService.UserDelete(id);

        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(3002, "删除管理失败！");
        }
        return responseResult;
    }
}
