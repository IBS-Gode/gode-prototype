package org.ibs.cdx.gode.store;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.ibs.cdx.gode.entity.*")
@EntityScan("org.ibs.cdx.gode.entity.*")
public class MongoStoreConfig {
}
