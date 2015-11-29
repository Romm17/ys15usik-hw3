package ua.yandex.shad.collections;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * @author Roman Usik
 */
public class MyArrayListTest {

    MyArrayList list;
    MyArrayList emptyList;

    @Before
    public void init() {
        emptyList = new MyArrayList();
        list = new MyArrayList();
        for (int i = 0; i < 9; i++) {
            list.add(i);
        }
    }

    @Test
    public void testIsEmptyOnEmptyArray() {
        assertTrue(emptyList.isEmpty());
    }

    @Test
    public void testIsEmptyOnNotEmptyArray() {
        assertFalse(list.isEmpty());
    }

    @Test
    public void testToArrayOnEmptyArray() {
        int[] expected = {};
        assertArrayEquals(emptyList.toArray(), expected);
    }

    @Test
    public void testToArray() {
        int[] expected = {1, 2, 3};
        MyArrayList list = new MyArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        int[] actual = list.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAdd() {
        int expected = list.size() + 1;
        list.add(10);
        assertEquals(expected, list.size());
    }

    @Test
    public void testAddWithCapacityEnlarging() {
        int expected = list.size() + 2;
        list.add(10);
        list.add(11);
        assertEquals(expected, list.size());
    }

    @Test
    public void testIterator() {
        int[] expected = list.toArray();
        int[] actual = new int[list.size()];
        Iterator it = list.iterator();
        int index = 0;
        while (it.hasNext()) {
            actual[index++] = (Integer)it.next();
        }
        assertArrayEquals(expected, actual);
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorWithNotExistingElement() {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            it.next();
        }
        assertNull(it.next());
    }

}
