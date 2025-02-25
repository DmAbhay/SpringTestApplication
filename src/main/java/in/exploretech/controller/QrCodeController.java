package in.exploretech.controller;

import org.springframework.web.bind.annotation.RestController;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class QrCodeController {

//    @GetMapping(value = "/api/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    @GetMapping("/api/qrcode")
    public ResponseEntity<byte[]> generateQrCode(@RequestParam("text") String text) {
        try {
            // Create a QRCodeWriter instance
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            // Generate a BitMatrix representing the QR code
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);

            // Convert the BitMatrix to a PNG image in a byte array
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();

            // Return the image as the response entity
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(pngData);
        } catch (WriterException | IOException e) {
            // Log exception here if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

