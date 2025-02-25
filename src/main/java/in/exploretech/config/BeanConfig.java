package in.exploretech.config;

import com.mongodb.client.MongoClients;
import dataman.dmbase.config.IniConfig;
import dataman.dmbase.dbutil.DatabaseUtil;
import dataman.dmbase.documentutil.DocumentUtil;
import in.exploretech.util.DebugBoundary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import in.exploretech.service.FilePathService;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
public class BeanConfig {
	
	@Autowired
	private FilePathService filePathService;

    @Autowired
    ExternalConfig externalConfig;

    @Value("${configFilePath}") // Injecting VM argument
    private String configFilePath;


	
	@Bean
    public String initialOps() {
        System.out.println("Jai Shree Krishna");
        filePathService.printFilePath();
        System.out.println(externalConfig.getMongoPort());


        return "Initialization complete";
    }

    //Database connection config start

    @Bean(name = "donationTransactionDatabaseUtil")
    public DatabaseUtil donationTransactionDatabaseUtil() {
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

    @Bean
    public DocumentUtil documentUtil() {

        IniConfig iniConfig = new IniConfig(configFilePath);

        // Force a fresh connection
        MongoClients.create().close(); // Close any existing connections
        // Encode '@' as '%40' in password


        String mongoDatabase = iniConfig.getProperty("mongoDatabase");
        String mongoHost = iniConfig.getProperty("mongoHost");
        String mongoUser = iniConfig.getProperty("mongoUser");
        String mongoPort = iniConfig.getProperty("mongoPort");
        String mongoPassword = iniConfig.getProperty("mongoPassword");

        DebugBoundary.printDebugBoundary();
        System.out.println(mongoPassword);
        System.out.println(mongoDatabase);
        System.out.println(mongoUser);
        System.out.println(mongoHost);
        System.out.println(mongoPort);
        DebugBoundary.printDebugBoundary();

        String encodedPassword = URLEncoder.encode(mongoPassword, StandardCharsets.UTF_8);

        //String databaseName = "blm";

//        String mongoUri = String.format("mongodb://%s:%s@%s:%d/%s?authSource=admin",
//                "dataman", encodedPassword, "javaserver", 27017, mongoDatabase);

        String mongoUri = String.format("mongodb://%s:%s@%s:%s/%s?authSource=admin",
                mongoUser, encodedPassword, mongoHost, mongoPort, mongoDatabase);

        DebugBoundary.printDebugBoundary();
        System.out.println(mongoUri);
        DebugBoundary.printDebugBoundary();

        // Create MongoDatabaseFactory
        MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), mongoDatabase);

        // Create MongoTemplate
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory);

        // Create GridFsTemplate
        GridFsTemplate gridFsTemplate = new GridFsTemplate(mongoDatabaseFactory, mongoTemplate.getConverter());

        // Return DocumentUtil with dependencies
        return new DocumentUtil(mongoDatabaseFactory, mongoTemplate);
    }



}
