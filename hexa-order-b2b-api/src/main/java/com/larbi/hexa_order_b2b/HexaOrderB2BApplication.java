package com.larbi.hexa_order_b2b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the HexaOrder B2B application.
 * This class is used to bootstrap and launch the Spring Boot application.
 * It contains the main method, which starts the application using Spring's {@link SpringApplication}.
 */
@SpringBootApplication
public class HexaOrderB2BApplication {

	/**
	 * The main method that starts the HexaOrder B2B application.
	 * It runs the Spring Boot application using the specified configuration class.
	 *
	 * @param args command-line arguments passed to the application (if any)
	 */
	public static void main(String[] args) {
		SpringApplication.run(HexaOrderB2BApplication.class, args);
	}

}
