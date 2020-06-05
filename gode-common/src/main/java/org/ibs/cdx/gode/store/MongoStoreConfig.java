package org.ibs.cdx.gode.store;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.ibs.cdx.gode.entity.*")
@EntityScan("org.ibs.cdx.gode.entity.*")
@ComponentScan("org.ibs.cdx.gode")
@PropertySource(value="classpath:gode.properties")
public class MongoStoreConfig extends AbstractMongoClientConfiguration {

    @Override
    public MongoClient mongoClient() {
        MongoClient mongoClient = MongoClients.create();
//        MongoClient mongoClient = MongoClients.create(
//                MongoClientSettings.builder()
//                        .applyToClusterSettings(builder ->
//                                builder.hosts(Arrays.asList(new ServerAddress("hostOne", 27018))))
//                        .build());
        return mongoClient;
    }

    @Override
    protected String getDatabaseName() {
        return "myNewDatabase";
    }
}
