package fmy.example.live_push;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        String hello = new StringBuffer().append("ja").append("va").toString();
//        String test ="计算机";
        String hello2 = new StringBuffer().append("计算机").append("软件").toString();
        System.out.println(hello.intern() == "java");
        System.out.println(hello2.intern() == "计算机软件");
//        System.out.println("a".hashCode());
//        System.out.println("a".hashCode());
        System.out.println("-------------------------------------");

    }
}