import com.woniu.ApplicationApp;
import com.woniu.pojo.PssCheckwork;
import com.woniu.service.impl.PssCheckworkServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = {ApplicationApp.class})
public class CheckworkTest {
    @Resource
    private PssCheckworkServiceImpl pssCheckworkService;
    @Test
    public void showList(){
        List<PssCheckwork> list = pssCheckworkService.getCheckworkByUserid(1);
        if(list.size()>0){
            System.out.println(list.toString());
        }else {
            System.out.println("没有数据");
        }
    }
}
