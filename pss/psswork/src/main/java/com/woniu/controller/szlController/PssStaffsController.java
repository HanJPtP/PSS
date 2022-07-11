package com.woniu.controller.szlController;

import com.woniu.dao.redis.UserRedisDao;
import com.woniu.pojo.PssPost;
import com.woniu.pojo.PssStaffs;
import com.woniu.pojo.PssUsers;
import com.woniu.service.impl.PssPostService;
import com.woniu.service.impl.PssStaffsService;
import com.woniu.util.PageInfo;
import com.woniu.util.ResponseResult;
import com.woniu.util.mutil.GenerateNoUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
public class PssStaffsController {

    @Resource
    private PssPostService pssPostService;

    @Resource
    private PssStaffsService pssStaffsService;

    @Resource
    private UserRedisDao userRedisDao;

    @Resource
    private PasswordEncoder passwordEncoder;

    public static Integer userid;

    /**
     * 员工列表
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("staff/list")
    public ResponseResult<Object> userList(@RequestParam(value = "username",required = false,defaultValue = "null")String username,
                                           @RequestParam(value = "postid",required = false,defaultValue = "-1")Integer postid,
                                           @RequestParam(value = "name",required = false,defaultValue = "")String name,
                                           @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
                                           @RequestParam(value = "pageSize",required = false,defaultValue = "5")Integer pageSize){

        //到redis查询用户信息
        PssUsers users = userRedisDao.findById(username).get();
        //获得用户的userid
        Integer usersId = users.getId();
        //获得用户的职位p_id
        Integer pId = users.getPId();

        //职位列表
        List<PssPost> postList = null;
        //员工数据
        PageInfo<PssStaffs> pageInfo = null;
        List<PssPost> postList1 = pssPostService.postList(2);
        List<PssPost> postList2 = pssPostService.postList(3);

        if(pId==1){//所长，拥有所有信息
            postList1.addAll(postList2);
            postList=postList1;
            pageInfo = pssStaffsService.staffList(-1, postid, name, pageNo, pageSize);
        }else if(pId==2){//综合班班长
            postList=postList1;
            pageInfo = pssStaffsService.staffList(usersId, postid, name, pageNo, pageSize);
        }else if(pId==3){//配电营业班班长
            postList=postList2;
            pageInfo = pssStaffsService.staffList(usersId, postid, name, pageNo, pageSize);
        }

        //存储数据
        HashMap<String,Object> staffMap = new HashMap<>();
        staffMap.put("postList", postList);
        staffMap.put("pageInfo",pageInfo);

        ResponseResult<Object> responseResult=new ResponseResult<>(200, "OK",staffMap);

        return responseResult;
    }

    /**
     * 添加员工
     * @param staff
     * @return
     */
    @PostMapping("staff/add")
    public ResponseResult<Void> userAdd(@RequestBody PssStaffs staff){
        //获得用户输入的密码
        String pwd = staff.getPassword();
        //进行加密，重新设置
        String password = passwordEncoder.encode(pwd);
        staff.setPassword(password);

        String staffNo = GenerateNoUtil.getStaffNo();
        staff.setJobno(staffNo);

        int n = pssStaffsService.staffAdd(staff);

        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(3002, "添加员工失败！");
        }
        return responseResult;
    }

    /**
     * 修改员工
     * @param staff
     * @return
     */
    @PostMapping("staff/edit")
    public ResponseResult<Void> userEdit(@RequestBody PssStaffs staff){

        int n = pssStaffsService.staffUpdate(staff);

        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(3002, "修改员工失败！");
        }
        return responseResult;
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @GetMapping("staff/delete")
    public ResponseResult<Void> userDelete(@RequestParam(value = "id",required = false,defaultValue = "")Integer id){

        int n = pssStaffsService.staffDelete(id);

        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(3002, "删除员工失败！");
        }
        return responseResult;
    }





}
