package com.woniu.service;


import com.woniu.fo.ItemAddPaperFo;
import com.woniu.vo.TestPaperVo;

import java.util.List;

public interface IPssTestBatch {

    int insertTestPaper(List<ItemAddPaperFo> itemAddPaperFos)throws Exception;

    List<TestPaperVo> showTestPaper()throws Exception;

    int updatePssTestBatch(Integer testId, String status)throws Exception;
}
