package com.grupo4.restaurante.config;

/*
 * Clase StartupEndPointLogger
 * Se encarga de imprimir los endpoints disponibles de la aplicación
 * en una tabla al iniciar el proyecto.
 * Los endpoints se imprimen por consola en una tabla legible.
 * Y se exportan a un archivo en el formato seleccionado (csv, txt, log, pdf).
 *
 * - Evita mostrar rutas internas de Spring para mayor seguridad.
 * - Permite configurar el formato del archivo exportado.
 * Spring te permite acceder a los mappings registrados por medio de RequestMappingHandlerMapping
 * creamos la clase StartupLogger.java para imprimir todos los endpoints con sus métodos HTTP.
 *
 * Autor: David De La Puente
 * Versión: 1.0
 * Desde: 2025-06-06
 */

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Component
@SuppressWarnings("unused")
public class StartupLogger {

    // Carpeta donde se guardarán los archivos de logs de endpoints.
    private static final String LOG_FOLDER = "logs";

    // Inyección de la aplicación para acceder a los beans registrados.
    @Autowired
    private ApplicationContext applicationContext;

    // Cargamos el formato desde application.properties
    @Value("${startup.endpoints.export-format:txt}")
    private String exportFormat;

    /**
     * Méto-do ejecutado después de la construcción del bean.
     * Recorre los controladores mapeados y muestra sus endpoints en tabla.
     */

    @PostConstruct
    public void logAllEndPoints() {
        // Obtenemos el handler mapping para acceder a todos los endpoints mapeados.
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);

        // Mapa ordenado de endpoints: clave = ruta, valor = clase::mét-odo.
        Map<String, String> endpoints = new TreeMap<>();

        handlerMapping.getHandlerMethods().forEach((mappingInfo, handlerMethod) -> {
            // Obtenemos el paquete de la clase controlador.
            String controller = handlerMethod.getBeanType().getPackageName();

            // Solo incluimos controladores dentro de nuestro paquete de la app
            // Filtramos solo los controladores de nuestro paquete (excluye rutas internas de spring).
            if (controller.startsWith("com.grupo4.restaurante.controllers")) {
                String clase = handlerMethod.getBeanType().getSimpleName();
                String metodo = handlerMethod.getMethod().getName();
                String rutas = mappingInfo.getPatternValues().toString();
                endpoints.put(rutas, clase + "::" + metodo);
            }
        });

        // Imprimir en tabla en consola.
        imprimirTablaEnConsola(endpoints);

        // Exportar archivo con los endpoints en formato elegido.
        exportarArchivo(endpoints);
    }


    // Muestra una tabla con los endpoints detectados en la consola.
    private void imprimirTablaEnConsola(Map<String, String> endpoints) {
    String border = "+---------------------------------------------------------+------------------------------------------+";
    String header = "| Endpoint                                                | Método                                   |";

        log.info("\n{}", border);
    log.info(header);
    log.info(border);

    endpoints.forEach((ruta,metodo)->
        log.info("| {} | {} |", padRight(ruta, 55), padRight(metodo, 40))
    );

    log.info(border);
    }

    /**
     * Exporta los endpoints detectados a un archivo usando el formato definido por el usuario.
     */

    private void exportarArchivo(Map<String, String> endpoints) {
        try {
            // Crear carpeta logs si no existe.
            Files.createDirectories(Path.of(LOG_FOLDER));

            // Formatear fecha actual para el nombre del archivo.
            String timestamp = LocalDateTime.now().toString().replace(":", "-");

            // Crear el nombre del archivo con extensión según formato.
            String filename = LOG_FOLDER + "/startup-endpoints-" + timestamp + "." + exportFormat.toLowerCase();

            // Abrimos el archivo para escribir los endpoints.
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

                // Exportación como CSV
                switch (exportFormat.toLowerCase()) {
                    case "csv", "pdf" -> {
                        writer.write("endpoint,metodo\n");
                        endpoints.forEach((ruta, metodo) -> {
                            try {
                                writer.write(ruta + "," + metodo + "\n");
                            } catch (Exception ignored) {

                            }
                        });
                    }
                    default -> {
                        // txt, log, etc.
                        writer.write("+---------------------------------------------------------+------------------------------------------+");
                        writer.write("| Endpoint                                                | Método                                   |");
                        writer.write("+---------------------------------------------------------+------------------------------------------+");
                        endpoints.forEach((ruta, metodo) -> {
                            try {
                                writer.write(String.format("| %-55s | %-40s |\n", ruta, metodo));
                            } catch (Exception ignored) {

                            }
                        });
                        writer.write("+---------------------------------------------------------+------------------------------------------+");
                    }
                }
            }

            log.info("🗃️ Endpoints exportados correctamente a: {}", exportFormat.toUpperCase());
            log.info("📁 Archivo generado: {}", filename);
            log.info("📊 Total de endpoints registrado: {}", endpoints.size());
            log.info("✅ Endpoints exportados correctamente a: {}", filename);
        } catch (Exception e) {
            log.error("❌ Error al exportar los endpoints: {}", e.getMessage());
        }
    }

    /**
     *  Esta función se encarga de formatear el texto mostrado en los log por consola.
     *  Dejando así un codigo más limpio.
     *  Añade espacios a la derecha para mantener el formato de la tabla.
     */
    private String padRight(String text, int length) {
        return String.format("%-" + length + "s", text);
    }
}


/* Esta opción la realicé primero, pero me dí cuenta de, qué
 * quizas en mi ansia de hacer el codigo más legible
 * comprensible por compañeros o futuros desarrolladores
 * podría estar incurriendo en un problema de seguridad.
 * al ser la primera vez que pruebo esto y no entender aun
 * bien como funciona.
    @Override
    public void run(String... args) {

        String port = env.getProperty("server.port", "8080");
        String profile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "default";
        String url = "http://localhost:" + port;
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        long controllerCount = applicationContext.getBeanNamesForAnnotation(org.springframework.stereotype.Controller.class).length;

        // Cabecera De La Tabla
        String line = "+---------------------------+-------------------------------------------+";
        System.out.println("\n" + line);
        System.out.printf("| %-25s | %-39s |\n", "🚀 Aplicación iniciada en", url);
        System.out.println(line);
        System.out.printf("| %-25s | %-39s |\n", "🕰️ Fecha de inicio", now);
        System.out.printf("| 👨‍💻 %-23s | %-39s |\n", "perfil activo", profile);
        System.out.printf("| 🎛️ %-23s | %-39s |\n", "Nº de Controladores", controllerCount);
        System.out.println(line);

        // Endpoints
        System.out.println("🔚 ENDPOINTS REGISTRADOS:");
        System.out.println("+----------------------+---------------------------+");
        System.out.printf("| %-20s | %-26s |\n", "Méto-do HTTP", "Ruta");
        System.out.println("+----------------------+---------------------------+");

        for (Map.Entry<?, HandlerMethod> entry : handlerMapping.getHandlerMethods().entrySet() {
            String path = entry.getKey().toString().replaceAll(".*\\[(.*)]", "$1");
            String method = entry.getValue().getMethod().getDeclaringClass().getSimpleName();
            String httpMethod = entry.getKey().toString().split(" ")[0].replaceAll("[^A-Z]", "");
            System.out.printf("| %-20s | %-26s |\n", httpMethod, path);
        }

        System.out.println("+----------------------+---------------------------+");

    }


}
*/