package in.exploretech.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataman.dmbase.redissessionutil.RedisObjectUtil;

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

    @PostMapping("/save-hash")
    public ResponseEntity<String> saveObjectAsHash(@RequestParam String objectKey,
                                                   @RequestParam long timeout,
                                                   @RequestBody Map<String, Object> objectData) {
        // Using seconds as the TimeUnit
        redisObjectUtil.saveObjectAsHash(objectKey, objectData, timeout, TimeUnit.SECONDS);
        redisObjectUtil.addFieldToHash(objectKey, "ournature", "to please krishna");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "krishna");
        map.put("age", "20");
        map.put("gender", "male");
        redisObjectUtil.saveObjectAsHash("mymap", map, timeout, TimeUnit.SECONDS);

        redisObjectUtil.saveObjectAsHash(objectKey, objectData, timeout, TimeUnit.SECONDS);
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
        redisObjectUtil.deleteFieldFromHash(objectKey, key);
        return ResponseEntity.ok("Field Removed Successfully");
    }

    @PostMapping("/add-one-field-to-object")
    public ResponseEntity<String> addOneFieldTotheObject(@RequestParam String objectKey, @RequestParam String key) {
        redisObjectUtil.addFieldToHash(objectKey, key, "to please krishna");
        return ResponseEntity.ok("Field added to the object Successfully");
    }




}
