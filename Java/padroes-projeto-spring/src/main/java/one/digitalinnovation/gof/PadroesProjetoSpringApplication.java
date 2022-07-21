package one.digitalinnovation.gof;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Projeto Spring Boot gerado via Spring Initializr.
 * Código copiado do repositório do instrutor do desafio: Venilton FalvoJr
 * Os seguintes módulos foram selecionados:
 * - Spring Data JPA
 * - Spring Web
 * - H2 Database
 * - OpenFeign
 * 
 * @author falvojr
 */
@EnableFeignClients
@SpringBootApplication
public class PadroesProjetoSpringApplication {

	//aplicação irá rodar em http://127.0.0.1:8080/swagger-ui.html
	public static void main(String[] args) {
		SpringApplication.run(PadroesProjetoSpringApplication.class, args);
	}

}
