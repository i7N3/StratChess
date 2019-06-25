import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.fail;

public class SquareTests {

    private boolean onBoard;
    private boolean notOnBoard;
    private Square square;

    @Before
    public void setUp() throws Exception {
        square = new Square(0, 0);
        Square square1 = new Square(0, 9);

        onBoard = square.onBoard();
        notOnBoard = square1.onBoard();
    }


//    @Test – определяет что метод method() является тестовым.
//    @Before – указывает на то, что метод будет выполнятся перед каждым тестируемым методом @Test.
//    @After – указывает на то что метод будет выполнятся после каждого тестируемого метода @Test
//    @BeforeClass – указывает на то, что метод будет выполнятся в начале всех тестов, а точней в момент запуска тестов(перед всеми тестами @Test).
//    @AfterClass – указывает на то, что метод будет выполнятся после всех тестов.
//    @Ignore – говорит, что метод будет проигнорирован в момент проведения тестирования.
//      (expected = Exception.class) – указывает на то, что в данном тестовом методе вы преднамеренно ожидаете Exception.
//      (timeout = 100) – указывает, что тестируемый метод не должен занимать больше чем 100 миллисекунд.


//    Основные методы класса Assert для проверки:
//
//    fail(String) – указывает на то что бы тестовый метод завалился при этом выводя текстовое сообщение.
//    assertTrue([message], boolean condition) – проверяет, что логическое условие истинно.
//    assertsEquals([String message], expected, actual) – проверяет, что два значения совпадают.
//    Примечание: для массивов проверяются ссылки, а не содержание массивов.
//
//    assertNull([message], object) – проверяет, что объект является пустым null.
//    assertNotNull([message], object) – проверяет, что объект не является пустым null.
//    assertSame([String], expected, actual) – проверяет, что обе переменные относятся к одному объекту.
//    assertNotSame([String], expected, actual) – проверяет, что обе переменные относятся к разным объектам.

    @Test
    public void square() {
        try {
            new Square(1, 1);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void square_ARGUMENT_LENGHT_EQUAL_2() {
        Square validSquare = new Square("e2");
        Square notValidSquare = new Square("eee");

        Assert.assertTrue(validSquare.getX() != -1 && validSquare.getY() != -1);
        Assert.assertTrue(notValidSquare.getX() == -1 && notValidSquare.getY() == -1);
    }

    @Test
    public void square_ARGUMENT_EQUAL_FIGURE_POSITION() {
        String a1 = "a1";

        Assert.assertEquals(new Square(a1).getX(), square.getX());
        Assert.assertEquals(new Square(a1).getY(), square.getY());
    }

    @Test
    public void yieldSquares_ARRAY_SIZE_EQUAL_64() {
        ArrayList<Square> yieldSquares = Square.yieldSquares();
        Assert.assertTrue(yieldSquares.size() == 64);
    }

    @Test
    public void getName() {
        Square square1 = new Square(1, 0);
        Square square2 = new Square(0, 7);
        Square square3 = new Square(7, 0);
        Square square4 = new Square(7, 7);

        String expected1 = square1.getName();
        String expected2 = square2.getName();
        String expected3 = square3.getName();
        String expected4 = square4.getName();

        String actual1 = "b1";
        String actual2 = "a8";
        String actual3 = "h1";
        String actual4 = "h8";

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
    }

    @Test
    public void getName_NOT_NULL() {
        Square expected = new Square(0, 0);
        Assert.assertNotNull(expected);
    }

    @Test
    public void onBoard() {
        Assert.assertTrue("onBoard must be TRUE", onBoard);
        Assert.assertFalse("onBoard must be FALSE", notOnBoard);
    }
}