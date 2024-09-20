package com.fintech.parser.hw3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomLinkedListTest {
    private CustomLinkedList<String> listString;
    private CustomLinkedList<Integer> listInteger;
    private CustomLinkedList<String> emptyListString;
    private CustomLinkedList<Integer> emptyListInteger;
    private CustomLinkedList<String> singleElementListString;
    private CustomLinkedList<Integer> singleElementListInteger;

    @BeforeEach
    void setUp() {
        listString = new CustomLinkedList<>();
        listString.addEnd("Word1");
        listString.addEnd("Word2");
        listInteger = new CustomLinkedList<>();
        listInteger.addAll(List.of(1,2,3));
        emptyListInteger = new CustomLinkedList<>();
        emptyListString = new CustomLinkedList<>();
        singleElementListInteger = new CustomLinkedList<>();
        singleElementListInteger.addEnd(1);
        singleElementListString = new CustomLinkedList<>();
        singleElementListString.addEnd("Word1");
    }

    @AfterEach
    void tearDown() {
        listString = null;
        listInteger = null;
        emptyListInteger = null;
        emptyListString = null;
        singleElementListInteger = null;
        singleElementListString = null;
    }

    @Test
    @DisplayName("Test get size")
    void getSize() {
        assertEquals(2, listString.getSize());
        assertEquals(3, listInteger.getSize());
    }

    @Test
    @DisplayName("Test get start element")
    void getStart() {
        assertEquals("Word1", listString.getStart());
        assertEquals("Word1", singleElementListString.getStart());
        assertThrows(RuntimeException.class, () -> emptyListString.getStart());

        assertEquals(1, listInteger.getStart());
        assertEquals(1, singleElementListInteger.getStart());
        assertThrows(RuntimeException.class, () -> emptyListInteger.getStart());
    }

    @Test
    @DisplayName("Test get end element")
    void getEnd() {
        assertEquals("Word2", listString.getEnd());
        assertEquals("Word1", singleElementListString.getEnd());
        assertThrows(RuntimeException.class, () -> emptyListString.getEnd());

        assertEquals(3, listInteger.getEnd());
        assertEquals(1, singleElementListInteger.getEnd());
        assertThrows(RuntimeException.class, () -> emptyListInteger.getEnd());
    }

    @Test
    @DisplayName("Test add end element")
    void addEnd() {
        listString.addEnd("Word3");
        assertEquals(3, listString.getSize());
        assertEquals("Word3", listString.getEnd());
        singleElementListString.addEnd("Word2");
        assertEquals(2, singleElementListString.getSize());
        assertEquals("Word2", singleElementListString.getEnd());
        emptyListString.addEnd("Word1");
        assertEquals(1, emptyListString.getSize());
        assertEquals("Word1", emptyListString.getEnd());

        listInteger.addEnd(4);
        assertEquals(4, listInteger.getSize());
        assertEquals(4, listInteger.getEnd());
        singleElementListInteger.addEnd(2);
        assertEquals(2, singleElementListInteger.getSize());
        assertEquals(2, singleElementListInteger.getEnd());
        emptyListInteger.addEnd(1);
        assertEquals(1, emptyListInteger.getSize());
        assertEquals(1, emptyListInteger.getEnd());
    }

    @Test
    @DisplayName("Test add start element")
    void addStart() {
        listString.addStart("Word0");
        assertEquals(3, listString.getSize());
        assertEquals("Word0", listString.getStart());
        singleElementListString.addStart("Word0");
        assertEquals(2, singleElementListString.getSize());
        assertEquals("Word0", singleElementListString.getStart());
        emptyListString.addStart("Word0");
        assertEquals(1, emptyListString.getSize());
        assertEquals("Word0", emptyListString.getStart());

        listInteger.addStart(0);
        assertEquals(4, listInteger.getSize());
        assertEquals(0, listInteger.getStart());
        singleElementListInteger.addStart(0);
        assertEquals(2, singleElementListInteger.getSize());
        assertEquals(0, singleElementListInteger.getStart());
        emptyListInteger.addStart(0);
        assertEquals(1, emptyListInteger.getSize());
        assertEquals(0, emptyListInteger.getStart());
    }

    @Test
    @DisplayName("Test is empty")
    void isEmpty() {
        assertTrue(emptyListInteger.isEmpty());
        assertTrue(emptyListString.isEmpty());

        assertFalse(listInteger.isEmpty());
        assertFalse(listString.isEmpty());

        assertFalse(singleElementListString.isEmpty());
        assertFalse(singleElementListInteger.isEmpty());
    }

    @Test
    @DisplayName("Test remove start element")
    void removeStart() {
        listString.removeStart();
        assertEquals(1, listString.getSize());
        assertEquals("Word2", listString.getStart());
        singleElementListString.removeStart();
        assertEquals(0, singleElementListString.getSize());
        emptyListString.removeStart();
        assertEquals(0, emptyListString.getSize());

        listInteger.removeStart();
        assertEquals(2, listInteger.getSize());
        assertEquals(2, listInteger.getStart());
        singleElementListInteger.removeStart();
        assertEquals(0, singleElementListInteger.getSize());
        emptyListInteger.removeStart();
        assertEquals(0, emptyListInteger.getSize());
    }

    @Test
    @DisplayName("Test remove end element")
    void removeEnd() {
        listString.removeEnd();
        assertEquals(1, listString.getSize());
        assertEquals("Word1", listString.getEnd());
        singleElementListString.removeEnd();
        assertEquals(0, singleElementListString.getSize());
        emptyListString.removeEnd();
        assertEquals(0, emptyListString.getSize());

        listInteger.removeEnd();
        assertEquals(2, listInteger.getSize());
        assertEquals(2, listInteger.getEnd());
        singleElementListInteger.removeEnd();
        assertEquals(0, singleElementListInteger.getSize());
        emptyListInteger.removeEnd();
        assertEquals(0, emptyListInteger.getSize());
    }

    @Test
    @DisplayName("Test contains element")
    void contains() {
        assertTrue(listString.contains("Word1"));
        assertTrue(listString.contains("Word2"));
        assertFalse(listString.contains("Word100"));
        assertFalse(listString.contains("Word3"));

        assertTrue(listInteger.contains(1));
        assertTrue(listInteger.contains(2));
        assertTrue(listInteger.contains(3));
        assertFalse(listInteger.contains(100));
        assertFalse(listInteger.contains(4));
    }

    @Test
    @DisplayName("Test add all element fromm Collection")
    void addAll() {
        List<String> newListString = List.of("Word3", "Word4", "Word5");
        listString.addAll(newListString);
        assertEquals(5, listString.getSize());
        assertTrue(listString.contains("Word3"));
        assertTrue(listString.contains("Word4"));
        assertEquals("Word5", listString.getEnd());

        List<Integer> newListInteger = List.of(4, 5);
        listInteger.addAll(newListInteger);
        assertEquals(5, listInteger.getSize());
        assertTrue(listInteger.contains(4));
        assertEquals(5, listInteger.getEnd());
    }

    @Test
    @DisplayName("Test add all element fromm CustomLinkedList")
    void testAddAll() {
        CustomLinkedList<String> newLinkedListString = new CustomLinkedList<>();
        newLinkedListString.addEnd("Word3");
        newLinkedListString.addEnd("Word4");
        listString.addAll(newLinkedListString);
        assertEquals(4, listString.getSize());
        assertTrue(listString.contains("Word3"));
        assertEquals("Word4", listString.getEnd());

        CustomLinkedList<Integer> newLinkedListInteger = new CustomLinkedList<>();
        newLinkedListInteger.addEnd(4);
        newLinkedListInteger.addEnd(5);
        listInteger.addAll(newLinkedListInteger);
        assertEquals(5, listInteger.getSize());
        assertTrue(listInteger.contains(4));
        assertEquals(5, listInteger.getEnd());
    }

    @Test
    @DisplayName("Test list to string")
    void toStringTest() {
        assertEquals("CustomLinkedList: 1 -> 2 -> 3", listInteger.toString());
        assertEquals("CustomLinkedList: Word1 -> Word2", listString.toString());
        assertEquals("CustomLinkedList: ", emptyListString.toString());
    }
}