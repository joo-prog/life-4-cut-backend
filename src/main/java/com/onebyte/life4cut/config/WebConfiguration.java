package com.onebyte.life4cut.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Configuration
public class WebConfiguration {

    private static String DATE_FORMAT = "yyyy-MM-dd";

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(
                new JavaTimeModule().addSerializer(
                        new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT))
                ).addDeserializer(
                        LocalDate.class,
                        new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT))
                )
        );
    }
}
