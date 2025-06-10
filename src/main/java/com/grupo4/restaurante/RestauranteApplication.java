package com.grupo4.restaurante; // Paquete raíz del proyecto, contiene la clase principal.

//Importación de paquetes necesarios para iniciar la aplicación.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Anotación @SpringBootApplication:
 * Marca esta clase como la clase principal de una aplicación Spring Boot.
 * Combina tres anotaciones:
 * - @Configuration: indica que esta clase puede contener beans configurables.
 * - @EnableAutoConfiguration: permite que Spring Boot configure automáticamente el proyecto según las dependencias.
 * - @ComponentScan: escanea automáticamente los paquetes para detectar componentes, controladores y servicios.
 */

@SpringBootApplication
public class RestauranteApplication {

	/**
	 * Mé-t-odo principal (main):
	 * este es el punto de entrada de la aplicación.
	 * SpringApplication.run(...) inicia toda la aplicación Spring.
	 * Al ejecutarse, configura el contexto de Spring y lanza el servidor embebido (como Tomcat).
     */
	public static void main(String[] args) {

		SpringApplication.run(RestauranteApplication.class, args);
	}

}
