import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BruinArrayListTest {

    private BruinArrayList<String> list;

    @BeforeEach
    public void setUp() {
        list = new BruinArrayList<>();
    }

    @Test
    public void testNewListIsEmptyAndSize() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testAddAppendReturnsTrue() {
        assertTrue(list.add("A"));
        assertTrue(list.add("B"));

        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
    }

    @Test
    public void testAddGrowsCapacity() {
        for (int i = 0; i < 100; i++) {
            assertTrue(list.add(String.valueOf(i)));
        }

        assertEquals(100, list.size());

        for (int i = 0; i < 100; i++) {
            assertEquals(String.valueOf(i), list.get(i));
        }
    }

    @Test
    public void testAddAtIndexStartMiddleEnd() {
        list.add("B");
        list.add("D");

        list.add(0, "A");
        list.add(2, "C");
        list.add(list.size(), "E");

        assertEquals(Arrays.asList("A", "B", "C", "D", "E"), list);
    }

    @Test
    public void testAddInvalidIndexThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "A"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, "A"));

        list.add("A");

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "B"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(2, "B"));
    }

    @Test
    public void testClearEmptiesList() {
        list.add("A");
        list.add("B");
        list.add("C");

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals("[]", list.toString());
    }

    @Test
    public void testGetValidIndices() {
        list.add("A");
        list.add("B");
        list.add("C");

        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    public void testGetInvalidIndexThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));

        list.add("A");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test
    public void testIndexOfFoundAndNotFound() {
        list.add("A");
        list.add("B");
        list.add("C");

        assertEquals(1, list.indexOf("B"));
        assertEquals(-1, list.indexOf("Z"));
    }

    @Test
    public void testIndexOfFirstOccurrenceWithDuplicates() {
        list.add("A");
        list.add("B");
        list.add("A");
        list.add("A");

        assertEquals(0, list.indexOf("A"));
    }

    @Test
    public void testRemoveByIndexReturnsElementAndShifts() {
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        assertEquals("B", list.remove(1));
        assertEquals(Arrays.asList("A", "C", "D"), list);
        assertEquals(3, list.size());
    }

    @Test
    public void testRemoveByIndexInvalidThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));

        list.add("A");

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    public void testRemoveByObjectRemovesFirstOccurrenceReturnsTrue() {
        list.add("A");
        list.add("B");
        list.add("A");

        assertTrue(list.remove("A"));
        assertEquals(Arrays.asList("B", "A"), list);
    }

    @Test
    public void testRemoveByObjectNotPresentReturnsFalse() {
        list.add("A");
        list.add("B");

        assertFalse(list.remove("Z"));
        assertEquals(Arrays.asList("A", "B"), list);
    }

    @Test
    public void testSetReplacesAndReturnsOldValue() {
        list.add("A");
        list.add("B");

        assertEquals("B", list.set(1, "C"));
        assertEquals("C", list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    public void testSetInvalidIndexThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(0, "A"));

        list.add("A");

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "B"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(1, "B"));
    }

    @Test
    public void testIteratorOrder() {
        list.add("A");
        list.add("B");
        list.add("C");

        Iterator<String> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("C", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteratorEmptyList() {
        Iterator<String> iterator = list.iterator();

        assertFalse(iterator.hasNext());
        assertThrows(java.util.NoSuchElementException.class, iterator::next);
    }

    @Test
    public void testContainsAndContainsAllViaProvidedCode() {
        list.add("A");
        list.add("B");
        list.add("C");

        assertTrue(list.contains("B"));
        assertFalse(list.contains("Z"));
        assertTrue(list.containsAll(Arrays.asList("A", "C")));
        assertFalse(list.containsAll(Arrays.asList("A", "Z")));
        assertEquals(2, list.lastIndexOf("C"));
        assertEquals(-1, list.lastIndexOf("Z"));
    }

    @Test
    public void testToArrayAndToString() {
        list.add("A");
        list.add("B");
        list.add("C");

        assertArrayEquals(
                new Object[] {"A", "B", "C"},
                list.toArray());

        String[] values = list.toArray(new String[0]);
        assertArrayEquals(new String[] {"A", "B", "C"}, values);

        assertEquals("[A, B, C]", list.toString());
    }

    @Test
    public void testAddAllUsesProvidedImplementation() {
        List<String> values = Arrays.asList("A", "B", "C");

        assertTrue(list.addAll(values));
        assertEquals(Arrays.asList("A", "B", "C"), list);
    }
}