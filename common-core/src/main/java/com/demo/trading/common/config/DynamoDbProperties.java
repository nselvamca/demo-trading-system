package com.demo.trading.common.config;

import lombok.Data;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class DynamoDbProperties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String region;

    public static DynamoDbProperties load() {
        Properties props = new Properties();
        try (InputStream input = DynamoDbProperties.class
                .getClassLoader()
                .getResourceAsStream("dynamodb.properties")) {

            if (input == null) {
                throw new IllegalStateException("Cannot find dynamodb.properties on classpath");
            }

            props.load(input);
            DynamoDbProperties config = new DynamoDbProperties();
            config.setEndpoint(props.getProperty("aws.dynamodb.endpoint"));
            config.setAccessKey(props.getProperty("aws.dynamodb.access-key"));
            config.setSecretKey(props.getProperty("aws.dynamodb.secret-key"));
            config.setRegion(props.getProperty("aws.dynamodb.region"));

            return config;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load dynamodb.properties", e);
        }
    }
}
