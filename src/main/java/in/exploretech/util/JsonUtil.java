package in.exploretech.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dataman.dmbase.encryptiondecryptionutil.EncryptionDecryptionUtil;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static <T> T decryptAndConvertToDTO(JsonNode payload, EncryptionDecryptionUtil encryptionDecryptionUtil, Class<T> dtoClass) throws JsonProcessingException, JsonProcessingException {

        // Get the encrypted response from payload
        String encryptedPayload = payload.get("data").asText();
        System.out.println(encryptedPayload);
        // Decrypt the encrypted message
        String decryptedPayload = encryptionDecryptionUtil.decrypt(encryptedPayload);
        // Convert the decrypted string back to a JsonNode
        JsonNode jsonNode = objectMapper.readTree(decryptedPayload);
        System.out.println("below is json node");
        System.out.println(jsonNode.toPrettyString());

        // Convert the JsonNode to the desired DTO class
        return objectMapper.treeToValue(jsonNode, dtoClass);

    }

    /**
     * Encrypts a given object and returns a ResponseEntity with the encrypted response.
     *
     * @param response                 The object to be encrypted.
     * @param encryptionDecryptionUtil The encryption utility used for encryption.
     * @return ResponseEntity containing the encrypted response.
     */
    public static ResponseEntity<?> encryptResponse(Object response, EncryptionDecryptionUtil encryptionDecryptionUtil) {
        try {
            // Convert the object to JsonNode
            JsonNode jsonNode = objectMapper.valueToTree(response);

            // Convert JsonNode to a JSON string
            String jsonString = objectMapper.writeValueAsString(jsonNode);

            // Encrypt the JSON string
            String encryptedMessage = encryptionDecryptionUtil.encrypt(jsonString);

            // Prepare the response map
            Map<String, String> result = new HashMap<>();
            result.put("data", encryptedMessage);

            // Return the encrypted response
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error encrypting JSON: " + e.getMessage());
        }
    }


}

