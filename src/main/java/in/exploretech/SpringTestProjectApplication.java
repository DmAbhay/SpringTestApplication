package in.exploretech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"in.exploretech", "dataman.dmbase.encryptiondecryptionutil", "dataman.dmbase.redissessionutil", "dataman.dmbase.documentutil","dataman.dmbase.customconfig"})
public class SpringTestProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTestProjectApplication.class, args);
	}

}
