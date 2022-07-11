package com.woniu.controller.szlController;

import com.woniu.pojo.PssPerms;
import com.woniu.service.impl.PssPermsService;
import com.woniu.util.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
public class PssPermsController {

    @Resource
    private PssPermsService pssPermsService ;

    /**
     * 用户权限
     * @param
     * @return
     */
    @GetMapping("user/perms")
    public ResponseResult<Object> userPerms(@RequestParam(value = "userid",required = false,defaultValue = "") Integer userid){
        //所有权限
        List<PssPerms> permsList = pssPermsService.listPerms();
        List<Integer> userPerm = pssPermsService.UserPermByUserId(userid);

        HashMap<String,Object> permsMap=new HashMap<>();
        permsMap.put("permsList",permsList);
        permsMap.put("userPerm",userPerm);

        ResponseResult<Object> responseResult = null;

        responseResult=new ResponseResult<>(200, "OK",permsMap);

        return responseResult;
    }

    /**
     * 用户权限
     * @param
     * @return
     */
    @GetMapping("user/addPerms")
    public ResponseResult<Void> addPerms(@RequestParam(value = "userid",required = false,defaultValue = "") Integer userid,
                                         @RequestParam(value = "permids",required = false,defaultValue = "") String permids){
        int n = pssPermsService.addPermByUserId(userid, permids.split(","));

        ResponseResult<Void> responseResult = null;

        if(n>0){
            responseResult= new ResponseResult<>(200,"OK");
        }else{
            responseResult=new ResponseResult<>(500,"员工授权失败...");
        }
        return responseResult;
    }


}
