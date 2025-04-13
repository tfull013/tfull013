package edu.odu.cs.cs350.tm1.output;

import edu.odu.cs.cs350.tm1.input.CommandLineParameters;
import edu.odu.cs.cs350.tm1.input.RefactoringCandidate;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestOutputToTerminal {

    @Test
    public void testOutputToFiles(){
        try {
        File outputFile = new File("src/test/data/Files_Scanned.txt");
        PrintStream fileOut = new PrintStream(outputFile);
        System.setOut(fileOut);

        //Creating parameter to export to file
        String[] testInput = new String[1];
        testInput[0] = "java -jar build/libs/project.jar 15 maxParams=20 src/test/data/Main.java";
        CommandLineParameters cmdParams = new CommandLineParameters(testInput);

        OutputToTerminal output = new OutputToTerminal();
        output.outputListOfFiles(cmdParams);

        // Reading the file and checking to make sure the data make it to the file correctly
        String filePath = "src/test/data/Files_Scanned.txt";
        StringBuilder fullFile = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null)
                fullFile.append(line).append("\n");
            
            reader.close();

            String fileContent = fullFile.toString();
            assertTrue(fileContent.contains("Main.java"));

            assertTrue(fileContent.contains("Tokens in file"));
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRefactoringCandidateOutput() throws IOException {
        File dummyFile = new File("project/src/test/data/Main.java");
        RefactoringCandidate candidate = new RefactoringCandidate(dummyFile, 100, 4);
        candidate.setCodeToRefactor("System.out.println(\"This is example code!\")");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream outWrapper = new PrintStream(outputStream);
        System.setOut(outWrapper);

        OutputToTerminal outputManager = new OutputToTerminal();
        outputManager.outputRefactoringCandidate(candidate);

        outputStream.close();
        String output = outputStream.toString(StandardCharsets.UTF_8);
        output = output.replaceAll(System.lineSeparator(), "\n"); // standardize for windows jank

        String expectedOutput = "";
        expectedOutput += dummyFile.getAbsolutePath();
        expectedOutput += ':';
        expectedOutput += candidate.getStartLine();
        expectedOutput += ':';
        expectedOutput += candidate.getStartColumn();
        expectedOutput += '\n';
        expectedOutput += "System.out.println(\"This is example code!\")";
        expectedOutput += '\n';

        assertEquals(
                expectedOutput,
                output
        );
    }
}
