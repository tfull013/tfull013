package edu.odu.cs.cs350.tm1;
/**
 * 
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import edu.odu.cs.cs350.tm1.input.CommandLineParameters;
import edu.odu.cs.cs350.tm1.input.RefactoringCandidate;
import edu.odu.cs.cs350.tm1.output.OutputToTerminal;
import edu.odu.cs.cs350.tm1.output.TokenScanner;

/**
 * A program that can scan java file and suggest blocks of reused code that can be refactored into separate functions.
 * 
 * 
 * @author TM1
 */
public class Main {
    
    /**
     * The main function that goes through all of the steps in order.
     * 
     * @param args
     * 
     * @author Joshua Hall
     */
    public static void main(String[] args) {
        System.out.println("\n"); //<- Not having a space was driving me insane

        // Parse command line arguments
        CommandLineParameters cmdParams = new CommandLineParameters(args);
        
        // Retrieve parsed values
        int nSuggestions = cmdParams.getNSuggestions();
        int maxParams = cmdParams.getMaxParams();
        
        //Section 1 Output:
        System.out.println("\nFiles Scanned");       
        
        // Retrieve and print valid input files
        OutputToTerminal outputManager = new OutputToTerminal();
        outputManager.outputListOfFiles(cmdParams);

        //Section 2 Output:
        System.out.println("\nRefactoring Suggestions:\n");
        
        // Generate nSuggestions Refactoring Candidates (mocked)
        final Collection<RefactoringCandidate> candidates = new ArrayList<>();

        for (int i = 0; i < nSuggestions; i++) {
            //This can be replaced when scanner is functional
            File mockInputFile = new File("project/src/test/data/Main.java");
            String mockCode = "for (int i = 0; i < 10; i++) {\n    System.out.println(i);\n} // Suggestion #" + (i+1);
            RefactoringCandidate candidate = new RefactoringCandidate(mockInputFile, 0, 0);
            candidate.setCodeToRefactor(mockCode);

            TokenScanner scanner = new TokenScanner(candidate);
            int tokenCount = scanner.countTokensInCandidate(candidate);

            candidate.setTokens(tokenCount);
            candidate.setOpportunities(10); // faked opportunity value

            candidates.add(candidate);
        }
        
        //Output list of suggested refactorings
        for (RefactoringCandidate candidate : candidates) {
            System.out.println(candidate.formattedOutput());
        }

        // Output candidate information
        for (RefactoringCandidate candidate : candidates) {
            outputManager.outputRefactoringCandidate(candidate);
        }

        // Display number of refactoring candidates that were printed
        System.out.println("\nTotal Refactoring Candidates: " + candidates.size());

        //Input Verification
        System.out.println("\nVerifying Input was Parsed Correctly:");

        // Output the parsed parameters
        System.out.println("Number of Suggestions: " + nSuggestions);
        System.out.println("Max Parameters: " + maxParams);
    }
}
