package in.exploretech.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import dataman.dmbase.debug.Debug;
import dataman.dmbase.encryptiondecryptionutil.EncryptionDecryptionUtil;
import dataman.dmbase.encryptiondecryptionutil.EncryptionDecryptionUtilNew;
import dataman.dmbase.encryptiondecryptionutil.PayloadEncryptionDecryptionUtil;
import in.exploretech.dto.RequestDTO;
import in.exploretech.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private EncryptionDecryptionUtilNew encryptionDecryptionUtilNew;

    @PostMapping("/encryption-request")
    public ResponseEntity<?> processUser(@RequestBody JsonNode request) {

//        ObjectNode response = objectMapper.createObjectNode();
//
//        try {
//
//            // Add a status field
//            response.put("status", "success");
//            response.put("message", "User data received");
//
//            // Use putPOJO() to add the entire UserRequest object as JSON
//            response.putPOJO("user", request);
//            response.putPOJO("secretKey", encryptionDecryptionUtil.getSecretKey());
//
//        } catch (Exception e) {
//
//            response.put("status", "error");
//            response.put("message", e.getMessage());
//
//        }

        try {

            String result =  objectMapper.writeValueAsString(request);
            System.out.println(result);
            HashMap<String, String> encryptedResponse = new HashMap<>();

            encryptedResponse.put("data", encryptionDecryptionUtilNew.encrypt(result));
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
        String decryptedPayload = encryptionDecryptionUtilNew.decrypt(encryptedPayload);
        // Convert the decrypted string back to a JsonNode
        try {
            JsonNode response = objectMapper.readTree(decryptedPayload);
            System.out.println(response.toPrettyString());
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/decrypt-and-convert-to-dto")
    public ResponseEntity<?> testDirect(@RequestBody JsonNode payload){
        try {
            Debug.printDebugBoundary("✔");
            System.out.println(payload.toPrettyString());
            Debug.printDebugBoundary("✔");
            // Use the generic method to convert to ApplicantBankDetailsDTO
            //RequestDTO requestDTO = PayloadEncryptionDecryptionUtil.decryptAndConvertToDTO(payload, encryptionDecryptionUtil, RequestDTO.class);
            RequestDTO requestDTO = PayloadEncryptionDecryptionUtil.decryptAndConvertToDTO(payload, encryptionDecryptionUtilNew, RequestDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(requestDTO);
        } catch (JsonProcessingException error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while mapping JSON to DTO: " + error.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/encrypt-object-and-send-map")
    public ResponseEntity<?> getEncryptedResponse() {

        RequestDTO response = new RequestDTO();

        response.setAge(20);
        response.setName("Govind");
        response.setUserId("userid");
        response.setGender("male");

        // Use the utility method to encrypt response
        //return PayloadEncryptionDecryptionUtil.encryptResponse(response, encryptionDecryptionUtil);
        return PayloadEncryptionDecryptionUtil.encryptResponse(response, encryptionDecryptionUtilNew);
    }

//    @GetMapping("/get-secret-key")
//    public ResponseEntity<?> getSecretKey(){
//        return ResponseEntity.ok();
//    }
}
