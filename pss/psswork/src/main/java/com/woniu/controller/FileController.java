package com.woniu.controller;

import com.woniu.pojo.PssBmessage;
import com.woniu.service.impl.PssBmessageServiceImpl;
import com.woniu.util.MinioUtils;
import com.woniu.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

/**
 * 文件上传下载
 */
@Slf4j
@RestController
public class FileController {
    private final MinioUtils minioUtils;
    private final PssBmessageServiceImpl pssBmessageService;

    public FileController(MinioUtils minioUtils, PssBmessageServiceImpl pssBmessageService) {
        this.minioUtils = minioUtils;
        this.pssBmessageService = pssBmessageService;
    }

    @PostMapping("/upload")
    public ResponseResult<Void> upload(@RequestPart("files")MultipartFile[] files){
//        int count = 0;
        String str = "";
        for(MultipartFile file : files){
            log.info("{}",file.getOriginalFilename());
            minioUtils.putObject("business", file, file.getOriginalFilename(),file.getContentType());
            String filecontent = minioUtils.getObjectUrl("business", file.getOriginalFilename(), 1, TimeUnit.DAYS);
            // 将生成的下载链接存入数据库
//            PssBmessage pssBmessage = new PssBmessage();
//            pssBmessage.setFilecontent(filecontent);
//            int i = pssBmessageService.addFilecontent(pssBmessage);
//            if (i>0){
//                count++;
                str = filecontent;
//            }
        }
//        ResponseResult<String> responseResult = null;
//        if (count>0){
//            responseResult = new ResponseResult<>(200, ""+count,str);
//        }else{
//           responseResult = new ResponseResult<>(2002, "上传文件失败,请联系管理员!");
//        }
        ResponseResult<Void> responseResult = null;
        if (str.length()>0){
            responseResult = new ResponseResult<>(200, str);
        }else{
            responseResult = new ResponseResult<>(2002, "上传文件失败,请联系管理员!");
        }
        return responseResult;
    }
}
