package in.exploretech.service;


import dataman.dmbase.encryptiondecryptionutil.EncryptionDecryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EncryptionDecryptionService {

//    private final EncryptionDecryptionUtil encryptionUtil;
//
//    public EncryptionDecryptionService(EncryptionDecryptionUtil encryptionUtil) {
//        this.encryptionUtil = encryptionUtil;
//    }

    @Autowired
    private EncryptionDecryptionUtil encryptionUtil;


    public String encrypt(String message) throws Exception {
        return encryptionUtil.encrypt(message);
    }

    public String decrypt(String encryptedMessage) throws Exception {
        return encryptionUtil.decrypt(encryptedMessage);
    }
}
