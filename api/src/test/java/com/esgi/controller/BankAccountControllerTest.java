package com.esgi.controller;

import com.esgi.bank.BankAccount;
import com.esgi.crm.User;
import com.esgi.dto.AmountDTO;
import com.esgi.repository.BankAccountRepository;
import com.esgi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@PropertySource("test:application.properties")
public class BankAccountControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    private static Integer USER_ID = 1;

    @BeforeClass
    public static void setUp() {
        RestAssured.basePath = "/api";
        RestAssured.baseURI = "http://localhost";
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.config = new RestAssuredConfig()
                .objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> new ObjectMapper()))
                .encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
    }

    @Before
    public void init() {
        clean();

        RestAssured.port = port;

        User user = User.builder()
                .id(USER_ID)
                .email("test@test.fr")
                .firstname("fname")
                .lastname("lname")
                .birthdate(LocalDate.now().minusYears(25))
                .build();
        userRepository.save(user);

        BankAccount bankAccount = BankAccount.builder()
                .id(1)
                .owner(user)
                .amount(200)
                .build();
        bankAccountRepository.save(bankAccount);
    }

    // TODO

    @After
    public void clean() {
        bankAccountRepository.deleteAll();
        userRepository.deleteAll();
    }
}
