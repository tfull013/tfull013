package edu.odu.cs.cs350.tm1.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import edu.odu.cs.cs350.tm1.input.RefactoringCandidate;

/**
 * Token Scanner will tally the number of tokens found in the file
 * 
 * @author Joshua Hall, Silver Weidman
*/
public class TokenScanner {
    /**
     * The file the class will look at
     */
    private final File fileToScan;
    /**
     * The number of token in the file
     */
    private int tokenCount;

    /**
     * Default Constructor
     * 
     * @author Joshua Hall
     */
    public TokenScanner() {
        this.fileToScan = null;
        this.tokenCount = 0;
    }

    /**
     * Construct by passing in a File
     * 
     * @param file The file to be looked at
     * 
     * @author Joshua Hall
     */
    public TokenScanner(File file) {
        this.fileToScan = file;
        this.tokenCount = 0;
    }

    /**
     * Construct by passing in a File Path as a String
     * 
     * @param pathToFile The address to the file
     * 
     * @author Joshua Hall
     */
    public TokenScanner(String pathToFile) {
        File file = new File(pathToFile);
        this.fileToScan = file;
        this.tokenCount = 0;
    }

    /**
     * Construct by passing in a File Path as a String
     * 
     * @param candidate The file found by the refactoring candidate
     * 
     * @author Joshua Hall
     */
    public TokenScanner(RefactoringCandidate candidate) {
        this.fileToScan = null;
        this.tokenCount = countTokensInCandidate(candidate);
    }

    /**
     * Scans the file and returns the total number of tokens.
     * 
     * @return The total number of tokens in the file (-1 if invalid)
     * 
     * @author Joshua Hall
     */
    public int countTokensInFile(){
        tokenCount = 0;
        try (FileReader reader = new FileReader(fileToScan)) {
            Lexer lexer = new Lexer(reader);
            while (lexer.scan() != Lexer.EOF) {
                tokenCount++;
                if (tokenCount >= 1000) break;
            }
        } catch (IOException e) {
            System.err.println("Error scanning file: " + e.getMessage());
            return -1; // Indicate failure
        }
        return tokenCount;
    }

    /**
     * Scans the Refactoring Candidate and returns the total number of tokens.
     * 
     * @return The total number of tokens in the candidate (-1 if invalid)
     * 
     * @author Joshua Hall
     */
    public int countTokensInCandidate(RefactoringCandidate candidate){
        tokenCount = 0;
        try (FileReader reader = new FileReader(generateFile("runtimeFile.txt", candidate.toString()))) {
            Lexer lexer = new Lexer(reader);
            while (lexer.scan() != Lexer.EOF) {
                tokenCount++;
                if (tokenCount >= 1000) break;
            }
        } catch (IOException e) {
            System.err.println("Error scanning file: " + e.getMessage());
            return -1; // Indicate failure
        }
        return tokenCount;
    }

     /**
     * The Lexer can only scan files, thus:
     *  - Generates a file to contain the code block
     * 
     * @return The file generated
     * 
     * @author Joshua Hall
     */
    private File generateFile(String name, String containing){
        File fileGenerated = new File("build\\" + name);
        try {
            fileGenerated.createNewFile();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileGenerated));
            writer.write(containing);
            
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return fileGenerated;
    }

    /**
     * Returns the file being scanned.
     * 
     * @return The file being scanned.
     * 
     * @author Joshua Hall
     */
    public File getFile() {
        return fileToScan;
    }

    /**
     * Returns the token count after scanning.
     * 
     * @return The token count.
     * 
     * @author Joshua Hall
     */
    public int getTokenCount() {
        return tokenCount;
    }
}