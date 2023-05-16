package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.Month.AUGUST;
import static java.time.Month.JANUARY;

@Configuration
public class StudentConfig {
    //bean
    //inject student repository so that this bean can access it
    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository){
        return args -> {
                    Student akshata = new Student(
                            "Akshata",
                            "akshata.dhuraji@gmail.com",
                            LocalDate.of(2000, JANUARY, 30));
                    Student ashima = new Student(
                            "Ashima",
                            "ashima.sd@gmail.com",
                            LocalDate.of(1970, AUGUST, 1));

                    //used to save all the data passed into repository
                    repository.saveAll(
                            List.of(akshata, ashima)
                    );
        };
    }
}
