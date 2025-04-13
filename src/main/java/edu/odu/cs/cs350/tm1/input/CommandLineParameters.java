package edu.odu.cs.cs350.tm1.input;
/**
 * 
 */

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Parses and holds the command line parameters
 * includes some functionality for # of refactorings
 * 
 * @author Silver Weidman, Cameron Montgomery, tprin006
 **/
public class CommandLineParameters {
    /**
     * The list of java files to parse through
     */
    private final Collection<File> inputFiles = new ArrayList<>();
    /**
     * The default number of suggestions
     */
    private int defaultSuggestions = 10;
    /**
     * The number of suggestions the program will make, constructor sets this equal to defaultSuggestions
     */
    private int nSuggestions = defaultSuggestions;
    /**
     * The maximum number of parameters for suggested rafactorings, default is equal to defaultSuggestions
     */
    private int maxParams = defaultSuggestions;

    /** Constructs a new set of command line parameters from the passed arguments 
     * 
     * @throws NumberFormatException if the command line is empty
     * 
     * @author Thomas Fuller
     */
    public CommandLineParameters(String[] commandLineArguments) {
        if (commandLineArguments.length > 0) {
            try {
                int parsedValue = Integer.parseInt(commandLineArguments[0]);
                nSuggestions = (parsedValue > 0) ? parsedValue : defaultSuggestions;
            } catch (NumberFormatException e) {
                nSuggestions = defaultSuggestions;
            }
        }

        for (String argument : commandLineArguments) {
            if (argument.startsWith("maxParams=")) {
                try {
                    int parsedInt = Integer.parseInt(argument.substring("maxParams=".length()));
                    maxParams = (parsedInt > 0) ? parsedInt : defaultSuggestions;
                } catch (NumberFormatException e) {
                    maxParams = defaultSuggestions;
                }
            } else if(!argument.equals("" + nSuggestions)){ // <- This avoids trying to scan the nSuggestions as a file, not the best but it works
                addValidFiles(Path.of(argument).toAbsolutePath().toFile());
            }
        }
    }
    
    /**
    * Recursively adds Java files to the inputFiles collection
    *
    * @param file The file or directory to process
    *
    * @author Thomas Fuller
    */
    private void addValidFiles(File file) {
        // If the file is a directory, process all its contents
        if (file.isDirectory()) {
            // Get all files and directories in this directory
            File[] contents = file.listFiles();
            // If directory is empty or can't be read, return
            if (contents == null)
                return;
            
            // Process each file/directory in the current directory
            for (File item : contents)
                addValidFiles(item); // Recursively process this item
        } 
        // Otherwise, check if it's a valid file and add it directly
        else if (isValidFile(file))
            inputFiles.add(file);
    }

    /**
     * Checks to see if the file being looked at is a java file
     * 
     * @param file
     * @return true if the file is a java file
     * @return false if the file is not a java file
     * 
     * @author Silver Weidman
     */
    private boolean isValidFile(File file) {
        if (file.toPath().toString().endsWith(".java")) {
            return true;
        } else {
            System.err.println("Invalid file: " + file.toPath());
            return false;
        }
    }

    /** Returns the files that are known to be valid inputs for the system 
     * 
     * @return List of java files
     * 
     * @author Silver Weidman
    */
    public Collection<File> getInputFiles() {
        return inputFiles;
    }

    /** Returns the maximum number of refactoring suggestions to display 
     * 
     * @return The number of suggestions
     * 
     * @author Cameron Montgomery
    */
    public int getNSuggestions() {
        return nSuggestions;
    }

    /** Returns the maximum number function parameters that would be considered in a candidate refactoring via a CLI property 
     * 
     * @return the number of max parameters
     * 
     * @author tprin006
    */
    public int getMaxParams() {
        return maxParams;
    }
}
