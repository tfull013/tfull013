package edu.odu.cs.cs350.tm1.input;
/**
 * 
 */

import java.io.File;

/**
 * The refactoring candidate the program is looking at to find duplicates of
 * 
 * @author Silver Weidman, Nicholas Brewster, 
 */
public class RefactoringCandidate {
    /**
     * The source file the candidate originates from
     */
    private final File sourceFile;
    /**
     * The line in the file the candidate was found
     */
    private final int startLine;
    /**
     * Where in the line the candidate was found
     */
    private final int startColumn;
    /**
     * The actual slice of code the program is looking for duplicates of
     */
    private String codeToRefactor = "";
    /**
     * The number of time the code slice appears in the file(s)
     */
    private int opportunities = 0;
    /**
     * How many tokens the slice of code contains
     */
    private int tokenValue = 0;
    /**
     * How many tokens the refactoring cadidate would save overall
     */
    private int opportunityScore = 0;

    /**
     * Constructor for the object
     * 
     * @param sourceFile
     * @param startLine
     * @param startColumn
     * 
     * @author Silver Weidman
     */
    public RefactoringCandidate(File sourceFile, int startLine, int startColumn) {
        this.sourceFile = sourceFile;
        this.startLine = startLine;
        this.startColumn = startColumn;
    }

    /**
     * Sets the code to look for refactoring opportunities
     * 
     * @param code
     * 
     * @author Joshua Hall
     */
    public void setCodeToRefactor(String code){
        this.codeToRefactor = code;
    }
    /**
     * Sets the number of tokens for the candidate
     * 
     * @param tokenCount
     * 
     * @author Joshua Hall
     */
    public void setTokens(int tokenCount){
        this.tokenValue = tokenCount;
    }
    /**
     * Sets the number of opportunities for refactoring
     * 
     * @param opportunity
     * 
     * @author Joshua Hall
     */
    public void setOpportunities(int opportunity){
        this.opportunities = opportunity;
    }

    /**
     * Returns the source file address
     * 
     * @return Source file address
     * 
     * @author Silver Weidman
     */
    public File getSourceFile() {
        return sourceFile;
    }
    /**
     * Returns the line in the file where the cadidate starts
     * 
     * @return The line number
     * 
     * @author Silver Weidman
     */
    public int getStartLine() {
        return startLine;
    }
    /**
     * Returns where in the line the candidate starts
     * 
     * @return The spot in the line
     * 
     * @author Silver Weidman
     */
    public int getStartColumn() {
        return startColumn;
    }
    /**
     * Returns the slice of code to search for refactoring opportunities of
     * 
     * @return A string of code
     * 
     * @author Joshua Hall
     */
    public String getCodeToRefactor(){
        return  this.codeToRefactor;
    }
    /**
     * Returns the number of tokens in the slice of code
     * 
     * @return The number of tokens
     * 
     * @author Joshua Hall
     */
    public int getTokens(){
        return this.tokenValue;
    }
    /**
     * Returns the number of time the code slice appears in the files scanned
     * 
     * @return an int of refactoring opportunities
     * 
     * @author Nicholas Brewster
     */
    public int getOpportunities(){
        return this.opportunities;
    }
    /**
     * Returns the opportunity score
     * 
     * @return an int representing how valuable the refactoring candidate is
     * 
     * @author Nicholas Brewster
     */
    public int getOpportunityScore(){
        return this.opportunityScore;
    }

    @Override
    public String toString() {
        return codeToRefactor;
    }

    /**
     * Calculating how valuable the refactoring candidate would be
     * 
     * @author Nicholas Brewster
     */
    public void calcOpportunityScore(){
        this.opportunityScore = this.tokenValue*(this.opportunities-1);
    }

     /**
     * Formats refactoring candidate
     * 
     * @author Trevor Prince
     */
    public String formattedOutput(){
        return "Source File: " + sourceFile.getName() +
        "\nStart Line: " + startLine + 
        "\nStart Column: " + startColumn + 
        "\nCode Slice: " + codeToRefactor + 
        "\nToken Value: " + tokenValue + 
        "\nRefactoring Opportunities: " + opportunities + 
        "\nOpportunity Score: " + opportunityScore + "\n";
    }


}
