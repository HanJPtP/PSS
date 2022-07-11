package com.woniu.controller;

import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssApproval;
import com.woniu.pojo.PssApprovaltype;
import com.woniu.pojo.PssLeavetype;
import com.woniu.service.impl.PssApprovalServiceImpl;
import com.woniu.service.impl.PssApprovaltypeServiceImpl;
import com.woniu.service.impl.PssLeavetypeServiceImpl;
import com.woniu.util.OrderUtil;
import com.woniu.util.ResponseResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ApprovalController {
    @Resource
    private PssApprovalServiceImpl approvalService;
    @Resource
    private PssApprovaltypeServiceImpl approvaltypeService;
    @Resource
    private PssLeavetypeServiceImpl leavetypeService;

    /**
     * 显示请假原因
     * 事假，病假
     * @return
     */
    @GetMapping("/leavetype/list")
    public ResponseResult<Object> getLeaveType(){
        List<PssLeavetype> leavetype = leavetypeService.leavetypeList();
        ResponseResult<Object> responseResult = null;
        if(leavetype.size()>0){
            responseResult = new ResponseResult<>(200,"ok",leavetype);
        }else{
            responseResult = new ResponseResult<>(2002,"没有数据");
        }
        return responseResult;
    }

    /**
     * 获得所有的请假类型
     * 补卡，请假，出差
     * @return
     */
    @GetMapping("/approvaltype/list")
    public ResponseResult<Object> getApprovalType(){
        List<PssApprovaltype> approvaltypes=approvaltypeService.getApprovalType();
        ResponseResult<Object> responseResult = null;
        if(approvaltypes.size()>0){
            responseResult = new ResponseResult<>(200,"ok",approvaltypes);
        }else {
            responseResult = new ResponseResult<>(2002,"没有数据");
        }
        return responseResult;
    }

    /**
     * 请假
     * @param pssApproval
     * @return
     */
    @ResponseBody
    @PostMapping("/approval/leave")
    public ResponseResult<Void> askForLeave(@RequestBody PssApproval pssApproval){
        pssApproval.setApprovalno(OrderUtil.getLeaveNo());
        LocalDateTime starttime = pssApproval.getStarttime();
        LocalDateTime endtime = pssApproval.getEndtime();
        //获得中间时隔的小时差
        Duration duration = Duration.between(starttime, endtime);
        Double leavealltime = Double.valueOf(duration.toHours());
        pssApproval.setLeavealltime(leavealltime);
        LocalDateTime rightNow = LocalDateTime.now();
        pssApproval.setApprovaltime(rightNow);
        pssApproval.setUserid(1);
        pssApproval.setStatus(0);
//        System.out.println("相差"+leavealltime+"小时");
        int n = approvalService.addApproval(pssApproval);
        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200,"请假提交成功");
        }else {
            responseResult = new ResponseResult<>(2003,"请假提交失败");
        }
        return responseResult;
    }

    /**
     * 出差
     * @param pssApproval
     * @return
     */
    @ResponseBody
    @PostMapping("/approval/evection")
    public ResponseResult<Void> addEvectionInfo(@RequestBody PssApproval pssApproval){
        pssApproval.setApprovalno(OrderUtil.getEvectionNo());
        LocalDateTime starttime = pssApproval.getStarttime();
        LocalDateTime endtime = pssApproval.getEndtime();
        //获得中间时隔的小时差
        Duration duration = Duration.between(starttime, endtime);
        Double leavealltime = Double.valueOf(duration.toHours());
        pssApproval.setLeavealltime(leavealltime);
        LocalDateTime rightNow = LocalDateTime.now();
        pssApproval.setApprovaltime(rightNow);
        pssApproval.setUserid(1);
        pssApproval.setStatus(0);
//        System.out.println("相差"+leavealltime+"小时");
        int n = approvalService.addApproval(pssApproval);
        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200,"出差提交成功");
        }else {
            responseResult = new ResponseResult<>(2003,"出差提交失败");
        }
        return responseResult;
    }

    /**
     * 补卡
     * @param leavetime
     * @param caremakeup
     * @param leaveresoult
     * @return
     */
    @ResponseBody
    public ResponseResult<Void> Cardreissue(@RequestBody
                                            @RequestParam(value = "leavetime",required = false,defaultValue = "") LocalDateTime leavetime,
                                            @RequestParam(value = "caremakeup",required = false,defaultValue = "") LocalDateTime caremakeup,
                                            @RequestParam(value = "leaveresoult",required = false,defaultValue = "")String leaveresoult){
        PssApproval approval = new PssApproval();
        approval.setLeavetime(leavetime);
        approval.setCaremakeuptime(caremakeup);
        approval.setStatus(0);
        int n = approvalService.addApproval(approval);
        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200,"出差请求提交成功");
        }else {
            responseResult = new ResponseResult<>(2003,"出差请求提交失败");
        }
        return responseResult;
    }

    /**
     * 多条件查询并分页
     * @param approvalid
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/approval/list")
    public ResponseResult<Object> getApprovalList(@RequestParam(value = "approvalid",required = false,defaultValue ="-1") Integer approvalid,
                                                  @RequestParam(value = "pageNo",required = false,defaultValue = "") Integer pageNo,
                                                  @RequestParam(value = "pageSize",required = false,defaultValue = "")Integer pageSize){
        PageInfo<PssApproval> pageInfo = approvalService.getApprovalList(approvalid, pageNo, pageSize);
        ResponseResult<Object> responseResult = null;
        if(pageInfo.getData().size()>0) {
            responseResult = new ResponseResult<>(200, "ok", pageInfo);
        }else {
            responseResult = new ResponseResult<>(500,"没有数据");
        }
        return responseResult;
    }

    /**
     *  修改讲师权限验证
     * @return
     */
    @PostMapping("/approval/updateshow")
    public ResponseResult<Void> updateshow(){
        ResponseResult<Void> responseResult = null;
        return new ResponseResult<>(200,"ok");
    }

    /**
     * 审批同意
     * @param approval
     * @return
     */
    @ResponseBody
    @PostMapping("/approval/agree")
    public ResponseResult<Void> agreeApproval(@RequestBody PssApproval approval){
        approval.setStatus(1);
        int n = approvalService.editApproval(approval);
        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200,"ok");
        }else {
            responseResult = new ResponseResult<>(2002,"审批失败");
        }
        return responseResult;
    }

    /**
     * 审批拒绝
     * @param approval
     * @return
     */
    @ResponseBody
    @PostMapping("/approval/refuse")
    public ResponseResult<Void> refuseApproval(@RequestBody PssApproval approval){
        approval.setStatus(2);
        int n = approvalService.editApproval(approval);
        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200,"ok");
        }else {
            responseResult = new ResponseResult<>(2002,"审批失败");
        }
        return responseResult;
    }
}
