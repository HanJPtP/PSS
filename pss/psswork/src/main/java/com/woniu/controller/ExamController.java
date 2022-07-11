package com.woniu.controller;


import com.woniu.fo.*;
import com.woniu.pojo.PageInfo;

import com.woniu.pojo.PssTestItem;
import com.woniu.pojo.PssTestType;
import com.woniu.service.impl.PssTestBatchImpl;
import com.woniu.service.impl.PssTestItemServiceIpml;
import com.woniu.service.impl.PssTestTypeServiceImpl;
import com.woniu.util.MinioUtils;
import com.woniu.util.ResponseResult;
import com.woniu.vo.ItemTestPaperVo;
import com.woniu.vo.ItemVo;
import com.woniu.vo.TestPaperVo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor //为final的对象实例化时注入值
public class ExamController {

    private final PssTestItemServiceIpml pssTestItemServiceIpml;

    private final PssTestTypeServiceImpl pssTestTypeServiceImpl;

    private final PssTestBatchImpl pssTestBatch;

    /**
     * 获得题目种类
     *
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/showtype")
//    @PreAuthorize("hasAuthority('subject:list')")
    public ResponseResult<Object> showTestType() {
        ResponseResult<Object> responseResult = null;
        List<PssTestType> pssTestTypes = pssTestTypeServiceImpl.listAllPssTestType();
        if (pssTestTypes.size() > 0) {
            responseResult = new ResponseResult<>(200, "ok", pssTestTypes);
        } else {
            responseResult = new ResponseResult<>(2002, "题目种类加载失败");
        }
        return responseResult;
    }

    /**
     * 主页面显示
     *
     * @param pageNo
     * @param pageSize
     * @param itemName
     * @param typeid
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/showitem")
//    @PreAuthorize("hasAuthority('subject:list')")
    public ResponseResult<Object> showTestItem(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                               @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "itemName", required = false, defaultValue = "") String itemName,
                                               @RequestParam(value = "typeid", required = false, defaultValue = "-1") Integer typeid) {
        ResponseResult<Object> responseResult = null;
        PageInfo<PssTestItem> pssTestItemByPageAndWrapper = pssTestItemServiceIpml.getPssTestItemByPageAndWrapper(pageNo, pageSize, itemName, typeid);
        if (pssTestItemByPageAndWrapper.getData().size() > 0) {
            responseResult = new ResponseResult<>(200, "ok", pssTestItemByPageAndWrapper);
        } else {
            responseResult = new ResponseResult<>(2002, "未查到题目");
        }
        return responseResult;
    }

    /**
     * 有无首页登录权限
     *
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/showitemtest")
//    @PreAuthorize("hasAuthority('subject:list')")
    public ResponseResult<Void> testShowPerms() {
        return new ResponseResult<>(200, "ok");
    }

    /**
     * 有没有修改权限
     *
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/updateitemtest")
    @PreAuthorize("hasAuthority('subject:update')")
    public ResponseResult<Void> testUpdatePerms() {
        return new ResponseResult<>(200, "ok");
    }

    /**
     * 有没有添加权限
     *
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/additemtest")
    @PreAuthorize("hasAuthority('subject:add')")
    public ResponseResult<Void> testAddPerms() {
        return new ResponseResult<>(200, "ok");
    }

    /**
     * 查询对应题目数据
     *
     * @param id
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/showitemvo")
    public ResponseResult<Object> showItemVo(@RequestParam(value = "id", required = false, defaultValue = "1") Integer id) {
        ResponseResult<Object> responseResult = null;
        ItemVo itemVoById = pssTestItemServiceIpml.getItemVoById(id);
        if (itemVoById != null) {
            responseResult = new ResponseResult<>(200, "ok", itemVoById);
        } else {
            responseResult = new ResponseResult<>(2003, "没有查到对应题目数据");
        }
        return responseResult;
    }

    /**
     * 对应添加题目
     *
     * @param itemfo
     * @return
     */
    @SneakyThrows
    @PostMapping("/test/additem")
    @PreAuthorize("hasAuthority('subject:add')")
    public ResponseResult<Void> addItemFo(@RequestBody ItemFo itemfo) {
        ResponseResult<Void> responseResult = null;
        if (itemfo.getPssTestItemList().size() <= 0 && itemfo.getPssTestItem().getTestyn() != null) {
            //表示是判断题
            itemfo.getPssTestItem().setTesttypeid(3);
            int i = pssTestItemServiceIpml.insertItem(itemfo);
            if (i > 0) {
                responseResult = new ResponseResult<>(200, "ok");
            } else {
                responseResult = new ResponseResult<>(4004, "添加判断题失败");
            }
        } else if (itemfo.getPssTestItemList().size() > 1 && itemfo.getPssTestItem().getTestyn().equals("")) {
            //表示是选择题
            int count = 0;
            //判断单选还是多选
            for (int i = 0; i < itemfo.getPssTestItemList().size(); i++) {
                PssTestItem pssTestItem = itemfo.getPssTestItemList().get(i);
                if (pssTestItem.getTestyn().equals("y")) {
                    count++;
                }
            }
            if (count == 1) {
                //单选题
                itemfo.getPssTestItem().setTesttypeid(1);
                for (int i = 0; i < itemfo.getPssTestItemList().size(); i++) {
                    itemfo.getPssTestItemList().get(i).setTesttypeid(1);
                }
                int i = pssTestItemServiceIpml.insertItem(itemfo);
                if (i > 0) {
                    responseResult = new ResponseResult<>(200, "ok");
                } else {
                    responseResult = new ResponseResult<>(4004, "添加单选题失败");
                }
            } else if (count > 1) {
                //多选题
                itemfo.getPssTestItem().setTesttypeid(2);
                for (int i = 0; i < itemfo.getPssTestItemList().size(); i++) {
                    itemfo.getPssTestItemList().get(i).setTesttypeid(2);
                }
                int i = pssTestItemServiceIpml.insertItem(itemfo);
                if (i > 0) {
                    responseResult = new ResponseResult<>(200, "ok");
                } else {
                    responseResult = new ResponseResult<>(4004, "添加多选题失败");
                }
            } else {
                responseResult = new ResponseResult<>(4002, "不是符合要求的选择题");
            }
        } else {
            responseResult = new ResponseResult<>(4003, "不是符合要求的单选或者多选或者判断题");
        }
        return responseResult;
    }

    /**
     * 删除,即修改题目状态
     *
     * @param id
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/delitem")
    @PreAuthorize("hasAuthority('subject:delete')")
    public ResponseResult<Void> delItem(@RequestParam(value = "id", required = false, defaultValue = "1") Integer id) {
        ResponseResult<Void> responseResult = null;
        int i = pssTestItemServiceIpml.delItemById(id, "n");
        if (i > 0) {
            responseResult = new ResponseResult<>(200, "禁用成功");
        } else {
            responseResult = new ResponseResult<>(5002, "禁用失败");
        }
        return responseResult;
    }

    /**
     * 恢复,即修改题目状态
     *
     * @param id
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/recoveryitem")
    @PreAuthorize("hasAuthority('subject:delete')")
    public ResponseResult<Void> recoveryItem(@RequestParam(value = "id", required = false, defaultValue = "1") Integer id) {
        ResponseResult<Void> responseResult = null;
        int i = pssTestItemServiceIpml.delItemById(id, "y");
        if (i > 0) {
            responseResult = new ResponseResult<>(200, "恢复成功");
        } else {
            responseResult = new ResponseResult<>(5002, "恢复失败");
        }
        return responseResult;
    }

    /**
     * 修改题目
     *
     * @param itemUpdateVo
     * @return
     */
    @SneakyThrows
    @PostMapping("/test/updateitem")
    @PreAuthorize("hasAuthority('subject:update')")
    public ResponseResult<Void> updateItem(@RequestBody ItemUpdateFo itemUpdateVo) {
        ResponseResult<Void> responseResult = null;
        if (itemUpdateVo.getPssTestItem().getTestyn().equals("") && itemUpdateVo.getPssTestItems().size() > 1) {
            //符合选择题要求
            int count = 0;
            for (int i = 0; i < itemUpdateVo.getPssTestItems().size(); i++) {
                if (itemUpdateVo.getPssTestItems().get(i).getTestyn().equals("y")) {
                    count++;
                }
            }
            if (count == 1) {
                //单选题
                itemUpdateVo.getPssTestItem().setTesttypeid(1);
                for (int i = 0; i < itemUpdateVo.getPssTestItems().size(); i++) {
                    itemUpdateVo.getPssTestItems().get(i).setTesttypeid(1);
                }
                int i = pssTestItemServiceIpml.updateItemToChangeType(itemUpdateVo);
                if (i > 0) {
                    responseResult = new ResponseResult<>(200, "ok");
                } else {
                    responseResult = new ResponseResult<>(4004, "修改单选题失败");
                }
            } else if (count > 1) {
                //多选题
                itemUpdateVo.getPssTestItem().setTesttypeid(2);
                for (int i = 0; i < itemUpdateVo.getPssTestItems().size(); i++) {
                    itemUpdateVo.getPssTestItems().get(i).setTesttypeid(2);
                }
                int i = pssTestItemServiceIpml.updateItemToChangeType(itemUpdateVo);
                if (i > 0) {
                    responseResult = new ResponseResult<>(200, "ok");
                } else {
                    responseResult = new ResponseResult<>(4004, "修改多选题失败");
                }
            } else {
                responseResult = new ResponseResult<>(4002, "不是符合要求的选择题");
            }
        } else if (itemUpdateVo.getPssTestItem().getTestyn().equals("y") || itemUpdateVo.getPssTestItem().getTestyn().equals("n")) {
            //符合判断题要求
            itemUpdateVo.getPssTestItem().setTesttypeid(3);
            int i = pssTestItemServiceIpml.updateItemById(itemUpdateVo.getPssTestItem());
            if (i > 0) {
                responseResult = new ResponseResult<>(200, "修改成功");
            } else {
                responseResult = new ResponseResult<>(6002, "修改失败");
            }
        } else {
            responseResult = new ResponseResult<>(6005, "不符合修改要求");
        }
        return responseResult;
    }

    /**
     * 显示又多少到可用的题目(每个种类分类显示)
     *
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/showitemcounts")
//    @PreAuthorize("hasAuthority('subject:list')")
    public ResponseResult<Object> showItemCounts() {
        ResponseResult<Object> responseResult = null;
        HashMap<Integer, ItemCountsFo> pssTestItemCountsByTypeId = pssTestItemServiceIpml.getPssTestItemCountsByTypeId();
        if (pssTestItemCountsByTypeId.size() > 0) {
            responseResult = new ResponseResult<>(200, "ok", pssTestItemCountsByTypeId);
        } else {
            responseResult = new ResponseResult<>(7002, "未查到数据");
        }
        return responseResult;
    }

    /**
     * 生成试卷
     *
     * @param itemTypeChooseCounts
     * @return
     */
    @SneakyThrows
    @PostMapping("/test/createTestPaper")
    public ResponseResult<Object> showItemTest(@RequestBody HashMap<Integer, ItemCountsFo> itemTypeChooseCounts) {
        ResponseResult<Object> responseResult = null;
        //获得页面传递过来的数据
        Set<Integer> integers = itemTypeChooseCounts.keySet();
        //获得数据库传递过来的数据
        HashMap<Integer, ItemCountsFo> pssTestItemCountsByTypeId = pssTestItemServiceIpml.getPssTestItemCountsByTypeId();
        StringBuffer msg = new StringBuffer("");
        //遍历比较
        for (Integer i : integers) {
            //如果页面传递的数据大于数据库中的数据
            if (itemTypeChooseCounts.get(i).getCounts() != null) {
                if (itemTypeChooseCounts.get(i).getCounts() > pssTestItemCountsByTypeId.get(i).getCounts()) {
                    //凭借msg
                    msg.append("题库中" + itemTypeChooseCounts.get(i).getPssTestType().getTypename() + "题目数量不足,仅有" + pssTestItemCountsByTypeId.get(i).getCounts() + "题");
                }
            }
        }
        //当msg的长度大于0时
        if (msg.toString().trim().length() > 0) {
            //返回对应的信息给页面提示
            responseResult = new ResponseResult<>(3002, msg);
        } else {
            //没有问题,从题库中取题目
            List<ItemTestPaperVo> itemTestPaperVoList = pssTestItemServiceIpml.showTheTestItemPaperVo(itemTypeChooseCounts);
            if (itemTestPaperVoList.size() > 0) {
                responseResult = new ResponseResult<>(200, "ok", itemTestPaperVoList);
            } else {
                responseResult = new ResponseResult<>(3002, "生成试卷失败");
            }
        }
        return responseResult;
    }

    @SneakyThrows
    @PostMapping("/test/addTestPaper")
    public ResponseResult<Void> addTestPaper(@RequestBody List<ItemAddPaperFo> itemAddPaperFos) {
        ResponseResult<Void> responseResult = null;
        int i = pssTestBatch.insertTestPaper(itemAddPaperFos);
        if (i > 0) {
            responseResult = new ResponseResult<>(200, "ok");
        } else {
            responseResult = new ResponseResult<>(3005, "生成考试试卷失败");
        }
        return responseResult;
    }

    /**
     * 将上传的excel转成对象存到数据库出去
     *
     * @param files
     * @return
     */
    @SneakyThrows
    @PostMapping("/test/upload")
    public ResponseResult<Void> updateTestPaPerExcelToSql(@RequestPart("files") MultipartFile[] files) {
        ResponseResult<Void> responseResult = null;
        //创建minio对象
        MinioUtils minioUtils = new MinioUtils("http://192.172.0.205:9000", "minioadmin", "minioadmin");
        //如果test存在,就不创建,不存在就创建
        if (!minioUtils.doesBucketExists("test")) {
            minioUtils.createBucket("test");
        }
        MultipartFile file = files[0];
        //上传到minio中
        String originalFilename = file.getOriginalFilename();
        String fileSuffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println(fileSuffixName);
        if (!fileSuffixName.equals(".xlsx")) {
            return responseResult = new ResponseResult<>(4002, "上传文件格式不为xlsx");
        }
        minioUtils.putObject("test", file, file.getOriginalFilename(), file.getContentType());
        //取出路径
        String test = minioUtils.getObjectUrl("test", file.getOriginalFilename(), 7, TimeUnit.DAYS);
        //得到流对象
        InputStream in = minioUtils.getObject("test", file.getOriginalFilename());
//        XSSFWorkbook sheets = new XSSFWorkbook(in);
//        //获得该对象中对应的表(通过下标)
//        XSSFSheet sheetAt = sheets.getSheetAt(0);
        //得到对应表中的最大有效行的number
//        int lastRowNum = sheetAt.getLastRowNum();
//        for (int i = 0; i <= lastRowNum; i++) {
//            int rownum = i;
//            //获得该行的对象
//            XSSFRow row = sheetAt.getRow(rownum);
//            if (row != null) {
//                //得到有效的列最大有效列
//                short lastCellNum = row.getLastCellNum();
//                //遍历每一列
//                for (int j = 0; j <= lastCellNum; j++) {
//                    //得到该列对象
//                    XSSFCell cell = row.getCell(j);
//                    if(cell!=null){
//                        //设置读取的方式
//                        cell.setCellType(CellType.STRING);
//                        //得到该对象的值
//                        String stringCellValue = cell.getStringCellValue();
//                        //输出该行该列该格子中的值
//                        System.out.println(stringCellValue);
//                    }
//                }
//            }
//        }
//
//        sheets.close();
        ItemCreateTestPaperFo itemCreateTestPaperFo = new ItemCreateTestPaperFo();
        List<ItemCreateTestPaperFo> paperListFromXlsx = itemCreateTestPaperFo.createPaperListFromXlsx(in);
//
//        if (paperListFromXlsx.size() > 0) {
//            for (ItemCreateTestPaperFo it : paperListFromXlsx) {
//                System.out.println(it.toString());
//            }
//        }
        if (paperListFromXlsx.size() > 0) {
            //存入数据库
            int i = pssTestItemServiceIpml.insertTestPaperItem(paperListFromXlsx);
            if (i > 0) {
                responseResult = new ResponseResult<>(200, "ok");
            } else {
                responseResult = new ResponseResult<>(6006, "生成题库失败");
            }
        } else {
            responseResult = new ResponseResult<>(5002, "excel载入失败");
        }
        return responseResult;
    }

    /**
     * 下载功能
     */
    @SneakyThrows
    @GetMapping("exam/download")
    public ResponseResult<Object> downLoadModelTestPaper() {
        ResponseResult<Object> responseResult = null;
        //创建minio对象
        MinioUtils minioUtils = new MinioUtils("http://192.172.0.205:9000", "minioadmin", "minioadmin");
        //得到模板文件的路径
        String test = minioUtils.getObjectUrl("test", "test.xlsx", 1, TimeUnit.DAYS);
        if (test.length() > 0) {
            responseResult = new ResponseResult<>(200, "ok", test);
        } else {
            responseResult = new ResponseResult<>(7001, "下载失败");
        }
        return responseResult;
    }

    /**
     * 显示考试试卷
     * 有多少道,多少份,题目选项是什么
     *
     * @return
     */
    @SneakyThrows
    @GetMapping("exam/showTest")
    public ResponseResult<Object> showTestPaperAll() {
        ResponseResult<Object> responseResult = null;
        List<TestPaperVo> testPaperVos = pssTestBatch.showTestPaper();
        if (testPaperVos.size() > 0) {
            responseResult = new ResponseResult<>(200, "ok", testPaperVos);
        } else {
            responseResult = new ResponseResult<>(2002, "还没有考试试卷,请生成试卷");
        }
        return responseResult;
    }

    /**
     * 修改考试题目状态
     *
     * @param testid
     * @param status
     * @return
     */
    @SneakyThrows
    @GetMapping("exam/updateTest")
    public ResponseResult<Void> updateTestPaperStatus(@RequestParam(value = "testid", required = false, defaultValue = "0") Integer testid,
                                                      @RequestParam(value = "status", required = false, defaultValue = "n") String status) {
        ResponseResult<Void> responseResult = null;
        int i = pssTestBatch.updatePssTestBatch(testid, status);
        if (i > 0) {
            responseResult = new ResponseResult<>(200, "ok");
        } else {
            responseResult = new ResponseResult<>(2002, "修改状态失败");
        }
        return responseResult;
    }

    @SneakyThrows
    @PostMapping("exam/paperdownload")
    public ResponseResult<Object> downLoadPaperTest(@RequestBody ExamTestPaperFo examTestPaperFo) {
        ResponseResult<Object> responseResult = null;
        int testid = examTestPaperFo.getTestid();
        List<ItemVo> itemVos = examTestPaperFo.getItemVos();
        String status = examTestPaperFo.getStatus();
        if (status.equals("n")) {
            responseResult = new ResponseResult<>(4007, "该试卷状态为不可用状态,请先改变状态后再下载");
        } else if (status.equals("y")) {
            //创建minio对象
            MinioUtils minioUtils = new MinioUtils("http://192.172.0.205:9000", "minioadmin", "minioadmin");
            //如果test存在,就不创建,不存在就创建
            if (!minioUtils.doesBucketExists("test")) {
                minioUtils.createBucket("test");
            }
            //创建word对象
            XWPFDocument xwpfDocument = new XWPFDocument();
            //遍历对象
            for (int i = 0; i < itemVos.size(); i++) {
                //创建大段
                XWPFParagraph paragraph = xwpfDocument.createParagraph();
                //创建行
                XWPFRun run = paragraph.createRun();
                //判断题目类型
                StringBuilder ItemName = new StringBuilder("");
                if (itemVos.get(i).getPssTestItem().getTesttypeid() == 3) {
                    ItemName.append("判断题:" + itemVos.get(i).getPssTestItem().getTestname());
                } else if (itemVos.get(i).getPssTestItem().getTesttypeid() == 2) {
                    ItemName.append("多选题:" + itemVos.get(i).getPssTestItem().getTestname());
                } else if (itemVos.get(i).getPssTestItem().getTesttypeid() == 1) {
                    ItemName.append("单选题:" + itemVos.get(i).getPssTestItem().getTestname());
                }
                //将题目写入对象中
                run.setText(ItemName.toString(), 0);
                //写选项
                if (itemVos.get(i).getPssTestItems().size() > 0) {
                    for (int j = 0; j < itemVos.get(i).getPssTestItems().size(); j++) {
                        //创建新的段落
                        XWPFRun run1 = paragraph.createRun();
                        char a = (char) ('A' + j);
                        StringBuilder stringBuilder = new StringBuilder("");
                        StringBuilder append = stringBuilder.append("      " + a + "." + itemVos.get(i).getPssTestItems().get(j).getTestname());
                        run1.setText(append.toString(), 0);
                    }
                }
            }
            //创建二进制流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //将其写入这个二进制流中
            xwpfDocument.write(byteArrayOutputStream);
            //转成输出流
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            //上传到minio中
            minioUtils.putObject("test", testid+".docx", byteArrayInputStream);
            //获得下载地址
            String test = minioUtils.getObjectUrl("test", testid + ".docx", 1, TimeUnit.DAYS);
            responseResult = new ResponseResult<>(200, "ok", test);
        }
        return responseResult;
    }
}
