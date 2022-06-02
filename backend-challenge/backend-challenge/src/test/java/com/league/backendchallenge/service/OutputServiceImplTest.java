package com.league.backendchallenge.service;

import com.league.backendchallenge.exception.DataNotFoundException;
import com.league.backendchallenge.exception.EmptyFileException;
import com.league.backendchallenge.exception.InvalidFileFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
class OutputServiceImplTest {

    private  OutputService outputService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    // private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.outputService = new OutputServiceImpl();
       // mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void  When_CalledEchoWithValidFile_VerifyTheStatusCodeIsOK() throws Exception {
        //Given
        String expected = "1,2,3\n" +
                "4,5,6\n" +
                "7,8,9";

        MockMultipartFile multipartFile = new MockMultipartFile("matrix.csv","matrix.csv","text/csv",
                new FileInputStream(new File("matrix.csv")));
        //When

        ResponseEntity responseEntity = this.outputService.echo(multipartFile);

        //Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().equals(expected));
    }
    @Test
    void  When_CalledEchoWithInvalidValidFile_ShouldThrowException() throws Exception {

        MockMultipartFile multipartFile = new MockMultipartFile("matrix.txt","matrix.txt","text/plaintext",
                new FileInputStream(new File("output.txt")));

        InvalidFileFormatException thrown = assertThrows(
                InvalidFileFormatException.class,
                () -> this.outputService.echo(multipartFile)
        );


        assertTrue(thrown.getMessage().contains("Invalid file!, File must be valid csv file."));

    }

    @Test
    void When_CalledEchoWithNullShouldThrowEmptyFileException() {

        //Given
        MockMultipartFile multipartFile = null;

        EmptyFileException thrown = assertThrows(
                EmptyFileException.class,
                () -> this.outputService.echo(multipartFile)
        );
        assertTrue(thrown.getMessage().contains("File must cannot be empty"));
    }

    @Test
    void When_Called_Flatten_WithValidFileExistShouldReturnHttpStatusOk() throws DataNotFoundException, FileNotFoundException {
        String expected = "1,2,3,4,5,6,7,8,9";

        ResponseEntity responseEntity = this.outputService.flatten();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().equals(expected));

    }

    @Test
    void When_Called_Sum_WithValidFileExistShouldReturnHttpStatusOk() throws DataNotFoundException, FileNotFoundException {
        int expectedSum = 45;

        ResponseEntity responseEntity = this.outputService.sum();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().equals(expectedSum));
    }

    @Test
    void When_Called_Multiply_WithValidFileExistShouldReturnHttpStatusOk() throws DataNotFoundException, FileNotFoundException {
        int expectedProduct = 362880;

        ResponseEntity responseEntity = this.outputService.multiply();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().equals(expectedProduct));
    }


}