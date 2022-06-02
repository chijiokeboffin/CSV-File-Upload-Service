package com.league.backendchallenge.service;


import com.league.backendchallenge.exception.DataNotFoundException;
import com.league.backendchallenge.exception.EmptyFileException;
import com.league.backendchallenge.exception.InvalidFileFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OutputServiceImpl implements OutputService {

    private static final Pattern VALIDEXTION = Pattern.compile("([^\\s]+(\\.(?i)(csv))$)");

    @Override
    public ResponseEntity<String>  echo(MultipartFile file) throws EmptyFileException,
            InvalidFileFormatException {
        if(file == null){
            throw new EmptyFileException("File must cannot be empty");
        }
        if(file.isEmpty()){
            throw new EmptyFileException("File must cannot be empty");
        }
       String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if(fileName.contains("..")){
            throw new InvalidFileFormatException("Filename contains invalid path sequence " + fileName);
        }
       var fileInput = file.getOriginalFilename();
       if(!validateFileExtn(fileInput)){
           throw new InvalidFileFormatException("Invalid file!, File must be valid csv file.");
       }

        String retVal = "";
       try {

           //convert file bytes
           byte[] bytes = file.getBytes();

           //convert bytes to string
           String stringData = new String(bytes);
           //save the file for easy access
           BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
           writer.write(stringData);
           writer.close();

           String[] rows = stringData.split("\n");
           StringBuilder sb = new StringBuilder();
           for (int i = 0, rowCount = rows.length; i < rowCount; i++) {
               String row = rows[i];
               sb.append(row.toString());
               sb.append("\n");
           }
           retVal = sb.toString().trim();
           return ResponseEntity.status(HttpStatus.OK).body(retVal) ;
       }
       catch (Exception ex){
          return null;
       }
    }

    @Override
    public ResponseEntity<String> invert() throws DataNotFoundException, FileNotFoundException {

       String lines = readDataLines();
        fileExist();
       if(lines == null || lines == ""){
          throw  new DataNotFoundException("Please upload valid csv file using echo endpoint.");
       }

       try{

           //convert lines to array
           String[] rows = lines.split("\n");

           String[][] matrix = new String[rows.length][];
           int index = 0;
           for (String row : rows) {
               matrix[index++] = row.split(",");
           }

           int transpose[][] = new int[3][3];

           for(int i=0;i<3;i++){
               for(int j=0;j<3;j++){
                   transpose[i][j]= Integer.parseInt(matrix[j][i]);
               }
           }
           StringBuilder sb = new StringBuilder();
           for(int[] item : Arrays.asList(transpose)){
               String row = Arrays.toString(item)
                       .replace("[", "")
                       .replace("]", "");
               sb.append(row.toString());
               sb.append("\n");
           }
           return ResponseEntity.status(HttpStatus.OK).body(sb.toString().trim());
       }catch (Exception ex){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
       }
    }

    @Override
    public ResponseEntity<String> flatten() throws DataNotFoundException, FileNotFoundException {
        String retVal = "";
        String lines = readDataLines();
        fileExist();

        if(lines == null || lines == ""){
            throw  new DataNotFoundException("Please upload valid csv file using echo endpoint.");
        }
        //convert lines to array
        String[] rows = lines.split("\n");
        List<String> list = new ArrayList<>();

        //Iterate through the array to create list
        for(String row : rows){
            String [] arr = row.split(",");
            for(String r: arr){
                list.add(r);
            }
        }
        //concatenate list value with comma to form string
        retVal = String.join(",", list);
        return ResponseEntity.status(HttpStatus.OK).body(retVal);
    }

    @Override
    public ResponseEntity<Integer>  sum() throws DataNotFoundException, FileNotFoundException {
        int sum = 0;
        String retVal = "";

        fileExist();
        String lines = readDataLines();
        if(lines == null || lines == ""){
            throw  new DataNotFoundException("Please upload valid csv file using echo endpoint.");
        }

        String[] rows = lines.split("\n");
        for(String line : rows){
            String [] arr = line.split(",");
            for(String c : arr){
                sum += Integer.parseInt(c);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(sum);
    }

    private void fileExist() throws FileNotFoundException {
        File file = new File("output.txt");
        if(!file.exists()){
            throw new FileNotFoundException("You must upload valid file to perform this action");
        }
    }

    @Override
    public ResponseEntity<Integer>  multiply() throws DataNotFoundException, FileNotFoundException {
        int multiply = 1;
        String retVal = "";
        fileExist();
        String lines = readDataLines();
        if(lines == null || lines == ""){
            throw  new DataNotFoundException("Please upload valid csv file using echo endpoint.");
        }
        String[] rows = lines.split("\n");
        for(String line : rows){
            String [] arr = line.split(",");
            for(String c : arr){
                multiply *= Integer.parseInt(c);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(multiply);
    }
    private static boolean validateFileExtn(String userName){

        Matcher mtch = VALIDEXTION.matcher(userName);
        if(mtch.matches()){
            return true;
        }
        return false;
    }
    private String readDataLines(){
        String retVal = "";
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("output.txt"));
            while((line = reader.readLine()) != null){
                retVal += line +"\n";
            }
            reader.close();
        } catch (FileNotFoundException e) {
            retVal = "";
        } catch (IOException e) {
            retVal = "";
        }
        return  retVal;
    }
}
