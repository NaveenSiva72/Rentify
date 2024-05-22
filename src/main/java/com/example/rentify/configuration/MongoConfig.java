package com.example.rentify.configuration;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${mongoUrl}")
    private String mongoUrl;
    @Override
    protected String getDatabaseName() {
        return "Rentify"; // your database name
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUrl);
    }
}

