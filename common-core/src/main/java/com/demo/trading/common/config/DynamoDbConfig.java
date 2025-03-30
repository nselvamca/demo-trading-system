package com.demo.trading.common.config;


import com.demo.trading.common.model.entity.OrderHistoryEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

@Configuration
public class DynamoDbConfig {

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        DynamoDbProperties config = DynamoDbProperties.load();

        return DynamoDbAsyncClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(config.getAccessKey(), config.getSecretKey())))
                .endpointOverride(URI.create(config.getEndpoint()))
                .region(Region.of(config.getRegion()))
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient enhancedAsyncClient(DynamoDbAsyncClient asyncClient) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(asyncClient)
                .build();
    }

    @Bean
    public DynamoDbAsyncTable<OrderHistoryEntity> orderHistoryTable(DynamoDbEnhancedAsyncClient enhancedAsyncClient) {
        return enhancedAsyncClient.table("OrderHistoryTable", TableSchema.fromBean(OrderHistoryEntity.class));
    }
}

