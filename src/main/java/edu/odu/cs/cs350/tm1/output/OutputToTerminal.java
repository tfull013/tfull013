package edu.odu.cs.cs350.tm1.output;
/**
 * 
 */

import java.io.File;

import edu.odu.cs.cs350.tm1.input.CommandLineParameters;
import edu.odu.cs.cs350.tm1.input.RefactoringCandidate;

/**
 * This class handles the output to the terminal
 * 
 * @author Silver Weidman, Nicholas Brewster, Joshua Hall
 */
public class OutputToTerminal {
    /**
     * This handles the output that contains all of the files scanned, and the number of tokens in each file
     * 
     * @param cmdParams the list of files scanned
     * 
     * @author Nicholas Brewster, Joshua Hall
     */
    public void outputListOfFiles(CommandLineParameters cmdParams){
        for (File file : cmdParams.getInputFiles()) {
            TokenScanner tokenScanner = new TokenScanner(file.getAbsolutePath());
            System.out.println("     " + file.getAbsolutePath() + ", Tokens in file: " + tokenScanner.countTokensInFile());
        }
    }

    /**
     * This handles the output the contains the refactoring opportunites
     * 
     * @param candidate Each opportunity the program found
     * 
     * @author Silver Weidman
     */
    public void outputRefactoringCandidate(RefactoringCandidate candidate) {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(candidate.getSourceFile().getAbsolutePath());
        outputBuilder.append(':');
        outputBuilder.append(candidate.getStartLine());
        outputBuilder.append(':');
        outputBuilder.append(candidate.getStartColumn());
        outputBuilder.append('\n');
        outputBuilder.append(candidate.getCodeToRefactor());
        System.out.println(outputBuilder);
    }
}
