import com.woniu.ApplicationApp;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssTestItem;
import com.woniu.service.impl.PssTestBatchImpl;
import com.woniu.service.impl.PssTestItemServiceIpml;
import com.woniu.vo.TestPaperVo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = {ApplicationApp.class})
public class TestDame {
    @Resource
    private PssTestItemServiceIpml pssTestItemServiceIpml;
    @Resource
    private PssTestBatchImpl pssTestBatch;

    @Test
    public void showItem(){
        PageInfo<PssTestItem> pssTestItemByPageAndWrapper = pssTestItemServiceIpml.getPssTestItemByPageAndWrapper(1, 5, "", -1);
        List<PssTestItem> data = pssTestItemByPageAndWrapper.getData();
        data.forEach(d-> System.out.println(d.toString()));
    }

    @SneakyThrows
    @Test
    public void showTestPaper(){
        List<TestPaperVo> testPaperVos = pssTestBatch.showTestPaper();
        testPaperVos.forEach(a-> System.out.println(a.toString()));
    }
}
