package test;

import com.mycomp.home.common.Code;
import org.junit.Test;

public class myTest extends ApplicationTest {



    @Test
    public void myt() {
        Integer num = Code.SUCCESS.getNum();
        System.out.println(num);
    }
}
