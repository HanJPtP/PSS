package com.woniu.controller;

import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssBmessage;
import com.woniu.pojo.PssBusiness;
import com.woniu.service.impl.PssBmessageServiceImpl;
import com.woniu.service.impl.PssBusinessServiceImpl;
import com.woniu.util.ResponseResult;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class PssBmessageController {
    private final PssBusinessServiceImpl pssBusinessService;
    private final PssBmessageServiceImpl pssBmessageService;
    public PssBmessageController(PssBmessageServiceImpl pssBmessageService, PssBusinessServiceImpl pssBusinessService) {
        this.pssBmessageService = pssBmessageService;
        this.pssBusinessService = pssBusinessService;
    }
    /**
     * 多条件分页查询新型业务列表信息
     * @return
     */
    @GetMapping("/bmessage/list")
    public ResponseResult<PageInfo> getBmessageList(@RequestParam(name = "pageNo",required = false,defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize",required = false,defaultValue = "5")Integer pageSize,
                                                    @RequestParam(name = "btitle",required = false,defaultValue = "")String btitle,
                                                    @RequestParam(name = "busname",required = false,defaultValue = "")String busname,
                                                    @RequestParam(name = "username",required = false,defaultValue = "")String username,
                                                    @RequestParam(name = "account",required = false,defaultValue = "")String account
                                                    ){
        PageInfo<PssBmessage> pageInfo = pssBmessageService.getBmessageList(pageNo, pageSize, btitle, busname, username);
        System.out.println("========+"+account);
        Integer userid = pssBmessageService.getAccount(account);
        ResponseResult<PageInfo> responseResult = null;
        if (pageInfo.getData().size()>0){
            responseResult = new ResponseResult<>(200, ""+userid, pageInfo);
        }else {
            responseResult = new ResponseResult<>(2002, "加载新型业务列表失败,请联系管理员!");
        }
        return responseResult;
    }
    /**
     * 添加新型业务信息
     */
    @PreAuthorize("hasAuthority('business:add')")
    @PostMapping("/bmessage/add")
    public ResponseResult<Void> saveBmessage(@RequestBody PssBmessage bmessage){
        bmessage.setCreationtime(new Date());
        System.out.println("======"+bmessage);
        int i = pssBmessageService.saveBmessage(bmessage);
        ResponseResult<Void> responseResult = null;
        if (i>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(2002, "添加新型业务失败,请联系管理员!");
        }
        return responseResult;
    }
    /**
     * 修改新型业务信息
     */
    @PreAuthorize("hasAuthority('business:update')")
    @PostMapping("/bmessage/upd")
    public ResponseResult<Void> updBmessage(@RequestBody PssBmessage pssBmessage){
        int i = pssBmessageService.updBmessage(pssBmessage);
        ResponseResult<Void> responseResult = null;
        if (i>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(2002, "修改新型业务失败,请联系管理员!");
        }
        return responseResult;
    }
    /**
     * 删除新型业务信息
     */
    @PreAuthorize("hasAuthority('business:delete')")
    @GetMapping ("/bmessage/del")
    public ResponseResult<Void> delBmessage(Integer id){
        int i = pssBmessageService.delBmessage(id);
        ResponseResult<Void> responseResult = null;
        if (i>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(2002, "删除新型业务失败,请联系管理员!");
        }
        return responseResult;
    }
    /**
     * 查询所有的新型业务类型
     */
    @GetMapping("/bmessage/type")
    public ResponseResult<Object> queryAllBusiness(){
        List<PssBusiness> busItem = pssBusinessService.queryAllBusiness();
        ResponseResult<Object> responseResult = null;
        if (busItem.size()>0){
            responseResult = new ResponseResult<>(200,"Ok",busItem);
        }else {
            responseResult = new ResponseResult<>(2002, "加载业务类型失败,请联系管理员!");
        }
        return responseResult;
    }
    /**
     * 判断是否有添加新型业务权限
     */
    @PreAuthorize("hasAuthority('business:add')")
    @PostMapping("/bmessage/isAdd")
    public ResponseResult<Void> IsCourtsAdd(){
        return new ResponseResult<>(200, "OK");
    }

    /**
     * 判断是否有修改新型业务权限
     */
    @PreAuthorize("hasAuthority('business:update')")
    @PostMapping("/bmessage/isUpd")
    public ResponseResult<Void> IsCourtsUpd(){
        return new ResponseResult<>(200, "OK");
    }

}
