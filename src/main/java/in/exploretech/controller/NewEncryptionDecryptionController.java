package in.exploretech.controller;

import in.exploretech.service.EncryptionDecryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class NewEncryptionDecryptionController {

    @Autowired
    private EncryptionDecryptionService encryptionService;

    @PostMapping("/encrypt")
    public String encrypt(@RequestParam String message) throws Exception {
        return encryptionService.encrypt(message);
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestParam String encryptedMessage) throws Exception {
        return encryptionService.decrypt(encryptedMessage);
    }
}

