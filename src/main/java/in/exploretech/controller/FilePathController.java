package in.exploretech.controller;

import dataman.dmbase.config.IniConfig;

import dataman.dmbase.dbutil.DatabaseUtil;
import dataman.dmbase.documentutil.DocumentUtil;
import dataman.dmbase.encryptiondecryptionutil.EncryptionDecryptionUtil;
import dataman.dmbase.redissessionutil.RedisSimpleKeyValuePairUtil;
import in.exploretech.config.ExternalConfig;
import in.exploretech.util.DebugBoundary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class FilePathController {

    @Autowired
    private ExternalConfig externalConfig;

    @Value("${configFilePath}") // Injecting VM argument
    private String configFilePath;

    @Autowired
    private EncryptionDecryptionUtil encryptionDecryptionUtil;


    @GetMapping("/test-iniConfig")
    public ResponseEntity<?> testConfigReader()  {
        System.out.println(externalConfig.getMongoPort());
        System.out.println(externalConfig.getMongoPassword());

        //ConfigReader = new ConfigReader(configFilePath);

        IniConfig iniConfig = new IniConfig(configFilePath);

        DebugBoundary.printDebugBoundary();


        String name = "Jai shree krishna";
        String encryptedName = encryptionDecryptionUtil.encrypt(name);

        System.out.println(name+" is encrypted as "+encryptedName);

        System.out.println(encryptedName+" is decrypted as "+encryptionDecryptionUtil.decrypt(encryptedName));


        DebugBoundary.printDebugBoundary();
        //System.out.println("This Mongo Port Coming via ConfigReader "+configReader.getProperty("mongoPort"));
        System.out.println("This Mongo Port Coming via ConfigReader "+iniConfig.getProperty("mongoPort"));
        return ResponseEntity.ok(encryptionDecryptionUtil.getSecretKey());
    }

    @Autowired
    @Qualifier("donationTransactionDatabaseUtil")
    private DatabaseUtil donationDbUtil;

    @Autowired
    @Qualifier("abhayTransactionDatabaseUtil")
    private DatabaseUtil abhayDbUtil;

    @Autowired
    private RedisSimpleKeyValuePairUtil redisSessionUtil;


    @PostMapping("/library-test-02")
    public String testQueryResult() {
        System.out.println(donationDbUtil.getRowAsMap("SELECT * FROM registration"));

        System.out.println(donationDbUtil.getRowsAsList("SELECT * FROM registration"));

        System.out.println(abhayDbUtil.fetchSpecificField("SELECT * FROM projectMaster WHERE projectId = 10001;", "centralDataFilePath"));
        System.out.println(abhayDbUtil.fetchSpecificField("SELECT * FROM projectMaster WHERE projectId = 10001;", "projectName"));

        //System.out.println(redisSessionUtil.generateAuthKey());
        redisSessionUtil.storeKey("authKey", "jai shree krishna bolo jai radhe", 30, TimeUnit.MINUTES);

        DebugBoundary.printDebugBoundary();
        System.out.println(redisSessionUtil.getKey("authKe"));

        String authKey = redisSessionUtil.getKey("authKey");

        if(authKey != null && authKey.equals("jai shree krishna bolo jai radhe")){
            System.out.println("You are eligible to hit API");
        }else{
            System.out.println("You are not eligible to hit API");
        }

        DebugBoundary.printDebugBoundary("❤\uD83C\uDF39");


        return "jai siya ram";
    }

    @Autowired
    private DocumentUtil documentUtil;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("bucketName") String bucketName,
                                             @RequestParam("file") MultipartFile file) {
        try {
            String fileId = documentUtil.storeFile(bucketName, file);
            return ResponseEntity.ok(fileId);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("File upload failed: " + e.getMessage());
        }
    }


}
