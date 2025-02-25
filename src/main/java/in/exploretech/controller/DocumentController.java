package in.exploretech.controller;


import dataman.dmbase.documentutil.DocumentUtil;
import dataman.dmbase.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class DocumentController {

    @Autowired
    private DocumentUtil documentUtil;


    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam String bucketName, @RequestParam MultipartFile file) throws IOException {
        return documentUtil.storeFile(bucketName, file);
    }

    @GetMapping("/get-file-response")
    public ResponseEntity<?> getFileResponse(@RequestParam String bucketName, @RequestParam String fileLink){
        return ResponseEntity.ok(documentUtil.getFileResponse(bucketName, fileLink));
    }
    @GetMapping("/get-file-as-bytes")
    public ResponseEntity<?> getFileAsBytes(@RequestParam String bucketName, @RequestParam String fileLink) throws IOException {
        return ResponseEntity.ok(documentUtil.getFileAsBytes(bucketName, fileLink));
    }
    @GetMapping("/get-file")
    public ResponseEntity<?> getFile(@RequestParam String bucketName, @RequestParam String fileLink){
        return ResponseEntity.ok(documentUtil.getFile(bucketName, fileLink));
    }

    @DeleteMapping("/delete-file")
    public ResponseEntity<?> deleteFile(@RequestParam String bucketName, @RequestParam String fileLink){
        documentUtil.deleteFileByIdAndCollectionName(bucketName, fileLink);
        return ResponseEntity.ok("file Deleted...!");
    }

}
