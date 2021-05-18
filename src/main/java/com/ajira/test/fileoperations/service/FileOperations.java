package com.ajira.test.fileoperations.service;

import java.util.Optional;

public interface FileOperations {
    Optional<String> performFileOperations(String[] filePaths) throws Exception;
}
