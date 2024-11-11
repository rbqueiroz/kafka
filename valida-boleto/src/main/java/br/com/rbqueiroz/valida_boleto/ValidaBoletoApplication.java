package br.com.rbqueiroz.valida_boleto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ValidaBoletoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidaBoletoApplication.class, args);
	}

}
