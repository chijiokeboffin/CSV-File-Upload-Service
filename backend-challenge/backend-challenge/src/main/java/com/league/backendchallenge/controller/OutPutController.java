package com.league.backendchallenge.controller;


import com.league.backendchallenge.exception.DataNotFoundException;
import com.league.backendchallenge.exception.EmptyFileException;
import com.league.backendchallenge.exception.InvalidFileFormatException;
import com.league.backendchallenge.service.OutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class OutPutController {

    private  final OutputService outputService;


    public OutPutController(OutputService outputService) {
        this.outputService = outputService;
    }

    @PostMapping("/echo")
    public ResponseEntity<String> Echo(@RequestParam("file") MultipartFile file)
            throws InvalidFileFormatException, EmptyFileException {

       return this.outputService.echo(file);
    }

    @GetMapping(path = "/invert")
    public ResponseEntity<String> Invert() throws DataNotFoundException, FileNotFoundException {
        return this.outputService.invert();
    }

    @GetMapping(path = "/flatten")
    public ResponseEntity<String> Flatten() throws DataNotFoundException, FileNotFoundException {
       return this.outputService.flatten();
    }

    @GetMapping(path = "/sum")
    public  ResponseEntity<Integer> Sum() throws DataNotFoundException, FileNotFoundException {
        return this.outputService.sum();
    }

    @GetMapping(path = "/multiply")
    public ResponseEntity<Integer> Multiply() throws DataNotFoundException, FileNotFoundException {
      return  this.outputService.multiply();
    }
}
