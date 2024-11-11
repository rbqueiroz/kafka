package br.com.rbqueiroz.api_boleto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ApiBoletoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBoletoApplication.class, args);
	}

}
