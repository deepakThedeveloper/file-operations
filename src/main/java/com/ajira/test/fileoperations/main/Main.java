package com.ajira.test.fileoperations.main;

import com.ajira.test.fileoperations.service.CSVFileOperations;
import com.ajira.test.fileoperations.service.FileOperations;

import java.util.Optional;
import java.util.Scanner;

public class Main {

    /**
     * Get file paths from user and perform file operation on them accordingly.
     * Currently only {@link CSVFileOperations} is supported.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Optional<String[]> filePaths = getFilePaths();
        if(!filePaths.isPresent()){
            return;
        }
        FileOperations fileOperations = new CSVFileOperations();
        Optional<String> outputFilePath = fileOperations.performFileOperations(filePaths.get());
        if(outputFilePath.isPresent()) {
            System.out.println("Data Generated Successfully. View the file at:" + outputFilePath.get());
        }
        else{
            System.out.println("Sorry. Unable to generate output file.");
        }
    }

    private static Optional<String[]> getFilePaths() {
        System.out.println("Please enter file paths separated by space: <Input File Path> " +
                "<Configuration File Path> <Output File Path>");

        String filePaths = new Scanner(System.in).nextLine();
        if(filePaths == null || filePaths.length() == 0){
            System.out.println("Operation terminated. File paths is null or empty!");
        }
        System.out.println("FilePaths:"+filePaths);
        String[] individualFilePaths = filePaths.split(" ");
        if(individualFilePaths.length != 3){
            System.out.println("\nOperation terminated. 3 file paths are expected separated by space. \n" +
                    "Found total file paths:"+individualFilePaths.length);
            return Optional.empty();
        }

        return Optional.of(individualFilePaths);

    }
}
