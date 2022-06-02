package com.league.backendchallenge.service;

import com.league.backendchallenge.exception.DataNotFoundException;
import com.league.backendchallenge.exception.EmptyFileException;
import com.league.backendchallenge.exception.InvalidFileFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface OutputService {

    ResponseEntity<String> echo(MultipartFile file) throws EmptyFileException, InvalidFileFormatException;
    ResponseEntity<String> invert() throws DataNotFoundException, FileNotFoundException;
    ResponseEntity<String> flatten() throws DataNotFoundException, FileNotFoundException;
    ResponseEntity<Integer>  sum() throws DataNotFoundException, FileNotFoundException;
    ResponseEntity<Integer> multiply() throws DataNotFoundException, FileNotFoundException;
}
