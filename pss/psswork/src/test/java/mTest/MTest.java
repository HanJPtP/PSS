package mTest;

import com.woniu.ApplicationApp;
import com.woniu.util.mutil.GenerateNoUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = {ApplicationApp.class})
public class MTest {

    @Test
    public void test1(){
        String orderTaskNo = GenerateNoUtil.getOrderTaskNo();
        System.out.println(orderTaskNo);
    }
}
