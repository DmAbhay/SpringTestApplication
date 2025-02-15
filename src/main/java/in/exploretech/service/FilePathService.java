package in.exploretech.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class FilePathService {
	
	@Value("${filePath}") // Injecting VM argument
    private String filePath;

    @Value("${configFilePath}") // Injecting VM argument
    private String configFilePath;

    public void printFilePath() {

        System.out.println("File Path from VM Argument: " + filePath);
        String id = UUID.randomUUID().toString().replace("-", "");
        System.out.println("Config File Path: "+ configFilePath);
        System.out.println("Id : "+ id);
    }

}
