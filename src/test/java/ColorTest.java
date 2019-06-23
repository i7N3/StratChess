import org.junit.Assert;
import org.junit.Test;

public class ColorTest {

    @Test
    public void FlipColor_WHITE_TO_BLACK() {
        Color color = Color.white;
        color = color.FlipColor(color);

        Assert.assertTrue(color == Color.black);
    }

    @Test
    public void FlipColor_BLACK_TO_WHITE() {
        Color color = Color.black;
        color = color.FlipColor(color);

        Assert.assertTrue(color == Color.white);
    }

    @Test
    public void FlipColor_NONE() {
        Color color = Color.none;
        color = color.FlipColor(color);

        Assert.assertTrue(color == Color.none);
    }
}
