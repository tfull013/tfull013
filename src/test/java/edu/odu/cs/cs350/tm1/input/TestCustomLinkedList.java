package edu.odu.cs.cs350.tm1.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCustomLinkedList {
    
    @Test
    public void testCustomLL() {
        // Making sure the list is created
        CustomLinkedList LLTest = new CustomLinkedList();
        assertNotNull(LLTest);

        // Making sure the element is added
        LLTest.add("element");
        assertTrue(LLTest.contains("element"));

        // Testing to see if the list is automatically alphabetized after each element gets added
        LLTest.add("alphabeticalOrderTest");
        LLTest.alphabetize();
        assertTrue(LLTest.contains("alphabeticalOrderTest"));
        assertTrue(LLTest.getFirst().equals("alphabeticalOrderTest"));

        assertTrue(LLTest.size() == 2);
    }
}
