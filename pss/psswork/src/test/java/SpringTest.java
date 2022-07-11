import com.woniu.ApplicationApp;
import com.woniu.dao.mysql.PssPermsMapper;
import com.woniu.pojo.PssPerms;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = ApplicationApp.class)
public class SpringTest {

    @Resource
    private PssPermsMapper pssPermsMapper;

    @Test
    public void permsList(){
        List<PssPerms> pssPermsList = pssPermsMapper.PermsMenu("admin");
        System.out.println(pssPermsList);
    }


}
