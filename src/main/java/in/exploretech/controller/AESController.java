package in.exploretech.controller;

import dataman.dmbase.encryptiondecryptionutil.EncryptionDecryptionUtil;
import dataman.dmbase.encryptiondecryptionutil.EncryptionDecryptionUtilNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AESController {



    @Autowired
    private EncryptionDecryptionUtilNew encryptionDecryptionUtilNew;


    @PostMapping("/get-secret-key-again")
    public ResponseEntity<?> getSecretKey(){

        Map<String, Object> response = new HashMap<>();

        response.put("secretKey1", encryptionDecryptionUtilNew.getSecretKey());
        response.put("secretKey2", encryptionDecryptionUtilNew.getSecretKey());
        response.put("secretKey3", encryptionDecryptionUtilNew.getSecretKey());
        response.put("secretKey4", encryptionDecryptionUtilNew.getSecretKey());
        response.put("secretKey5", encryptionDecryptionUtilNew.getSecretKey());
        response.put("secretKey6", encryptionDecryptionUtilNew.getSecretKey());
        response.put("secretKey7", encryptionDecryptionUtilNew.getSecretKey());

        return ResponseEntity.ok(response);

    }
}
