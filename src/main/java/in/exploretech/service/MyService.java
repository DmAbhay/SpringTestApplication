package in.exploretech.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public MyService(String message) { // Injecting the bean
        System.out.println("Injected Bean Value: " + message);
    }
    
    public int add(String message) {
    	System.out.println("injected Bean value "+ message);
    	return 12;
    }
    
    public String show(String message) {
    	System.out.println("Injected Bean Value "+ message);
    	return "shown";
    }
}
