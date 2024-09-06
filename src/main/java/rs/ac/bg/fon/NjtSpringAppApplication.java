package rs.ac.bg.fon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "rs.ac.bg.fon")
public class NjtSpringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NjtSpringAppApplication.class, args);
	}

}
