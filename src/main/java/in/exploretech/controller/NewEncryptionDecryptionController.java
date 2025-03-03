package in.exploretech.controller;

import dataman.dmbase.encryptiondecryptionutil.EncryptionDecryptionUtilNew;
import in.exploretech.service.EncryptionDecryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class NewEncryptionDecryptionController {

    @Autowired
    private EncryptionDecryptionUtilNew encryptionService;

    @PostMapping("/encrypt")
    public String encrypt(@RequestParam String message) throws Exception {
        return encryptionService.encrypt(message);
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestParam String encryptedMessage) throws Exception {
        return encryptionService.decrypt(encryptedMessage);
    }

    @PostMapping("/get-secret-key-again-new")
    public ResponseEntity<?> getSecretKey(){

        Map<String, Object> response = new HashMap<>();
        response.put("secretKey1", encryptionService.getSecretKey());
        response.put("secretKey2", encryptionService.getSecretKey());
        response.put("secretKey3", encryptionService.getSecretKey());
        response.put("secretKey4", encryptionService.getSecretKey());
        response.put("secretKey5", encryptionService.getSecretKey());
        response.put("secretKey6", encryptionService.getSecretKey());
        response.put("secretKey7", encryptionService.getSecretKey());

        return ResponseEntity.ok(response.get("secretKey1"));

    }

}

