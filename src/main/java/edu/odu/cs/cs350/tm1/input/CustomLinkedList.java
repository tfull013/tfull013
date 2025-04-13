package edu.odu.cs.cs350.tm1.input;
/**
 * 
 */

import java.util.Collections;
import java.util.LinkedList;

/** This class extends the given LinkedList structure so we can overwrite and add methods to better suit our program
 * 
 * @author Nicholas Brewster
*/
public class CustomLinkedList extends LinkedList<String> {

    /**
     * Alphabetizes the linkedlist
    */
    public void alphabetize() {
        Collections.sort(this);
    }
}
