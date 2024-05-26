package com.example.rentify.configuration;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
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
        return "Rentify";
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        // Create custom codec for ZonedDateTime
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(new ZonedDateTimeCodec())
        );

        // Create MongoClient with custom codec registry
        return MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(mongoUrl))
                        .codecRegistry(codecRegistry)
                        .build());
    }
}
