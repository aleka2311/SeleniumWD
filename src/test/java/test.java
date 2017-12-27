import org.testng.Assert;
import org.testng.annotations.Test;

public class test {
    @Test
    public void test(){
        int a=5;
        int b=5;
        Assert.assertEquals(a+b, 10);
    }
}
