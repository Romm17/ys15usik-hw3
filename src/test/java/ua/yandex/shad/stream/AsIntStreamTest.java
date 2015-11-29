package ua.yandex.shad.stream;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * @author Roman Usik
 */
public class AsIntStreamTest {

    private int[] array;
    private int[] emptyArray;

    private IntStream stream;
    private IntStream emptyStream;

    @Before
    public void init() {
        array = new int[] {-2, 2, -1, 1, 0};
        emptyArray = new int[] {};
        stream = AsIntStream.of(array);
        emptyStream = AsIntStream.of(emptyArray);
    }

    @Test
    public void testAverageOnEmtpyArray() {
        Double actual = emptyStream.average();
        assertNull(actual);
    }

    @Test
    public void testAverage() {
        Double expected = 0.0;
        Double actual = stream.average();
        assertEquals(expected, actual);
    }

    @Test
    public void testMinOnEmtpyArray() {
        Integer actual = emptyStream.min();
        assertNull(actual);
    }

    @Test
    public void testMin() {
        Integer expected = -2;
        Integer actual = stream.min();
        assertEquals(expected, actual);
    }

    @Test
    public void testMaxOnEmtpyArray() {
        Integer actual = emptyStream.max();
        assertNull(actual);
    }

    @Test
    public void testMax() {
        Integer expected = 2;
        Integer actual = stream.max();
        assertEquals(expected, actual);
    }

    @Test
    public void testCountOnEmptyArray() {
        long expected = 0;
        long actual = emptyStream.count();
        assertEquals(expected, actual);
    }

    @Test
    public void testCount() {
        long expected = 5;
        long actual = stream.count();
        assertEquals(expected, actual);
    }

    @Test
    public void testFilterOnEmptyArray() {
        int expected = 0;
        int actual = emptyStream
                .filter(x -> x < Integer.MAX_VALUE)
                .reduce(expected, (x, y) -> x + y);
        assertEquals(expected, actual);
    }

    @Test
    public void testFilterLessThanTwo() {
        int expected = -2;
        int actual = stream
                .filter(x -> x < 2)
                .reduce(0, (x, y) -> x + y);
        assertEquals(expected, actual);
    }

    @Test
    public void testFilterAbsMoreThanThree() {
        int expected = 0;
        int actual = stream
                .filter(x -> x * x > 9)
                .reduce(0, (x, y) -> x + y);
        assertEquals(expected, actual);
    }

    @Test
    public void testForEachOnEmptyArray() {
        String expected = "";
        StringBuilder actual = new StringBuilder();
        emptyStream.forEach(actual::append);
        assertEquals(expected, actual.toString());
    }

    @Test
    public void testForEach() {
        String expected = "-22-110";
        StringBuilder actual = new StringBuilder();
        stream.forEach(actual::append);
        assertEquals(expected, actual.toString());
    }

    @Test
    public void testMapOnEmptyArray() {
        int expected = 0;
        int actual = emptyStream
                .map(x -> -x)
                .reduce(0, (x, y) -> x + y);
        assertEquals(expected, actual);
    }

    @Test
    public void testMap() {
        int expected = 0;
        int actual = stream
                .map(x -> -x)
                .reduce(0, (x, y) -> x + y);
        assertEquals(expected, actual);
    }

    @Test
    public void testReduceOnEmptyArray() {
        int expected = 1000;
        int actual = emptyStream.reduce(expected, (x, y) -> x - y);
        assertEquals(expected, actual);
    }

    @Test
    public void testReduce() {
        int expected = 1000;
        int actual = stream.reduce(expected, (x, y) -> x - y);
        assertEquals(expected, actual);
    }

    @Test
    public void testSumOnEmptyArray() {
        Integer actual = emptyStream.sum();
        assertNull(actual);
    }

    @Test
    public void testSum() {
        Integer expected = 0;
        Integer actual = stream.sum();
        assertEquals(expected, actual);
    }

    @Test
    public void testToArrayOnEmptyArray() {
        assertArrayEquals(emptyArray, emptyStream.toArray());
    }

    @Test
    public void testToArray() {
        assertArrayEquals(array, stream.toArray());
    }

    @Test
    public void testFlatMap() {
        int expected = 5;
        int actual = stream
                .map(x -> x * 2)
                .flatMap(x -> AsIntStream.of(x, x + 1))
                .reduce(0, (x, y) -> x + y);
        assertEquals(expected, actual);
    }

}
