package edu.odu.cs.cs350.tm1.input;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestRefactoringCandidate {
    private File dummyFile = new File("project/src/test/data/Main.java");

    @Test
    public void TestConstructor() {
        RefactoringCandidate candidate = new RefactoringCandidate(dummyFile, 0, 0);
        assertNotNull(candidate);
    }

    @Test
    public void TestSetCode() {
        RefactoringCandidate candidate = new RefactoringCandidate(dummyFile, 0, 0);
        candidate.setCodeToRefactor("code");
        assertEquals("code", candidate.getCodeToRefactor());
    }

    @Test
    public void TestSetTokens() {
        RefactoringCandidate candidate = new RefactoringCandidate(dummyFile, 0, 0);
        candidate.setTokens(10);
        assertEquals(10, candidate.getTokens());
    }

    @Test
    public void TestSetOpportunity() {
        RefactoringCandidate candidate = new RefactoringCandidate(dummyFile, 0, 0);
        candidate.setOpportunities(5);
        assertEquals(5, candidate.getOpportunities());
    }

    @Test
    public void TestCalcOpportunityScore() {
        RefactoringCandidate candidate = new RefactoringCandidate(dummyFile, 0, 0);
        candidate.setTokens(10);
        candidate.setOpportunities(5);
        candidate.calcOpportunityScore();
        assertEquals(40, candidate.getOpportunityScore());
    }

    @Test
    public void TestFormattedOutput() {
        RefactoringCandidate candidate = new RefactoringCandidate(dummyFile, 0, 0);
        candidate.setCodeToRefactor("for (int i = 0, i < 1, i++)");
        candidate.setTokens(10);
        candidate.setOpportunities(5);
        candidate.calcOpportunityScore();
        String expectedOutput = "Source File: Main.java\n" +
        "Start Line: 0\n" +
        "Start Column: 0\n" +
        "Code Slice: for (int i = 0, i < 1, i++)\n" +
        "Token Value: 10\n" +
        "Refactoring Opportunities: 5\n" +
        "Opportunity Score: 40\n";

        assertEquals(expectedOutput, candidate.formattedOutput());
    }

}

