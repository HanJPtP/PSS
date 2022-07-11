package com.woniu.service.impl;

import com.woniu.dao.mysql.PssTestBatchMapper;
import com.woniu.fo.ItemAddPaperFo;
import com.woniu.pojo.PssTestBatch;
import com.woniu.service.IPssTestBatch;
import com.woniu.vo.ItemVo;
import com.woniu.vo.TestPaperVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PssTestBatchImpl implements IPssTestBatch {
    @Resource
    private PssTestBatchMapper pssTestBatchMapper;
    @Resource
    private PssTestItemServiceIpml pssTestItemServiceIpml;
    /**
     * 生成考试试卷
     * @param itemAddPaperFos
     * @return
     * @throws Exception
     */
    @Override
    public int insertTestPaper(List<ItemAddPaperFo> itemAddPaperFos) throws Exception {
        List<PssTestBatch> batchList = new ArrayList<>();
        //获得目前最大试卷的数量
        Integer maxTestid = pssTestBatchMapper.getMaxTestid();
        //循环题目添加到考试表里面
        for(int i=0;i<itemAddPaperFos.size();i++){
            PssTestBatch pssTestBatch = new PssTestBatch();
            pssTestBatch.setTestid(maxTestid+1);
            pssTestBatch.setTestitemid(itemAddPaperFos.get(i).getPssTestItem().getId());
            batchList.add(pssTestBatch);
        }
        return pssTestBatchMapper.updatePssTestBatchTestItemIdToAotherTestItemId(batchList);
    }

    @Override
    public List<TestPaperVo> showTestPaper() throws Exception {
        List<TestPaperVo> testPaperVos = new ArrayList<>();
        //查询出所有考试试卷
        List<PssTestBatch> batchList = pssTestBatchMapper.listAllPssTestBatch();
        List<Integer> integers = pssTestBatchMapper.showTestItemPaperId();
        //遍历创建对象并插值封装对象
        for (int i = 0; i < integers.size(); i++) {
            TestPaperVo testPaperVo = new TestPaperVo();
            List<ItemVo> itemVoList = new ArrayList<>();
            for (int j = 0; j < batchList.size(); j++) {
                //如果试卷id和id相同,封装到一个对象中
                if(integers.get(i)==batchList.get(j).getTestid()){
                    //存入id
                    testPaperVo.setTestName("第"+integers.get(i)+"套试卷");
                    testPaperVo.setTestid(integers.get(i));
                    testPaperVo.setStatus(batchList.get(j).getStatus());
                    ItemVo itemVoById = pssTestItemServiceIpml.getItemVoById(batchList.get(j).getTestitemid());
                    itemVoList.add(itemVoById);
                }
            }
            testPaperVo.setItemVos(itemVoList);
            testPaperVos.add(testPaperVo);
        }
        return testPaperVos;
    }

    @Override
    public int updatePssTestBatch(Integer testId, String status) throws Exception {
        return pssTestBatchMapper.updatePssTestBatch(testId,status);
    }
}
