package in.exploretech.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataman.dmbase.redissessionutil.RedisObjectUtil;

import dataman.dmbase.redissessionutil.RedisSimpleKeyValuePairUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisUtilController {

    @Autowired
    private RedisObjectUtil redisObjectUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisSimpleKeyValuePairUtil redisSimpleKeyValuePairUtil;

    @PostMapping("/save-hash")
    public ResponseEntity<String> saveObjectAsHash(@RequestParam String objectKey,
                                                   @RequestParam long timeout,
                                                   @RequestBody Map<String, Object> objectData) {
        // Using seconds as the TimeUnit
        redisObjectUtil.saveJson(objectKey, objectData, timeout, TimeUnit.SECONDS);
        redisObjectUtil.addFieldToObject(objectKey, "ournature", "to please krishna");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "krishna");
        map.put("age", "20");
        map.put("gender", "male");
        redisObjectUtil.saveJson("mymap", map, timeout, TimeUnit.SECONDS);

        redisObjectUtil.saveJson(objectKey, objectData, timeout, TimeUnit.SECONDS);
        return ResponseEntity.ok("Object saved as hash with timeout!");
    }

    @GetMapping("/get-specific-field-from-object")
    public ResponseEntity<Object> getOneFieldFromObject(@RequestParam String objectKey, @RequestParam String fieldKey) {
        Object value = redisObjectUtil.getObjectValue(objectKey, fieldKey);
        return ResponseEntity.ok(value);
    }

    @GetMapping("/get-whole-object")
    public ResponseEntity<Object> getObjectValue(@RequestParam String objectKey) {
//        Object value = redisObjectUtil.getObjectAsHash(objectKey);
//        return ResponseEntity.ok(value);

        // Get the hash as a Map
        var objectMap = redisObjectUtil.getObjectAsHash(objectKey);

        // Convert the Map to JsonNode
        JsonNode jsonNode = objectMapper.valueToTree(objectMap);
        return ResponseEntity.ok(jsonNode);
    }


    @PostMapping("/delete-field-from-object")
    public ResponseEntity<String> deleteSpecicFieldFromObject(@RequestParam String objectKey, @RequestParam String key) {
        redisObjectUtil.deleteFieldFromObject(objectKey, key);
        return ResponseEntity.ok("Field Removed Successfully");
    }

    @PostMapping("/add-one-field-to-object")
    public ResponseEntity<String> addOneFieldTotheObject(@RequestParam String objectKey, @RequestParam String key) {
        redisObjectUtil.addFieldToObject(objectKey, key, "Ashish");
        return ResponseEntity.ok("Field added to the object Successfully");
    }

    @PostMapping("/store-simple-key-value-pair")
    public ResponseEntity<?> addOneField(@RequestParam String key, @RequestParam String value){
        redisSimpleKeyValuePairUtil.storeKey(key, value, 300, TimeUnit.SECONDS);
        return ResponseEntity.ok("Your Key has been stored in Redis");
    }

    @GetMapping("/get-simple-key-value")
    public ResponseEntity<?> getValue(@RequestParam String key){
        String value = redisSimpleKeyValuePairUtil.getKey(key);
        return ResponseEntity.ok(value);
    }
}
