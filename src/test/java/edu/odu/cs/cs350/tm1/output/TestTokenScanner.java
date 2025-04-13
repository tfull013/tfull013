package edu.odu.cs.cs350.tm1.output;

import edu.odu.cs.cs350.tm1.input.RefactoringCandidate;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestTokenScanner {
    private File dummyFile = new File("project/src/test/data/Main.java");

    @Test
    public void TestConstructorDefault() {
        TokenScanner scanner = new TokenScanner();
        assertNotNull(scanner);
        assertNull(scanner.getFile());
        assertEquals(0, scanner.getTokenCount());
    }

    @Test
    public void TestConstructorWithAFile() {
        File fillerFile = new File("null");
        TokenScanner scanner = new TokenScanner(fillerFile);
        assertNotNull(scanner);
    }

    @Test
    public void TestConstructorWithAStringPath() {
        TokenScanner scanner = new TokenScanner("src/test/data/Main.java");
        assertNotNull(scanner);
    }

    @Test
    public void TestConstructorWithARefactoringCandidate() {
        RefactoringCandidate candidate = new RefactoringCandidate(dummyFile, 0, 0);
        TokenScanner scanner = new TokenScanner(candidate);
        assertNotNull(scanner);
    }

    @Test
    public void TestRefactoringCandidate() {
        RefactoringCandidate candidate = new RefactoringCandidate(dummyFile, 0, 0);
        candidate.setCodeToRefactor("x + y = result");
        TokenScanner scanner = new TokenScanner("");
        assertNotEquals(0, scanner.countTokensInCandidate(candidate));
    }

    @Test
    public void TestKnownTokenCountForRefactoringCandidate() {
        RefactoringCandidate candidate = new RefactoringCandidate(dummyFile, 0, 0);
        candidate.setCodeToRefactor("x + y = result");
        TokenScanner scanner = new TokenScanner("");
        assertEquals(5, scanner.countTokensInCandidate(candidate));
    }

    @Test
    public void TestEmptyRefactoringCandidate() {
        RefactoringCandidate candidate = new RefactoringCandidate(dummyFile, 0, 0);
        candidate.setCodeToRefactor("");
        TokenScanner scanner = new TokenScanner("");
        assertEquals(0, scanner.countTokensInCandidate(candidate));
    }

    @Test
    public void testInvalidFile() {
        File invalidFile = new File("this is an invalid file path");
        TokenScanner scanner = new TokenScanner(invalidFile);
        
        assertEquals(-1, scanner.countTokensInFile());
    }

    @Test
    public void testEmptyFile() {
        TokenScanner scanner = new TokenScanner("src/test/data/test_directory_a/test_directory_b/EmptyFile.java");
        assertEquals(0, scanner.countTokensInFile());
    }

    @Test
    public void testNonEmptyFile() {
        TokenScanner scanner = new TokenScanner("src/test/data/Main.java");
        assertNotEquals(0, scanner.countTokensInFile());
    }
    
    @Test
    public void testKnownTokenCount() {
        TokenScanner scanner = new TokenScanner("src/test/data/Main.java");
        assertEquals(32, scanner.countTokensInFile());
    }

    @Test
    public void testAbsolutePath() {
        File file = new File("src/test/data/Main.java");
        TokenScanner scanner = new TokenScanner(file.getAbsolutePath());
        assertEquals(32, scanner.countTokensInFile());
    }
}
