package com.woniu.fo;

import com.woniu.pojo.PssTestItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCreateTestPaperFo {
    //题目
    private PssTestItem pssTestItem;
    //选项
    private List<PssTestItem> pssTestItems;

    public List<ItemCreateTestPaperFo> createPaperListFromXlsx(InputStream in) throws Exception {
        //存读取到达xlsx格式文件的对象
        List<ItemCreateTestPaperFo> itemCreateTestPaperFoList = new ArrayList<>();

        List<PssTestItem> pssTestItemList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        //获得第一张表
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        //当得到的第一个表的对象最大有效行数为0时,表示不在该表中
        if (sheetAt.getLastRowNum() <= 1) {
            //返回空值
            return itemCreateTestPaperFoList;
        } else {
            //有效行数大于0时
            for (int i = 1; i <= sheetAt.getLastRowNum(); i++) {
                //获得每一行的对象
                XSSFRow row = sheetAt.getRow(i);
                if (row != null) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    short lastCellNum = row.getLastCellNum();
                    for (int j = 0; j < lastCellNum; j++) {
                        //设置读取的方式
                        XSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            cell.setCellType(CellType.STRING);
                            //得到该对象的值
                            String stringCellValue = cell.getStringCellValue();
                            arrayList.add(stringCellValue);
                        } else {
                            arrayList.add(null);
                        }
                    }
                    for (String a : arrayList) {
                        if (a.length() > 0) {
                            System.out.println(a.split("\\.")[0]);
                        } else {
                            System.out.println(a);
                        }
                    }
                    if (arrayList.size() == 6) {
                        PssTestItem pssTestItem = new PssTestItem(Integer.parseInt(arrayList.get(0).split("\\.")[0]), arrayList.get(1), Integer.parseInt(arrayList.get(2).split("\\.")[0]), Integer.parseInt(arrayList.get(3).split("\\.")[0]), arrayList.get(4), arrayList.get(5), null);
                        System.out.println(pssTestItem.toString());
                        pssTestItemList.add(pssTestItem);
                    }
                }
            }
        }
        workbook.close();
        //封装成itemCreateTestPaperFoList集合导出
        for (PssTestItem p : pssTestItemList) {
            if (p.getTestyn().equals("") && p.getParentid() == 0) {
                //选择题
                ItemCreateTestPaperFo itemCreateTestPaperFo = new ItemCreateTestPaperFo();
                itemCreateTestPaperFo.setPssTestItem(p);
                itemCreateTestPaperFoList.add(itemCreateTestPaperFo);
//                pssTestItemList.remove(p);
            } else if ((p.getTestyn().equals("y") || p.getTestyn().equals("n")) && p.getParentid() == 0) {
                //判断题
                ItemCreateTestPaperFo itemCreateTestPaperFo = new ItemCreateTestPaperFo();
                itemCreateTestPaperFo.setPssTestItem(p);
                itemCreateTestPaperFoList.add(itemCreateTestPaperFo);
//                pssTestItemList.remove(p);
            } else if ((p.getTestyn().equals("y") || p.getTestyn().equals("n")) && p.getParentid() != 0) {
                //说明是选项
                if (itemCreateTestPaperFoList.size() > 0 && pssTestItemList.size() > 0) {
                    //从中找到符合条件的存入对应的集合中
                    for (ItemCreateTestPaperFo it : itemCreateTestPaperFoList) {
                        ArrayList<PssTestItem> pssTestItems = new ArrayList<>();
                        for (PssTestItem ps : pssTestItemList) {
                            if (ps.getParentid() != 0) {
                                if (it.getPssTestItem().getId() == ps.getParentid()) {
                                    pssTestItems.add(ps);
//                                    pssTestItemList.remove(ps);
                                }
                            }
                        }
                        //存入对应对象中
                        if (pssTestItems.size() > 1) {
                            it.setPssTestItems(pssTestItems);
                        }
                    }
                }
            }
        }
        return itemCreateTestPaperFoList;
    }
}
