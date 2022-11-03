package team2.chartBox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@SpringBootApplication
public class ChartBoxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChartBoxApplication.class, args);
	}

}
