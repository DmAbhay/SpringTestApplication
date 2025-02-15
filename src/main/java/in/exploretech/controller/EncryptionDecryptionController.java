package in.exploretech.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import dataman.dmbase.encryptiondecryptionutil.EncryptionDecryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class EncryptionDecryptionController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    //private final EncryptionDecryptionUtil encryptionDecryptionUtil = new EncryptionDecryptionUtil();

    @Autowired
    private EncryptionDecryptionUtil encryptionDecryptionUtil;

    @PostMapping("/encryption-request")
    public ResponseEntity<?> processUser(@RequestBody JsonNode request) {

        ObjectNode response = objectMapper.createObjectNode();

        try {
            // Add a status field
            response.put("status", "success");
            response.put("message", "User data received");

            // Use putPOJO() to add the entire UserRequest object as JSON
            response.putPOJO("user", request);
            response.putPOJO("secretKey", encryptionDecryptionUtil.getSecretKey());


        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        try {
            String result =  objectMapper.writeValueAsString(response);
            System.out.println(result);
            HashMap<String, String> encryptedResponse = new HashMap<>();

            encryptedResponse.put("data", encryptionDecryptionUtil.encrypt(result));
            return ResponseEntity.ok(encryptedResponse);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/decrypt-request")
    public ResponseEntity<?> decryptPayload(@RequestBody JsonNode request) throws Exception {



        String encryptedPayload = request.get("data").asText();

        String decryptedPayload = encryptionDecryptionUtil.decrypt(encryptedPayload);

        // Convert the decrypted string back to a JsonNode
        try {
            JsonNode response = objectMapper.readTree(decryptedPayload);
            System.out.println(response.toPrettyString());
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
