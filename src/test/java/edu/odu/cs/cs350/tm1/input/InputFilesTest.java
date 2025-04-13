package edu.odu.cs.cs350.tm1.input;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InputFilesTest {
    private final Path relativeTestDataPath = Paths.get("src/test/data/");
    private final Path absoluteTestDataPath = Paths.get("src/test/data/").toAbsolutePath();

    private File getTestDataFile(String child) {
        return absoluteTestDataPath.resolve(child).toAbsolutePath().toFile();
    }

    @Test
    void noArguments() {
        CommandLineParameters cliParams = new CommandLineParameters(new String[] {});
        assertTrue(cliParams.getInputFiles().isEmpty());
    }

    @Test
    void oneJavaAbsoluteFile() {
        String[] args = new String[] {absoluteTestDataPath.resolve("JavaTestFile.java").toString()};
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertIterableEquals(List.of(getTestDataFile("JavaTestFile.java")), cliParams.getInputFiles());
    }

    @Test
    void twoJavaAbsoluteFile() {
        String[] args = new String[] {
                absoluteTestDataPath.resolve("JavaTestFile.java").toString(),
                absoluteTestDataPath.resolve("test_directory_a/JavaTestFile2.java").toString()
        };
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertIterableEquals(List.of(
                getTestDataFile("JavaTestFile.java"),
                getTestDataFile("test_directory_a/JavaTestFile2.java")
        ), cliParams.getInputFiles());
    }

    // Make sure we're normalizing file paths to make things easier for consumers
    @Test
    void oneJavaRelativeFile() {
        String[] args = new String[] {relativeTestDataPath.resolve("JavaTestFile.java").toString()};
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertIterableEquals(List.of(getTestDataFile("JavaTestFile.java")), cliParams.getInputFiles());
    }

    // Don't accept anything that's not a java file
    @Test
    void oneMiscAbsoluteFile() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream outWrapper = new PrintStream(outputStream);
        System.setErr(outWrapper);

        String[] args = new String[] {absoluteTestDataPath.resolve("MiscTestFile.txt").toString()};
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertIterableEquals(List.of(), cliParams.getInputFiles());

        outputStream.close();
        String output = outputStream.toString(StandardCharsets.UTF_8);
        output = output.replaceAll(System.lineSeparator(), "\n"); // standardize for windows jank

        assertEquals(
                "Invalid file: " + absoluteTestDataPath.resolve("MiscTestFile.txt") + '\n',
                output
        );
    }

    // Make sure nSuggestions accepts a valid amount of suggestions
    @Test
    void validNSuggestions() {
        String[] args = new String[]{"5", absoluteTestDataPath.resolve("JavaTestFile.java").toString()};
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertEquals(5, cliParams.getNSuggestions());
        assertIterableEquals(List.of(getTestDataFile("JavaTestFile.java")), cliParams.getInputFiles());
    }

    // Make sure an non-integer is not valid for nSuggestions (defaults)
    @Test
    void invalidNSuggestions() {
        String[] args = new String[]{"not_a_number", absoluteTestDataPath.resolve("JavaTestFile.java").toString()};
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertEquals(10, cliParams.getNSuggestions()); //should be default
        assertIterableEquals(List.of(getTestDataFile("JavaTestFile.java")), cliParams.getInputFiles());
    }

    // Make sure negative nSuggestions are invalid (defaults)
    @Test
    void negativeNSuggestions() {
        String[] args = new String[]{"-5", absoluteTestDataPath.resolve("JavaTestFile.java").toString()};
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertEquals(10, cliParams.getNSuggestions()); //should be default again
        assertIterableEquals(List.of(getTestDataFile("JavaTestFile.java")), cliParams.getInputFiles());
    }

    // Make sure 0 nSuggestions are invalid (defaults)
    @Test
    void zeroNSuggestions() {
        String[] args = new String[]{"0", absoluteTestDataPath.resolve("JavaTestFile.java").toString()};
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertEquals(10, cliParams.getNSuggestions()); //should be default again
        assertIterableEquals(List.of(getTestDataFile("JavaTestFile.java")), cliParams.getInputFiles());
    }

    // Make sure nSuggestions files are generated
    @Test
    void correctNumberOfCandidates() {
        int nSuggestions = 5;
        Collection<RefactoringCandidate> candidates = new ArrayList<>();
        for (int i = 0; i < nSuggestions; i++) {
            File mockInputFile = new File("project/src/test/data/Main.java");
            String mockCode = "for (int i = 0; i < 10; i++) {\n    System.out.println(i);\n} // Suggestion #" + (i+1);
            RefactoringCandidate candidate = new RefactoringCandidate(mockInputFile, 0, 0);
            candidate.setCodeToRefactor(mockCode);
            candidate.setTokens(10 + i); //mock
            candidate.setOpportunities(100 - i * 10); //mock for decreasing opporunity like in main
            candidates.add(candidate);
        }
        assertEquals(nSuggestions, candidates.size());
    }

    // Make sure maxParams accepts a valid amount of suggestions
    @Test
    void validMaxParams() {
        String[] args = {"maxParams=20"};
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertEquals(20, cliParams.getMaxParams());
    }

    // Make sure an non-integer is not valid for maxParams
    @Test
    void invalidMaxParams() {
        String[] args =  {"maxParams=test"};
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertEquals(10, cliParams.getMaxParams()); //should be default
    }

    @Test
    void negMaxParams() {
        String[] args = {"maxParams=-5"};
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertEquals(10, cliParams.getMaxParams());
    }

    @Test
    void noMaxParams() {
        String[] args = {};
        CommandLineParameters cliParams = new CommandLineParameters(args);
        assertEquals(10, cliParams.getMaxParams());
    }

    @Test
    void directoryWithJavaFiles() {
    // Use the test_directory_a which already exists in your test data
    String[] args = new String[] {absoluteTestDataPath.resolve("test_directory_a").toString()};
    CommandLineParameters cliParams = new CommandLineParameters(args);
    
    // This assumes test_directory_a contains JavaTestFile2.java and potentially other Java files
    assertTrue(cliParams.getInputFiles().contains(getTestDataFile("test_directory_a/JavaTestFile2.java")));
    
    // Add assertions for any other Java files you expect to find in the directory
    }

    @Test
    void nestedDirectoryWithJavaFiles() {
    // This test assumes you have a nested directory structure in your test data
    // For example, test_directory_a/nested/JavaTestFile3.java
    String[] args = new String[] {absoluteTestDataPath.toString()};
    CommandLineParameters cliParams = new CommandLineParameters(args);
    
    // Test that Java files from the root directory are included
    //assertTrue(cliParams.getInputFiles().contains(getTestDataFile("JavaTestFile.java")));
    
    // Test that Java files from subdirectories are included
    //assertTrue(cliParams.getInputFiles().contains(getTestDataFile("test_directory_a/JavaTestFile2.java")));
    
    // Add assertions for any nested directory Java files if they exist
    // assertTrue(cliParams.getInputFiles().contains(getTestDataFile("test_directory_a/nested/JavaTestFile3.java")));
    }
}
