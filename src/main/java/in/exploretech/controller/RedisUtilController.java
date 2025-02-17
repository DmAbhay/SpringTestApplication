package in.exploretech.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import dataman.dmbase.debug.Debug;
import dataman.dmbase.redissessionutil.RedisSimpleKeyValuePairUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dataman.dmbase.redissessionutil.RedisObjectUtil;
import in.exploretech.util.DebugBoundary;

@RestController
public class RedisUtilController {

    @Autowired
    private RedisObjectUtil redisObjectUtil;

    @Autowired
    private RedisSimpleKeyValuePairUtil redisSimpleKeyValuePairUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/save-hash")
    public ResponseEntity<String> saveObjectAsHash(@RequestParam String objectKey,
                                                   @RequestParam long timeout,
                                                   @RequestParam String value,
                                                   @RequestBody Map<String, Object> objectData) {
        // Using seconds as the TimeUnit
        redisObjectUtil.saveJson(objectKey, objectData, timeout, TimeUnit.SECONDS);
        redisObjectUtil.addFieldToObject(objectKey, "ournature", "to please krishna");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "krishna");
        map.put("age", "20");
        map.put("gender", "male");

        redisObjectUtil.saveJson("mymap", map, timeout, TimeUnit.SECONDS);

        Map<String, String > user = new HashMap<>();
        user.put("username", "Abhay");
        user.put("token", "lksjflkjwe59034850nvf5049gf0948nb093485094385039485nvgf439058nvg");
        user.put("userid", "ABHAY120EXPLORE");
        user.put("value", value);

        redisObjectUtil.saveObject("user", user, timeout, TimeUnit.SECONDS);


        DebugBoundary.printDebugBoundary();
        System.out.println("object is about to store in redis");

        redisObjectUtil.saveJson(objectKey, objectData, timeout, TimeUnit.SECONDS);
        System.out.println("object is stored at redis server");
        DebugBoundary.printDebugBoundary();
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


    @DeleteMapping("/delete-field-from-object")
    public ResponseEntity<String> deleteSpecicFieldFromObject(@RequestParam String objectKey, @RequestParam String key) {
        redisObjectUtil.deleteFieldFromObject(objectKey, key);
        return ResponseEntity.ok("Field Removed Successfully");
    }

    @PostMapping("/add-one-field-to-object")
    public ResponseEntity<String> addOneFieldTotheObject(@RequestParam String objectKey, @RequestParam String key) {
        redisObjectUtil.addFieldToObject(objectKey, key, "to please krishna");
        return ResponseEntity.ok("Field added to the object Successfully");
    }

    @DeleteMapping("/delete-whole-object")
    public ResponseEntity<String> deleletWholeObject(@RequestParam String objectKey){
        redisObjectUtil.deleteObject(objectKey);
        return ResponseEntity.ok("Object is deleted successfully from Redis");
    }



    //Simple Redis Key storage functions
    @PostMapping("/store-simple-key-value-pair")
    public ResponseEntity<String> storeKeyInRedis(@RequestParam String key, @RequestParam String value, @RequestParam Long expirationTime){
        redisSimpleKeyValuePairUtil.storeKey(key, value, expirationTime, TimeUnit.MINUTES);
        Debug.printDebugBoundary("❤\uD83C\uDF39\uD83C\uDF39");
        return ResponseEntity.ok("Your key is stored successfully in Redis");
    }

    @DeleteMapping("/delete-simple-key-from-redis")
    public ResponseEntity<String> deleteSimpleKey(@RequestParam String key){
        redisSimpleKeyValuePairUtil.deleteKey(key);
        return ResponseEntity.ok("Your key is deleted successfully from Redis");
    }

    @GetMapping("/get-simple-key-from-redis")
    public ResponseEntity<String> getSimpleKey(@RequestParam String key){
        return ResponseEntity.ok("Your key is: "+redisSimpleKeyValuePairUtil.getKey(key));
    }


}
