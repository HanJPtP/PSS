package com.woniu.controller;

import com.woniu.fo.PssPerfItem;
import com.woniu.pojo.PssPerf;
import com.woniu.pojo.PssPost;
import com.woniu.pojo.PssQuota;
import com.woniu.pojo.PssStaffs;
import com.woniu.service.impl.PostQuotaServiceImpl;
import com.woniu.util.PageInfo;
import com.woniu.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PssPostQuotaController {

    private final PostQuotaServiceImpl postQuotaService;
    public PssPostQuotaController(PostQuotaServiceImpl postQuotaService) {
        this.postQuotaService = postQuotaService;
    }
    //根据所长和班长的管理权限,显示对应组员信息
    @GetMapping("/quota/list")
    public ResponseResult<PageInfo> getQuotaStaffs(@RequestParam(name = "account",required = false,defaultValue = "")String account,
                                                   @RequestParam(name = "pageNo",required = false,defaultValue = "1")Integer pageNo,
                                                   @RequestParam(name = "pageSize",required = false,defaultValue = "5")Integer pageSize){
        PageInfo<PssStaffs> pageInfo = postQuotaService.getStaffsIsName(account, pageNo, pageSize);
        ResponseResult<PageInfo> responseResult = null;
        if (pageInfo.getData().size()>0){
            responseResult = new ResponseResult<>(200, "OK", pageInfo);
        }else{
            responseResult = new ResponseResult<>(2002, "加载员工信息失败,请联系管理员!" );
        }
        return responseResult;
    }
    // 根据当前登录人的postid查询当前员工要考核的选项
    @GetMapping("/quota/getquota")
    public ResponseResult<Object> getQuotaList(String account){
        List<PssQuota> quotas = postQuotaService.getQuotaListByPostid(account);
        ResponseResult<Object> responseResult = null;
        if (quotas.size()>0){
            responseResult = new ResponseResult<>(200, "OK", quotas);
        }else{
            responseResult = new ResponseResult<>(2002, "加载考核信息失败,请联系管理员!" );
        }
        return responseResult;
    }
    /**
     * 添加考核评分
     */
    @PostMapping("/quota/saveperf")
    public ResponseResult<Void> savePerf(@RequestBody PssPerfItem pssPerfsItem){
        List<PssQuota> quetaItem = pssPerfsItem.getQuotaItem();
        String appname = pssPerfsItem.getAppname();
        Integer staffsid = pssPerfsItem.getStaffs().getId();
        System.out.println("appname的值是"+appname);
        System.out.println("staffsid的值是"+staffsid);
        System.out.println(quetaItem.get(0).toString());
        List<PssPerf> perfList = new ArrayList<>();
        for (int i = 0; i < quetaItem.size(); i++) {

            String quotaName = quetaItem.get(i).getQuotaName();
            Double score = quetaItem.get(i).getScore();
            Double weight = quetaItem.get(i).getWeight();
            PssPerf pssPerf = new PssPerf(score, quotaName, appname, weight, staffsid);
            perfList.add(pssPerf);
        }
        int i = postQuotaService.savePerf(perfList);
        ResponseResult<Void> responseResult = null;
        if (i>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else{
            responseResult = new ResponseResult<>(2002, "添加考核评分信息失败,请联系管理员!" );
        }
        return responseResult;
    }
}
