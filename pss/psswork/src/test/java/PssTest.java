import com.woniu.ApplicationApp;
import com.woniu.pojo.*;
import com.woniu.service.impl.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = ApplicationApp.class)
public class PssTest {

    @Resource
    private Pss_whdataService pssWhdataService;

    @Resource
    private PssWarehourseService pssWarehourseService;
    @Resource
    private PssinoutService pssinoutService;

    @Resource
    private PssCheckService pssCheckService;


    @Resource
    private PsswhtoolnumService pssToolsNumsService;

    @Test
    public void fun(){
        System.out.println("hhhh");
        PageInfo<PssWhdata> pageInfo = pssWhdataService.ListWhdata(1, 5, "安全");
//        int size = pageInfo.getData().size();
//        System.out.println(size);
        System.out.println(pageInfo.getData().toString());
    }

    /**
     * 添加四库
     */
    @Test
    public void fun2(){
        System.out.println("hhhh");

        PssWhdata pssWhdata = new PssWhdata(1, "123", "ad", 2,
                1, "2", 1, "oiu", "098");
        int i = pssWhdataService.addWhdata(pssWhdata);

        System.out.println(i);
    }


    /**
     * 显示四库类型
     */
    @Test
    public void fun3(){


        List<PssWarehouse> pssWarehouses = pssWarehourseService.listWhtype();

        System.out.println(pssWarehouses.toString());
    }


    /**
     * 显示四库类型
     */
    @Test
    public void fun4(){

//        Date date1 = new Date(2022-05-12);
//        Date date2= new Date("2022-05-12");
        PageInfo<PssWhInout> pageInfo = pssinoutService.ListWhinout(1, 2,null,null);
        String s = pageInfo.getData().toString();
        System.out.println(s);
    }


    /**
     * 显示四库类型
     */
    @Test
    public void fun5(){

//        Date date1 = new Date(2022-05-12);
//        Date date2= new Date("2022-05-12");
        PageInfo<PssWhCheck> pageInfo = pssCheckService.listCheck(1, 2,null);
        String s = pageInfo.getData().toString();
        System.out.println(s);
    }



    /**
     * 显示四库类型
     */
    @Test
    public void fun6(){

//        Date date1 = new Date(2022-05-12);
//        Date date2= new Date("2022-05-12");
        List<PssToolsNums> pssToolsNums = pssToolsNumsService.toolnumInfor("OP001");
        String s = pssToolsNums.toString();
        System.out.println(s);
    }
}
