
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class CalcTest {

    @Test
    void calc1() {
        String str="2+2*2";
        Calc calc =new Calc();
        assertEquals(6,calc.calc(str));
    }
    @Test
    void calc2() {
        String str="(2+2)*(2-1)";
        Calc calc =new Calc();
        assertEquals(4,calc.calc(str));
    }
    @Test
    void calc3() {
        String str="(2+2)%2";
        Calc calc =new Calc();
        assertEquals(0,calc.calc(str));
    }
    @Test
    void calc4() {
        String str="(4+6)/2";
        Calc calc =new Calc();
        assertEquals(5,calc.calc(str));
    }
    @Test
    void calc5() {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("33\n4\n".getBytes()));
        String str="a+b";
        Calc calc =new Calc(System.in);
        assertEquals(37,calc.calc(str));
        System.setIn(stdin);
    }
}