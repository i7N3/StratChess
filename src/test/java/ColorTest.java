import org.junit.Assert;
import org.junit.Test;

public class ColorTest {

    @Test
    public void flipColor_WHITE_TO_BLACK() {
        Color color = Color.white;
        color = color.flipColor(color);

        Assert.assertTrue(color == Color.black);
    }

    @Test
    public void flipColor_BLACK_TO_WHITE() {
        Color color = Color.black;
        color = color.flipColor(color);

        Assert.assertTrue(color == Color.white);
    }

    @Test
    public void flipColor_NONE() {
        Color color = Color.none;
        color = color.flipColor(color);

        Assert.assertTrue(color == Color.none);
    }
}
