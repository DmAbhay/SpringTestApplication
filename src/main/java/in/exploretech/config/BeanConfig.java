package in.exploretech.config;

import com.mongodb.client.MongoClients;
import dataman.dmbase.dbutil.DatabaseUtil;
import dataman.dmbase.documentutil.DocumentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import in.exploretech.service.FilePathService;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class BeanConfig {
	
	@Autowired
	private FilePathService filePathService;

    @Autowired
    ExternalConfig externalConfig;
	
	@Bean
    public String initialOps() {
        System.out.println("Jai Shree Krishna");
        filePathService.printFilePath();
        System.out.println(externalConfig.getMongoPort());


        return "Initialization complete";
    }

    //Database connection config start

    @Bean(name = "donationTransactionDatabaseUtil")
    public DatabaseUtil donationTransactionDatabaseUtil() {  // ✅ Corrected method signature
        DatabaseUtil dbUtil = new DatabaseUtil(
                "javaserver",
                "1434",
                "donation_Transaction",
                "sa",
                "dataman@123"
        );
        return dbUtil;
    }

    @Bean(name = "abhayTransactionDatabaseUtil")
    public DatabaseUtil abhayTransactionDatabaseUtil() {
        DatabaseUtil dbUtil = new DatabaseUtil(
//                "javaserver",
                "103.221.76.177",
                "1434",
                "abhay_company",
                "sa",
                "dataman@123"
        );
        return dbUtil;
    }

    //Database connection config end


//    @Bean
//    public DocumentUtil documentUtil() {
//        // Construct MongoDB URI
//        String mongoUri = String.format("mongodb://%s:%s@%s:%d/%s?authSource=admin",
//                "dataman", "dataman@123", "javaserver", "27017", "blm");
//
//        // Create MongoDatabaseFactory
//        MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), "blm");
//
//        // Create MongoTemplate
//        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory);
//
//        // Create GridFsTemplate
//        GridFsTemplate gridFsTemplate = new GridFsTemplate(mongoDatabaseFactory, mongoTemplate.getConverter());
//
//        // Return DocumentUtil with dependencies
//        return new DocumentUtil(mongoDatabaseFactory, mongoTemplate);
//    }

    @Bean
    public DocumentUtil documentUtil() {
        // Encode '@' as '%40' in password
        String mongoUri = String.format("mongodb://%s:%s@%s:%d/%s?authSource=admin",
                "dataman", "dataman%40123", "javaserver", 27017, "blm");

        // Create MongoDatabaseFactory
        MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), "blm");

        // Create MongoTemplate
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory);

        // Create GridFsTemplate
        GridFsTemplate gridFsTemplate = new GridFsTemplate(mongoDatabaseFactory, mongoTemplate.getConverter());

        // Return DocumentUtil with dependencies
        return new DocumentUtil(mongoDatabaseFactory, mongoTemplate);
    }



}
