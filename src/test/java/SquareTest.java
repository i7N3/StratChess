import org.junit.Assert;
import org.junit.Test;

public class SquareTest {

    @Test
    public void getName() {
        Square square1 = new Square(1, 0);
        Square square2 = new Square(0, 7);
        Square square3 = new Square(7, 0);
        Square square4 = new Square(7, 7);

        String expected1 = square1.GetName();
        String expected2 = square2.GetName();
        String expected3 = square3.GetName();
        String expected4 = square4.GetName();

        String actual1 = "b1";
        String actual2 = "a8";
        String actual3 = "h1";
        String actual4 = "h8";

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
    }
}